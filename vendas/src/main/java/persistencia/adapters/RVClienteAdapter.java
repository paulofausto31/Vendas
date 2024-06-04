package persistencia.adapters;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import persistencia.brl.ClienteNaoPositivadoBRL;
import persistencia.brl.PedidoBRL;
import persistencia.dto.ClienteDTO;
import vendas.telas.ClienteComplemento;
import vendas.telas.ClienteContasReceber;
import vendas.telas.JustificativaTabContainer;
import vendas.telas.PedidoTabContainer;
import vendas.telas.R;

public class RVClienteAdapter extends RecyclerView.Adapter<RVClienteAdapter.ViewHolder> {

    private Context context;
    private static List<ClienteDTO> lista;
    private static OnItemClickListener listener;

    public RVClienteAdapter(Context ctx, List<ClienteDTO> lista, OnItemClickListener listener){
        this.context = ctx;
        this.lista = lista;
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @NonNull
    @Override
    public RVClienteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_clientes, parent, false);
        return new RVClienteAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RVClienteAdapter.ViewHolder holder, int position) {
        ClienteDTO dto = lista.get(position);
        PedidoBRL pedBRL = new PedidoBRL(context);
        ClienteNaoPositivadoBRL cnpBRL = new ClienteNaoPositivadoBRL(context);

        holder.txtRVNomeCliente.setText(dto.getNome());
        holder.txtRVCodigoCliente.setText(dto.getCodCliente().toString() + "  ");
        holder.txtRVEmpresa.setText(dto.getRazaoSocial().substring(0, 20));

        int retorno = 0;
        if (pedBRL.getPedidoAbertoCliente(dto.getCodCliente()) > 0)
            holder.txtRVPedido.setText("Pedido");
        else if(cnpBRL.getCNPClienteAberto(dto.getCodCliente()) > 0)
            holder.txtRVPedido.setText("Justificativa");
        else
            retorno = 4;

        if (retorno == 0)
            holder.txtRVPedido.setVisibility(View.VISIBLE);
        else
            holder.txtRVPedido.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnLongClickListener, PopupMenu.OnMenuItemClickListener {
        public TextView txtRVCodigoCliente;
        public TextView txtRVEmpresa;
        public TextView txtRVPedido;
        public TextView txtRVNomeCliente;

        public ViewHolder(View view) {
            super(view);
            txtRVCodigoCliente = view.findViewById(R.id.txtRVCodCliente);
            txtRVEmpresa = view.findViewById(R.id.lblRVEmpresa);
            txtRVPedido = view.findViewById(R.id.txtRVPedido);
            txtRVNomeCliente = view.findViewById(R.id.txtRVNomeCliente);
            view.setOnLongClickListener((View.OnLongClickListener) this);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
        @Override
        public boolean onLongClick(View v) {
            PopupMenu popup = new PopupMenu(v.getContext(), v);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.popup_clientes, popup.getMenu());
            popup.setOnMenuItemClickListener((PopupMenu.OnMenuItemClickListener) this);
            popup.show();
            return true;
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            int position = getAdapterPosition();
            ClienteDTO dto = lista.get(position);
            Intent intent;
            switch (item.getItemId()) {
                case R.id.action_pedido:
                    intent = new Intent(context, PedidoTabContainer.class);
                    intent.putExtra("idCliente", dto.getId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    return true;
                case R.id.action_justificativa:
                    intent = new Intent(context, JustificativaTabContainer.class);
                    intent.putExtra("idCliente", dto.getId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    return true;
                case R.id.action_creceber:
                    intent = new Intent(context, ClienteContasReceber.class);
                    intent.putExtra("codCliente", dto.getCodCliente());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    return true;
                case R.id.action_complemento:
                    intent = new Intent(context, ClienteComplemento.class);
                    intent.putExtra("idCliente", dto.getId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    return true;
                default:
                    return false;
            }
        }
    }
}
