package TaskNetwork;

import persistencia.dto.ClienteNaoPositivadoDTO;
import persistencia.dto.ItenPedidoDTO;
import persistencia.dto.PedidoDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("pedidos/cadastrar")
    Call<Long> enviarPedido(@Body PedidoDTO pedDTO);

    @POST("clientenaopositivado/cadastrar")
    Call<Void> enviarClienteNaoPositivado(@Body ClienteNaoPositivadoDTO cnpDTO);
}
