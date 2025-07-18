package merito.model;

/**
 *
 * @author Rafael
 */
public class ProdutoModel {
    
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
