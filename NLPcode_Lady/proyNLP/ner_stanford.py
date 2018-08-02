#!/usr/bin/env python
# -*- coding: utf-8 -*-

import nltk.tag.stanford as st
tagger = st.StanfordNERTagger('/home/ela/NLPcode/proyNLP/NER-Stanford/stanford-ner-2014-06-16/classifiers/english.all.3class.distsim.crf.ser.gz',
                              '/home/ela/NLPcode/proyNLP/NER-Stanford/stanford-ner-2014-06-16/stanford-ner.jar') # here PATH_TO_GZ and PATH_TO_JAR are the FULL path to where I store the file "all.3class.distsim.crf.ser.gz" and the file "stanford-ner.jar"

#cadena_english = u'University Technical Particular of Loja and University of Cuenca located in south of Ecuador country are the most famous educational institutions'
cadena_spanish = u'La Universidad Tecnica Particular de Loja y la Universidad de Cuenca localizadas al sur del Ecuador son las instituciones educativas mas famosas'
cadena1 = cadena_spanish.encode('utf8')

var = tagger.tag(cadena1.split())

for it in var:
    print it