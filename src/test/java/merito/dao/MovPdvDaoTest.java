package merito.dao;

import merito.model.MovpdvModel;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MovPdvDaoTest {

    private static Connection conn;
    private MovPdvDao dao;

    @BeforeAll
    public static void setupDatabase() throws SQLException {
        conn = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");

        try (Statement st = conn.createStatement()) {
            st.execute("""
                CREATE TABLE tb_combustivel (
                    com_id BIGINT PRIMARY KEY,
                    com_nome VARCHAR(100),
                    com_preco DOUBLE
                )
            """);
            st.execute("""
                CREATE TABLE tb_bomba (
                    bb_id BIGINT PRIMARY KEY,
                    bb_combustivel BIGINT,
                    FOREIGN KEY (bb_combustivel) REFERENCES tb_combustivel(com_id)
                )
            """);
            st.execute("""
                CREATE TABLE tb_mov_pdv (
                    mov_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    mov_bomba BIGINT,
                    mov_qtd FLOAT,
                    mov_cliente BIGINT,
                    mov_obs VARCHAR(100),
                    mov_valor FLOAT,
                    mov_valor_total FLOAT,
                    mov_desc FLOAT,
                    mov_time TIMESTAMP,
                    FOREIGN KEY (mov_bomba) REFERENCES tb_bomba(bb_id)
                )
            """);
            st.execute("INSERT INTO tb_combustivel (com_id, com_nome, com_preco) VALUES (1, 'Gasolina', 5.5)");
            st.execute("INSERT INTO tb_bomba (bb_id, bb_combustivel) VALUES (1, 1)");
        }
        conn.setAutoCommit(false);
    }

    @AfterAll
    public static void tearDown() throws SQLException {
        conn.close();
    }

    @BeforeEach
    public void setup() {
        dao = new MovPdvDao();
    }

    @AfterEach
    public void rollback() throws SQLException {
        conn.rollback();
    }

    @Test
    public void testCadastrarEListarMov() throws Exception {
        MovpdvModel mov = new MovpdvModel();
        String sql = "SELECT m.mov_id, m.mov_qtd, m.mov_obs, m.mov_valor_total, m.mov_desc, "
               + "m.mov_time, m.mov_cliente, m.mov_bomba, m.mov_valor, "
               + "c.com_nome AS comb_nome "
               + "FROM tb_mov_pdv m "
               + "JOIN tb_bomba b ON m.mov_bomba = b.bb_id "
               + "JOIN tb_combustivel c ON b.bb_combustivel = c.com_id "
               + "ORDER BY m.mov_time DESC";


        mov.setBomba(1L);
        mov.setQuantidade(10f);
        mov.setCliente(123L);
        mov.setObs("Teste");
        mov.setValor(5.5f);
        mov.setValorTotal(55f);
        mov.setDesconto(0f);
        mov.setHora(LocalDateTime.now());

        dao.cadastrarMov(mov, conn, false);

        List<MovpdvModel> lista = dao.listarSQL(sql, conn);

        assertFalse(lista.isEmpty(), "Deve retornar pelo menos um registro");
        boolean achou = lista.stream().anyMatch(m -> "Teste".equals(m.getObs()));
        assertTrue(achou, "sucesso");
    }

    @Test
    public void testAlterarMov() throws Exception {
        MovpdvModel mov = new MovpdvModel();
        String sql = "SELECT m.mov_id, m.mov_qtd, m.mov_obs, m.mov_valor_total, m.mov_desc, "
               + "m.mov_time, m.mov_cliente, m.mov_bomba, m.mov_valor, "
               + "c.com_nome AS comb_nome "
               + "FROM tb_mov_pdv m "
               + "JOIN tb_bomba b ON m.mov_bomba = b.bb_id "
               + "JOIN tb_combustivel c ON b.bb_combustivel = c.com_id "
               + "ORDER BY m.mov_time DESC";

        mov.setBomba(1L);
        mov.setQuantidade(5f);
        mov.setCliente(123L);
        mov.setObs("alterar");
        mov.setValor(5.5f);
        mov.setValorTotal(27.5f);
        mov.setDesconto(0f);
        mov.setHora(LocalDateTime.now());

        dao.cadastrarMov(mov, conn, false);

        List<MovpdvModel> lista = dao.listarSQL(sql, conn);
        MovpdvModel movInserido = lista.stream()
            .filter(m -> "alterar".equals(m.getObs()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Registro não encontrado"));

        movInserido.setObs("alterado");
        movInserido.setQuantidade(7f);

        boolean alterou = dao.alterarMov(movInserido, conn, false);
        assertTrue(alterou, "sucesso");

        List<MovpdvModel> listaAtualizada = dao.listarSQL(sql, conn);
        boolean achouAlterado = listaAtualizada.stream()
            .anyMatch(m -> "alterado".equals(m.getObs()) && m.getQuantidade() == 7f);
        assertTrue(achouAlterado, "sucesso");
    }

    @Test
    public void testExcluirMov() throws Exception {
        MovpdvModel mov = new MovpdvModel();
        String sql = "SELECT m.mov_id, m.mov_qtd, m.mov_obs, m.mov_valor_total, m.mov_desc, "
               + "m.mov_time, m.mov_cliente, m.mov_bomba, m.mov_valor, "
               + "c.com_nome AS comb_nome "
               + "FROM tb_mov_pdv m "
               + "JOIN tb_bomba b ON m.mov_bomba = b.bb_id "
               + "JOIN tb_combustivel c ON b.bb_combustivel = c.com_id "
               + "ORDER BY m.mov_time DESC";


        mov.setBomba(1L);
        mov.setQuantidade(5f);
        mov.setCliente(123L);
        mov.setObs("excluir");
        mov.setValor(5.5f);
        mov.setValorTotal(27.5f);
        mov.setDesconto(0f);
        mov.setHora(LocalDateTime.now());

        dao.cadastrarMov(mov, conn, false);

        List<MovpdvModel> lista = dao.listarSQL(sql, conn);
        MovpdvModel movInserido = lista.stream()
            .filter(m -> "excluir".equals(m.getObs()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Registro não encontrado"));

        boolean excluiu = dao.excluirMov(movInserido.getId(), conn, false);
        assertTrue(excluiu, "sucesso");

        List<MovpdvModel> listaAtualizada = dao.listarSQL(sql, conn);
        boolean achou = listaAtualizada.stream()
            .anyMatch(m -> "excluir".equals(m.getObs()));
        assertFalse(achou, "sucesso");
    }
}
