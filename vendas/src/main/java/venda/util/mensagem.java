package venda.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

public class mensagem {

    static boolean retorno;

	public static void toast (Context ctx, String msg) {
		Toast.makeText (ctx, msg, Toast.LENGTH_SHORT).show ();
	} 
	     
    public static void trace (Context ctx, String msg) 
    {
        toast (ctx, msg);
    }	
    
    public static void messageBox(Context ctx, String titulo, String mensagem, String botaoPositivo){
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		builder.setTitle(titulo);
		builder.setMessage(mensagem);
		builder.setPositiveButton(botaoPositivo, null);
		AlertDialog dialog = builder.show();
    }
        
}
