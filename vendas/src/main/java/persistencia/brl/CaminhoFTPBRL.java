package persistencia.brl;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipException;

import persistencia.dao.CaminhoFTPDAO;
import persistencia.dto.CaminhoFTPDTO;
import venda.util.FTP;
import venda.util.Global;
import venda.util.Util;

public class CaminhoFTPBRL {
    CaminhoFTPDAO caminhoFTPDAO;
    CaminhoFTPDTO ftpDTO;
    Activity act;
    Context ctx;

    public CaminhoFTPBRL(){}
    
    public CaminhoFTPBRL(Context ctx)
    {
    	this.ctx = ctx;
        if (caminhoFTPDAO == null)
            caminhoFTPDAO = new CaminhoFTPDAO(ctx);
    }

	public CaminhoFTPBRL(Context ctx, Activity act)
	{
    	this.act = act;
		this.ctx = ctx;
		if (caminhoFTPDAO == null)
			caminhoFTPDAO = new CaminhoFTPDAO(act);
	}

	public CaminhoFTPDTO getByEmpresa(String codEmpresa){
		return caminhoFTPDAO.getByEmpresa(codEmpresa);
	}

	public CaminhoFTPDTO getByEmpresa()
	{
		CaminhoFTPDTO ftpDTO = caminhoFTPDAO.getByEmpresa();
		int totreg = getTotalRegistros();
		if (ftpDTO.getId() == null) {
			ftpDTO = caminhoFTPDAO.getDefault();
		}
		return ftpDTO;
	}

	public void SalvaCaminhoFTP(CaminhoFTPDTO ftpDTO)
	{
		int totreg = caminhoFTPDAO.getTotalRegistros(ftpDTO.getCodEmpresa());
		if (totreg == 0) {
			ftpDTO.setId(null);
			Global.tituloAplicacao = "PalmVenda";
			InsereCaminhoFTP(ftpDTO);
		}
		else
			Update(ftpDTO);
		Global.codEmpresa = ftpDTO.getCodEmpresa();
		Global.caminhoFTPDTO = caminhoFTPDAO.getByEmpresa(ftpDTO.getCodEmpresa());
	}

	private boolean InsereCaminhoFTP(CaminhoFTPDTO ftpDTO)
	{
		try
		{
			return caminhoFTPDAO.insert(ftpDTO);
		}
		catch (Exception e)
		{
			venda.util.mensagem.trace(ctx, e.toString());
			return false;
		}
	}

	private boolean Update(CaminhoFTPDTO ftpDTO)
	{
		try
		{
			return caminhoFTPDAO.update(ftpDTO);
		}
		catch (Exception e)
		{
			venda.util.mensagem.trace(ctx, e.toString());
			return false;
		}
	}


	public boolean DeleteAll()
	{
		try
		{
			return caminhoFTPDAO.deleteAll();
		}
		catch (Exception e)
		{
			venda.util.mensagem.trace(ctx, e.toString());
			return false;
		}
	}

	public boolean DeleteByEmpresa()
	{
		try
		{
			return caminhoFTPDAO.deleteByEmpresa();
		}
		catch (Exception e)
		{
			venda.util.mensagem.trace(ctx, e.toString());
			return false;
		}
	}

	public int getTotalRegistros()
	{
		return  caminhoFTPDAO.getTotalRegistros();
	}

	public Boolean ValidaCaminhoFTP(int tipo,CaminhoFTPDTO ftpDTO){
    	
    	Boolean retorno = true;
    	String mensagem = null;
    	
    	if (ftpDTO.getServerLocal().trim().length() <= 0)
    		mensagem = "Informe o servidor Local";
    	else if (ftpDTO.getUserLocal().trim().length() <= 0)
    		mensagem = "Informe o usuário Local";
    	else if (ftpDTO.getPasswordLocal().trim().length() <= 0)
    		mensagem = "Informe a senha Local";
    	else if (ftpDTO.getServerRemoto().trim().length() <= 0)
    		mensagem = "Informe o servidor remoto";
    	else if (ftpDTO.getUserRemoto().trim().length() <= 0)
    		mensagem = "Informe o usuário remoto";
    	else if (ftpDTO.getPasswordRemoto().trim().length() <= 0)
    		mensagem = "Informe a senha remota";
    	else if (ftpDTO.getCaminho().trim().length() <= 0)
    		mensagem = "Informe o caminho";
    	else if (ftpDTO.getEmailEmpresa().trim().length() <= 0)
    		mensagem = "Informe o email da empresa";
    	
    	if (tipo == 1 && mensagem != null){
    		Toast.makeText(ctx, mensagem, Toast.LENGTH_SHORT).show();
    		retorno = false;
    	}else if (tipo == 2 && mensagem != null){
    		retorno = false;
    	} 
    	return retorno;
    }
    
    public FTPClient ConectaFTP() throws NumberFormatException, Exception{
    	ftpDTO = caminhoFTPDAO.getByEmpresa();
    	if (ValidaCaminhoFTP(1, ftpDTO)){
    		if (Global.caminhoFTPDTO.getComDefault().equals("L")) //2131099676
    			return FTP.ConectaServidorFTP(ftpDTO.getServerLocal(), ftpDTO.getUserLocal(), 
    					ftpDTO.getPasswordLocal(), ftpDTO.getCaminho() + Global.codVendedor, Integer.parseInt(ftpDTO.getPortaFTP()));
    		else if (Global.caminhoFTPDTO.getComDefault().equals("R")){ //2131099677
    			if (!FTP.verificaConexao(ctx))
    				throw new Exception("Verifique sua conexão com internet");
    			return FTP.ConectaServidorFTP(ftpDTO.getServerRemoto(), ftpDTO.getUserRemoto(), 
    					ftpDTO.getPasswordRemoto(), "/FTPCliente/" + ftpDTO.getCodEmpresa().substring(1) + "/" + Global.codVendedor, Integer.parseInt(ftpDTO.getPortaFTP()));
    		} else
    			return null;
    	}
		else
			return null;
    }

    public Boolean RecebeArquivoFTP(String arquivo, FTPClient ftp, String pastaDest){
    	ftpDTO = caminhoFTPDAO.getByEmpresa();
		if (Global.caminhoFTPDTO.getComDefault().equals("L"))
	    	return FTP.RecebeArquivoFTP(arquivo, ftp, pastaDest);
		else if (Global.caminhoFTPDTO.getComDefault().equals("R"))
	    	return FTP.RecebeArquivoFTP(arquivo, ftp, pastaDest);
		else
			return true;
    }

    public Boolean EnviaArquivoFTP(String arquivo, FTPClient ftp, String pastaDest){
    	ftpDTO = caminhoFTPDAO.getByEmpresa();
		if (Global.caminhoFTPDTO.getComDefault().equals("L"))
	    	return FTP.EnviaArquivoFTP(arquivo, ftp, pastaDest);
		else if (Global.caminhoFTPDTO.getComDefault().equals("R"))
	    	return FTP.EnviaArquivoFTP(arquivo, ftp, pastaDest);
		else
			return true;
    }

    public void DesconectaFTP(FTPClient ftp){
    	if (Global.caminhoFTPDTO.getComDefault().equals("L") || Global.caminhoFTPDTO.getComDefault().equals("R"))
    		FTP.DesconectaFTP(ftp);
    }

    public String getEmailDaEmpresa(){
    	ftpDTO = caminhoFTPDAO.getByEmpresa();
    	return ftpDTO.getEmailEmpresa();
    }
    
    public Boolean CheckFileExists(String[] arquivos, FTPClient ftp) throws IOException{
		for (String arquivoExp : arquivos) {
			if (FTP.checkFileExists(ftp, arquivoExp))
				return true;
		}
		return false;
    }

	public Boolean CheckFileExist(String arquivo, FTPClient ftp) throws IOException{
		if (FTP.checkFileExists(ftp, arquivo))
			return true;
		else
			return false;
	}

    public boolean CriaArquivoZip(String nomeArqZip, String nomeArqTxt, String pastaDest) throws ZipException, IOException{
    	Util zip = new Util();
    	File fileZip = new File(Environment.getExternalStorageDirectory().toString().concat(pastaDest).concat("/").concat(nomeArqZip));
    	File fileTxt = new File(Environment.getExternalStorageDirectory().toString().concat(pastaDest).concat("/").concat(nomeArqTxt));
    	zip.criarZip(fileZip, fileTxt);
		return true;
    }
}

