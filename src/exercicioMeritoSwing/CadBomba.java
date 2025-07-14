/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package exercicioMeritoSwing;

import exercicioMeritoBeans.BombaBean;
import exercicioMeritoBeans.ProdutoBean;
import exercicioMeritoControls.ProdutoControl;
import java.awt.Toolkit;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.awt.Image;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Rafael
 */
public class CadBomba extends javax.swing.JFrame {
    
    private final ProdutoControl produtoControl = new ProdutoControl();
    private DefaultTableModel tmConsulta = new DefaultTableModel(null,new String[]{"Bomba", "Combustivel"});
    private ListSelectionModel lsConsulta;
    private final List<ProdutoBean> produtos;
    private List<BombaBean> bombas;
    private Boolean inicializando = true;
    
    

    /**
     * Creates new form CadBomba
     */
    public CadBomba() {
        initComponents();
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image img = kit.getImage("gas.png");
        this.setIconImage(img);
        desabilitarCampos();
        
        DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        DefaultTableCellRenderer direita = new DefaultTableCellRenderer();

        esquerda.setHorizontalAlignment(SwingConstants.LEFT);
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        direita.setHorizontalAlignment(SwingConstants.RIGHT);
        
        tbConsulta.getColumnModel().getColumn(0).setPreferredWidth(20);
        tbConsulta.getColumnModel().getColumn(1).setPreferredWidth(20);
        tbConsulta.getColumnModel().getColumn(0).setCellRenderer(direita);
        tbConsulta.setAutoCreateRowSorter(true);
        tbConsulta.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                tbConsultaLinhaSelecionada(tbConsulta);
            }
        });
        
        produtos = produtoControl.listarSQL("SELECT * FROM tb_combustivel");
        cbCombustivel.removeAllItems();
        for(int i = 0; i < produtos.size(); i++){
            cbCombustivel.addItem(produtos.get(i).getNome());
        }
        listar();
    }
    
    private void limparCampos(){
        tfId.setText("");
        cbCombustivel.setSelectedIndex(0);
    }
    
    private void habilitarCampos(){
        cbCombustivel.setEnabled(true);
        btSalvar.setEnabled(true);
        btExcluir.setEnabled(true);
    }
    
    private void desabilitarCampos(){
        cbCombustivel.setEnabled(false);
        btSalvar.setEnabled(false);
        btExcluir.setEnabled(false);
    }
    
    protected void tbConsultaLinhaSelecionada(JTable tb){
        try{
            if(tb.getSelectedRow() != -1 && !inicializando){
                habilitarCampos();
                int ls = tbConsulta.convertRowIndexToModel(tb.getSelectedRow());
                tfId.setText(bombas.get(ls).getId()+"");
                for(int i = 0; i < produtos.size(); i++){
                    ProdutoBean get = produtos.get(i);
                    if(get.getId()== bombas.get(i).getCombustivel()){
                        cbCombustivel.setSelectedIndex(i);
                        break;
                    }    
                }
            } else {
                limparCampos();
            }
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }
    

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tbConsulta = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        tfId = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cbCombustivel = new javax.swing.JComboBox<>();
        btAdc = new javax.swing.JButton();
        btExcluir = new javax.swing.JButton();
        btSalvar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tbConsulta.setModel(tmConsulta);
        jScrollPane1.setViewportView(tbConsulta);

        jLabel1.setText("ID:");

        tfId.setEditable(false);
        tfId.setBorder(null);

        jLabel2.setText("Combustivel:");

        cbCombustivel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btAdc.setText("+");
        btAdc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAdcActionPerformed(evt);
            }
        });

        btExcluir.setText("-");
        btExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btExcluirActionPerformed(evt);
            }
        });

        btSalvar.setText("Salvar");
        btSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSalvarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 515, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfId, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btAdc, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbCombustivel, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btSalvar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btAdc)
                    .addComponent(btExcluir))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tfId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cbCombustivel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btSalvar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btExcluirActionPerformed
        excluir();
    }//GEN-LAST:event_btExcluirActionPerformed

    private void btAdcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAdcActionPerformed
        habilitarCampos();
    }//GEN-LAST:event_btAdcActionPerformed

    private void btSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSalvarActionPerformed
      salvar();
    }//GEN-LAST:event_btSalvarActionPerformed

    private void salvar(){
        if(cbCombustivel.getSelectedIndex() > -1){
            try {
                BombaBean bean = new BombaBean();                
                bean.setCombustivel(produtos.get(cbCombustivel.getSelectedIndex()).getId());
                if(tfId.getText().trim().isEmpty()){
                    bean.setId(produtoControl.gerarId(1));
                    produtoControl.cadastraBomba(bean);
                    limparCampos();
                } else {
                    bean.setId(Long.parseLong(tfId.getText()));
                    produtoControl.alteraBomba(bean);
                    limparCampos();
                }
                listar();
            } catch (Exception ex){
                JOptionPane.showMessageDialog(null, "Erro ao cadastrar " + ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecione o combustivel");
        }
    }
    
    private void excluir(){
        if(tbConsulta.getSelectedRow() != -1){
            int ls = tbConsulta.convertRowIndexToModel(tbConsulta.getSelectedRow());
            produtoControl.excluiBomba(bombas.get(ls).getId());
        }
        listar();
    }
    
    private void listar(){
        try {
            bombas = produtoControl.listarSQL("SELECT \n"
                    + " b.bb_id AS bomba_id,\n"
                    + " c.com_nome AS combustivel_nome\n"
                    + " FROM tb_bomba b JOIN\n"
                    + " tb_combustivel c ON\n"
                    + " b.bb_combustivel = c.com_id", "");
            mostrar(bombas);
        } catch (Exception ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Pesquisa : " + ex.getLocalizedMessage());
        }
    }
    
    private void mostrar(List<BombaBean> lista){
        inicializando = true;
        while(tmConsulta.getRowCount() > 0){
            tmConsulta.removeRow(0);
        }
        inicializando = false;
        if(lista.isEmpty()){
            JOptionPane.showMessageDialog(null, "Nenhum registro  encontrado");
        } else {
            String[] campos = new String[]{null,null};
            for(int i = 0; i < lista.size(); i++){
                tmConsulta.addRow(campos);
                tmConsulta.setValueAt(lista.get(i).getId(), i, 0);
                tmConsulta.setValueAt(lista.get(i).getCombustivelNome(), i, 1);
                
            }
        }
    }
    
   
    
    
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
            java.util.logging.Logger.getLogger(CadBomba.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CadBomba.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CadBomba.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CadBomba.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CadBomba().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAdc;
    private javax.swing.JButton btExcluir;
    private javax.swing.JButton btSalvar;
    private javax.swing.JComboBox<String> cbCombustivel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbConsulta;
    private javax.swing.JTextField tfId;
    // End of variables declaration//GEN-END:variables
}
