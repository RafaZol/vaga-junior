package merito.views;

import merito.dao.PessoaDao;
import merito.utils.Utils;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

/**
 *
 * @author Rafael
 */
public class Login extends javax.swing.JFrame {
    
    private final PessoaDao pessoaDao = new PessoaDao();

    /**
     * Creates new form Login
     */
    public Login() {
        initComponents();
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image img = kit.getImage("mercado.png");
        this.setIconImage(img);
        
        try {
            for(javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()){
                if("Windows".equals(info.getName())){
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException cx){
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, cx);
        } catch (InstantiationException ix){
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ix);
        } catch (IllegalAccessException ix){
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ix);
        } catch (javax.swing.UnsupportedLookAndFeelException jx) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, jx);
        }
      
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tfName = new javax.swing.JTextField();
        pfPass = new javax.swing.JPasswordField();
        btLogin = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btLogin.setText("Entrar");
        btLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLoginActionPerformed(evt);
            }
        });

        jLabel1.setText("Usuario:");

        jLabel2.setText("Senha:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pfPass, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfName, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(btLogin)))
                .addContainerGap(90, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tfName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(pfPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btLogin)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLoginActionPerformed
        if (emptyField()){
            login();
        } else {
            JOptionPane.showMessageDialog(null, "Verifique se todos os campos foram preenchidos corretamente'");
        }
    }//GEN-LAST:event_btLoginActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }
    
    private boolean emptyField() {
        if(tfName.getText().trim().isEmpty() || pfPass.getText().trim().isEmpty()){
            return false;
        }        
        return true;
    }
    
    private void login(){
        try {
            String cripto;
            cripto = Utils.cripto(pfPass.getText());            
            if(pessoaDao.vereficarCredenciais(tfName.getText(),cripto)){
                abreTela();               
            } else {
                JOptionPane.showMessageDialog(null, "Usuário ou senha não encontrados");
                pfPass.setText("");
            }
        } catch (Exception ex){
            System.out.println("ERRO : " + ex);
        }
    }
    
    private void abreTela() throws ClassNotFoundException{
        this.dispose();
        Menu menu = new Menu();
        menu.pack();
        menu.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        menu.setLocationRelativeTo(null);
        menu.setVisible(true); 

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btLogin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPasswordField pfPass;
    private javax.swing.JTextField tfName;
    // End of variables declaration//GEN-END:variables
}
