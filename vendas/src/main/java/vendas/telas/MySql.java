package vendas.telas;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import vendas.telas.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class MySql extends Activity {

	String str = "new";
	static ResultSet rs;
	static PreparedStatement st;
    static Connection con;
	
    @Override
	protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //final   TextView tv=(TextView)findViewById(R.id.user);

        try
        {
        	Class.forName("com.mysql.jdbc.Driver").newInstance();
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/sitdb", "root", "pfmn03");
            st=con.prepareStatement("select * from banbanc where bacodi=204");
            rs=st.executeQuery();
             while(rs.next())
             {
             str=rs.getString(2);


             }


             Toast.makeText(getBaseContext(), str, Toast.LENGTH_SHORT).show();        }
        catch(Exception e)
        {
        	Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
     }	

    
}
