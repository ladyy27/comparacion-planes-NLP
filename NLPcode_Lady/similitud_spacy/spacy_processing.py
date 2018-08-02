import spacy
from spacy.vectors import Vectors
nlp = spacy.load('es_core_news_md')

def tokenizing(cadena):
    tokens_oracion = []
    doc = nlp(cadena)
    for token in doc:
        tokens_oracion.append(token.text)
    return tokens_oracion
