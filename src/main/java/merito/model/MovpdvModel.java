package merito.model;

import java.time.LocalDateTime;

/**
 *
 * @author Rafael
 */
public class MovpdvModel {
    
    private long id;
    private long bomba;
    private float quantidade;
    private long cliente;
    private String obs;
    private float valorTotal;
    private float desconto;
    private LocalDateTime hora;
    private String combustivel;
    private float valor;
    
    public long getId(){
        return id;
    }
    
    public void setId(long id){
        this.id = id;
    }
    
    public long getBomba(){
        return bomba;
    }
    
    public void setBomba(long bomba){
        this.bomba = bomba;
    }
    
    public float getQuantidade(){
        return quantidade;
    }
    
    public void setQuantidade(float quantidade){
        this.quantidade = quantidade;
    }
    
    public long getCliente(){
        return cliente;
    }
    
    public void setCliente(long cliente){
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
    
    public String getCombustivelNome(){
        return combustivel;
    }
    
    public void setCombustivelNome(String combustivel){
        this.combustivel = combustivel;
    }
    
    public float getValor(){
        return valor;
    }
    
    public void setValor(float valor){
        this.valor = valor;
    }
}
