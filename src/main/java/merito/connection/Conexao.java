package merito.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexao {

    private static Connection connPublic;

    public static Connection getConnPublic() throws ClassNotFoundException {
        if (connPublic == null) {
            try {
                Properties props = ConfiguracaoBanco.carregarConfiguracoes();

                String tipoConexao = props.getProperty("tipoConexao");
                String host = props.getProperty("host");
                String porta = props.getProperty("porta");
                String banco = props.getProperty("banco");
                String usuario = props.getProperty("usuario");
                String senha = props.getProperty("senha");

                connPublic = conectar(tipoConexao, host, usuario, senha, banco, porta, "");
                connPublic.setAutoCommit(false);

            } catch (SQLException | java.io.IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Erro ao conectar com o banco de dados.");
            }
        }
        return connPublic;
    }

    public static void setConnPublic(Connection connPublic) {
        Conexao.connPublic = connPublic;
    }

    public static Connection conectar(String tipoConexao, String host, String usuario, String senha,
                                      String banco, String porta, String aux) throws SQLException {
        String url = "jdbc:" + tipoConexao + "://" + host + ":" + porta + "/" + banco;
        if (!aux.isEmpty()) {
            url += "?ApplicationName=APIlite&" + aux;
        }
        Properties props = new Properties();
        props.setProperty("user", usuario);
        props.setProperty("password", senha);

        return DriverManager.getConnection(url, props);
    }
}
