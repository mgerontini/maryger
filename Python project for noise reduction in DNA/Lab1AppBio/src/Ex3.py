#!/usr/bin/python
import random
import math

def add(a,b):
    return a+b


  
input_var = input("Enter the length of desired sequence: ")
userNumber = int(input_var) 

nucleotides = ["A","C","G","T"]


newLine = 0

#print choise
Line = ''
for i in range(1, userNumber+1):
    chois = random.uniform(0, 4)
    choise = int( math.floor(chois) )
    Line += nucleotides[choise]  #random.choice(nucleotides)    
    newLine = i%80
    if i == userNumber or newLine == 0: 
        
        Line+='\n'
        
else:
    sequenceName = "my"+str(userNumber)+"randomSequence"
    print sequenceName+'\n' 
    print  Line
    

