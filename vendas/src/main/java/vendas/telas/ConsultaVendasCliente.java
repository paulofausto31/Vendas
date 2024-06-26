package vendas.telas;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import persistencia.adapters.RVConsultaClienteAdapter;
import persistencia.brl.ClienteBRL;
import persistencia.brl.ItenPedidoBRL;
import persistencia.brl.PedidoBRL;
import persistencia.dto.ClienteDTO;
import persistencia.dto.PedidoDTO;
import venda.util.Global;

public class ConsultaVendasCliente extends Fragment implements RVConsultaClienteAdapter.OnItemLongClickListener {

	private RecyclerView recyclerView;
	private RVConsultaClienteAdapter adapter;
	List<PedidoDTO> lista;
	Intent intent;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_consulta_cliente, container, false);
		recyclerView = view.findViewById(R.id.recycler_view_consulta_cliente);
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

		return view;
	}

	@Override
	public void onItemLongClick(View view, int position) {
		PedidoDTO pedDTO = lista.get(position);
		ClienteBRL cliBRL = new ClienteBRL(getContext());
		ClienteDTO cliDTO = cliBRL.getByCodCliente(pedDTO.getCodCliente());
		PopupMenu popup = new PopupMenu(getContext(), view);
		popup.getMenuInflater().inflate(R.menu.popup_consulta_cliente, popup.getMenu());
		popup.setOnMenuItemClickListener(item -> {
			if (item.getItemId() == R.id.action_consulta_cliente_novo) {
				intent = new Intent(getContext(), PedidoTabContainer.class);
				intent.putExtra("idCliente", cliDTO.getId());
				//intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				return true;
			}
			if (item.getItemId() == R.id.action_consulta_cliente_editar) {
				venda.util.Global.pedidoGlobalDTO = pedDTO;
				intent = new Intent(getContext(), PedidoTabContainer.class);
				intent.putExtra("idCliente", 0);
				//intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				return true;
			}
			if (item.getItemId() == R.id.action_consulta_cliente_excluir) {
				AlertDialog.Builder confirmacao = new AlertDialog.Builder(getContext());

				confirmacao.setTitle("Excluir Pedido");
				confirmacao.setMessage("Confirma Exclusão deste Pedido ?");
				confirmacao.setCancelable(false);

				confirmacao.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						PedidoBRL pedBRL = new PedidoBRL(getContext());
						ItenPedidoBRL itpBRL = new ItenPedidoBRL(getContext());
						itpBRL.deleteByCodPedido(pedDTO.getId());
						pedBRL.delete(pedDTO);
						onResume();
					}
				});

				confirmacao.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();

					}
				});

				AlertDialog alertDialog = confirmacao.create();

				alertDialog.show();

				return true;
			}
			return false;
		});
		popup.show();
	}

	@Override
	public void onResume() {
		super.onResume();
		PedidoBRL brl = new PedidoBRL(getContext());
		lista = brl.getAllPedAberto();
		adapter = new RVConsultaClienteAdapter(getContext(), lista, this);
		recyclerView.setAdapter(adapter);
	}
}





		/*extends ListActivity {

	private static final int MENU_NOVO = 1;
	private static final int MENU_EDITAR = 2;
	private static final int MENU_EXCLUIR = 3;
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
		setListAdapter(new ConsultaClienteAdapter(getBaseContext(), brl.getAllPedAberto()));		
	}

	@Override  
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {  
    super.onCreateContextMenu(menu, v, menuInfo);  
        menu.setHeaderTitle("Sub Menu Consulta");
        menu.add(0,MENU_NOVO,0,"Novo Pedido"); 
        menu.add(0,MENU_EDITAR,0,"Editar Pedido"); 
        menu.add(0,MENU_EXCLUIR,0,"Excluir Pedido"); 
    }  
	 
	 @Override
	 public boolean onMenuItemSelected(int featureID, MenuItem menu){
		
		 ClienteBRL cliBRL;
		 ClienteDTO cliDTO;
		 ListView l = getListView();
		 AdapterContextMenuInfo info = (AdapterContextMenuInfo) menu.getMenuInfo();
		 if (info != null)
			 pedDTO = (PedidoDTO)l.getAdapter().getItem(info.position);

		 switch (menu.getItemId()){
		 
		 case MENU_NOVO: // mensagem
			 cliBRL = new ClienteBRL(getBaseContext());
			 cliDTO = cliBRL.getByCodCliente(pedDTO.getCodCliente());
			 startActivity(new Intent(getBaseContext(), PedidoTabContainer.class).putExtra("idCliente", cliDTO.getId()));
			 
			 return true;
			  
			//startActivityForResult(new Intent(getBaseContext(), FiltroFornecedor.class), RETORNO_FORNECEDOR);
		 case MENU_EDITAR:
			 venda.util.Global.pedidoGlobalDTO = pedDTO;
			 startActivity(new Intent(getBaseContext(), PedidoTabContainer.class));
			 
			 return true;
		 case MENU_EXCLUIR:
			 AlertDialog.Builder confirmacao = new AlertDialog.Builder(this);
			 
			 confirmacao.setTitle("Excluir Pedido");
			 confirmacao.setMessage("Confirma Exclusão deste Pedido ?");
			 confirmacao.setCancelable(false);
			 
			 confirmacao.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					PedidoBRL pedBRL = new PedidoBRL(getBaseContext());
					ItenPedidoBRL itpBRL = new ItenPedidoBRL(getBaseContext());
					itpBRL.deleteByCodPedido(pedDTO.getId());
					pedBRL.delete(pedDTO);
					onResume();
				}
			});
			 
			 confirmacao.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
					
				}
			});
			 
			 AlertDialog alertDialog = confirmacao.create();

			 alertDialog.show();
			 			 
			 return true;
		 }
		 return false;
	 }
	 

}*/
