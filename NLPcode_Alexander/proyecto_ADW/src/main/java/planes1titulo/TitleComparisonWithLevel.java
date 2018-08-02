package planes1titulo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import it.uniroma1.lcl.adw.ADW;
import it.uniroma1.lcl.adw.DisambiguationMethod;
import it.uniroma1.lcl.adw.ItemType;
import it.uniroma1.lcl.adw.comparison.SignatureComparison;
import it.uniroma1.lcl.adw.comparison.WeightedOverlap;
import planes2nivel.LevelComparison;

public class TitleComparisonWithLevel {
	
	public static void main(String[] args) throws FileNotFoundException, SQLException {
		testWeightedOverlap();
	}

	public static void testWeightedOverlap() throws FileNotFoundException, SQLException {
		
//		correccionTraduccion();

		// DB connection
		MySqlConnection dbObject = new MySqlConnection();
		Connection conn = dbObject.AbrirConexion();
		
		ArrayList<Triple> loteTriples = dbObject.getBatchTitlesFromDB1(conn);
		ArrayList<Triple> triples = dbObject.getTitlesFromDB(conn);

		ADW pipeLine = new ADW();

		String text1 = "A manager fired the worker.";
		ItemType text1Type = ItemType.SURFACE;

		String text2 = "An employee was terminated from work by his boss.";
		ItemType text2Type = ItemType.SURFACE;

		// if lexical items has to be disambiguated
		DisambiguationMethod disMethod = DisambiguationMethod.ALIGNMENT_BASED;

		// measure for comparing semantic signatures
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

				cont++;
				System.out.println();

				String num = String.format("%.2f", score);

				// si materias son iguales
				if (num.contains("1.")) {
					System.out.println("SOY UNO");
					LevelComparison levelComparison = new LevelComparison();
					double levelScore = levelComparison.getLevelScore(text1, text2);
					score = (score * 0.5) + (levelScore * 0.5);
					num = String.format("%.2f", score);
				}
				
				dbObject.storeTitlesComparisonWithLevelToDB(conn, triple1.getS(), triple1.getO(), triple2.getS(), triple2.getO(), score);
				
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
					dbObject.updateDataToDB(conn, title.getO().replace(traduction.getS(), traduction.getO()), String.valueOf(title.getId()));
				}
			}
		}
	}
}
