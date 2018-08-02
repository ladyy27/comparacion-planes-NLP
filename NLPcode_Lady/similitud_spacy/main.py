#Proyecto de similaridad de titulos y topicos en espaniol
from mysql_connection import *
from spacy_processing import *
set_connectionMySQL()

sql_materias = "SELECT id,o FROM planes WHERE p = 'asignatura' LIMIT 5"
sql_nivel = "SELECT nivel, value FROM nivel"

#Recuperar lista de niveles
list_niveles = []
nivel_query = select_query(sql_nivel)
for nivel in nivel_query:
    list_niveles.append(list(nivel))

"""
for i in list_niveles:
    print (i[1])
"""

#Recuperar diccionario de materias con id
materias_query = select_query(sql_materias)
dict_materias = {}

for i in materias_query:
    indice = i[0]
    materia_texto= str(i[1])
    dict_materias[indice] = materia_texto

#for it in dict_materias:
#    print (it, dict_materias[it])

for it in dict_materias:
    lista_tokens= tokenizing(dict_materias[it])
    for token in lista_tokens:
        for nivel in list_niveles:
            if token == nivel[1] :
                print(token, nivel[0])

#Reconocer nivel
close_connectionMySQL()
