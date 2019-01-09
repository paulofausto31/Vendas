package persistencia.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import persistencia.dto.ProdutoDTO;

public class produtoAdapter extends BaseAdapter {

	private Context ctx;
	private List<ProdutoDTO> lista;
	
	public produtoAdapter(Context ctx, List<ProdutoDTO> lista){
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
		ProdutoDTO dto = (ProdutoDTO)getItem(position); 
		
		LayoutInflater layout = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = layout.inflate(vendas.telas.R.layout.produto_lista, null);
		
		TextView txtCodigo = (TextView)v.findViewById(vendas.telas.R.id.txtCodigo);
		txtCodigo.setText(dto.getCodProduto().toString());

		TextView txtDescricao = (TextView)v.findViewById(vendas.telas.R.id.txtDescricao);
		txtDescricao.setText(dto.getDescricao());

		TextView txtEstoque = (TextView)v.findViewById(vendas.telas.R.id.txtEstoque);
		txtEstoque.setText(dto.getEstoque().toString());

		return v;
	}

}
