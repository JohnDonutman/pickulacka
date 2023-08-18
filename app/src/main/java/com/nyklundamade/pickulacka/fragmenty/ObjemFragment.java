package com.nyklundamade.pickulacka.fragmenty;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.nyklundamade.pickulacka.R;
import com.nyklundamade.pickulacka.enumTridy.ObjemEnum;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ObjemFragment extends Fragment {

    private Spinner jednotkySpinner;
    private EditText brazilskyPytelEditText, uruguayskyPytelEditText, nadrzeFabieEditText, objemEditText;
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            if (objemEditText.isFocused()) {
                brazilskyPytelEditText.removeTextChangedListener(textWatcher);
                uruguayskyPytelEditText.removeTextChangedListener(textWatcher);
                nadrzeFabieEditText.removeTextChangedListener(textWatcher);
            } else if (brazilskyPytelEditText.isFocused()) {
                objemEditText.removeTextChangedListener(textWatcher);
                uruguayskyPytelEditText.removeTextChangedListener(textWatcher);
                nadrzeFabieEditText.removeTextChangedListener(textWatcher);
            } else if (uruguayskyPytelEditText.isFocused()) {
                objemEditText.removeTextChangedListener(textWatcher);
                brazilskyPytelEditText.removeTextChangedListener(textWatcher);
                nadrzeFabieEditText.removeTextChangedListener(textWatcher);
            } else if (nadrzeFabieEditText.isFocused()) {
                objemEditText.removeTextChangedListener(textWatcher);
                brazilskyPytelEditText.removeTextChangedListener(textWatcher);
                uruguayskyPytelEditText.removeTextChangedListener(textWatcher);
            }
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (objemEditText.isFocused()) {
                pocitejObjem(s);
            } else if (brazilskyPytelEditText.isFocused()) {
                pocitejBrazilskePytle(s);
            } else if (uruguayskyPytelEditText.isFocused()) {
                pocitejUruguayskePytle(s);
            } else if (nadrzeFabieEditText.isFocused()) {
                pocitejNadrze(s);
            }
        }
    };

    private View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                EditText editText = (EditText) v;

                if (editText == objemEditText) {
                    objemEditText.addTextChangedListener(textWatcher);
                } else if (editText == brazilskyPytelEditText) {
                    brazilskyPytelEditText.addTextChangedListener(textWatcher);
                } else if (editText == uruguayskyPytelEditText) {
                    uruguayskyPytelEditText.addTextChangedListener(textWatcher);
                } else if (editText == nadrzeFabieEditText) {
                    nadrzeFabieEditText.addTextChangedListener(textWatcher);
                }
            }
        }
    };

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_objem, container, false);

        InputFilter decimalFilter = new InputFilter() {
            final Pattern pattern = Pattern.compile("[0-9]*\\.?[0-9]*");

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                StringBuilder builder = new StringBuilder(dest);
                builder.replace(dstart, dend, source.subSequence(start, end).toString());

                Matcher matcher = pattern.matcher(builder.toString());

                if (!matcher.matches()) {
                    return "";
                }

                return null;
            }
        };

        brazilskyPytelEditText = view.findViewById(R.id.editTextBrazilskyPytel);
        uruguayskyPytelEditText = view.findViewById(R.id.editTextUruguayskyPytel);
        nadrzeFabieEditText = view.findViewById(R.id.editTextFabieNadrz);
        objemEditText = view.findViewById(R.id.editTextObjem);

        objemEditText.setFilters(new InputFilter[]{decimalFilter});
        brazilskyPytelEditText.setFilters(new InputFilter[]{decimalFilter});
        uruguayskyPytelEditText.setFilters(new InputFilter[]{decimalFilter});
        nadrzeFabieEditText.setFilters(new InputFilter[]{decimalFilter});

        objemEditText.setOnFocusChangeListener(onFocusChangeListener);
        brazilskyPytelEditText.setOnFocusChangeListener(onFocusChangeListener);
        uruguayskyPytelEditText.setOnFocusChangeListener(onFocusChangeListener);
        nadrzeFabieEditText.setOnFocusChangeListener(onFocusChangeListener);

        jednotkySpinner = view.findViewById(R.id.spinnerJendotkyObjem);

        String[] znacky = new String[ObjemEnum.values().length];
        for (int i = 0; i < ObjemEnum.values().length; i++) {
            znacky[i] = ObjemEnum.values()[i].getZnacka();
        }

        ArrayAdapter<String> znackyAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, znacky);
        znackyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jednotkySpinner.setAdapter(znackyAdapter);
        jednotkySpinner.setSelection(3);

        return view;
    }

    private void pocitejObjem(Editable objemEditable) {
        String objemString = objemEditable.toString();
        if (objemString.isEmpty()) {
            objemString = "0";
        }

        double objem = Double.parseDouble(objemString);

        double totalBrazilskyPytel = objem / 7.29;
        double totalUruguayskyPytel = objem / 0.25;
        double totalNadrze = objem / 45;

        // Zaokrouhlení na maximálně 6 desetinných míst
        totalBrazilskyPytel = Math.round(totalBrazilskyPytel * 1e6) / 1e6;
        totalUruguayskyPytel = Math.round(totalUruguayskyPytel * 1e6) / 1e6;
        totalNadrze = Math.round(totalNadrze * 1e6) / 1e6;

        brazilskyPytelEditText.setText(String.valueOf(totalBrazilskyPytel));
        uruguayskyPytelEditText.setText(String.valueOf(totalUruguayskyPytel));
        nadrzeFabieEditText.setText(String.valueOf(totalNadrze));
    }

    private void pocitejBrazilskePytle(Editable pytleEditable) {
        String pytleString = pytleEditable.toString();
        if (pytleString.isEmpty()) {
            pytleString = "0";
        }

        double pytle = Double.parseDouble(pytleString);

        double totalObjem = pytle * 7.29;
        double totalUruguayskyPytel = totalObjem / 0.25;
        double totalNadrze = totalObjem / 45;

        // Zaokrouhlení na maximálně 6 desetinných míst
        totalObjem = Math.round(totalObjem * 1e6) / 1e6;
        totalUruguayskyPytel = Math.round(totalUruguayskyPytel * 1e6) / 1e6;
        totalNadrze = Math.round(totalNadrze * 1e6) / 1e6;

        objemEditText.setText(String.valueOf(totalObjem));
        uruguayskyPytelEditText.setText(String.valueOf(totalUruguayskyPytel));
        nadrzeFabieEditText.setText(String.valueOf(totalNadrze));

    }

    private void pocitejUruguayskePytle(Editable pytleEditable) {
        String pytleString = pytleEditable.toString();
        if (pytleString.isEmpty()) {
            pytleString = "0";
        }

        double pytle = Double.parseDouble(pytleString);

        double totalObjem = pytle * 0.25;
        double totalBrazilskyPytel = totalObjem / 7.29;
        double totalNadrze = totalObjem / 45;

        // Zaokrouhlení na maximálně 6 desetinných míst
        totalObjem = Math.round(totalObjem * 1e6) / 1e6;
        totalBrazilskyPytel = Math.round(totalBrazilskyPytel * 1e6) / 1e6;
        totalNadrze = Math.round(totalNadrze * 1e6) / 1e6;

        objemEditText.setText(String.valueOf(totalObjem));
        brazilskyPytelEditText.setText(String.valueOf(totalBrazilskyPytel));
        nadrzeFabieEditText.setText(String.valueOf(totalNadrze));
    }

    private void pocitejNadrze(Editable nadrzeEditable) {
        String nadrzeString = nadrzeEditable.toString();
        if (nadrzeString.isEmpty()) {
            nadrzeString = "0";
        }

        double nadrze = Double.parseDouble(nadrzeString);

        double totalObjem = nadrze * 45;
        double totalUruguayskyPytel = totalObjem / 0.25;
        double totalBrazilskyPytel = totalObjem / 7.29;

        // Zaokrouhlení na maximálně 6 desetinných míst
        totalObjem = Math.round(totalObjem * 1e6) / 1e6;
        totalBrazilskyPytel = Math.round(totalBrazilskyPytel * 1e6) / 1e6;
        totalUruguayskyPytel = Math.round(totalUruguayskyPytel * 1e6) / 1e6;

        objemEditText.setText(String.valueOf(totalObjem));
        brazilskyPytelEditText.setText(String.valueOf(totalBrazilskyPytel));
        uruguayskyPytelEditText.setText(String.valueOf(totalUruguayskyPytel));
    }


}
