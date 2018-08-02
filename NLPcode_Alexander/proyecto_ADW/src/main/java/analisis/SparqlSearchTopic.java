package analisis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFactory;
//import org.apache.jena.query.ResultSetFormatter;
//import org.apache.jena.rdf.model.Literal;
//import org.apache.jena.rdf.model.ResourceFactory;

import it.uniroma1.lcl.adw.ADW;
import it.uniroma1.lcl.adw.DisambiguationMethod;
import it.uniroma1.lcl.adw.ItemType;
import it.uniroma1.lcl.adw.comparison.SignatureComparison;
import it.uniroma1.lcl.adw.comparison.WeightedOverlap;

public class SparqlSearchTopic {

	public static void main(String[] args) throws FileNotFoundException, SQLException {

		MySqlConnection dbObject = new MySqlConnection();
		Connection conn = dbObject.AbrirConexion();
		ArrayList<Triple> triples = dbObject.getTriplesFromDB(conn);

		PrintWriter pw = new PrintWriter(new File("topicsComparison.csv"));
		StringBuilder sb = new StringBuilder();
		
		System.out.println("Tamaño Triples: " + triples.size());
		
		ArrayList<QueryResult> result = search();
		
		for (Triple triple : triples) {
			System.out.println("\nElemento " + triples.indexOf(triple));
			System.out.println("\n" + triple.getId() + " " + triple.getS() + " " + triple.getO() + "\n");
			sb.append("\n" + triple.getId() + " " + triple.getS() + ";" + triple.getO() + "\n");
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

		ArrayList<UnescoObject> unescoObjects = new ArrayList<UnescoObject>();
		
//		int nCom = 0;
		
		for (QueryResult queryResult : result) {
			UnescoObject unescoObject = new UnescoObject();
			unescoObject.setConceptName(queryResult.getConceptName());
			unescoObject.setConceptUri(queryResult.getConceptUri());

			String text2 = queryResult.getConceptName();
			
			double score = pipeLine.getPairSimilarity(text1, text2, disMethod, measure, text1Type, text2Type);
			unescoObject.setScore(score);
			unescoObjects.add(unescoObject);
//			nCom++;
//			System.out.println("cont2: " + nCom);
		}

		java.util.Collections.sort(unescoObjects);

		int cont = 0;

		// print comparison top scores
		for (UnescoObject unescoObject : unescoObjects) {
			if (cont < 10) {
				System.out.println(" score: " + unescoObject.getScore() + " - " + unescoObject.getConceptUri() + " "
						+ unescoObject.getConceptName());
				
				sb.append(unescoObject.getScore() + ";" + unescoObject.getConceptUri() + ";"
						+ unescoObject.getConceptName() + "\n");
			} else {
				break;
			}
			cont++;
		}
			
	}

}
