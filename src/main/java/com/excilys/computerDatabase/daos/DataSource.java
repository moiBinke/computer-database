package com.excilys.computerDatabase.daos;

import com.zaxxer.hikari.HikariConfig;

public class DataSource {

	private static HikariConfig instance;
	
	static {
		instance = new HikariConfig("datasource.properties" );
	}

	
	public static HikariConfig getInstance() {
		
		return instance;
	}
	
}