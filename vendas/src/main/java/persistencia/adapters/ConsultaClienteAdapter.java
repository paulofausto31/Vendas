package persistencia.adapters;

import java.text.DecimalFormat;
import java.util.List;

import persistencia.brl.ClienteBRL;
import persistencia.brl.ItenPedidoBRL;
import persistencia.dto.ClienteDTO;
import persistencia.dto.PedidoDTO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ConsultaClienteAdapter extends BaseAdapter {

	private Context ctx;
	private List<PedidoDTO> lista;
	
	public ConsultaClienteAdapter(Context ctx, List<PedidoDTO> lista){
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
		ClienteBRL cliBRL = new ClienteBRL(ctx);
		ClienteDTO cliDTO;
		ItenPedidoBRL itpBRL = new ItenPedidoBRL(ctx);
		
		LayoutInflater layout = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = layout.inflate(vendas.telas.R.layout.consulta_vendascliente, null);
		
		TextView txtCodCliente = (TextView)v.findViewById(vendas.telas.R.id.txtCodClienteConsulta);
		txtCodCliente.setText(pedDTO.getCodCliente().toString());

		TextView txtDataPedido = (TextView)v.findViewById(vendas.telas.R.id.txtDataConsulta);
		txtDataPedido.setText(pedDTO.getDataPedido());

		TextView txtNomeCliente = (TextView)v.findViewById(vendas.telas.R.id.txtNomeClienteConsulta);
		cliDTO = cliBRL.getByCodCliente(pedDTO.getCodCliente());
		txtNomeCliente.setText(cliDTO.getNome());
		
		TextView txtValorPedido = (TextView)v.findViewById(vendas.telas.R.id.txtValorConsulta);
	    Double totalPedido = itpBRL.getTotalPedido(pedDTO.getId());
		DecimalFormat formatador = new DecimalFormat("##,##00.00");  
	    String totalFormatado = formatador.format(totalPedido);  
	    totalFormatado = totalFormatado.replace(',', '.'); 		
		txtValorPedido.setText(totalFormatado);

		return v;
	}

}
