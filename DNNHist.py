#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Fri Feb 14 14:07:49 2020

@author: gracehymas
"""


import sys
import time

import pandas as pd
from keras.layers import Dense
from keras.models import Sequential
from sklearn.preprocessing import scale

sys.path.append("../dataset-and-plotting")
from nnPlotting import *

def totalSensitivity(A,B,errorA,errorB):
    totalSensitivity = np.sqrt(A**2 + B**2)
    totalError = np.sqrt(((A*errorA)/np.sqrt(A**2 + B**2))**2 + ((B*errorB)/np.sqrt(A**2 + B**2))**2)

    return (totalSensitivity,totalError)

print("STARTED")
start = time.time()

for nJets in [2,3]:

    print("STARTED TRAINING " + str(nJets) + " Jet Neural Network")

    if nJets == 2:
        variables = ['nTrackJetsOR', 'MV1cB1_cont', 'MV1cB2_cont', 'mBB', 'dRBB', 'pTB1', 'pTB2', 'MET', 'dPhiVBB','dPhiLBmin', 'Mtop', 'dYWH', 'mTW', 'pTV']

    else:
        variables = ['nTrackJetsOR', 'MV1cB1_cont', 'MV1cB2_cont', 'mBB', 'dRBB', 'pTB1', 'pTB2', 'MET', 'dPhiVBB','dPhiLBmin', 'Mtop', 'dYWH', 'mTW', 'pTV', 'mBBJ', 'pTJ3', 'MV1cJ3_cont']

    # Read in Data
    dfEven = pd.read_csv('../dataset-and-plotting/CSV_withBinnedDijetMassValues/ADV_' + str(nJets) + 'jet_batch_even.csv', index_col=0)
    dfOdd = pd.read_csv('../dataset-and-plotting/CSV_withBinnedDijetMassValues/ADV_' + str(nJets) + 'jet_batch_odd.csv', index_col=0)

    # Process Even Events
    xEven = scale(dfEven[variables].to_numpy())
    yEven = dfEven['Class'].to_numpy()
    wEven = dfEven['training_weight'].to_numpy()

    # Process Odd Events
    xOdd = scale(dfOdd[variables].to_numpy())
    yOdd = dfOdd['Class'].to_numpy()
    wOdd = dfOdd['training_weight'].to_numpy()

    # Define Architecture
    def DNNClassifier():

        model = Sequential()

        # Add Layers
        model.add(Dense(units=14, input_shape=(xEven.shape[1],), activation='relu')) # 1st layer
        model.add(Dense(14, init='uniform', activation='relu')) # hidden layer
        model.add(Dense(14, init='uniform', activation='relu')) # hidden layer
        model.add(Dense(1,activation='sigmoid')) # output layer
        model.compile(loss='binary_crossentropy', optimizer='SGD', metrics=['accuracy'])
        return model

    # Create and Compile models
    modelEven = DNNClassifier()
    modelOdd = DNNClassifier()

    # Set these parameters
    epochs = 200
    batchSize = 100

    # Train Model
    modelEven.fit(xEven,yEven, sample_weight = wEven, epochs=epochs, batch_size=batchSize, verbose = 1)
    modelOdd.fit(xOdd,yOdd, sample_weight = wOdd, epochs=epochs, batch_size=batchSize, verbose = 1)
    
    #modelEven.fit(dfEven[variables], dfEven['Class'], sample_weight=dfEven['training_weight'])
    #modelOdd.fit(dfOdd[variables], dfOdd['Class'], sample_weight=dfOdd['training_weight'])

    print("Time Taken = " + str(round(time.time()-start,2))+"s")

    ## EVALUATION DNN & Plots
    dfOdd['decision_value'] = modelEven.predict_proba(xOdd)
    dfEven['decision_value'] = modelOdd.predict_proba(xEven)
    df = pd.concat([dfOdd,dfEven])
    
    dfSorted = df.sort_values('decision_value')
    
    ##### Signal Events in LOW (-1.0 to +0.8) & HIGH (+0.8 to +1.0) NN REGION GRAPHS #####
    dfSorted_minus1plus08 = dfSorted.loc[dfSorted['decision_value'] <= 0.8]
    dfSorted_plus08plus1 = dfSorted.loc[dfSorted['decision_value'] > 0.8]
    
    dfNEW = dfSorted_minus1plus08
    savedSignalData = (dfNEW.loc[dfNEW['Class'] == 1])['mBB_raw'] #you can also use dfNEW['column_name']
    plt.hist([savedSignalData], 400,  label="(-1.0 to +0.8) of $NN_{output}$", stacked=True, alpha=0.75)

    dfNEW2 = dfSorted_plus08plus1
    savedSignalData = (dfNEW2.loc[dfNEW2['Class'] == 1])['mBB_raw'] #you can also use dfNEW['column_name']
    plt.hist([savedSignalData], 50,  label="(+0.8 to +1.0) of $NN_{output}$", stacked=True, alpha=0.75)

    plt.xlabel("mBB, MeV")
    plt.xlim(0,250000)
    plt.xticks(rotation=45)
    plt.ylabel('Events')
    plt.legend()
    plt.title('mBB Signal - in Low & High NN output Regions')
    plt.grid(True)

    # save figure
    figureName = "mBB_NN_SignalLowVsHighRegion" + str(nJets) + "Jet.pdf"
    fig = plt.gcf()
    plt.savefig(figureName, dpi=100, bbox_inches='tight')
    plt.show()
    fig.clear()
    #####
    
print("Total Time Taken", time.time() - start)
print("FINISHED")