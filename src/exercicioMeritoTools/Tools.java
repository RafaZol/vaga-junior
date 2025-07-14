/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exercicioMeritoTools;

/**
 *
 * @author Rafael
 */
public class Tools {
    
    public static String Cripto(String senha){
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
