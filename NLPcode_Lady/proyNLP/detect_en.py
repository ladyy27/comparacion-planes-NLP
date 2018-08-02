#!/usr/bin/env python
# -*- coding: utf-8 -*-
from pattern.en import parse, split

def en_parsing(sentenceText):
	parse_en = parse(sentenceText, tokenize=True, tags=True, chunks=True, relations=True, lemmata=True).split()
	return parse_en