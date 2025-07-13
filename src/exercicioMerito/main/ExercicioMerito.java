package exercicioMerito.main;

import java.sql.Connection;
import exercicioMeritoConnections.Conexao;
import exercicioMeritoSwing.Login;
import javax.swing.WindowConstants;

/**
 *
 * @author Rafael
 * 11/07/2025
 */
public class ExercicioMerito {
    public static void main(String[] args) {
        
        Connection conn = Conexao.getConnPublic();
        
        if(conn != null){
            System.out.println("Sucess");
            
        }
        
        Login login = new Login();
        login.pack();
        login.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        login.setLocationRelativeTo(null);
        login.setVisible(true);
    }
    
}
