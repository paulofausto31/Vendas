package persistencia.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import persistencia.db.db;
import persistencia.dto.ItemAvariaDTO;
import persistencia.dto.ItenPedidoDTO;
import venda.util.Global;

public class ItemAvariaDAO {
    private static String tableName = "ItensAvaria";
    private SQLiteDatabase db;
    private static Context ctx;
    private static String[] columns = {"id", "codEmpresa", "codAvaria", "codProduto", "quantidade", "preco", "unidade"};

    public ItemAvariaDAO(){}

    public ItemAvariaDAO(Context ctx){
        this.ctx = ctx;
        this.db = new db(ctx).getWritableDatabase();
    }

    public void CloseConection(){
        db.close();
    }

    public long insert(ItemAvariaDTO dto){

        ContentValues ctv = new ContentValues();
        //ctv.put("id", dto.getId());
        ctv.put("codEmpresa", Global.codEmpresa);
        ctv.put("codAvaria", dto.getCodAvaria());
        ctv.put("codProduto", dto.getCodProduto());
        ctv.put("quantidade", dto.getQuantidade());
        ctv.put("preco", dto.getPreco());
        ctv.put("unidade", dto.getUnidade());

        return db.insert(tableName, null, ctv);
    }

    public boolean delete(ItemAvariaDTO dto){

        return (db.delete(tableName, "id=?", new String[]{dto.getId().toString()}) > 0);
    }

    public boolean deleteByEmpresa(){

        return (db.delete(tableName, "codEmpresa=?", new String[]{Global.codEmpresa}) > 0);
    }

    public boolean deleteByCodAvaria(Integer codAvaria){

        return (db.delete(tableName, "codAvaria=? and codEmpresa=?", new String[]{codAvaria.toString(), Global.codEmpresa}) > 0);
    }

    public boolean deleteAll(){

        return (db.delete(tableName,null , null) > 0);
    }

    public Boolean update(ItemAvariaDTO dto){

        ContentValues ctv = new ContentValues();
        ctv.put("codEmpresa", Global.codEmpresa);
        ctv.put("codAvaria", dto.getCodAvaria());
        ctv.put("codProduto", dto.getCodProduto());
        ctv.put("quantidade", dto.getQuantidade());
        ctv.put("preco", dto.getPreco());
        ctv.put("unidade", dto.getUnidade());

        return (db.update(tableName, ctv, "id=?", new String[]{dto.getId().toString()} ) > 0);
    }

    public ItemAvariaDTO getById(Integer ID){

        Cursor rs = db.query(tableName, columns, "id=?", new String[]{ID.toString()}, null, null, null);

        ItemAvariaDTO dto = null;

        if(rs.moveToFirst()){
            dto = new ItemAvariaDTO();
            dto.setId(rs.getInt(rs.getColumnIndex("id")));
            dto.setCodEmpresa(rs.getString(rs.getColumnIndex("codEmpresa")));
            dto.setCodAvaria(rs.getInt(rs.getColumnIndex("codAvaria")));
            dto.setCodProduto(rs.getLong(rs.getColumnIndex("codProduto")));
            dto.setQuantidade(rs.getDouble(rs.getColumnIndex("quantidade")));
            dto.setPreco(rs.getDouble(rs.getColumnIndex("preco")));
            dto.setUnidade(rs.getString(rs.getColumnIndex("unidade")));
        }

        return dto;
    }

    public List<ItemAvariaDTO> getByCodAvaria(Integer codAvaria){

        Cursor rs = db.query(tableName, columns, "codAvaria=? and codEmpresa=?", new String[]{codAvaria.toString(), Global.codEmpresa}, null, null, null);

        ItemAvariaDTO dto = null;
        List<ItemAvariaDTO> lista = new ArrayList<ItemAvariaDTO>();

        while(rs.moveToNext()){
            dto = new ItemAvariaDTO();
            dto.setId(rs.getInt(rs.getColumnIndex("id")));
            dto.setCodEmpresa(rs.getString(rs.getColumnIndex("codEmpresa")));
            dto.setCodAvaria(rs.getInt(rs.getColumnIndex("codAvaria")));
            dto.setCodProduto(rs.getLong(rs.getColumnIndex("codProduto")));
            dto.setQuantidade(rs.getDouble(rs.getColumnIndex("quantidade")));
            dto.setPreco(rs.getDouble(rs.getColumnIndex("preco")));
            dto.setUnidade(rs.getString(rs.getColumnIndex("unidade")));
            lista.add(dto);
        }

        return lista;
    }

    public ItemAvariaDTO getByCodProduto(Integer codAvaria, Integer codProduto){

        Cursor rs = db.query(tableName, columns, "codAvaria=? and codProduto=? and codEmpresa=?", new String[]{codAvaria.toString(), codProduto.toString(), Global.codEmpresa}, null, null, null);

        ItemAvariaDTO dto = null;

        if(rs.getCount() > 0){
            rs.moveToFirst();
            dto = new ItemAvariaDTO();
            dto.setId(rs.getInt(rs.getColumnIndex("id")));
            dto.setCodEmpresa(rs.getString(rs.getColumnIndex("codEmpresa")));
            dto.setCodAvaria(rs.getInt(rs.getColumnIndex("codAvaria")));
            dto.setCodProduto(rs.getLong(rs.getColumnIndex("codProduto")));
            dto.setQuantidade(rs.getDouble(rs.getColumnIndex("quantidade")));
            dto.setPreco(rs.getDouble(rs.getColumnIndex("preco")));
            dto.setUnidade(rs.getString(rs.getColumnIndex("unidade")));
        }

        return dto;
    }

    public Double getTotalAvaria(Integer codAvaria){

        Cursor rs = db.query(tableName, columns, "codAvaria=? and codEmpresa=?", new String[]{codAvaria.toString(), Global.codEmpresa}, null, null, null);

        Double totalPedido = 0.0;

        while(rs.moveToNext()){
            totalPedido = totalPedido + (rs.getDouble(rs.getColumnIndex("quantidade")) * rs.getDouble(rs.getColumnIndex("preco")));
        }

        return totalPedido;
    }

    public List<ItemAvariaDTO> getAll(){

        Cursor rs = db.rawQuery("SELECT * FROM ItensAvaria WHERE codEmpresa = ".concat(Global.codEmpresa), null);

        List<ItemAvariaDTO> lista = new ArrayList<ItemAvariaDTO>();

        while(rs.moveToNext()){
            ItemAvariaDTO dto = new ItemAvariaDTO(rs.getInt(rs.getColumnIndex("id")), rs.getString(rs.getColumnIndex("codEmpresa")), rs.getInt(rs.getColumnIndex("codAvaria")), rs.getLong(rs.getColumnIndex("codProduto")),
                    rs.getDouble(rs.getColumnIndex("quantidade")), rs.getDouble(rs.getColumnIndex("preco")), rs.getString(rs.getColumnIndex("unidade")));
            lista.add(dto);
        }

        return lista;
    }

}
