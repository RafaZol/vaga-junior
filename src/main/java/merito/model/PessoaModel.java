package merito.model;

/**
 *
 * @author Rafael
 */
public class PessoaModel {
    private Long id;
    private String nome;
    private int idade;
    private String email;
    private Long documento;
    private Long telefone;
    private int tipo;
    
    
    public Long getId(){
        return id;
    }
    
    public void setId(Long id){
        this.id = id;
    }
    
    public String getNome(){
        return nome;
    }
    
    public void setNome(String nome){
        this.nome = nome;
    }
    
    public int getIdade(){
        return idade;
    }
    
    public void setIdade(int idade){
        this.idade = idade;
    }
    
    public String getEmail(){
        return email;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public Long getDoc(){
       return documento;
    }
    
    public void setDoc(Long doc){
        this.documento = doc;
    }
    
    public Long getTelefone(){
       return telefone;
    }
    
    public void setTelefone(Long telefone){
        this.telefone = telefone;
    }
    
    public int getTipo(){
        return tipo;
    }
    
    public void setTipo(int tipo){
        this.tipo = tipo;
    }
}
