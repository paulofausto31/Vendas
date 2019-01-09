package venda.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class Util {
	
	public List<ZipEntry> listarEntradasZip( File arquivo ) throws ZipException, IOException {  
		    List<ZipEntry> entradasDoZip = new ArrayList<ZipEntry>();  
		    ZipFile zip = null;  
		    try {  
		      zip = new ZipFile( arquivo );  
		      Enumeration<?> e = zip.entries();  
		      ZipEntry entrada = null;  
		      while( e.hasMoreElements() ) {  
		        entrada = (ZipEntry) e.nextElement();  
		        entradasDoZip.add ( entrada );  
		      }  
		      setArquivoZipAtual( arquivo );  
		    }  
		    finally {  
		      if( zip != null ) {  
		        zip.close();  
		      }  
		    }  
		    return entradasDoZip;  
		  }  
		  
	public void extrairZip( File diretorio ) throws ZipException, IOException {  
		    extrairZip( this.getArquivoZipAtual(), diretorio );  
		  }  
		  
	public void extrairZip( File arquivoZip, File diretorio ) throws ZipException, IOException {  
		    ZipFile zip = null;  
		    File arquivo = null;  
		    InputStream is = null;  
		    OutputStream os = null;  
		    byte[] buffer = new byte[TAMANHO_BUFFER];  
		    try {  
		      //cria diretorio informado, caso n�o exista
		      if( !diretorio.exists() ) {  
		        diretorio.mkdirs();  
		      }  
		      if( !diretorio.exists() || !diretorio.isDirectory() ) {  
		        throw new IOException("Informe um diretório válido");
		      }  
		      zip = new ZipFile( arquivoZip );  
		      Enumeration<?> e = zip.entries();  
		      while( e.hasMoreElements() ) {  
		        ZipEntry entrada = (ZipEntry) e.nextElement();  
		        arquivo = new File( diretorio, entrada.getName() );  
		        //se for diretorio inexistente, cria a estrutura
		        //e pula pra proxima entrada
		        if( entrada.isDirectory() && !arquivo.exists() ) {  
		          arquivo.mkdirs();  
		          continue;  
		        }  
		        //se a estrutura de diretorios nao existe, cria
		        if( !arquivo.getParentFile().exists() ) {  
		          arquivo.getParentFile().mkdirs();  
		        }  
		        try {  
		          //l� o arquivo do zip e grava em disco  
		          is = zip.getInputStream( entrada );  
		          os = new FileOutputStream( arquivo );  
		          int bytesLidos = 0;  
		          if( is == null ) {  
		            throw new ZipException("Erro ao ler a entrada do zip: "+entrada.getName());  
		          }  
		          while( (bytesLidos = is.read( buffer )) > 0 ) {  
		            os.write( buffer, 0, bytesLidos );  
		          }  
		        } finally {  
		          if( is != null ) {  
		            try {  
		              is.close();  
		            } catch( Exception ex ) {}  
		          }  
		          if( os != null ) {  
		            try {  
		              os.close();  
		            } catch( Exception ex ) {}  
		          }  
		        }  
		      }  
		    } finally {  
		      if( zip != null ) {  
		        try {  
		          zip.close();  
		        } catch( Exception e ) {}  
		      }  
		    }  
		  }  
		    
	public List<ZipEntry> criarZip( File arquivoZip, File arquivos ) throws ZipException, IOException {  
		    FileOutputStream fos = null;  
		    BufferedOutputStream bos = null;  
		    setArquivoZipAtual( null );  
		    try {  
		      //adiciona a extens�o .zip no arquivo, caso n�o exista  
		      if( !arquivoZip.getName().toLowerCase().endsWith(".zip") ) {  
		        arquivoZip = new File( arquivoZip.getAbsolutePath()+".zip" );  
		      }  
		      fos = new FileOutputStream( arquivoZip );  
		      bos = new BufferedOutputStream( fos, TAMANHO_BUFFER );  
		      List<ZipEntry> listaEntradasZip = criarZip( bos, arquivos );  
		      setArquivoZipAtual( arquivoZip );  
		      return listaEntradasZip;  
		    }  
		    finally {  
		      if( bos != null ) {  
		        try {  
		          bos.close();  
		        } catch( Exception e ) {}  
		      }  
		      if( fos != null ) {  
		        try {  
		          fos.close();  
		        } catch( Exception e ) {}  
		      }  
		    }  
		  }  
		    
	public List<ZipEntry> criarZip( OutputStream os, File arquivos ) throws ZipException, IOException {  
		    if( arquivos == null ) {  
		      throw new ZipException("Adicione ao menos um arquivo ou diretório");
		    }  
		    List<ZipEntry> listaEntradasZip = new ArrayList<ZipEntry>();  
		    ZipOutputStream zos = null;  
		    try {  
		      zos = new ZipOutputStream( os );  
		        String caminhoInicial = arquivos.getParent();  
		        List<ZipEntry> novasEntradas = adicionarArquivoNoZip( zos, arquivos, caminhoInicial );  
		        if( novasEntradas != null ) {  
		          listaEntradasZip.addAll( novasEntradas );  
		        }  
		    }  
		    finally {  
		      if( zos != null ) {  
		        try {  
		          zos.close();  
		        } catch( Exception e ) {}  
		      }  
		    }  
		    return listaEntradasZip;  
		  }  
		    
	private List<ZipEntry> adicionarArquivoNoZip( ZipOutputStream zos, File arquivo, String caminhoInicial ) throws IOException {  
		    List<ZipEntry> listaEntradasZip = new ArrayList<ZipEntry>();  
		    FileInputStream fis = null;  
		    BufferedInputStream bis = null;  
		    byte buffer[] = new byte[TAMANHO_BUFFER];  
		    try {  
		      //diretorios nao sao adicionados
		      if( arquivo.isDirectory() ) {  
		        //recursivamente adiciona os arquivos dos diretorios abaixo
		        File[] arquivos = arquivo.listFiles();  
		        for( int i=0; i<arquivos.length; i++ ) {  
		          List<ZipEntry> novasEntradas = adicionarArquivoNoZip( zos, arquivos[i], caminhoInicial );  
		          if( novasEntradas != null ) {  
		            listaEntradasZip.addAll( novasEntradas );  
		          }  
		        }  
		        return listaEntradasZip;  
		      }  
		      String caminhoEntradaZip = null;  
		      int idx = arquivo.getAbsolutePath().indexOf(caminhoInicial);  
		      if( idx >= 0 ) {  
		        //calcula os diretorios a partir do diretorio inicial
		        //isso serve para nao colocar uma entrada com o caminho completo
		        caminhoEntradaZip = arquivo.getAbsolutePath().substring( idx+caminhoInicial.length()+1 );  
		      }  
		      ZipEntry entrada = new ZipEntry( caminhoEntradaZip );  
		      zos.putNextEntry( entrada );  
		      zos.setMethod( ZipOutputStream.DEFLATED );  
		      fis = new FileInputStream( arquivo );  
		      bis = new BufferedInputStream( fis, TAMANHO_BUFFER );  
		      int bytesLidos = 0;  
		      while((bytesLidos = bis.read(buffer, 0, TAMANHO_BUFFER)) != -1) {  
		        zos.write( buffer, 0, bytesLidos );  
		      }  
		      listaEntradasZip.add( entrada );  
		    }  
		    finally {  
		      if( bis != null ) {  
		        try {  
		          bis.close();  
		        } catch( Exception e ) {}  
		      }  
		      if( fis != null ) {  
		        try {  
		          fis.close();  
		        } catch( Exception e ) {}  
		      }  
		    }  
		    return listaEntradasZip;  
		  }  
		    
	public void fecharZip() {  
		    setArquivoZipAtual( null );  
		  }  
		    
	public File getArquivoZipAtual() {  
		    return arquivoZipAtual;  
		  }  
		    
	private void setArquivoZipAtual(File arquivoZipAtual) {  
		    this.arquivoZipAtual = arquivoZipAtual;  
		  }  
		    
	private File arquivoZipAtual;  
	private static final int TAMANHO_BUFFER = 2048; // 2 Kb  

	
	public static String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

	public static Date StringToDate(String data) throws ParseException{
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
		Date date = (Date)formatter.parse(data);
		
		return date;
	}
	
	public static int dataDiff(java.util.Date dataLow, java.util.Date dataHigh){  
		  
	     GregorianCalendar startTime = new GregorianCalendar();  
	     GregorianCalendar endTime = new GregorianCalendar();  
	       
	     GregorianCalendar curTime = new GregorianCalendar();  
	     GregorianCalendar baseTime = new GregorianCalendar();  
	  
	     startTime.setTime(dataLow);  
	     endTime.setTime(dataHigh);  
	       
	     int dif_multiplier = 1;  
	       
	     // Verifica a ordem de inicio das datas  
	     if( dataLow.compareTo( dataHigh ) < 0 ){  
	         baseTime.setTime(dataHigh);  
	         curTime.setTime(dataLow);  
	         dif_multiplier = 1;  
	     }else{  
	         baseTime.setTime(dataLow);  
	         curTime.setTime(dataHigh);  
	         dif_multiplier = -1;  
	     }  
	       
	     int result_years = 0;  
	     int result_months = 0;  
	     int result_days = 0;  
	  
	     // Para cada mes e ano, vai de mes em mes pegar o ultimo dia para import acumulando  
	     // no total de dias. Ja leva em consideracao ano bissesto  
	     while( curTime.get(GregorianCalendar.YEAR) < baseTime.get(GregorianCalendar.YEAR) ||  
	            curTime.get(GregorianCalendar.MONTH) < baseTime.get(GregorianCalendar.MONTH)  )  
	     {  
	           
	         int max_day = curTime.getActualMaximum( GregorianCalendar.DAY_OF_MONTH );  
	         result_months += max_day;  
	         curTime.add(GregorianCalendar.MONTH, 1);  
	           
	     }  
	       
	     // Marca que e um saldo negativo ou positivo
	     result_months = result_months*dif_multiplier;  
	       
	       
	     // Retirna a diferenca de dias do total dos meses  
	     result_days += (endTime.get(GregorianCalendar.DAY_OF_MONTH) - startTime.get(GregorianCalendar.DAY_OF_MONTH));  
	       
	     return result_years+result_months+result_days;  
	}  
	
	public static String getTime() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
	
	public static String FormataZeros(String value, int tamanho){
		String retorno = value;
		for (int i = tamanho; i > value.length(); i--) {
			retorno = "0".concat(retorno);
		}
		return retorno;
	}

	public static String FormataSpaces(String value, int tamanho){
		String retorno = value;
		for (int i = tamanho; i > value.length(); i--) {
			retorno = retorno.concat(" ");
		}
		return retorno;
	}

	public static String FormataSpaces(String value, int esquerda, int direita){
		String retorno = "";
		for (int i = esquerda; i > value.length(); i--) {
			retorno = retorno.concat(" ");
		}
		retorno.concat(value);
		for (int i = direita; i > value.length(); i--) {
			retorno = retorno.concat(" ");
		}
		return retorno;
	}

	public static boolean isNumeric(String s) {
	    return s.matches("[-+]?\\d*\\.?\\d+");  
	} 

	public static String montaChave(String data) throws ParseException {
        int xEncData01, xEncData02;
        int xEncData03;
        int xEncData05, xEncData06;
        int xEncData07, xEncData08;
        double xRet;
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
        Date date = (Date)formatter.parse(data);

        xEncData01 = Math.max(1,Integer.parseInt(data.substring(0, 1)));
        xEncData02 = Math.max(1,Integer.parseInt(data.substring(1, 2)));
        xEncData03 = Math.max(1,Integer.parseInt(data.substring(3, 5)));
        xEncData05 = Math.max(1,Integer.parseInt(data.substring(6, 7)));
        xEncData06 = Math.max(1,Integer.parseInt(data.substring(7, 8)));
        xEncData07 = Math.max(1,Integer.parseInt(data.substring(8, 9)));
        xEncData08 = Math.max(1,Integer.parseInt(data.substring(9, 10)));

        xRet = (math.pot(xEncData01,1))*(math.pot(xEncData02,1))*(math.pot(xEncData03,3))*(math.pot(xEncData05,1))*(math.pot(xEncData06,1))*(math.pot(xEncData07,1))*(math.pot(xEncData08,1))*(math.pot(dataHora.getDiaSemana(date),3));
        xRet = xRet+xEncData01+xEncData02+xEncData03+xEncData05+xEncData06+xEncData07+xEncData08;

        xRet = Integer.parseInt(texto.right(texto.strZero(String.valueOf((int)xRet),20), 6));
        if (xRet < 1000) {
           xRet = Integer.parseInt(texto.right(texto.strZero(String.valueOf((int)math.pot(xRet,2)),20),6));
        }
        return texto.strZero(String.valueOf((int)xRet),6);
	}
}
