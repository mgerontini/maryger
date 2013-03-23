#! /usr/bin/env python
import sys

results = list()
def readFiles():
    
    for i in range(1,len(sys.argv)):
        dnaList = list()
        f = open ("../" + sys.argv[i], "r")
       
        line = f.readline()
        
       #  sys.stdout.write(str())
        while line:  
            if not line.startswith('>'):
               
                dnaList.append(line)
                
            line = f.readline()
        else: 
            
            results.append( computeGC(dnaList)) 

    return results


def computeGC(dnaList):
    string = ''.join(dnaList)
    G = string.count('G')
    
    C = string.count('C')
   
    
    res = (G+C)/ float (len(string))
    
    return res


def main():
   res = readFiles()
   for i in range(0,len(res)):
       sys.stdout.write("%.3f" % res[i]+"\n")
       

main()