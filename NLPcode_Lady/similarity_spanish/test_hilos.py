import multiprocessing
import time
import spacy
nlp = spacy.load('es_core_news_md')
from similarity_proccesing import *
from mysql_connection import *

def lista_materias():
    consulta = select_query("SELECT o FROM planes WHERE p = 'asignatura'")
    list1 =[]
    for i in consulta:
        list1.append(i[0])
    return list1


#Función que consume los datos de la cola
def worker(q):
    contador = 0
    while not q.empty():
        n = q.get()
        print( '{0} imprimiendo: {1}\n'.format(multiprocessing.current_process().name, n), end = '')
        #time.sleep(0.5)
        contador+=1
    print('{0} terminó su trabajo\n'.format(multiprocessing.current_process().name), end = '')
    print('{0} total iteraciones'.format(contador))

def main():
    #Vamos a llenar la cola con algunos datos, en este caso enteros:
    q = multiprocessing.Queue()
    for materia in lista_materias():
        q.put("FÍSICA PARA LAS CIENCIAS BIOMÉDICAS," + materia + "= "+ str(score_comparison_titles("FÍSICA PARA LAS CIENCIAS BIOMÉDICAS", materia)))
    #for n in range(1, 100):
    #   q.put(n)

    #Creamos los procesos
    process_count = 200
    processes=[]
    for i in range(process_count):
        p = multiprocessing.Process(target=worker, args = (q,))
        processes.append(p)
        p.start()


    #Esperamos a que todos los procesos terminen antes de terminar el programa principal
    for process in processes:
         process.join()

if __name__ == '__main__':
    main()

    """kwargs = {'inicio': 0,
              'incremento': 1,
              'limite': 10}
              
               kwargs =(materia,)"""