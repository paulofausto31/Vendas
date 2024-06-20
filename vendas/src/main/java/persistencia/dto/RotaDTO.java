package persistencia.dto;

public class RotaDTO {
    Integer id;
    String codEmpresa;
    Integer codCliente;
    Integer codRota;
    Integer seqVisita;

    public RotaDTO(){

    }

    public RotaDTO(int id, String codEmpresa, int codCliente, int codRota, int seqVisita){
        this.id = id;
        this.codEmpresa = codEmpresa;
        this.codCliente = codCliente;
        this.codRota = codRota;
        this.seqVisita = seqVisita;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getCodEmpresa() {
        return codEmpresa;
    }
    public void setCodEmpresa(String codEmpresa) {
        this.codEmpresa = codEmpresa;
    }
    public Integer getCodCliente() {
        return codCliente;
    }
    public void setCodCliente(Integer codCliente) {
        this.codCliente = codCliente;
    }
    public Integer getCodRota() {
        return codRota;
    }
    public void setCodRota(Integer codRota) {
        this.codRota = codRota;
    }
    public Integer getSeqVisita() {
        return seqVisita;
    }
    public void setSeqVisita(Integer seqVisita) {
        this.seqVisita = seqVisita;
    }
}
