#!/usr/bin/env python
# -*- coding: utf-8 -*-
import MySQLdb
#from textblob import TextBlob
from pattern.es import parse, split
from rdflib import Graph, Literal, BNode, Namespace, RDF, URIRef
from pattern.en import pprint, parsetree, tree

#DB: Conexion a la DB
db = MySQLdb.connect("localhost","root","root","planesdocentes_utpl")
#cadenabase= u'Universidad Técnica Particular de Loja y Universidad de Cuenca'
titulo = u'Importancia del componente dentro del perfil de egreso de la titulación:'
cadena =u'Sistemas Basados en el Conocimiento es una asignatura que se imparte en décimo ciclo de la titulación de Ingeniería en Sistemas Informáticos y Computación, ofertada por la Universidad Técnica Particular de Loja. La asignatura introduce al profesional en formación en una de las áreas más interesantes, emergentes y prometedoras que ha surgido en nuestra era actual de la sociedad del conocimiento, estructuras de conocimiento abierto y las tecnologías de la Web Semántica. Hoy en día, es imprescindible que nuestros ingenieros, adquieran competencias clave para capturar, modelar, gestionar y explotar datos y conocimiento que en grandes cantidades se generan y están disponibles en la Web y que con diferentes fines pueden ser aprovechados en la práctica laboral, académica y empresarial. En el presente componente académico, se cubren aspectos como: la creación de esquemas de representación de conocimiento y datos, la descripción de recursos y objetos del mundo real mediante esos modelos, la publicación de datos enlazados en la Web, así como su posterior aprovechamiento para efectos de recuperar datos y generar nuevo conocimiento. Cuando un estudiante finalice este componente habrá adquirido la capacidad para identificar áreas y usos potenciales de las tecnologías de la web semántica, modelar dominios de conocimiento e implementar modelos procesables por máquinas.'

cadena1 = cadena.encode('utf8')
titulo1 = titulo.encode('utf8')
contenido = cadena.encode('utf8')

print '-----------------LEMA-------------------'
#Preprocesamiento de texto con el metodo parse() de pattern
texto = parse(cadena1, tokenize = True, tags = True, chunks = True, relations=True, lemmata=True).split()

#RDF: Definir grafo
g = Graph()

#DB
cursor = db.cursor()
idsentence = 1
idtoken = 1

for sentence in texto:
    for token in sentence:
        print "token:" + token[0] + " tag:" + token[1] + " chunk:" + token[2] + " pnp:" + token[3] + " rol:" + token[4] + " lema:" + token[5]



        """"
        sujeto = "Token" + str(idtoken) + "_Sentence" + str(idsentence) + "_Section1_SBC_SIC"
        predicado1 = "vutpl:tokenId"
        predicado2 = "vutpl:data"
        predicado3 = "vutpl:pos"
        predicado4 = "vutpl:hasChunk"
        predicado5 = "vutpl:hasRole"
        predicado6 = "vutpl:pnp"
        predicado7 = "vutpl:lemma"
        objeto1 = idtoken
        objeto2 = token[0]
        objeto3 = token[1]
        objeto4 = token[2]
        objeto5 = token[4]
        objeto6 = token[3]
        objeto7 = token[5]

        try:
            cursor.execute('''INSERT into tripletas (sujeto, predicado, objeto) values (%s, %s, %s)''', (sujeto, predicado1, objeto1))
            cursor.execute('''INSERT into tripletas (sujeto, predicado, objeto) values (%s, %s, %s)''', (sujeto, predicado2, objeto2))
            cursor.execute('''INSERT into tripletas (sujeto, predicado, objeto) values (%s, %s, %s)''', (sujeto, predicado3, objeto3))
            if objeto4 != "O":
                cursor.execute('''INSERT into tripletas (sujeto, predicado, objeto) values (%s, %s, %s)''', (sujeto, predicado4, objeto4))
            if objeto5 != "O":
                cursor.execute('''INSERT into tripletas (sujeto, predicado, objeto) values (%s, %s, %s)''', (sujeto, predicado5, objeto5))
            if objeto6 != "O":
                cursor.execute('''INSERT into tripletas (sujeto, predicado, objeto) values (%s, %s, %s)''', (sujeto, predicado6, objeto6))
            cursor.execute('''INSERT into tripletas (sujeto, predicado, objeto) values (%s, %s, %s)''', (sujeto, predicado7, objeto7))
            #print "entrando...."
            db.commit()
        except:
            db.rollback()



        idtoken += 1
        if token[0]== ".":
            idsentence += 1
            idtoken = 0
        """

db.close()

#sacar dividido por sentences
#s = parse(contenido)
#for sentence in split(s):
    #print sentence