import codecs
import struct
import sys
import glob
import os
import types
import binascii
import re
from mysql_connection import *

class ClsPJ:
    #Clase para almacenar los datos de cada PJ
    def __init__(self):
        #self.cadena = []
        self.cadena = ""
        self.nombre = ""
        #pass    
    def AddDato(self, dato):
    	self.cadena = self.cadena + ". " + dato
        #self.cadena.append(dato)
    
    #def printDato(self):
        #for linea in self.cadena:
        	#print(linea)


def concatenar_por_ID():
	#cadena = ""
	for oracion in textos_limpios: #Recorrermos el log, menos la primera l?nea que es la cabecera
		if not oracion[5] in lstPJ: #Si la key no existe en el diccionario
			lstPJ[oracion[5]] = ClsPJ() #Creamos una entrada nueva en el diccionario
			lstPJ[oracion[5]].nombre = oracion[5] #escribimos la key en el objeto)

		lstPJ[oracion[5]].AddDato(str(oracion[3])) #agregamos el dato al objeto
	return

set_connectionMySQL()


sql_asignaturas = "SELECT o FROM planes2 WHERE p = 'asignatura'"
sql_titulacion = "SELECT DISTINCT o, titulacion FROM planes2 WHERE p = 'asignatura'"
asignaturas = ieee_select_query(sql_titulacion)

f =codecs.open("/home/ela/Dropbox/ProyectoUTPL-NLP/NLPcode_Lady/proyPlanesDocentes/files/asignatura_tit.txt","wb",encoding="UTF-8")

for a in asignaturas:
	codigo = str(a[0])
	titulos = str(a[1])
	#f.write('%s' % (codigo) + '\n')
	f.write('%s (%s)' % (codigo, titulos) + '\n')



"""

sql_contenido = "SELECT * FROM planes WHERE  p = 'resultado' ORDER BY id ASC"
textos_limpios = ieee_select_query(sql_contenido)
lstPJ = {} #creamos el diccionario que contendr? los datos
concatenar_por_ID() #Leemos el archivo de prueba

contenidos= []
for k, v in lstPJ.items():
	contenidos.append(v.cadena)
	#print(v.nombre)
   

iteradorr = 1
lenn = len(contenidos)
f =codecs.open("/home/ela/Dropbox/ProyectoUTPL-NLP/NLPcode_Lady/proyPlanesDocentes/files/resultados.txt","wb",encoding="UTF-8")
for itera in contenidos:
    textol = str(itera)
    #print(textol)
    #print ("Guardando " + str(iteradorr) + "de " + str(lenn))
    f.write('%s' % textol + '\n')
    #iteradorr += 1
"""
close_connectionMySQL()
