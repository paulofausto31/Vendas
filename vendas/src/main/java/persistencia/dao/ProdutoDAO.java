package persistencia.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import persistencia.db.db;
import persistencia.dto.ProdutoDTO;
import venda.util.Global;

public class ProdutoDAO {

	private static String tableName = "produto";
	private SQLiteDatabase db;
	private static Context ctx;
	private static String[] columns = {"id", "codEmpresa", "codProduto", "descricao", "unidade", "estoque",
		"grupo", "fornecedor", "unidade2"};
	
	public ProdutoDAO(){}
	
	public ProdutoDAO(Context ctx){
		this.ctx = ctx;
		this.db = new db(ctx).getWritableDatabase();
	}

	public void CloseConection(){
		db.close();
	}
	
	public boolean insert(ProdutoDTO dto){
		
		ContentValues ctv = new ContentValues();
		ctv.put("id", dto.getId());
		ctv.put("codEmpresa", dto.getCodEmpresa());
		ctv.put("codProduto", dto.getCodProduto());
		ctv.put("descricao", dto.getDescricao());
		ctv.put("unidade", dto.getUnidade());
		ctv.put("estoque", dto.getEstoque());
		ctv.put("grupo", dto.getGrupo());
		ctv.put("fornecedor", dto.getFornecedor());
		ctv.put("unidade2", dto.getUnidade2());
		
		return (db.insert(tableName, null, ctv) > 0);
	}
	
	public boolean delete(ProdutoDTO dto){
		
		return (db.delete(tableName, "id=?", new String[]{dto.getId().toString()}) > 0);
	}

	public boolean deleteByEmpresa(){

		return (db.delete(tableName, "codEmpresa=?", new String[]{Global.codEmpresa}) > 0);
	}

	public boolean deleteAll(){

		return (db.delete(tableName,null , null) > 0);
	}

	public boolean update(ProdutoDTO dto){

		ContentValues ctv = new ContentValues();
		ctv.put("codEmpresa", dto.getCodEmpresa());
		ctv.put("codProduto", dto.getCodProduto());
		ctv.put("descricao", dto.getDescricao());
		ctv.put("unidade", dto.getUnidade());
		ctv.put("estoque", dto.getEstoque());
		ctv.put("grupo", dto.getGrupo());
		ctv.put("fornecedor", dto.getFornecedor());
		ctv.put("unidade2", dto.getUnidade2());
		
		return (db.update(tableName, ctv, "id=?", new String[]{dto.getId().toString()}) > 0);
	}
	
	public ProdutoDTO getById(Integer ID){

		Cursor rs = db.query(tableName, columns, "id=?", new String[]{ID.toString()}, null, null, null);
		
		ProdutoDTO dto = null;
		
		if(rs.moveToFirst()){
			dto = new ProdutoDTO();
			dto.setId(rs.getInt(rs.getColumnIndex("id")));
			dto.setCodEmpresa(rs.getString(rs.getColumnIndex("codEmpresa")));
			dto.setCodProduto(rs.getLong(rs.getColumnIndex("codProduto")));
			dto.setDescricao(rs.getString(rs.getColumnIndex("descricao")));
			dto.setUnidade(rs.getString(rs.getColumnIndex("unidade")));
			dto.setEstoque(rs.getDouble(rs.getColumnIndex("estoque")));
			dto.setGrupo(rs.getString(rs.getColumnIndex("grupo")));
			dto.setFornecedor(rs.getInt(rs.getColumnIndex("fornecedor")));
			dto.setUnidade2(rs.getInt(rs.getColumnIndex("unidade2")));
		}
		
		return dto;
	}
	
	public ProdutoDTO getByCodProduto(Long codProduto){

		Cursor rs = db.query(tableName, columns, "codProduto=? and codEmpresa=?", new String[]{codProduto.toString(), Global.codEmpresa}, null, null, null);
		
		ProdutoDTO dto = null;
		
		if(rs.moveToFirst()){
			dto = new ProdutoDTO();
			dto.setId(rs.getInt(rs.getColumnIndex("id")));
			dto.setCodEmpresa(rs.getString(rs.getColumnIndex("codEmpresa")));
			dto.setCodProduto(rs.getLong(rs.getColumnIndex("codProduto")));
			dto.setDescricao(rs.getString(rs.getColumnIndex("descricao")));
			dto.setUnidade(rs.getString(rs.getColumnIndex("unidade")));
			dto.setEstoque(rs.getDouble(rs.getColumnIndex("estoque")));
			dto.setGrupo(rs.getString(rs.getColumnIndex("grupo")));
			dto.setFornecedor(rs.getInt(rs.getColumnIndex("fornecedor")));
			dto.setUnidade2(rs.getInt(rs.getColumnIndex("unidade2")));
		}
		
		return dto;
	}
	
	public List<ProdutoDTO> getAll(){

		Cursor rs = db.rawQuery("SELECT * FROM produto WHERE codEmpresa = ".concat(Global.codEmpresa), null);
		
		List<ProdutoDTO> lista = new ArrayList<ProdutoDTO>();
		
		while(rs.moveToNext()){
			ProdutoDTO dto = new ProdutoDTO(rs.getInt(rs.getColumnIndex("id")), rs.getString(rs.getColumnIndex("codEmpresa")), rs.getLong(rs.getColumnIndex("codProduto")), rs.getString(rs.getColumnIndex("descricao")),
					rs.getString(rs.getColumnIndex("unidade")), rs.getDouble(rs.getColumnIndex("estoque")), rs.getString(rs.getColumnIndex("grupo")),
					rs.getInt(rs.getColumnIndex("fornecedor")), rs.getInt(rs.getColumnIndex("unidade2")));
			lista.add(dto);
			}
		return lista;
	}
		
		public List<ProdutoDTO> getAllOrdenado(){

			Cursor rs = db.rawQuery("SELECT * FROM produto WHERE codEmpresa = ".concat(Global.codEmpresa).concat(" order by Descricao"), null);
			
			List<ProdutoDTO> lista = new ArrayList<ProdutoDTO>();
			
			int total = 0;
			
			while(rs.moveToNext()){
				ProdutoDTO dto = new ProdutoDTO(rs.getInt(rs.getColumnIndex("id")), rs.getString(rs.getColumnIndex("codEmpresa")), rs.getLong(rs.getColumnIndex("codProduto")), rs.getString(rs.getColumnIndex("descricao")),
						rs.getString(rs.getColumnIndex("unidade")), rs.getDouble(rs.getColumnIndex("estoque")), rs.getString(rs.getColumnIndex("grupo")),
						rs.getInt(rs.getColumnIndex("fornecedor")), rs.getInt(rs.getColumnIndex("unidade2")));
				lista.add(dto);
				total++;
				if (total >= 350)
					rs.moveToLast();
		}
		
		return lista;
	}

		public List<ProdutoDTO> getByFornecedor(Integer codFornecedor){

			Cursor rs = db.query(tableName, columns, "fornecedor=? and codEmpresa=?", new String[]{codFornecedor.toString(), Global.codEmpresa}, null, null, null);
			
			List<ProdutoDTO> lista = new ArrayList<ProdutoDTO>();

			while(rs.moveToNext()){
				ProdutoDTO dto = new ProdutoDTO(rs.getInt(rs.getColumnIndex("id")), rs.getString(rs.getColumnIndex("codEmpresa")), rs.getLong(rs.getColumnIndex("codProduto")), rs.getString(rs.getColumnIndex("descricao")),
						rs.getString(rs.getColumnIndex("unidade")), rs.getDouble(rs.getColumnIndex("estoque")), rs.getString(rs.getColumnIndex("grupo")),
						rs.getInt(rs.getColumnIndex("fornecedor")), rs.getInt(rs.getColumnIndex("unidade2")));
				lista.add(dto);
			}
			
			return lista;
		}

		public List<ProdutoDTO> getByDescricao(String descricao){

			Cursor rs = db.query(tableName, columns, "descricao like ? and codEmpresa=?", new String[]{"%" + descricao + "%", Global.codEmpresa}, null, null, null);
			
			List<ProdutoDTO> lista = new ArrayList<ProdutoDTO>();

			while(rs.moveToNext()){
				ProdutoDTO dto = new ProdutoDTO(rs.getInt(rs.getColumnIndex("id")), rs.getString(rs.getColumnIndex("codEmpresa")), rs.getLong(rs.getColumnIndex("codProduto")), rs.getString(rs.getColumnIndex("descricao")),
						rs.getString(rs.getColumnIndex("unidade")), rs.getDouble(rs.getColumnIndex("estoque")), rs.getString(rs.getColumnIndex("grupo")),
						rs.getInt(rs.getColumnIndex("fornecedor")), rs.getInt(rs.getColumnIndex("unidade2")));
				lista.add(dto);
			}
			
			return lista;
		}

		public Double getSaldoEstoque(String codProduto){

			Cursor rs = db.rawQuery("SELECT sum(p.estoque) as saldo FROM Produto p where p.codProduto = ".concat(codProduto).concat(" and codEmpresa =").concat(Global.codEmpresa), null);
			
			Double saldo = 0.0;

			if(rs.moveToFirst()){
				saldo = rs.getDouble(rs.getColumnIndex("saldo"));
			}
			
			return saldo;
		}

		public List<ProdutoDTO> getByGrupo(String codGrupo){

			Cursor rs = db.query(tableName, columns, "grupo=? and codEmpresa=?", new String[]{codGrupo.toString(), Global.codEmpresa}, null, null, null);
			
			List<ProdutoDTO> lista = new ArrayList<ProdutoDTO>();

			while(rs.moveToNext()){
				ProdutoDTO dto = new ProdutoDTO(rs.getInt(rs.getColumnIndex("id")), rs.getString(rs.getColumnIndex("codEmpresa")), rs.getLong(rs.getColumnIndex("codProduto")), rs.getString(rs.getColumnIndex("descricao")),
						rs.getString(rs.getColumnIndex("unidade")), rs.getDouble(rs.getColumnIndex("estoque")), rs.getString(rs.getColumnIndex("grupo")),
						rs.getInt(rs.getColumnIndex("fornecedor")), rs.getInt(rs.getColumnIndex("unidade2")));
				lista.add(dto);
			}
			
			return lista;
		}

}

