package persistencia.dto;

/**
 * Created by Paulo on 25/04/2018.
 */

public class EmpresaDTO {

    Integer id;
    String codEmpresa;
    String cnpj;
    String razaoSocial;
    String fantasia;

    public EmpresaDTO(){

    }

    public EmpresaDTO(Integer id, String codEmpresa, String cnpj, String razaoSocial, String fantasia){
        this.id = id;
        this.codEmpresa = codEmpresa;
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
        this.fantasia = fantasia;
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
    public String getCnpj() {
        return cnpj;
    }
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    public String getRazaoSocial() {
        return razaoSocial;
    }
    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }
    public String getFantasia() {
        return fantasia;
    }
    public void setFantasia(String fantasia) {
        this.fantasia = fantasia;
    }

}
