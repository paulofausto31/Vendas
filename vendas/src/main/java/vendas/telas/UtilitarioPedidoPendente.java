package vendas.telas;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import java.util.List;

import persistencia.adapters.RVUtilitarioPedidoAdapter;
import persistencia.brl.PedidoBRL;
import persistencia.dto.PedidoDTO;

public class UtilitarioPedidoPendente extends Fragment implements RVUtilitarioPedidoAdapter.OnItemLongClickListener {

    private RecyclerView recyclerView;
    private RVUtilitarioPedidoAdapter adapter;
    PedidoBRL brl;
    List<PedidoDTO> lista;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pendentes, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_pendentes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        brl = new PedidoBRL(getContext());
        lista = brl.getAllPedAberto();
        adapter = new RVUtilitarioPedidoAdapter(getContext(), lista,this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }
}

