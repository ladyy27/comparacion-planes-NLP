package planes3topicoEtiquetado;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

public class TopicLabeling {

	public static void main(String[] args) throws FileNotFoundException, SQLException {

		long startTime = System.nanoTime();

		final MySqlConnection dbObject = new MySqlConnection();
		final Connection conn = dbObject.AbrirConexion();
		final ArrayList<Triple> triples = dbObject.getTriplesFromDB(conn);

		System.out.println("Tamaño Triples: " + triples.size());

		// final ArrayList<QueryResult> result = search();
		final ArrayList<Triple> result = searchSQL();

		ArrayList<Thread> threads = new ArrayList<Thread>();
		
		Integer cont = 0;

		for (final Triple triple : triples) {

			Thread thread = new Thread() {
				public void run() {
					System.out.println("Elemento " + (triples.indexOf(triple) + 1));
					System.out.println(triple.getId() + " " + triple.getS() + " " + triple.getO() + "\n");

					UnescoObject unescoObject = new UnescoObject();
					unescoObject = comparison(result, triple.getO());
					dbObject.updateDataToDB(conn, unescoObject.getConceptUri(), unescoObject.getConceptName(),
							String.valueOf(triple.getId()));
				}
			};
			
			cont++;
			
			threads.add(thread);
			
			if (cont == 8) {
				long startTimeBlock = System.nanoTime();
				try {
					for (Thread th : threads) {
						th.start();
						System.out.println("Iniciando hilo");
					}
					for (Thread th : threads) {
						th.join();
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				long endTimeBlock = System.nanoTime();
				long elapsedTime = endTimeBlock - startTimeBlock;
				double blockTime = (double) (elapsedTime / 1000000000.0);

				System.out.println("Block time " + blockTime + " s");
				
				cont = 0;
				threads.clear();
			}
			
//			thread.start();
//			try {
//				thread.join();
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			threads.add(thread);
		}
		

//		try {
//			for (Thread thread : threads) {
//				thread.start();
//				System.out.println("Iniciando hilo");
//			}
//			for (Thread thread : threads) {
//				thread.join();
//			}
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		System.out.println("done!");

		long endTime = System.nanoTime();
		long elapsedTime = endTime - startTime;
		double minutes = (double) (elapsedTime / 1000000000.0)/60;

		System.out.println("Took " + minutes + " min");

	}

	// static ArrayList<QueryResult> search() {
	// ParameterizedSparqlString qs = new ParameterizedSparqlString(
	// "" + "prefix skos: <http://www.w3.org/2004/02/skos/core#>\n"
	// + " select distinct ?conceptUri ?conceptName from <http://unesco.com>
	// where {\n"
	// + " ?conceptUri a skos:Concept ;\n" + " skos:prefLabel ?name .\n"
	// + " FILTER (LANG(?name)='en')\n" + " BIND (STR(?name) AS ?conceptName)
	// \n" + "}");
	//
	// QueryExecution exec =
	// QueryExecutionFactory.sparqlService("http://localhost:8890/sparql",
	// qs.asQuery());
	//
	// ResultSet results = exec.execSelect();
	//
	// ArrayList<QueryResult> conceptsResult = new ArrayList<QueryResult>();
	//
	// QueryResult queryResult;
	//
	// while (results.hasNext()) {
	// QuerySolution res = results.next();
	//
	// queryResult = new QueryResult();
	//
	// queryResult.setConceptName(res.get("conceptName").toString());
	// queryResult.setConceptUri(res.get("conceptUri").toString());
	// conceptsResult.add(queryResult);
	//
	// }
	//
	// return conceptsResult;
	// }

	static ArrayList<Triple> searchSQL() {
		MySqlConnection dbObject = new MySqlConnection();
		Connection conn = dbObject.AbrirConexion();
		ArrayList<Triple> unescoTaxonomy = dbObject.getUnescoTaxonomy(conn);
		return unescoTaxonomy;
	}

	static UnescoObject comparison(ArrayList<Triple> Result, String value) {

		ADW pipeLine = new ADW();
//
		final String text1 = value;
		ItemType text1Type = ItemType.SURFACE;

		ItemType text2Type = ItemType.SURFACE;

		DisambiguationMethod disMethod = DisambiguationMethod.ALIGNMENT_BASED;

		SignatureComparison measure = new WeightedOverlap();

		final ArrayList<UnescoObject> unescoObjects = new ArrayList<UnescoObject>();
//		ArrayList<Thread> threads = new ArrayList<Thread>();
//
//		Integer nThreads = 6;
//		int size = 2504 / nThreads;
//		for (int start = 0; start < Result.size(); start += size) {
//			int end = Math.min(start + size, Result.size());
//			// System.out.println("Start: " + start);
//			// System.out.println("End " + end);
//			final List<Triple> sublist = Result.subList(start, end);
//
//			System.out.println("Tamaño lista: " + sublist.size());
//
//			Thread thread = new Thread() {
//				/*
//				 * (non-Javadoc)
//				 * 
//				 * @see java.lang.Thread#run()
//				 */
//				public void run() {
//					for (Triple tr : sublist) {
//						ItemType text1Type = ItemType.SURFACE;
//						ItemType text2Type = ItemType.SURFACE;
//						ADW pipeLine = new ADW();
//						DisambiguationMethod disMethod = DisambiguationMethod.ALIGNMENT_BASED;
//						SignatureComparison measure = new WeightedOverlap();
//						
//						UnescoObject unescoObject = new UnescoObject();
//						unescoObject.setConceptName(tr.getO());
//						unescoObject.setConceptUri(tr.getS());
//
//						String text2 = tr.getO();
//
//						double score = pipeLine.getPairSimilarity(text1, text2, disMethod, measure, text1Type, text2Type);
//						unescoObject.setScore(score);
//						unescoObjects.add(unescoObject);
//					}
//				}
//			};
//			threads.add(thread);
//		}
//		
//		try {
//			for (Thread thread : threads) {
//				thread.start();
//				System.out.println("Iniciando hilo");
//			}
//			for (Thread thread : threads) {
//				thread.join();
//			}
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		for (Triple result : Result) {
			UnescoObject unescoObject = new UnescoObject();
			unescoObject.setConceptName(result.getO());
			unescoObject.setConceptUri(result.getS());

			String text2 = result.getO();

			double score = pipeLine.getPairSimilarity(text1, text2, disMethod, measure, text1Type, text2Type);
			unescoObject.setScore(score);
			unescoObjects.add(unescoObject);
		}

		java.util.Collections.sort(unescoObjects);

		return unescoObjects.get(0);

	}

}
