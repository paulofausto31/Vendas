package vendas.telas;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import persistencia.adapters.RVClienteAdapter;
import persistencia.adapters.RVUtilitarioPedidoAdapter;
import persistencia.brl.PedidoBRL;
import persistencia.dto.ClienteDTO;
import persistencia.dto.PedidoDTO;

public class UtilitarioPedidoFechado extends Fragment {

	private RecyclerView recyclerView;
	private RVUtilitarioPedidoAdapter adapter;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_fechados, container, false);
		recyclerView = view.findViewById(R.id.recycler_view_fechados);
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		PedidoBRL brl = new PedidoBRL(getContext());
		List<PedidoDTO> lista = brl.getAllPedEnviado();
		adapter = new RVUtilitarioPedidoAdapter(getContext(), lista);
		recyclerView.setAdapter(adapter);
	}
}

/*

	/*@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTitle(Global.tituloAplicacao);

		registerForContextMenu(getListView());
	}*/

	/*private void AbrePrincipal() {
		Intent principal = new Intent(this, Principal.class);
		startActivity(principal);
	}

	@Override
	protected void onResume() {
		super.onResume();
		PedidoBRL brl = new PedidoBRL(getBaseContext());
		setListAdapter(new UtilitarioPedidoAdapter(getBaseContext(), brl.getAllPedEnviado()));
	}

	@Override
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
    super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Sub Menu Utilitário");
        menu.add(0,MENU_ABREPEDIDO,0,"Abre Pedido");
    }

	 @Override
	 public boolean onMenuItemSelected(int featureID, MenuItem menu){

		 ListView l = getListView();
		 AdapterContextMenuInfo info = (AdapterContextMenuInfo) menu.getMenuInfo();
		 if (info != null)
			 pedDTO = (PedidoDTO)l.getAdapter().getItem(info.position);

		 switch (menu.getItemId()){

		 case MENU_ABREPEDIDO: // mensagem
			 PedidoBRL pedBRL = new PedidoBRL(getBaseContext());
			 pedBRL.AbrePedidoBaixado(pedDTO.getId());
 			 setListAdapter(new UtilitarioPedidoAdapter(getBaseContext(), pedBRL.getAllPedEnviado()));
			 return true;
		 }
		 return false;
	 }


}*/