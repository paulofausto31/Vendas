package persistencia.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
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
    private List<ClienteDTO> lista;
    private OnItemClickListener listener;

    public RVClienteAdapter(Context ctx, List<ClienteDTO> lista, OnItemClickListener listener) {
        this.context = ctx;
        this.lista = lista;
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cliente_lista_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ClienteDTO dto = lista.get(position);
        PedidoBRL pedBRL = new PedidoBRL(context);
        ClienteNaoPositivadoBRL cnpBRL = new ClienteNaoPositivadoBRL(context);

        holder.txtNomeCliente.setText(dto.getRazaoSocial());
        holder.txtFantasia.setText(dto.getNome());
        holder.txtSeqVisita.setText("Seq. Visita: " + dto.getSeqVisita().toString());

        // Lógica de cores dinâmicas para o Status
        if (pedBRL.getPedidoAbertoCliente(dto.getCodCliente()) > 0) {
            holder.txtStatusPedido.setText("Pedido Realizado");
            holder.txtStatusPedido.setTextColor(Color.parseColor("#2E7D32")); // Verde escuro
            holder.txtStatusPedido.setBackgroundColor(Color.parseColor("#E8F5E9")); // Verde clarinho de fundo
            holder.txtStatusPedido.setVisibility(View.VISIBLE);
        } else if (cnpBRL.getCNPClienteAberto(dto.getCodCliente()) > 0) {
            holder.txtStatusPedido.setText("Justificativa");
            holder.txtStatusPedido.setTextColor(Color.parseColor("#E65100")); // Laranja escuro
            holder.txtStatusPedido.setBackgroundColor(Color.parseColor("#FFF3E0")); // Laranja clarinho de fundo
            holder.txtStatusPedido.setVisibility(View.VISIBLE);
        } else {
            holder.txtStatusPedido.setVisibility(View.GONE);
        }

        holder.btnMenuAcoes.setOnClickListener(v -> showBottomSheet(dto));
        holder.btnAtalhoPedido.setOnClickListener(v -> abrirTelaPedido(dto));
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    private void showBottomSheet(ClienteDTO dto) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.cliente_bottomsheet, null);

        TextView lblNome = view.findViewById(R.id.lblNomeClienteMenu);
        LinearLayout btnPedido = view.findViewById(R.id.btnMenuNovoPedido);
        LinearLayout btnFinanceiro = view.findViewById(R.id.btnMenuFinanceiro);
        LinearLayout btnComplemento = view.findViewById(R.id.btnMenuComplemento);
        LinearLayout btnJustificativa = view.findViewById(R.id.btnMenuJustificativa);

        lblNome.setText(dto.getRazaoSocial());

        btnPedido.setOnClickListener(v -> { abrirTelaPedido(dto); bottomSheetDialog.dismiss(); });
        btnJustificativa.setOnClickListener(v -> {
            Intent intent = new Intent(context, JustificativaTabContainer.class);
            intent.putExtra("idCliente", dto.getId());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            bottomSheetDialog.dismiss();
        });
        btnFinanceiro.setOnClickListener(v -> {
            Intent intent = new Intent(context, ClienteContasReceber.class);
            intent.putExtra("codCliente", dto.getCodCliente());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            bottomSheetDialog.dismiss();
        });
        btnComplemento.setOnClickListener(v -> {
            Intent intent = new Intent(context, ClienteComplemento.class);
            intent.putExtra("idCliente", dto.getId());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            bottomSheetDialog.dismiss();
        });

        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
    }

    private void abrirTelaPedido(ClienteDTO dto) {
        Intent intent = new Intent(context, PedidoTabContainer.class);
        intent.putExtra("idCliente", dto.getId());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtNomeCliente, txtFantasia, txtSeqVisita, txtStatusPedido;
        public ImageButton btnMenuAcoes;
        public View btnAtalhoPedido;

        public ViewHolder(View view) {
            super(view);
            txtNomeCliente = view.findViewById(R.id.txtNomeCliente);
            txtFantasia = view.findViewById(R.id.txtFantasia);
            txtSeqVisita = view.findViewById(R.id.txtVisita);
            txtStatusPedido = view.findViewById(R.id.txtStatusPedido);
            btnMenuAcoes = view.findViewById(R.id.btnMenuAcoes);
            btnAtalhoPedido = view.findViewById(R.id.btnNovoPedido);
        }
    }
}