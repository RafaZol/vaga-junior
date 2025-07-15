/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exercicioMeritoBeans;

import java.time.LocalDateTime;

/**
 *
 * @author Rafael
 */
public class MovpdvBean {
    
    private long id;
    private BombaBean bomba;
    private float quantidade;
    private PessoaBean cliente;
    private String obs;
    private float valorTotal;
    private float desconto;
    private LocalDateTime hora;
    
    public long getId(){
        return id;
    }
    
    public void setId(long id){
        this.id = id;
    }
    
    public BombaBean getBomba(){
        return bomba;
    }
    
    public void setBomba(BombaBean bomba){
        this.bomba = bomba;
    }
    
    public float getQuantidade(){
        return quantidade;
    }
    
    public void setQuantidade(float quantidade){
        this.quantidade = quantidade;
    }
    
    public PessoaBean getCliente(){
        return cliente;
    }
    
    public void setCliente(PessoaBean cliente){
        this.cliente = cliente;
    }
    
    public String getObs(){
        return obs;
    }
    
    public void setObs(String obs){
        this.obs = obs;
    }
    
    public float getValorTotal(){
        return valorTotal;
    }
    
    public void setValorTotal(float valorTotal){
        this.valorTotal = valorTotal;
    }
    
    public float getDesconto(){
        return desconto;
    }
    
    public void setDesconto(float desconto){
        this.desconto = desconto;
    }
    
    public LocalDateTime getHora(){
        return hora;
    }
    
    public void setHora(LocalDateTime hora){
        this.hora = hora;
    }
}
