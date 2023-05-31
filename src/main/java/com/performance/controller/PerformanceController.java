package com.performance.controller;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.performance.datasource.MyDataSourceFactory;

@RestController
public class PerformanceController {

    @Value( "${spring.datasource.mysql.username}" )
	private String mysqlUsername;
	@Value( "${spring.datasource.mysql.password}" )
	private String mysqlPassword;
	@Value( "${spring.datasource.mysql.url}" )
	private String mysqlUrl;
    @Value( "${spring.datasource.postgres.username}" )
	private String postgresUsername;
	@Value( "${spring.datasource.postgres.password}" )
	private String postgresPassword;
	@Value( "${spring.datasource.postgres.url}" )
	private String postgresUrl;
    @Value( "${spring.datasource.iris.username}" )
	private String irisUsername;
	@Value( "${spring.datasource.iris.password}" )
	private String irisPassword;
	@Value( "${spring.datasource.iris.url}" )
	private String irisUrl;
    
    @GetMapping("/tests/insert/{db}")
    String insertRecords(@PathVariable ("db") String db, @RequestParam("total") Integer total) {
    
        DataSource ds = null;
        String insertQuery = "";
        int result = 0;
        switch (db) {
            case "mysql":
                ds = MyDataSourceFactory.getMySQLDataSource(mysqlUsername, mysqlUrl, mysqlPassword);
                insertQuery = "INSERT INTO test.patient VALUES (null, 'Name%d', 'LastName%d', null)";
                break;
            case "postgres":
                ds = MyDataSourceFactory.getPostgresDataSource(postgresUsername, postgresUrl, postgresPassword);
                insertQuery = "INSERT INTO test.patient (\"name\",\"lastname\") VALUES ('Name%d', 'LastName%d')";
                break;
            case "iris":
                ds = MyDataSourceFactory.getIrisDataSource(irisUsername, irisUrl, irisPassword);
                insertQuery = "INSERT INTO test.patient VALUES ('Name%d', 'LastName%d', null)";
                break;
        
            default:
                break;
        }
        try
        {
            Connection connection = ds.getConnection();
            Statement stmt = null;
		    // ResultSet rs = null;
            stmt = connection.createStatement();
            for (int i = 1; i <= total; i++ )
            {
                result += stmt.executeUpdate(String.format(insertQuery, i, i));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
         
        return "Records inserted: "+result;
    }

    @GetMapping("/tests/select/{db}")
    String selectRecords(@PathVariable ("db") String db) {
    
        DataSource ds = null;
        String selectQuery = "";
        int result = 0;
        selectQuery = "SELECT * FROM test.patient";
        switch (db) {
            case "mysql":
                ds = MyDataSourceFactory.getMySQLDataSource(mysqlUsername, mysqlUrl, mysqlPassword);            
                break;
            case "postgres":
                ds = MyDataSourceFactory.getPostgresDataSource(postgresUsername, postgresUrl, postgresPassword);
                break;
            case "iris":
                ds = MyDataSourceFactory.getIrisDataSource(irisUsername, irisUrl, irisPassword);
                break;
        
            default:
                break;
        }
        try
        {
            Connection connection = ds.getConnection();
            Statement stmt = null;
		    ResultSet rs = null;
            stmt = connection.createStatement();
            rs = stmt.executeQuery(selectQuery);
            while (rs.next()) {
                result++;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
         
        return "Total records: "+result;
    }

    @GetMapping("/tests/insertBlob/{db}")
    String insertBlobRecords(@PathVariable ("db") String db, @RequestParam("total") Integer total) {
    
        DataSource ds = null;
        String insertQuery = "";
        int recorded = 0;

        switch (db) {
            case "mysql":
                ds = MyDataSourceFactory.getMySQLDataSource(mysqlUsername, mysqlUrl, mysqlPassword);
                insertQuery = "INSERT INTO test.patient (Name, Lastname, Photo) VALUES (?, ?, ?)";
                break;
            case "postgres":
                ds = MyDataSourceFactory.getPostgresDataSource(postgresUsername, postgresUrl, postgresPassword);
                insertQuery = "INSERT INTO test.patient (\"name\",\"lastname\", \"photo\") VALUES (?, ?, ?)";
                break;
            case "iris":
                ds = MyDataSourceFactory.getIrisDataSource(irisUsername, irisUrl, irisPassword);
                insertQuery = "INSERT INTO test.patient VALUES (?, ?, ?)";
                break;
        
            default:
                break;
        }
        try
        {
            Connection connection = ds.getConnection();
            PreparedStatement pstmt = null;
		    // ResultSet rs = null;            
            for (int i = 1; i <= total; i++ )
            {
                InputStream is = PerformanceController.class.getClassLoader().getResourceAsStream("static/photo.jpg");

                pstmt = connection.prepareStatement(insertQuery);
                pstmt.setString(1, "Name"+i);
                pstmt.setString(2, "LastName"+i);
                pstmt.setBinaryStream(3, is);
                pstmt.executeUpdate();
                recorded++;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
         
        return "Records inserted: "+recorded;
    }

    @GetMapping("/tests/selectLike/{db}")
    String selectLikeRecords(@PathVariable ("db") String db) {
    
        DataSource ds = null;
        String selectQuery = "";
        int result = 0;
        selectQuery = "SELECT * FROM test.patient WHERE Name like '%12'";
        switch (db) {
            case "mysql":
                ds = MyDataSourceFactory.getMySQLDataSource(mysqlUsername, mysqlUrl, mysqlPassword);            
                break;
            case "postgres":
                ds = MyDataSourceFactory.getPostgresDataSource(postgresUsername, postgresUrl, postgresPassword);
                break;
            case "iris":
                ds = MyDataSourceFactory.getIrisDataSource(irisUsername, irisUrl, irisPassword);
                break;
        
            default:
                break;
        }
        try
        {
            Connection connection = ds.getConnection();
            Statement stmt = null;
		    ResultSet rs = null;
            stmt = connection.createStatement();
            rs = stmt.executeQuery(selectQuery);
            while (rs.next()) {
                result++;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
         
        return "Total records: "+result;
    }
    
}
