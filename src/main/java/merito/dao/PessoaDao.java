package merito.dao;

import merito.model.PessoaModel;
import merito.model.UserModel;
import java.sql.Connection;
import javax.swing.JOptionPane;
import merito.connection.Conexao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class PessoaDao {
    
    private PreparedStatement pstm;
    private ResultSet rs;
    
    private String sql_insert = "INSERT INTO tb_pessoas (\n"
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
    
    private String sql_insert_user = "INSERT INTO users (\n"
            + " usu_name,\n"
            + " usu_pass,\n"
            + " tb_pessoa_id,\n"
            + " usu_id)\n"
            + " VALUES (?, ?, ?, ?)";
    
    private String sql_update_user = "UPDATE users SET\n"
            + " usu_name = ?,\n"
            + " tb_pessoa_id = ?\n"
            + " WHERE usu_id = ?";
    
    private String sql_delete_user = "DELETE FROM users WHERE usu_id = ?";
public long cadastrar(PessoaModel model) throws ClassNotFoundException{
    return cadastrar(model, Conexao.getConnPublic(), true);
}

public long cadastrar(PessoaModel model, Connection conn, boolean commit){
    try {
        pstm = conn.prepareStatement(sql_insert);
        pstm.setString(1, model.getNome());
        pstm.setInt(2, model.getIdade());
        pstm.setLong(3, model.getDoc());
        pstm.setLong(4, model.getTelefone());
        pstm.setString(5, model.getEmail());
        pstm.setInt(6, model.getTipo());
        pstm.setLong(7, model.getId());
        pstm.executeUpdate();
        if(commit){
            conn.commit();
        }
    } catch (Exception e){
        e.printStackTrace();
    }
    return 0;
}

public boolean alterar(PessoaModel model) throws ClassNotFoundException{
    return alterar(model, Conexao.getConnPublic(), true);
}

public boolean alterar(PessoaModel model, Connection conn, boolean commit){
    int regs = 0;
    try {
        pstm = conn.prepareStatement(sql_update);
        pstm.setString(1, model.getNome());
        pstm.setInt(2, model.getIdade());
        pstm.setLong(3, model.getDoc());
        pstm.setLong(4, model.getTelefone());
        pstm.setString(5, model.getEmail());
        pstm.setInt(6, model.getTipo());
        pstm.setLong(7, model.getId());

        regs = pstm.executeUpdate();
        if(commit){
            conn.commit();
        }
    } catch (Exception e){
        e.printStackTrace();
    }
    return regs > 0;
}

public boolean excluir(Long id) throws ClassNotFoundException{
    return excluir(id, Conexao.getConnPublic(), true);
}

public boolean excluir(Long id, Connection conn, boolean commit){
    int rg = 0;
    try {
        pstm = conn.prepareStatement(sql_delete);
        pstm.setLong(1, id);
        rg = pstm.executeUpdate();
        if(commit){
            conn.commit();
        }
    } catch (Exception e){
        JOptionPane.showMessageDialog(null, "Nenhum registro encontrado\n" + e);
    }
    return rg > 0;
}


public List<PessoaModel> listarSQL() throws ClassNotFoundException{
    String sql = "SELECT * FROM public.tb_pessoas";
    return listarSQL(sql, Conexao.getConnPublic());
}

public List<PessoaModel> listarSQL(String sql) throws ClassNotFoundException{
    return listarSQL(sql, Conexao.getConnPublic());
}

public List<PessoaModel> listarSQL(String sql, Connection conn){
    List<PessoaModel> lista = new ArrayList<>();
    try {
        pstm = conn.prepareStatement(sql);
        rs = pstm.executeQuery();
        while(rs.next()){
            lista.add(preencherModel(rs));
        }
    } catch (Exception e){
        e.printStackTrace();
    }
    return lista;
}

public long cadastrarUser(UserModel model) throws ClassNotFoundException{
    return cadastrarUser(model, Conexao.getConnPublic(), true);
}

public long cadastrarUser(UserModel model, Connection conn, boolean commit){
    try {
        pstm = conn.prepareStatement(sql_insert_user);
        pstm.setString(1, model.getNome());
        pstm.setString(2, model.getPass());
        pstm.setLong(3, model.getPessoaId());
        pstm.setLong(4, model.getUserId());
        pstm.executeUpdate();
        if(commit){
            conn.commit();
        }
    } catch (Exception e){
        e.printStackTrace();
    }
    return 0;
}

public long alterarUser(UserModel model) throws ClassNotFoundException{
    return alterarUser(model, Conexao.getConnPublic(), true);
}

public long alterarUser(UserModel model, Connection conn, boolean commit){
    try {
        pstm = conn.prepareStatement(sql_update_user);
        pstm.setString(1, model.getNome());
        pstm.setLong(2, model.getPessoaId());
        pstm.setLong(3, model.getUserId());
        pstm.executeUpdate();
        if(commit){
            conn.commit();
        }
    } catch (Exception e){
        e.printStackTrace();
    }
    return 0;
}

public boolean excluirUser(Long id) throws ClassNotFoundException{
    return excluirUser(id, Conexao.getConnPublic(), true);
}

public boolean excluirUser(Long id, Connection conn, boolean commit){
    int regs = 0;
    try {
        pstm = conn.prepareStatement(sql_delete_user);
        pstm.setLong(1, id);
        regs = pstm.executeUpdate();
        if(commit){
            conn.commit();
        }
    } catch (Exception e){
        JOptionPane.showMessageDialog(null, "Nenhum registro encontrado\n" + e);
    }
    return regs > 0;
}

public List<UserModel> listarUser() throws ClassNotFoundException{
    String sql = "SELECT p.tb_name, "
               + "p.tb_pessoa_id AS pessoa_id, "
               + "u.usu_name, "
               + "u.tb_pessoa_id AS tb_pessoa_id, "
               + "u.usu_id, "
               + "u.usu_pass "
               + "FROM tb_pessoas p "
               + "INNER JOIN users u ON p.tb_pessoa_id = u.tb_pessoa_id";
    return listarUser(sql, Conexao.getConnPublic());
}

public List<UserModel> listarUser(String sql) throws ClassNotFoundException{
    return listarUser(sql, Conexao.getConnPublic());
}

public List<UserModel> listarUser(String sql, Connection conn){
    List<UserModel> lista = new ArrayList<>();
    try {
        pstm = conn.prepareStatement(sql);
        rs = pstm.executeQuery();
        while(rs.next()){
            lista.add(preencherUserModel(rs));
        }
    } catch (Exception e){
        e.printStackTrace();
    }
    return lista;
}

public boolean vereficarCredenciais(String idUsu, String pass) throws SQLException, ClassNotFoundException {
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

private PessoaModel preencherModel(ResultSet rs){
    PessoaModel model = new PessoaModel();
    try {
        model.setId(rs.getLong("tb_pessoa_id"));
        model.setNome(rs.getString("tb_name"));
        model.setIdade(rs.getInt("tb_idade"));
        model.setDoc(rs.getLong("tb_documento"));
        model.setTipo(rs.getInt("tb_tipo_pessoa"));
        model.setTelefone(rs.getLong("tb_telefone"));
        model.setEmail(rs.getString("tb_email"));
    } catch(SQLException e){
        e.printStackTrace();
    }
    return model;
}

private UserModel preencherUserModel(ResultSet rs){
    UserModel user = new UserModel();
    try {
        user.setUserId(rs.getLong("usu_id"));
        user.setPessoaId(rs.getLong("tb_pessoa_id"));
        user.setNome(rs.getString("usu_name"));
        user.setPass(rs.getString("usu_pass"));
    } catch(SQLException e){
        e.printStackTrace();
    }
    return user;
}

}