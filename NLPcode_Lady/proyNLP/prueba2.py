import nltk
from nltk.tokenize import word_tokenize

training_set = [[(w.lower(),t) for w,t in s] for s in nltk.corpus.conll2002.tagged_sents('esp.train')]

unigram_tagger = nltk.UnigramTagger(training_set)
bigram_tagger = nltk.BigramTagger(training_set, backoff=unigram_tagger)

cadena ="Importancia del componente dentro del perfil de egreso de la titulación:"
tokens = [token.lower() for token in word_tokenize(cadena)]

print (tokens)
