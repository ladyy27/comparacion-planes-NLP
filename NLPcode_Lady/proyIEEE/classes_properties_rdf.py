from rdflib import Graph, Literal, BNode, Namespace, RDF,RDFS, URIRef
# Libreria usada: rdflib

# RDF: Seteando propiedades y clases
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