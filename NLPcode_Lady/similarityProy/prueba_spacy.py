import spacy
from spacy.vectors import Vectors

nlp = spacy.load('es_core_news_md')

text_calculo1_IQ = "MORFOLOGÍA FUNCIONAL II"
text_calculo1_II = "MORFOLOGÍA FUNCIONAL IV"
#text_calculo1_IQ = "La manzana de Raul es verde"
#text_calculo1_II = "La manzana verde es de Raul"
doc1 = nlp(text_calculo1_IQ)
doc2 = nlp(text_calculo1_II)

print (doc1.similarity(doc2))
