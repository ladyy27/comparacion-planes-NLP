package planes0comparacion;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class GeneralComparison {
	public static void main(String[] args) throws FileNotFoundException, SQLException {
		String codigo1 = "CURSO_PRE-TNII002_20";
//		String codigo2 = "CURSO_PRE-TNII002_20";
		
		MySqlConnection dbObject = new MySqlConnection();
		Connection conn = dbObject.AbrirConexion();
		ArrayList<Triple> codigos = dbObject.getTitlesFromDB(conn);
		
		for (Triple c : codigos) {
			
			String title1 = getTitle(codigo1);
			String title2 = getTitle(c.getS());
			
			TitleComparison titleComparison = new TitleComparison();
			double titleScore = titleComparison.getTitleScore(title1, title2);
			System.out.println("Similitud de Títulos: " + titleScore);
			
			TopicComparison topicsComparison = new TopicComparison();
			double topicsScore = topicsComparison.getTopicScore(getTopics(codigo1), getTopics(c.getS()));
			System.out.println("Similitud de Topicos: " + topicsScore);
			
			double totalScore = titleScore * 0.5 + topicsScore * 0.5;
			
			System.out.println("Comparison Score de Planes: " + totalScore);
			
			dbObject.storePlansComparison(conn, codigo1, title1, c.getS(), title2, titleScore, topicsScore, totalScore);
		}
		
//		String title1 = getTitle(codigo1);
//		String title2 = getTitle(codigo2);
//		
//		TitleComparison titleComparison = new TitleComparison();
//		double titleScore = titleComparison.getTitleScore(title1, title2);
//		System.out.println("Similitud de Títulos: " + titleScore);
//		
//		TopicComparison topicsComparison = new TopicComparison();
//		double topicsScore = topicsComparison.getTopicScore(getTopics(codigo1), getTopics(codigo2));
//		System.out.println("Similitud de Topicos: " + topicsScore);
//		
//		double totalScore = titleScore * 0.5 + topicsScore * 0.5;
//		
//		System.out.println("Comparison Score de Planes: " + totalScore);
//		
//		MySqlConnection dbObject = new MySqlConnection();
//		Connection conn = dbObject.AbrirConexion();
//		dbObject.storePlansComparison(conn, codigo1, title1, codigo2, title2, titleScore, topicsScore, totalScore);
	}
	
	public static String getTitle(String code) {
		MySqlConnection dbObject = new MySqlConnection();
		Connection conn = dbObject.AbrirConexion();
		String title = dbObject.getTitle(conn, code);
		System.out.println("Titulo: " + title);
		return title;
	}
	
	public static String getTopics(String code) {
		MySqlConnection dbObject = new MySqlConnection();
		Connection conn = dbObject.AbrirConexion();
		String topics = dbObject.getTopics(conn, code);
		System.out.println("Topicos: " + topics);
		return topics;
	}
}
