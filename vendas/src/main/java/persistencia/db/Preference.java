package persistencia.db;

import android.content.Context;
import android.content.SharedPreferences;

public class Preference {
	
	public void Preference(Context ctx){
		SharedPreferences preferencia = ctx.getSharedPreferences("ConfiguracaoFTP", 0);

	}

}
