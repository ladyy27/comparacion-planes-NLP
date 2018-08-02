from mysql_connection import *
import spacy
from spacy.vectors import Vectors
nlp = spacy.load('es_core_news_md')

def tokenizing(cadena):
    tokens_oracion = []
    doc = nlp(cadena)
    for token in doc:
        tokens_oracion.append(token.text)
    return tokens_oracion

def set_dict_distancias(sql_distancia):
    distancias_query = select_query(sql_distancia)
    dict_distancias = {}
    for dist, value in distancias_query:
        dict_distancias[dist] = value
    return dict_distancias

#Recuperar lista de niveles
def set_list_levels (sql_nivel):
    list_niveles = []
    nivel_query = select_query(sql_nivel)
    for nivel in nivel_query:
        list_niveles.append(list(nivel))
    return list_niveles

def identify_title_level(asignatura, list_niveles):
    has_nivel = False
    dict_titulo_nivel = {}
    lista_tokens= tokenizing(asignatura)
    for token in lista_tokens:
        for iter in list_niveles:
            if token == iter[1]:
                has_nivel = True
                nivel_text = token
                nivel = iter[0]

    if has_nivel == True:
        lista_tokens.remove(nivel_text)
        dict_titulo_nivel['titulo'] = lista_tokens
        dict_titulo_nivel['nivel']= nivel
        dict_titulo_nivel['nivel_text'] = nivel_text
    else:
        dict_titulo_nivel['titulo'] = lista_tokens
        dict_titulo_nivel['nivel'] = 0
    return dict_titulo_nivel

def diferencia_nivel(n1, n2):
    if (n1 == 0 and n2 ==1) or (n2 == 0 and n1 == 1):
        diferencia = 0.50
    elif n1 > n2:
        diferencia = n1 - n2
    elif n2 > n1:
        diferencia = n2 - n1
    else:
        diferencia = 0
    return diferencia

def is_same_title(titulo1, titulo2):
    if titulo1 == titulo2:
        return True
    else:
        return False

def concat_title(lista_tokens):
    solo_titulo = ""
    for token in lista_tokens:
        solo_titulo += token + " "
    return solo_titulo

def score_comparison_titles(titulo1, titulo2):
    doc1 = nlp(titulo1)
    doc2 = nlp(titulo2)
    score = doc1.similarity(doc2)
    score_title = regla_tres(score)
    return score_title

def regla_tres(value):
    total = value * 0.50
    return total

def score_title_nivel(score_nivel, score_title):
    total_score = score_nivel + score_title
    return total_score

def score_level(distancia,dict_distancias):
    for d in dict_distancias:
        if distancia == d:
            score_nivel = regla_tres(float(dict_distancias[d]))
    return score_nivel

def similarity_titles(titulo1, titulo2, lista_niveles, dict_distancias):
    # 1-Identificar y separar titulo de nivel
    # #print(identify_title_level(dict_materias[it].lower(), lista_niveles))
    # #print(identify_title_level(dict_materias[ir].lower(), lista_niveles))

    # 2-Concatenar solo titulo
    dict_titulo_nivel1 = identify_title_level(titulo1.lower(), lista_niveles)
    dict_titulo_nivel2 = identify_title_level(titulo2.lower(), lista_niveles)

    titulo_dict1 = dict_titulo_nivel1['titulo']
    titulo_dict2 = dict_titulo_nivel2['titulo']
    nivel_dict1 = dict_titulo_nivel1['nivel']
    nivel_dict2 = dict_titulo_nivel2['nivel']

    solo_titulo1 = concat_title(titulo_dict1)
    solo_titulo2 = concat_title(titulo_dict2)

    # 3- es el mismo titulo?
    if is_same_title(solo_titulo1, solo_titulo2) == True:
        #print("CALCULO DE SCORE DE SIMILITUD DE TITULO Y NIVEL")
        # 4a- Calcular distancia entre niveles
        dist = diferencia_nivel(nivel_dict1, nivel_dict2)

        score_nivel = score_level(dist, dict_distancias)
        #print("SCORE NIVEL:")
        #print(score_nivel)
        #print("SCORE COMPARACIÓN DE TÍTULOS:")
        score_titulos = score_comparison_titles(solo_titulo1, solo_titulo2)
        #print(score_titulos)
        #print("SCORE TOTAL: NIVEL + TITULO")
        #print(score_title_nivel(score_nivel, score_titulos))
        total_score = score_title_nivel(score_nivel, score_titulos)
    else:
        #print("SCORE SIMILITUD DE TITULO")
        #print(score_comparison_titles(solo_titulo1, solo_titulo2))
        total_score = score_comparison_titles(solo_titulo1, solo_titulo2)
    return total_score