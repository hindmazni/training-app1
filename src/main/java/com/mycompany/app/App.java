

/**
 * Hello world!
 *
 */
package com.mycompany.app;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class App 
{
static Connection conn = null;
	static PreparedStatement statement = null;

	private static void makeJDBCConnection() {
 
		try {
			Class.forName("com.mysql.jdbc.Driver");
			log("Le driver JDBC pour MySQL est disponible.");
		} catch (ClassNotFoundException e) {
			log("Le driver JDBC pour MySQL n'est pas disponible! Verifier que vous avez bien ajout� votre dependance Maven dans le POM!");
			e.printStackTrace();
			return;
		}
 
		try {
			// Charger le JDBC driver pour MYSQL.
			conn = DriverManager.getConnection("jdbc:mysql://localhost/training", "root", "");
			if (conn != null) {
				log("Connexion � la base de donn�es a �t� �tablie avec succ�s.");
			} else {
				log("Probl�me de connexion � la base!");
			}
		} catch (SQLException e) {
			log("Connexion au MySQL n'est pas �tablie!");
			e.printStackTrace();
			return;
		}
 
	}

 
	private static void getDataFromDB() {
 
		try {
			// Requete Select MySQL
			String getQueryStatement = "SELECT * FROM session";
 
			statement = conn.prepareStatement(getQueryStatement);
 
			// Executer la requete
			ResultSet rs = statement.executeQuery();
 
			// Parser le resultat
			while (rs.next()) {
				String name = rs.getString("name");
				String track = rs.getString("track");
				String address = rs.getString("adress");
				String date = rs.getString("date_session");
				int nb_participants = rs.getInt("nb_participants");
				int iscompleted = rs.getInt("iscomplete");
				
 
				// Afficher le r�sultat
				System.out.format("%s, %s, %s, %s, %d, %d\n", name, track, address, date, nb_participants, iscompleted);
			}
 
		} catch (
 
		SQLException e) {
			e.printStackTrace();
		}
 
	}
 
	private static void log(String string) {
		System.out.println(string);
 
	}

	public static void main(String[] argv) {
 
		try {
			log("-------- Connexion au serveur de donn�es MYSQL ------------");
			makeJDBCConnection();
			log("-------- Afficher toutes les sessions de formations ------------");
			getDataFromDB();

			statement.close();
			conn.close(); // Fermer la connexion
 
		} catch (SQLException e) {
 
			e.printStackTrace();
		}
	}
}
