package persistencia.adapters;

import java.util.List;

import persistencia.brl.ProdutoBRL;
import persistencia.dto.ItenPedidoDTO;
import persistencia.dto.ProdutoDTO;

import vendas.telas.PedidoItens;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ItensPedidoAdapter extends BaseAdapter {

	private Context ctx;
	private List<ItenPedidoDTO> lista;
	
	public ItensPedidoAdapter(Context ctx, List<ItenPedidoDTO> lista){
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
		ItenPedidoDTO dto = (ItenPedidoDTO)getItem(position);
		ProdutoBRL proBRL = new ProdutoBRL(ctx);
		ProdutoDTO proDTO = new ProdutoDTO();
		
		LayoutInflater layout = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = layout.inflate(vendas.telas.R.layout.pedido_itens, null);
		
		TextView txtProduto = (TextView)v.findViewById(vendas.telas.R.id.txtProdutoItens);
		proDTO = proBRL.getByCodProduto(dto.getCodProduto());
		txtProduto.setText(proDTO.getDescricao());

		TextView txtQuantidade = (TextView)v.findViewById(vendas.telas.R.id.txtQuantidade);
		txtQuantidade.setText(dto.getQuantidade().toString());
		
		TextView txtPrecoUnitario = (TextView)v.findViewById(vendas.telas.R.id.txtPrecoUnitario);
		txtPrecoUnitario.setText(dto.getPreco().toString());
		
		TextView txtDAList = (TextView)v.findViewById(vendas.telas.R.id.txtDAList);
		if (dto.getDAValor() > 0.00)
			txtDAList.setText(dto.getDA().concat(":").concat(dto.getDAValor().toString()).concat("%"));
		else
			txtDAList.setText(null);

		TextView txtTotal = (TextView)v.findViewById(vendas.telas.R.id.txtTotal);
		Double total = dto.getQuantidade() * dto.getPreco();
		txtTotal.setText(total.toString());
		
		return v;
	}

}
