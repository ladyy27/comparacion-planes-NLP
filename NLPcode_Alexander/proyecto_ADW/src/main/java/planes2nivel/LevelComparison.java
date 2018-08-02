package planes2nivel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class LevelComparison {

	public double getLevelScore(String title1, String title2) throws SQLException {
		return similarityComparison(analize(title1), analize(title2), maxLevel());
	}

	// public static void main(String[] args) throws FileNotFoundException,
	// SQLException {
	//
	// String titulo1 = "Cálculo";
	// String titulo2 = "Cálculo";
	//
	// double comparison = similarityComparison(analize(titulo1),
	// analize(titulo2), maxLevel());
	// System.out.println("Porcentaje de comparación: " + comparison);
	//
	// }

	public static Integer maxLevel() throws SQLException {
		LevelList levelList = new LevelList();
		ArrayList<Triple> triples = levelList.getLevelList();

		Integer maxLevel = 0;

		for (Triple triple : triples) {
			if (Integer.parseInt(triple.getS()) > maxLevel) {
				maxLevel = Integer.parseInt(triple.getS());
			}
		}

		return maxLevel;
	}

	public static Integer analize(String value) throws SQLException {
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

		Integer level = 0;

		Annotation document = new Annotation(value);
		pipeline.annotate(document);

		List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);

		for (CoreMap sentence : sentences) {
			for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
				String word = token.get(CoreAnnotations.TextAnnotation.class);
				level = level(word);
			}
		}
		return level;
	}

	public static Integer level(String potencialNivel) throws SQLException {
		LevelList levelList = new LevelList();
		ArrayList<Triple> levels = levelList.getLevelList();

		for (Triple level : levels) {
			if (level.getO().equals(potencialNivel)) {
				System.out.println("Nivel encontrado: " + level.getS());
				return Integer.parseInt(level.getS());
			}
		}
		return 0;
	}

	public static double similarityComparison(Integer nivel1, Integer nivel2, Integer maxLevel) {
		MySqlConnection dbObject = new MySqlConnection();
		Connection conn = dbObject.AbrirConexion();
		ArrayList<Triple> distances = dbObject.getLevelDistanceFromDB(conn); // obtener
																				// distancias

		double distancia = (double) Math.abs(nivel1 - nivel2);
		
		if ((nivel1.equals(0) | nivel2.equals(0)) && (nivel1.equals(1) | nivel2.equals(1))) {
			distancia -= 0.5; // distancia de 0-1 o 1-0 es 0.5
		}

		System.out.println("Distancia: " + distancia);
		double porcSimilitud = 0;

		for (Triple distance : distances) {
			double d = Double.parseDouble(distance.getS());
			if (d == distancia) {
				porcSimilitud = Double.parseDouble(distance.getO());
				System.out.println("Porcentaje: " + porcSimilitud);
			}
		}

		// return 1 - (double) (Math.abs(nivel1 - nivel2)) / maxLevel;
		return porcSimilitud;
	}

}
