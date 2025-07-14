/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exercicioMeritoBeans;

/**
 *
 * @author Rafael
 */
public class ProdutoBean {
    
    private long id;
    private String nome;
    private double valor;
    
    public long getId(){
        return id;
    }
    
    public void setId(long id){
        this.id=id;
    }
    
    public String getNome(){
        return nome;
    }
    
    public void setNome(String nome){
        this.nome = nome;
    }
    
    public double getValor(){
        return valor;
    }
    
    public void setValor(double valor){
        this.valor = valor;
    }
    
}
