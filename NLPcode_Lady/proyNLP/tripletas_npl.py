#!/usr/bin/env python
# -*- coding: utf-8 -*-
import MySQLdb
#from textblob import TextBlob
from pattern.es import parse, split
from rdflib import Graph, Literal, BNode, Namespace, RDF, URIRef
from pattern.en import pprint, parsetree, tree

#-------------------------------------------RDF: Seteando propiedades y clases-------------------------------------
vutplSchema = Namespace('http://data.utpl.edu.ec/vutpl/vocabulary/')
vutplResource = Namespace('http://data.utpl.edu.ec/vutpl/resource/')

#Definir Clases
grade = URIRef(vutplSchema+'Grade/')
subject = URIRef(vutplSchema+'Subject/')
plan = URIRef(vutplSchema+'TeachingPlan/')
section = URIRef(vutplSchema+'Section/')
sentence = URIRef(vutplSchema+'Sentence/')
token= URIRef(vutplSchema+'Token/')
rol = URIRef(vutplSchema+'Rol/')
chunk = URIRef(vutplSchema+'Chunk/')
dependency = URIRef(vutplSchema+'Dependency/')
postag = URIRef(vutplSchema+'PosTag/')
entity = URIRef(vutplSchema+'Entity/')

#Definir Propiedades
hasSubject = URIRef(vutplSchema+'hasSubject')
hasTeachingPlan = URIRef(vutplSchema+'hasTeachingPlan')
hasSection = URIRef(vutplSchema+'hasSection')
hasSentence = URIRef(vutplSchema+'hasSentence')
sentenceId = URIRef(vutplSchema+'sentenceId')
hasToken= URIRef(vutplSchema+'hasToken')
pnp = URIRef(vutplSchema+'pnp')
tokenId = URIRef(vutplSchema+'tokenId')
pos = URIRef(vutplSchema+'pos')
data = URIRef(vutplSchema+'data')
ner = URIRef(vutplSchema+'ner')
lemma = URIRef(vutplSchema+'lemma')
hasRole = URIRef(vutplSchema+'hasRole')
hasChunk = URIRef(vutplSchema+'hasChunk')

#Nombres de Recursos
grade_name = "SIC"
subject_name = "SBC"
subject_grade = subject_name + "_" + grade_name

# Create an identifier for subjects
gradeNode = BNode(grade + grade_name)
subjectNode = BNode(subject + subject_grade)
planNode = BNode(plan + subject_grade)
sectionNode = BNode(section + "Section"+"1"+"_" + subject_grade)

#DB: Conexion a la DB
db = MySQLdb.connect("localhost","root","root","planesdocentes_utpl")
#cadenabase= u'Universidad Técnica Particular de Loja y Universidad de Cuenca'
titulo = u'El personal no se ha capacitado:'
cadena =u'Sistemas Basados en el Conocimiento es una asignatura que se imparte en décimo ciclo de la titulación de Ingeniería en Sistemas Informáticos y Computación, ofertada por la Universidad Técnica Particular de Loja. La asignatura introduce al profesional en formación en una de las áreas más interesantes, emergentes y prometedoras que ha surgido en nuestra era actual de la sociedad del conocimiento, estructuras de conocimiento abierto y las tecnologías de la Web Semántica. Hoy en día, es imprescindible que nuestros ingenieros, adquieran competencias clave para capturar, modelar, gestionar y explotar datos y conocimiento que en grandes cantidades se generan y están disponibles en la Web y que con diferentes fines pueden ser aprovechados en la práctica laboral, académica y empresarial. En el presente componente académico, se cubren aspectos como: la creación de esquemas de representación de conocimiento y datos, la descripción de recursos y objetos del mundo real mediante esos modelos, la publicación de datos enlazados en la Web, así como su posterior aprovechamiento para efectos de recuperar datos y generar nuevo conocimiento. Cuando un estudiante finalice este componente habrá adquirido la capacidad para identificar áreas y usos potenciales de las tecnologías de la web semántica, modelar dominios de conocimiento e implementar modelos procesables por máquinas.'

cadena1 = cadena.encode('utf8')
titulo1 = titulo.encode('utf8')
contenido = cadena.encode('utf8')

print '-----------------LEMA-------------------'
#Preprocesamiento de texto con el metodo parse() de pattern
texto = parse(titulo, tokenize = True, tags = True, chunks = True, relations=True, lemmata=True).split()

#RDF: Definir grafo
g = Graph()

#DB
cursor = db.cursor()
idsen = 1
idtok = 1

for sen in texto:
    sentenceNode = BNode(sentence + "Sentence" + str(idsen) + "_Section" + "1" + "_" + subject_grade)
    g.add((sentenceNode, RDF.type, sentence))

    g.add((sectionNode, RDF.type, section))
    g.add((sectionNode, hasSentence, URIRef(sentenceNode)))

    for tok in sen:
        #print "token:" + tok[0] + " tag:" + tok[1] + " chunk:" + tok[2] + " pnp:" + tok[3] + " rol:" + tok[4] + " lema:" + tok[5]

        tokenNode = BNode(token + "Token" + str(idtok) + "_Sentence" + str(idsen) + "_Section" + "1" + "_" + subject_grade)

        # sentence has token
        g.add((sentenceNode, hasToken, URIRef(tokenNode)))

        # sentence has token
        objeto1 = idtok
        objeto2 = token[0]
        objeto3 = token[1]
        objeto4 = token[2]
        objeto5 = token[4]
        objeto6 = token[3]
        objeto7 = token[5]

        # token ---- has-property ----- data
        g.add((tokenNode, RDF.type, token))

        if objeto5 != "O":
            g.add((tokenNode, hasRole, URIRef(rol + objeto5)))

        if objeto6 != "O":
            g.add((tokenNode, pnp, Literal(objeto6, lang="es")))

        if objeto4 != "O":
            g.add((tokenNode, hasChunk, URIRef(chunk + objeto4)))

        g.add((tokenNode, tokenId, Literal(objeto1, lang="es")))
        g.add((tokenNode, pos, URIRef(postag + objeto3)))
        g.add((tokenNode, data, Literal(objeto2, lang="es")))
        g.add((tokenNode, lemma, Literal(objeto7, lang="es")))

        #ENTIDAD NOMBRADA
        #g.add((tokenNode, ner, URIRef(entity + "ORGANIZATION")))


        idtok += 1
        if tok[0]== ".":
            idsen += 1
            idtok = 0

# RDF: Guardando info de clases, Token no incluido
g.add((planNode, RDF.type, plan))
g.add((planNode, hasSection, URIRef(sectionNode)))

g.add((subjectNode, RDF.type, subject))
g.add((subjectNode, hasTeachingPlan, URIRef(planNode)))

g.add((gradeNode, RDF.type, grade))
g.add((gradeNode, hasSubject, URIRef(subjectNode)))


# CICLO PARA GUARDAR TRIPLETAS EN BDD
for s, p, o in g:
    print (s)
    """try:
        cursor.execute('''INSERT into tripletas (sujeto, predicado, objeto) values (%s, %s, %s)''', (s, p, o))
        print "entrando...."
        db.commit()
    except:
        db.rollback()
    """

db.close()
