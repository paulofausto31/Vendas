package persistencia.dto;

public class RotaDTO {
    Integer id;
    String codEmpresa;
    Integer codRota;
    String descricao;

    public RotaDTO(){

    }

    public RotaDTO(int id, String codEmpresa, int codRota, String descricao){
        this.id = id;
        this.codEmpresa = codEmpresa;
        this.codRota = codRota;
        this.descricao = descricao;
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
    public Integer getCodRota() {
        return codRota;
    }
    public void setCodRota(Integer codRota) {
        this.codRota = codRota;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    @Override
    public String toString() {
        return this.descricao;
    }
}
