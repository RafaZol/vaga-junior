package merito;
import merito.views.Login;
import javax.swing.WindowConstants;

/**
 *
 * @author Rafael
 * 11/07/2025
 */
public class ExercicioMerito {
    public static void main(String[] args) {
        
        
        Login login = new Login();
        login.pack();
        login.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        login.setLocationRelativeTo(null);
        login.setVisible(true);
    }
    
}
