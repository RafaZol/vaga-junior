package merito.dao;

import merito.model.BombaModel;
import merito.model.ProdutoModel;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProdutoDaoTest {

    private ProdutoDao dao;
    private Connection conn;

    @BeforeAll
    public void setup() throws Exception {
        conn = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");

        try (Statement pstm = conn.createStatement()) { 
            pstm.execute("DROP TABLE IF EXISTS tb_bomba CASCADE");         
            pstm.execute("""
                CREATE TABLE IF NOT EXISTS tb_combustivel (
                    com_id BIGINT PRIMARY KEY,
                    com_nome VARCHAR(100),
                    com_preco DOUBLE
                )
                """);


            pstm.execute("""
                CREATE TABLE tb_bomba (
                    bb_id BIGINT PRIMARY KEY,
                    bb_combustivel BIGINT,
                    FOREIGN KEY (bb_combustivel) REFERENCES tb_combustivel(com_id)
                )
                """);

            pstm.execute("INSERT INTO tb_combustivel (com_id, com_nome, com_preco)\n"
                                        + " VALUES (2, 'Etanol', 5.99)");
            pstm.execute("INSERT INTO tb_bomba (bb_id, bb_combustivel) VALUES (1, 2)");
        }

        conn.setAutoCommit(false);
        dao = new ProdutoDao();
    }

    @AfterAll
    public void tearDown() throws Exception {
        if (conn != null && !conn.isClosed()) {
            conn.rollback();
            conn.close();
        }
    }

    private ProdutoModel criarProduto() {
        ProdutoModel p = new ProdutoModel();
        p.setId(System.currentTimeMillis());
        p.setNome("Gasolina");
        p.setValor(5.49);
        return p;
    }

    private BombaModel criarBomba(Long combustivelId) {
        BombaModel b = new BombaModel();
        b.setId(System.currentTimeMillis());
        b.setCombustivel(combustivelId);
        return b;
    }

    @Test
    public void testCadastrarAlterarExcluirProduto() throws Exception {
        ProdutoModel p = criarProduto();

        String sql = "SELECT * FROM tb_combustivel";
        long id = dao.cadastrarProduto(p, conn, false);

        List<ProdutoModel> lista = dao.listarSQL(sql, conn);
        boolean cadastrado = lista.stream().anyMatch(prod -> prod.getId() == p.getId());
        assertTrue(cadastrado, "Produto cadastrado");

        p.setNome("Diesel");
        p.setValor(6.99);

        boolean alterou = dao.alterarProduto(p, conn, false);
        assertTrue(alterou, "Produto alterado");

        lista = dao.listarSQL(sql, conn);
        boolean alterado = lista.stream()
            .anyMatch(prod -> prod.getId() == p.getId() && "Diesel".equals(prod.getNome()) && prod.getValor() == 6.99);
        assertTrue(alterado, "sucesso");

        boolean excluiu = dao.excluirProduto(p.getId(), conn, false);
        assertTrue(excluiu, "Produto excluído");

        lista = dao.listarSQL(sql, conn);
        boolean existe = lista.stream().anyMatch(prod -> prod.getId() == p.getId());
        assertFalse(existe, "sucesso");
    }

    @Test
    public void testCadastrarAlterarExcluirBomba() throws Exception {
        ProdutoModel p = criarProduto();
        dao.cadastrarProduto(p, conn, false);

        BombaModel b = criarBomba(p.getId());
        String sql = "SELECT * FROM tb_bomba";
        long bombaId = dao.cadastrarBomba(b, conn, false);

        List<BombaModel> listaBomba = dao.listarSQLb(sql, "sql", conn);
        boolean cadastrado = listaBomba.stream().anyMatch(bomba -> bomba.getId() == b.getId());
        assertTrue(cadastrado, "Bomba cadastrada");

        b.setCombustivel(p.getId()); 

        boolean alterou = dao.alterarBomba(b, conn, false);
        assertTrue(alterou, "Bomba alterada");

        listaBomba = dao.listarSQLb(sql, "sql", conn);
        boolean alterado = listaBomba.stream()
            .anyMatch(bomba -> bomba.getId() == b.getId() && bomba.getCombustivel() == p.getId());
        assertTrue(alterado, "Sucesso");

        boolean excluiu = dao.excluirBomba(b.getId(), conn, false);
        assertTrue(excluiu, "Bomba excluída");

        listaBomba = dao.listarSQLb(sql, "sql", conn);
        boolean existe = listaBomba.stream().anyMatch(bomba -> bomba.getId() == b.getId());
        assertFalse(existe, "Sucesso");
    }
}
