package persistencia.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import persistencia.db.db;
import persistencia.dto.ClienteDTO;
import persistencia.dto.RotaClienteDTO;
import persistencia.dto.RotaDTO;
import venda.util.Global;

public class RotaDAO {
    private static String tableName = "Rota";
    private SQLiteDatabase db;
    private static Context ctx;
    private static String[] columns = {"id", "codEmpresa", "codRota", "descricao"};

    public RotaDAO(){}

    public RotaDAO(Context ctx){
        this.ctx = ctx;
        this.db = new db(ctx).getWritableDatabase();
    }

    public void CloseConection(){
        db.close();
    }

    public boolean insert(RotaDTO dto){

        ContentValues ctv = new ContentValues();
        ctv.put("id", dto.getId());
        ctv.put("codEmpresa", dto.getCodEmpresa());
        ctv.put("codRota", dto.getCodRota());
        ctv.put("descricao", dto.getDescricao());

        return (db.insert(tableName, null, ctv) > 0);
    }

    public boolean delete(RotaDTO dto){

        return (db.delete(tableName, "id=?", new String[]{dto.getId().toString()}) > 0);
    }

    public boolean deleteByEmpresa(){

        return (db.delete(tableName, "codEmpresa=?", new String[]{Global.codEmpresa}) > 0);
    }

    public boolean deleteAll(){

        return (db.delete(tableName,null , null) > 0);
    }

    public boolean update(RotaDTO dto){

        ContentValues ctv = new ContentValues();
        ctv.put("codEmpresa", dto.getCodEmpresa());
        ctv.put("codRota", dto.getCodRota());
        ctv.put("descricao", dto.getDescricao());

        return (db.update(tableName, ctv, "id=?", new String[]{dto.getId().toString()}) > 0);
    }

    public List<RotaDTO> getComboRota(){

        String sql = "SELECT * FROM rota WHERE codEmpresa = ".concat(Global.codEmpresa).concat(" order by Descricao");
        Cursor rs = db.rawQuery(sql, null);

        RotaDTO dto = null;
        List<RotaDTO> lista = new ArrayList<RotaDTO>();
        //lista.add("Selecione ...");
        while(rs.moveToNext()){
            dto = new RotaDTO();
            dto.setId(rs.getInt(rs.getColumnIndex("id")));
            dto.setCodEmpresa(rs.getString(rs.getColumnIndex("codEmpresa")));
            dto.setCodRota(rs.getInt(rs.getColumnIndex("codRota")));
            dto.setDescricao(rs.getString(rs.getColumnIndex("Descricao")));
            lista.add(dto);
        }

        return lista;
    }
}
