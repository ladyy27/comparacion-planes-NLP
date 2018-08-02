package planes1titulo;

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

	public void updateDataToDB(Connection conn, String o_en, String id) {

		try {
			PreparedStatement connPrepareStat = null;
			String insertQueryStatement = "UPDATE planes SET o_en=? WHERE id=? ";

			connPrepareStat = conn.prepareStatement(insertQueryStatement);
			connPrepareStat.setString(1, o_en);
			connPrepareStat.setString(2, id);

			// execute insert SQL statement
			connPrepareStat.executeUpdate();
			// log("Data added successfully");
			connPrepareStat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// public void getDataFromDB(Connection conn) {
	// PreparedStatement connPrepareStat = null;
	//
	// try {
	//
	// // MySQL Select Query Tutorial
	// String getQueryStatement = "SELECT * FROM planesTripletas";
	//
	// connPrepareStat = conn.prepareStatement(getQueryStatement);
	//
	// // Execute the Query, and get a java ResultSet
	// ResultSet rs = connPrepareStat.executeQuery();
	//
	// // Let's iterate through the java ResultSet
	// while (rs.next()) {
	// String sujeto = rs.getString("s");
	// String predicado = rs.getString("p");
	// String objeto = rs.getString("o");
	//
	// // Simply Print the results
	// // System.out.format("%s, %s, %s\n", sujeto, predicado, objeto);
	// }
	// connPrepareStat.close();
	//
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	//
	// }

	public void cleanResultsDB(Connection conn) {
		PreparedStatement connPrepareStat = null;

		try {

			// MySQL Select Query Tutorial
			String getQueryStatement = "TRUNCATE TABLE subjComparisonNew";

			connPrepareStat = conn.prepareStatement(getQueryStatement);

			// Execute the Query, and get a java ResultSet
			connPrepareStat.executeUpdate();
			connPrepareStat.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Triple getTitleFromDB(Connection conn, String code) {
		PreparedStatement connPrepareStat = null;

		Triple triple = null;

		try {

			// MySQL Select Query Tutorial
			// String getQueryStatement = "select * from planesCalculo where
			// p=\"asignatura\"";
			String getQueryStatement = "select * from planes where s like \"%" + code + "%\" and p=\"asignatura\"";

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
			}
			connPrepareStat.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return triple;
	}

	public ArrayList<Triple> getTitlesFromDB(Connection conn) {
		PreparedStatement connPrepareStat = null;

		Triple triple;
		ArrayList<Triple> triples = new ArrayList<Triple>();

		try {

			// MySQL Select Query Tutorial
			// String getQueryStatement = "select * from planesCalculo where
			// p=\"asignatura\"";
			String getQueryStatement = "select * from planes where p=\"asignatura\"";

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

	public ArrayList<Triple> getBatchTitlesFromDB(Connection conn) {
		PreparedStatement connPrepareStat = null;

		Triple triple;
		ArrayList<Triple> triples = new ArrayList<Triple>();

		try {

			// MySQL Select Query Tutorial
			// String getQueryStatement = "select * from planesCalculo where
			// p=\"asignatura\"";
			// String getQueryStatement = "select * from planes where
			// p=\"asignatura\" limit 100";
			// String getQueryStatement = "select * from planes where
			// p=\"asignatura\" limit 100, 100";
			// String getQueryStatement = "select * from planes where
			// p=\"asignatura\" limit 200, 100";
			// String getQueryStatement = "select * from planes where

			// String getQueryStatement = "select * from planes where
			// p=\"asignatura\" limit 599, 1";
			String getQueryStatement = "select * from planes where p=\"asignatura\"";
			// String getQueryStatement = "select * from planes where
			// p=\"asignatura\" limit 600, 300";
			// String getQueryStatement = "select * from planes where
			// p=\"asignatura\" limit 400, 500";

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

	public ArrayList<Triple> getBatchTitlesFromDB1(Connection conn) {
		PreparedStatement connPrepareStat = null;

		Triple triple;
		ArrayList<Triple> triples = new ArrayList<Triple>();

		try {

			// MySQL Select Query Tutorial
			// String getQueryStatement = "select * from planesCalculo where
			// p=\"asignatura\"";
			// String getQueryStatement = "select * from planes where
			// p=\"asignatura\" limit 100";
			// String getQueryStatement = "select * from planes where
			// p=\"asignatura\" limit 340, 260";
			String getQueryStatement = "select * from planes where p=\"asignatura\" limit 1013, 315";
			// String getQueryStatement = "select * from planes where
			// p=\"asignatura\" limit 200, 100";

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

	public ArrayList<Triple> getCorreccionTraduccionFromDB(Connection conn) {
		PreparedStatement connPrepareStat = null;

		Triple triple;
		ArrayList<Triple> triples = new ArrayList<Triple>();

		try {

			// MySQL Select Query Tutorial
			// String getQueryStatement = "select * from planesCalculo where
			// p=\"asignatura\"";
			String getQueryStatement = "select * from correcionTraduccion";

			connPrepareStat = conn.prepareStatement(getQueryStatement);

			// Execute the Query, and get a java ResultSet
			ResultSet rs = connPrepareStat.executeQuery();

			// Let's iterate through the java ResultSet
			while (rs.next()) {
				triple = new Triple();
				triple.setId(Integer.parseInt(rs.getString("id")));
				triple.setS(rs.getString("valor"));
				triple.setP("traduccion");
				triple.setO(rs.getString("valorTraduccion"));

				triples.add(triple);

			}
			connPrepareStat.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return triples;
	}

	public void storeTitlesComparisonToDB(Connection conn, String codeX, String subjectX, String codeY, String subjectY,
			Double score) {

		try {
			PreparedStatement connPrepareStat = null;
			// String insertQueryStatement = "INSERT planes SET o_en=? WHERE
			// id=? ";
			String insertQueryStatement = "INSERT INTO titlesComparison (codeX, subjectX, codeY, subjectY, score) VALUES (?,?,?,?,?)";

			connPrepareStat = conn.prepareStatement(insertQueryStatement);
			connPrepareStat.setString(1, codeX);
			connPrepareStat.setString(2, subjectX);
			connPrepareStat.setString(3, codeY);
			connPrepareStat.setString(4, subjectY);
			connPrepareStat.setString(5, String.valueOf(score));

			// execute insert SQL statement
			connPrepareStat.executeUpdate();
			// log("Data added successfully");
			connPrepareStat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void store(Connection conn, ArrayList<String> cons) {

		try {
			PreparedStatement connPrepareStat = null;

			for (String query : cons) {
				connPrepareStat = conn.prepareStatement(query);
				connPrepareStat.executeUpdate();
			}

			// execute insert SQL statement
			connPrepareStat.close();
			// log("Data added successfully");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void storeTitlesComparisonWithLevelToDB(Connection conn, String codeX, String subjectX, String codeY,
			String subjectY, Double score) {

		try {
			PreparedStatement connPrepareStat = null;
			// String insertQueryStatement = "INSERT planes SET o_en=? WHERE
			// id=? ";
			String insertQueryStatement = "INSERT INTO titlesComparisonWithLevel (codeX, subjectX, codeY, subjectY, score) VALUES (?,?,?,?,?)";

			connPrepareStat = conn.prepareStatement(insertQueryStatement);
			connPrepareStat.setString(1, codeX);
			connPrepareStat.setString(2, subjectX);
			connPrepareStat.setString(3, codeY);
			connPrepareStat.setString(4, subjectY);
			connPrepareStat.setString(5, String.valueOf(score));

			// execute insert SQL statement
			connPrepareStat.executeUpdate();
			// log("Data added successfully");
			connPrepareStat.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// public ArrayList<SubjectWithContext> getSubjectsFromDB(Connection conn) {
	// PreparedStatement connPrepareStat = null;
	//
	// SubjectWithContext subject;
	// ArrayList<SubjectWithContext> subjects = new
	// ArrayList<SubjectWithContext>();
	//
	// try {
	//
	// // MySQL Select Query Tutorial
	// String getQueryStatement = "select * from planesCalculo where
	// (p=\"asignatura\" or p=\"responsable\")";
	//
	// connPrepareStat = conn.prepareStatement(getQueryStatement);
	//
	// // Execute the Query, and get a java ResultSet
	// ResultSet rs = connPrepareStat.executeQuery();
	//
	// // Let's iterate through the java ResultSet
	// while (rs.next()) {
	// subject = new SubjectWithContext();
	// subject.setCode(rs.getString("s"));
	//
	// if (rs.getString("p").equals("asignatura") && subject.getSubject() != "")
	// {
	// subject.setSubject(rs.getString("o_en"));
	// subjects.add(subject);
	// }
	//
	// if (rs.getString("p").equals("responsable") && subject.getManager() !=
	// "") {
	//// subject.setManager(rs.getString("o_en"));
	// subjects.get(subjects.size() - 1).setManager(rs.getString("o_en"));
	// }
	//
	// System.out.println("Código: " + subject.getCode());
	// System.out.println("Materia: " + subject.getSubject());
	// System.out.println("Responsable: " + subject.getManager());
	//
	// }
	// connPrepareStat.close();
	//
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	//
	// return subjects;
	// }

	// public ArrayList<Sentence> getSentenceFromDB(Connection conn) {
	// PreparedStatement connPrepareStat = null;
	//
	// Sentence sentence;
	// ArrayList<Sentence> sentences = new ArrayList<Sentence>();
	//
	// try {
	//
	// // MySQL Select Query Tutorial
	// String getQueryStatement = "select * from planes where p=\"asignatura\"
	// or p=\"texto_numero\" or p=\"texto_limpio\" or p=\"resultado\"";
	//
	// // String getQueryStatement = "select * from planes where id=147";
	// connPrepareStat = conn.prepareStatement(getQueryStatement);
	//
	// // Execute the Query, and get a java ResultSet
	// ResultSet rs = connPrepareStat.executeQuery();
	//
	// // Let's iterate through the java ResultSet
	// while (rs.next()) {
	// sentence = new Sentence();
	// sentence.setId(Integer.parseInt(rs.getString("id")));
	// sentence.setWord(rs.getString("o"));
	//
	// sentences.add(sentence);
	// }
	// connPrepareStat.close();
	//
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	//
	// return sentences;
	// }

	// Simple log utility
	private static void log(String string) {
		System.out.println(string);

	}

}
