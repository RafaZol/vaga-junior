package exercicioMeritoBeans;

/**
 *
 * @author Rafael
 */
public class BombaBean {
    
    private long id;
    private long combustivel;
    private String combustivelAux;
    
    public long getId(){
        return id;
    }
    
    public void setId(long id){
        this.id = id;
    }
    
    public long getCombustivel(){
        return combustivel;
    }
    
    public void setCombustivel(long combustivel){
        this.combustivel = combustivel;
    }
    
    public String getCombustivelNome() {
    return combustivelAux;
}

    public void setCombustivelAux(String combustivelAux) {
        this.combustivelAux = combustivelAux;
    }
}
