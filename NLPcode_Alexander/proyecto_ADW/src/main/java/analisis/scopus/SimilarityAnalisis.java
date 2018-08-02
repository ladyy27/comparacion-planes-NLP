package analisis.scopus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;


import analisis.Triple;
import it.uniroma1.lcl.adw.ADW;
import it.uniroma1.lcl.adw.DisambiguationMethod;
import it.uniroma1.lcl.adw.ItemType;
import it.uniroma1.lcl.adw.comparison.SignatureComparison;
import it.uniroma1.lcl.adw.comparison.WeightedOverlap;

public class SimilarityAnalisis {

	public static void main(String[] args) throws FileNotFoundException{
		
		CSVReader csvReader = new CSVReader();
		
		ArrayList<Triple> triples = csvReader.getData("scopus/ClasificacionScopus.csv");

		PrintWriter pw = new PrintWriter(new File("scopus/scopusAnalisis.csv"));
		StringBuilder sb = new StringBuilder();
		
		System.out.println("Tamaño Triples: " + triples.size());
		
		ArrayList<QueryResult> result = search();
		
		for (Triple triple : triples) {
			System.out.println("\nElemento " + triples.indexOf(triple));
			System.out.println("\n" + triple.getS() + " " + triple.getO() + "\n");
			sb.append(triple.getS() + ";" + triple.getO() + ";");
			comparison(result, triple.getO(), sb);
		}
		
		pw.write(sb.toString());
		pw.close();
		System.out.println("done!");

	}

	static ArrayList<QueryResult> search() {
		ParameterizedSparqlString qs = new ParameterizedSparqlString(
				"" + "prefix skos: <http://www.w3.org/2004/02/skos/core#>\n"
						+ "	select distinct ?conceptUri ?conceptName from <http://unesco.com> where {\n"
						+ "	?conceptUri a skos:Concept ;\n" + "	skos:prefLabel ?name .\n"
						+ "	FILTER (LANG(?name)='en')\n" + "	BIND (STR(?name)  AS ?conceptName) \n" + "}");

		QueryExecution exec = QueryExecutionFactory.sparqlService("http://localhost:8890/sparql", qs.asQuery());

		ResultSet results = exec.execSelect();
		
		ArrayList<QueryResult> conceptsResult = new ArrayList<QueryResult>();
		
		QueryResult queryResult;
		
		while (results.hasNext()) {
			QuerySolution res = results.next();
			
			queryResult = new QueryResult();
			
			queryResult.setConceptName(res.get("conceptName").toString());
			queryResult.setConceptUri(res.get("conceptUri").toString());
			conceptsResult.add(queryResult);
			
		}

		return conceptsResult;

	}

	static void comparison(ArrayList<QueryResult> result, String value, StringBuilder sb) {

		ADW pipeLine = new ADW();

		String text1 = value;
		ItemType text1Type = ItemType.SURFACE;

		ItemType text2Type = ItemType.SURFACE;

		DisambiguationMethod disMethod = DisambiguationMethod.ALIGNMENT_BASED;

		SignatureComparison measure = new WeightedOverlap();

		ArrayList<ScopusObject> scopusObjects = new ArrayList<ScopusObject>();
		
//		int nCom = 0;
		
		for (QueryResult queryResult : result) {
			ScopusObject unescoObject = new ScopusObject();
			unescoObject.setConceptName(queryResult.getConceptName());
			unescoObject.setConceptUri(queryResult.getConceptUri());

			String text2 = queryResult.getConceptName();
			
			double score = pipeLine.getPairSimilarity(text1, text2, disMethod, measure, text1Type, text2Type);
			unescoObject.setScore(score);
			scopusObjects.add(unescoObject);
//			nCom++;
//			System.out.println("cont2: " + nCom);
		}

		java.util.Collections.sort(scopusObjects);

		int cont = 0;

		// print comparison top scores
		for (ScopusObject scopusObject : scopusObjects) {
			if (cont < 1) {
				System.out.println(" score: " + scopusObject.getScore() + " - " + scopusObject.getConceptUri() + " "
						+ scopusObject.getConceptName());
				
				sb.append(scopusObject.getScore() + ";" + scopusObject.getConceptUri() + ";"
						+ scopusObject.getConceptName() + "\n");
			} else {
				break;
			}
			cont++;
		}
			
	}

}
