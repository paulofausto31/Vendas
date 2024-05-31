package vendas.telas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editText;

    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    String itemId = result.getData().getStringExtra("itemId");
                    editText.setText(itemId);
                }
            }
    );

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.edit_text);
        Button button = findViewById(R.id.button);

        button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PedidoProdutoLista.class);
            activityResultLauncher.launch(intent);
        });

        editText.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PedidoProdutoLista.class);
            activityResultLauncher.launch(intent);
        });
    }
}
