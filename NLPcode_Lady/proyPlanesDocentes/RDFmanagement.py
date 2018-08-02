#!/usr/bin/env python
# -*- coding: utf-8 -*-
from rdflib import Graph, Literal, BNode, Namespace, RDF,RDFS, URIRef

def create_graph():
	grafo = Graph()
	return grafo

def add_node(cadena):
	nameNode = BNode(cadena)
	return nameNode

def add_graph(grafo,s,p,o):
	grafo.add((s, p, o))
	return grafo


# RDF: Seteando propiedades y clases
vutplSchema = Namespace('http://data.utpl.edu.ec/vutpl/vocabulary/')
vutplResource = Namespace('http://data.utpl.edu.ec/vutpl/resource/')

sentencetype = URIRef(vutplSchema+'Sentence')
tokentype = URIRef(vutplSchema+'Token')
roltype = URIRef(vutplSchema+'Rol')
chunktype = URIRef(vutplSchema+'Chunk')
dependencytype = URIRef(vutplSchema+'Dependency')
postagtype = URIRef(vutplSchema+'PosTag')
entitytype = URIRef(vutplSchema+'Entity')


#Definir Clases
grade = URIRef(vutplSchema+'Grade/')
subject = URIRef(vutplSchema+'Subject/')
plan = URIRef(vutplSchema+'TeachingPlan/')
section = URIRef(vutplSchema+'Section/')
sentence = URIRef(vutplResource+'Sentence/')
token= URIRef(vutplResource+'Token/')
rol = URIRef(vutplResource+'Rol/')
chunk = URIRef(vutplResource+'Chunk/')
dependency = URIRef(vutplResource+'Dependency/')
postag = URIRef(vutplResource+'PosTag/')
entity = URIRef(vutplResource+'Entity/')

#Definir Propiedades
hasSubject = URIRef(vutplSchema+'hasSubject')
hasTeachingPlan = URIRef(vutplSchema+'hasTeachingPlan')
hasSection = URIRef(vutplSchema+'hasSection')
hasSentence = URIRef(vutplSchema+'hasSentence')
sentenceId = URIRef(vutplSchema+'sentenceId')
dataSentence = URIRef(vutplSchema+'dataSentence')
hasToken= URIRef(vutplSchema+'hasToken')
pnp = URIRef(vutplSchema+'pnp')
tokenId = URIRef(vutplSchema+'tokenId')
pos = URIRef(vutplSchema+'pos')
data = URIRef(vutplSchema+'data')
ner = URIRef(vutplSchema+'ner')
lemma = URIRef(vutplSchema+'lemma')
hasRole = URIRef(vutplSchema+'hasRole')
hasChunk = URIRef(vutplSchema+'hasChunk')
