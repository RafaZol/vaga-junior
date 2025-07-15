/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exercicioMeritoControls;

import exercicioMeritoBeans.BombaBean;
import exercicioMeritoBeans.ProdutoBean;
import exercicioMeritoConnections.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Rafael
 */
public class ProdutoControl {
    
    private PreparedStatement pstm;
    private ResultSet rs;
    
    private String cadastraProduto = "INSERT INTO public.tb_combustivel (\n"
            + " com_id,"
            + " com_nome,\n"
            + " com_preco)\n"
            + " VALUES (?, ?, ?)";
    
    private String cadastraBomba = "INSERT INTO tb_bomba (\n"
            + " bb_id,\n"
            + " bb_combustivel)\n"
            + " VALUES (?, ?)";
    
    private String alteraProduto = "UPDATE tb_combustivel SET"
            + " com_nome = ?,"
            + " com_preco = ?"
            + " WHERE com_id = ?";
    
    private String alteraBomba = "UPDATE tb_bomba SET"
            + " bb_combustivel = ?"
            + " WHERE bb_id = ?";
    
    private String excluiProduto = "DELETE FROM tb_combustivel WHERE com_id = ?";
    
    private String excluiBomba = "DELETE FROM tb_bomba WHERE bb_id = ?";

    public long cadastraProduto(ProdutoBean prod){
        return cadastraProduto(prod, Conexao.getConnPublic(), true);
    }
    
    public long cadastraProduto(ProdutoBean prod, Connection conn, boolean commit){
        try{
            pstm = conn.prepareStatement(cadastraProduto);
            
            pstm.setLong(1, prod.getId());
            pstm.setString(2, prod.getNome());
            pstm.setDouble(3, prod.getValor());
            int rs = pstm.executeUpdate();
            if(commit){
                conn.commit();
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return 0;
    }
    
    public boolean alteraProduto(ProdutoBean prod){
        return alteraProduto(prod, Conexao.getConnPublic(), true);
    }
    
    public boolean alteraProduto(ProdutoBean prod, Connection conn, boolean commit){
        int regs = 0;
        try {
            pstm = conn.prepareStatement(alteraProduto);
            
            pstm.setString(1, prod.getNome());
            pstm.setDouble(2, prod.getValor());
            pstm.setLong(3, prod.getId());
            
            regs = pstm.executeUpdate();
            if(commit){
                conn.commit();
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return regs > 0;
    }
    
    public boolean excluiProduto(long id){
        return excluiProduto(id, Conexao.getConnPublic(), true);
    }
    
    public boolean excluiProduto(long id, Connection conn, boolean commit){
        int regs = 0;
        try{
            pstm = conn.prepareStatement(excluiProduto);
            
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
    
    public boolean excluiBomba(long id){
        return excluiBomba(id, Conexao.getConnPublic(), true);
    }
    
    public boolean excluiBomba(long id, Connection conn, boolean commit){
        int regs = 0;
        try{
            pstm = conn.prepareStatement(excluiBomba);
            
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

    public long cadastraBomba(BombaBean bomba){
        return cadastraBomba(bomba, Conexao.getConnPublic(), true);
    }
    
    public long cadastraBomba(BombaBean bomba, Connection conn, boolean commit){
        try{
            pstm = conn.prepareStatement(cadastraBomba);
            
            pstm.setLong(1, bomba.getId());
            pstm.setLong(2, bomba.getCombustivel());
            
            int rs = pstm.executeUpdate();
            if(commit){
                conn.commit();
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return 0;
    }
    
    public boolean alteraBomba(BombaBean bomba){
        return alteraBomba(bomba, Conexao.getConnPublic(), true);
    }
    
    public boolean alteraBomba(BombaBean bomba, Connection conn, boolean commit){
        int regs = 0;
        try {
            pstm = conn.prepareStatement(alteraBomba);
            
            pstm.setLong(1, bomba.getCombustivel());
            pstm.setLong(2, bomba.getId());

            
            regs = pstm.executeUpdate();
            if(commit){
                conn.commit();
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return regs > 0;
    }
    
    public ProdutoBean preencheProdutoBean(ResultSet rs){
        ProdutoBean prod = new ProdutoBean();
        try {
            prod.setId(rs.getLong("com_id"));
            prod.setNome(rs.getString("com_nome"));
            prod.setValor(rs.getDouble("com_preco"));
        } catch (SQLException sx){
            sx.printStackTrace();
        }
        return prod;
    }
    
    public BombaBean preencheBombaBemn(ResultSet rs, String aux){
        BombaBean bomba = new BombaBean();
        try {            
            if(aux.equals("")){
                bomba.setId(rs.getLong("bomba_id"));
                bomba.setCombustivelAux(rs.getString("combustivel_nome"));
            } else {
                bomba.setId(rs.getLong("bb_id"));
                bomba.setCombustivel(rs.getLong("bb_combustivel"));
            }
            
        } catch (SQLException sx){
            sx.printStackTrace();
        }
        return bomba;
    }
    
    public List<ProdutoBean> listarSQL(String sql){
        return listarSQL(sql,Conexao.getConnPublic());
    }
    
    public List<ProdutoBean> listarSQL(String sql, Connection conn){
        List<ProdutoBean> lista = new ArrayList<>();
        try{
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                lista.add(preencheProdutoBean(rs));
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return lista;
    }
    
    public List<BombaBean> listarSQL(String sql, String aux){
        return listarSQL(sql, aux, Conexao.getConnPublic());
    }
    
    public List<BombaBean> listarSQL(String sql, String aux, Connection conn){
        List<BombaBean> lista = new ArrayList<>();
        try{
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                lista.add(preencheBombaBemn(rs, aux));
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return lista;
    }
}
