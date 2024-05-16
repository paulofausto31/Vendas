package vendas.telas;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.android.volley.BuildConfig;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

import persistencia.brl.CaminhoFTPBRL;
import persistencia.brl.ClienteBRL;
import persistencia.brl.ClienteNaoPositivadoBRL;
import persistencia.brl.ConfiguracaoBRL;
import persistencia.brl.ContaReceberBRL;
import persistencia.brl.EmpresaBRL;
import persistencia.brl.FormaPgtoBRL;
import persistencia.brl.FornecedorBRL;
import persistencia.brl.GrupoBRL;
import persistencia.brl.ItenPedidoBRL;
import persistencia.brl.JustificativaPositivacaoBRL;
import persistencia.brl.PedidoBRL;
import persistencia.brl.PrecoBRL;
import persistencia.brl.ProdutoBRL;
import persistencia.brl.VendedorBRL;
import persistencia.dto.ClienteDTO;
import persistencia.dto.ClienteNaoPositivadoDTO;
import persistencia.dto.ConfiguracaoDTO;
import persistencia.dto.ContaReceberDTO;
import persistencia.dto.EmpresaDTO;
import persistencia.dto.FormaPgtoDTO;
import persistencia.dto.FornecedorDTO;
import persistencia.dto.GrupoDTO;
import persistencia.dto.ItenPedidoDTO;
import persistencia.dto.JustificativaPositivacaoDTO;
import persistencia.dto.PedidoDTO;
import persistencia.dto.PrecoDTO;
import persistencia.dto.ProdutoDTO;
import persistencia.dto.VendedorDTO;
import venda.util.Global;
import venda.util.Util;

public class Comunicacao extends Activity {

	private static final int MENU_CONFIGURACAO = 1;
	private static final int TIMEOUT_MILLISEC = 30000;
	//private static final int local = 2131230976;
	//private static final int remoto = 2131230968;
	//private static final int nenhum = 2131493092;
	//private static final int webService = 2131493093;
	private Button btnImportar;
	private Button btnExportar;
	private Button btnApagaBanco;
	private RadioGroup rbtDestino;
	private ProgressBar progresso;
	private Handler handler = new Handler();
	private Boolean processando = false;
	private String pastaDest;
	private String[] arquivosExportacao = new String[] { "TABPED.TXT", "TABPEI.TXT", "TABCNP.TXT" };
	private String[] arquivosImportacao = new String[] { "TABEMP.TXT", "TABVEN.TXT", "TABMOT.TXT", "TABPRO.TXT", "TABCLI.TXT",
			"TABPRE.TXT", "TABFOR.TXT", "TABLIN.TXT", "TABCFG.TXT", "TABPGT.TXT", "TABREC.TXT", "Final" };
//	private String[] arquivosImportacao = new String[] { "TABEMP.TXT", "TABMOT.TXT", "TABPRO.TXT", "TABCLI.TXT", "TABPRE.TXT",
//			"TABVEN.TXT", "TABFOR.TXT", "TABLIN.TXT", "TABCFG.TXT", "TABPGT.TXT", "TABREC.TXT", "Final" };
	ClienteBRL cliBRL;
	ConfiguracaoBRL cfgBRL;
	CaminhoFTPBRL ftpBRL;
	ProdutoBRL proBRL;
	String lstrlinha;
	FTPClient ftp;
	BufferedReader br;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comunicacao);
		this.setTitle(Global.tituloAplicacao);

		ftpBRL = new CaminhoFTPBRL(getBaseContext(), this);
		cfgBRL = new ConfiguracaoBRL(getBaseContext());
		btnImportar = (Button)findViewById(R.id.btnImportar);
		btnExportar = (Button)findViewById(R.id.btnExportar);
		btnApagaBanco = (Button)findViewById(R.id.btnApagaBanco);
		rbtDestino = (RadioGroup)findViewById(R.id.radioGroup1);
		progresso = (ProgressBar)findViewById(R.id.progressBar);
	    Global.caminhoFTPDTO = ftpBRL.getByEmpresa();
		if (Global.codVendedor.equals("9999"))
			btnApagaBanco.setVisibility(View.VISIBLE);

		GetComDefault();

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && false == Environment.isExternalStorageManager()) {
			Uri uri = Uri.parse("package:" + BuildConfig.APPLICATION_ID);
			startActivity(new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, uri));
		}

		btnApagaBanco.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				EmpresaBRL empBRL = new EmpresaBRL(getApplicationContext());
				empBRL.DeleteAll();
				VendedorBRL venBRL = new VendedorBRL(getApplicationContext());
				venBRL.DeleteAll();
				JustificativaPositivacaoBRL jusBRL = new JustificativaPositivacaoBRL(getApplicationContext());
				jusBRL.DeleteAll();
				ProdutoBRL proBRL = new ProdutoBRL((getApplicationContext()));
				proBRL.DeleteAll();
				ClienteBRL cliBRL = new ClienteBRL(getApplicationContext());
				cliBRL.DeleteAll();
				PrecoBRL preBRL = new PrecoBRL(getApplicationContext());
				preBRL.DeleteAll();
				FornecedorBRL forBRL = new FornecedorBRL(getApplicationContext());
				forBRL.DeleteAll();
				GrupoBRL gruBRL = new GrupoBRL(getApplicationContext());
				gruBRL.DeleteAll();
				ConfiguracaoBRL cfgBRL = new ConfiguracaoBRL(getApplicationContext());
				cfgBRL.DeleteAll();
				FormaPgtoBRL fpgBRL = new FormaPgtoBRL(getApplicationContext());
				fpgBRL.DeleteAll();
				ContaReceberBRL crbBRL = new ContaReceberBRL(getApplicationContext());
				crbBRL.DeleteAll();
				PedidoBRL pedBRL = new PedidoBRL(getApplicationContext());
				pedBRL.DeleteAll();
				ItenPedidoBRL itpBRL = new ItenPedidoBRL(getApplicationContext());
				itpBRL.DeleteAll();
				ClienteNaoPositivadoBRL cnpBRL = new ClienteNaoPositivadoBRL(getApplicationContext());
				cnpBRL.DeleteAll();
				CaminhoFTPBRL ftpBRL = new CaminhoFTPBRL(getApplicationContext());
				ftpBRL.DeleteAll();
			}
		});
			
		btnExportar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SetComDefault(rbtDestino);
				try {
					pastaDest = "/";
					HabilitaDesabilitaBotoes(false);
					processando = true;
					progresso.setVisibility(View.VISIBLE);
					progresso.setIndeterminate(true);
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							try {
								if (rbtDestino.getCheckedRadioButtonId() == R.id.rbtLocal ||
										rbtDestino.getCheckedRadioButtonId() == R.id.rbtRemoto) {

									ftp = ftpBRL.ConectaFTP();
									if (!ftpBRL.CheckFileExists(arquivosExportacao, ftp)) {
										if (ExportaArquivosFTP())
											venda.util.Global.retornoThead = "Concluído";
										else
											throw new Exception("Falha na exportação, repita o procedimento");
									} else
										throw new Exception("Importação pelo FTPInterpos pendente.");
								}
								else if(rbtDestino.getCheckedRadioButtonId() == R.id.rbtEmail){
									if (ExportaArquivosEmail())
										venda.util.Global.retornoThead = "Concluído";
									else
										throw new Exception("Falha na exportação, repita o procedimento");
								}
							} catch (IOException e) {
								e.printStackTrace();
								venda.util.Global.retornoThead = "O Servidor pode estar offLine";
							} catch (Exception e) {
								e.printStackTrace();
								venda.util.Global.retornoThead = e.getMessage();
							}
							ftpBRL.DesconectaFTP(ftp);

							handler.post(new Runnable() {
								
								@Override
								public void run() {
									Toast.makeText(getBaseContext(), venda.util.Global.retornoThead, Toast.LENGTH_SHORT).show();
									HabilitaDesabilitaBotoes(true);
									processando = false;
									progresso.setVisibility(View.GONE);
								}
							});
						}
					}).start();
				} catch (Exception e) {
				}
			}
		});
		
		btnImportar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				SetComDefault(rbtDestino);
				try {
					pastaDest = "/";
					HabilitaDesabilitaBotoes(false);
					processando = true;
					progresso.setVisibility(View.VISIBLE);
					progresso.setIndeterminate(true);
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							try {
								ftp = ftpBRL.ConectaFTP();
							} catch (NumberFormatException e) {
								e.printStackTrace();
							} catch (Exception e) {
								e.printStackTrace();
							}
							for (final String arquivoImp : arquivosImportacao) {
								handler.post(new Runnable() {
									
									@Override
									public void run() {
										if (arquivoImp.equals("Final")){
											HabilitaDesabilitaBotoes(true);
											processando = false;
											progresso.setVisibility(View.GONE);
											//Toast.makeText(getApplicationContext(), Global.retornoimportacao.toString(), Toast.LENGTH_LONG).show();
										}else{
											Toast.makeText(getBaseContext(), "Importando ".concat(arquivoImp), Toast.LENGTH_SHORT).show();
										}
									}
								});
								if (!arquivoImp.equals("Final"))
									RecebeArquivosTXT(arquivoImp);
							}
							ftpBRL.DesconectaFTP(ftp);
						}
					}).start();
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
	}

    private void btnConfiguracao_click(){
    	Intent comunicacao = new Intent(this, ConfiguracaoTabContainer.class);
    	startActivity(comunicacao);       	
    }    

    @Override
	 public boolean onCreateOptionsMenu(Menu menu){
		// Menu
		MenuItem MenuConfiguracao = menu.add(0, MENU_CONFIGURACAO, 0, "Configuração");

		return true; 
	 }
	 
	 @Override
	 public boolean onMenuItemSelected(int featureID, MenuItem menu){
		
		 switch (menu.getItemId()){
		 
		 case MENU_CONFIGURACAO:
	    	Intent configuracao = new Intent(this, ConfiguracaoTabContainer.class);
	    	startActivity(configuracao);       	
			 
	    	return true;
		 }
		 return false;
	 }
	 
	 private void GetComDefault(){
		 if (Global.caminhoFTPDTO == null || Global.caminhoFTPDTO.getComDefault() == null || Global.caminhoFTPDTO.getComDefault().equals("L"))
			rbtDestino.check(R.id.rbtLocal);
		 else if (Global.caminhoFTPDTO.getComDefault().equals("R"))
			rbtDestino.check(R.id.rbtRemoto);
		 else if (Global.caminhoFTPDTO.getComDefault().equals("E"))
			rbtDestino.check(R.id.rbtEmail);
		 else if (Global.caminhoFTPDTO.getComDefault().equals("W"))
			rbtDestino.check(R.id.rbtWebService);
		 else 
			rbtDestino.check(R.id.rbtLocal);
	 }
	 
	 private void SetComDefault(RadioGroup rbtDestino){

		 switch (rbtDestino.getCheckedRadioButtonId()){
			 case R.id.rbtLocal:
				 Global.caminhoFTPDTO.setComDefault("L");
				 break;
			 case R.id.rbtRemoto:
				 Global.caminhoFTPDTO.setComDefault("R");
				 break;
			 case R.id.rbtWebService:
				 Global.caminhoFTPDTO.setComDefault("W");
				 break;
			 case R.id.rbtEmail:
				 Global.caminhoFTPDTO.setComDefault("E");
				 break;
			 default:
				 Global.caminhoFTPDTO.setComDefault("L");
				 break;
		 }

		 ftpBRL.SalvaCaminhoFTP(Global.caminhoFTPDTO);
	 }


	private void RecebeArquivosTXT(String arquivo){
		if (rbtDestino.getCheckedRadioButtonId() == R.id.rbtWebService){
			Toast.makeText(getBaseContext(), "Importando ".concat(arquivo), Toast.LENGTH_SHORT).show();
			if (arquivo.equals("TABMOT.TXT"))
				Toast.makeText(getBaseContext(), "Importando ".concat(arquivo), Toast.LENGTH_SHORT).show();
			else if (arquivo.equals("TABPRO.TXT"))
				Toast.makeText(getBaseContext(), "Importando ".concat(arquivo), Toast.LENGTH_SHORT).show();
			else if (arquivo.equals("TABCFG.TXT"))
				TABPGT();
			else if (arquivo.equals("TABVEN.TXT"))
				Toast.makeText(getBaseContext(), "Importando ".concat(arquivo), Toast.LENGTH_SHORT).show();
			else if (arquivo.equals("TABPGT.TXT"))
				Toast.makeText(getBaseContext(), "Importando ".concat(arquivo), Toast.LENGTH_SHORT).show();
			else if (arquivo.equals("TABFOR.TXT"))
				Toast.makeText(getBaseContext(), "Importando ".concat(arquivo), Toast.LENGTH_SHORT).show();
			else if (arquivo.equals("TABFOR.TXT"))
				Toast.makeText(getBaseContext(), "Importando ".concat(arquivo), Toast.LENGTH_SHORT).show();
			else if (arquivo.equals("TABLIN.TXT"))
				Toast.makeText(getBaseContext(), "Importando ".concat(arquivo), Toast.LENGTH_SHORT).show();
			else if (arquivo.equals("TABPRE.TXT"))
				Toast.makeText(getBaseContext(), "Importando ".concat(arquivo), Toast.LENGTH_SHORT).show();
			else if (arquivo.equals("TABREC.TXT"))
				Toast.makeText(getBaseContext(), "Importando ".concat(arquivo), Toast.LENGTH_SHORT).show();
			else if (arquivo.equals("TABCLI.TXT"))
				Toast.makeText(getBaseContext(), "Importando ".concat(arquivo), Toast.LENGTH_SHORT).show();
		}
		else{
			ftpBRL.RecebeArquivoFTP(arquivo, ftp, pastaDest);
			File file = new File(Environment.getExternalStorageDirectory().toString().concat(pastaDest).concat("/").concat(arquivo));
			if (file.exists()){
				//Toast.makeText(getBaseContext(), "Importando ".concat(arquivo), Toast.LENGTH_SHORT).show();
				if (arquivo.equals("TABEMP.TXT"))
					ImportaEmpresa();
				else if (arquivo.equals("TABMOT.TXT"))
					ImportaJustificativaPositivacao();
				else if (arquivo.equals("TABPRO.TXT"))
					ImportaProduto();
				else if (arquivo.equals("TABCFG.TXT"))
					ImportaConfiguracao();
				else if (arquivo.equals("TABVEN.TXT"))
					ImportaVendedor();
				else if (arquivo.equals("TABPGT.TXT"))
					ImportaFormaPgto();
				else if (arquivo.equals("TABFOR.TXT"))
					ImportaFornecedor();
				else if (arquivo.equals("TABLIN.TXT"))
					ImportaGrupo();
				else if (arquivo.equals("TABPRE.TXT"))
					ImportaPreco();
				else if (arquivo.equals("TABREC.TXT"))
					ImportaContaReceber();
				else if (arquivo.equals("TABCLI.TXT"))
					ImportaCliente();
			}
		}
	}

	public void TABPGT() {
	    try {
	        FormaPgtoBRL brl = new FormaPgtoBRL(getApplicationContext());
	        FormaPgtoDTO dto = new FormaPgtoDTO();
	        // http://androidarabia.net/quran4android/phpserver/connecttoserver.php

	        // Log.i(getClass().getSimpleName(), "send  task - start");
	        HttpParams httpParams = new BasicHttpParams();
	        HttpConnectionParams.setConnectionTimeout(httpParams,
	                TIMEOUT_MILLISEC);
	        HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
	        //
	        HttpParams p = new BasicHttpParams();
	        // p.setParameter("name", pvo.getName());
	        p.setParameter("metodo", "consulta");

	        // Instantiate an HttpClient
	        HttpClient httpclient = new DefaultHttpClient(p);
	        String url = "http://pfsoft.esy.es/serverphp/tabpgt.php?metodo=consulta";
	        HttpPost httppost = new HttpPost(url);

	        // Instantiate a GET HTTP method
	        try {
	            Log.i(getClass().getSimpleName(), "send  task - start");
	            //
/*	            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
	                    2);
	            nameValuePairs.add(new BasicNameValuePair("user", "1"));
	            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));*/
	            ResponseHandler<String> responseHandler = new BasicResponseHandler();
	            String responseBody = httpclient.execute(httppost,
	                    responseHandler);
	            // Parse
	            JSONObject json = new JSONObject(responseBody);
	            JSONArray jArray = json.getJSONArray("tabpgt");

	            brl.DeleteAll();
	            for (int i = 0; i < jArray.length(); i++) {
	                JSONObject e = jArray.getJSONObject(i);

	                dto.setCodFPgto(e.getString("CodFPgto"));
	                dto.setDescricao(e.getString("Descricao"));
	                dto.setMulta(Double.parseDouble(e.getString("Multa").replace(',', '.')));
	                dto.setJuros(Double.parseDouble(e.getString("Juros").replace(',', '.')));
	                brl.InsereFormaPgto(dto);
	            }
	        } catch (ClientProtocolException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        // Log.i(getClass().getSimpleName(), "send  task - end");

	    } catch (Throwable t) {
	        Toast.makeText(this, "Request failed: " + t.toString(),
	                Toast.LENGTH_LONG).show();
	    }
	}
	
	private void HabilitaDesabilitaBotoes(Boolean flag) {
		btnImportar.setEnabled(flag);
		btnExportar.setEnabled(flag);
	}
	
	@Override
	public void onBackPressed() {
		if (!processando)
		super.onBackPressed();
	}

	@SuppressWarnings("finally")
	public int CountSizeArq(BufferedReader br){
		int cont = 0;
        try {
        	br.mark(1000000);
			while (br.readLine() != null) {
				cont += 1;
			}
			br.reset();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			return cont;
		}
	}
	
	public Boolean ExportaArquivosFTP() throws IOException {
		boolean retorno;
		PedidoBRL pedBRL = new PedidoBRL(getApplicationContext());
		int NumItensPedido = ExportaItensPedido();
		int NumPedidos = ExportaPedidos();
		ExportaClienteNPositivados();
		if (NumItensPedido > 0 && NumPedidos > 0) {
			for (String arquivoExp : arquivosExportacao) {
				ftpBRL.EnviaArquivoFTP(arquivoExp, ftp, pastaDest);
			}
			if (ftpBRL.CheckFileExists(arquivosExportacao, ftp)) {
				retorno = true;
				pedBRL.UpdateBaixado();
			} else
				retorno = false;
		}else
			retorno = false;
		return retorno;
}

	public void EnviarEmail(){
		String to = ftpBRL.getEmailDaEmpresa();
		final String from = "palmvendasit@gmail.com";
		String host = "smtp.gmail.com";
		String tabped = "TABPED.TXT";
		String tabpei = "TABPEI.TXT";
		String tabcnp = "TABCNP.TXT";
		String msgText1 = "Enviando arquivo.\n";
		String subject = "Pedidos vendedor " + Global.codVendedor;

		// cria algumas propriedades e obtem uma sessao padrao
		Properties props = System.getProperties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(from, "jugkimpfmloguqvo"); //mxjlpcucqrzmuwjx
					}
				});

		try
		{
			// cria a mensagem
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(from));
			InternetAddress[] address = {new InternetAddress(to)};
			msg.setRecipients(Message.RecipientType.TO, address);
			msg.setSubject(subject);

			// cria a primeira parte da mensagem
			MimeBodyPart mbp1 = new MimeBodyPart();
			mbp1.setText(msgText1);

			// cria a segunda parte da mensage
			MimeBodyPart mbp2 = new MimeBodyPart();
			MimeBodyPart mbp3 = new MimeBodyPart();
			MimeBodyPart mbp4 = new MimeBodyPart();

			// anexa o arquivo na mensagem
			String caminho = Environment.getExternalStorageDirectory().toString().concat(pastaDest).concat(tabped);
			FileDataSource fds = new FileDataSource(caminho);
			mbp2.setDataHandler(new DataHandler(fds));
			mbp2.setFileName(fds.getName());
			caminho = Environment.getExternalStorageDirectory().toString().concat(pastaDest).concat(tabpei);
			fds = new FileDataSource(caminho);
			mbp3.setDataHandler(new DataHandler(fds));
			mbp3.setFileName(fds.getName());
			caminho = Environment.getExternalStorageDirectory().toString().concat(pastaDest).concat(tabcnp);
			fds = new FileDataSource(caminho);
			mbp4.setDataHandler(new DataHandler(fds));
			mbp4.setFileName(fds.getName());

			// cria a Multipart
			Multipart mp = new MimeMultipart();
			mp.addBodyPart(mbp1);
			mp.addBodyPart(mbp2);
			mp.addBodyPart(mbp3);
			mp.addBodyPart(mbp4);

			// adiciona a Multipart na mensagem
			msg.setContent(mp);

			// configura a data: cabecalho
			msg.setSentDate(new Date());

			// envia a mensagem
			Transport.send(msg);

		}
		catch (MessagingException mex) {
			mex.printStackTrace();
			Exception ex = null;
			if ((ex = mex.getNextException()) != null) {
				ex.printStackTrace();
			}
		}
	}
	public Boolean ExportaArquivosEmail() throws IOException {
		boolean retorno = true;
		PedidoBRL pedBRL = new PedidoBRL(getApplicationContext());
		int NumItensPedido = ExportaItensPedido();
		int NumPedidos = ExportaPedidos();
		ExportaClienteNPositivados();
		if (NumItensPedido > 0 && NumPedidos > 0) {
			EnviarEmail();
			pedBRL.UpdateBaixado();
		}
		else
			retorno = false;
		return retorno;
	}
	
	private void ExportaClienteNPositivados() {
       String lstrNomeArq;
       File arq;
       byte[] dados;
       String lstrlinha;
       ClienteNaoPositivadoBRL cnpBRL = new ClienteNaoPositivadoBRL(getApplicationContext());
       try
       {
    	   lstrNomeArq = "TABCNP.TXT";
            
           arq = new File(Environment.getExternalStorageDirectory().toString().concat(pastaDest), lstrNomeArq);
           if (arq.exists()) {
			   arq.delete();
		   }
           FileOutputStream fos;
		   fos = new FileOutputStream(arq);

           List<ClienteNaoPositivadoDTO> list = cnpBRL.getAllAberto();

			   for (ClienteNaoPositivadoDTO itemCNP : list) {
				   lstrlinha = Util.FormataZeros(itemCNP.getCodCliente().toString(), 6);
				   lstrlinha = lstrlinha.concat(Util.FormataZeros(itemCNP.getCodJustificativa().toString(), 2));
				   lstrlinha = lstrlinha.concat(Util.FormataSpaces(itemCNP.getData(), 10));
				   lstrlinha = lstrlinha.concat(Util.FormataSpaces(itemCNP.getHora(), 5));
				   lstrlinha = lstrlinha.concat(Util.FormataSpaces(itemCNP.getObs(), 100));
				   lstrlinha = lstrlinha.concat(Util.FormataZeros(Global.codEmpresa, 4));
				   lstrlinha = lstrlinha.concat(Util.FormataSpaces(itemCNP.getDataFim(), 10));
				   lstrlinha = lstrlinha.concat(Util.FormataSpaces(itemCNP.getHoraFim(), 5));
				   lstrlinha = lstrlinha.concat("\r\n");
				   //transforma o texto digitado em array de bytes
				   dados = lstrlinha.getBytes();
				   //escreve os dados
				   fos.write(dados);
			   }
			   // fecha o arquivo
			   fos.flush();
			   fos.close();
			   cnpBRL.updateBaixado();
		} catch (Exception e) {
            venda.util.mensagem.trace(getApplicationContext(), "Erro : " + e.getMessage());
        } finally{
        	cnpBRL.CloseConection();
        }
	}

	private int ExportaPedidos() {
       String lstrNomeArq;
       File arq;
       int retorno = 0;
       byte[] dados;
       String lstrlinha;
       PedidoBRL pedBRL = new PedidoBRL(getApplicationContext());
       try
       {
    	   lstrNomeArq = "TABPED.TXT";
            
           arq = new File(Environment.getExternalStorageDirectory().toString().concat(pastaDest), lstrNomeArq);
           if (arq.exists())
        	   arq.delete();
           FileOutputStream fos;

           List<PedidoDTO> listPedido = pedBRL.getAllAberto();
           retorno = listPedido.size();
           if (retorno > 0)
           {
			   fos = new FileOutputStream(arq);

			   for (PedidoDTO itemPedido : listPedido) {
				   lstrlinha = Util.FormataZeros(itemPedido.getId().toString(), 4);
				   lstrlinha = lstrlinha.concat(Util.FormataZeros(itemPedido.getCodCliente().toString(), 6));
				   lstrlinha = lstrlinha.concat(Util.FormataZeros(itemPedido.getCodVendedor().toString(), 4));
				   lstrlinha = lstrlinha.concat(Util.FormataSpaces(itemPedido.getFormaPgto(), 5));
				   lstrlinha = lstrlinha.concat(Util.FormataZeros(itemPedido.getPrazo().toString(), 4));
				   lstrlinha = lstrlinha.concat(Util.FormataSpaces(itemPedido.getParcela().toString(), 3));
				   lstrlinha = lstrlinha.concat(Util.FormataSpaces(itemPedido.getDataPedido(), 10));
				   lstrlinha = lstrlinha.concat(Util.FormataSpaces(itemPedido.getHoraPedido(), 5));
				   lstrlinha = lstrlinha.concat(Util.FormataSpaces(itemPedido.getDataPedido(), 10));
				   if (itemPedido.getHoraPedidoFim() == null)
					   lstrlinha = lstrlinha.concat(Util.FormataSpaces(itemPedido.getHoraPedido(), 5));
				   else
					   lstrlinha = lstrlinha.concat(Util.FormataSpaces(itemPedido.getHoraPedidoFim(), 5));
				   lstrlinha = lstrlinha.concat(Util.FormataSpaces(itemPedido.getLatitude(), 20));
				   lstrlinha = lstrlinha.concat(Util.FormataSpaces(itemPedido.getLongitude(), 20));
				   lstrlinha = lstrlinha.concat(Util.FormataSpaces(itemPedido.getInfAdicional(), 255));
				   lstrlinha = lstrlinha.concat(Util.FormataZeros(Global.codEmpresa, 4));
				   lstrlinha = lstrlinha.concat("\r\n");
				   //transforma o texto digitado em array de bytes
				   dados = lstrlinha.getBytes();
				   //escreve os dados
				   fos.write(dados);
			   }
			   // fecha o arquivo
			   fos.flush();
			   fos.close();
		   }
           //pedBRL.UpdateBaixado();
		} catch (Exception e) {
            venda.util.mensagem.trace(getApplicationContext(), "Erro : " + e.getMessage());
        } finally{
        	pedBRL.CloseConection();
        	return retorno;
        }
	}

	private int ExportaItensPedido() {
       String lstrNomeArq;
       File arq;
       byte[] dados;
       int retorno = 0;
       String lstrlinha;
       ItenPedidoBRL itpBRL = new ItenPedidoBRL(getApplicationContext());
       try
       {
    	   lstrNomeArq = "TABPEI.TXT";

           arq = new File(Environment.getExternalStorageDirectory().toString().concat(pastaDest), lstrNomeArq);
           if (arq.exists())
        	   arq.delete();
           FileOutputStream fos;

           List<ItenPedidoDTO> listIten = itpBRL.getAllAbertos();
       	   DecimalFormat formatador = new DecimalFormat("##,##00.00");
       	   retorno = listIten.size();
       	   if (retorno > 0) {
			   fos = new FileOutputStream(arq);

			   for (ItenPedidoDTO itemPedido : listIten) {
				   lstrlinha = Util.FormataZeros(itemPedido.getCodPedido().toString(), 4);
				   lstrlinha = lstrlinha.concat(Util.FormataZeros(itemPedido.getCodProduto().toString(), 10));
				   lstrlinha = lstrlinha.concat(Util.FormataZeros(formatador.format(itemPedido.getQuantidade()).replace('.', ','), 6));
				   lstrlinha = lstrlinha.concat(Util.FormataZeros(formatador.format(itemPedido.getPreco()).replace('.', ','), 8));
				   lstrlinha = lstrlinha.concat(Util.FormataZeros(itemPedido.getUnidade().toString(), 6));
				   lstrlinha = lstrlinha.concat(Util.FormataZeros(Global.codEmpresa, 4));
				   lstrlinha = lstrlinha.concat("\r\n");
				   //transforma o texto digitado em array de bytes
				   dados = lstrlinha.getBytes();
				   //escreve os dados
				   fos.write(dados);
			   }
			   // fecha o arquivo
			   fos.flush();
			   fos.close();
		   }
		} catch (Exception e) {
            venda.util.mensagem.trace(getApplicationContext(), "Erro : " + e.getMessage());
        } finally{
        	itpBRL.CloseConetion();
        	return retorno;
        }
	}

	private void ImportaConfiguracao() {
		String lstrNomeArq;
        File arq; 
        String lstrlinha;
        ConfiguracaoBRL brl = new ConfiguracaoBRL(getApplicationContext());
        brl.DeleteByEmpresa();
        try
        {

            lstrNomeArq = "TABCFG.txt";
 
            arq = new File(Environment.getExternalStorageDirectory().toString().concat(pastaDest), lstrNomeArq);
            BufferedReader br = new BufferedReader(new FileReader(arq));
            
            while ((lstrlinha = br.readLine()) != null) {
            	if (lstrlinha.length() > 0) {
					ConfiguracaoDTO dto = brl.InstanciaConfiguracao(lstrlinha);
					if (!brl.InsereConfiguracao(dto)) {
						new Exception("Falhou importacao de Configuracao");
					}
				}
            }            
        	arq.delete();
             
        } catch (Exception e) {
            venda.util.mensagem.trace(getApplicationContext(), "Erro : " + e.getMessage());
        } finally{
        	brl.CloseConection();
        }
	}

	private void ImportaEmpresa() {
		String lstrNomeArq;
		File arq;
		String lstrlinha;
		EmpresaBRL brl = new EmpresaBRL(getApplicationContext());
		try
		{

			lstrNomeArq = "TABEMP.txt";

			arq = new File(Environment.getExternalStorageDirectory().toString().concat(pastaDest), lstrNomeArq);
			BufferedReader br = new BufferedReader(new FileReader(arq));

			while ((lstrlinha = br.readLine()) != null) {
				EmpresaDTO dto = brl.InstanciaEmpresa(lstrlinha);
				Global.codEmpresa = dto.getCodEmpresa();
				brl.DeleteByEmpresa(dto.getCodEmpresa());
				if (!brl.InsereEmpresa(dto)){
					new Exception("Falhou importacao de Empresa");
				}
				break;
			}
			arq.delete();

		} catch (Exception e) {
			venda.util.mensagem.trace(getApplicationContext(), "Erro : " + e.getMessage());
		} finally{
			brl.CloseConection();
		}
	}

	private void ImportaJustificativaPositivacao() {
		String lstrNomeArq;
        File arq; 
        String lstrlinha;
        JustificativaPositivacaoBRL brl = new JustificativaPositivacaoBRL(getApplicationContext());
        brl.DeleteByEmpresa();
        try
        {

            lstrNomeArq = "TABMOT.txt";
 
            arq = new File(Environment.getExternalStorageDirectory().toString().concat(pastaDest), lstrNomeArq);
            BufferedReader br = new BufferedReader(new FileReader(arq));

            while ((lstrlinha = br.readLine()) != null) {
				if (lstrlinha.length() > 0) {
					JustificativaPositivacaoDTO dto = brl.InstanciaJustPositivacao(lstrlinha);
					if (!brl.InsereJustPositivacao(dto)) {
						new Exception("Falhou importacao de Justificativas");
					}
				}
            }            
        	arq.delete();
             
        } catch (Exception e) {
            venda.util.mensagem.trace(getApplicationContext(), "Erro : " + e.getMessage());
        } finally{
        	brl.CloseConection();
        }
    }

	private void ImportaContaReceber() {
		String lstrNomeArq;
        File arq; 
        String lstrlinha;
        ContaReceberBRL brl = new ContaReceberBRL(getApplicationContext());
        brl.DeleteByEmpresa();
        try
        {

            lstrNomeArq = "TABREC.txt";
 
            arq = new File(Environment.getExternalStorageDirectory().toString().concat(pastaDest), lstrNomeArq);
            BufferedReader br = new BufferedReader(new FileReader(arq));

            while ((lstrlinha = br.readLine()) != null) {
				if (lstrlinha.length() > 0) {
					ContaReceberDTO dto = brl.InstanciaContaReceber(lstrlinha);
					if(dto != null) {
						if (!brl.InsereContaReceber(dto))
							new Exception("Falhou importacao de Contas a Receber");
					}
				}
            }            
        	arq.delete();
             
        } catch (Exception e) {
            venda.util.mensagem.trace(getApplicationContext(), "Erro : " + e.getMessage());
        } finally{
        	brl.CloseConection();
        }
    }

	private void ImportaPreco() {
		String lstrNomeArq;
        File arq; 
        String lstrlinha;
        PrecoBRL brl = new PrecoBRL(getApplicationContext());
        brl.DeleteByEmpresa();
        try
        {

            lstrNomeArq = "TABPRE.txt";
 
            arq = new File(Environment.getExternalStorageDirectory().toString().concat(pastaDest), lstrNomeArq);
            BufferedReader br = new BufferedReader(new FileReader(arq));

            while ((lstrlinha = br.readLine()) != null) {
				if (lstrlinha.length() > 0) {
					PrecoDTO dto = brl.InstanciaPreco(lstrlinha);
					if (!brl.InserePreco(dto)) {
						new Exception("Falhou importacao de Precos");
					}
				}
            }            
        	arq.delete();
             
        } catch (Exception e) {
            venda.util.mensagem.trace(getApplicationContext(), "Erro : " + e.getMessage());
        } finally{
        	brl.CloseConection();
        }
    }

	private void ImportaProduto() {
		String lstrNomeArq;
        File arq; 
        proBRL = new ProdutoBRL(getApplicationContext());
        proBRL.DeleteByEmpresa();
        try
        {

            lstrNomeArq = "TABPRO.txt";
 
            arq = new File(Environment.getExternalStorageDirectory().toString().concat(pastaDest), lstrNomeArq);
            br = new BufferedReader(new FileReader(arq));
            
			while ((lstrlinha = br.readLine()) != null) {
				if (lstrlinha.length() > 0) {
					Log.d("Produto", lstrlinha);
					ProdutoDTO dto = proBRL.InstanciaProduto(lstrlinha);
					if (!proBRL.InsereProduto(dto)) {
						new Exception("Falhou importacao de Produtos");
					}
				}
			}
        	arq.delete();
   
        } catch (Exception e) {
            venda.util.mensagem.trace(getApplicationContext(), "Erro : " + e.getMessage());
        } finally{
        	proBRL.CloseConection();
        }
    }

	private void ImportaCliente() {
		String lstrNomeArq;
        File arq; 
        
        cliBRL = new ClienteBRL(getApplicationContext());
        cliBRL.DeleteByEmpresa();
        try
        {

            lstrNomeArq = "TABCLI.txt";
 
            arq = new File(Environment.getExternalStorageDirectory().toString().concat(pastaDest), lstrNomeArq);
            br = new BufferedReader(new FileReader(arq));
            
			while ((lstrlinha = br.readLine()) != null) {
				if (lstrlinha.length() > 0) {
					ClienteDTO dto = cliBRL.InstanciaCliente(lstrlinha);
					if (!cliBRL.InsereCliente(dto)) {
						new Exception("Falhou importa��o de Clientes");
					}
				}
			}
        	arq.delete();

					
        } catch (Exception e) {
            venda.util.mensagem.trace(getApplicationContext(), "Erro : " + e.getMessage());
        } finally{
        	cliBRL.CloseConection();
        }
    }

	private void ImportaGrupo() {
		String lstrNomeArq;
        File arq; 
        String lstrlinha;
        GrupoBRL brl = new GrupoBRL(getApplicationContext());
        brl.DeleteByEmpresa();
        try
        {

            lstrNomeArq = "TABLIN.txt";
 
            arq = new File(Environment.getExternalStorageDirectory().toString().concat(pastaDest), lstrNomeArq);
            BufferedReader br = new BufferedReader(new FileReader(arq));

            while ((lstrlinha = br.readLine()) != null) {
				if (lstrlinha.length() > 0) {
					GrupoDTO dto = brl.InstanciaGrupo(lstrlinha);
					if (!brl.InsereGrupo(dto)) {
						new Exception("Falhou importacao de Grupos");
					}
				}
            }            
        	arq.delete();
             
        } catch (Exception e) {
            venda.util.mensagem.trace(getApplicationContext(), "Erro : " + e.getMessage());
        } finally{
        	brl.CloseConection();
        }
    }

	private void ImportaVendedor() {
		String lstrNomeArq;
        File arq; 
        String lstrlinha;
        VendedorBRL brl = new VendedorBRL(getApplicationContext());
        brl.DeleteByEmpresa();
        try
        {

            lstrNomeArq = "TABVEN.txt";
 
            arq = new File(Environment.getExternalStorageDirectory().toString().concat(pastaDest), lstrNomeArq);
            BufferedReader br = new BufferedReader(new FileReader(arq));

            while ((lstrlinha = br.readLine()) != null) {
				if (lstrlinha.length() > 0) {
					VendedorDTO dto = brl.InstanciaVendedor(lstrlinha);
					if (!brl.InsereVendedor(dto)) {
						new Exception("Falhou importa��o de Vendedores");
					}
				}
            }            
        	arq.delete();
             
        } catch (Exception e) {
            venda.util.mensagem.trace(getApplicationContext(), "Erro : " + e.getMessage());
        } finally{
        	brl.CloseConection();
        }
    }

	private void ImportaFormaPgto() {
		String lstrNomeArq;
        File arq; 
        String lstrlinha;
        FormaPgtoBRL brl = new FormaPgtoBRL(getApplicationContext());
        brl.DeleteByEmpresa();
        try
        {

            lstrNomeArq = "TABPGT.txt";
 
            arq = new File(Environment.getExternalStorageDirectory().toString().concat(pastaDest), lstrNomeArq);
            BufferedReader br = new BufferedReader(new FileReader(arq));

            while ((lstrlinha = br.readLine()) != null) {
				if (lstrlinha.length() > 0) {
					FormaPgtoDTO dto = brl.InstanciaFormaPgto(lstrlinha);
					if (!brl.InsereFormaPgto(dto)) {
						new Exception("Falhou importacao de Formas de Pagamento");
					}
				}
            }            
        	arq.delete();
             
        } catch (Exception e) {
            venda.util.mensagem.trace(getApplicationContext(), "Erro : " + e.getMessage());
        } finally{
        	brl.CloseConection();
        }
    }

	private void ImportaFornecedor() {
		String lstrNomeArq;
        File arq; 
        String lstrlinha;
        FornecedorBRL brl = new FornecedorBRL(getApplicationContext());
        brl.DeleteByEmpresa();
        try
        {

            lstrNomeArq = "TABFOR.txt";
 
            arq = new File(Environment.getExternalStorageDirectory().toString().concat(pastaDest), lstrNomeArq);
            BufferedReader br = new BufferedReader(new FileReader(arq));

            while ((lstrlinha = br.readLine()) != null) {
				if (lstrlinha.length() > 0) {
					FornecedorDTO dto = brl.InstanciaFornecedor(lstrlinha);

					if (!brl.InsereFornecedor(dto)) {
						new Exception("Falhou importacao de Fornecedores");
					}
				}
            }            
        	arq.delete();
             
        } catch (Exception e) {
            venda.util.mensagem.trace(getApplicationContext(), "Erro : " + e.getMessage());
        } finally{
        	brl.CloseConection();
        }
    }
}
