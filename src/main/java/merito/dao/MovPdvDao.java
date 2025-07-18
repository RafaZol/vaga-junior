package merito.dao;

import merito.model.MovpdvModel;
import merito.connection.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MovPdvDao {

    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    private String sql_insert = "INSERT INTO tb_mov_pdv (\n" +
        " mov_id,\n" + 
        " mov_bomba,\n" +
        " mov_qtd,\n" +
        " mov_cliente,\n" +
        " mov_obs,\n" +
        " mov_valor,\n" +
        " mov_valor_total,\n" +
        " mov_desc,\n" +
        " mov_time)\n" +
        " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private String sql_update = "UPDATE tb_mov_pdv SET \n" +
        "mov_bomba = ?,\n" +
        "mov_qtd = ?,\n" +
        "mov_cliente = ?,\n" +
        "mov_obs = ?,\n" +
        "mov_valor = ?,\n" +
        "mov_valor_total = ?,\n" +
        "mov_desc = ?\n" +
        "WHERE mov_id = ?";

    private String sql_delete = "DELETE FROM tb_mov_pdv WHERE mov_id = ?";

    public void cadastrarMov(MovpdvModel model) throws ClassNotFoundException {
        cadastrarMov(model, Conexao.getConnPublic(), true);
    }

    public void cadastrarMov(MovpdvModel model, Connection conn, boolean commit) {
        try {
            preparedStatement = conn.prepareStatement(sql_insert);
            preparedStatement.setLong(1, model.getId());
            preparedStatement.setLong(2, model.getBomba());
            preparedStatement.setFloat(3, model.getQuantidade());

            if (model.getCliente() != 0f) {
                preparedStatement.setLong(4, model.getCliente());
            } else {
                preparedStatement.setLong(4, 0);
            }

            preparedStatement.setString(5, model.getObs());
            preparedStatement.setFloat(6, model.getValor());
            preparedStatement.setFloat(7, model.getValorTotal());

            if (model.getDesconto() != 0f) {
                preparedStatement.setFloat(8, model.getDesconto());
            } else {
                preparedStatement.setFloat(8, 0f);
            }

            preparedStatement.setTimestamp(9, Timestamp.valueOf(model.getHora()));
            preparedStatement.executeUpdate();

            if (commit) {
                conn.commit();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean alterarMov(MovpdvModel model) throws ClassNotFoundException {
        return alterarMov(model, Conexao.getConnPublic(), true);
    }

    public boolean alterarMov(MovpdvModel model, Connection conn, boolean commit) {
        int registrosAfetados = 0;
        try {
            preparedStatement = conn.prepareStatement(sql_update);

            preparedStatement.setLong(1, model.getBomba());
            preparedStatement.setFloat(2, model.getQuantidade());

            if (model.getCliente() != 0f) {
                preparedStatement.setLong(3, model.getCliente());
            } else {
                preparedStatement.setLong(3, 0);
            }

            preparedStatement.setString(4, model.getObs());
            preparedStatement.setFloat(5, model.getValor());
            preparedStatement.setFloat(6, model.getValorTotal());

            if (model.getDesconto() != 0f) {
                preparedStatement.setFloat(7, model.getDesconto());
            } else {
                preparedStatement.setFloat(7, 0f);
            }

            preparedStatement.setLong(8, model.getId());

            registrosAfetados = preparedStatement.executeUpdate();

            if (commit) {
                conn.commit();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return registrosAfetados > 0;
    }

    public boolean excluirMov(long id) throws ClassNotFoundException {
        return excluirMov(id, Conexao.getConnPublic(), true);
    }

    public boolean excluirMov(long id, Connection conn, boolean commit) {
        int registrosAfetados = 0;
        try {
            preparedStatement = conn.prepareStatement(sql_delete);
            preparedStatement.setLong(1, id);
            registrosAfetados = preparedStatement.executeUpdate();

            if (commit) {
                conn.commit();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return registrosAfetados > 0;
    }

    public MovpdvModel preencherModel(ResultSet rs) {
        MovpdvModel model = new MovpdvModel();
        try {
            model.setId(rs.getLong("mov_id"));
            model.setQuantidade(rs.getFloat("mov_qtd"));
            model.setObs(rs.getString("mov_obs"));
            model.setValorTotal(rs.getFloat("mov_valor_total"));
            model.setDesconto(rs.getFloat("mov_desc"));
            model.setHora(rs.getTimestamp("mov_time").toLocalDateTime());

            if (!rs.wasNull()) {
                model.setCliente(rs.getLong("mov_cliente"));
            }

            if (!rs.wasNull()) {
                model.setBomba(rs.getLong("mov_bomba"));
            }

            model.setValor(rs.getFloat("mov_valor"));
            model.setCombustivelNome(rs.getString("comb_nome"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return model;
    }

    public List<MovpdvModel> listarSQL() throws ClassNotFoundException {
        String sql = "SELECT m.mov_id, m.mov_qtd, m.mov_obs, m.mov_valor_total, m.mov_desc, "
                   + "m.mov_time, m.mov_cliente, m.mov_bomba, m.mov_valor, "
                   + "c.com_nome AS comb_nome "
                   + "FROM tb_mov_pdv m "
                   + "JOIN tb_bomba b ON m.mov_bomba = b.bb_id "
                   + "JOIN tb_combustivel c ON b.bb_combustivel = c.com_id "
                   + "ORDER BY m.mov_time DESC";

        return listarSQL(sql, Conexao.getConnPublic());
    }

    public List<MovpdvModel> listarSQL(String sql, Connection conn) {
        List<MovpdvModel> lista = new ArrayList<>();
        try {
            preparedStatement = conn.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                lista.add(preencherModel(resultSet));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lista;
    }
}
