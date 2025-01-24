package venda.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.Date;
import java.net.SocketException;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.net.ftp.*;

import java.net.HttpURLConnection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Environment;

import persistencia.brl.CaminhoFTPBRL;

public class FTP {
	
	public static FTPClient ConectaServidorFTP(String Servidor, String login, String senha, String diretorio, int porta) throws Exception {
		final FTPClient ftp = new FTPClient();
            ftp.connect(Servidor, porta);
            if (!ftp.login(login, senha))
            	throw new Exception("Usuário ou senha inválidos");
            if (!ftp.changeWorkingDirectory(diretorio)){
            	ftp.logout();
            	ftp.disconnect();
            	throw new Exception("Pasta do vendedor não encontrada no servidor");
            }
        	return ftp;
    }
	
	/* Funçao para verificar existência de conexão com a internet
	 */
	public static  boolean verificaConexao(Context ctx) {
	    boolean conectado;
		ConnectivityManager conectivtyManager = (ConnectivityManager) ctx.getSystemService(ctx.CONNECTIVITY_SERVICE);
	    if (conectivtyManager.getActiveNetworkInfo() != null
	            && conectivtyManager.getActiveNetworkInfo().isAvailable()
	            && conectivtyManager.getActiveNetworkInfo().isConnected()) {
	    	conectado = true;
	    } else {
	        conectado = false;
	    }
	    return conectado;
	}
	
	public static Boolean EnviaArquivoFTP(String arquivo, FTPClient ftp, String pastaDest){
		try {
			File file = new File(Environment.getExternalStorageDirectory().toString().concat(pastaDest).concat("/").concat(arquivo));
			File fileDir = new File(Environment.getExternalStorageDirectory().toString().concat(pastaDest));
			if (!fileDir.exists())
				fileDir.mkdir();
            FileInputStream arqEnviar = new FileInputStream(file);
            
			ftp.setBufferSize(49152); 
            ftp.enterLocalPassiveMode();
            ftp.setFileTransferMode(FTPClient.BINARY_FILE_TYPE);
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
//            
            Boolean status = ftp.storeFile(arquivo, arqEnviar);
            arqEnviar.close();
			return status;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static Boolean RecebeArquivoFTP(String arquivo, FTPClient ftp, String pastaDest){
		try {
			boolean status;
			File file = new File(Environment.getExternalStorageDirectory().toString().concat(pastaDest).concat("/").concat(arquivo));
			File fileDir = new File(Environment.getExternalStorageDirectory().toString().concat(pastaDest));
			if (!fileDir.exists())
				fileDir.mkdir();
			if (file.exists())
				file.delete();
			ftp.setBufferSize(49152);  
            ftp.enterLocalPassiveMode();  
            ftp.setFileTransferMode(FTPClient.BINARY_FILE_TYPE);  
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			FileOutputStream arqEnviar = new FileOutputStream(file, true);
            status = ftp.retrieveFile(arquivo, arqEnviar);
			if (!status && file.exists())
				file.delete();
            arqEnviar.close();
			return status;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
     * Determines whether a file exists or not
     * @param filePath
     * @return true if exists, false otherwise
     * @throws IOException thrown if any I/O error occurred.
     */
    public static boolean checkFileExists(FTPClient ftp, String arquivo) throws IOException {
    	int returnCode;
        InputStream inputStream = ftp.retrieveFileStream(arquivo);
        returnCode = ftp.getReplyCode();
        if (inputStream == null || returnCode == 550) {
            return false;
        }
        return true;
    }

	public static void DesconectaFTP(FTPClient ftp){
		try {
            ftp.logout();
            ftp.disconnect();			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
    /** 
     * Copia arquivos de um local para o outro 
     * @param origem - Arquivo de origem 
     * @param destino - Arquivo de destino 
     * @param overwrite - Confirmacao para sobrescrever os arquivos
     * @throws IOException 
     */ 
    public static void copy(File origem, File destino, boolean overwrite) throws IOException{ 
        Date date = new Date();
       if (destino.exists() && !overwrite){ 
          System.err.println(destino.getName()+" já existe, ignorando...");
          return; 
       } 
       FileInputStream fisOrigem = new FileInputStream(origem); 
       FileOutputStream fisDestino = new FileOutputStream(destino); 
       FileChannel fcOrigem = fisOrigem.getChannel();   
       FileChannel fcDestino = fisDestino.getChannel();   
       fcOrigem.transferTo(0, fcOrigem.size(), fcDestino);   
       fisOrigem.close();   
       fisDestino.close(); 
       Long time = new Date().getTime() - date.getTime();
       System.out.println("Saiu copy"+time);
    }

}
