#!/usr/bin/env python
# -*- coding: utf-8 -*-
from pattern.fr import parse, split

def fr_parsing(sentenceText):
	parse_fr = parse(sentenceText, tokenize=True, tags=True, chunks=True, relations=True, lemmata=True).split()
	return parse_fr