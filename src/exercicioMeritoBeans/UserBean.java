package exercicioMeritoBeans;

/**
 *
 * @author Rafael
 */
public class UserBean {
    
    private long userId;
    private long pessoaId;
    private String nome;
    private String pessoa;
    private String pass;
    
    public long getUserId(){
        return userId;
    }
    
    public void setUserId(long userId){
        this.userId = userId;
    }
    
    public long getPessoaId(){
        return pessoaId;
    }
    
    public void setPessoaId(long pessoaId){
        this.pessoaId = pessoaId;
    }
    
    public String getNome(){
        return nome;
    }
    
    public void setNome(String nome){
        this.nome = nome;
    }
    
    public String getPessoa(){
        return pessoa;
    }
    
    public void setPessoa(String pessoa){
        this.pessoa = pessoa;
    }
    
    public String getPass(){
        return pass;
    }
    
    public void setPass(String pass){
        this.pass = pass;
    }
}
