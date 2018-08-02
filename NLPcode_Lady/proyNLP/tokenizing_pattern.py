#!/usr/bin/env python
# -*- coding: utf-8 -*-
import MySQLdb
from pattern.es import parse, split, parsetree, tag, lemma
from pattern.en import pprint

db = MySQLdb.connect("localhost","root","root","planesdocentes_utpl")

titulo = u'Importancia del componente dentro del perfil de egreso de la titulación:'
cadena =u'Sistemas Basados en el Conocimiento es una asignatura que se imparte en décimo ciclo de la titulación de Ingeniería en Sistemas Informáticos y Computación, ofertada por la Universidad Técnica Particular de Loja. La asignatura introduce al profesional en formación en una de las áreas más interesantes, emergentes y prometedoras que ha surgido en nuestra era actual de la sociedad del conocimiento, estructuras de conocimiento abierto y las tecnologías de la Web Semántica. Hoy en día, es imprescindible que nuestros ingenieros, adquieran competencias clave para capturar, modelar, gestionar y explotar datos y conocimiento que en grandes cantidades se generan y están disponibles en la Web y que con diferentes fines pueden ser aprovechados en la práctica laboral, académica y empresarial. En el presente componente académico, se cubren aspectos como: la creación de esquemas de representación de conocimiento y datos, la descripción de recursos y objetos del mundo real mediante esos modelos, la publicación de datos enlazados en la Web, así como su posterior aprovechamiento para efectos de recuperar datos y generar nuevo conocimiento. Cuando un estudiante finalice este componente habrá adquirido la capacidad para identificar áreas y usos potenciales de las tecnologías de la web semántica, modelar dominios de conocimiento e implementar modelos procesables por máquinas.'

titulo1 = titulo.encode('utf8')
contenido = cadena.encode('utf8')

"""
print '-----------------TOKENIZING-------------------'
pprint(parse(contenido, tokenize = True, tags = True, chunks = True, relations=True, lemmata=True))


print '-----------------SENTENCES-------------------'
s = parse(contenido)
for sentence in split(s):
    print sentence
    
"""
#http://data.utpl.edu.ec/vutpl/resource/Token/Token1_Sentence1_Section1_Calculo_Contabilidad


print '-----------------SOLO TOKENS-------------------'
tag(contenido, tokenize=True, encoding='utf-8')

cursor = db.cursor()
idsentence = 1
idtoken = 1
for word, pos in tag(contenido):
    sujeto = "Token" + str(idtoken) + "_Sentence"+str(idsentence) +"_Section1_SBC_SIC"
    predicado = "vutpl:data"
    predicado2 = "vutpl:pos"
    predicado3 = "vutpl:tokenId"
    objeto = word
    objeto2 = pos
    objeto3 = idtoken


    try:
        cursor.execute('''INSERT into tripletas (sujeto, predicado, objeto) values (%s, %s, %s)''', (sujeto, predicado, objeto))
        cursor.execute('''INSERT into tripletas (sujeto, predicado, objeto) values (%s, %s, %s)''', (sujeto, predicado2, objeto2))
        cursor.execute('''INSERT into tripletas (sujeto, predicado, objeto) values (%s, %s, %s)''', (sujeto, predicado3, objeto3))
        print "entrando...."
        db.commit()
    except:
        db.rollback()
        


    idtoken += 1
    if pos == ".":
        idsentence+=1
        idtoken = 0

db.close()










