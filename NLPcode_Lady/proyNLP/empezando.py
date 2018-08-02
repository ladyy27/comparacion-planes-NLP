# coding=utf-8
from textblob import TextBlob
from textblob import Word
import random
#import textract

#pdf = textract.process("/home/ela/NLP archivos/tiedemann2014.pdf")

cadena = str("Importancia del componente dentro del perfil de egreso de la titulacion: Sistemas Basados en el Conocimiento es una asignatura que se imparte en decimo ciclo de la titulacion de Ingenieria en Sistemas Informaticos y Computacion, ofertada por la Universidad Tecnica Particular de Loja. La asignatura introduce al profesional en formacion en una de las areas mas interesantes, emergentes y prometedoras que ha surgido en nuestra era actual de la sociedad del conocimiento, estructuras de conocimiento abierto y las tecnologias de la Web Semantica. Hoy en dia, es imprescindible que nuestros ingenieros, adquieran competencias clave para capturar, modelar, gestionar y explotar datos y conocimiento que en grandes cantidades se generan y estan disponibles en la Web y que con diferentes fines pueden ser aprovechados en la practica laboral, academica y empresarial. En el presente componente academico, se cubren aspectos como: la creacion de esquemas de representacion de conocimiento y datos, la descripcion de recursos y objetos del mundo real mediante esos modelos, la publicacion de datos enlazados en la Web, asi como su posterior aprovechamiento para efectos de recuperar datos y generar nuevo conocimiento. Cuando un estudiante finalice este componente habra adquirido la capacidad para identificar areas y usos potenciales de las tecnologias de la web semantica, modelar dominios de conocimiento e implementar modelos procesables por maquinas.")

entrada = TextBlob(cadena)


print ("----------------------------------")
print ('POS: ')
print entrada.tags

"""print ("----------------------------------")
print ('Parrafos: ')
print entrada.sentences
print ("----------------------------------")
print ('El idioma identificado del texto es: ')
print entrada.detect_language()
"""
print ("----------------------------------")
print ('Traduccion a INGLES')
#entrada = entrada.translate(to='en')
print entrada.translate(to='en')
print ("----------------------------------")

nn = list()
for words, pos in entrada.tags:
    if pos == 'NNP':
        nn.append(words.lemmatize()) #palabra raiz de la que se esta hablando

print ("Palabras clave: ")
for item in random.sample(nn, 5): # numero de palabras a extraer
    words = Word(item)
    print (words)
