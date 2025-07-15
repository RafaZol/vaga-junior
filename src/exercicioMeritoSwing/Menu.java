/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package exercicioMeritoSwing;

import exercicioMeritoBeans.BombaBean;
import exercicioMeritoBeans.MovpdvBean;
import exercicioMeritoBeans.PessoaBean;
import exercicioMeritoBeans.ProdutoBean;
import exercicioMeritoControls.MovPdvControl;
import exercicioMeritoControls.PessoaControl;
import exercicioMeritoControls.ProdutoControl;
import exercicioMeritoTools.Tools;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Toolkit;
import java.time.LocalDateTime;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

/**
 *
 * @author Rafael
 */
public class Menu extends javax.swing.JFrame {
    
    private final ProdutoControl produtoControl = new ProdutoControl();
    private final PessoaControl pessoaControl = new PessoaControl();
    private final MovPdvControl movControl = new MovPdvControl();
    private DefaultTableModel tmConsulta = new DefaultTableModel(null, new String[]{"BOMBA", "PRODUTO",
        "VALOR", "QUANTIA", "TOTAL" ,"DATA/HORA"});
    private ListSelectionModel lsConsulta;
    private final List<BombaBean> bombas;
    private final List<PessoaBean> clientes;
    private List<MovpdvBean> movBean;
    private Boolean inicializando = true;
    List<ProdutoBean> produtos;

    /**
     * Creates new form Menu
     */
    public Menu() {
        initComponents();
        menuInitAction();
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image img = kit.getImage("mercado.png");
        this.setIconImage(img);
        
        DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        DefaultTableCellRenderer direita = new DefaultTableCellRenderer();

        esquerda.setHorizontalAlignment(SwingConstants.LEFT);
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        direita.setHorizontalAlignment(SwingConstants.RIGHT);
        
        tbConsulta.getColumnModel().getColumn(0).setPreferredWidth(20);
        tbConsulta.getColumnModel().getColumn(1).setPreferredWidth(20);
        tbConsulta.getColumnModel().getColumn(2).setPreferredWidth(20);
        tbConsulta.getColumnModel().getColumn(3).setPreferredWidth(20);
        tbConsulta.getColumnModel().getColumn(4).setPreferredWidth(20);
        tbConsulta.getColumnModel().getColumn(5).setPreferredWidth(20);
        tbConsulta.getColumnModel().getColumn(0).setCellRenderer(direita);
        tbConsulta.setAutoCreateRowSorter(true);
        tbConsulta.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                tbConsultaLinhaSelecionada(tbConsulta);
            }
        });
        
        bombas = produtoControl.listarSQL("SELECT * FROM tb_bomba", "sql");
        cbBomba.removeAllItems();
        for(int i = 0; i < bombas.size(); i++){
            cbBomba.addItem("Bomba : " + bombas.get(i).getId());
        }
        
        clientes = pessoaControl.listarSQL("SELECT * FROM tb_pessoas");
        cbCliente.removeAllItems();
        cbCliente.addItem("Selecione o Cliente");
        for(int i = 0; i < clientes.size(); i++){
            cbCliente.addItem(clientes.get(i).getNome());
        }
        listar();   
    }
    
    
    private void limparCampos(){
        tfId.setText("");
        cbBomba.setSelectedIndex(0);
        cbCliente.setSelectedIndex(0);
        tfValor.setText("");
        tfQuantia.setText("");
        tfObs.setText("");
        tfDes.setText("");
        tfValorTotal.setText("");
    }
    
    private boolean verificarCampos(){
        if(cbBomba.getSelectedIndex() <= -1 && tfValor.getText().trim().isEmpty()
                && tfQuantia.getText().trim().isEmpty() && tfValorTotal.getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(null, "Preencha Todos os Campos obrigatorios corretamente");
            return false;
        }
        return true;
    }
    
    protected void tbConsultaLinhaSelecionada(JTable tb){
        try{
            if(tb.getSelectedRow() != -1 && !inicializando){
                int ls = tbConsulta.convertRowIndexToModel(tb.getSelectedRow());
                tfId.setText(movBean.get(ls).getId()+"");
                tfQuantia.setText(movBean.get(ls).getQuantidade()+"");
                tfObs.setText(movBean.get(ls).getObs());
                tfDes.setText(movBean.get(ls).getDesconto()+"");
                tfValorTotal.setText(movBean.get(ls).getValorTotal()+"");
                produtos = produtoControl.listarSQL("SELECT * FROM tb_combustivel");
                for(int i = 0; i < bombas.size(); i++){
                    BombaBean b = bombas.get(i);
                    if(b.getId() == movBean.get(ls).getBomba()){
                        cbBomba.setSelectedIndex(i);
                        for(int j = 0; j < produtos.size(); j++){
                            ProdutoBean p = produtos.get(j);
                            if(p.getId() == b.getCombustivel()){
                                tfValor.setText(p.getValor() + "");
                                break;
                            }                        
                        }
                    break;    
                    }
                }
                for(int i = 0; i < clientes.size(); i++){
                    PessoaBean p =clientes.get(i);
                    if(p.getId() == movBean.get(ls).getCliente()){
                        cbCliente.setSelectedIndex(i);
                        break;
                    }                    
                }                                    
            } else {
                limparCampos();
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
    
    private void mostrar(List<MovpdvBean> lista){
        inicializando = true;
        while(tmConsulta.getRowCount() > 0){
            tmConsulta.removeRow(0);
        }
        inicializando = false;
        String[] campos = new String[]{null, null, null, null, null, null};
        for(int i  = 0; i < lista.size(); i++){
            tmConsulta.addRow(campos);
            tmConsulta.setValueAt(lista.get(i).getBomba(), i, 0);
            tmConsulta.setValueAt(lista.get(i).getCombustivelNome(), i, 1);
            tmConsulta.setValueAt(lista.get(i).getValor(), i, 2);
            tmConsulta.setValueAt(lista.get(i).getQuantidade(), i, 3);
            tmConsulta.setValueAt(lista.get(i).getValorTotal(), i, 4);
            tmConsulta.setValueAt(lista.get(i).getHora(), i, 5);
        }
    }
    
    private void listar(){
        try{
            movBean = movControl.listarSQL("SELECT"                    
                    + " m.mov_id,\n" 
                    + " m.mov_qtd,\n" 
                    + " m.mov_obs,\n" 
                    + " m.mov_valor_total,\n" 
                    + " m.mov_desc,\n" 
                    + " m.mov_time,\n" 
                    + " m.mov_cliente,\n" 
                    + " m.mov_bomba,"
                    + " m.mov_valor,\n" 
                    + " c.com_nome AS comb_nome\n"   
                    + "FROM tb_mov_pdv m\n" 
                    + "JOIN tb_bomba b ON m.mov_bomba = b.bb_id\n" 
                    + "JOIN tb_combustivel c ON b.bb_combustivel = c.com_id\n" 
                    + "ORDER BY m.mov_time DESC", "");
            mostrar(movBean);
        } catch (Exception ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Pesquisa : " + ex.getLocalizedMessage());
        }
    }
    
    private void finalizar(){
        if(verificarCampos()){
            try{
                MovpdvBean bean = new MovpdvBean();                               
                bean.setBomba(bombas.get(cbBomba.getSelectedIndex()).getId());
                bean.setQuantidade(Float.parseFloat(tfQuantia.getText().replace(",", ".")));
                bean.setValor(Float.parseFloat(tfValor.getText().replace(",", ".")));
                bean.setValorTotal(Float.parseFloat(tfValorTotal.getText().replace(",", ".")));
                int index = cbCliente.getSelectedIndex();
                if (index > 0 && index <= clientes.size()) {
                    bean.setCliente(clientes.get(index - 1).getId());
                }
//                bean.setCliente(clientes.get(cbCliente.getSelectedIndex()).getId());
                bean.setDesconto(Float.parseFloat(tfDes.getText().replace(",", ".")));
                bean.setObs(tfObs.getText());                
                if(tfId.getText().trim().isEmpty()){
                    bean.setId(Tools.gerarId("mov_id", "tb_mov_pdv"));
                    bean.setHora(LocalDateTime.now());
                    movControl.cadastraMov(bean);
                    limparCampos();
                } else {
                    bean.setId(Long.parseLong(tfId.getText()));
                    movControl.alteraMov(bean);
                    limparCampos();
                }
                listar();
            } catch (Exception ex){
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Erro ao finalizar venda");
        } 
    }
    
    private void excluir(){
        if(tbConsulta.getSelectedRow() != -1){
            int ls = tbConsulta.convertRowIndexToModel(tbConsulta.getSelectedRow());
            movControl.excluiMov(movBean.get(ls).getId());
        }
        listar();
    }
    
    private void calcular(){
        float vlU = 0f, vlT = 0f, qtd = 0f, desc = 0f;
        try{
            if(!tfValor.getText().trim().isEmpty()){
                vlU = Float.parseFloat(tfValor.getText().replace(",", "."));
            }
            if(!tfValorTotal.getText().trim().isEmpty()){
                vlT = Float.parseFloat(tfValorTotal.getText().replace(",", "."));
            }
            if(!tfQuantia.getText().trim().isEmpty()){
                qtd = Float.parseFloat(tfQuantia.getText().replace(",", "."));
            }
            if(!tfDes.getText().trim().isEmpty()){
                desc = Float.parseFloat(tfDes.getText().replace(",", "."));
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Insira apenas números válidos.");
            return;
        }
        
        if (vlU > 0 && qtd > 0 && vlT == 0) {
            vlT = vlU * qtd;
        } else if (vlU == 0 && qtd > 0 && vlT > 0) {
            vlU = vlT / qtd;
        } else if (vlU > 0 && qtd == 0 && vlT > 0) {
            qtd = vlT / vlU;
        }
        float vlrTFinal = vlT;
        if (desc > 0) {
            vlrTFinal = vlT * (1 - desc / 100);
        }
        
        tfValor.setText(String.format("%.2f", vlU));
        tfQuantia.setText(String.format("%.2f", qtd));
        tfValorTotal.setText(String.format("%.2f", vlrTFinal));
        tfDes.setText(desc+"");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        cbBomba = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        tfValor = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        tfQuantia = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        btCalcular = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        cbCliente = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        tfObs = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        tfDes = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        tfValorTotal = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        btCancela = new javax.swing.JButton();
        tfFinaliza = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbConsulta = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        tfId = new javax.swing.JTextField();
        btExcluir = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jmCadPessoa = new javax.swing.JMenuItem();
        jmUsu = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jmCadProdutos = new javax.swing.JMenuItem();
        jmCadBombas = new javax.swing.JMenuItem();
        jmPdv = new javax.swing.JMenu();
        jmPay = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Bomba:");

        cbBomba.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel2.setText("Valor:");

        jLabel3.setText("R$:");

        jLabel4.setText("Quantidade:");

        tfQuantia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfQuantiaActionPerformed(evt);
            }
        });

        jLabel5.setText("L :");

        btCalcular.setText("Calcular");
        btCalcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCalcularActionPerformed(evt);
            }
        });

        jLabel6.setText("Cliente:");

        cbCliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel7.setText("Observações :");

        jLabel8.setText("Desconto :");

        jLabel9.setText("%");

        jLabel10.setText("Valor Total:");

        tfValorTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfValorTotalActionPerformed(evt);
            }
        });

        jLabel11.setText("R$:");

        btCancela.setText("Cancelar");
        btCancela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelaActionPerformed(evt);
            }
        });

        tfFinaliza.setText("Finalizar");
        tfFinaliza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfFinalizaActionPerformed(evt);
            }
        });

        tbConsulta.setModel(tmConsulta);
        jScrollPane1.setViewportView(tbConsulta);

        jLabel12.setText("ID:");

        tfId.setEditable(false);
        tfId.setBorder(null);
        tfId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfIdActionPerformed(evt);
            }
        });

        btExcluir.setText("Excluir");
        btExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btExcluirActionPerformed(evt);
            }
        });

        jButton1.setText("Atualizar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jMenu1.setText("File");

        jmCadPessoa.setText("Cadastro de Pessoas");
        jmCadPessoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmCadPessoaActionPerformed(evt);
            }
        });
        jMenu1.add(jmCadPessoa);

        jmUsu.setText("Cadastro de Usuário");
        jmUsu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmUsuActionPerformed(evt);
            }
        });
        jMenu1.add(jmUsu);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");

        jmCadProdutos.setText("Cadastro de Produtos");
        jmCadProdutos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmCadProdutosActionPerformed(evt);
            }
        });
        jMenu2.add(jmCadProdutos);

        jmCadBombas.setText("Cadastro de Bombas");
        jmCadBombas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmCadBombasActionPerformed(evt);
            }
        });
        jMenu2.add(jmCadBombas);

        jMenuBar1.add(jMenu2);

        jmPdv.setText("jMenu1");

        jmPay.setText("PDV");
        jmPay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmPayActionPerformed(evt);
            }
        });
        jmPdv.add(jmPay);

        jMenuBar1.add(jmPdv);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(cbCliente, javax.swing.GroupLayout.Alignment.LEADING, 0, 172, Short.MAX_VALUE)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(cbBomba, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jLabel6))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel3)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(tfValor, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(19, 19, 19)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel5)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(tfQuantia, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(34, 34, 34)
                                                .addComponent(btCalcular))
                                            .addComponent(jLabel4)))
                                    .addComponent(jLabel7)
                                    .addComponent(tfObs)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(tfDes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel9)
                                .addGap(39, 39, 39)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addGap(1, 1, 1)
                                        .addComponent(tfValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btExcluir)
                                        .addGap(18, 18, 18)
                                        .addComponent(btCancela)
                                        .addGap(18, 18, 18)
                                        .addComponent(tfFinaliza))
                                    .addComponent(jLabel10))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfId, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(tfId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbBomba, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(tfQuantia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(btCalcular))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfObs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfDes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(tfValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(btCancela)
                    .addComponent(tfFinaliza)
                    .addComponent(btExcluir))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jmCadPessoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmCadPessoaActionPerformed
        cadastrarPessoa();
    }//GEN-LAST:event_jmCadPessoaActionPerformed

    private void jmCadProdutosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmCadProdutosActionPerformed
        cadastraProduto();       
    }//GEN-LAST:event_jmCadProdutosActionPerformed

    private void jmPayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmPayActionPerformed
        // abrirPDV();
    }//GEN-LAST:event_jmPayActionPerformed

    private void jmUsuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmUsuActionPerformed
        cadastrarUsuario();
    }//GEN-LAST:event_jmUsuActionPerformed

    private void jmCadBombasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmCadBombasActionPerformed
        cadastrarBombas();
    }//GEN-LAST:event_jmCadBombasActionPerformed

    private void tfQuantiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfQuantiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfQuantiaActionPerformed

    private void tfValorTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfValorTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfValorTotalActionPerformed

    private void btCancelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelaActionPerformed
        limparCampos();
    }//GEN-LAST:event_btCancelaActionPerformed

    private void tfFinalizaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfFinalizaActionPerformed
        finalizar();
    }//GEN-LAST:event_tfFinalizaActionPerformed

    private void btCalcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCalcularActionPerformed
        calcular();
    }//GEN-LAST:event_btCalcularActionPerformed

    private void tfIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfIdActionPerformed

    }//GEN-LAST:event_tfIdActionPerformed

    private void btExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btExcluirActionPerformed
        excluir();
    }//GEN-LAST:event_btExcluirActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        listar();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cadastrarPessoa(){
        CadPessoas pessoa = new CadPessoas();
        pessoa.pack();
        pessoa.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        pessoa.setLocationRelativeTo(null);
        pessoa.setVisible(true); 
    }
    
    private void cadastrarUsuario(){
        CadUsu usu = new CadUsu();
        usu.pack();
        usu.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        usu.setLocationRelativeTo(null);
        usu.setVisible(true);
    }
    
    private void cadastraProduto(){
        CadProdutos prod = new CadProdutos();
        prod.pack();
        prod.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        prod.setLocationRelativeTo(null);
        prod.setVisible(true);
    }
    
    private void cadastrarBombas(){
        CadBomba b = new CadBomba();
        b.pack();
        b.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        b.setLocationRelativeTo(null);
        b.setVisible(true);
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
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu().setVisible(true);
            }
        });
    }
    
    private void menuInitAction(){
        jMenu1.setText("");
        jMenu2.setText("");
        jmPdv.setText("");
        ImageIcon icon = new ImageIcon("user.png");
        ImageIcon icon2 = new ImageIcon("gas.png");
        ImageIcon icon3 = new ImageIcon("pdv.png");
        Image image = icon.getImage().getScaledInstance(22, 22,Image.SCALE_SMOOTH);
        Image image2 = icon2.getImage().getScaledInstance(22, 22,Image.SCALE_SMOOTH);
        Image image3 = icon3.getImage().getScaledInstance(22, 22,Image.SCALE_SMOOTH);
        ImageIcon imagei = new ImageIcon(image);
        ImageIcon imagei2 = new ImageIcon(image2);
        ImageIcon imagei3 = new ImageIcon(image3);
        jMenu1.setIcon(imagei);
        jMenu2.setIcon(imagei2);
        jmPdv.setIcon(imagei3);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCalcular;
    private javax.swing.JButton btCancela;
    private javax.swing.JButton btExcluir;
    private javax.swing.JComboBox<String> cbBomba;
    private javax.swing.JComboBox<String> cbCliente;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenuItem jmCadBombas;
    private javax.swing.JMenuItem jmCadPessoa;
    private javax.swing.JMenuItem jmCadProdutos;
    private javax.swing.JMenuItem jmPay;
    private javax.swing.JMenu jmPdv;
    private javax.swing.JMenuItem jmUsu;
    private javax.swing.JTable tbConsulta;
    private javax.swing.JTextField tfDes;
    private javax.swing.JButton tfFinaliza;
    private javax.swing.JTextField tfId;
    private javax.swing.JTextField tfObs;
    private javax.swing.JTextField tfQuantia;
    private javax.swing.JTextField tfValor;
    private javax.swing.JTextField tfValorTotal;
    // End of variables declaration//GEN-END:variables
}
