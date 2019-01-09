package persistencia.adapters;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import persistencia.brl.ItenPedidoBRL;
import persistencia.brl.ProdutoBRL;
import persistencia.dto.ItenPedidoDTO;
import persistencia.dto.ProdutoDTO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ConsultaProdutosAdapter extends BaseAdapter {

	private Context ctx;
	private List<Long> lista;
	
	public ConsultaProdutosAdapter(Context ctx, List<Long> list){
		this.ctx = ctx;
		this.lista = list;
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
		Long codProduto = (Long)getItem(position);
		ProdutoBRL proBRL = new ProdutoBRL(ctx);
		ProdutoDTO proDTO;
		ItenPedidoBRL itpBRL = new ItenPedidoBRL(ctx);
		
		LayoutInflater layout = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = layout.inflate(vendas.telas.R.layout.consulta_vendasproduto, null);
		
		TextView txtCodProduto = (TextView)v.findViewById(vendas.telas.R.id.txtCodProdutoConsulta);
		proDTO = proBRL.getByCodProduto(codProduto);
		txtCodProduto.setText(proDTO.getCodProduto().toString());

		TextView txtDescricaoProduto = (TextView)v.findViewById(vendas.telas.R.id.txtDescricaoProdutoConsulta);
		txtDescricaoProduto.setText(proDTO.getDescricao());

		TextView txtQuantidadeConsulta = (TextView)v.findViewById(vendas.telas.R.id.txtQtdVendaConsulta);
		
	    Double total = itpBRL.getSumQtdAberto(proDTO.getCodProduto());
		DecimalFormat formatador = new DecimalFormat("##,###0.00");  
	    String totalFormatado = formatador.format(total);  
	    totalFormatado = totalFormatado.replace('.', ','); 		
		txtQuantidadeConsulta.setText(totalFormatado);

		return v;
	}

}
