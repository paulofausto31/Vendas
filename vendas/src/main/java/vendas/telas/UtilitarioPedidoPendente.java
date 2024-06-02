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
import java.util.Arrays;
import java.util.List;

import persistencia.adapters.PedidoSelecaoAdapter;
import persistencia.adapters.RVUtilitarioPedidoAdapter;
import persistencia.brl.PedidoBRL;
import persistencia.dto.PedidoDTO;
import venda.util.Global;

public class UtilitarioPedidoPendente extends Fragment {

    private RecyclerView recyclerView;
    private RVUtilitarioPedidoAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pendentes, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_pendentes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        PedidoBRL brl = new PedidoBRL(getContext());
        List<PedidoDTO> lista = brl.getAllPedAberto();
        adapter = new RVUtilitarioPedidoAdapter(getContext(), lista);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
/*extends ListActivity {

    private static final int MENU_ABREPEDIDO = 1;
    private static final int MENU_FECHAPEDIDO = 2;

    PedidoDTO pedDTO = new PedidoDTO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle(Global.tituloAplicacao);

        registerForContextMenu(getListView());
    }


    @Override
    protected void onResume() {
        super.onResume();
        PedidoBRL brl = new PedidoBRL(getBaseContext());
        setListAdapter(new PedidoSelecaoAdapter(getBaseContext(), brl.getAllPedAberto()));
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Sub Menu Utilitário");
        menu.add(0,MENU_ABREPEDIDO,0,"Abre Pedido");
        menu.add(0,MENU_FECHAPEDIDO,0,"Fecha Pedido");
    }

    @Override
    public boolean onMenuItemSelected(int featureID, MenuItem menu){

        ListView l = getListView();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menu.getMenuInfo();
        PedidoBRL pedBRL = new PedidoBRL(getBaseContext());
        if (info != null)
            pedDTO = (PedidoDTO)l.getAdapter().getItem(info.position);

        switch (menu.getItemId()){

            case MENU_ABREPEDIDO: // mensagem
                pedBRL.AbrePedidoPendente(pedDTO.getId());
                setListAdapter(new PedidoSelecaoAdapter(getBaseContext(), pedBRL.getAllPedAberto()));
                return true;
            case MENU_FECHAPEDIDO: // mensagem
                pedBRL.FechaPedidoPendente(pedDTO.getId());
                setListAdapter(new PedidoSelecaoAdapter(getBaseContext(), pedBRL.getAllPedAberto()));
                return true;
        }
        return false;
    }


}*/
