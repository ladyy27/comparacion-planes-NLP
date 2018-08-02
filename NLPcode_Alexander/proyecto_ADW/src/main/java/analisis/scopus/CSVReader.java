package analisis.scopus;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import analisis.Triple;

public class CSVReader {

    public ArrayList<Triple> getData(String nameFile) {
		
        String csvFile = nameFile;
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        
		Triple triple;
		ArrayList<Triple> triples = new ArrayList<Triple>();
		
		int cont = 0;

        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] scopus = line.split(cvsSplitBy);
                
                if (cont != 0) {
                	System.out.println("Scopus Area [code= " + scopus[0] + " , name=" + scopus[1] + "]");
                    
                    triple = new Triple();
                    triple.setS(scopus[0]);
                    triple.setP("subjectArea");
                    
//                    scopus[1] = scopus[1].replace(" (miscellaneous)", "").replace(" (miscalleneous)", "");
//                    if (scopus[1].substring(scopus[1].length()-1, scopus[1].length()).equals(" ")) {
//                    	System.out.println("Antes: " + scopus[1]);
//                    	scopus[1] = scopus[1].replace(scopus[1].substring(scopus[1].length()-1, scopus[1].length()), "");
//                    	System.out.println("Después: " + scopus[1]);
//					}
                    
    				triple.setO(scopus[1]);
    				triples.add(triple);
				}
                
                cont++;

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

		return triples;
	}

}
