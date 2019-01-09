package persistencia.adapters;

import java.util.List;

import persistencia.brl.ClienteNaoPositivadoBRL;
import persistencia.brl.PedidoBRL;
import persistencia.dto.ClienteDTO;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CidadeAdapter extends BaseAdapter {

	private Context ctx;
	private List<ClienteDTO> lista;

	public CidadeAdapter(Context ctx, List<ClienteDTO> lista){
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
	public View getView(int position, View convertView, ViewGroup parent) {
		ClienteDTO dto = (ClienteDTO)getItem(position);
		
		LayoutInflater layout = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = layout.inflate(vendas.telas.R.layout.filtro_cidade, null);
		
		TextView txtCidade = (TextView)v.findViewById(vendas.telas.R.id.txtCidade);
		txtCidade.setText(dto.getCidade().toString() + "  ");

		return v;
	}

	
}
