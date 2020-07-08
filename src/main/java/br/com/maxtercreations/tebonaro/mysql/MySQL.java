package br.com.maxtercreations.tebonaro.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class MySQL {

	private String username, password, host, database;
	private Connection mainConnection, slaveConnection;

	public MySQL(String host, String database, String username, String password) {
		this.username = username;
		this.password = password;
		this.host = host;
		this.database = database;
	}

	public Connection getConnection() {
		return mainConnection;
	}

	public boolean openConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			mainConnection = DriverManager.getConnection("jdbc:mysql://" + host + "/" + database + "?useTimezone=true&serverTimezone=UTC", username, password);
			slaveConnection = DriverManager.getConnection("jdbc:mysql://" + host + "/" + database + "?useTimezone=true&serverTimezone=UTC", username, password);
			return true;
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return false;
	}

	public synchronized void createTable(String table) {
		try {
			executeUpdate(table);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized ResultSet executeQuery(String query) {
		try {
			if (mainConnection.isClosed())
				System.out.println("The main connection is closed, therefore, is not possible to execute the query ('" + query + "').");
			
			PreparedStatement preparedStatement = mainConnection.prepareStatement(query);
			return preparedStatement.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public synchronized void executeQuery(String query,HashMap<Integer, Object> objects) {
		try {
			if (mainConnection.isClosed())
				System.out.println("The main connection is closed, therefore, is not possible to execute the query ('" + query + "').");
			
			PreparedStatement preparedStatement = mainConnection.prepareStatement(query);
			objects.forEach((key, value) -> {
				try {
					preparedStatement.setObject(key, value);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
			preparedStatement.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized void executeUpdate(String update) {
		try {
			PreparedStatement preparedStatement = slaveConnection.prepareStatement(update);
			preparedStatement.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
