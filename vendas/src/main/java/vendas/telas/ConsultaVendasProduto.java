package vendas.telas;

import android.app.ListActivity;

import persistencia.adapters.ConsultaProdutosAdapter;
import persistencia.brl.ItenPedidoBRL;
import venda.util.Global;

public class ConsultaVendasProduto extends ListActivity {

	@Override
	protected void onResume() {
		super.onResume();
		this.setTitle(Global.tituloAplicacao);

		ItenPedidoBRL itpBRL = new ItenPedidoBRL(getBaseContext());
		setListAdapter(new ConsultaProdutosAdapter(getBaseContext(), itpBRL.getAllAberto()));		
	}

	
}
