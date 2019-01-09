package persistencia.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import persistencia.db.db;
import persistencia.dto.CaminhoFTPDTO;
import venda.util.Global;

public class CaminhoFTPDAO {
	private static String tableName = "configuracaoFTP";
	private SQLiteDatabase db;
	private static Context ctx;
	private static String[] columns = {"id", "codEmpresa", "serverLocal", "userLocal", "passwordLocal",
			"serverRemoto", "userRemoto", "passwordRemoto", "caminho", "caminhoManual",
			"portaFTP", "metodoEntrada", "comDefault"};

	public CaminhoFTPDAO(){}

	public CaminhoFTPDAO(Context ctx){
		this.ctx = ctx;
		this.db = new db(ctx).getWritableDatabase();
	}

	public void CloseConection(){
		db.close();
	}

	public boolean insert(CaminhoFTPDTO dto){

		ContentValues ctv = new ContentValues();
		ctv.put("id", dto.getId());
		ctv.put("codEmpresa", dto.getCodEmpresa());
		ctv.put("serverLocal", dto.getServerLocal());
		ctv.put("userLocal", dto.getUserLocal());
		ctv.put("passwordLocal", dto.getPasswordLocal());
		ctv.put("serverRemoto", dto.getServerRemoto());
		ctv.put("userRemoto", dto.getUserRemoto());
		ctv.put("passwordRemoto", dto.getPasswordRemoto());
		ctv.put("caminho", dto.getCaminho());
		ctv.put("caminhoManual", dto.getCaminhoManual());
		ctv.put("portaFTP", dto.getPortaFTP());
		ctv.put("metodoEntrada", dto.getMetodoEntrada());
		ctv.put("comDefault", dto.getComDefault());

		return (db.insert(tableName, null, ctv) > 0);
	}

	public boolean delete(CaminhoFTPDTO dto){

		return (db.delete(tableName, "id=?", new String[]{dto.getId().toString()}) > 0);
	}

	public boolean deleteByEmpresa(){

		return (db.delete(tableName, "codEmpresa=?", new String[]{Global.codEmpresa}) > 0);
	}

	public boolean deleteAll(){

		return (db.delete(tableName,null , null) > 0);
	}

	public boolean update(CaminhoFTPDTO dto){

		ContentValues ctv = new ContentValues();
		ctv.put("codEmpresa", dto.getCodEmpresa());
		ctv.put("serverLocal", dto.getServerLocal());
		ctv.put("userLocal", dto.getUserLocal());
		ctv.put("passwordLocal", dto.getPasswordLocal());
		ctv.put("serverRemoto", dto.getServerRemoto());
		ctv.put("userRemoto", dto.getUserRemoto());
		ctv.put("passwordRemoto", dto.getPasswordRemoto());
		ctv.put("caminhoManual", dto.getCaminhoManual());
		ctv.put("caminho", dto.getCaminho());
		ctv.put("portaFTP", dto.getPortaFTP());
		ctv.put("metodoEntrada", dto.getMetodoEntrada());
		ctv.put("comDefault", dto.getComDefault());

		return (db.update(tableName, ctv, "codEmpresa=?", new String[]{dto.getCodEmpresa().toString()}) > 0);
	}

	public CaminhoFTPDTO getByEmpresa(){

		Cursor rs = db.query(tableName, columns, "codEmpresa=?", new String[]{Global.codEmpresa}, null, null, null);

		CaminhoFTPDTO dto = new CaminhoFTPDTO();

		if(rs.moveToFirst()){
			dto = new CaminhoFTPDTO();
			dto.setId(rs.getInt(rs.getColumnIndex("id")));
			dto.setCodEmpresa(rs.getString(rs.getColumnIndex("codEmpresa")));
			dto.setServerLocal(rs.getString(rs.getColumnIndex("serverLocal")));
			dto.setUserLocal(rs.getString(rs.getColumnIndex("userLocal")));
			dto.setPasswordLocal(rs.getString(rs.getColumnIndex("passwordLocal")));
			dto.setServerRemoto(rs.getString(rs.getColumnIndex("serverRemoto")));
			dto.setUserRemoto(rs.getString(rs.getColumnIndex("userRemoto")));
			dto.setPasswordRemoto(rs.getString(rs.getColumnIndex("passwordRemoto")));
			dto.setCaminho(rs.getString(rs.getColumnIndex("caminho")));
			dto.setCaminhoManual(rs.getString(rs.getColumnIndex("caminhoManual")));
			dto.setPortaFTP(rs.getString(rs.getColumnIndex("portaFTP")));
			dto.setMetodoEntrada(rs.getString(rs.getColumnIndex("metodoEntrada")));
			dto.setComDefault(rs.getString(rs.getColumnIndex("comDefault")));
		}

		return dto;
	}

	public CaminhoFTPDTO getByEmpresa(String codEmpresa){

		Cursor rs = db.query(tableName, columns, "codEmpresa=?", new String[]{codEmpresa}, null, null, null);

		CaminhoFTPDTO dto = new CaminhoFTPDTO();

		if(rs.moveToFirst()){
			dto = new CaminhoFTPDTO();
			dto.setId(rs.getInt(rs.getColumnIndex("id")));
			dto.setCodEmpresa(rs.getString(rs.getColumnIndex("codEmpresa")));
			dto.setServerLocal(rs.getString(rs.getColumnIndex("serverLocal")));
			dto.setUserLocal(rs.getString(rs.getColumnIndex("userLocal")));
			dto.setPasswordLocal(rs.getString(rs.getColumnIndex("passwordLocal")));
			dto.setServerRemoto(rs.getString(rs.getColumnIndex("serverRemoto")));
			dto.setUserRemoto(rs.getString(rs.getColumnIndex("userRemoto")));
			dto.setPasswordRemoto(rs.getString(rs.getColumnIndex("passwordRemoto")));
			dto.setCaminho(rs.getString(rs.getColumnIndex("caminho")));
			dto.setCaminhoManual(rs.getString(rs.getColumnIndex("caminhoManual")));
			dto.setPortaFTP(rs.getString(rs.getColumnIndex("portaFTP")));
			dto.setMetodoEntrada(rs.getString(rs.getColumnIndex("metodoEntrada")));
			dto.setComDefault(rs.getString(rs.getColumnIndex("comDefault")));
		}

		return dto;
	}

	public int getTotalRegistros()
	{
		Cursor rs = db.rawQuery("SELECT * FROM configuracaoFTP", null);

		return  rs.getCount();
	}

	public int getTotalRegistros(String codEmpresa)
	{
		//Cursor rs = db.query(tableName, columns, "codEmpresa=?", new String[]{codEmpresa}, null, null, null);
		if (codEmpresa.length() == 14)
			codEmpresa = "1".concat(codEmpresa);
		Cursor rs = db.rawQuery("SELECT * FROM configuracaoFTP WHERE codEmpresa = ".concat(codEmpresa), null);

		return  rs.getCount();
	}

	public CaminhoFTPDTO getDefault(){

		Cursor rs = db.rawQuery("SELECT * FROM configuracaoFTP", null);

		CaminhoFTPDTO dto = new CaminhoFTPDTO();

		if(rs.moveToFirst()){
			dto = new CaminhoFTPDTO();
			dto.setId(rs.getInt(rs.getColumnIndex("id")));
			dto.setCodEmpresa(rs.getString(rs.getColumnIndex("codEmpresa")));
			dto.setServerLocal(rs.getString(rs.getColumnIndex("serverLocal")));
			dto.setUserLocal(rs.getString(rs.getColumnIndex("userLocal")));
			dto.setPasswordLocal(rs.getString(rs.getColumnIndex("passwordLocal")));
			dto.setServerRemoto(rs.getString(rs.getColumnIndex("serverRemoto")));
			dto.setUserRemoto(rs.getString(rs.getColumnIndex("userRemoto")));
			dto.setPasswordRemoto(rs.getString(rs.getColumnIndex("passwordRemoto")));
			dto.setCaminho(rs.getString(rs.getColumnIndex("caminho")));
			dto.setCaminhoManual(rs.getString(rs.getColumnIndex("caminhoManual")));
			dto.setPortaFTP(rs.getString(rs.getColumnIndex("portaFTP")));
			dto.setMetodoEntrada(rs.getString(rs.getColumnIndex("metodoEntrada")));
			dto.setComDefault(rs.getString(rs.getColumnIndex("comDefault")));
		}

		return dto;
	}
}
