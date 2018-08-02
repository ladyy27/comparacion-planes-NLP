#Proyecto de similaridad de titulos y topicos en espaniol
from mysql_connection import *
from similarity_proccesing import *
import codecs
from time import time
import threading

sql_materias = "SELECT id,s,o FROM planes WHERE p = 'asignatura'"
sql_nivel = "SELECT nivel, value FROM nivel"
sql_distancia = "SELECT distancia, porcSimilitud FROM distancia"

#Recuperar diccionario de distancias
dict_distancias = set_dict_distancias(sql_distancia)

#Recuperar lista de niveles
lista_niveles = set_list_levels (sql_nivel)

#Recuperar diccionario de materias con id
materias_query = select_query(sql_materias)

dict_materias = {}

a1 = "FÍSICA PARA LAS CIENCIAS BIOMÉDICAS"
"""
a2 = "física iii"
"""

for i in materias_query:
    indice = i[0]
    codigo = str(i[1])
    materia_texto = str(i[2])
    tupla = (codigo, materia_texto)
    dict_materias[indice] = tupla

f = codecs.open("files/comparison_titles_spanish.sql","wb",encoding="UTF-8")
f.write ("DROP TABLE IF EXISTS titlesComparison_spanish; " +
"CREATE TABLE titlesComparison_spanish (" +
"id int(11) unsigned NOT NULL AUTO_INCREMENT," +
  "codeX varchar(200) NOT NULL DEFAULT ''," +
  "subjectX varchar(200) NOT NULL DEFAULT ''," +
  "codeY varchar(200) NOT NULL DEFAULT '', "+
  "subjectY varchar(200) NOT NULL DEFAULT '', "+
  "score double NOT NULL, "+
  "PRIMARY KEY (id) "+
") ENGINE=InnoDB DEFAULT CHARSET=utf8;\n")




total_materias = len(dict_materias)
contador = 0
for ix in dict_materias:
    start_time = time()

    '''
    #Comparacion 1 materia contra todas
    codeY = dict_materias[ix][0]
    subjectY = dict_materias[ix][1]

    
    f.write('INSERT INTO  titlesComparison_spanish(codeX, subjectX, codeY, subjectY, score) values (%s, %s,%s, %s, %s)' % (
        '"' + "111" + '"', '"' + a1 + '"', '"' + codeY + '"', '"' + subjectY + '"', similarity_titles(a1, subjectY, lista_niveles, dict_distancias)) + ';\n')
    '''

    for iy in dict_materias:
        codeX = dict_materias[ix][0]
        subjectX = dict_materias[ix][1]
        codeY = dict_materias[iy][0]
        subjectY = dict_materias[iy][1]
        comparison_score = similarity_titles(subjectX, subjectY, lista_niveles, dict_distancias)
        f.write('INSERT INTO  titlesComparison_spanish(codeX, subjectX, codeY, subjectY, score) values (%s, %s,%s, %s, %s)' % (
            '"' + codeX + '"', '"' + subjectX + '"', '"' + codeY+ '"', '"' + subjectY + '"', comparison_score) + ';\n')

    elapsed_time = time() - start_time
    #print("Elapsed time: %.10f seconds." % elapsed_time)
    print (str(threading.current_thread().getName()) + "It" + str(contador) + " de " + str(total_materias) + " - " + " %.10f segundos." % elapsed_time )
    #print("Iteracion" + str(contador) + " de " + str(total_materias))
    contador += 1

#Cerrar archivo
f.close()
#Cerrar conexion
close_connectionMySQL()


"""
threads = list()
for i in range(3):
    t = threading.Thread(target=main)
    threads.append(t)
    t.start()
"""








