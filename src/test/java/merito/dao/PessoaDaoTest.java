package merito.dao;

import merito.model.PessoaModel;
import merito.model.UserModel;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PessoaDaoTest {

    private PessoaDao dao;
    private Connection conn;

    @BeforeAll
    public void setup() throws Exception {
        conn = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");

        try (Statement stmt = conn.createStatement()) {
            stmt.execute("""
                CREATE TABLE tb_pessoas (
                    tb_pessoa_id BIGINT PRIMARY KEY,
                    tb_name VARCHAR(100),
                    tb_idade INT,
                    tb_documento BIGINT,
                    tb_telefone BIGINT,
                    tb_email VARCHAR(100),
                    tb_tipo_pessoa INT
                )
                """);

            stmt.execute("""
                CREATE TABLE users (
                    usu_id BIGINT PRIMARY KEY,
                    usu_name VARCHAR(100),
                    usu_pass VARCHAR(100),
                    tb_pessoa_id BIGINT,
                    FOREIGN KEY (tb_pessoa_id) REFERENCES tb_pessoas(tb_pessoa_id)
                )
                """);

               stmt.execute(" INSERT INTO tb_pessoas ( tb_pessoa_id, tb_name,\n"
                + " tb_idade, tb_telefone, tb_email, tb_tipo_pessoa, tb_documento)\n"
                + " VALUES \n"
                + " (1, 'admin', 20, 55999999999, 'admin@admin.com', 1, 11111111111)");
                stmt.execute("INSERT INTO users (usu_id, usu_name, usu_pass, tb_pessoa_id) VALUES ('1', 'admin', 'admin', '1')");
        }

        conn.setAutoCommit(false);
        dao = new PessoaDao();
    }

    @AfterAll
    public void tearDown() throws Exception {
        if (conn != null && !conn.isClosed()) {
            conn.rollback();
            conn.close();
        }
    }

    private PessoaModel criarPessoa() {
        PessoaModel p = new PessoaModel();
        p.setId(System.currentTimeMillis());
        p.setNome("Teste");
        p.setIdade(30);
        p.setDoc(12345678901L);
        p.setTelefone(11999999999L);
        p.setEmail("teste@teste.com");
        p.setTipo(1);
        return p;
    }

    private UserModel criarUser(Long pessoaId) {
        UserModel u = new UserModel();
        u.setUserId(System.currentTimeMillis());
        u.setNome("admin");
        u.setPass("admin");
        u.setPessoaId(pessoaId);
        return u;
    }

    @Test
    public void testCadastrarAlterarExcluirPessoa() throws Exception {
        PessoaModel p = criarPessoa();

        long id = dao.cadastrar(p, conn, false);

        List<PessoaModel> lista = dao.listarSQL("SELECT * FROM tb_pessoas", conn);
        boolean cadastrado = lista.stream().anyMatch(person -> person.getId().equals(p.getId()));
        assertTrue(cadastrado, "cadastrar pessoa");

        p.setNome("Pessoa Alterada");
        boolean alterou = dao.alterar(p, conn, false);
        assertTrue(alterou, "alterar pessoa");

        lista = dao.listarSQL("SELECT * FROM tb_pessoas", conn);
        boolean alterado = lista.stream()
            .anyMatch(person -> person.getId().equals(p.getId()) && "Pessoa Alterada".equals(person.getNome()));
        assertTrue(alterado, "Pessoa alterada");

        boolean excluiu = dao.excluir(p.getId(), conn, false);
        assertTrue(excluiu, "excluir pessoa");

        lista = dao.listarSQL("SELECT * FROM tb_pessoas", conn);
        boolean existe = lista.stream().anyMatch(person -> person.getId().equals(p.getId()));
        assertFalse(existe, "Pessoa excluída");
    }

    @Test
    public void testCadastrarAlterarExcluirUser() throws Exception {
        PessoaModel p = criarPessoa();
        dao.cadastrar(p, conn, false);

        UserModel u = criarUser(p.getId());
        long userId = dao.cadastrarUser(u, conn, false);

        List<UserModel> listaUser = dao.listarUser("SELECT * FROM users", conn);
        boolean cadastrado = listaUser.stream().anyMatch(user -> user.getUserId() == u.getUserId());
        assertTrue(cadastrado, "Ususario cadastrado");

        u.setNome("userAlterado");
        long alterou = dao.alterarUser(u, conn, false);

        listaUser = dao.listarUser("SELECT * FROM users", conn);
        boolean alterado = listaUser.stream()
            .anyMatch(user -> user.getUserId() == u.getUserId() && "userAlterado".equals(user.getNome()));
        assertTrue(alterado, "Usuario alterado");

        boolean excluiu = dao.excluirUser(u.getUserId(), conn, false);
        assertTrue(excluiu, "Usuario excluído");

        listaUser = dao.listarUser("SELECT * FROM users", conn);
        boolean existe = listaUser.stream().anyMatch(user -> user.getUserId() == u.getUserId());
        assertFalse(existe, "sucesso");
    }

}
