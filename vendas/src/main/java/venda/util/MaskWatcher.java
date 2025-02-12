package venda.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class MaskWatcher implements TextWatcher {

    private final EditText editText;
    private boolean isUpdating;
    private final String mask = "##/##/####";
    private final StringBuilder oldValue = new StringBuilder();

    public MaskWatcher(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        oldValue.setLength(0);
        oldValue.append(s);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (isUpdating || s.length() > mask.length()) {
            return;
        }

        String cleanText = s.toString().replaceAll("[^\\d]", "");
        StringBuilder maskedText = new StringBuilder();

        int index = 0;
        for (char m : mask.toCharArray()) {
            if (m == '#' && index < cleanText.length()) {
                maskedText.append(cleanText.charAt(index));
                index++;
            } else if (m != '#') {
                maskedText.append(m);
            }
        }

        isUpdating = true;
        editText.setText(maskedText.toString());
        editText.setSelection(maskedText.length());
        isUpdating = false;
    }

    @Override
    public void afterTextChanged(Editable s) {
    }
}
