package vendas.telas;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.content.Context;
import android.content.pm.PackageManager;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.BuildConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import TaskNetwork.ApiService;
import persistencia.adapters.RVHistoricoPedidoAdapter;
import persistencia.brl.ClienteBRL;
import persistencia.brl.ClienteNaoPositivadoBRL;
import persistencia.brl.ItenPedidoBRL;
import persistencia.brl.PedidoBRL;
import persistencia.dto.ClienteDTO;
import persistencia.dto.ClienteNaoPositivadoDTO;
import persistencia.dto.ItenPedidoDTO;
import persistencia.dto.PedidoDTO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import venda.util.DateSerializer;
import venda.util.Global;
import venda.util.PDFGenerator;
import venda.util.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@SuppressLint("NewApi")
public class PedidoHistorico extends Fragment implements RVHistoricoPedidoAdapter.OnItemLongClickListener {

	private RecyclerView recyclerView;
	private RVHistoricoPedidoAdapter adapter;
	private PedidoTabContainer mainActivity;
	PedidoDTO pedDTO;
	ItenPedidoBRL itpBRL;
	PedidoBRL pedBRL;
	List<PedidoDTO> lista;
	ApiService apiService;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_pedido_historico, container, false);
		recyclerView = view.findViewById(R.id.recycler_view_pedido_historico);
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

		pedBRL = new PedidoBRL(getContext());
		itpBRL = new ItenPedidoBRL(getContext());

		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl("http://192.168.100.27:8080/") // 216.137.189.176
				.addConverterFactory(GsonConverterFactory.create())
				.build();

		apiService = retrofit.create(ApiService.class);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && false == Environment.isExternalStorageManager()) {
			Uri uri = Uri.parse("package:vendas.telas");
			//Uri uri = Uri.parse("package:" + BuildConfig.APPLICATION_ID);
			startActivity(new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, uri));
		}

		return view;
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if (context instanceof PedidoTabContainer) {
			mainActivity = (PedidoTabContainer) context;
		}
	}

	public String convertStringToDate(String dateString) {
		// Formato atual da data na string
		String currentPattern = "dd/MM/yyyy";

		// Novo formato desejado para a data na string
		String newPattern = "yyyy-MM-dd";
		try {
			// Primeiro, convertemos a string para o formato Date atual
			SimpleDateFormat currentFormat = new SimpleDateFormat(currentPattern);
			Date date = currentFormat.parse(dateString);

			// Em seguida, formatamos a data para o novo formato desejado
			SimpleDateFormat newFormat = new SimpleDateFormat(newPattern);
			String formattedDate = newFormat.format(date);

			// Exibindo o resultado
			return formattedDate;
		} catch (ParseException e) {
			// Tratamento de exceção se a string não puder ser analisada no formato atual
			e.printStackTrace();
			return "";
		}
	}

	private void EnviaClienteNaoPositivado(List<ClienteNaoPositivadoDTO> lstClienteNaoPositivado) {
		// Configurar Gson para considerar apenas os campos anotados com @Expose
		Gson gson = new GsonBuilder()
				.excludeFieldsWithoutExposeAnnotation()
				.registerTypeAdapter(Date.class, new DateSerializer())
				.create();

		for (ClienteNaoPositivadoDTO cnpDTO : lstClienteNaoPositivado) {
			ClienteNaoPositivadoDTO newCnpDTO = cnpDTO;
			String newDataIni = convertStringToDate(cnpDTO.getData());
			newCnpDTO.setData(newDataIni);
			newCnpDTO.setDataFim(newDataIni);
			newCnpDTO.setHora(cnpDTO.getHora().substring(0,5));
			newCnpDTO.setHoraFim(cnpDTO.getHoraFim().substring(0,5));
			newCnpDTO.setCodEmpresa(Global.codEmpresa.substring(1,14));

			// Converter o objeto pedDTO para JSON e registrar no log
			String json = gson.toJson(newCnpDTO);
			Log.d("JSON", "JSON gerado: " + json);

			Call<Void> call = apiService.enviarClienteNaoPositivado(newCnpDTO);
			call.enqueue(new Callback<Void>() {
				@Override
				public void onResponse(Call<Void> call, Response<Void> response) {
					if (response.isSuccessful()) {
						// Pedido enviado com sucesso
						Toast.makeText(getContext(), "Cliente não positivado enviado com sucesso", Toast.LENGTH_SHORT).show();
						Log.d("API", "Cliente não positivado enviado com sucesso");
					} else {
						// Ocorreu um erro na resposta
						String errorMessage = "";
						try {
							errorMessage = response.errorBody().string();
						} catch (IOException e) {
							e.printStackTrace();
							errorMessage = "Erro ao ler a mensagem de erro.";
						}
						Toast.makeText(getContext(), "Falha ao enviar Cliente não positivado: " + errorMessage, Toast.LENGTH_SHORT).show();
						Log.d("API", "Erro: " + response.code() + " - " + errorMessage);
					}
				}

				@Override
				public void onFailure(Call<Void> call, Throwable t) {
					// Ocorreu um erro na comunicação
					Log.e("API", "Erro ao enviar Cliente não positivado", t);
				}
			});
		}
	}

	private void EnviaPedido(PedidoDTO pedDTO, List<ItenPedidoDTO> lstItemPedido) {
		// Configurar Gson para considerar apenas os campos anotados com @Expose
		Gson gson = new GsonBuilder()
				.excludeFieldsWithoutExposeAnnotation()
				.registerTypeAdapter(Date.class, new DateSerializer())
				.create();

		PedidoDTO newPedDTO = pedDTO;
		String newDataIni = convertStringToDate(pedDTO.getDataPedido());
		newPedDTO.setDataPedido(newDataIni);
		newPedDTO.setDataPedidoFim(newDataIni);
		newPedDTO.setHoraPedido(pedDTO.getHoraPedido().substring(0,5));
		newPedDTO.setHoraPedidoFim(pedDTO.getHoraPedidoFim().substring(0,5));
		newPedDTO.setCodEmpresa(pedDTO.getCodEmpresa().substring(1,14));
		newPedDTO.setItemPedido(lstItemPedido);
		// Converter o objeto pedDTO para JSON e registrar no log
		String json = gson.toJson(newPedDTO);
		Log.d("JSON", "JSON gerado: " + json);

		Call<Long> call = apiService.enviarPedido(newPedDTO);
		call.enqueue(new Callback<Long>() {
			@Override
			public void onResponse(Call<Long> call, Response<Long> response) {
				if (response.isSuccessful()) {
					// Pedido enviado com sucesso
					Long idPedidoMySQL = response.body();
					pedBRL.updatePedidoMySQL(pedDTO.getId(), idPedidoMySQL);
					Toast.makeText(getContext(), "Pedido enviado com sucesso", Toast.LENGTH_SHORT).show();
					Log.d("API", "Pedido enviado com sucesso");
				} else {
					// Ocorreu um erro na resposta
					String errorMessage = "";
					try {
						errorMessage = response.errorBody().string();
					} catch (IOException e) {
						e.printStackTrace();
						errorMessage = "Erro ao ler a mensagem de erro.";
					}
					Toast.makeText(getContext(), "Falha ao enviar pedido: " + errorMessage, Toast.LENGTH_SHORT).show();
					Log.d("API", "Erro: " + response.code() + " - " + errorMessage);
				}
			}

			@Override
			public void onFailure(Call<Long> call, Throwable t) {
				// Ocorreu um erro na comunicação
				Log.e("API", "Erro ao enviar pedido", t);
			}
		});
	}

	@Override
	public void onItemLongClick(View view, int position) {
		PedidoDTO pedDTO = lista.get(position);
		PopupMenu popup = new PopupMenu(getContext(), view);
		popup.getMenuInflater().inflate(R.menu.popup_historico, popup.getMenu());
		MenuItem enviarPedido = popup.getMenu().findItem(R.id.action_historico_enviar_pedido);
		enviarPedido.setVisible(false);
		popup.setOnMenuItemClickListener(item -> {
			if (item.getItemId() == R.id.action_historico_editar) {
				venda.util.Global.pedidoGlobalDTO = pedDTO;
				RetornaTabPedidoBasico();
				return true;
			}
			if (item.getItemId() == R.id.action_historico_enviar_pedido) {
				if (pedDTO.getBaixado() == 0) {
					List<ItenPedidoDTO> lstItemPedido = itpBRL.getByCodPedido(pedDTO.getId());
					EnviaPedido(pedDTO, lstItemPedido);
					ClienteNaoPositivadoBRL cnpBRL = new ClienteNaoPositivadoBRL(getContext());
					List<ClienteNaoPositivadoDTO> lstClienteNaoPositivado = cnpBRL.getAllAberto();
					EnviaClienteNaoPositivado(lstClienteNaoPositivado);
				}
				else
					Toast.makeText(getContext(), "Este pedido ja foi enviado !!", Toast.LENGTH_SHORT).show();
				return true;
			}
			if (item.getItemId() == R.id.action_historico_pdf) {
				ItenPedidoBRL itpBRL = new ItenPedidoBRL(getContext());
				ClienteBRL cliBRL = new ClienteBRL(getContext());
				ClienteDTO cliDTO = cliBRL.getByCodCliente(pedDTO.getCodCliente());
				List<ItenPedidoDTO> list = itpBRL.getByCodPedido(pedDTO.getId());
				PDFGenerator pdf = new PDFGenerator();
				pdf.createPDF(getContext(),"arquivoPDF.pdf", list, cliDTO);
				sharePDF("arquivoPDF.pdf");

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

	public void sharePDF(String fileName) {
		Context context = getContext();
		String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString();
		File file = new File(path, fileName);

		Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);

		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);
		shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
		shareIntent.setType("application/pdf");
		shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

		// Verifique se o WhatsApp está instalado
		if (isAppInstalled(context, "com.whatsapp")) {
			shareIntent.setPackage("com.whatsapp");
		} else {
			// O WhatsApp não está instalado, mostre um aviso ou use outro aplicativo de compartilhamento
			shareIntent = Intent.createChooser(shareIntent, "Escolha um aplicativo para compartilhar o PDF");
		}

		try {
			startActivity(shareIntent);
		} catch (android.content.ActivityNotFoundException ex) {
			// Mostrar uma mensagem de erro se não houver aplicativos que possam lidar com o Intent
			Toast.makeText(context, "Nenhum aplicativo encontrado para compartilhar o PDF", Toast.LENGTH_SHORT).show();
		}
	}

	// Método para verificar se um aplicativo está instalado
	private boolean isAppInstalled(Context context, String packageName) {
		PackageManager pm = context.getPackageManager();
		try {
			pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
			return true;
		} catch (PackageManager.NameNotFoundException e) {
			return false;
		}
	}

}
