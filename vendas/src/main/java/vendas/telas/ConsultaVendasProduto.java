package vendas.telas;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import persistencia.adapters.ConsultaProdutosAdapter;
import persistencia.adapters.RVConsultaProdutoAdapter;
import persistencia.brl.ItenPedidoBRL;
import persistencia.brl.PedidoBRL;
import persistencia.dto.ItenPedidoDTO;
import persistencia.dto.PedidoDTO;
import venda.util.Global;

public class ConsultaVendasProduto extends Fragment {

	private RecyclerView recyclerView;
	private RVConsultaProdutoAdapter adapter;
	List<Long> lista;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_consulta_produto, container, false);
		recyclerView = view.findViewById(R.id.recycler_view_consulta_produto);
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		ItenPedidoBRL itpBRL = new ItenPedidoBRL(getContext());
		lista = itpBRL.getAllAberto();
		adapter = new RVConsultaProdutoAdapter(getContext(), lista);
		recyclerView.setAdapter(adapter);
	}
}







		/*extends ListActivity {

	@Override
	protected void onResume() {
		super.onResume();
		this.setTitle(Global.tituloAplicacao);

		ItenPedidoBRL itpBRL = new ItenPedidoBRL(getBaseContext());
		setListAdapter(new ConsultaProdutosAdapter(getBaseContext(), itpBRL.getAllAberto()));		
	}

	
}*/
