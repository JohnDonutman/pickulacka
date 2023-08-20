package com.nyklundamade.pickulacka.fragmenty;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.nyklundamade.pickulacka.R;
import com.nyklundamade.pickulacka.enumTridy.ObjemEnum;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ObjemFragment extends Fragment {

    private Spinner jednotkySpinner;
    private EditText brazilskyPytelEditText, uruguayskyPytelEditText, nadrzeFabieEditText, objemEditText;
    private double koeficientZnacky, objemAktualni, brazilskyPytelAktualni, uruguayskyPytelAktualni, nadrzFabieAktualni;
    private ObjemEnum aktualniZnacka;
    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
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

    private final View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                EditText editText = (EditText) v;

                brazilskyPytelEditText.removeTextChangedListener(textWatcher);
                uruguayskyPytelEditText.removeTextChangedListener(textWatcher);
                nadrzeFabieEditText.removeTextChangedListener(textWatcher);
                objemEditText.removeTextChangedListener(textWatcher);

                editText.addTextChangedListener(textWatcher);
            }
        }
    };

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_objem, container, false);

        koeficientZnacky = 1.0;

        objemAktualni = 0.0;
        brazilskyPytelAktualni = 0.0;
        uruguayskyPytelAktualni = 0.0;
        nadrzFabieAktualni = 0.0;

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

        aktualniZnacka = ObjemEnum.fromString((String) jednotkySpinner.getSelectedItem());

        jednotkySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                brazilskyPytelEditText.removeTextChangedListener(textWatcher);
                uruguayskyPytelEditText.removeTextChangedListener(textWatcher);
                nadrzeFabieEditText.removeTextChangedListener(textWatcher);
                objemEditText.removeTextChangedListener(textWatcher);

                double nasobitel = getNasobitel();

                // nastavím nový koeficient podle nově zvolené značky
                switch (position) {
                    case 0:
                    case 5: {
                        koeficientZnacky = 0.001;
                        break;
                    }
                    case 1: {
                        koeficientZnacky = 0.01;
                        break;
                    }
                    case 2: {
                        koeficientZnacky = 0.1;
                        break;
                    }
                    case 3:
                    case 6: {
                        koeficientZnacky = 1.0;
                        break;
                    }
                    case 4: {
                        koeficientZnacky = 100.0;
                        break;
                    }
                    case 7: {
                        koeficientZnacky = 1000.0;
                        break;
                    }
                }

                // následně spočítám skutečnou hodnotu
                brazilskyPytelAktualni = brazilskyPytelAktualni * nasobitel * koeficientZnacky;
                uruguayskyPytelAktualni = uruguayskyPytelAktualni * nasobitel * koeficientZnacky;
                nadrzFabieAktualni = nadrzFabieAktualni * nasobitel * koeficientZnacky;

                // Zaokrouhlení na maximálně 6 desetinných míst
                brazilskyPytelAktualni = Math.round(brazilskyPytelAktualni * 1e6) / 1e6;
                uruguayskyPytelAktualni = Math.round(uruguayskyPytelAktualni * 1e6) / 1e6;
                nadrzFabieAktualni = Math.round(nadrzFabieAktualni * 1e6) / 1e6;

                // a nastavím jako text do polí
                if (brazilskyPytelAktualni < 0.001) {
                    brazilskyPytelEditText.setText("0.0");
                } else {
                    brazilskyPytelEditText.setText(String.valueOf(brazilskyPytelAktualni));
                }
                if (uruguayskyPytelAktualni < 0.001) {
                    uruguayskyPytelEditText.setText("0.0");
                } else {
                    uruguayskyPytelEditText.setText(String.valueOf(uruguayskyPytelAktualni));
                }
                if (nadrzFabieAktualni < 0.001) {
                    nadrzeFabieEditText.setText("0.0");
                } else {
                    nadrzeFabieEditText.setText(String.valueOf(nadrzFabieAktualni));
                }

                // nastavím novou aktuální značku
                aktualniZnacka = ObjemEnum.fromString((String) jednotkySpinner.getSelectedItem());

                // nastavím listenera na editText zpět
                if (objemEditText.isFocused()) {
                    objemEditText.addTextChangedListener(textWatcher);
                } else if (brazilskyPytelEditText.isFocused()) {
                    brazilskyPytelEditText.addTextChangedListener(textWatcher);
                } else if (uruguayskyPytelEditText.isFocused()) {
                    uruguayskyPytelEditText.addTextChangedListener(textWatcher);
                } else if (nadrzeFabieEditText.isFocused()) {
                    nadrzeFabieEditText.addTextChangedListener(textWatcher);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    private double getNasobitel() {
        double nasobitel = 1.0;

        // zavedu násobitele pro převod na litry podle poslední volené značky
        if (aktualniZnacka.equals(ObjemEnum.MILILITR) || aktualniZnacka.equals(ObjemEnum.KUBIK_CENTIMETR)) {
            nasobitel = 1000;
        } else if (aktualniZnacka.equals(ObjemEnum.CENTILITR)) {
            nasobitel = 100;
        } else if (aktualniZnacka.equals(ObjemEnum.DECILITR)) {
            nasobitel = 10;
        } else if (aktualniZnacka.equals(ObjemEnum.HEKTOLITR)) {
            nasobitel = 0.01;
        } else if (aktualniZnacka.equals(ObjemEnum.KUBIK_METR)) {
            nasobitel = 0.001;
        }
        return nasobitel;
    }

    private void pocitejObjem(Editable objemEditable) {
        String objemString = objemEditable.toString();

        if (objemString.isEmpty()) {
            objemString = "0";
        }

        objemAktualni = Double.parseDouble(objemString);

        // výpočet podle litrů
        brazilskyPytelAktualni = objemAktualni / 7.29 * koeficientZnacky;
        uruguayskyPytelAktualni = objemAktualni / 0.25 * koeficientZnacky;
        nadrzFabieAktualni = objemAktualni / 45 * koeficientZnacky;

        // Zaokrouhlení na maximálně 6 desetinných míst
        brazilskyPytelAktualni = Math.round(brazilskyPytelAktualni * 1e6) / 1e6;
        uruguayskyPytelAktualni = Math.round(uruguayskyPytelAktualni * 1e6) / 1e6;
        nadrzFabieAktualni = Math.round(nadrzFabieAktualni * 1e6) / 1e6;

        if (brazilskyPytelAktualni < 0.001) {
            brazilskyPytelEditText.setText("0.0");
        } else {
            brazilskyPytelEditText.setText(String.valueOf(brazilskyPytelAktualni));
        }
        if (uruguayskyPytelAktualni < 0.001) {
            uruguayskyPytelEditText.setText("0.0");
        } else {
            uruguayskyPytelEditText.setText(String.valueOf(uruguayskyPytelAktualni));
        }
        if (nadrzFabieAktualni < 0.001) {
            nadrzeFabieEditText.setText("0.0");
        } else {
            nadrzeFabieEditText.setText(String.valueOf(nadrzFabieAktualni));
        }
    }

    private void pocitejBrazilskePytle(Editable pytleEditable) {
        String pytleString = pytleEditable.toString();
        if (pytleString.isEmpty()) {
            pytleString = "0";
        }

        brazilskyPytelAktualni = Double.parseDouble(pytleString);

        objemAktualni = brazilskyPytelAktualni * 7.29 / koeficientZnacky;
        uruguayskyPytelAktualni = objemAktualni / 0.25 * koeficientZnacky;
        nadrzFabieAktualni = objemAktualni / 45 * koeficientZnacky;

        // Zaokrouhlení na maximálně 6 desetinných míst
        objemAktualni = Math.round(objemAktualni * 1e6) / 1e6;
        uruguayskyPytelAktualni = Math.round(uruguayskyPytelAktualni * 1e6) / 1e6;
        nadrzFabieAktualni = Math.round(nadrzFabieAktualni * 1e6) / 1e6;

        if (objemAktualni < 0.001) {
            objemEditText.setText("0.0");
        } else {
            objemEditText.setText(String.valueOf(objemAktualni));
        }
        if (uruguayskyPytelAktualni < 0.001) {
            uruguayskyPytelEditText.setText("0.0");
        } else {
            uruguayskyPytelEditText.setText(String.valueOf(uruguayskyPytelAktualni));
        }
        if (nadrzFabieAktualni < 0.001) {
            nadrzeFabieEditText.setText("0.0");
        } else {
            nadrzeFabieEditText.setText(String.valueOf(nadrzFabieAktualni));
        }

    }

    private void pocitejUruguayskePytle(Editable pytleEditable) {
        String pytleString = pytleEditable.toString();
        if (pytleString.isEmpty()) {
            pytleString = "0";
        }

        uruguayskyPytelAktualni = Double.parseDouble(pytleString);

        objemAktualni = uruguayskyPytelAktualni * 0.25 / koeficientZnacky;
        brazilskyPytelAktualni = objemAktualni / 7.29 * koeficientZnacky;
        nadrzFabieAktualni = objemAktualni / 45 * koeficientZnacky;

        // Zaokrouhlení na maximálně 6 desetinných míst
        objemAktualni = Math.round(objemAktualni * 1e6) / 1e6;
        brazilskyPytelAktualni = Math.round(brazilskyPytelAktualni * 1e6) / 1e6;
        nadrzFabieAktualni = Math.round(nadrzFabieAktualni * 1e6) / 1e6;

        if (brazilskyPytelAktualni < 0.001) {
            brazilskyPytelEditText.setText("0.0");
        } else {
            brazilskyPytelEditText.setText(String.valueOf(brazilskyPytelAktualni));
        }
        if (objemAktualni < 0.001) {
            objemEditText.setText("0.0");
        } else {
            objemEditText.setText(String.valueOf(objemAktualni));
        }
        if (nadrzFabieAktualni < 0.001) {
            nadrzeFabieEditText.setText("0.0");
        } else {
            nadrzeFabieEditText.setText(String.valueOf(nadrzFabieAktualni));
        }
    }

    private void pocitejNadrze(Editable nadrzeEditable) {
        String nadrzeString = nadrzeEditable.toString();
        if (nadrzeString.isEmpty()) {
            nadrzeString = "0";
        }

        nadrzFabieAktualni = Double.parseDouble(nadrzeString);

        objemAktualni = nadrzFabieAktualni * 45 / koeficientZnacky;
        uruguayskyPytelAktualni = objemAktualni / 0.25 * koeficientZnacky;
        brazilskyPytelAktualni = objemAktualni / 7.29 * koeficientZnacky;

        // Zaokrouhlení na maximálně 6 desetinných míst
        objemAktualni = Math.round(objemAktualni * 1e6) / 1e6;
        brazilskyPytelAktualni = Math.round(brazilskyPytelAktualni * 1e6) / 1e6;
        uruguayskyPytelAktualni = Math.round(uruguayskyPytelAktualni * 1e6) / 1e6;

        if (brazilskyPytelAktualni < 0.001) {
            brazilskyPytelEditText.setText("0.0");
        } else {
            brazilskyPytelEditText.setText(String.valueOf(brazilskyPytelAktualni));
        }
        if (uruguayskyPytelAktualni < 0.001) {
            uruguayskyPytelEditText.setText("0.0");
        } else {
            uruguayskyPytelEditText.setText(String.valueOf(uruguayskyPytelAktualni));
        }
        if (objemAktualni < 0.001) {
            objemEditText.setText("0.0");
        } else {
            objemEditText.setText(String.valueOf(objemAktualni));
        }
    }
}
