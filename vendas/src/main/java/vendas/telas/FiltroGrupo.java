package vendas.telas;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import persistencia.adapters.GrupoAdapter;
import persistencia.brl.GrupoBRL;
import persistencia.dto.GrupoDTO;
import venda.util.Global;

public class FiltroGrupo extends ListActivity {
	
	@Override
	public void onCreate(Bundle e){
		super.onCreate(e);
		this.setTitle(Global.tituloAplicacao);

		GrupoBRL brl = new GrupoBRL(getBaseContext());
		
		setListAdapter(new GrupoAdapter(getBaseContext(), brl.getAll()));	
	}

	 protected void onListItemClick(ListView l, View v, int position, long id){
		GrupoDTO dto = (GrupoDTO) l.getAdapter().getItem(position);
		setResult(RESULT_OK, new Intent().putExtra("codGrupo", dto.getCodGrupo()));
		finish();
	 } 
}