package br.com.maxtercreations.tebonaro.manager;

import br.com.maxtercreations.tebonaro.mysql.MySQLManager;

public class Manager {

	private MySQLManager mysqlManager;
	
	public Manager() {
		mysqlManager = new MySQLManager();
	}
	
	public MySQLManager getMySQLManager() {
		return mysqlManager;
	}
	
}
