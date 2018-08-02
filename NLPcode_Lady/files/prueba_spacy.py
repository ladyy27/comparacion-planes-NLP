import spacy
nlp = spacy.load('es')

text_calculo1_IQ = "Límites y sus propiedades. Derivación.Aplicaciones de la derivada. Integración. Técnicas de integración"
text_calculo1_II = "Preparación para el cálculo. Límites y sus propiedades. Derivación. Aplicaciones de la derivada. Integración. Funciones logarítmica, exponencial y otras funciones trascendentes. Ecuaciones diferenciales. Aplicaciones de la integral. Técnicas de integración, regla de L'Hôpital e integrales impropias. Series infinitas. Cónicas, ecuaciones y paramétricas y coordenadas polares. Vectores y la geometría del espacio. Funciones de varias variables. Integración múltiple."

#text_calculo1_IQ = "La manzana de Raul es verde"
#text_calculo1_II = "La manzana verde es de Raul"
doc1 = nlp(text_calculo1_IQ)
doc2 = nlp(text_calculo1_II)

print (doc1.similarity(doc2))