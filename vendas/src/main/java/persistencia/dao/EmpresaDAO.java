package persistencia.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import persistencia.db.db;
import persistencia.dto.EmpresaDTO;

/**
 * Created by Paulo on 25/04/2018.
 */

public class EmpresaDAO {
    private static String tableName = "empresa";
    private SQLiteDatabase db;
    private static Context ctx;
    private static String[] columns = {"id", "codEmpresa", "cnpj", "razaoSocial", "fantasia", "endereco",
            "bairro", "cidade", "uf", "cep", "telefone", "email"};

    public EmpresaDAO(){}

    public EmpresaDAO(Context ctx){
        this.ctx = ctx;
        this.db = new db(ctx).getWritableDatabase();
    }

    public void CloseConection(){
        db.close();
    }

    public boolean insert(EmpresaDTO dto){

        ContentValues ctv = new ContentValues();
        ctv.put("id", dto.getId());
        ctv.put("codEmpresa", dto.getCodEmpresa());
        ctv.put("cnpj", dto.getCnpj());
        ctv.put("razaoSocial", dto.getRazaoSocial());
        ctv.put("fantasia", dto.getFantasia());
        ctv.put("endereco", dto.getEndereco());
        ctv.put("bairro", dto.getBairro());
        ctv.put("cidade", dto.getCidade());
        ctv.put("uf", dto.getUf());
        ctv.put("cep", dto.getCep());
        ctv.put("telefone", dto.getTelefone());
        ctv.put("email", dto.getEmail());

        return (db.insert(tableName, null, ctv) > 0);
    }

    public boolean delete(EmpresaDTO dto){

        return (db.delete(tableName, "id=?", new String[]{dto.getId().toString()}) > 0);
    }

    public boolean deleteAll(){

        return (db.delete(tableName,null , null) > 0);
    }

    public boolean deleteByEmpresa(String codEmpresa){

        return (db.delete(tableName,"codEmpresa=?" , new String[]{codEmpresa.toString()}) > 0);
    }

    public boolean update(EmpresaDTO dto){

        ContentValues ctv = new ContentValues();
        ctv.put("codEmpresa", dto.getCodEmpresa());
        ctv.put("cnpj", dto.getCnpj());
        ctv.put("razaoSocial", dto.getRazaoSocial());
        ctv.put("fantasia", dto.getFantasia());
        ctv.put("endereco", dto.getEndereco());
        ctv.put("bairro", dto.getBairro());
        ctv.put("cidade", dto.getCidade());
        ctv.put("uf", dto.getUf());
        ctv.put("cep", dto.getCep());
        ctv.put("telefone", dto.getTelefone());
        ctv.put("email", dto.getEmail());

        return (db.update(tableName, ctv, "id=?", new String[]{dto.getId().toString()}) > 0);
    }

    public EmpresaDTO getById(Integer ID){

        Cursor rs = db.query(tableName, columns, "id=?", new String[]{ID.toString()}, null, null, null);

        EmpresaDTO dto = null;

        if(rs.moveToFirst()){
            dto = new EmpresaDTO();
            dto.setId(rs.getInt(rs.getColumnIndex("id")));
            dto.setCodEmpresa(rs.getString(rs.getColumnIndex("codEmpresa")));
            dto.setCnpj(rs.getString(rs.getColumnIndex("cnpj")));
            dto.setRazaoSocial(rs.getString(rs.getColumnIndex("RazaoSocial")));
            dto.setFantasia(rs.getString(rs.getColumnIndex("Fantasia")));
            dto.setEndereco(rs.getString(rs.getColumnIndex("Endereco")));
            dto.setBairro(rs.getString(rs.getColumnIndex("Bairro")));
            dto.setCidade(rs.getString(rs.getColumnIndex("Cidade")));
            dto.setUf(rs.getString(rs.getColumnIndex("UF")));
            dto.setCep(rs.getString(rs.getColumnIndex("CEP")));
            dto.setTelefone(rs.getString(rs.getColumnIndex("Telefone")));
            dto.setEmail(rs.getString(rs.getColumnIndex("Email")));
        }

        return dto;
    }

    public EmpresaDTO getByCodEmpresa(String codEmpresa){

        Cursor rs = db.query(tableName, columns, "codEmpresa=?", new String[]{codEmpresa.toString()}, null, null, null);

        EmpresaDTO dto = null;

        if(rs.moveToFirst()){
            dto = new EmpresaDTO();
            dto.setId(rs.getInt(rs.getColumnIndex("id")));
            dto.setCodEmpresa(rs.getString(rs.getColumnIndex("codEmpresa")));
            dto.setCnpj(rs.getString(rs.getColumnIndex("cnpj")));
            dto.setRazaoSocial(rs.getString(rs.getColumnIndex("RazaoSocial")));
            dto.setFantasia(rs.getString(rs.getColumnIndex("Fantasia")));
            dto.setEndereco(rs.getString(rs.getColumnIndex("Endereco")));
            dto.setBairro(rs.getString(rs.getColumnIndex("Bairro")));
            dto.setCidade(rs.getString(rs.getColumnIndex("Cidade")));
            dto.setUf(rs.getString(rs.getColumnIndex("UF")));
            dto.setCep(rs.getString(rs.getColumnIndex("CEP")));
            dto.setTelefone(rs.getString(rs.getColumnIndex("Telefone")));
            dto.setEmail(rs.getString(rs.getColumnIndex("Email")));
        }

        return dto;
    }

    public List<EmpresaDTO> getAll(){

        Cursor rs = db.rawQuery("SELECT * FROM empresa", null);

        EmpresaDTO empDTO = new EmpresaDTO();
        List<EmpresaDTO> lista = new ArrayList<EmpresaDTO>();

        while(rs.moveToNext()){
            empDTO.setId(rs.getInt(rs.getColumnIndex("id")));
            empDTO.setCodEmpresa(rs.getString(rs.getColumnIndex("codEmpresa")));
            empDTO.setCnpj(rs.getString(rs.getColumnIndex("cnpj")));
            empDTO.setRazaoSocial(rs.getString(rs.getColumnIndex("RazaoSocial")));
            empDTO.setFantasia(rs.getString(rs.getColumnIndex("Fantasia")));
            empDTO.setEndereco(rs.getString(rs.getColumnIndex("Endereco")));
            empDTO.setBairro(rs.getString(rs.getColumnIndex("Bairro")));
            empDTO.setCidade(rs.getString(rs.getColumnIndex("Cidade")));
            empDTO.setUf(rs.getString(rs.getColumnIndex("UF")));
            empDTO.setCep(rs.getString(rs.getColumnIndex("CEP")));
            empDTO.setTelefone(rs.getString(rs.getColumnIndex("Telefone")));
            empDTO.setEmail(rs.getString(rs.getColumnIndex("Email")));
            //= new EmpresaDTO(rs.getInt(rs.getColumnIndex("id")), rs.getString(rs.getColumnIndex("codEmpresa")), rs.getString(rs.getColumnIndex("cnpj")), rs.getString(rs.getColumnIndex("razaoSocial")), rs.getString(rs.getColumnIndex("fantasia")),
            //        rs.getString(rs.getColumnIndex("endereco")), rs.getString(rs.getColumnIndex("bairro")), rs.getString(rs.getColumnIndex("cidade")), rs.getString(rs.getColumnIndex("uf")), rs.getString(rs.getColumnIndex("cep")), rs.getString(rs.getColumnIndex("telefone")),
            //        rs.getString(rs.getColumnIndex("email")));
            lista.add(empDTO);
        }

        return lista;
    }

    public int getTotalEmpresas(){

        Cursor rs = db.rawQuery("SELECT * FROM empresa", null);

        return rs.getCount();
    }

    public List<String> getCombo(String campo, String item0){

        Cursor rs = db.rawQuery("SELECT * FROM empresa", null);

        List<String> lista = new ArrayList<String>();
        lista.add(item0);
        while(rs.moveToNext()){
            lista.add(rs.getString(rs.getColumnIndex(campo)).toString());
        }

        return lista;
    }

    public List<String> getCombo(String campo1, String campo2, String item0){

        Cursor rs = db.rawQuery("SELECT * FROM empresa", null);

        List<String> lista = new ArrayList<String>();
        lista.add(item0);
        while(rs.moveToNext()){
            lista.add(rs.getString(rs.getColumnIndex(campo1)).toString().concat(" - ").concat(rs.getString(rs.getColumnIndex(campo2)).toString()));
        }

        return lista;
    }
}


