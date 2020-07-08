package br.com.maxtercreations.tebonaro.mysql;

public class MySQLManager {

	private MySQL mysql;

	public MySQLManager() {
		mysql = new MySQL("localhost", "restapi", "root", "");


		mysql.openConnection();
	}

	public MySQL getMySQL() {
		return mysql;
	}

}
