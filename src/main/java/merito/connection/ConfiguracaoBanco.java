package merito.connection;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfiguracaoBanco {

   public static Properties carregarConfiguracoes() throws IOException {
    Properties props = new Properties();

    try (InputStream is = Conexao.class.getClassLoader().getResourceAsStream("db_config.txt")) {
        if (is == null) {
            throw new FileNotFoundException("Arquivo db_config.txt não encontrado no classpath.");
        }
        props.load(is);
    }

    return props;
}

}
