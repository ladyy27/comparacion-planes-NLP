from spacy_processing import *
from mysql_connection import *

#Recuperar lista de niveles
def set_list_levels (sql_nivel):
    list_niveles = []
    nivel_query = select_query(sql_nivel)
    for nivel in nivel_query:
        list_niveles.append(list(nivel))
    return list_niveles

def calcular_similarity(asignatura1, asignatura2):
    return

def identify_level(asignatura, list_niveles):
    lista_tokens= tokenizing(asignatura)
    for token in lista_tokens:
        for nivel in list_niveles:
            if token == nivel[1] :
                return nivel[0]

def diferencia_nivel(n1, n2):
    if n1 > n2:
        diferencia = n1 - n2
    elif n2 > n1:
        diferencia = n2 - n1
    else:
        diferencia = 0
    return diferencia

def is_same_title(asignatura1, asignatura2):
    if asignatura1 :
        return True
    else:
        return False


lista_niveles = set_list_levels ("SELECT nivel, value FROM nivel")

a1 = "Calculo I"
a2 = "Calculo a"
n1 = identify_level(a1, lista_niveles )
n2 = identify_level(a2, lista_niveles )

print (diferencia_nivel(n1, n2) )
