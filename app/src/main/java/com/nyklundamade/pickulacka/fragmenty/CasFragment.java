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
import com.nyklundamade.pickulacka.enumTridy.CasEnum;
import com.nyklundamade.pickulacka.enumTridy.ObjemEnum;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CasFragment extends Fragment {

    private Spinner jednotkySpinner;
    private TextView zpozdeniZnackaTextView;
    private EditText casEditText, zpozdeniEditText, dneskEditText, pozdejcEditText, pivkoEditText, uzjduEditText, mnultEditText;
    private double koeficientZnacky, casAktualni, zpozdeniAktualni, dneskAktualni, pozdejcAktualni, pivkoAktualni, mnultAktualni, uzjduAktualni;
    private CasEnum aktualniZnacka;

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (casEditText.isFocused()) {
                pocitejCas(s);
            } else if (zpozdeniEditText.isFocused()) {
                pocitejZpozdeni(s);
            } else if (dneskEditText.isFocused()) {
                pocitejDnesky(s);
            } else if (pozdejcEditText.isFocused()) {
                pocitejPozdejc(s);
            } else if (pivkoEditText.isFocused()) {
                pocitejPivka(s);
            } else if (uzjduEditText.isFocused()) {
                pocitejUzjdu(s);
            } else if (mnultEditText.isFocused()) {
                pocitejMnulty(s);
            }
        }
    };

    private final View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                EditText editText = (EditText) v;

                casEditText.removeTextChangedListener(textWatcher);
                zpozdeniEditText.removeTextChangedListener(textWatcher);
                dneskEditText.removeTextChangedListener(textWatcher);
                pozdejcEditText.removeTextChangedListener(textWatcher);
                pivkoEditText.removeTextChangedListener(textWatcher);
                uzjduEditText.removeTextChangedListener(textWatcher);
                mnultEditText.removeTextChangedListener(textWatcher);

                editText.addTextChangedListener(textWatcher);
            }
        }
    };

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cas, container, false);

        koeficientZnacky = 1.0;

        casAktualni = 0.0;
        zpozdeniAktualni = 0.0;
        dneskAktualni = 0.0;
        pozdejcAktualni = 0.0;
        pivkoAktualni = 0.0;
        uzjduAktualni = 0.0;
        mnultAktualni = 0.0;

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

        casEditText = view.findViewById(R.id.editTextCas);
        zpozdeniEditText = view.findViewById(R.id.editTextZpozdeni);
        dneskEditText = view.findViewById(R.id.editTextDnesk);
        pozdejcEditText = view.findViewById(R.id.editTextPozdejc);
        pivkoEditText = view.findViewById(R.id.editTextPivko);
        uzjduEditText = view.findViewById(R.id.editTextUzjdu);
        mnultEditText = view.findViewById(R.id.editTextMnult);
        zpozdeniZnackaTextView = view.findViewById(R.id.textViewZpozdeniZnacka);

        casEditText.setFilters(new InputFilter[]{decimalFilter});
        zpozdeniEditText.setFilters(new InputFilter[]{decimalFilter});
        dneskEditText.setFilters(new InputFilter[]{decimalFilter});
        pozdejcEditText.setFilters(new InputFilter[]{decimalFilter});
        pivkoEditText.setFilters(new InputFilter[]{decimalFilter});
        uzjduEditText.setFilters(new InputFilter[]{decimalFilter});
        mnultEditText.setFilters(new InputFilter[]{decimalFilter});

        casEditText.setOnFocusChangeListener(onFocusChangeListener);
        zpozdeniEditText.setOnFocusChangeListener(onFocusChangeListener);
        dneskEditText.setOnFocusChangeListener(onFocusChangeListener);
        pozdejcEditText.setOnFocusChangeListener(onFocusChangeListener);
        pivkoEditText.setOnFocusChangeListener(onFocusChangeListener);
        uzjduEditText.setOnFocusChangeListener(onFocusChangeListener);
        mnultEditText.setOnFocusChangeListener(onFocusChangeListener);

        jednotkySpinner = view.findViewById(R.id.spinnerJendotkyCas);

        String[] znacky = new String[CasEnum.values().length];
        for (int i = 0; i < CasEnum.values().length; i++) {
            znacky[i] = CasEnum.values()[i].getZnacka();
        }

        ArrayAdapter<String> znackyAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, znacky);
        znackyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jednotkySpinner.setAdapter(znackyAdapter);
        jednotkySpinner.setSelection(1);

        aktualniZnacka = CasEnum.fromString((String) jednotkySpinner.getSelectedItem());

        jednotkySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                casEditText.removeTextChangedListener(textWatcher);
                zpozdeniEditText.removeTextChangedListener(textWatcher);
                dneskEditText.removeTextChangedListener(textWatcher);
                pozdejcEditText.removeTextChangedListener(textWatcher);
                pivkoEditText.removeTextChangedListener(textWatcher);
                uzjduEditText.removeTextChangedListener(textWatcher);
                mnultEditText.removeTextChangedListener(textWatcher);

                double nasobitel = getNasobitel();

                // nastavím nový koeficient podle nově zvolené značky
                switch (position) {
                    case 0: {
                        koeficientZnacky = (double) 1/60;
                        zpozdeniZnackaTextView.setText(CasEnum.SEKUNDA.getZnacka());
                        break;
                    }
                    case 1: {
                        koeficientZnacky = 1.0;
                        zpozdeniZnackaTextView.setText(CasEnum.MINUTA.getZnacka());
                        break;
                    }
                    case 2: {
                        koeficientZnacky = 60;
                        zpozdeniZnackaTextView.setText(CasEnum.HODINA.getZnacka());
                        break;
                    }
                    case 3: {
                        koeficientZnacky = 60*24;
                        zpozdeniZnackaTextView.setText(CasEnum.DEN.getZnacka());
                        break;
                    }
                    case 4: {
                        koeficientZnacky = 60*24*30;
                        zpozdeniZnackaTextView.setText(CasEnum.MESIC.getZnacka());
                        break;
                    }
                    case 5: {
                        koeficientZnacky = 60*24*30*365.25;
                        zpozdeniZnackaTextView.setText(CasEnum.ROK.getZnacka());
                        break;
                    }
                }

                // následně spočítám skutečnou hodnotu
                dneskAktualni = dneskAktualni * nasobitel * koeficientZnacky;
                pozdejcAktualni = pozdejcAktualni * nasobitel * koeficientZnacky;
                pivkoAktualni = pivkoAktualni * nasobitel * koeficientZnacky;
                uzjduAktualni = uzjduAktualni * nasobitel * koeficientZnacky;
                mnultAktualni = mnultAktualni * nasobitel * koeficientZnacky;

                // Zaokrouhlení na maximálně 6 desetinných míst
                dneskAktualni = Math.round(dneskAktualni * 1e6) / 1e6;
                pozdejcAktualni = Math.round(pozdejcAktualni * 1e6) / 1e6;
                pivkoAktualni = Math.round(pivkoAktualni * 1e6) / 1e6;
                uzjduAktualni = Math.round(uzjduAktualni * 1e6) / 1e6;
                mnultAktualni = Math.round(mnultAktualni * 1e6) / 1e6;

                // a nastavím jako text do polí
                if (dneskAktualni < 0.001) {
                    dneskEditText.setText("0.0");
                } else {
                    dneskEditText.setText(String.valueOf(dneskAktualni));
                }
                if (pozdejcAktualni < 0.001) {
                    pozdejcEditText.setText("0.0");
                } else {
                    pozdejcEditText.setText(String.valueOf(pozdejcAktualni));
                }
                if (pivkoAktualni < 0.001) {
                    pivkoEditText.setText("0.0");
                } else {
                    pivkoEditText.setText(String.valueOf(pivkoAktualni));
                }
                if (uzjduAktualni < 0.001) {
                    uzjduEditText.setText("0.0");
                } else {
                    uzjduEditText.setText(String.valueOf(uzjduAktualni));
                }
                if (mnultAktualni < 0.001) {
                    mnultEditText.setText("0.0");
                } else {
                    mnultEditText.setText(String.valueOf(mnultAktualni));
                }

                // nastavím novou aktuální značku
                aktualniZnacka = CasEnum.fromString((String) jednotkySpinner.getSelectedItem());

                // nastavím listenera na editText zpět
                if (casEditText.isFocused()) {
                    casEditText.addTextChangedListener(textWatcher);
                } else if (dneskEditText.isFocused()) {
                    dneskEditText.addTextChangedListener(textWatcher);
                } else if (pozdejcEditText.isFocused()) {
                    pozdejcEditText.addTextChangedListener(textWatcher);
                } else if (pivkoEditText.isFocused()) {
                    pivkoEditText.addTextChangedListener(textWatcher);
                } else if (uzjduEditText.isFocused()) {
                    uzjduEditText.addTextChangedListener(textWatcher);
                } else if (mnultEditText.isFocused()) {
                    mnultEditText.addTextChangedListener(textWatcher);
                } else if (zpozdeniEditText.isFocused()) {
                    zpozdeniEditText.addTextChangedListener(textWatcher);
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
        if (aktualniZnacka.equals(CasEnum.SEKUNDA)) {
            nasobitel = 60.0;
        } else if (aktualniZnacka.equals(CasEnum.HODINA)) {
            nasobitel = (double) 1/60;
        } else if (aktualniZnacka.equals(CasEnum.DEN)) {
            nasobitel = (double) 1/60/24;
        } else if (aktualniZnacka.equals(CasEnum.MESIC)) {
            nasobitel = (double) 1/60/24/30;
        } else if (aktualniZnacka.equals(CasEnum.ROK)) {
            nasobitel = (double) 1 /60/24/30/365.25;
        }
        return nasobitel;
    }

    private void pocitejCas(Editable objemEditable) {
        String casString = objemEditable.toString();

        if (casString.isEmpty()) {
            casString = "0";
        }

        casAktualni = zpozdeniAktualni + Double.parseDouble(casString);

        // výpočet podle minut
        dneskAktualni = casAktualni / 1440 * koeficientZnacky;
        pozdejcAktualni = casAktualni / 941.22 * koeficientZnacky;
        pivkoAktualni = casAktualni / 38.756 * koeficientZnacky;
        uzjduAktualni = casAktualni / 15 * koeficientZnacky;
        mnultAktualni = casAktualni / 2.34 * koeficientZnacky;

        // Zaokrouhlení na maximálně 6 desetinných míst
        dneskAktualni = Math.round(dneskAktualni * 1e6) / 1e6;
        pozdejcAktualni = Math.round(pozdejcAktualni * 1e6) / 1e6;
        pivkoAktualni = Math.round(pivkoAktualni * 1e6) / 1e6;
        uzjduAktualni = Math.round(uzjduAktualni * 1e6) / 1e6;
        mnultAktualni = Math.round(mnultAktualni * 1e6) / 1e6;

        // a nastavím jako text do polí
        if (dneskAktualni < 0.001) {
            dneskEditText.setText("0.0");
        } else {
            dneskEditText.setText(String.valueOf(dneskAktualni));
        }
        if (pozdejcAktualni < 0.001) {
            pozdejcEditText.setText("0.0");
        } else {
            pozdejcEditText.setText(String.valueOf(pozdejcAktualni));
        }
        if (pivkoAktualni < 0.001) {
            pivkoEditText.setText("0.0");
        } else {
            pivkoEditText.setText(String.valueOf(pivkoAktualni));
        }
        if (uzjduAktualni < 0.001) {
            uzjduEditText.setText("0.0");
        } else {
            uzjduEditText.setText(String.valueOf(uzjduAktualni));
        }
        if (mnultAktualni < 0.001) {
            mnultEditText.setText("0.0");
        } else {
            mnultEditText.setText(String.valueOf(mnultAktualni));
        }
    }

    private void pocitejDnesky(Editable objemEditable) {
        String dneskString = objemEditable.toString();

        if (dneskString.isEmpty()) {
            dneskString = "0";
        }

        dneskAktualni = Double.parseDouble(dneskString);

        // výpočet podle minut
        casAktualni = zpozdeniAktualni + dneskAktualni * 1440 / koeficientZnacky;
        pozdejcAktualni = casAktualni / 941.22 * koeficientZnacky;
        pivkoAktualni = casAktualni / 38.756 * koeficientZnacky;
        uzjduAktualni = casAktualni / 15 * koeficientZnacky;
        mnultAktualni = casAktualni / 2.34 * koeficientZnacky;

        // Zaokrouhlení na maximálně 6 desetinných míst
        casAktualni = Math.round(casAktualni * 1e6) / 1e6;
        pozdejcAktualni = Math.round(pozdejcAktualni * 1e6) / 1e6;
        pivkoAktualni = Math.round(pivkoAktualni * 1e6) / 1e6;
        uzjduAktualni = Math.round(uzjduAktualni * 1e6) / 1e6;
        mnultAktualni = Math.round(mnultAktualni * 1e6) / 1e6;

        // a nastavím jako text do polí
        if (casAktualni < 0.001) {
            casEditText.setText("0.0");
        } else {
            casEditText.setText(String.valueOf(casAktualni));
        }
        if (pozdejcAktualni < 0.001) {
            pozdejcEditText.setText("0.0");
        } else {
            pozdejcEditText.setText(String.valueOf(pozdejcAktualni));
        }
        if (pivkoAktualni < 0.001) {
            pivkoEditText.setText("0.0");
        } else {
            pivkoEditText.setText(String.valueOf(pivkoAktualni));
        }
        if (uzjduAktualni < 0.001) {
            uzjduEditText.setText("0.0");
        } else {
            uzjduEditText.setText(String.valueOf(uzjduAktualni));
        }
        if (mnultAktualni < 0.001) {
            mnultEditText.setText("0.0");
        } else {
            mnultEditText.setText(String.valueOf(mnultAktualni));
        }
    }

    private void pocitejPozdejc(Editable objemEditable) {
        String pozdejcString = objemEditable.toString();

        if (pozdejcString.isEmpty()) {
            pozdejcString = "0";
        }

        pozdejcAktualni = Double.parseDouble(pozdejcString);

        // výpočet podle minut
        casAktualni = zpozdeniAktualni + pozdejcAktualni * 941.22 / koeficientZnacky;
        dneskAktualni = casAktualni / 1440 * koeficientZnacky;
        pivkoAktualni = casAktualni / 38.756 * koeficientZnacky;
        uzjduAktualni = casAktualni / 15 * koeficientZnacky;
        mnultAktualni = casAktualni / 2.34 * koeficientZnacky;

        // Zaokrouhlení na maximálně 6 desetinných míst
        dneskAktualni = Math.round(dneskAktualni * 1e6) / 1e6;
        casAktualni = Math.round(casAktualni * 1e6) / 1e6;
        pivkoAktualni = Math.round(pivkoAktualni * 1e6) / 1e6;
        uzjduAktualni = Math.round(uzjduAktualni * 1e6) / 1e6;
        mnultAktualni = Math.round(mnultAktualni * 1e6) / 1e6;

        // a nastavím jako text do polí
        if (dneskAktualni < 0.001) {
            dneskEditText.setText("0.0");
        } else {
            dneskEditText.setText(String.valueOf(dneskAktualni));
        }
        if (casAktualni < 0.001) {
            casEditText.setText("0.0");
        } else {
            casEditText.setText(String.valueOf(casAktualni));
        }
        if (pivkoAktualni < 0.001) {
            pivkoEditText.setText("0.0");
        } else {
            pivkoEditText.setText(String.valueOf(pivkoAktualni));
        }
        if (uzjduAktualni < 0.001) {
            uzjduEditText.setText("0.0");
        } else {
            uzjduEditText.setText(String.valueOf(uzjduAktualni));
        }
        if (mnultAktualni < 0.001) {
            mnultEditText.setText("0.0");
        } else {
            mnultEditText.setText(String.valueOf(mnultAktualni));
        }
    }

    private void pocitejPivka(Editable objemEditable) {
        String pivkoString = objemEditable.toString();

        if (pivkoString.isEmpty()) {
            pivkoString = "0";
        }

        pivkoAktualni = Double.parseDouble(pivkoString);

        // výpočet podle minut
        casAktualni = zpozdeniAktualni + pivkoAktualni * 38.756 / koeficientZnacky;
        dneskAktualni = casAktualni / 1440 * koeficientZnacky;
        pozdejcAktualni = casAktualni / 941.22 * koeficientZnacky;
        uzjduAktualni = casAktualni / 15 * koeficientZnacky;
        mnultAktualni = casAktualni / 2.34 * koeficientZnacky;

        // Zaokrouhlení na maximálně 6 desetinných míst
        dneskAktualni = Math.round(dneskAktualni * 1e6) / 1e6;
        pozdejcAktualni = Math.round(pozdejcAktualni * 1e6) / 1e6;
        casAktualni = Math.round(casAktualni * 1e6) / 1e6;
        uzjduAktualni = Math.round(uzjduAktualni * 1e6) / 1e6;
        mnultAktualni = Math.round(mnultAktualni * 1e6) / 1e6;

        // a nastavím jako text do polí
        if (dneskAktualni < 0.001) {
            dneskEditText.setText("0.0");
        } else {
            dneskEditText.setText(String.valueOf(dneskAktualni));
        }
        if (pozdejcAktualni < 0.001) {
            pozdejcEditText.setText("0.0");
        } else {
            pozdejcEditText.setText(String.valueOf(pozdejcAktualni));
        }
        if (casAktualni < 0.001) {
            casEditText.setText("0.0");
        } else {
            casEditText.setText(String.valueOf(casAktualni));
        }
        if (uzjduAktualni < 0.001) {
            uzjduEditText.setText("0.0");
        } else {
            uzjduEditText.setText(String.valueOf(uzjduAktualni));
        }
        if (mnultAktualni < 0.001) {
            mnultEditText.setText("0.0");
        } else {
            mnultEditText.setText(String.valueOf(mnultAktualni));
        }
    }

    private void pocitejUzjdu(Editable objemEditable) {
        String uzjduString = objemEditable.toString();

        if (uzjduString.isEmpty()) {
            uzjduString = "0";
        }

        uzjduAktualni = Double.parseDouble(uzjduString);

        // výpočet podle minut
        casAktualni = zpozdeniAktualni + uzjduAktualni * 15 / koeficientZnacky;
        dneskAktualni = casAktualni / 1440 * koeficientZnacky;
        pozdejcAktualni = casAktualni / 941.22 * koeficientZnacky;
        pivkoAktualni = casAktualni / 38.756 * koeficientZnacky;
        mnultAktualni = casAktualni / 2.34 * koeficientZnacky;

        // Zaokrouhlení na maximálně 6 desetinných míst
        dneskAktualni = Math.round(dneskAktualni * 1e6) / 1e6;
        pozdejcAktualni = Math.round(pozdejcAktualni * 1e6) / 1e6;
        pivkoAktualni = Math.round(pivkoAktualni * 1e6) / 1e6;
        casAktualni = Math.round(casAktualni * 1e6) / 1e6;
        mnultAktualni = Math.round(mnultAktualni * 1e6) / 1e6;

        // a nastavím jako text do polí
        if (dneskAktualni < 0.001) {
            dneskEditText.setText("0.0");
        } else {
            dneskEditText.setText(String.valueOf(dneskAktualni));
        }
        if (pozdejcAktualni < 0.001) {
            pozdejcEditText.setText("0.0");
        } else {
            pozdejcEditText.setText(String.valueOf(pozdejcAktualni));
        }
        if (pivkoAktualni < 0.001) {
            pivkoEditText.setText("0.0");
        } else {
            pivkoEditText.setText(String.valueOf(pivkoAktualni));
        }
        if (casAktualni < 0.001) {
            casEditText.setText("0.0");
        } else {
            casEditText.setText(String.valueOf(casAktualni));
        }
        if (mnultAktualni < 0.001) {
            mnultEditText.setText("0.0");
        } else {
            mnultEditText.setText(String.valueOf(mnultAktualni));
        }
    }

    private void pocitejMnulty(Editable objemEditable) {
        String mnultString = objemEditable.toString();

        if (mnultString.isEmpty()) {
            mnultString = "0";
        }

        mnultAktualni = Double.parseDouble(mnultString);

        // výpočet podle minut
        casAktualni = zpozdeniAktualni + mnultAktualni * 2.34 / koeficientZnacky;
        dneskAktualni = casAktualni / 1440 * koeficientZnacky;
        pozdejcAktualni = casAktualni / 941.22 * koeficientZnacky;
        pivkoAktualni = casAktualni / 38.756 * koeficientZnacky;
        uzjduAktualni = casAktualni / 15 * koeficientZnacky;

        // Zaokrouhlení na maximálně 6 desetinných míst
        dneskAktualni = Math.round(dneskAktualni * 1e6) / 1e6;
        pozdejcAktualni = Math.round(pozdejcAktualni * 1e6) / 1e6;
        pivkoAktualni = Math.round(pivkoAktualni * 1e6) / 1e6;
        uzjduAktualni = Math.round(uzjduAktualni * 1e6) / 1e6;
        casAktualni = Math.round(casAktualni * 1e6) / 1e6;

        // a nastavím jako text do polí
        if (dneskAktualni < 0.001) {
            dneskEditText.setText("0.0");
        } else {
            dneskEditText.setText(String.valueOf(dneskAktualni));
        }
        if (pozdejcAktualni < 0.001) {
            pozdejcEditText.setText("0.0");
        } else {
            pozdejcEditText.setText(String.valueOf(pozdejcAktualni));
        }
        if (pivkoAktualni < 0.001) {
            pivkoEditText.setText("0.0");
        } else {
            pivkoEditText.setText(String.valueOf(pivkoAktualni));
        }
        if (uzjduAktualni < 0.001) {
            uzjduEditText.setText("0.0");
        } else {
            uzjduEditText.setText(String.valueOf(uzjduAktualni));
        }
        if (casAktualni < 0.001) {
            casEditText.setText("0.0");
        } else {
            casEditText.setText(String.valueOf(casAktualni));
        }
    }

    private void pocitejZpozdeni(Editable editable) {
        String zpozdeniString = editable.toString();

        if (zpozdeniString.isEmpty()) {
            casAktualni = casAktualni - zpozdeniAktualni;
            zpozdeniString = "0";
        }

        zpozdeniAktualni = Double.parseDouble(zpozdeniString);

        // přičtení koeficientu zpoždění
        casAktualni = (zpozdeniAktualni * koeficientZnacky) + casAktualni;
        dneskAktualni = casAktualni / 1440 * koeficientZnacky;
        pozdejcAktualni = casAktualni / 941.22 * koeficientZnacky;
        pivkoAktualni = casAktualni / 38.756 * koeficientZnacky;
        uzjduAktualni = casAktualni / 15 * koeficientZnacky;
        mnultAktualni = casAktualni / 2.34 * koeficientZnacky;

        // Zaokrouhlení na maximálně 6 desetinných míst
        casAktualni = Math.round(casAktualni * 1e6) / 1e6;
        dneskAktualni = Math.round(dneskAktualni * 1e6) / 1e6;
        pozdejcAktualni = Math.round(pozdejcAktualni * 1e6) / 1e6;
        pivkoAktualni = Math.round(pivkoAktualni * 1e6) / 1e6;
        uzjduAktualni = Math.round(uzjduAktualni * 1e6) / 1e6;
        mnultAktualni = Math.round(mnultAktualni * 1e6) / 1e6;

        // a nastavím jako text do polí
        if (casAktualni < 0.001) {
            casEditText.setText("0.0");
        } else {
            casEditText.setText(String.valueOf(casAktualni));
        }
        if (dneskAktualni < 0.001) {
            dneskEditText.setText("0.0");
        } else {
            dneskEditText.setText(String.valueOf(dneskAktualni));
        }
        if (pozdejcAktualni < 0.001) {
            pozdejcEditText.setText("0.0");
        } else {
            pozdejcEditText.setText(String.valueOf(pozdejcAktualni));
        }
        if (pivkoAktualni < 0.001) {
            pivkoEditText.setText("0.0");
        } else {
            pivkoEditText.setText(String.valueOf(pivkoAktualni));
        }
        if (uzjduAktualni < 0.001) {
            uzjduEditText.setText("0.0");
        } else {
            uzjduEditText.setText(String.valueOf(uzjduAktualni));
        }
        if (mnultAktualni < 0.001) {
            mnultEditText.setText("0.0");
        } else {
            mnultEditText.setText(String.valueOf(mnultAktualni));
        }
    }
}
