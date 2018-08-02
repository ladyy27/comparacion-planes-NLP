package planes3topicoEtiquetado;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author alexanders0
 */
public class MySqlConnection {

	public void CerrarConexion(Connection conn) throws SQLException {
		conn.close(); // connection close
	}

	public Connection AbrirConexion() {
		Connection conn = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			log("Congrats - Seems your MySQL JDBC Driver Registered!");
		} catch (ClassNotFoundException e) {
			log("Sorry, couldn't found JDBC driver. Make sure you have added JDBC Maven Dependency Correctly");
			e.printStackTrace();
		}

		try {
			// DriverManager: The basic service for managing a set of JDBC
			// drivers.
			conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/nlp_db", "alexanders_0", "root");
			if (conn != null) {
				log("Connection Successful! Enjoy. Now it's time to push data");
			} else {
				log("Failed to make connection!");
			}
		} catch (SQLException e) {
			log("MySQL Connection Failed!");
			e.printStackTrace();
		}
		return conn;
	}

	public void addDataToDB(Connection conn, String sujeto, String predicado, String objeto) {

		try {
			PreparedStatement connPrepareStat = null;
			// String insertQueryStatement = "INSERT INTO planesTraduction
			// VALUES (?,?,?)";
			String insertQueryStatement = "INSERT  INTO  planesTraduction VALUES  (?,?,?)";

			connPrepareStat = conn.prepareStatement(insertQueryStatement);
			connPrepareStat.setString(1, sujeto);
			connPrepareStat.setString(2, predicado);
			connPrepareStat.setString(3, objeto);

			// execute insert SQL statement
			connPrepareStat.executeUpdate();
			// log("Data added successfully");
			connPrepareStat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateDataToDB(Connection conn, String skosUri, String skosName, String id) {

		try {
			PreparedStatement connPrepareStat = null;
			String insertQueryStatement = "UPDATE planes SET skos=?, skos_name=? WHERE id=? ";

			connPrepareStat = conn.prepareStatement(insertQueryStatement);
			connPrepareStat.setString(1, skosUri);
			connPrepareStat.setString(2, skosName);
			connPrepareStat.setString(3, id);

			// execute insert SQL statement
			connPrepareStat.executeUpdate();
			// log("Data added successfully");
			connPrepareStat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Triple> getTriplesFromDB(Connection conn) {
		PreparedStatement connPrepareStat = null;

		Triple triple;
		ArrayList<Triple> triples = new ArrayList<Triple>();

		try {

			// MySQL Select Query Tutorial
			String getQueryStatement = "select * from planes where p=\"asignatura\" and skos_name is null";
//			String getQueryStatement = "select * from planes where p=\"texto_topico\" and s like \"%CURSO_PRE-TNIQ004_20%\" limit 83, 1";
//			String getQueryStatement = "select * from planes where (p=\"asignatura\" or p=\"responsable\" or p=\"seccion\" or p=\"departamento\" or p=\"texto_topico\" or p=\"texto_limpio\" or p=\"resultado\") and skos is null and o_en is not null limit 25";
//			String getQueryStatement = "SELECT * FROM planes where ((p=\"asigantura\" or p=\"responsable\" or p=\"seccion\" or p=\"departamento\" or p=\"texto_topico\" or p=\"texto_limpio\" or p=\"resultado\")) and skos is not null and id>=3590 and id<=3714";
//			String getQueryStatement = "select * from planes where o like \"%-%\" and p=\"seccion\"";
			
			connPrepareStat = conn.prepareStatement(getQueryStatement);

			// Execute the Query, and get a java ResultSet
			ResultSet rs = connPrepareStat.executeQuery();

			// Let's iterate through the java ResultSet
			while (rs.next()) {
				triple = new Triple();
				triple.setId(Integer.parseInt(rs.getString("id")));
				triple.setS(rs.getString("s"));
				triple.setP(rs.getString("p"));
				triple.setO(rs.getString("o_en"));

				triples.add(triple);

			}
			connPrepareStat.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return triples;
	}

	public ArrayList<Triple> getUnescoTaxonomy(Connection conn) {
		PreparedStatement connPrepareStat = null;

		Triple triple;
		ArrayList<Triple> triples = new ArrayList<Triple>();

		try {

			// MySQL Select Query Tutorial
			String getQueryStatement = "select * from unescoTaxonomy";

			connPrepareStat = conn.prepareStatement(getQueryStatement);

			// Execute the Query, and get a java ResultSet
			ResultSet rs = connPrepareStat.executeQuery();

			// Let's iterate through the java ResultSet
			while (rs.next()) {
				triple = new Triple();
				triple.setId(Integer.parseInt(rs.getString("id")));
				triple.setS(rs.getString("conceptUri"));
				triple.setP("conceptName");
				triple.setO(rs.getString("conceptName"));

				triples.add(triple);

			}
			connPrepareStat.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return triples;
	}

	// Simple log utility
	private static void log(String string) {
		System.out.println(string);

	}

}
