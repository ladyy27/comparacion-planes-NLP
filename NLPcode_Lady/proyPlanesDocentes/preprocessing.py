#!/usr/bin/env python
# -*- coding: utf-8 -*-
import codecs
from RDFmanagement import *
from rdflib import Graph, Literal, BNode, Namespace, RDF,RDFS, URIRef

def stopwordsList(filename):
	#Cargar lista de stopwords
	stopwordslist = []
	stopfile= codecs.open(filename,"r",encoding="UTF-8")
	for line in stopfile:
	    stop = line
	    stop2 = stop.replace("\n","")
	    stopwordslist.append(stop2)
	return stopwordslist

#en palabras con tilde:
def replace_tildes(es_string):
	es_string1 = es_string.replace("á", "a").replace("é", "e").replace("í", "i").replace("ó", "o").replace("ú", "u").replace("Á", "A").replace("É", "E").replace("Í", "I").replace("Ó", "O").replace("Ú", "U")
	return es_string1

#Generar tripletas para
def generateTriplesByDetectedLang(parse_text,graph,sentenceNode,lang_str,id_token, id_sentence, stoplist_lang, stoplist_punt):
	print lang_str
	for sen in parse_text:
		for tok in sen: 
			if tok[5] not in stoplist_lang:
				if tok[5] not in stoplist_punt:
					print tok[0] + " --- "+ tok[5]
					tokenNode = add_node(token + "Token" + str(id_token) + "_Sentence" + str(id_sentence))
					add_graph(graph, tokenNode, RDF.type, tokentype)
					add_graph(graph, sentenceNode, hasToken, URIRef(tokenNode))
					if tok[4] != "O":
						add_graph(graph, tokenNode, hasRole, URIRef(rol + tok[4]))
					if tok[3] != "O":
						add_graph(graph, tokenNode, pnp, Literal(tok[3], lang="en"))
					if tok[2] != "O":
						add_graph(graph, tokenNode, hasChunk, URIRef(chunk + tok[2]))
					add_graph(graph, tokenNode, tokenId, Literal(id_token, lang="en"))
					posTagNode = add_node(postag + tok[1])
					add_graph(graph, posTagNode, RDF.type, postagtype)
					add_graph(graph, posTagNode, RDFS.label, Literal(tok[1], lang="en"))
					add_graph(graph, tokenNode, pos, posTagNode)
					add_graph(graph, tokenNode, RDFS.label, Literal(tok[1], lang="en"))
					add_graph(graph, tokenNode, data, Literal(tok[0], lang="en"))
					add_graph(graph, tokenNode, lemma, Literal(tok[5], lang="en"))
					id_token += 1

		id_token = 1
		id_sentence += 1
	
	return
    
#Guardar tripletas en archivo sql
def storeTriplesDBfile(grafo):
	lengg = len(grafo)
	iterador = 1
	f =codecs.open("/home/ela/tripletasSinStopwords.sql","wb",encoding="UTF-8")

	for tripleta in grafo:
	    print "Guardando " + str(iterador) + " de " + str(lengg)
	    #print tripleta
	    sujeto1 = tripleta[0]
	    predicado1 = tripleta[1]
	    objeto1 = tripleta[2]
	    f.write('INSERT into tripletas values  (%s, %s, %s)' % ('"' + sujeto1 + '"', '"' + predicado1 + '"', '"' 
	    	+ objeto1 + '"') + ';\n')
	    iterador +=1
	return