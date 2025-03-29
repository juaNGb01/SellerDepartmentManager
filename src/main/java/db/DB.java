package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {
    private static Connection conn = null;

    public static Connection getConnection(){

        if(conn == null){
            try{

                Properties props = loadProperties();
                String url = props.getProperty("dburl");
                conn = DriverManager.getConnection(url, props);

            }catch (SQLException e){
                throw new DbException("Erro ao conectar com o banco: " + e.getMessage());
            }
        }
        return conn;
    }

    public static void closeConnection(){
        if(conn != null){
            try{
                conn.close();
            }catch (SQLException e){
                throw new DbException("Erro ao fechar conexao :" + e.getMessage());
            }
        }
    }

    public static void closeStatement(Statement stmt){
        if(stmt != null){
            try{
                stmt.close();
            }catch (SQLException e){
                throw new DbException("Erro ao fechar o Statement!: " + e.getMessage());
            }
        }
    }

    public static void closeResutSet(ResultSet rs){
        if(rs != null){
            try{
                rs.close();

            }catch (SQLException e){
                throw new DbException("Erro ao fechar ResultSet!: " + e.getMessage());
            }
        }
    }

    public static Properties loadProperties(){
        try(FileInputStream fs = new FileInputStream("db.properties")){
            Properties props = new Properties();
            props.load(fs);
            return props;

        } catch (IOException e) {
            throw new DbException("Erro ao carregar properties: " + e.getMessage());
        }
    }
}
