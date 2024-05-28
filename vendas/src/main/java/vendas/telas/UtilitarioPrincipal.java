package vendas.telas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UtilitarioPrincipal extends Activity {

    private Button btnPrincipal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voltar_principal);

        btnPrincipal = (Button) findViewById(R.id.btnUtilitarioPrincipal);

        btnPrincipal.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                AbrePrincipal();
            }
        });
    }

    private void AbrePrincipal() {
        Intent principal = new Intent(this, Principal.class);
        startActivity(principal);
    }
}
