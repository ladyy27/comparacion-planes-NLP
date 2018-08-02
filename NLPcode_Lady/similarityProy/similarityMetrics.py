#!/usr/bin/env python
# -*- coding: utf-8 -*-
from pattern.metrics import similarity, levenshtein
from pattern.en import wordnet


s = "Preparación para el cálculo. Límites y sus propiedades. Derivación. Aplicaciones de la derivada. Integración. Funciones logarítmica, exponencial y otras funciones trascendentes. Ecuaciones diferenciales. Aplicaciones de la integral. Técnicas de integración, regla de L'Hôpital e integrales impropias. Series infinitas. Cónicas, ecuaciones y paramétricas y coordenadas polares. Vectores y la geometría del espacio. Funciones de varias variables. Integración múltiple."
s1 = "Límites y sus propiedades. Derivación. Aplicaciones de la derivada. Integración. Técnicas de integración"



print similarity(s1, s)
print levenshtein(s, s1)

"""a = wordnet.synsets('software')[0]
b = wordnet.synsets('database')[0]
c = wordnet.synsets('programming')[0]

print wordnet.ancestor(a, b)  
print wordnet.similarity(a, a) 
print wordnet.similarity(a, b)
print wordnet.similarity(a, c)
print wordnet.similarity(b, c)  

"""

