/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exercicioMeritoControls;

import exercicioMeritoBeans.PessoaBean;
import exercicioMeritoBeans.UserBean;
import exercicioMeritoConnections.Conexao;
import java.sql.Connection;
import javax.swing.JOptionPane;
import exercicioMeritoConnections.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Rafael
 */
public class PessoaControl {
    
    private PreparedStatement pstm;
    private ResultSet rs;
    
    private String sql_cadastra = "INSERT INTO tb_pessoas (\n"
            + " tb_name,\n"
            + " tb_idade,\n" 
            + " tb_documento,\n"
            + " tb_telefone,\n"
            + " tb_email,\n" 
            + " tb_tipo_pessoa,"
            + " tb_pessoa_id)\n"
            + " VALUES (?, ?, ?, ?, ?, ?, ?)";
    
    private String sql_update = "UPDATE tb_pessoas SET\n"
            + " tb_name = ?,\n"
            + " tb_idade = ?,\n" 
            + " tb_documento = ?,\n"
            + " tb_telefone = ?,\n"
            + " tb_email = ?,\n" 
            + " tb_tipo_pessoa = ?\n"
            + " WHERE tb_pessoa_id = ?";
    
    private String sql_delete = "DELETE FROM tb_pessoas WHERE tb_pessoa_id = ?";
    
    private String sql_cadastra_user = "INSERT INTO users (\n"
            + " usu_name,\n"
            + " usu_pass,\n"
            + " tb_pessoa_id,\n"
            + " usu_ud)\n"
            + " VALUES (?, ?, ?, ?)";
    
    private String sql_update_user = "UPDATE users SET\n"
            + " usu_name = ?,\n"
            + " tb_pessoa_id = ?\n"
            + " WHERE usu_id = ?";
    
    private String sql_delete_user = "DELETE FROM users WHERE usu_id = ?";
    
    public long cadastrar(PessoaBean bean){
        return cadastrar(bean, Conexao.getConnPublic(), true);
    }
    
    public long cadastrar(PessoaBean bean, Connection conn, boolean commit){
        try {
            pstm = conn.prepareStatement(sql_cadastra);
            
            pstm.setString(1, bean.getNome());
            pstm.setInt(2, bean.getIdade());
            pstm.setLong(3, bean.getDoc());
            pstm.setLong(4, bean.getTelefone());
            pstm.setString(5, bean.getEmail());
            pstm.setInt(6, bean.getTipo());
            pstm.setLong(7, bean.getId());
            int rs = pstm.executeUpdate();
            if(commit){
                conn.commit();
            }
                    
        } catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
    
    public boolean alterar(PessoaBean bean){
        return alterar(bean, Conexao.getConnPublic(), true);
    }
    
    public boolean alterar(PessoaBean bean, Connection conn, boolean commit){
        int regs = 0;
        try{
            pstm = conn.prepareStatement(sql_update);
        
            pstm.setString(1, bean.getNome());
            pstm.setInt(2, bean.getIdade());
            pstm.setLong(3, bean.getDoc());
            pstm.setLong(4, bean.getTelefone());
            pstm.setString(5, bean.getEmail());
            pstm.setInt(6, bean.getTipo());
            pstm.setLong(7, bean.getId());
        
            regs = pstm.executeUpdate();
            if(commit){
                conn.commit();
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return regs > 0;
                
    }
    
    
    public boolean excluir(Long id){
        return excluir(id, Conexao.getConnPublic(), true);
    }
    
    public boolean excluir(long id, Connection conn, Boolean commit){
        int rg = 0;
        try{
            pstm=conn.prepareStatement(sql_delete);
            pstm.setLong(1, id);
            rg = pstm.executeUpdate();
            if(commit){
                conn.commit();;
            }
        } catch (Exception ex){
            JOptionPane.showMessageDialog(null, "Nenhum registro encontrado" + ex);
        }
        return rg > 0;
    }
    
    public List<PessoaBean> listarSQL(String sql){
        return listarSQL(sql, Conexao.getConnPublic());
    }
    
    public List<PessoaBean> listarSQL(String sql, Connection conn){
        List<PessoaBean> lista = new ArrayList<>();
        try{
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                lista.add(preencheBean(rs));
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return lista;
    }
    
    public List<UserBean> listarUser(String sql){
        return listarUser(sql, Conexao.getConnPublic());
    }
    
    public List<UserBean> listarUser(String sql, Connection conn){
        List<UserBean> lista = new ArrayList<>();
        try{
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            while(rs.next()){
                lista.add(preencheUserBean(rs));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lista;
    }
    
    public long gerarId(int i){
        return gerarId(i, Conexao.getConnPublic());
    }
    
    public long gerarId(int i, Connection conn){
        String sql = "";
        if(i == 0){
            sql = "SELECT MAX(tb_pessoa_id) AS ultimo_id FROM tb_pessoas";
        } else if (i == 1) {
            sql = "SELECT MAX(usu_id) AS ultimo_id FROM userss";
        } else {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar usuário");
        }   
        try (
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("ultimo_id") + 1;
            }

        } catch (SQLException sx) {
            sx.printStackTrace();
        }
        return 0;
    }
    
    public long cadastraUser(UserBean user){
        return cadastraUser(user, Conexao.getConnPublic(), true);
    }
    
    public long cadastraUser(UserBean user, Connection conn, boolean commit){
        int regs = 0;
        try{
            pstm = conn.prepareStatement(sql_cadastra_user);
            
            pstm.setString(1, user.getNome());
            pstm.setString(2, user.getPass());
            pstm.setLong(3, user.getPessoaId());
            pstm.setLong(4, user.getUserId());
            
            regs = pstm.executeUpdate();
            if(commit){
                conn.commit();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }
    
    
    
    public long alteraUser(UserBean user){
        return alteraUser(user, Conexao.getConnPublic(), true);
    }
    
    private long alteraUser(UserBean user, Connection conn, boolean commit){
        int regs = 0;
        try {
            pstm = conn.prepareStatement(sql_update_user);
            
            pstm.setString(1, user.getNome());
            pstm.setLong(2, user.getPessoaId());
            pstm.setLong(3, user.getUserId());
            
            regs = pstm.executeUpdate();
            if(commit){
                conn.commit();
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return 0;
    }
    
    public boolean excluirUser(Long user){
        return excluirUser(user, Conexao.getConnPublic(), true);
    }
    
    public boolean excluirUser(Long user, Connection conn, boolean commit){
        int regs = 0;
        try{
            pstm = conn.prepareStatement(sql_delete_user);
            pstm.setLong(1, user);
            
            regs = pstm.executeUpdate();
            if(commit){
                conn.commit();
            }
            
        } catch (Exception ex){
            JOptionPane.showMessageDialog(null, "Nenhum registro encontrado" + ex);
        }
        return regs > 0;
    }
    
    private PessoaBean preencheBean(ResultSet rs){
        PessoaBean bean = new PessoaBean();
        try{
            bean.setId(rs.getLong("tb_pessoa_id"));
            bean.setNome(rs.getString("tb_name"));
            bean.setIdade(rs.getInt("tb_idade"));
            bean.setDoc(rs.getLong("tb_documento"));
            bean.setTipo(rs.getInt("tb_tipo_pessoa"));
            bean.setTelefone(rs.getLong("tb_telefone"));
            bean.setEmail(rs.getString("tb_email"));
        } catch(SQLException sx){
            sx.printStackTrace();
        }
        return bean;
    }
    
    private UserBean preencheUserBean (ResultSet rs){
        UserBean user = new UserBean();
        try{
            user.setUserId(rs.getLong("user_pessoa_id"));
            user.setPessoaId(rs.getLong("pessoa_id"));
            user.setNome(rs.getString("usu_name"));
            user.setPessoa(rs.getString("tb_name"));
        } catch (SQLException sx){
            sx.printStackTrace();
        }
        return user;
    }
    
    public boolean vereficaCredenciais(String idUsu, String pass ) throws SQLException {
        boolean resultado = false;
        String sql = "SELECT * FROM users WHERE usu_name = ? AND usu_pass = ?";
        pstm = Conexao.getConnPublic().prepareStatement(sql);
        pstm.setString(1, idUsu);
        pstm.setString(2, pass);
        rs = pstm.executeQuery();
        if(rs.next()){
            resultado = true;
        }
        rs.close();
        pstm.close();
        return resultado;
    }
    
}
