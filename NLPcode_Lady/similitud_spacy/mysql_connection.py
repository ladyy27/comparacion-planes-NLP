import pymysql.cursors

#autocommit=True
# DB: Conexion a la DB
def set_connectionMySQL():
	db = pymysql.connect(host='localhost',port=8889, user='root', password='root', db='proyNLP')
	return db

def close_connectionMySQL():
	db = set_connectionMySQL()
	db.close()
	return

def select_query(sql):
	# DB: Declarar cursor
	db = set_connectionMySQL()
	cursor = db.cursor()

	# DB: Ejecutar consulta
	try:
		cursor.execute(sql)
		resultados = cursor.fetchall()
	except:
		resultados = "no se pudo recuperar la consulta"
		db.rollback()
	return resultados

def insert_triples_query(s,p,o):
	# DB: Declarar cursor
	db = set_connectionMySQL()
	cursor = db.cursor()

	# DB: Ejecutar consulta
	try:
		sql = "INSERT INTO tripletas values ('%s', '%s', '%s')" % (s, p, o)
		cursor.execute(sql)
	except:
	    db.rollback()
	return

def update_sql(idd, valor):
	# DB: Declarar cursor
	db = set_connectionMySQL()
	cursor = db.cursor()

	# DB: Ejecutar consulta
	try:
		sql = "UPDATE planes1 SET cod_a ='%s' WHERE id= %d" % (valor, idd)
		print (sql)
		cursor.execute(sql)
		db.commit()
	except:
	    db.rollback()
	return

def update_titulacion(idd, valor):
	# DB: Declarar cursor
	db = set_connectionMySQL()
	cursor = db.cursor()

	# DB: Ejecutar consulta
	try:
		sql = "UPDATE planes2 SET titulacion ='%s' WHERE id= %d" % (valor, idd)
		print (sql)
		cursor.execute(sql)
		db.commit()
	except:
	    db.rollback()
	return
