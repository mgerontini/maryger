#! /usr/bin/env python
import sys
from numpy.lib.scimath import sqrt



titles = []


def calcDistance():
    arr = []
    
    listOfComp = readFilesAndCalcComp()
    for i in range(0,len(listOfComp)):
        piX = listOfComp[i]
        results = list()
        for j in range(0,len(listOfComp)):
            piY = listOfComp[j]
            diff_arr = [pow((piX[0]-piY[0]),2),pow((piX[1]-piY[1]),2),pow((piX[2]-piY[2]),2),pow((piX[3]-piY[3]),2)]
            float_diff_arr = float(diff_arr[0] +diff_arr[1]+diff_arr[2]+diff_arr[3])

            diff = sqrt(float_diff_arr/float(4))
            results.append(diff)
            #arr[i].append(diff)
            
        else:
            arr.append(results)
    return arr

def calcSingleComposition(dnaList):
    
    string = ''.join(dnaList)
    N = string.count('G')+string.count('C')+string.count('A')+string.count('T')
    G = string.count('G') /float(N)   
    C = string.count('C') /float(N)
    A = string.count('A') /float(N)  
    T = string.count('T') /float(N)
    
    
    nucleotideComp = [A,C,G,T]  
    print nucleotideComp 
    return nucleotideComp
    

def readFilesAndCalcComp():
    results = list()
    
    
    for i in range(1,len(sys.argv)):
        dnaList = list()
        titles.append(sys.argv[i])
        f = open ("../"+sys.argv[i]  , "r")
        
        line = f.readline()
        
       #  sys.stdout.write(str())
        while line:  
            if not line.startswith('>'):
               
                dnaList.append(line)
                
            line = f.readline()
        else: 
            
            results.append( calcSingleComposition(dnaList)) 

    return results



def printEverything():
    res = calcDistance()
    
    string = max(titles, key=len)
    tabs = ""
    for i in range(0,len(string)):
        tabs+=" "
    sys.stdout.write(tabs+'\t'+str(titles)+'\n')
    for i in range(0,len(titles)):
        sys.stdout.write(str(titles[i])+"\t"+str(res[i])+"\n")

printEverything()        
        