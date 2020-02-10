package com.excilys.computerDatabase.daos;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excilys.computerDatabase.exceptions.Logging;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
@Component
public class DaoFactoryHikary {

	private static Connection connect;
	private static DaoFactoryHikary instance;

	private static HikariConfig hikariConfig; 
	private static HikariConfig hikariConfigTest; 
    private static HikariDataSource dataSource;
    static {
    	hikariConfig=new HikariConfig("/datasource.properties");
    	hikariConfigTest=new HikariConfig("/datasource.properties");
    	dataSource= new HikariDataSource(hikariConfig);
    }
    
	private DaoFactoryHikary() { }

	public Connection getConnection() {
		try {
			connect = dataSource.getConnection();
		} catch (SQLException sqlException) {
			for (Throwable e : sqlException) {
				Logging.afficherMessageError("Problèmes rencontrés: " + e);
			}

		}
		return connect;
	}

	public static DaoFactoryHikary getInstance() {
		if (instance == null) {
			instance = new DaoFactoryHikary();
		}
		return instance;
	}

	public static Connection disconnect() {
		if (connect != null) {
			try {
				connect.close();
			} catch (SQLException sqlException) {
				for (Throwable e : sqlException) {
					Logging.afficherMessageError("Problèmes rencontrés: " + e);
				}
			}
		}
		return connect;
	}
}


