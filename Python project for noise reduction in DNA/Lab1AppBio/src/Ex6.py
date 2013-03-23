'''
Created on 30 Okt 2012

@author: maryger
'''
# Stop Codons
# UAG
# UGA
# UAA

import sys
import re

codons = {}
codons['AAA'] = 'K'
codons['AAC'] = 'N'
codons['AAG'] = 'K'
codons['AAU'] = 'N'
codons['ACA'] = 'T'
codons['ACC'] = 'T'
codons['ACG'] = 'T'
codons['ACU'] = 'T'
codons['AGA'] = 'R'
codons['AGC'] = 'S'
codons['AGG'] = 'R'
codons['AGU'] = 'S'
codons['AUA'] = 'I'
codons['AUC'] = 'I'
codons['AUG'] = 'M'
codons['AUU'] = 'I'
codons['CAA'] = 'Q'
codons['CAC'] = 'H'
codons['CAG'] = 'Q'
codons['CAU'] = 'H'
codons['CCA'] = 'P'
codons['CCC'] = 'P'
codons['CCG'] = 'P'
codons['CCU'] = 'P'
codons['CGA'] = 'R'
codons['CGC'] = 'R'
codons['CGG'] = 'R'
codons['CGU'] = 'R'
codons['CUA'] = 'L'
codons['CUC'] = 'L'
codons['CUG'] = 'L'
codons['CUU'] = 'L'
codons['GAA'] = 'E'
codons['GAC'] = 'D'
codons['GAG'] = 'E'
codons['GAU'] = 'D'
codons['GCA'] = 'A'
codons['GCC'] = 'A'
codons['GCG'] = 'A'
codons['GCU'] = 'A'
codons['GGA'] = 'G'
codons['GGC'] = 'G'
codons['GGG'] = 'G'
codons['GGU'] = 'G'
codons['GUA'] = 'V'
codons['GUC'] = 'V'
codons['GUG'] = 'V'
codons['GUU'] = 'V'
codons['UAA'] = '*'
codons['UAC'] = 'Y'
codons['UAG'] = '*'
codons['UAU'] = 'Y'
codons['UCA'] = 'S'
codons['UCC'] = 'S'
codons['UCG'] = 'S'
codons['UCU'] = 'S'
codons['UGA'] = '*'
codons['UGC'] = 'C'
codons['UGG'] = 'W'
codons['UGU'] = 'C'
codons['UUA'] = 'L'
codons['UUC'] = 'F'
codons['UUG'] = 'L'
codons['UUU'] = 'F'
stop_codons = ["TAA","TAG","TGA"]
single_stop_codon = ""
stopcodons = list()
ambiguities = list()
proteinalphabet = list()
proteinalphabet2 = list()
proteinalphabet3 = list()
titles = list()
elements = list()

def getORFwithoutStopCodon(listOfDNA):
    
    resultList = list()
    result1 = ""
    result2 = ""
    result3 = ""
    result4 = ""
   
  
   
    
    string = ''.join(listOfDNA)
    string = string.upper()
    for i in range(1, len(string), 3):
            
            if string[i:(i + 3)].upper() in stop_codons:
                result1+=string[i:(i + 3)]
                resultList.append(result1)
                break
            else:
                str = string[i:(i + 3)] 
                if len(str)==3:
                    result1 += string[i:(i + 3)] 
    else:
        resultList.append(result1) 
    for i in range(2, len(string), 3):
            if string[i:(i + 3)].upper() in stop_codons:
                result2 += string[i:(i + 3)]
                resultList.append(result2)
                break
            else: 
                str = string[i:(i + 3)] 
                if len(str)==3: 
                    result2 += str  
    else:
        resultList.append(result2)  
    for i in range(3, len(string), 3):
            if string[i:(i + 3)].upper() in stop_codons:
                result3 += string[i:(i + 3)]
                resultList.append(result3)
                break
            else: 
                str = string[i:(i + 3)] 
                if len(str)==3:  
                    result3 += str
    else:
        resultList.append(result3)
        
    for i in range(0, len(string), 3):
            if string[i:(i + 3)].upper() in stop_codons:
                result4 += string[i:(i + 3)]
                resultList.append(result4)
                break
            else: 
                str = string[i:(i + 3)] 
                if len(str)==3:  
                    result4 += str
 
    else:
        resultList.append(result4)   
    
    return resultList

def getBiggestORF(listOfDNA):
   
    string = max(listOfDNA, key=len)
       
    return string

def getTranslatedORF(string):
   # print "dna: "+string
    result = ""
    regex = re.compile('[A,T,G,C]+$')
    if len(string) == 1 :
        result = " "
     
    elif  re.match("^[ATGC]*$", string):
         
        for i in range(0, len(string), 3): 
                result += getAminoFromCodon(string[i:(i + 3)].upper())
    else :
        for i in range(0, len(string)): 
            if i%3 == 0:
                result += "X"
    #print result    
    return result    

def getAminoFromCodon(codon):
    codon = str(codon)
    codon = codon.replace("T", "U")
    
    
    return codons[str(codon)]

def getBlocksOfInfo():
  #  input_var = raw_input("Which sequence file you want to read: ") 
    f = open ("../translationtest.dna.txt" , "r")
    line = f.readline()
    block = list()
    firstTime = True
    while line:
    
        
        if line.startswith('>')  :
            if firstTime:
                titles.append(line)
                firstTime = False
            else:
                elements.append(block)
                titles.append(line)
                block = list()
        else:
            block.append(line.replace('\n', ''))
       
        line = f.readline()
    else:        
        elements.append(block)
        block = list()
       
      
        
def getTranslatedbiggestORFs():
    string = ""
    getBlocksOfInfo()
   
    for i in range(0, len(titles)):
        
        if i < len(elements):
            title = str(titles[i])
            
            if title.startswith(">single_stop_codon"):
                single_stop_codon = elements[i][0]
                codons[single_stop_codon] = '*'
                sys.stdout.write (str(titles[i]) + '\n')
            elif title.startswith(">stopcodon"):
                result = getBiggestORF(getORFwithoutStopCodon(elements[i]))
                result = getTranslatedORF(str(result))
                fn = result
               
                #print ">stopcodon"+"\n"+result
                   # print result
                sys.stdout.write (title.rstrip('\n')+'\n'+  result + '\n')
            else:
                delimString = re.split('[ \t]', str(title))
                result = getBiggestORF(getORFwithoutStopCodon(elements[i]))
                result = getTranslatedORF(result)
                #print title+"\n"+result
                sys.stdout.write (delimString[0].rstrip('\n')+'\n'+ result + '\n')

        
    else : return string

    



print getTranslatedbiggestORFs()




