package planes2nivel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class LevelList {
	
	public ArrayList<Triple> getLevelList () throws SQLException {
		
		MySqlConnection dbObject = new MySqlConnection();
		Connection conn = dbObject.AbrirConexion();
		ArrayList<Triple> triples = dbObject.getLevelsFromDB(conn);
		dbObject.CerrarConexion(conn);
		return triples;
	}

}
