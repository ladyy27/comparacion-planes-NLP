#!/usr/bin/env python
# -*- coding: utf-8 -*-
from pattern.vector import Document, Model, TFIDF

#s = "Preparación para el cálculo. Límites y sus propiedades. Derivación. Aplicaciones de la derivada. Integración. Funciones logarítmica, exponencial y otras funciones trascendentes. Ecuaciones diferenciales. Aplicaciones de la integral. Técnicas de integración, regla de L'Hôpital e integrales impropias. Series infinitas. Cónicas, ecuaciones y paramétricas y coordenadas polares. Vectores y la geometría del espacio. Funciones de varias variables. Integración múltiple."
#s1 = "Límites y sus propiedades. Derivación. Aplicaciones de la derivada. Integración. Técnicas de integración"


s = "Data Science, there is a new opportunity"
s1 = "Data Science is a new yield to discover"
"""
d1 = Document(s, 
       filter = lambda w: w.lstrip("'").isalnum(), 
  punctuation = '.,;:!?()[]{}\'`"@#$*+-|=~_', 
          top = None,       # Filter words not in the top most frequent.
    threshold = 0,          # Filter words whose count falls below threshold.
      exclude = [],         # Filter words in the exclude list. 
      stemmer = None,       # STEMMER | LEMMA | function | None.
    stopwords = False,      # Include stop words?
         name = 'ii',
         type = 'plan_docente_IQ',
     language = 'en', 
  description = 'seccion de planes docentes')

d2 = Document(s1, 
       filter = lambda w: w.lstrip("'").isalnum(), 
  punctuation = '.,;:!?()[]{}\'`"@#$*+-|=~_', 
          top = None,       # Filter words not in the top most frequent.
    threshold = 0,          # Filter words whose count falls below threshold.
      exclude = [],         # Filter words in the exclude list. 
      stemmer = None,       # STEMMER | LEMMA | function | None.
    stopwords = False,      # Include stop words?
         name = 'ii',
         type = 'plan_docente_II',
     language = 'en', 
  description = 'seccion de planes docentes II')


m = Model(documents=[d1, d2], weight=TFIDF)
print m.similarity(d1, d2) # tiger vs. lion

"""

d1 = Document('No limits, only weapons', type='tiger')
d2 = Document('War and weapons', type='lion',)
#d3 = Document('An elephant is a big grey animal with a slurf.', type='elephant')
 
m = Model(documents=[d1, d2], weight=TFIDF)
 
print d1.vector
print
print d2.vector
print m.similarity(d1, d2) # tiger vs. lion
#print m.similarity(d1, d3) # tiger vs. elephant



