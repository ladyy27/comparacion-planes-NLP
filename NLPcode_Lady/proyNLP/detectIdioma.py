#!/usr/bin/env python
# -*- coding: utf-8 -*-
########Import textblob
from textblob import TextBlob
from detect_es import *
from detect_en import *
import codecs
#######

""""
stopwordslist = []
with codecs.open('spanish', encoding='utf-8') as f:
    for line in f.readlines():
    	stop = line
    	stop2 = stop.replace("\n","")
        stopwordslist.append(stop2)
	    #stop1 = stop.encode('utf8')

for i in stopwordslist:
	print i

"""
def stopwordsList(filename):
	#Cargar lista de stopwords
	stopwordslist = []
	stopfile= codecs.open(filename,"r",encoding="UTF-8")
	for line in stopfile:
	    stop = line
	    stop2 = stop.replace("\n","")
	    stopwordslist.append(stop2)
	return stopwordslist

#reemplazar tildes en frases en español
#cad_es= "Explicá las diferentés fases de procesamiento de un tejido para su observación al microscopio óptico y menciona las técnicas histológicas para identificación de tejidos epiteliales."
cad_es = "anaconda, serpiente"


cad_es = cad_es.replace("á", "a").replace("é", "e").replace("í", "i").replace("ó", "o").replace("ú", "u").replace("Á", "A").replace("É", "E").replace("Í", "I").replace("Ó", "O").replace("Ú", "U")
"""if "á" in cad_es:
	cad_es= cad_es.replace("á", "a")
elif "é" in cad_es:
	cad_es= cad_es.replace("é", "e")
	print "entrando"
elif "í" in cad_es:
	cad_es= cad_es.replace("í", "i")
elif "ó" in cad_es:
	cad_es= cad_es.replace("ó", "o")
elif "ú" in cad_es:
	cad_es= cad_es.replace("ú", "u")
elif "Á" in cad_es:
	cad_es= cad_es.replace("Á", "A")
elif "É" in cad_es:
	cad_es= cad_es.replace("É", "e")
elif "Í" in cad_es:
	cad_es= cad_es.replace("Í", "I")
elif "Ó" in cad_es:
	cad_es= cad_es.replace("Ó", "O")
elif "Ú" in cad_es:
	cad_es= cad_es.replace("Ú", "U")
"""
print "-------- CADENA EN ESPANIOL SIN TILDES-----------------------"
print cad_es
print "-------- CADENA EN ESPANIOL SIN TILDES-----------------------"


lista = []
#textblob para traduccion de idioma
cad_es_r= TextBlob(cad_es)
lista.append(cad_es_r)
cad_fr= TextBlob("Je m'appelle Mercy. Au revoir")
lista.append(cad_fr)
cad_en= TextBlob("Aerospace and electronic systems")
lista.append(cad_en)


es_stopwords= stopwordsList("spanish")
fr_stopwords= stopwordsList("french")
en_stopwords= stopwordsList("english")
punt_stopwords= stopwordsList("puntuacion")
 

for i in lista:
	if i.detect_language() == 'es':
		print "ES:"
		a = str(i)
		print a
		texto = es_parsing(a)
		for sen in texto:
			for tok in sen: 
				#lema = tok[5]
				if tok[5] not in es_stopwords:
					if tok[5] not in punt_stopwords:
						print tok[0] + " --- "+ tok[5]
		print texto
		print "----"
	
	elif i.detect_language() == 'en':
		print "EN:"
		a = str(i)
		print a
		texto = en_parsing(a)
		print texto
		for sen in texto:
			for tok in sen: 
				#lema = tok[5]
				if tok[5] not in en_stopwords: 
					if tok[5] not in punt_stopwords:
						print tok[0] + " --- "+ tok[5] 
		print "----"
	elif i.detect_language() == 'fr':
		print "FR:"
		a = str(i)
		print a
		texto = parse(a, tokenize=True, tags=True, chunks=True, relations=True, lemmata=True).split()
		print texto
		for sen in texto:
			for tok in sen: 
				#lema = tok[5]
				if tok[5] not in fr_stopwords: 
					if tok[5] not in punt_stopwords:
						print tok[0] + " --- "+ tok[5] 
		print "----"
