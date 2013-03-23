'''
Created on 30 Okt 2012

@author: maryger
'''
import sys
import re


def printStringInFarsta(string):

    if string.startswith('#') or string.startswith('//') or string.startswith('\n') :
        pass
    else:
        delimString = re.split('[ \t]', string)
        #  delimString = string.split(seps)
        
       
        if len(delimString)>=2:
            sys.stdout.write('>'+delimString[0])
            sys.stdout.write( constructFarstaBlocks(delimString[len(delimString)-1]).rstrip('\n')+'\n')

def constructFarstaBlocks(string):    
    Line = ''
    for i in range(0,len(string)):
        
        if i ==  len(string) or i%60==0:
            
            Line +='\n'
        Line += string[i]
    else: return Line 

def readStringSequences():
    input_var = raw_input("Which sequence file you want to read: ") 
    f = open ("../" + input_var, "r")
    line = f.readline()
    while line:
        printStringInFarsta(line)
        
        line = f.readline()
        


#Main part block
readStringSequences()
