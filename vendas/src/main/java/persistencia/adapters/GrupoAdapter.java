package persistencia.adapters;

import java.util.List;

import persistencia.dto.GrupoDTO;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GrupoAdapter extends BaseAdapter {

	private Context ctx;
	private List<GrupoDTO> lista;
	
	public GrupoAdapter(Context ctx, List<GrupoDTO> lista){
		this.ctx = ctx;
		this.lista = lista;
	}
	
	@Override
	public int getCount() {
		return lista.size();
	}

	@Override
	public Object getItem(int position) {
		return lista.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {
		GrupoDTO dto = (GrupoDTO)getItem(position);
		
		LayoutInflater layout = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = layout.inflate(vendas.telas.R.layout.filtro_grupo, null);
		
		TextView txtDescricao = (TextView)v.findViewById(vendas.telas.R.id.txtDescricao);
		txtDescricao.setText(dto.getDescricao());

		return v;
	}

}
