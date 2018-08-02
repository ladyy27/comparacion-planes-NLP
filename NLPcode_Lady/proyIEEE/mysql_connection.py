import MySQLdb

# DB: Conexion a la DB
def set_connectionMySQL():
	db = MySQLdb.connect("localhost", "root", "root", "planesdocentes_utpl")

# DB: Declarar cursor
cursor = db.cursor()

# DB: Ejecutar consulta
try:
    cursor.execute("TRUNCATE TABLE tripletas")
    cursor.execute("SELECT * FROM 2017_IEEE_Taxonomy_v1")
    resultados = cursor.fetchall()
except:
    db.rollback()

db.close()