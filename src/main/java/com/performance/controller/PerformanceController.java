package com.performance.controller;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;

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
        int recorded = 0;
        switch (db) {
            case "mysql":
                ds = MyDataSourceFactory.getMySQLDataSource(mysqlUsername, mysqlUrl, mysqlPassword);
                insertQuery = "INSERT INTO test.patient VALUES (null, ?, ?, null, ?, ?, ?)";
                break;
            case "postgres":
                ds = MyDataSourceFactory.getPostgresDataSource(postgresUsername, postgresUrl, postgresPassword);
                insertQuery = "INSERT INTO test.patient (\"name\",\"lastname\",\"phone\",\"address\",\"city\") VALUES (?, ?, ?, ?, ?)";
                break;
            case "iris":
                ds = MyDataSourceFactory.getIrisDataSource(irisUsername, irisUrl, irisPassword);
                insertQuery = "INSERT INTO test.patient VALUES (?, ?, null, ?, ?, ?)";
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
                Random random = new Random();
                pstmt = connection.prepareStatement(insertQuery);
                pstmt.setString(1, "Name"+i);
                pstmt.setString(2, "LastName"+i);
                pstmt.setString(3, "Phone"+i);
                pstmt.setString(4, "Address"+i);
                pstmt.setInt(5, random.nextInt(8 - 1) + 1);
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
                insertQuery = "INSERT INTO test.patient (Name, Lastname, Photo, Phone, Address, City) VALUES (?, ?, ?, ?, ?, ?)";
                break;
            case "postgres":
                ds = MyDataSourceFactory.getPostgresDataSource(postgresUsername, postgresUrl, postgresPassword);
                insertQuery = "INSERT INTO test.patient (\"name\",\"lastname\", \"photo\", \"phone\", \"address\", \"city\") VALUES (?, ?, ?, ?, ?, ?)";
                break;
            case "iris":
                ds = MyDataSourceFactory.getIrisDataSource(irisUsername, irisUrl, irisPassword);
                insertQuery = "INSERT INTO test.patient VALUES (?, ?, ?,?, ?, ?)";
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
                Random random = new Random();

                pstmt = connection.prepareStatement(insertQuery);
                pstmt.setString(1, "Name"+i);
                pstmt.setString(2, "LastName"+i);
                pstmt.setBinaryStream(3, is);
                pstmt.setString(4, "Phone"+i);
                pstmt.setString(5, "Address"+i);
                pstmt.setInt(6, random.nextInt(8 - 1) + 1);
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

    @GetMapping("/tests/selectGroupBy/{db}")
    String selectGroupBy(@PathVariable ("db") String db) {
    
        DataSource ds = null;
        String selectQuery = "";
        int result = 0;
        selectQuery = "SELECT count(p.Name), c.Name FROM test.patient p left join test.city c on p.City = c.Id GROUP BY c.Name";
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

    @GetMapping("/tests/update/{db}")
    String UpdateRecords(@PathVariable ("db") String db) {
    
        DataSource ds = null;
        String selectQuery = "";
        int result = 0;
        selectQuery = "UPDATE test.patient SET Phone = '+15553535301' WHERE Name in (SELECT Name FROM test.patient where Name like '%12')";
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
            stmt = connection.createStatement();
            result = stmt.executeUpdate(selectQuery);
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
         
        return "Result of update: "+result;
    }

    @GetMapping("/tests/delete/{db}")
    String DeleteRecords(@PathVariable ("db") String db) {
    
        DataSource ds = null;
        String selectQuery = "";
        int result = 0;
        selectQuery = "DELETE test.patient WHERE Name in (SELECT Name FROM test.patient where Name like '%12')";
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
            stmt = connection.createStatement();
            result = stmt.executeUpdate(selectQuery);
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
         
        return "Result of update: "+result;
    }
    
}
