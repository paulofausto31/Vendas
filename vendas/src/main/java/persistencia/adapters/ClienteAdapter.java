package persistencia.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import persistencia.brl.ClienteNaoPositivadoBRL;
import persistencia.brl.PedidoBRL;
import persistencia.dto.ClienteDTO;

public class ClienteAdapter extends BaseAdapter {

	private Context ctx;
	private List<ClienteDTO> lista;
	
	public ClienteAdapter(Context ctx, List<ClienteDTO> lista){
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
		ClienteDTO dto = (ClienteDTO)getItem(position);
		PedidoBRL pedBRL = new PedidoBRL(ctx);
		ClienteNaoPositivadoBRL cnpBRL = new ClienteNaoPositivadoBRL(ctx);
		
		LayoutInflater layout = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = layout.inflate(vendas.telas.R.layout.cliente_lista, null);
		
		TextView txtCodigo = (TextView)v.findViewById(vendas.telas.R.id.txtCodCliente);
		txtCodigo.setText(dto.getCodCliente().toString() + "  ");

		TextView txtRazaoSocial = (TextView)v.findViewById(vendas.telas.R.id.lblEmpresa);
		txtRazaoSocial.setText(dto.getRazaoSocial().substring(0, 20));

		TextView txtDescricao = (TextView)v.findViewById(vendas.telas.R.id.txtNomeCliente);
		txtDescricao.setText(dto.getNome());
		
		TextView txtPedido = (TextView)v.findViewById(vendas.telas.R.id.txtPedido);
		int retorno = 0;
		if (pedBRL.getPedidoAbertoCliente(dto.getCodCliente()) > 0)
			txtPedido.setText("Pedido");
		else if(cnpBRL.getCNPClienteAberto(dto.getCodCliente()) > 0)
			txtPedido.setText("Justificativa");
		else
			retorno = 4;
		txtPedido.setVisibility(retorno);

		return v;
	}

}
