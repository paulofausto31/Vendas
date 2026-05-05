package persistencia.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import persistencia.db.db;
import persistencia.dto.AvariaDTO;
import persistencia.dto.PedidoDTO;
import venda.util.Global;

public class AvariaDAO {
    private static String tableName = "Avarias";
    private SQLiteDatabase db;
    private static Context ctx;
    private static String[] columns = {"id", "codEmpresa", "codCliente", "codVendedor", "dataAvaria", "InfAdicional"};

    public AvariaDAO(){}

    public AvariaDAO(Context ctx){
        this.ctx = ctx;
        this.db = new db(ctx).getWritableDatabase();
    }

    public void CloseConection(){
        db.close();
    }

    public long insert(AvariaDTO dto){

        ContentValues ctv = new ContentValues();
        //ctv.put("id", dto.getId());
        ctv.put("codEmpresa", Global.codEmpresa);
        ctv.put("codCliente", dto.getCodCliente());
        ctv.put("codVendedor", dto.getCodVendedor());
        ctv.put("InfAdicional", dto.getInfAdicional());
        ctv.put("dataAvaria", dto.getDataAvaria());

        return db.insert(tableName, null, ctv);
    }

    public boolean delete(AvariaDTO dto){

        return (db.delete(tableName, "id=?", new String[]{dto.getId().toString()}) > 0);
    }

    public boolean deleteByEmpresa(){

        return (db.delete(tableName, "codEmpresa=?", new String[]{Global.codEmpresa}) > 0);
    }

    public boolean deleteAll(){

        return (db.delete(tableName,null , null) > 0);
    }

    public boolean update(AvariaDTO dto){

        ContentValues ctv = new ContentValues();
        ctv.put("codEmpresa", Global.codEmpresa);
        ctv.put("codCliente", dto.getCodCliente());
        ctv.put("codVendedor", dto.getCodVendedor());
        ctv.put("InfAdicional", dto.getInfAdicional());
        ctv.put("dataAvaria", dto.getDataAvaria());

        return (db.update(tableName, ctv, "id=?", new String[]{dto.getId().toString()}) > 0);
    }

    public AvariaDTO getById(Integer ID){

        Cursor rs = db.query(tableName, columns, "id=?", new String[]{ID.toString()}, null, null, null);

        AvariaDTO dto = null;

        if(rs.moveToFirst()){
            dto = new AvariaDTO();
            dto.setId(rs.getInt(rs.getColumnIndex("id")));
            dto.setCodEmpresa(rs.getString(rs.getColumnIndex("codEmpresa")));
            dto.setCodCliente(rs.getInt(rs.getColumnIndex("codCliente")));
            dto.setCodVendedor(rs.getInt(rs.getColumnIndex("codVendedor")));
            dto.setInfAdicional(rs.getString(rs.getColumnIndex("InfAdicional")));
            dto.setDataAvaria(rs.getString(rs.getColumnIndex("dataAvaria")));
        }

        return dto;

    }

    public List<AvariaDTO> getByCodCliente(Integer codCliente){

        Cursor rs = db.query(tableName, columns, "codCliente=? and codEmpresa=?", new String[]{codCliente.toString(), Global.codEmpresa}, null, null, "id DESC");

        AvariaDTO dto = null;
        List<AvariaDTO> lista = new ArrayList<AvariaDTO>();

        while(rs.moveToNext()){
            dto = new AvariaDTO();
            dto.setId(rs.getInt(rs.getColumnIndex("id")));
            dto.setCodEmpresa(rs.getString(rs.getColumnIndex("codEmpresa")));
            dto.setCodCliente(rs.getInt(rs.getColumnIndex("codCliente")));
            dto.setCodVendedor(rs.getInt(rs.getColumnIndex("codVendedor")));
            dto.setInfAdicional(rs.getString(rs.getColumnIndex("InfAdicional")));
            dto.setDataAvaria(rs.getString(rs.getColumnIndex("dataAvaria")));
            lista.add(dto);
        }

        return lista;
    }

//    public List<AvariaDTO> getAll(){

//        Cursor rs = db.rawQuery("SELECT * FROM avarias WHERE codEmpresa = ".concat(Global.codEmpresa), null);

 //       List<AvariaDTO> lista = new ArrayList<AvariaDTO>();

//        while(rs.moveToNext()){
//            AvariaDTO dto = new AvariaDTO(rs.getInt(rs.getColumnIndex("id")), rs.getString(rs.getColumnIndex("codEmpresa")), rs.getInt(rs.getColumnIndex("codCliente")), rs.getInt(rs.getColumnIndex("codVendedor")),
//                    rs.getInt(rs.getColumnIndex("codAvaria")), rs.getString(rs.getColumnIndex("dataAvaria")));
//            lista.add(dto);
//        }

//        return lista;
//    }
}
