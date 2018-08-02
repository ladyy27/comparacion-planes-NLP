#!/usr/bin/env python
# -*- coding: utf-8 -*-

import MySQLdb
from pattern.en import parse, split
from pattern.db import Datasheet
from rdflib import Graph, Literal, BNode, Namespace, RDF,RDFS, URIRef
import codecs

grafoSentence = Graph()

u = "Green's law"
print type(u)
#
sentenceNode = BNode("sujeto")
dataSentence = URIRef('predicado')
grafoSentence.add((sentenceNode, dataSentence, Literal(u, lang="en")))
    #grafoSentence.add((sentenceNode, dataSentence, Literal(concept, datatype="http://www.w3.org/2001/XMLSchema#string")))
    
for i in grafoSentence:
	print i
    
    #texto = parse(concept, tokenize=True, tags=True, chunks=True, relations=True, lemmata=True).split()

"""

uuu = str(u)
print uuu
uu = u.decode('utf8')
print uu
s = uu.encode('utf8')
print s
"""

