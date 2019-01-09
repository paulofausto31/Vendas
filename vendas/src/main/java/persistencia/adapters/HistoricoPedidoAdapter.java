package persistencia.adapters;

import java.text.DecimalFormat;
import java.util.List;

import persistencia.brl.ItenPedidoBRL;
import persistencia.dto.PedidoDTO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HistoricoPedidoAdapter extends BaseAdapter {

	private Context ctx;
	private List<PedidoDTO> lista;
	
	public HistoricoPedidoAdapter(Context ctx, List<PedidoDTO> lista){
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
		PedidoDTO pedDTO = (PedidoDTO)getItem(position);
		ItenPedidoBRL itpBRL = new ItenPedidoBRL(ctx);
		
		LayoutInflater layout = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = layout.inflate(vendas.telas.R.layout.pedido_historico, null);
		
		TextView txtDataPedido = (TextView)v.findViewById(vendas.telas.R.id.txtDataHistorico);
		txtDataPedido.setText(pedDTO.getDataPedido());

		TextView txtPgto = (TextView)v.findViewById(vendas.telas.R.id.txtPgto);
		txtPgto.setText(pedDTO.getFormaPgto());

		TextView txtTotalPedido = (TextView)v.findViewById(vendas.telas.R.id.txtTotalHistorico);
		Double total = itpBRL.getTotalPedido(pedDTO.getId());
		DecimalFormat formatador = new DecimalFormat("##,##00.00");  
	    String totalFormatado = formatador.format(total);  
	    totalFormatado = totalFormatado.replace(',', '.'); 		
		txtTotalPedido.setText(totalFormatado);
		
		return v;
	}

}
