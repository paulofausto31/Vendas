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
    String endereco;
    String bairro;
    String cidade;
    String uf;
    String cep;
    String telefone;
    String email;


    public EmpresaDTO(){

    }

    public EmpresaDTO(Integer id, String codEmpresa, String cnpj, String razaoSocial, String fantasia,
                      String endereco, String bairro, String cidade, String uf, String cep, String telefone, String email){
        this.id = id;
        this.codEmpresa = codEmpresa;
        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
        this.fantasia = fantasia;
        this.endereco = endereco;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
        this.cep = cep;
        this.telefone = telefone;
        this.email = email;
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
    public String getEndereco() {
        return endereco;
    }
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    public String getBairro() {
        return bairro;
    }
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
    public String getCidade() {
        return cidade;
    }
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
    public String getUf() {
        return uf;
    }
    public void setUf(String uf) {
        this.uf = uf;
    }
    public String getCep() {
        return cep;
    }
    public void setCep(String cep) {
        this.cep = cep;
    }
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

}
