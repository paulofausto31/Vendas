package persistencia.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AvariaDTO {
    Integer id;
    String codEmpresa;
    Integer codCliente;
    Integer codVendedor;
    String dataAvaria;
    String InfAdicional;
    List<ItemAvariaDTO> itemAvaria;

    public AvariaDTO(){

    }

    public AvariaDTO(Integer id, String codEmpresa, Integer codCliente, Integer codVendedor, String dataAvaria, String InfAdicional, List<ItemAvariaDTO> itemAvaria){
        this.id = id;
        this.codEmpresa = codEmpresa;
        this.codCliente = codCliente;
        this.codVendedor = codVendedor;
        this.InfAdicional = InfAdicional;
        this.dataAvaria = dataAvaria;
        this.itemAvaria = itemAvaria;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getInfAdicional() {
        return InfAdicional;
    }
    public void setInfAdicional(String InfAdicional) {
        this.InfAdicional = InfAdicional;
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
    public Integer getCodVendedor() {
        return codVendedor;
    }
    public void setCodVendedor(Integer codVendedor) {
        this.codVendedor = codVendedor;
    }
    public String getDataAvaria() {
        return dataAvaria;
    }
    public void setDataAvaria(String dataAvaria) {
        this.dataAvaria = dataAvaria;
    }
    public List<ItemAvariaDTO> getItemAvaria() {
        return itemAvaria;
    }
    public void setItemAvaria(List<ItemAvariaDTO> itemPedido) {
        this.itemAvaria = itemAvaria;
    }
}
