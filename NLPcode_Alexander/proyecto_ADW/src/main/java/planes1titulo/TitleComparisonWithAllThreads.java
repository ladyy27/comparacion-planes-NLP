package planes1titulo;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import it.uniroma1.lcl.adw.ADW;
import it.uniroma1.lcl.adw.DisambiguationMethod;
import it.uniroma1.lcl.adw.ItemType;
import it.uniroma1.lcl.adw.comparison.SignatureComparison;
import it.uniroma1.lcl.adw.comparison.WeightedOverlap;
import planes2nivel.LevelComparison;

public class TitleComparisonWithAllThreads {

	public static void main(String[] args) throws FileNotFoundException, SQLException {
		testWeightedOverlap();
	}

	public static void testWeightedOverlap() throws FileNotFoundException, SQLException {

		long startTime = System.nanoTime();

		// DB connection
		MySqlConnection dbObject = new MySqlConnection();
		Connection conn = dbObject.AbrirConexion();
		ArrayList<Triple> loteTriples = dbObject.getBatchTitlesFromDB(conn);
		ArrayList<Triple> triples = dbObject.getTitlesFromDB(conn);
		ArrayList<Thread> threads = new ArrayList<Thread>();

		final ArrayList<String> cons = new ArrayList<String>();

		Integer nThreads = 8;

		int size = 1328 / nThreads; // sublist size
		for (final Triple triple : loteTriples) {
			System.out.println("Iniciando comparación: " + (loteTriples.indexOf(triple) + 1));
			long comparisonStartTime = System.nanoTime();
			for (int start = 0; start < triples.size(); start += size) {
				int end = Math.min(start + size, triples.size());
				// System.out.println("Start: " + start);
				// System.out.println("End " + end);
				final List<Triple> sublist = triples.subList(start, end);

				System.out.println("Tamaño lista: " + sublist.size());

				Thread thread = new Thread() {
					/*
					 * (non-Javadoc)
					 * 
					 * @see java.lang.Thread#run()
					 */
					public void run() {
						for (Triple tr : sublist) {
							ItemType text1Type = ItemType.SURFACE;
							ItemType text2Type = ItemType.SURFACE;
							ADW pipeLine = new ADW();
							DisambiguationMethod disMethod = DisambiguationMethod.ALIGNMENT_BASED;
							SignatureComparison measure = new WeightedOverlap();
							double score;
							do {
								score = pipeLine.getPairSimilarity(triple.getO(), tr.getO(), disMethod, measure,
										text1Type, text2Type);
							} while (score == 0);

							String query = "INSERT INTO subjComparisonNew (codeX, subjectX, codeY, subjectY, score) VALUES (\""
									+ triple.getS() + "\", \"" + triple.getO() + "\", \"" + tr.getS() + "\", \""
									+ tr.getO() + "\", \"" + score + "\")";

							cons.add(query);
						}
					}
				};
				threads.add(thread);
			}
			try {
				for (Thread thread : threads) {
					thread.start();
					System.out.println("Iniciando hilo");
				}
				for (Thread thread : threads) {
					thread.join();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			long comparisonEndTime = System.nanoTime();
			long comparisonElapsedTime = comparisonEndTime - comparisonStartTime;
			double comparisonTime = (double) comparisonElapsedTime / 1000000000.0;

			System.out.println("ComparisonTime " + (loteTriples.indexOf(triple) + 1) + ": " + comparisonTime + " s\n");
			dbObject.store(conn, cons);
			threads.clear();
			cons.clear();
		}

		System.out.println("done!");

		long endTime = System.nanoTime();
		long elapsedTime = endTime - startTime;
		double minutes = (double) (elapsedTime / 1000000000.0) / 60;

		System.out.println("Took " + minutes + " min");

	}

	public static void correccionTraduccion() {

		MySqlConnection dbObject = new MySqlConnection();
		Connection conn = dbObject.AbrirConexion();
		ArrayList<Triple> titles = dbObject.getTitlesFromDB(conn);
		ArrayList<Triple> correccionTraduccion = dbObject.getCorreccionTraduccionFromDB(conn);

		for (Triple title : titles) {
			for (Triple traduction : correccionTraduccion) {
				if (title.getO().contains(traduction.getS())) {
					dbObject.updateDataToDB(conn,
							title.getO().replaceAll("\\b" + traduction.getS() + "\\b", traduction.getO()),
							String.valueOf(title.getId()));
				}
			}
		}
	}
}
