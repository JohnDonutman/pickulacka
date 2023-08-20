package com.nyklundamade.pickulacka.fragmenty;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.nyklundamade.pickulacka.R;
import com.nyklundamade.pickulacka.enumTridy.PlochaEnum;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlochaFragment extends Fragment {

    Spinner jednotkySpinner;
    EditText stadionyEditText, vaclavakyEditText, plochaEditText;
    TextView bzilionTextView;
    private Double koeficientZnacky, stadionyAktualni, vaclavakyAktualni, plochaAktualni;
    private PlochaEnum aktualniZnacka;
    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (plochaEditText.isFocused()) {
                pocitejPlochu(s);
            } else if (stadionyEditText.isFocused()) {
                pocitejStadiony(s);
            } else if (vaclavakyEditText.isFocused()) {
                pocitejVaclavaky(s);
            }
        }
    };

    private final View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                EditText editText = (EditText) v;

                stadionyEditText.removeTextChangedListener(textWatcher);
                vaclavakyEditText.removeTextChangedListener(textWatcher);
                plochaEditText.removeTextChangedListener(textWatcher);

                editText.addTextChangedListener(textWatcher);
            }
        }
    };

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plocha, container, false);

        koeficientZnacky = 1.0;

        plochaAktualni = 0.0;
        stadionyAktualni = 0.0;
        vaclavakyAktualni = 0.0;

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

        plochaEditText = view.findViewById(R.id.editTextPlocha);
        stadionyEditText = view.findViewById(R.id.editTextStadiony);
        vaclavakyEditText = view.findViewById(R.id.editTextVaclavaky);
        bzilionTextView = view.findViewById(R.id.textViewBzilion);

        plochaEditText.setFilters(new InputFilter[]{decimalFilter});
        stadionyEditText.setFilters(new InputFilter[]{decimalFilter});
        vaclavakyEditText.setFilters(new InputFilter[]{decimalFilter});

        plochaEditText.setOnFocusChangeListener(onFocusChangeListener);
        stadionyEditText.setOnFocusChangeListener(onFocusChangeListener);
        vaclavakyEditText.setOnFocusChangeListener(onFocusChangeListener);

        jednotkySpinner = view.findViewById(R.id.spinnerJendotkyPlocha);

        String[] znacky = new String[PlochaEnum.values().length];
        for (int i = 0; i < PlochaEnum.values().length; i++) {
            znacky[i] = PlochaEnum.values()[i].getZnacka();
        }

        ArrayAdapter<String> znackyAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, znacky);
        znackyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jednotkySpinner.setAdapter(znackyAdapter);
        jednotkySpinner.setSelection(3);

        aktualniZnacka = PlochaEnum.fromString((String) jednotkySpinner.getSelectedItem());

        jednotkySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                stadionyEditText.removeTextChangedListener(textWatcher);
                vaclavakyEditText.removeTextChangedListener(textWatcher);
                plochaEditText.removeTextChangedListener(textWatcher);

                double nasobitel = getNasobitel();

                // nastavím nový koeficient podle nově zvolené značky
                switch (position) {
                    case 0: {
                        koeficientZnacky = 0.000001;
                        break;
                    }
                    case 1: {
                        koeficientZnacky = 0.0001;
                        break;
                    }
                    case 2: {
                        koeficientZnacky = 0.01;
                        break;
                    }
                    case 3: {
                        koeficientZnacky = 1.0;
                        break;
                    }
                    case 4: {
                        koeficientZnacky = 1000000.0;
                        break;
                    }
                }

                // následně spočítám skutečnou hodnotu
                stadionyAktualni = stadionyAktualni * nasobitel * koeficientZnacky;
                vaclavakyAktualni = vaclavakyAktualni * nasobitel * koeficientZnacky;

                // Zaokrouhlení na maximálně 6 desetinných míst
                stadionyAktualni = Math.round(stadionyAktualni * 1e6) / 1e6;
                vaclavakyAktualni = Math.round(vaclavakyAktualni * 1e6) / 1e6;

                // a nastavím jako text do polí
                if (stadionyAktualni < 0.001) {
                    stadionyEditText.setText("0.0");
                } else {
                    stadionyEditText.setText(String.valueOf(stadionyAktualni));
                }
                if (vaclavakyAktualni < 0.001) {
                    vaclavakyEditText.setText("0.0");
                } else {
                    vaclavakyEditText.setText(String.valueOf(vaclavakyAktualni));
                }

                if (vaclavakyAktualni > 2.56897) {
                    bzilionTextView.setText(R.string.neni_to_bzilion);
                } else {
                    bzilionTextView.setText(R.string.je_to_bzilion);
                }

                // nastavím novou aktuální značku
                aktualniZnacka = PlochaEnum.fromString((String) jednotkySpinner.getSelectedItem());

                // nastavím listenera na editText zpět
                if (stadionyEditText.isFocused()) {
                    stadionyEditText.addTextChangedListener(textWatcher);
                } else if (vaclavakyEditText.isFocused()) {
                    vaclavakyEditText.addTextChangedListener(textWatcher);
                } else if (plochaEditText.isFocused()) {
                    plochaEditText.addTextChangedListener(textWatcher);
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
        if (aktualniZnacka.equals(PlochaEnum.MILIMETR)) {
            nasobitel = 1000000.0;
        } else if (aktualniZnacka.equals(PlochaEnum.CENTIMETR)) {
            nasobitel = 10000.0;
        } else if (aktualniZnacka.equals(PlochaEnum.DECIMETR)) {
            nasobitel = 100.0;
        } else if (aktualniZnacka.equals(PlochaEnum.KILOMETR)) {
            nasobitel = 0.000001;
        }
        return nasobitel;
    }

    private void pocitejPlochu(Editable editable) {
        String plochaString = editable.toString();

        if (plochaString.isEmpty()) {
            plochaString = "0";
        }

        plochaAktualni = Double.parseDouble(plochaString);

        // výpočet podle litrů
        stadionyAktualni = plochaAktualni / 7140 * koeficientZnacky;
        vaclavakyAktualni = plochaAktualni / 42255 * koeficientZnacky;

        // Zaokrouhlení na maximálně 6 desetinných míst
        stadionyAktualni = Math.round(stadionyAktualni * 1e6) / 1e6;
        vaclavakyAktualni = Math.round(vaclavakyAktualni * 1e6) / 1e6;

        if (stadionyAktualni < 0.001) {
            stadionyEditText.setText("0.0");
        } else {
            stadionyEditText.setText(String.valueOf(stadionyAktualni));
        }
        if (vaclavakyAktualni < 0.001) {
            vaclavakyEditText.setText("0.0");
        } else {
            vaclavakyEditText.setText(String.valueOf(vaclavakyAktualni));
        }

        if (vaclavakyAktualni > 2.56897) {
            bzilionTextView.setText(R.string.neni_to_bzilion);
        } else {
            bzilionTextView.setText(R.string.je_to_bzilion);
        }
    }

    private void pocitejStadiony(Editable editable) {
        String stadionString = editable.toString();

        if (stadionString.isEmpty()) {
            stadionString = "0";
        }

        stadionyAktualni = Double.parseDouble(stadionString);

        // výpočet podle litrů
        plochaAktualni = stadionyAktualni * 7140 / koeficientZnacky;
        vaclavakyAktualni = plochaAktualni / 42255 * koeficientZnacky;

        // Zaokrouhlení na maximálně 6 desetinných míst
        plochaAktualni = Math.round(plochaAktualni * 1e6) / 1e6;
        vaclavakyAktualni = Math.round(vaclavakyAktualni * 1e6) / 1e6;

        if (plochaAktualni < 0.001) {
            plochaEditText.setText("0.0");
        } else {
            plochaEditText.setText(String.valueOf(plochaAktualni));
        }
        if (vaclavakyAktualni < 0.001) {
            vaclavakyEditText.setText("0.0");
        } else {
            vaclavakyEditText.setText(String.valueOf(vaclavakyAktualni));
        }

        if (vaclavakyAktualni > 2.56897) {
            bzilionTextView.setText(R.string.neni_to_bzilion);
        } else {
            bzilionTextView.setText(R.string.je_to_bzilion);
        }
    }

    private void pocitejVaclavaky(Editable editable) {
        String vaclavakString = editable.toString();

        if (vaclavakString.isEmpty()) {
            vaclavakString = "0";
        }

        vaclavakyAktualni = Double.parseDouble(vaclavakString);

        // výpočet podle litrů
        plochaAktualni = vaclavakyAktualni * 42255 / koeficientZnacky;
        stadionyAktualni = plochaAktualni / 7140 * koeficientZnacky;

        // Zaokrouhlení na maximálně 6 desetinných míst
        stadionyAktualni = Math.round(stadionyAktualni * 1e6) / 1e6;
        plochaAktualni = Math.round(plochaAktualni * 1e6) / 1e6;

        if (stadionyAktualni < 0.001) {
            stadionyEditText.setText("0.0");
        } else {
            stadionyEditText.setText(String.valueOf(stadionyAktualni));
        }
        if (plochaAktualni < 0.001) {
            plochaEditText.setText("0.0");
        } else {
            plochaEditText.setText(String.valueOf(plochaAktualni));
        }

        if (vaclavakyAktualni > 2.56897) {
            bzilionTextView.setText(R.string.neni_to_bzilion);
        } else {
            bzilionTextView.setText(R.string.je_to_bzilion);
        }
    }
}
