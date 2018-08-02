package planes0comparacion;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import it.uniroma1.lcl.adw.ADW;
import it.uniroma1.lcl.adw.DisambiguationMethod;
import it.uniroma1.lcl.adw.ItemType;
import it.uniroma1.lcl.adw.comparison.SignatureComparison;
import it.uniroma1.lcl.adw.comparison.WeightedOverlap;

public class TopicComparison {

	public double getTopicScore(String topics1, String topics2) throws SQLException, FileNotFoundException {
		return topicsSimilarityComparison(topics1, topics2);
	}

	// public static void main(String[] args) throws FileNotFoundException,
	// SQLException {
	// testWeightedOverlap();
	// }

	public static double topicsSimilarityComparison(String topics1, String topics2) throws FileNotFoundException, SQLException {

		// DB connection
//		MySqlConnection dbObject = new MySqlConnection();
//		Connection conn = dbObject.AbrirConexion();
//		ArrayList<Triple> triples = dbObject.getTitlesFromDB(conn);

		ADW pipeLine = new ADW();

		String text1 = topics1;
		ItemType text1Type = ItemType.SURFACE;

		String text2 = topics2;
		ItemType text2Type = ItemType.SURFACE;

		// if lexical items has to be disambiguated
		DisambiguationMethod disMethod = DisambiguationMethod.ALIGNMENT_BASED;

		// measure for comparing semantic signatures
		SignatureComparison measure = new WeightedOverlap();
		measure.toString();

		double score = pipeLine.getPairSimilarity(text1, text2, disMethod, measure, text1Type, text2Type);

//		System.out.printf("\n[%s] %s - [%s] %s, scorºe: \t %.2f \n", triple1.getId(), text1, triple2.getId(), text2,
//				score);
		
		return score;

	}
}
