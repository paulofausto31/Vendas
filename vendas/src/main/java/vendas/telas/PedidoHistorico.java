package vendas.telas;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.content.Context;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import persistencia.adapters.RVHistoricoPedidoAdapter;
import persistencia.brl.ClienteBRL;
import persistencia.brl.ItenPedidoBRL;
import persistencia.brl.PedidoBRL;
import persistencia.dto.ItenPedidoDTO;
import persistencia.dto.PedidoDTO;
import venda.util.Global;
import venda.util.Util;

@SuppressLint("NewApi")
public class PedidoHistorico extends Fragment implements RVHistoricoPedidoAdapter.OnItemLongClickListener {

	private RecyclerView recyclerView;
	private RVHistoricoPedidoAdapter adapter;
	private PedidoTabContainer mainActivity;
	PedidoDTO pedDTO;
	ItenPedidoBRL itpBRL;
	PedidoBRL pedBRL;
	List<PedidoDTO> lista;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_pedido_historico, container, false);
		recyclerView = view.findViewById(R.id.recycler_view_pedido_historico);
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

		pedBRL = new PedidoBRL(getContext());
		itpBRL = new ItenPedidoBRL(getContext());

		return view;
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if (context instanceof PedidoTabContainer) {
			mainActivity = (PedidoTabContainer) context;
		}
	}

	@Override
	public void onItemLongClick(View view, int position) {
		PedidoDTO pedDTO = lista.get(position);
		PopupMenu popup = new PopupMenu(getContext(), view);
		popup.getMenuInflater().inflate(R.menu.popup_historico, popup.getMenu());
		popup.setOnMenuItemClickListener(item -> {
			if (item.getItemId() == R.id.action_historico_editar) {
				venda.util.Global.pedidoGlobalDTO = pedDTO;
				RetornaTabPedidoBasico();
				return true;
			}
			if (item.getItemId() == R.id.action_historico_desconto){
				if (!itpBRL.ExisteDA(pedDTO.getId()))
					DescontoAcrescimo('D', "Desconto", "Informe o Desconto", pedDTO);
				else
					Toast.makeText(getContext(), "Este pedido ja possui desconto ou acrescimo", Toast.LENGTH_SHORT).show();

				return true;
			}
			if (item.getItemId() == R.id.action_historico_acrescimo){
				if (!itpBRL.ExisteDA(pedDTO.getId()))
					DescontoAcrescimo('A', "Acrescimo", "Informe o Acrescimo", pedDTO);
				else
					Toast.makeText(getContext(), "Este pedido ja possui desconto ou acrescimo", Toast.LENGTH_SHORT).show();

				return true;
			}
			if (item.getItemId() == R.id.action_historico_excluir) {
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
						venda.util.Global.pedidoGlobalDTO = new PedidoDTO();
						RetornaTabPedidoBasico();
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

	private void RetornaTabPedidoBasico() {
		if (mainActivity != null) {
			ViewPager2 viewPager = mainActivity.getViewPager();
			viewPager.setCurrentItem(0); // Index da aba que você deseja abrir
		}
	}

	/*
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		pedBRL = new PedidoBRL(getBaseContext());
		itpBRL = new ItenPedidoBRL(getBaseContext());
		registerForContextMenu(getListView());		
	}

	@Override  
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {  
    super.onCreateContextMenu(menu, v, menuInfo);  
        menu.setHeaderTitle("Sub Menu Historico");
        menu.add(0,MENU_EDITAR,0,"Editar Pedido"); 
        menu.add(0,MENU_EXCLUIR,0,"Excluir Pedido"); 
        menu.add(0,MENU_VISUALIZAR,0,"Visualizar Pedido"); 
        menu.add(0,MENU_DESCONTO,0,"Desconto Pedido"); 
        menu.add(0,MENU_ACRESCIMO,0,"Acrescimo Pedido"); 
        menu.add(0,MENU_PDF,0,"Gerar PDF");
		menu.add(0,MENU_ENVIAR,0,"Enviar Pedido");
    }  
	
	 @Override
	 public boolean onMenuItemSelected(int featureID, MenuItem menu){
		 ListView l = getListView();
		 AdapterContextMenuInfo info = (AdapterContextMenuInfo) menu.getMenuInfo();
		 pedDTO = (PedidoDTO)l.getAdapter().getItem(info.position);
		 if (pedDTO.getBaixado() == 1 && menu.getItemId() != 3){
			 Toast.makeText(getBaseContext(), "Este pedido ja foi baixado/enviado e não pode ser manipulado", Toast.LENGTH_SHORT).show();
			 return false;
		 }
		
		 switch (menu.getItemId()){
		 
		 case MENU_EDITAR:  
			 venda.util.Global.pedidoGlobalDTO = pedDTO;
			 RetornaTabPedidoBasico();
			 
			 return true;
		 case MENU_VISUALIZAR:  
			 venda.util.Global.pedidoGlobalDTO = pedDTO;
			 RetornaTabPedidoBasico();
			 
			 return true;
		 case MENU_DESCONTO:
			 if (!itpBRL.ExisteDA(pedDTO.getId()))
				 DescontoAcrescimo('D', "Desconto", "Informe o Desconto", pedDTO);
			 else
				 Toast.makeText(getBaseContext(), "Este pedido ja possui desconto ou acrescimo", Toast.LENGTH_SHORT).show();
			 
			 return true;
		 case MENU_PDF:
			 criandoPdf();
			 
			 return true;

		 case MENU_ENVIAR:
			 EnviarPedido(pedDTO);

			 return true;
		 case MENU_ACRESCIMO:  
			 if (!itpBRL.ExisteDA(pedDTO.getId()))
				 DescontoAcrescimo('A', "Acrescimo", "Informe o Acrescimo", pedDTO);
			 else
				 Toast.makeText(getBaseContext(), "Este pedido ja possui desconto ou acrescimo", Toast.LENGTH_SHORT).show();
			 
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
					venda.util.Global.pedidoGlobalDTO = new PedidoDTO();			
					RetornaTabPedidoBasico();
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

	private void EnviarPedido(PedidoDTO pedDTO) {
		PedidoBRL pedBRL = new PedidoBRL(getContext());
		ItenPedidoBRL itpBRL = new ItenPedidoBRL(getContext());

		String pedidoWS = "p|" +
						  pedDTO.getCodCliente().toString() + "|" + pedDTO.getDataPedido().toString()    + "|" +
				   		  pedDTO.getHoraPedido().toString() + "|" + pedDTO.getHoraPedidoFim().toString() + "|" +
				          pedDTO.getParcela().toString()    + "|" + pedDTO.getPrazo().toString()         + "|" +
				          pedDTO.getFormaPgto().toString()  + "|" + pedDTO.getCodVendedor().toString()   + "|" +
				          pedDTO.getLatitude().toString()   + "|" + pedDTO.getLongitude().toString()     + "|" +
				          pedDTO.getId().toString()         + "|" + pedDTO.getInfAdicional().toString();

		List<ItenPedidoDTO> itens = itpBRL.getByCodPedido(pedDTO.getId());
		for (ItenPedidoDTO itenPedidoDTO : itens) {
			pedidoWS += "i|" +
					    itenPedidoDTO.getCodProduto().toString() + "|" + itenPedidoDTO.getQuantidade().toString() + "|" +
					    itenPedidoDTO.getPreco().toString();
		}
		Global.pedidoWS = pedidoWS;
	}
*/

	@SuppressLint("NewApi")
	private void criandoPdf() {
         Document document = new Document();
		 try {
			 String filename = "teste.pdf";
			 String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/MeuPdf";
			 File dir = new File(path, filename); 
			 if (!dir.exists()) { 
				 dir.getParentFile().mkdirs(); 
				 } 
			 FileOutputStream fOut = new FileOutputStream(dir); 
			 fOut.flush(); 
			 PdfWriter.getInstance(document, fOut);
			 document.open();
			 document.add(new Paragraph(Util.FormataSpaces("Aqui esta meu pdf ",5,0)));
			 } 
		 catch (FileNotFoundException e) {
			 e.printStackTrace(); 
			 } 
		 catch (IOException e) { 
			 e.printStackTrace(); 
			 } 
		 catch (Exception e) {
			 Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
			 e.printStackTrace(); 
			 } 
		 finally {
			 document.close(); 
		 }
	 }
	 
	 private void DescontoAcrescimo(final char tipo, final String titulo, final String mensagem, final PedidoDTO pedDTO){
		 AlertDialog.Builder pesquisa = new AlertDialog.Builder(getContext());
		 
		 pesquisa.setTitle(titulo);
		 pesquisa.setMessage(mensagem);
		 pesquisa.setCancelable(false);
		 
		 final EditText input = new EditText(getContext());
		 pesquisa.setView(input);
		 
		 pesquisa.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Double value = Double.parseDouble(input.getText().toString());	
				ClienteBRL cliBRL = new ClienteBRL(getContext());
				if (tipo == 'D')
					SetDescontoPedido(pedDTO, value);
				else
					SetAcrescimoPedido(pedDTO, value);
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
	 
	private void SetDescontoPedido(PedidoDTO pedDTO, Double value) {
		Double preco;
		ItenPedidoBRL itpBRL = new ItenPedidoBRL(getContext());
		List<ItenPedidoDTO> itens = itpBRL.getByCodPedido(pedDTO.getId());
		for (ItenPedidoDTO itenPedidoDTO : itens) {
			preco = itenPedidoDTO.getPreco();
			preco = preco - (preco * (value / 100));
			
			DecimalFormat formatador = new DecimalFormat("##,##00.00");  
		    String precoFormatado = formatador.format(preco);  
		    precoFormatado = precoFormatado.replace(',', '.'); 		

			itenPedidoDTO.setPreco(Double.parseDouble(precoFormatado));
			itenPedidoDTO.setDA("D");
			itenPedidoDTO.setDAValor(value);
			itpBRL.Update(itenPedidoDTO);
		}
	}

	private void SetAcrescimoPedido(PedidoDTO pedDTO, Double value) {
		Double preco;
		ItenPedidoBRL itpBRL = new ItenPedidoBRL(getContext());
		List<ItenPedidoDTO> itens = itpBRL.getByCodPedido(pedDTO.getId());
		for (ItenPedidoDTO itenPedidoDTO : itens) {
			preco = itenPedidoDTO.getPreco();
			preco = preco + (preco * (value / 100));
			itenPedidoDTO.setPreco(preco);
			itenPedidoDTO.setDA("A");
			itenPedidoDTO.setDAValor(value);
			itpBRL.Update(itenPedidoDTO);
		}
	}
/*
	private void RetornaTabPedidoBasico() {
    TabActivity tabs = (TabActivity) getParent();
    tabs.getTabHost().setCurrentTab(0); 			 
	}
*/
	@Override
	public void onResume() {
		super.onResume();
		PedidoBRL pedBRL = new PedidoBRL(getContext());
		lista = pedBRL.getByCodCliente(venda.util.Global.pedidoGlobalDTO.getCodCliente());
		adapter = new RVHistoricoPedidoAdapter(getContext(), lista, this);
		recyclerView.setAdapter(adapter);
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		PedidoDTO pedDTO = venda.util.Global.pedidoGlobalDTO;
		pedDTO.setHoraPedidoFim(venda.util.Util.getTime());
		pedBRL.Update(pedDTO);
		venda.util.Global.pedidoGlobalDTO = pedDTO;
	}


}
