package persistencia.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import persistencia.brl.ItenPedidoBRL;
import persistencia.dto.PedidoDTO;

public class UtilitarioPedidoAdapter extends BaseAdapter {

	private Context ctx;
	private List<PedidoDTO> lista;
	
	public UtilitarioPedidoAdapter(Context ctx, List<PedidoDTO> lista){
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
		View v = layout.inflate(vendas.telas.R.layout.utilitario_pedido, null);
		
		TextView txtCodCliente = (TextView)v.findViewById(vendas.telas.R.id.txtCodClienteUtil);
		txtCodCliente.setText(pedDTO.getCodCliente().toString());

		TextView txtDataPedido = (TextView)v.findViewById(vendas.telas.R.id.txtDataPedidoUtil);
		txtDataPedido.setText(pedDTO.getDataPedido());

		TextView txtValorPedido = (TextView)v.findViewById(vendas.telas.R.id.txtValorPedidoUtil);
	    Double totalPedido = itpBRL.getTotalPedido(pedDTO.getId());
		DecimalFormat formatador = new DecimalFormat("##,##00.00");  
	    String totalFormatado = formatador.format(totalPedido);  
	    totalFormatado = totalFormatado.replace(',', '.'); 		
		txtValorPedido.setText(totalFormatado);

		return v;
	}

}
