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
import android.widget.PopupMenu;

import persistencia.adapters.RVClienteAdapter;
import persistencia.adapters.RVUtilitarioPedidoAdapter;
import persistencia.brl.PedidoBRL;
import persistencia.dto.ClienteDTO;
import persistencia.dto.PedidoDTO;

public class UtilitarioPedidoFechado extends Fragment implements RVUtilitarioPedidoAdapter.OnItemLongClickListener {

	private RecyclerView recyclerView;
	private RVUtilitarioPedidoAdapter adapter;
	List<PedidoDTO> lista;
	PedidoBRL brl;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_fechados, container, false);
		recyclerView = view.findViewById(R.id.recycler_view_fechados);
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

		brl = new PedidoBRL(getContext());

		return view;
	}

	@Override
	public void onItemLongClick(View view, int position) {
		PedidoDTO pedDTO = lista.get(position);
		PopupMenu popup = new PopupMenu(getContext(), view);
		popup.getMenuInflater().inflate(R.menu.popup_utilitario, popup.getMenu());
		popup.setOnMenuItemClickListener(item -> {
			if (item.getItemId() == R.id.action_abre_pedido) {
				atualizarLista(pedDTO);
				return true;
			}
			return false;
		});
		popup.show();
	}

	private void atualizarLista(PedidoDTO dto) {
		// Adicione lógica para atualizar a lista
		brl.AbrePedidoBaixado(dto.getId());
		lista = brl.getAllPedEnviado();
		adapter = new RVUtilitarioPedidoAdapter(getContext(), lista, this);
		recyclerView.setAdapter(adapter);
		//Toast.makeText(getContext(), "Lista Atualizada", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onResume() {
		super.onResume();
		lista = brl.getAllPedEnviado();
		adapter = new RVUtilitarioPedidoAdapter(getContext(), lista, this);
		recyclerView.setAdapter(adapter);
	}
}
