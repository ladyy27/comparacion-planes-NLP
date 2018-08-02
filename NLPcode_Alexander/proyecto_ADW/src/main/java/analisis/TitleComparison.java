package analisis;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;

import it.uniroma1.lcl.adw.ADW;
import it.uniroma1.lcl.adw.DisambiguationMethod;
import it.uniroma1.lcl.adw.ItemType;
import it.uniroma1.lcl.adw.comparison.Cosine;
import it.uniroma1.lcl.adw.comparison.Jaccard;
import it.uniroma1.lcl.adw.comparison.SignatureComparison;
import it.uniroma1.lcl.adw.comparison.WeightedOverlap;

public class TitleComparison {
	public static void main(String[] args) throws FileNotFoundException {
		testWeightedOverlap();
	}

	public static void testWeightedOverlap() throws FileNotFoundException {

		// DB connection
		MySqlConnection dbObject = new MySqlConnection();
		Connection conn = dbObject.AbrirConexion();
		ArrayList<Triple> triples = dbObject.getTriplesFromDB(conn);

		ADW pipeLine = new ADW();

		String text1 = "A manager fired the worker.";
		ItemType text1Type = ItemType.SURFACE;

		// String text2 = "windmill#n rotate#v wind#n";
		// ItemType text2Type = ItemType.SURFACE_TAGGED;

		String text2 = "An employee was terminated from work by his boss.";
		ItemType text2Type = ItemType.SURFACE;

		// if lexical items has to be disambiguated
		DisambiguationMethod disMethod = DisambiguationMethod.ALIGNMENT_BASED;

		// measure for comparing semantic signatures
		SignatureComparison measure = new WeightedOverlap();
		measure.toString();
		// SignatureComparison measure2 = new Cosine();
		// SignatureComparison measure3 = new Jaccard();
		
		PrintWriter pw = new PrintWriter(new File("testCrossAll.csv"));
		StringBuilder sb = new StringBuilder();
		sb.append("x");
		sb.append(';');
		
		for (Triple triple : triples) {
			sb.append("[" + triple.getId() + "] " + triple.getO());
			sb.append(';');
		}
		
		sb.append('\n');

		int cont = 1;

		for (Triple triple1 : triples) {
			sb.append("[" + triple1.getId() + "] " + triple1.getO());
			sb.append(';');
			System.out.println("Comparación " + cont);
			for (Triple triple2 : triples) {
				text1 = triple1.getO();
				text2 = triple2.getO();
				// System.out.println("id Triple:"+ triple1.getId());

				double score = pipeLine.getPairSimilarity(text1, text2, disMethod, measure, text1Type, text2Type);

				// System.out.println("score weightedOverlap = " + score);
				System.out.printf("\n[%s] %s - [%s] %s, score: \t %.2f", triple1.getId(), text1, triple2.getId(), text2,
						score);
				
				String num = String.format("%.2f", score);
				
				sb.append(num);
				sb.append(';');
			}
			sb.append('\n');
			cont++;
			System.out.println();
		}

		pw.write(sb.toString());
		pw.close();
		System.out.println("done!");

	}
}
