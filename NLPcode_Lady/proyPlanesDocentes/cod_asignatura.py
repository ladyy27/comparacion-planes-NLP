from mysql_connection import *

db= set_connectionMySQL()

"""
"sql = "SELECT * FROM planes1 WHERE p = 'texto_limpio' or p = 'resultado'"
sql1 = "SELECT s FROM planes1 WHERE p = 'asignatura'"

alist= []
asignaturas = ieee_select_query(sql1)
for a in asignaturas:
	alist.append(a[0])


res = ieee_select_query(sql)

"""
for r in res:
	cod = str(r[1])
	idd = int(r[0])
	for aa in alist:
		if cod.find(str(aa)) >= 0:
			update_sql(idd, str(aa))∫
"""


#UPDATE TITULACION



sql = "SELECT s,o FROM planes2 WHERE p = 'responsable'" #lista de titulaciones, codigo
res_titulaciones = ieee_select_query(sql)
dic_titulaciones = {}

sql1 = "SELECT id, s FROM planes2 WHERE p = 'asignatura'" #lista de codigos de asignaturas
res_asignaturas = ieee_select_query(sql1)

for i in res_titulaciones:
	codigo_a = str(i[0])
	nombre_carrera = str(i[1])
	dic_titulaciones[codigo_a] = nombre_carrera

#for key in dic_titulaciones:
	#print (key, dic_titulaciones[key])
cursor = db.cursor()
for r in res_asignaturas:
	cod = str(r[1])
	idd = int(r[0])
	for key in dic_titulaciones:
		if str(key) == cod:
			nom_titulacion = str(dic_titulaciones[key])
			sql = "UPDATE planes2 SET titulacion ='%s' WHERE id= %d" % (nom_titulacion, idd)
			print (sql)
			cursor.execute(sql)
			db.commit()
		else:
			print ("what!?")
			#update_titulacion(idd,nom_titulacion)
			#print ("Actualizar "+ nom_titulacion + " en " + idd)

close_connectionMySQL()
