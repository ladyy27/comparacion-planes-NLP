from __future__ import print_function
from textblob import TextBlob
import nltk
from nltk.corpus import stopwords
from nltk import word_tokenize
from nltk.data import load
from nltk.stem import SnowballStemmer
from string import punctuation
import spaghetti as sgt

#stopword list to use
spanish_stopwords = stopwords.words('spanish')

#spanish stemmer
#stemmer = SnowballStemmer('spanish')

#punctuation to remove
non_words = list(punctuation)
#we add spanish punctuation
non_words.extend(['¿', '¡'])
non_words.extend(map(str,range(10)))

stemmer = SnowballStemmer('spanish')
def stem_tokens(tokens, stemmer):
    stemmed = []
    for item in tokens:
        stemmed.append(stemmer.stem(item))
    return stemmed

def tokenize(text):
    # remove punctuation
    text = ''.join([c for c in text if c not in non_words])
    # tokenize
    tokens =  word_tokenize(text)

    # stem
    try:
        stems = stem_tokens(tokens, stemmer)
    except Exception as e:
        print(e)
        print(text)
        stems = ['']
    return stems



cadena = 'Importancia del componente dentro del perfil de egreso de la titulación:'
cadena1 = 'Importancia del componente dentro del perfil de egreso de la titulación:'.split()
entrada = TextBlob(cadena)
#cadena_encode= cadena.encode('utf-8')
print (entrada)

print ("----------------------------------")
print ('POS: ')
print (entrada.tags)

print ("----------------------------------")
print ('Parrafos: ')
print (entrada.sentences)
print ("----------------------------------")
print ('El idioma identificado del texto es: ')
print (entrada.detect_language())

tokens = tokenize(cadena)
print ("TOKENS: ")
print (entrada.words)
lemas = stem_tokens(tokens, stemmer)
print ("LEMAS: ")
print (lemas)

print ("TAGS: ")
print (sgt.pos_tag(cadena1), end="\n\n")
