#!/usr/bin/env python
# -*- coding: utf-8 -*-
from mysql_connection import *
from detect_en import *
from detect_es import *
from detect_fr import *
from preprocessing import *
from RDFmanagement import *
from rdflib import Graph, Literal, BNode, Namespace, RDF,RDFS, URIRef
from textblob import TextBlob

#Setear conexion a MYSQL
set_connectionMySQL()

#Capturar listas de stopwords
es_stopwords= stopwordsList("spanish")
fr_stopwords= stopwordsList("french")
en_stopwords= stopwordsList("english")
punt_stopwords= stopwordsList("puntuacion")

#Variables de idioma
lang_es = "es"
lang_en = "en"
lang_fr = "fr"

#RDF: Definir grafo
graph = create_graph()

#Capturar el resultado de una consulta SQL
resultados = ieee_select_query()

#Capturar conceptos, procesamiento NLP y generar tripletas
idsen = 1
idtok = 1

for fila in resultados:
    concept = str(fila[1])
    sentenceNode = add_node(sentence + "Sentence" + str (idsen))

    add_graph(graph, sentenceNode, RDF.type, sentencetype)
    add_graph(graph, sentenceNode, sentenceId, Literal(idsen, lang="es"))
    add_graph(graph, sentenceNode, dataSentence, Literal(concept, lang="es"))

    #Reemplazar tilde en textos en espaniol
    conceptSinTildes = replace_tildes(concept)
    text_object= TextBlob(conceptSinTildes)
    text_str = str(text_object)

    if len(text_str) > 2:
        if text_object.detect_language() == lang_es:
            parse_text = es_parsing(text_str)
            generateTriplesByDetectedLang(parse_text,graph,sentenceNode,lang_es, idtok, idsen, es_stopwords, punt_stopwords)
        elif text_object.detect_language() == lang_en:
            parse_text = en_parsing(text_str)
            generateTriplesByDetectedLang(parse_text,graph,sentenceNode,lang_en, idtok, idsen, en_stopwords, punt_stopwords)
        elif text_object.detect_language() == lang_fr:
            parse_text = fr_parsing(text_str)
            generateTriplesByDetectedLang(parse_text,graph,sentenceNode,lang_fr, idtok, idsen, fr_stopwords, punt_stopwords)

#Guardar todas las tripletas en un archivo SQL
storeTriplesDBfile(graph)

#Cerrar conexion de MYSQL
close_connectionMySQL()
