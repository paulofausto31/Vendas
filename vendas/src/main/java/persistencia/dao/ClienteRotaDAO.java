package persistencia.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import persistencia.db.db;
import persistencia.dto.ClienteRotaDTO;
import venda.util.Global;

public class ClienteRotaDAO {
    private static String tableName = "clienteRota";
    private SQLiteDatabase db;
    private static Context ctx;
    private static String[] columns = {"id", "codEmpresa", "codCliente", "codRota", "seqVisita"};

    public ClienteRotaDAO(){}

    public ClienteRotaDAO(Context ctx){
        this.ctx = ctx;
        this.db = new db(ctx).getWritableDatabase();
    }

    public void CloseConection(){
        db.close();
    }

    public boolean insert(ClienteRotaDTO dto){

        ContentValues ctv = new ContentValues();
        ctv.put("id", dto.getId());
        ctv.put("codEmpresa", dto.getCodEmpresa());
        ctv.put("codRota", dto.getCodRota());
        ctv.put("codCliente", dto.getCodCliente());
        ctv.put("seqVisita", dto.getSeqVisita());

        return (db.insert(tableName, null, ctv) > 0);
    }

    public boolean delete(ClienteRotaDTO dto){

        return (db.delete(tableName, "id=?", new String[]{dto.getId().toString()}) > 0);
    }

    public boolean deleteByEmpresa(){

        return (db.delete(tableName, "codEmpresa=?", new String[]{Global.codEmpresa}) > 0);
    }

    public boolean deleteAll(){

        return (db.delete(tableName,null , null) > 0);
    }

    public boolean update(ClienteRotaDTO dto){

        ContentValues ctv = new ContentValues();
        ctv.put("codEmpresa", dto.getCodEmpresa());
        ctv.put("codRota", dto.getCodRota());
        ctv.put("codCliente", dto.getCodCliente());
        ctv.put("seqVisita", dto.getSeqVisita());

        return (db.update(tableName, ctv, "id=?", new String[]{dto.getId().toString()}) > 0);
    }

}
