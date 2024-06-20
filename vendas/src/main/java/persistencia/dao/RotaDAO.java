package persistencia.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import persistencia.db.db;
import persistencia.dto.RotaDTO;
import venda.util.Global;

public class RotaDAO {
    private static String tableName = "rota";
    private SQLiteDatabase db;
    private static Context ctx;
    private static String[] columns = {"id", "codEmpresa", "codCliente", "codRota", "seqVisita"};

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
        ctv.put("codCliente", dto.getCodCliente());
        ctv.put("seqVisita", dto.getSeqVisita());

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
        ctv.put("codCliente", dto.getCodCliente());
        ctv.put("seqVisita", dto.getSeqVisita());

        return (db.update(tableName, ctv, "id=?", new String[]{dto.getId().toString()}) > 0);
    }

    public List<String> getComboRota(){

        String sql = "SELECT codRota FROM rota WHERE codEmpresa = ".concat(Global.codEmpresa).concat(" group by codRota");
        Cursor rs = db.rawQuery(sql, null);

        List<String> lista = new ArrayList<String>();
        lista.add("Selecione ...");
        while(rs.moveToNext()){
            lista.add(rs.getString(rs.getColumnIndex("codRota")).toString());
        }

        return lista;
    }
}
