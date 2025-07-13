package exercicioMeritoConnections;

/**
 *
 * @author Rafael
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
        
public class Conexao {
    
    private static Connection connPublic;
    
    
    public static Connection getConnPublic(){
        try{
          connPublic = Conexao.conectar("postgresql", "localhost", "postgres", "5432", "merito", "5432", "");  
            
          connPublic.setAutoCommit(false);
        } catch (SQLException sx){    
            sx.printStackTrace();
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return connPublic;
    }
    
    public static void setConnPublic(Connection connPublic){
        Conexao.connPublic = connPublic;
    }
    
    public static Connection conectar(String tipoConexao, String host, String usu, String senha,
            String banco, String porta, String aux) throws SQLException, Exception{
        registrar(tipoConexao);
        String url = "jdbc:" + tipoConexao + "://" + host + ":" + porta + "/" + banco + "?ApplicationName+APIlite"
                + ((aux.isEmpty() ? "" : "&Parameter1" + aux ));
        Properties props = new Properties();
        props.setProperty("user", usu);
        props.setProperty("password", senha);
        return DriverManager.getConnection(url, props);
    }
    
    public static void registrar(String tp) throws ClassNotFoundException {
        if (tp.equals("postgresql")){
            tp = "org.postgresql.Driver";
        }
        if(tp.equals("mysql")){
            tp = "com.mysql.jdbc.Driver";
        }
        Class.forName(tp);
    }
 }
