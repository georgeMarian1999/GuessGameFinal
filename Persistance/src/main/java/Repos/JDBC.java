package Repos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBC {
    private Properties JDBCProps;


    public JDBC(Properties props){
        this.JDBCProps=props;
    }
    private Connection instance=null;
    public Connection getNewConnection(){

        String url=JDBCProps.getProperty("Curse.jdbc.url");
        String user=JDBCProps.getProperty("Curse.jdbc.user");
        String pass=JDBCProps.getProperty("Curse.jdbc.pass");

        Connection con=null;
        try{
            if(user!=null && pass!=null)
                con= DriverManager.getConnection(url,user,pass);
            else con= DriverManager.getConnection(url);
        }
        catch(SQLException ex){
            System.out.println(ex);
        }
        return con;
    }
    public Connection getConnection(){

        try{
            if(instance==null || instance.isClosed())
                instance=this.getNewConnection();
        }catch(SQLException ex){
            System.out.println(ex);
        }
        return instance;
    }
}
