package vendas.telas;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import persistencia.adapters.FornecedorAdapter;
import persistencia.dao.FornecedorDAO;
import persistencia.dto.FornecedorDTO;
import venda.util.Global;

public class FiltroFornecedor extends ListActivity {
	
	@Override
	public void onCreate(Bundle e){
		super.onCreate(e);
		this.setTitle(Global.tituloAplicacao);

		FornecedorDAO dao = new FornecedorDAO(getBaseContext());
		
		setListAdapter(new FornecedorAdapter(getBaseContext(), dao.getAll()));	
	}

	 protected void onListItemClick(ListView l, View v, int position, long id){
		FornecedorDTO dto = (FornecedorDTO) l.getAdapter().getItem(position);
		setResult(RESULT_OK, new Intent().putExtra("codFornecedor", dto.getCodFornecedor()));
		finish();
	 } 
}