package persistencia.adapters;

import java.text.ParseException;
import java.util.List;

import persistencia.brl.ContaReceberBRL;
import persistencia.brl.FormaPgtoBRL;
import persistencia.dto.ContaReceberDTO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ContasReceberAdapter extends BaseAdapter {

	private Context ctx;
	private List<ContaReceberDTO> lista;
	
	public ContasReceberAdapter(Context ctx, List<ContaReceberDTO> lista){
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
		ContaReceberDTO dto = (ContaReceberDTO)getItem(position);
		FormaPgtoBRL brl = new FormaPgtoBRL(ctx);
		
		LayoutInflater layout = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = layout.inflate(vendas.telas.R.layout.cliente_contasreceber, null);
		
		TextView txtDocumento = (TextView)v.findViewById(vendas.telas.R.id.txtDocumento);
		txtDocumento.setText(dto.getDocumento());

		TextView txtVencimento = (TextView)v.findViewById(vendas.telas.R.id.txtVencimento);
		txtVencimento.setText(dto.getDataVencimento());
		
		TextView txtPromessa = (TextView)v.findViewById(vendas.telas.R.id.txtPromessa);
		txtPromessa.setText(dto.getDataPromessa());

		TextView txtValor = (TextView)v.findViewById(vendas.telas.R.id.txtValor);
		txtValor.setText(dto.getValor().toString());

		TextView txtFPgto = (TextView)v.findViewById(vendas.telas.R.id.txtFPgto);
		txtFPgto.setText(dto.getFormaPgto());

		return v;
	}

}
