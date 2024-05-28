package vendas.telas;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import venda.util.Global;

public class UtilitarioTabContainer extends TabActivity {
    @Override
    public void onCreate(Bundle e){
        super.onCreate(e);
        setContentView(R.layout.tabcontainer);
        this.setTitle(Global.tituloAplicacao);

        TabHost host = getTabHost();
        TabHost.TabSpec sec;
        //... Pedidos Enviados
        sec = host.
                newTabSpec("Enviados").
                setIndicator("Enviados").
                setContent(new Intent(this,UtilitarioPedido.class))
        ;
        host.addTab(sec);
        //... Pedidos Pendentees de Envio
        sec = host.
                newTabSpec("Pendentes").
                setIndicator("Pendentes").
                setContent(new Intent(this, UtilitarioPedidoPendente.class))
        ;
        host.addTab(sec);
        //... Voltar
        sec = host.
                newTabSpec("Principal").
                setIndicator("Principal").
                setContent(new Intent(this, UtilitarioPrincipal.class))
        ;
        host.addTab(sec);
        //======
        host.setCurrentTabByTag("Enviados");
    }
}

