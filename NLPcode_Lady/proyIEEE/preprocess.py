import codecs

#STOPWORDS

#Generar lista de stopwords
def set_stopwords(file_path):
	stopwords = []
	stopfile= codecs.open(file_path,"r",encoding="UTF-8")
	for line in stopfile:
	    stop = str(line)
	    stop1 = stop.encode('utf8')
	    stop2 = stop1.replace("\n","")
	    stopwords.append(stop2)
	return stopwords



