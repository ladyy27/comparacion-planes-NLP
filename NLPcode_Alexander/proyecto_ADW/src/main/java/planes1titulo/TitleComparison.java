package planes1titulo;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import it.uniroma1.lcl.adw.ADW;
import it.uniroma1.lcl.adw.DisambiguationMethod;
import it.uniroma1.lcl.adw.ItemType;
import it.uniroma1.lcl.adw.comparison.SignatureComparison;
import it.uniroma1.lcl.adw.comparison.WeightedOverlap;

public class TitleComparison {
	
	public static void main(String[] args) throws FileNotFoundException, SQLException {
		testWeightedOverlap();
	}

	public static void testWeightedOverlap() throws FileNotFoundException, SQLException {
		
//		correccionTraduccion();

		// DB connection
		MySqlConnection dbObject = new MySqlConnection();
		Connection conn = dbObject.AbrirConexion();
		
		ArrayList<Triple> loteTriples = dbObject.getBatchTitlesFromDB(conn);
		ArrayList<Triple> triples = dbObject.getTitlesFromDB(conn);

		ADW pipeLine = new ADW();

		String text1 = "A manager fired the worker.";
		ItemType text1Type = ItemType.SURFACE;

		String text2 = "An employee was terminated from work by his boss.";
		ItemType text2Type = ItemType.SURFACE;

		DisambiguationMethod disMethod = DisambiguationMethod.ALIGNMENT_BASED;

		SignatureComparison measure = new WeightedOverlap();
		measure.toString();

		int cont = 1;

		for (Triple triple1 : loteTriples) {
			System.out.println("Comparación " + cont);
			for (Triple triple2 : triples) {
				text1 = triple1.getO();
				text2 = triple2.getO();
				// System.out.println("id Triple:"+ triple1.getId());
				System.out.println(triple1.getO());
				System.out.println(triple2.getO());

				double score = pipeLine.getPairSimilarity(text1, text2, disMethod, measure, text1Type, text2Type);

				System.out.printf("\n[%s] %s - [%s] %s, scorºe: \t %.2f \n", triple1.getId(), text1, triple2.getId(), text2,
						score);
				
				dbObject.storeTitlesComparisonToDB(conn, triple1.getS(), triple1.getO(), triple2.getS(), triple2.getO(), score);
				
				String num = String.format("%.2f", score);
				
			}
			cont++;
			System.out.println();
		}

		System.out.println("done!");

	}
	
	public static void correccionTraduccion() {
		
		MySqlConnection dbObject = new MySqlConnection();
		Connection conn = dbObject.AbrirConexion();
		ArrayList<Triple> titles = dbObject.getTitlesFromDB(conn);
		ArrayList<Triple> correccionTraduccion = dbObject.getCorreccionTraduccionFromDB(conn);
		
		for (Triple title : titles) {
			for (Triple traduction : correccionTraduccion) {
				if (title.getO().contains(traduction.getS())) {
					dbObject.updateDataToDB(conn, title.getO().replaceAll("\\b" + traduction.getS() + "\\b", traduction.getO()), String.valueOf(title.getId()));
				}
			}
		}
	}
}
