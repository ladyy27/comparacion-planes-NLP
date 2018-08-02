#!/usr/bin/env python
# -*- coding: utf-8 -*-
from nltk.corpus import stopwords
from nltk.tokenize import word_tokenize

"""
example_sent = "Technical Private University of Loja and University of Cuenca located in south of Ecuador country are the most famous educational institutions."
#example_sent1 = "La Universidad Técnica Particular de Loja y la Universidad de Cuenca localizadas al sur del Ecuador son las instituciones educativas más famosas"
cadena_spanish = u'La Universidad Tecnica Particular de Loja y la Universidad de Cuenca localizadas al sur del Ecuador son las instituciones educativas mas famosas'
cadena1 = cadena_spanish.encode('utf8')

stop_words = set(stopwords.words('english'))

word_tokens = word_tokenize(cadena1)

filtered_sentence = [w for w in word_tokens if not w in stop_words]

filtered_sentence = []

for w in word_tokens:
    if w not in stop_words:
        filtered_sentence.append(w)

print(word_tokens)
print(filtered_sentence)

"""

from nltk.tokenize import sent_tokenize, word_tokenize
from nltk.corpus import stopwords
 
data = "All work and no play makes jack dull boy. All work and no play makes jack a dull boy."
stopWords = set(stopwords.words('english'))
print type(stopWords)

words = word_tokenize(data)
print words
wordsFiltered = []
 
for w in words:
    if w not in stopWords:
    	wordsFiltered.append(w)
    print (w)
 
print(wordsFiltered)