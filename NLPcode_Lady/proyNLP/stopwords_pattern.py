#!/usr/bin/env python
# -*- coding: utf-8 -*-
from pattern.vector import words, count
from pattern.en import parse, split
import codecs

#stopwords = ['-',',','!','?']


stopwords = []
stopfile= codecs.open("stopwords","r",encoding="UTF-8")
for line in stopfile:
	stop = str(line)
	stop1 = stop.encode('utf8')
	stop2 = stop1.replace("\n","")
	stopwords.append(stop2)
 
print stopwords
#for i in stopwords:
#	print i


string = "Science - general!?"
"""
#string = "Radiofrequency microelectromechanical systems. There are functional requirements to develop a successful software."
print string

#funcionw= words(string, filter = lambda w: w.strip("'").isalnum(), punctuation = '.,;:!?()[]{}`''\"@#$^&*+-|=~_')
funcionw= words(string, filter = lambda w: w.strip("'").isalnum(), punctuation = '.,;:!?()[]{}`''\"@#$^&*+|=~_')
print funcionw


funcionc = count(
      words = funcionw, 
        top = None,         # Filter words not in the top most frequent (int).
  threshold = 0,            # Filter words whose count <= threshold.
    stemmer = None,         # PORTER | LEMMA | function | None
    exclude = [],           # Filter words in the exclude list.
  stopwords = False,        # Include stop words?
   language = 'en')

print funcionc
"""


texto = parse(string, tokenize=True, tags=True, chunks=True, relations=True, lemmata=True).split()
print texto

for sen in texto:
	for tok in sen:
		palabra =tok[0]
		if (palabra not in stopwords):
			print "Valido:"
			print str(palabra)
		else:
			print "stopword:"
			print str(palabra)





