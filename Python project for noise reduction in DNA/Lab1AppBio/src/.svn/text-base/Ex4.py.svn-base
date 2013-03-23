
import re


input_var = raw_input("Enter the file name you want to read: ") + ".txt"

f = open ("../"+input_var,"r")

#Read file line by line
line = f.readline()

re1='^(P)'    # Any Single Word Character (Not Whitespace) 1
re2='(R)'    # Any Single Word Character (Not Whitespace) 2
re3='(O)'    # Any Single Word Character (Not Whitespace) 3
re4='(T)'    # Any Single Word Character (Not Whitespace) 4
re5='(1)'    # Any Single Digit 1
re6='(_)'    # Any Single Character 1
re7='((?:[a-z][a-z]+))'    # Word 1
pattern = re1+re2+re3+re4+re5+re6+re7
rg = re.compile(pattern,re.IGNORECASE|re.DOTALL)

noMatches = 0
resultString = ""

while line:
    result = rg.search(line)
    if result is None:
        pass
    else:
        noMatches += 1
        resultString +=  result.group() +'\n'
    line = f.readline()

# Close the file and print result
print str(noMatches)
print resultString
f.close()
