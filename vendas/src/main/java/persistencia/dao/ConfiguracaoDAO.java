package persistencia.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import persistencia.db.db;
import persistencia.dto.ConfiguracaoDTO;
import venda.util.Global;

public class ConfiguracaoDAO {
	private static String tableName = "configuracao";
	private SQLiteDatabase db;
	private static Context ctx;
	private static String[] columns = {"id", "descontoAcrescimo", "codEmpresa", "criticaEstoque",
		"prazoMaximo", "parcelaMaxima", "descontoMaximo", "dataCarga", "horaCarga", "mensagem"};
	
	public ConfiguracaoDAO(){}
	
	public ConfiguracaoDAO(Context ctx){
		this.ctx = ctx;
		this.db = new db(ctx).getWritableDatabase();
	}
	
	public void CloseConection(){
		db.close();
	}
	
	public boolean insert(ConfiguracaoDTO dto){
		
		ContentValues ctv = new ContentValues();
		ctv.put("id", dto.getId());
		ctv.put("descontoAcrescimo", dto.getDescontoAcrescimo());
		ctv.put("codEmpresa", dto.getCodEmpresa());
		ctv.put("criticaEstoque", dto.getCriticaEstoque());
		ctv.put("prazoMaximo", dto.getPrazoMaximo());
		ctv.put("parcelaMaxima", dto.getParcelaMaxima());
		ctv.put("descontoMaximo", dto.getDescontoMaximo());
		ctv.put("dataCarga", dto.getDataCarga());
		ctv.put("horaCarga", dto.getHoraCarga());
		ctv.put("mensagem", dto.getMensagem());
		
		return (db.insert(tableName, null, ctv) > 0);
	}
	
	public boolean delete(ConfiguracaoDTO dto){
		
		return (db.delete(tableName, "id=?", new String[]{dto.getId().toString()}) > 0);
	}

	public boolean deleteByEmpresa(){

		return (db.delete(tableName, "codEmpresa=?", new String[]{Global.codEmpresa}) > 0);
	}

	public boolean deleteAll(){

		return (db.delete(tableName,null , null) > 0);
	}

	public boolean update(ConfiguracaoDTO dto){

		ContentValues ctv = new ContentValues();
		ctv.put("descontoAcrescimo", dto.getDescontoAcrescimo());
		ctv.put("codEmpresa", dto.getCodEmpresa());
		ctv.put("criticaEstoque", dto.getCriticaEstoque());
		ctv.put("prazoMaximo", dto.getPrazoMaximo());
		ctv.put("parcelaMaxima", dto.getParcelaMaxima());
		ctv.put("descontoMaximo", dto.getDescontoMaximo());
		ctv.put("dataCarga", dto.getDataCarga());
		ctv.put("horaCarga", dto.getHoraCarga());
		ctv.put("mensagem", dto.getMensagem());
		
		return (db.update(tableName, ctv, "id=?", new String[]{dto.getId().toString()}) > 0);
	}
	
	public ConfiguracaoDTO getById(Integer ID){

		Cursor rs = db.query(tableName, columns, "id=?", new String[]{ID.toString()}, null, null, null);
		
		ConfiguracaoDTO dto = null;
		
		if(rs.moveToFirst()){
			dto = new ConfiguracaoDTO();
			dto.setId(rs.getInt(rs.getColumnIndex("id")));
			dto.setDescontoAcrescimo(rs.getString(rs.getColumnIndex("descontoAcrescimo")));
			dto.setCodEmpresa(rs.getString(rs.getColumnIndex("codEmpresa")));
			dto.setCriticaEstoque(rs.getString(rs.getColumnIndex("criticaEstoque")));
			dto.setPrazoMaximo(rs.getInt(rs.getColumnIndex("prazoMaximo")));
			dto.setParcelaMaxima(rs.getInt(rs.getColumnIndex("parcelaMaxima")));
			dto.setDescontoMaximo(rs.getDouble(rs.getColumnIndex("descontoMaximo")));
			dto.setDataCarga(rs.getString(rs.getColumnIndex("dataCarga")));
			dto.setHoraCarga(rs.getString(rs.getColumnIndex("horaCarga")));
			dto.setMensagem(rs.getString(rs.getColumnIndex("mensagem")));
		}
		
		return dto;
	}
	
	public ConfiguracaoDTO getAll(){

		Cursor rs = db.rawQuery("SELECT * FROM configuracao WHERE codEmpresa = ".concat(Global.codEmpresa), null);
		
		ConfiguracaoDTO cfgDTO = null;
		
		if(rs.moveToFirst()){
			cfgDTO = new ConfiguracaoDTO(rs.getInt(rs.getColumnIndex("id")), rs.getString(rs.getColumnIndex("descontoAcrescimo")), 
					rs.getString(rs.getColumnIndex("codEmpresa")), rs.getString(rs.getColumnIndex("criticaEstoque")), rs.getInt(rs.getColumnIndex("prazoMaximo")), 
					rs.getInt(rs.getColumnIndex("parcelaMaxima")), rs.getDouble(rs.getColumnIndex("descontoMaximo")), rs.getString(rs.getColumnIndex("dataCarga")), 
					rs.getString(rs.getColumnIndex("horaCarga")), rs.getString(rs.getColumnIndex("mensagem")));
		}
		
		return cfgDTO;
	}

	public String getMensagem(){

		Cursor rs = db.rawQuery("SELECT * FROM configuracao WHERE codEmpresa = ".concat(Global.codEmpresa), null);
		
		String msg = null;
		
		if(rs.moveToFirst()){
			msg = rs.getString(rs.getColumnIndex("mensagem")).trim();
		}
		
		return msg;
	}
}
