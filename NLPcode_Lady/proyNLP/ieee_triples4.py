#!/usr/bin/env python
# -*- coding: utf-8 -*-
########Import textblob
from textblob import TextBlob
########
import MySQLdb
from pattern.en import parse, split,pprint
from pattern.db import Datasheet
from rdflib import Graph, Literal, BNode, Namespace, RDF,RDFS, URIRef
import codecs



#Cargar lista de stopwords
stopwords = []
stopfile= codecs.open("stopwords_english","r",encoding="UTF-8")
for line in stopfile:
    stop = str(line)
    stop1 = stop.encode('utf8')
    stop2 = stop1.replace("\n","")
    stopwords.append(stop2)
#stopWords = set(stopwords.words('english'))

#lista de stopwords
f =codecs.open("/home/ela/deletedStopwordsLady.txt","wb",encoding="UTF-8")
stopWords_capturados = []


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

#RDF: Definir grafo
grafoSentence = Graph()

# DB: Conexion a la DB
db = MySQLdb.connect("localhost", "root", "root", "triplesNLP")

# DB: Declarar cursor
cursor = db.cursor()

# DB: Ejecutar consulta
try:
    #cursor.execute("TRUNCATE TABLE tripletas")
    #cursor.execute("SELECT * FROM 2017_IEEE_Taxonomy_v1 WHERE Id = 419")
    cursor.execute("SELECT Id, CAST(CONVERT(Concept USING utf8) AS binary) FROM 2017_IEEE_Taxonomy_v1")
    resultados = cursor.fetchall()
    
except:
    db.rollback()
idsen = 1
idtok = 1
for fila in resultados:
    #concept = str(fila[1])
    concept = "Ecuador"

    #idsen = str(fila[0])
    #print str(fila[1])
    
    #print concept
    #print "Tipo de variable"
    #print type(concept)
    #concept = u'

    sentenceNode = BNode(sentence + "Sentence" + str(idsen))

    grafoSentence.add((sentenceNode, RDF.type, sentencetype))


    grafoSentence.add((sentenceNode, sentenceId, Literal(idsen, lang="en")))

    grafoSentence.add((sentenceNode, dataSentence, Literal(concept, lang="en")))
    #grafoSentence.add((sentenceNode, dataSentence, Literal(concept, datatype="http://www.w3.org/2001/XMLSchema#string")))
    

    
    texto = parse(concept, tokenize=True, tags=True, chunks=True, relations=True, lemmata=True).split()
    #print(parse(concept, tokenize = True, tags = True, chunks = True, relations=True, lemmata=True))
    pprint(parse(concept, tokenize=True, tags=True, chunks=True, relations=True, lemmata=True))
"""
    for sen in texto:
        for tok in sen:

            objeto1 = idtok
            objeto2 = tok[0]
            objeto3 = tok[1]
            objeto4 = tok[2]
            objeto5 = tok[4]
            objeto6 = tok[3]
            objeto7 = tok[5]

            if objeto7 not in stopwords:
                print "Token Valido " + str(objeto1) + " : "  + str(objeto2) + ", lemma: "+  str(objeto7)+ ", pos: "+  str(objeto3) + ", rol: "+  str(objeto5) + ", pnp: "+  str(objeto6)+ ", chunk: "+  str(objeto4)
                
                #print "Token Valido " + str(objeto1) + " : "  + str(objeto2) + ", lemma: "+  str(objeto7)
                #print  "Sentence # " +  str(idsen)+ ", Token#" + str(objeto1) + " : " +str(objeto2) 
        
                      
                tokenNode = BNode(token + "Token" + str(objeto1) + "_Sentence" + str(idsen))
                grafoSentence.add((tokenNode, RDF.type, tokentype))
                # sentence has token
                grafoSentence.add((sentenceNode, hasToken, URIRef(tokenNode)))
                
                # token ---- has-property ----- data
                if objeto5 != "O":
                    grafoSentence.add((tokenNode, hasRole, URIRef(rol + objeto5)))

                if objeto6 != "O":
                    grafoSentence.add((tokenNode, pnp, Literal(objeto6, lang="en")))

                if objeto4 != "O":
                    grafoSentence.add((tokenNode, hasChunk, URIRef(chunk + objeto4)))

                grafoSentence.add((tokenNode, tokenId, Literal(objeto1, lang="en")))
                posTagNode = BNode(postag + objeto3)
                grafoSentence.add((posTagNode, RDF.type, postagtype))
                grafoSentence.add((posTagNode, RDFS.label, Literal(objeto3, lang="en")))
                #grafoSentence.add((tokenNode, pos, URIRef(postag + objeto3)))
                grafoSentence.add((tokenNode, pos, posTagNode))

                grafoSentence.add((tokenNode, RDFS.label, Literal(objeto3, lang="en")))
                grafoSentence.add((tokenNode, data, Literal(objeto2, lang="en")))
                grafoSentence.add((tokenNode, lemma, Literal(objeto7, lang="en")))
                
                # ENTIDAD NOMBRADA
                # g.add((tokenNode, ner, URIRef(entity + "ORGANIZATION")))
                
                idtok += 1
                
            else:
                cad = str(tok[0]) + ' - ' + concept 
                #stopWords_capturados.append(cad)
                f.write(cad + "\n")

        idtok = 1
        idsen += 1

#Guardar tripletas en archivo sql

lengg = len(grafoSentence)
iterador = 1
f =codecs.open("/home/ela/tripletasSinStopwords.sql","wb",encoding="UTF-8")
#f.write("TRUNCATE table tripletas ;\n")  
for tripleta in grafoSentence:
    print "Guardando " + str(iterador) + " de " + str(lengg)
    #print tripleta
    sujeto1 = tripleta[0]
    predicado1 = tripleta[1]
    objeto1 = tripleta[2]
    f.write('INSERT into tripletasSinSW values  (%s, %s, %s)' % ('"' + sujeto1 + '"', '"' + predicado1 + '"', '"' + objeto1 + '"') + ';\n')
    try:
        #cursor.execute('''INSERT into tripletas values (%s, %s, %s)''', (sujeto1, predicado1, objeto1))
        #db.commit()
        pass
    except:
        print "no presentar"
        #db.rollback()

    iterador+= 1
"""

db.close()
