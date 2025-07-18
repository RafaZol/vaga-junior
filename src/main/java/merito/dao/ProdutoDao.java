package merito.dao;

import merito.model.BombaModel;
import merito.model.ProdutoModel;
import merito.connection.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDao {

    private PreparedStatement pstm;
    private ResultSet rs;

    private String sql_insert = "INSERT INTO public.tb_combustivel (\n"
    + " com_id,"
    + " com_nome,\n"
    + " com_preco)\n"
    + " VALUES (?, ?, ?)";

private String sql_insert_bomba = "INSERT INTO tb_bomba (\n"
    + " bb_id,\n"
    + " bb_combustivel)\n"
    + " VALUES (?, ?)";

private String sql_update = "UPDATE tb_combustivel SET"
    + " com_nome = ?,"
    + " com_preco = ?"
    + " WHERE com_id = ?";

private String sql_updade_bomba = "UPDATE tb_bomba SET"
    + " bb_combustivel = ?"
    + " WHERE bb_id = ?";

private String sql_delete = "DELETE FROM tb_combustivel WHERE com_id = ?";

private String sql_delete_bomba = "DELETE FROM tb_bomba WHERE bb_id = ?";

    public long cadastrarProduto(ProdutoModel prod) throws ClassNotFoundException {
        return cadastrarProduto(prod, Conexao.getConnPublic(), true);
    }

    public long cadastrarProduto(ProdutoModel prod, Connection conn, boolean commit) {
        try {
            pstm = conn.prepareStatement(sql_insert);
            pstm.setLong(1, prod.getId());
            pstm.setString(2, prod.getNome());
            pstm.setDouble(3, prod.getValor());
            pstm.executeUpdate();
            if (commit) {
                conn.commit();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0L;
    }

    public boolean alterarProduto(ProdutoModel prod) throws ClassNotFoundException {
        return alterarProduto(prod, Conexao.getConnPublic(), true);
    }

    public boolean alterarProduto(ProdutoModel prod, Connection conn, boolean commit) {
        int regs = 0;
        try {
            pstm = conn.prepareStatement(sql_update);
            pstm.setString(1, prod.getNome());
            pstm.setDouble(2, prod.getValor());
            pstm.setLong(3, prod.getId());
            regs = pstm.executeUpdate();
            if (commit) {
                conn.commit();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return regs > 0;
    }

    public boolean excluirProduto(long id) throws ClassNotFoundException {
        return excluirProduto(id, Conexao.getConnPublic(), true);
    }

    public boolean excluirProduto(long id, Connection conn, boolean commit) {
        int regs = 0;
        try {
            pstm = conn.prepareStatement(sql_delete);
            pstm.setLong(1, id);
            regs = pstm.executeUpdate();
            if (commit) {
                conn.commit();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return regs > 0;
    }

    public long cadastrarBomba(BombaModel bomba) throws ClassNotFoundException {
        return cadastrarBomba(bomba, Conexao.getConnPublic(), true);
    }

    public long cadastrarBomba(BombaModel bomba, Connection conn, boolean commit) {
        try {
            pstm = conn.prepareStatement(sql_insert_bomba);
            pstm.setLong(1, bomba.getId());
            pstm.setLong(2, bomba.getCombustivel());
            pstm.executeUpdate();
            if (commit) {
                conn.commit();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0L;
    }

    public boolean alterarBomba(BombaModel bomba) throws ClassNotFoundException {
        return alterarBomba(bomba, Conexao.getConnPublic(), true);
    }

    public boolean alterarBomba(BombaModel bomba, Connection conn, boolean commit) {
        int regs = 0;
        try {
            pstm = conn.prepareStatement(sql_updade_bomba);
            pstm.setLong(1, bomba.getCombustivel());
            pstm.setLong(2, bomba.getId());
            regs = pstm.executeUpdate();
            if (commit) {
                conn.commit();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return regs > 0;
    }

    public boolean excluirBomba(long id) throws ClassNotFoundException {
        return excluirBomba(id, Conexao.getConnPublic(), true);
    }

    public boolean excluirBomba(long id, Connection conn, boolean commit) {
        int regs = 0;
        try {
            pstm = conn.prepareStatement(sql_delete_bomba);
            pstm.setLong(1, id);
            regs = pstm.executeUpdate();
            if (commit) {
                conn.commit();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return regs > 0;
    }

    public ProdutoModel preencherProdutoModel(ResultSet rs) {
        ProdutoModel prod = new ProdutoModel();
        try {
            prod.setId(rs.getLong("com_id"));
            prod.setNome(rs.getString("com_nome"));
            prod.setValor(rs.getDouble("com_preco"));
        } catch (SQLException sx) {
            sx.printStackTrace();
        }
        return prod;
    }

    public BombaModel preencherBombaModel(ResultSet rs, String aux) {
        BombaModel bomba = new BombaModel();
        try {
            if (aux.equals("sql")) {
                bomba.setId(rs.getLong("bb_id"));
                bomba.setCombustivel(rs.getLong("bb_combustivel"));
            } else {
                bomba.setId(rs.getLong("bomba_id"));
                bomba.setCombustivelAux(rs.getString("combustivel_nome"));
            }
        } catch (SQLException sx) {
            sx.printStackTrace();
        }
        return bomba;
    }

    public List<ProdutoModel> listarSQL(String sql) throws ClassNotFoundException {
        return listarSQL(sql, Conexao.getConnPublic());
    }

    public List<ProdutoModel> listarSQL() throws ClassNotFoundException {
        String sql = "SELECT * FROM tb_combustivel";
        return listarSQL(sql, Conexao.getConnPublic());
    }

    public List<ProdutoModel> listarSQL(String sql, Connection conn) {
        List<ProdutoModel> lista = new ArrayList<>();
        try {
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                lista.add(preencherProdutoModel(rs));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public List<BombaModel> listarSQLb(String aux) throws ClassNotFoundException {
        String sql = "SELECT * FROM tb_bomba";
        return listarSQLb(sql, aux, Conexao.getConnPublic());
    }

    public List<BombaModel> listarSQLb() throws ClassNotFoundException {
        String aux = "";
        String sql = "SELECT b.bb_id AS bomba_id, c.com_nome AS combustivel_nome "
                   + "FROM tb_bomba b "
                   + "JOIN tb_combustivel c ON b.bb_combustivel = c.com_id";
        return listarSQLb(sql, aux, Conexao.getConnPublic());
    }

    public List<BombaModel> listarSQLb(String sql, String aux, Connection conn) {
        List<BombaModel> lista = new ArrayList<>();
        try {
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                lista.add(preencherBombaModel(rs, aux));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lista;
    }
}
