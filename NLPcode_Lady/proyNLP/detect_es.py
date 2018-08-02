#!/usr/bin/env python
# -*- coding: utf-8 -*-
from pattern.es import parse, split

def es_parsing(sentenceText):
	parse_es = parse(sentenceText, tokenize=True, tags=True, chunks=True, relations=True, lemmata=True).split()
	return parse_es
