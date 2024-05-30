package vendas.telas;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import persistencia.adapters.ClienteAdapter;
import persistencia.brl.*;
import persistencia.dto.*;
import venda.util.Global;

public class ClienteLista extends ListActivity {
	
	private static final int MENU_ORDENAR_NOME = 1;
	private static final int MENU_PESQUISA_FANTASIA = 2;
	private static final int MENU_PESQUISA_RSOCIAL = 3;
	private static final int MENU_COMPLEMENTO = 4;
	private static final int MENU_CONTASRECEBER = 5;
	private static final int MENU_JUSTIFICATIVA = 6;
	private static final int MENU_PEDIDO = 7;
	private static final int MENU_ORDENAR_SEQVISITA = 8;
	private static final int MENU_TODOS = 9;

	private static int RETORNO_ORDENACAO = 1;
	
	ClienteBRL brl;
	ContaReceberBRL crbrl;
	
	@Override
	public void onCreate(Bundle e){
		super.onCreate(e);
		this.setTitle(Global.tituloAplicacao);

		registerForContextMenu(getListView());
	}
	
	
	@Override
	protected void onResume() {
		super.onResume();
		brl = new ClienteBRL(getBaseContext());
		crbrl = new ContaReceberBRL(getBaseContext());
		setListAdapter(new ClienteAdapter(getBaseContext(), brl.getRotaDiaOrdenado("nome")));
	}


	@Override  
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {  
    super.onCreateContextMenu(menu, v, menuInfo);  
        menu.setHeaderTitle("Sub Menu Cliente");
        menu.add(0,MENU_COMPLEMENTO,0,"Complemento"); 
        menu.add(0,MENU_CONTASRECEBER,0,"Contas a Receber"); 
        menu.add(0,MENU_JUSTIFICATIVA ,0,"Justificativa"); 
        menu.add(0,MENU_PEDIDO ,0,"Pedido"); 
    }  

	protected void onListItemClick(ListView l, View v, int position, long id){
		 ClienteDTO dto = (ClienteDTO) l.getAdapter().getItem(position);
		 Double totReceber = crbrl.getTotalReceberCliente(dto.getCodCliente());

		String longMessage = "Codigo: " + dto.getCodCliente().toString() + "\n"
			 + "R.Social: " + dto.getRazaoSocial() + "\n"
			 + "Fantasia: " + dto.getNome() + "\n"
			 + "CPF/CNPJ: " + dto.getCpfCnpj() + "\n"
			 + "Endereço: " + dto.getEndereco() + "\n"
			 + "Telefone: " + dto.getTelefone() + "\n"
			 + "Limite Crédito: " + dto.getLimiteCredito() + "\n"
		   	 + "Saldo disponível: " + (dto.getLimiteCredito() - totReceber)  + "\n"
			 + "C.Receber em aberto: " + totReceber + "\n"
			 + "Prazo: " + dto.getPrazo().toString();
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(longMessage)
				.setTitle("Mensagem")
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// Ação ao clicar no botão OK
					}
				});
		AlertDialog dialog = builder.create();
		dialog.show();
	 }

	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_cliente, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_ordenar_nome:
				setListAdapter(new ClienteAdapter(getBaseContext(), brl.getRotaDiaOrdenado("nome")));
				return true;
			case R.id.menu_ordenar_seqvisita:
				setListAdapter(new ClienteAdapter(getBaseContext(), brl.getRotaDiaOrdenado("seqVisita")));
				return true;
			case R.id.menu_pesquisar_fantasia:
				pesquisa(1);
				return true;
			case R.id.menu_pesquisa_rsocial:
				pesquisa(2);
				return true;
			case R.id.menu_mostrar_todos:
				setListAdapter(new ClienteAdapter(getBaseContext(), brl.getTodosOrdenado("nome")));
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	 @Override
	 public boolean onMenuItemSelected(int featureID, MenuItem menu){
		
		 ListView l = getListView();
		 AdapterContextMenuInfo info = (AdapterContextMenuInfo) menu.getMenuInfo();
		 ClienteDTO cliDTO = new ClienteDTO();
		 if (info != null)
			 cliDTO = (ClienteDTO)l.getAdapter().getItem(info.position);

		 switch (menu.getItemId()){
		 
/*		 case MENU_ORDENAR_NOME: // mensagem
			 setListAdapter(new ClienteAdapter(getBaseContext(), brl.getRotaDiaOrdenado("nome")));
			 
			 return true;
			  
			//startActivityForResult(new Intent(getBaseContext(), FiltroFornecedor.class), RETORNO_FORNECEDOR);
		 case MENU_ORDENAR_SEQVISITA: // mensagem 
			 setListAdapter(new ClienteAdapter(getBaseContext(), brl.getRotaDiaOrdenado("seqVisita")));
			 
			 return true;
		 case MENU_TODOS: // mensagem
				 setListAdapter(new ClienteAdapter(getBaseContext(), brl.getTodosOrdenado("nome")));

				 return true;
		 case MENU_PESQUISA_FANTASIA:
			 pesquisa(1);
			 
			 return true;
		 case MENU_PESQUISA_RSOCIAL:
			 pesquisa(2);
			 
			 return true;*/
		 case MENU_COMPLEMENTO:
			 startActivity(new Intent(getBaseContext(), ClienteComplemento.class).putExtra("idCliente", cliDTO.getId()));
			 
			 return true;
		 case MENU_CONTASRECEBER:
			 startActivity(new Intent(getBaseContext(), ClienteContasReceber.class).putExtra("codCliente", cliDTO.getCodCliente()));
			 
			 return true;
		 case MENU_JUSTIFICATIVA:
			 startActivity(new Intent(getBaseContext(), JustificativaTabContainer.class).putExtra("idCliente", cliDTO.getId()));
			 
			 return true;
		 case MENU_PEDIDO:
			 if(cliDTO.getRotaDia().equals("S"))
				 startActivity(new Intent(getBaseContext(), PedidoTabContainer.class).putExtra("idCliente", cliDTO.getId()));
			 else
				 venda.util.mensagem.messageBox(this, "Alerta", "Pedido n�o permitido para cliente fora de rota", "Ok");
			 
			 return true;
		 }
		 return false;
	 }
	 
	 private void pesquisa(final int tipoPesquisa){
		 AlertDialog.Builder pesquisa = new AlertDialog.Builder(this);
		 
		 pesquisa.setTitle("Pesquisa cliente");
		 pesquisa.setMessage("Informe o nome do cliente");
		 pesquisa.setCancelable(false);
		 
		 final EditText input = new EditText(this);
		 pesquisa.setView(input);
		 
		 pesquisa.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String value = input.getText().toString();	
				ClienteBRL cliBRL = new ClienteBRL(getBaseContext());
				if (tipoPesquisa == 1)
					setListAdapter(new ClienteAdapter(getBaseContext(), cliBRL.getByNome(value)));
				else
					setListAdapter(new ClienteAdapter(getBaseContext(), cliBRL.getByRazaoSocial(value)));
			}
		});
		 
		 pesquisa.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				
			}
		});
		 
		 AlertDialog alertDialog = pesquisa.create();

		 alertDialog.show();
		 
	 }
	 	 
}
