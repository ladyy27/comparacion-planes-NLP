import MySQLdb

# DB: Conexion a la DB
def set_connectionMySQL():
	db = MySQLdb.connect("localhost", "root", "root", "triplesNLP")
	return db

def close_connectionMySQL():
	db = set_connectionMySQL()
	db.close()
	return

def ieee_select_query():
	# DB: Declarar cursor
	db = set_connectionMySQL()
	cursor = db.cursor()

	# DB: Ejecutar consulta
	try:
	    cursor.execute("TRUNCATE TABLE tripletas")
	    cursor.execute("SELECT Id, CAST(CONVERT(Concept USING utf8) AS binary) FROM 2017_IEEE_Taxonomy_v1")
	    #cursor.execute("SELECT * FROM planes WHERE p='asignatura' or p='texto_numero' or p='texto_limpio' or p='resultado'")
	    resultados = cursor.fetchall()
	except:
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







