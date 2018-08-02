package planes4tituloEtiquetado;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;

import it.uniroma1.lcl.adw.ADW;
import it.uniroma1.lcl.adw.DisambiguationMethod;
import it.uniroma1.lcl.adw.ItemType;
import it.uniroma1.lcl.adw.comparison.SignatureComparison;
import it.uniroma1.lcl.adw.comparison.WeightedOverlap;

public class CareerLabeling {

	public static void main(String[] args) throws FileNotFoundException, SQLException {

		MySqlConnection dbObject = new MySqlConnection();
		Connection conn = dbObject.AbrirConexion();
		ArrayList<String> careers = dbObject.getCareersFromDB(conn);
		
		System.out.println("Tamaño Triples: " + careers.size());
		
		ArrayList<QueryResult> result = search();
		
		for (String career : careers) {
			System.out.println("\nElemento " + careers.indexOf(career));
			System.out.println("\n" + career +"\n");
			UnescoObject unescoObject = new UnescoObject();
			unescoObject = comparison(result, career);
			dbObject.updateDataToDB(conn, unescoObject.getConceptUri(), unescoObject.getConceptName(), career);
		}
		
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

	static UnescoObject comparison(ArrayList<QueryResult> result, String value) {

		ADW pipeLine = new ADW();

		String text1 = value;
		ItemType text1Type = ItemType.SURFACE;

		ItemType text2Type = ItemType.SURFACE;

		DisambiguationMethod disMethod = DisambiguationMethod.ALIGNMENT_BASED;

		SignatureComparison measure = new WeightedOverlap();

		ArrayList<UnescoObject> unescoObjects = new ArrayList<UnescoObject>();
		
		for (QueryResult queryResult : result) {
			UnescoObject unescoObject = new UnescoObject();
			unescoObject.setConceptName(queryResult.getConceptName());
			unescoObject.setConceptUri(queryResult.getConceptUri());

			String text2 = queryResult.getConceptName();
			
			double score = pipeLine.getPairSimilarity(text1, text2, disMethod, measure, text1Type, text2Type);
			unescoObject.setScore(score);
			unescoObjects.add(unescoObject);
		}

		java.util.Collections.sort(unescoObjects);
		
		return unescoObjects.get(0);
			
	}

}
