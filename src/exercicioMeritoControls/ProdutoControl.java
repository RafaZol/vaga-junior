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

/**
 *
 * @author Rafael
 */
public class ProdutoControl {
    
    private PreparedStatement pstm;
    private ResultSet rs;
    
    private String cadastraProduto = "INSERT INTO";
    
    private String cadastraBomba = "INSERT INTO";
    
    private String alteraProduto = "UPDATE SET";
    
    private String alteraBomba = "UPDATE SET";
    
    private String excluiProduto = "DELETE FROM";
    
    private String excluiBomba = "DELETE FROM";

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
            pstm.setString(2, bomba.getCombustivel());
            
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
            
            pstm.setString(1, bomba.getCombustivel());
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
    
    public ProdutoBean preenchePreodutoBean(ResultSet rs){
        ProdutoBean prod = new ProdutoBean();
        try {
            prod.setId(rs.getLong(""));
            prod.setNome(rs.getString(""));
            prod.setValor(rs.getDouble(""));
        } catch (SQLException sx){
            sx.printStackTrace();
        }
        return prod;
    }
    
    public BombaBean preencheBombaBemn(ResultSet rs){
        BombaBean bomba = new BombaBean();
        try {
            bomba.setId(rs.getLong(""));
            bomba.setCombustivel(rs.getString(""));
        } catch (SQLException sx){
            sx.printStackTrace();
        }
        return bomba;
    }
    
}
