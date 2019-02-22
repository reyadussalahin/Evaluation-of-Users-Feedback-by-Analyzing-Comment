

/*
	Class: Database
	Description: Creates connection to execute sql command and supports various easy ways to update and query
				 word column of every table

	Author: Md. Reyadus Salahin (CSE, CUET)
	
*/

package myjava;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

public class Database {
	private Connection connection;
	private Statement statement;
	private String url;
	private String user;
	private String pass;

	public Database(String u, String p) throws SQLException {
		url = "jdbc:mysql://localhost/project_feedback?useSSL=false&useUnicode=true&characterEncoding=utf-8";
		user = u;
		pass = p;
		connection = DriverManager.getConnection(url, user, pass);
		statement = connection.createStatement();
	}

	public boolean containsWord(String word, String table) throws SQLException {
		String cmd = "select word from " + table + " where word like " + "\""+ word + "\"";
		ResultSet rs = statement.executeQuery(cmd);
		return rs.next();
	}

	public String[] getWordList(String table) throws SQLException {
		String cmd = "select word from " + table;
		ResultSet rs = statement.executeQuery(cmd);
		List<String> al = new ArrayList<String>();
		while(rs.next()) {
			al.add(rs.getString("word"));
		}
		String[] a = new String[al.size()];
		a = al.toArray(a);
		return a;
	}

	public int getKeyValue(String key) throws SQLException {
		String cmd = "select val from keyword where word = " + "\"" + key + "\"";
		ResultSet rs = statement.executeQuery(cmd);
		if(!rs.next()) return 0;
		return rs.getInt("val");
	}

	public boolean addWord(String word, String table) throws SQLException {
		if(containsWord(word, table)) return false;
		String cmd = "insert into " + table + " values (" + "\"" + word + "\"" + ")";
		statement.executeUpdate(cmd);
		return true;
	}

	public boolean addKeyValue(String key, int value) throws SQLException {
		if(containsWord(key, "keyword")) return false;
		String cmd = "insert into keyword values (" + "\"" + key + "\"" + ", " + value + ")";
		statement.executeUpdate(cmd);
		return true;
	}

	public void close() throws SQLException {
		statement.close();
		connection.close();
	}
}
