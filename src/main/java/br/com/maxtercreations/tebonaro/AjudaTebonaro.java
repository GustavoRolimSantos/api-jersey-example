package br.com.maxtercreations.tebonaro;

import br.com.maxtercreations.tebonaro.manager.Manager;

public class AjudaTebonaro {

	private static Manager manager = new Manager();
	
	public static Manager getManager() {
		return manager;
	}

}
