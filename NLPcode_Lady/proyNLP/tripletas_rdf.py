from rdflib import Graph, Literal, BNode, Namespace, RDF, URIRef
#from rdflib.namespace import DC, FOAF

# 3. 'Token#14' hasROle Chunk
# 2.'SIC' es de tipo Grade
# 1. declarar una clase  -- no
# llamar n.propiedad

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

#RDF: Definir grafo
g = Graph()






# Add triples using store's add method.
g.add( (gradeNode, RDF.type, grade))
g.add( (gradeNode, hasSubject, URIRef (subjectNode)))

g.add( (subjectNode, RDF.type, subject ))
g.add( (subjectNode, hasTeachingPlan, URIRef(planNode) ))

g.add( (planNode, RDF.type, plan))
g.add( (planNode, hasSection, URIRef(sectionNode) ))

g.add( (sectionNode, RDF.type, section))
g.add( (sectionNode, hasSentence, URIRef(sentenceNode) ))

g.add( (sentenceNode, RDF.type, sentence))
g.add( (sentenceNode, hasToken, URIRef(tokenNode) ))

g.add( (tokenNode, hasRole, URIRef(rol + "NP-I" )) )
g.add( (tokenNode, pnp, Literal("PNP", lang="es")))
g.add( (tokenNode, RDF.type, token))
g.add( (tokenNode, pnp, Literal("PNP", lang="es")))
g.add( (tokenNode, hasRole, URIRef(rol + "NP-I" )) )
g.add( (tokenNode, hasChunk, URIRef(chunk + "AA" )) )
g.add( (tokenNode, tokenId, Literal("1", lang="es")))
g.add( (tokenNode, pos, URIRef(postag + "II" )) )
g.add( (tokenNode, data, Literal("personal", lang="es")))
g.add( (tokenNode, ner, URIRef(entity + "ORGANIZATION" )) )
g.add( (tokenNode, lemma, Literal("persona", lang="es")))


# Iterate over triples in store and print them out.
print("--- printing raw triples ---")
for s, p, o in g:
    print (s,p,o)
    #print((s, p, o))

# For each foaf:Person in the store print out its mbox property.
"""
print("--- printing mboxes ---")
for person in g.subjects(RDF.type, FOAF.Person):
    for mbox in g.objects(person, FOAF.mbox):
        print(mbox)

# Bind a few prefix, namespace pairs for more readable output
g.bind("dc", DC)
g.bind("foaf", FOAF)

print(g.serialize(format='n3'))
"""