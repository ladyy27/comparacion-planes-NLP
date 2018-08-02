#!/usr/bin/env python
# -*- coding: utf-8 -*-
from pattern.en import parse, split, pprint
from textblob import TextBlob, Word

cadena_spanish = u'La casa mas llenando' #problemas con tildes con textos en espaniol
cadena_english = u'University Technical Particular of Loja and University of Cuenca located in south of Ecuador country are the most famous educational institutions'

cadena1 = cadena_english.encode('utf8')

text = TextBlob(cadena_english)
print "Idioma Identificado: " + text.detect_language()

pprint(parse(cadena1, tokenize = True, tags = True, chunks = True, relations=True, lemmata=True))

print "Traducir a 'es':"
to_spanish = text.translate(to='es')
print to_spanish




