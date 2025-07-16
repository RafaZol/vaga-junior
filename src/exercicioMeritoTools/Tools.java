package exercicioMeritoTools;

import exercicioMeritoConnections.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Rafael
 */
public class Tools {
    
    public static String Cripto(String senha){
        int contador, tamanho, codigo;
        String senhaCriptografada = "";
        tamanho = senha.length();
        senha = senha.toUpperCase();
        contador = 0;
        
        while(contador < tamanho){
            codigo = senha.charAt(contador) + 130;
            senhaCriptografada = senhaCriptografada + (char) codigo;
            contador++;
        }
        return senhaCriptografada;
    }
    
    /*
    * @param coluna sql1 
    * @paran tabela sql2 
    */
    public static long gerarId(String sql1, String sql2){
        return gerarId(sql1, sql2, Conexao.getConnPublic());
    }
    public static long gerarId(String sql1, String sql2, Connection conn){
        String sql =  "SELECT MAX(\n"
                + sql1 + ")\n"
                + " AS ultimo_id FROM " + sql2;
        try {
            PreparedStatement pstm  = conn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            
            if(rs.next()){
                return rs.getInt("ultimo_id")+1;            
            }
        } catch (SQLException sx){
            sx.printStackTrace();
        }
        return 0;
    }
    
}
