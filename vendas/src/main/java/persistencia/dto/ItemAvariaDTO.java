package persistencia.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemAvariaDTO {
    Integer id;
    String codEmpresa;
    Integer codAvaria;
    Long codProduto;
    Double preco;
    Double quantidade;
    String unidade;

    public ItemAvariaDTO(){

    }

    public ItemAvariaDTO(Integer id, String codEmpresa, Integer codAvaria, Long codProduto, Double preco, Double quantidade, String unidade){
        this.id = id;
        this.codEmpresa = codEmpresa;
        this.codAvaria = codAvaria;
        this.codProduto = codProduto;
        this.quantidade = quantidade;
        this.preco = preco;
        this.unidade = unidade;
    }

    public String getUnidade() {
        return unidade;
    }
    public void setUnidade(String unidade) {
        this.unidade = unidade;
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
    public Integer getCodAvaria() {
        return codAvaria;
    }
    public void setCodAvaria(Integer codAvaria) {
        this.codAvaria = codAvaria;
    }
    public Long getCodProduto() {
        return codProduto;
    }
    public void setCodProduto(Long codProduto) {
        this.codProduto = codProduto;
    }
    public Double getPreco() {
        return preco;
    }
    public void setPreco(Double preco) {
        this.preco = preco;
    }
    public Double getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }
}
