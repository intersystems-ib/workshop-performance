package com.performance.datasource;

import java.io.IOException;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.postgresql.ds.PGSimpleDataSource;

import com.intersystems.jdbc.IRISDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;

public class MyDataSourceFactory {
    
    public static DataSource getMySQLDataSource(String username, String url, String password) {
		MysqlDataSource mysqlDS = null;
		try {
			mysqlDS = new MysqlDataSource();
			mysqlDS.setURL(url);
			mysqlDS.setUser(username);
			mysqlDS.setPassword(password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mysqlDS;
	}
	
	public static DataSource getPostgresDataSource(String username, String url, String password){
		PGSimpleDataSource postgresDS = null;
		try {			
			postgresDS = new PGSimpleDataSource();
			postgresDS.setURL(url);
			postgresDS.setUser(username);
			postgresDS.setPassword(password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return postgresDS;
	}

    public static DataSource getIrisDataSource(String username, String url, String password){
		IRISDataSource irisDS = null;
		try {			
			irisDS = new IRISDataSource();
			irisDS.setURL(url);
            // irisDS.setConnectionSecurityLevel(10);
			irisDS.setUser(username);
			irisDS.setPassword(password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return irisDS;
	}
}
