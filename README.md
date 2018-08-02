# comparacion-planes-NLP
Proyecto enfocado en la comparación semántica de planes docentes usando herramientas **NLP** (Natural Language Processing), la gestión de los datos se realiza mediante un enfoque de **Linked Data** (recursos RDF). En cuanto al diseño arquitectónico de la aplicación se ha planteado un enfoque orientado a **Microservicios** (MS).

#### Ubicación: Universidad Técnica Particular de Loja (UTPL) - Loja - Ecuador

#### Responsables
* Nelson Piedra Pullaguari ([nopiedra@utpl.edu.ec](nopiedra@utpl.edu.ec)) 
* Lady Yaguachi Pereira ([leyaguachi1@utpl.edu.ec](leyaguachi1@utpl.edu.ec)) 
* Alexander Sánchez Narváez ([ajsanchez9@utpl.edu.ec](ajsanchez9@utpl.edu.ec)) 

#### Estado: En desarrollo

## Tareas realizadas

### Diseño Arquitectónico:
Esta arquitectura toma en cuenta el uso de **REST**, un estilo arquitectónico que permite una interacción ligera y universal entre microservicios y **Linked Data** para la gestión de información, este enfoque permite mejorar el reuso, la interoperabilidad y la integración de recursos de información.

Para diseñar la arquitectura de microservicios de la aplicación NLP se tuvo en cuenta tres factores: 
* Funcionalidad de la aplicación: Comparación semántica de contenidos expresados en lenguaje natural para determinar el grado de relación y similitud entre estos.
* Tareas NLP: Pre-procesamiento, Análisis Morfológico, Sintáctico y Semántico.
* Naturaleza de los microservicios: En una arquitectura de microservicios se considera funcionalidades en lugar de módulos, dichas funcionalidades se exponen y representan como servicios que se comunican entre sí mediante mecanismos ligeros (HTTP).

![Arquitectura MS para aplicación NLP](https://github.com/ladyy27/comparacion-planes-NLP/blob/master/files/NLPArchitecture.png)

### Ontología:
Referente a Web semántica, se construyó una ontología que contemple las clases y propiedades a usarse en el prototipo final. La ontología sirve como base para la generación de las tripletas para los resultados obtenidos de la fase de Preprocesamiento y de Análisis Morfológico: [Spacy(Python)](https://spacy.io/usage/linguistic-features), [CoreNLP](https://stanfordnlp.github.io/CoreNLP/) y guardar los resultados en formato RDF (tripletas): [rdflib (Python)](https://github.com/RDFLib/rdflib), [Apache Jena](https://jena.apache.org/tutorials/rdf_api.html).

![Ontologia Planes Docentes](https://github.com/ladyy27/comparacion-planes-NLP/blob/master/files/NLP_Ontology_Unified.png)

### Analisis de similitud:
* #### Pre-proceso:
1. Traducción a inglés de los principales elementos de los planes como título, sección, departamento, tópicos y resultados.
2. Etiquetado de los tópicos con taxonomía de [Unesco](http://skos.um.es/unesco6/view.php?fmt=1) y [DBpedia](https://wiki.dbpedia.org/) , permitiendo identificar cada tópico dentro de las áreas de conocimiento representada en recursos RDF. Herramientas utilizadas: [Virtuoso](https://virtuoso.openlinksw.com/).

* #### Proceso:
1. Ingreso de código de dos materias y obtención de títulos y tópicos.
2. Comparación de títulos, si al comparar los nombres se obtiene una similitud de de 100% entonces se compara también el nivel, y se promedian los resultados. Para comparar el nivel se establecieron 10 niveles y el porcentaje de distancia que se da entre los mismos (i.e)
_Cálculo I VS Cálculo, Score = 100 % similitud_
3. Comparar el etiquetado de topicos obtenido la medida de similud para tópicos.
4. El score general es resultado del promedio entre el score del título y el de tópicos.
Herramientas utilizadas: [ADW Semantic Similarity (Java)](http://lcl.uniroma1.it/adw/), [Spacy Similarity (Python)](https://spacy.io/usage/vectors-similarity), Modelo de distancia entre niveles (planteado por los responsables del proyecto)

![Proceso de comparación semántica](https://github.com/ladyy27/comparacion-planes-NLP/blob/master/files/ComparisonProtocol.png)

### Clustering:
Se ha propuesto usar como medida de similitud la clasificación de títulos y tópicos de planes obtenida mediante la aplicación de algoritmos de clusterización: **Hierarchy, K-means y Affinity Propagation**. Herramientas utilizadas: [Scipy](https://docs.scipy.org/doc/scipy/reference/cluster.hierarchy.html), [Sckit Learn](http://scikit-learn.org/stable/modules/generated/sklearn.cluster.AffinityPropagation.html).
