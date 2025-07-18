package merito.utils;

/**
 *
 * @author Rafael
 */
public class Utils {
    
    public static String cripto(String senha){
        int contador, tamanho, codigo;
        String senhaCriptografada = "";
        tamanho = senha.length();
        senha = senha.toUpperCase();
        contador = 0;
        
        while(contador < tamanho){
            codigo = senha.charAt(contador) + 130;
            senhaCriptografada = senhaCriptografada + (char) codigo;
            contador++;
        }
        return senhaCriptografada;
    }
}
