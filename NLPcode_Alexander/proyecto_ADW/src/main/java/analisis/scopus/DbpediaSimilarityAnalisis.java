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
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.ResourceFactory;

import analisis.Triple;
import it.uniroma1.lcl.adw.ADW;
import it.uniroma1.lcl.adw.DisambiguationMethod;
import it.uniroma1.lcl.adw.ItemType;
import it.uniroma1.lcl.adw.comparison.SignatureComparison;
import it.uniroma1.lcl.adw.comparison.WeightedOverlap;

public class DbpediaSimilarityAnalisis {

	public static void main(String[] args) throws FileNotFoundException{
		
		CSVReader csvReader = new CSVReader();
		
		ArrayList<Triple> triples = csvReader.getData("scopus/ClasificacionScopus1.csv");

		PrintWriter pw = new PrintWriter(new File("scopus/DbpediascopusAnalisis.csv"));
		StringBuilder sb = new StringBuilder();
		
		System.out.println("Tamaño Triples: " + triples.size());
		
		for (Triple triple : triples) {
			System.out.println("\nElemento " + triples.indexOf(triple));
			ArrayList<QueryResult> result = search(triple.getO());
			System.out.println("\n" + triple.getS() + " " + triple.getO() + "\n");
			
			sb.append(triple.getS() + ";" + triple.getO() + ";");
			
			for (QueryResult queryResult : result) {
				System.out.println(queryResult.getConceptUri());
				sb.append(queryResult.getConceptUri() + ";");
			}
			
			if (result.size() == 0) {
				String[] values = triple.getO().split(" and ");
				
				for (String string : values) {
					string = string.replace("\"", "").replace(" (miscellaneous)", "").replace(" (miscalleneous)", "");
					string = "\"" + string + "\"";
					
					System.out.println(string);
					result = search(string);
					
					for (QueryResult queryResult : result) {
						System.out.println(queryResult.getConceptUri());
						sb.append(queryResult.getConceptUri() + ";");
					}
				}
//				search(value);
//				;
			}
			
			
			sb.append("\n");
//			comparison(result, triple.getO(), sb);
		}
		
		pw.write(sb.toString());
		pw.close();
		System.out.println("done!");

	}

	static ArrayList<QueryResult> search(String value) {
		System.out.println("Value: "+value);
		ParameterizedSparqlString qs = new ParameterizedSparqlString(""
				+ "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" + 
				"prefix skos: <http://www.w3.org/2004/02/skos/core#>\n" + 
				"prefix owl: <http://www.w3.org/2002/07/owl#>\n" + 
				"select ?conceptUri ?conceptName from <http://dbpedia.org> where {\n" + 
				"	{\n" + 
				"		?conceptUri rdfs:label ?conceptName .\n" + 
				"		FILTER (?conceptName="+value+"@en)\n" + 
				"	}\n" + 
				"}");
		
//		Literal subject = ResourceFactory.createLangLiteral(value, "es");
//        qs.setParam("m", subject);

		QueryExecution exec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", qs.asQuery());

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
		
		System.out.println("Tamaño respuesta: " + conceptsResult.size());

		return conceptsResult;

	}

//	static void comparison(ArrayList<QueryResult> result, String value, StringBuilder sb) {
//
//		ADW pipeLine = new ADW();
//
//		String text1 = value;
//		ItemType text1Type = ItemType.SURFACE;
//
//		ItemType text2Type = ItemType.SURFACE;
//
//		DisambiguationMethod disMethod = DisambiguationMethod.ALIGNMENT_BASED;
//
//		SignatureComparison measure = new WeightedOverlap();
//
//		ArrayList<ScopusObject> scopusObjects = new ArrayList<ScopusObject>();
//		
////		int nCom = 0;
//		
//		for (QueryResult queryResult : result) {
//			ScopusObject unescoObject = new ScopusObject();
//			unescoObject.setConceptName(queryResult.getConceptName());
//			unescoObject.setConceptUri(queryResult.getConceptUri());
//
//			String text2 = queryResult.getConceptName();
//			
//			double score = pipeLine.getPairSimilarity(text1, text2, disMethod, measure, text1Type, text2Type);
//			unescoObject.setScore(score);
//			scopusObjects.add(unescoObject);
////			nCom++;
////			System.out.println("cont2: " + nCom);
//		}
//
//		java.util.Collections.sort(scopusObjects);
//
//		int cont = 0;
//
//		// print comparison top scores
//		for (ScopusObject scopusObject : scopusObjects) {
//			if (cont < 1) {
//				System.out.println(" score: " + scopusObject.getScore() + " - " + scopusObject.getConceptUri() + " "
//						+ scopusObject.getConceptName());
//				
//				sb.append(scopusObject.getScore() + ";" + scopusObject.getConceptUri() + ";"
//						+ scopusObject.getConceptName() + "\n");
//			} else {
//				break;
//			}
//			cont++;
//		}
//			
//	}

}
