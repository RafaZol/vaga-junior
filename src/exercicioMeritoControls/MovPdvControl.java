/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exercicioMeritoControls;

import exercicioMeritoBeans.BombaBean;
import exercicioMeritoBeans.MovpdvBean;
import exercicioMeritoBeans.PessoaBean;
import exercicioMeritoConnections.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rafael
 */
public class MovPdvControl {
     
    private PreparedStatement pstm;
    private ResultSet rs;
    
    private String sql_cadastra = "INSERT INTO tb_mov_pdv (\n" 
    + " mov_id,\n" 
    + " mov_bomba,\n" 
    + " mov_qtd,\n" 
    + " mov_cliente,\n" 
    + " mov_obs,\n"
    + " mov_valor,\n" 
    + " mov_valor_total,\n" 
    + " mov_desc,\n" 
    + " mov_time)\n"
    + " VALUES "
    + "(?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    private String sql_update = "UPDATE tb_mov_pdv SET \n" 
    + "mov_bomba = ?,\n" 
    + "mov_qtd = ?,\n" 
    + "mov_cliente = ?,\n" 
    + "mov_obs = ?,\n" 
    + "mov_valor = ?,\n"        
    + "mov_valor_total = ?,\n" 
    + "mov_desc = ?\n" 
    +"WHERE mov_id = ?";
    
    private String sql_delete = "DELETE FROM tb_mov_pdv WHERE mov_id = ?";
    
    public long cadastraMov(MovpdvBean bean){
        return cadastraMov(bean, Conexao.getConnPublic(), true);
    }
    
    public long cadastraMov(MovpdvBean bean, Connection conn, boolean commit){
        int regs = 0;
        try {
            pstm = conn.prepareStatement(sql_cadastra);
            pstm.setLong(1, bean.getId());
            pstm.setLong(2, bean.getBomba());
            pstm.setFloat(3, bean.getQuantidade());
            if(bean.getCliente() != 0f){
                pstm.setLong(4, bean.getCliente());
            } else {
                pstm.setLong(4, 0);
            }
            pstm.setString(5,bean.getObs());
            pstm.setFloat(6, bean.getValor());
            pstm.setFloat(7, bean.getValorTotal());
            if(bean.getDesconto() != 0f){
                pstm.setFloat(8, bean.getDesconto());
            } else {
                pstm.setFloat(8, 0f);
            }
            pstm.setTimestamp(9, Timestamp.valueOf(bean.getHora()));
            regs = pstm.executeUpdate();
            if(commit){
                conn.commit();
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }
    
    public boolean alteraMov(MovpdvBean bean){
        return alteraMov(bean, Conexao.getConnPublic(), true);
    }
    
    public boolean alteraMov(MovpdvBean bean, Connection conn, boolean commit){
        int regs = 0;
        try {
            pstm = conn.prepareStatement(sql_update);
            
            pstm.setLong(1, bean.getBomba());
            pstm.setFloat(2, bean.getQuantidade());
            if(bean.getCliente() != 0f){
                pstm.setLong(3, bean.getCliente());
            } else {
                pstm.setLong(3, 0);
            }
            pstm.setString(4,bean.getObs());
            pstm.setFloat(5, bean.getValor());
            pstm.setFloat(6, bean.getValorTotal());
            if(bean.getDesconto() != 0f){
                pstm.setFloat(7, bean.getDesconto());
            } else {
                pstm.setFloat(7, 0f);
            }
            pstm.setLong(8, bean.getId());
            regs = pstm.executeUpdate();
            if(commit){
                conn.commit();
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return regs > 0;
    }
    
    public boolean excluiMov(long id){
        return excluiMov(id, Conexao.getConnPublic(), true);
    }
    
    public boolean excluiMov(long id, Connection conn, boolean commit){
        int regs = 0;
        try {
            pstm = conn.prepareStatement(sql_delete);
            
            pstm.setLong(1, id);
            regs = pstm.executeUpdate();
            if(commit){
                conn.commit();
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return regs > 0;
    }
    
    public MovpdvBean preencheBean(ResultSet rs, String aux){
        MovpdvBean bean = new MovpdvBean();                
        try{            
            bean.setId(rs.getLong("mov_id"));
            bean.setQuantidade(rs.getFloat("mov_qtd"));
            bean.setObs(rs.getString("mov_obs"));
            bean.setValorTotal(rs.getFloat("mov_valor_total"));
            bean.setDesconto(rs.getFloat("mov_desc"));
            bean.setHora(rs.getTimestamp("mov_time").toLocalDateTime());
            if(!rs.wasNull()){
                bean.setCliente(rs.getLong("mov_cliente"));
            }
            if(!rs.wasNull()){                
                bean.setBomba(rs.getLong("mov_bomba"));
            }            
            bean.setValor(rs.getFloat("mov_valor"));
            if(aux.equals("")){
                bean.setCombustivelNome(rs.getString("comb_nome"));                
            }
        } catch (SQLException sx){
            sx.printStackTrace();
        }
        return bean;
    }
    
    public List<MovpdvBean> listarSQL(String sql, String aux){
        return listarSQL(sql, aux, Conexao.getConnPublic());
    }
    
    public List<MovpdvBean> listarSQL(String sql, String aux, Connection conn){
        List<MovpdvBean> lista = new ArrayList<>();
        try {
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                lista.add(preencheBean(rs, aux));
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return lista;
    }
    
}
