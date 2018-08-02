package planes0comparacion;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import it.uniroma1.lcl.adw.ADW;
import it.uniroma1.lcl.adw.DisambiguationMethod;
import it.uniroma1.lcl.adw.ItemType;
import it.uniroma1.lcl.adw.comparison.SignatureComparison;
import it.uniroma1.lcl.adw.comparison.WeightedOverlap;
import planes2nivel.LevelComparison;

public class TitleComparison {

	public double getTitleScore(String title1, String title2) throws SQLException, FileNotFoundException {
		return titleSimilarityComparison(title1, title2);
	}

	// public static void main(String[] args) throws FileNotFoundException,
	// SQLException {
	// testWeightedOverlap();
	// }

	public static double titleSimilarityComparison(String title1, String title2) throws FileNotFoundException, SQLException {

		// DB connection
//		MySqlConnection dbObject = new MySqlConnection();
//		Connection conn = dbObject.AbrirConexion();
//		ArrayList<Triple> triples = dbObject.getTitlesFromDB(conn);

		ADW pipeLine = new ADW();

		String text1 = title1;
		ItemType text1Type = ItemType.SURFACE;

		String text2 = title2;
		ItemType text2Type = ItemType.SURFACE;

		// if lexical items has to be disambiguated
		DisambiguationMethod disMethod = DisambiguationMethod.ALIGNMENT_BASED;

		// measure for comparing semantic signatures
		SignatureComparison measure = new WeightedOverlap();
		measure.toString();

		double score = pipeLine.getPairSimilarity(text1, text2, disMethod, measure, text1Type, text2Type);

//		System.out.printf("\n[%s] %s - [%s] %s, scorºe: \t %.2f \n", triple1.getId(), text1, triple2.getId(), text2,
//				score);

		String num = String.format("%.2f", score);

		// si materias son iguales
		if (num.contains("1.")) {
			System.out.println("SOY UNO");
			LevelComparison levelComparison = new LevelComparison();
			double levelScore = levelComparison.getLevelScore(text1, text2);
			score = (score * 0.5) + (levelScore * 0.5);
//			num = String.format("%.2f", score);
		}
		
		return score;

	}
}
