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
import com.nyklundamade.pickulacka.enumTridy.VzdalenostEnum;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VzdalenostFragment extends Fragment {

    Spinner jednotkySpinner;
    EditText palceEditText, penisyEditText, schodyEditText, sloniEditText, fabieEditText, danEditText, stadionyEditText, glompyEditText, zemekouleEditText, geldolfyEditText, vzdalenostEditText;
    private double koeficientZnacky, palceAktualni, penisyAktualni, schodyAktualni, sloniAktualni, fabieAktualni, danAktualni, stadionyAktualni, glompyAktualni, zemekouleAktualni, geldolfyAktualni, vzdalenostAktualni;
    private VzdalenostEnum aktualniZnacka;
    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (palceEditText.isFocused()) {
                pocitejPalce(s);
            } else if (penisyEditText.isFocused()) {
                pocitejPenisy(s);
            } else if (schodyEditText.isFocused()) {
                pocitejSchody(s);
            } else if (sloniEditText.isFocused()) {
                pocitejSlony(s);
            } else if (fabieEditText.isFocused()) {
                pocitejFabie(s);
            } else if (danEditText.isFocused()) {
                pocitejDan(s);
            } else if (stadionyEditText.isFocused()) {
                pocitejStadiony(s);
            } else if (glompyEditText.isFocused()) {
                pocitejGlompy(s);
            } else if (zemekouleEditText.isFocused()) {
                pocitejZemekoule(s);
            } else if (geldolfyEditText.isFocused()) {
                pocitejGeldolfy(s);
            } else if (vzdalenostEditText.isFocused()) {
                pocitejVzdalenost(s);
            }

        }
    };

    private final View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                EditText editText = (EditText) v;

                palceEditText.removeTextChangedListener(textWatcher);
                penisyEditText.removeTextChangedListener(textWatcher);
                schodyEditText.removeTextChangedListener(textWatcher);
                sloniEditText.removeTextChangedListener(textWatcher);
                fabieEditText.removeTextChangedListener(textWatcher);
                danEditText.removeTextChangedListener(textWatcher);
                stadionyEditText.removeTextChangedListener(textWatcher);
                glompyEditText.removeTextChangedListener(textWatcher);
                zemekouleEditText.removeTextChangedListener(textWatcher);
                geldolfyEditText.removeTextChangedListener(textWatcher);
                vzdalenostEditText.removeTextChangedListener(textWatcher);

                editText.addTextChangedListener(textWatcher);
            }
        }
    };

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vzdalenost, container, false);

        koeficientZnacky = 1.0;

        palceAktualni = 0.0;
        penisyAktualni = 0.0;
        schodyAktualni = 0.0;
        sloniAktualni = 0.0;
        fabieAktualni = 0.0;
        danAktualni = 0.0;
        stadionyAktualni = 0.0;
        glompyAktualni = 0.0;
        zemekouleAktualni = 0.0;
        geldolfyAktualni = 0.0;
        vzdalenostAktualni = 0.0;

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

        vzdalenostEditText = view.findViewById(R.id.editTextVzdalenost);
        palceEditText = view.findViewById(R.id.editTextPalce);
        penisyEditText = view.findViewById(R.id.editTextPenisy);
        schodyEditText = view.findViewById(R.id.editTextSchody);
        sloniEditText = view.findViewById(R.id.editTextSloni);
        fabieEditText = view.findViewById(R.id.editTextFabie);
        danEditText = view.findViewById(R.id.editTextDan);
        stadionyEditText = view.findViewById(R.id.editTextStadiony);
        glompyEditText = view.findViewById(R.id.editTextGlompy);
        zemekouleEditText = view.findViewById(R.id.editTextZemekoule);
        geldolfyEditText = view.findViewById(R.id.editTextGeldolfy);

        vzdalenostEditText.setFilters(new InputFilter[]{decimalFilter});
        palceEditText.setFilters(new InputFilter[]{decimalFilter});
        penisyEditText.setFilters(new InputFilter[]{decimalFilter});
        schodyEditText.setFilters(new InputFilter[]{decimalFilter});
        sloniEditText.setFilters(new InputFilter[]{decimalFilter});
        fabieEditText.setFilters(new InputFilter[]{decimalFilter});
        danEditText.setFilters(new InputFilter[]{decimalFilter});
        stadionyEditText.setFilters(new InputFilter[]{decimalFilter});
        glompyEditText.setFilters(new InputFilter[]{decimalFilter});
        zemekouleEditText.setFilters(new InputFilter[]{decimalFilter});
        geldolfyEditText.setFilters(new InputFilter[]{decimalFilter});

        vzdalenostEditText.setOnFocusChangeListener(onFocusChangeListener);
        palceEditText.setOnFocusChangeListener(onFocusChangeListener);
        penisyEditText.setOnFocusChangeListener(onFocusChangeListener);
        schodyEditText.setOnFocusChangeListener(onFocusChangeListener);
        sloniEditText.setOnFocusChangeListener(onFocusChangeListener);
        fabieEditText.setOnFocusChangeListener(onFocusChangeListener);
        danEditText.setOnFocusChangeListener(onFocusChangeListener);
        stadionyEditText.setOnFocusChangeListener(onFocusChangeListener);
        glompyEditText.setOnFocusChangeListener(onFocusChangeListener);
        zemekouleEditText.setOnFocusChangeListener(onFocusChangeListener);
        geldolfyEditText.setOnFocusChangeListener(onFocusChangeListener);

        jednotkySpinner = view.findViewById(R.id.spinnerJendotkyVzdalenost);

        String[] znacky = new String[VzdalenostEnum.values().length];
        for (int i = 0; i < VzdalenostEnum.values().length; i++) {
            znacky[i] = VzdalenostEnum.values()[i].getZnacka();
        }

        ArrayAdapter<String> znackyAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, znacky);
        znackyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jednotkySpinner.setAdapter(znackyAdapter);
        jednotkySpinner.setSelection(3);

        aktualniZnacka = VzdalenostEnum.fromString((String) jednotkySpinner.getSelectedItem());

        jednotkySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                vzdalenostEditText.removeTextChangedListener(textWatcher);
                palceEditText.removeTextChangedListener(textWatcher);
                penisyEditText.removeTextChangedListener(textWatcher);
                schodyEditText.removeTextChangedListener(textWatcher);
                sloniEditText.removeTextChangedListener(textWatcher);
                fabieEditText.removeTextChangedListener(textWatcher);
                danEditText.removeTextChangedListener(textWatcher);
                stadionyEditText.removeTextChangedListener(textWatcher);
                glompyEditText.removeTextChangedListener(textWatcher);
                zemekouleEditText.removeTextChangedListener(textWatcher);
                geldolfyEditText.removeTextChangedListener(textWatcher);

                double nasobitel = getNasobitel();

                // nastavím nový koeficient podle nově zvolené značky
                switch (position) {
                    case 0: {
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
                    case 3: {
                        koeficientZnacky = 1.0;
                        break;
                    }
                    case 4: {
                        koeficientZnacky = 1000.0;
                        break;
                    }
                }

                // následně spočítám skutečnou hodnotu
                palceAktualni = palceAktualni * nasobitel * koeficientZnacky;
                penisyAktualni = penisyAktualni * nasobitel * koeficientZnacky;
                schodyAktualni = schodyAktualni * nasobitel * koeficientZnacky;
                sloniAktualni = sloniAktualni * nasobitel * koeficientZnacky;
                fabieAktualni = fabieAktualni * nasobitel * koeficientZnacky;
                danAktualni = danAktualni * nasobitel * koeficientZnacky;
                stadionyAktualni = stadionyAktualni * nasobitel * koeficientZnacky;
                glompyAktualni = glompyAktualni * nasobitel * koeficientZnacky;
                zemekouleAktualni = zemekouleAktualni * nasobitel * koeficientZnacky;
                geldolfyAktualni = geldolfyAktualni * nasobitel * koeficientZnacky;

                // Zaokrouhlení na maximálně 6 desetinných míst
                palceAktualni = Math.round(palceAktualni * 1e6) / 1e6;
                penisyAktualni = Math.round(penisyAktualni * 1e6) / 1e6;
                schodyAktualni = Math.round(schodyAktualni * 1e6) / 1e6;
                sloniAktualni = Math.round(sloniAktualni * 1e6) / 1e6;
                fabieAktualni = Math.round(fabieAktualni * 1e6) / 1e6;
                danAktualni = Math.round(danAktualni * 1e6) / 1e6;
                stadionyAktualni = Math.round(stadionyAktualni * 1e6) / 1e6;
                glompyAktualni = Math.round(glompyAktualni * 1e6) / 1e6;
                zemekouleAktualni = Math.round(zemekouleAktualni * 1e6) / 1e6;
                geldolfyAktualni = Math.round(geldolfyAktualni * 1e6) / 1e6;

                // a nastavím jako text do polí
                if (palceAktualni < 0.001) {
                    palceEditText.setText("0.0");
                } else {
                    palceEditText.setText(String.valueOf(palceAktualni));
                }
                if (penisyAktualni < 0.001) {
                    penisyEditText.setText("0.0");
                } else {
                    penisyEditText.setText(String.valueOf(penisyAktualni));
                }
                if (schodyAktualni < 0.001) {
                    schodyEditText.setText("0.0");
                } else {
                    schodyEditText.setText(String.valueOf(schodyAktualni));
                }
                if (sloniAktualni < 0.001) {
                    sloniEditText.setText("0.0");
                } else {
                    sloniEditText.setText(String.valueOf(sloniAktualni));
                }
                if (fabieAktualni < 0.001) {
                    fabieEditText.setText("0.0");
                } else {
                    fabieEditText.setText(String.valueOf(fabieAktualni));
                }
                if (danAktualni < 0.001) {
                    danEditText.setText("0.0");
                } else {
                    danEditText.setText(String.valueOf(danAktualni));
                }
                if (stadionyAktualni < 0.001) {
                    stadionyEditText.setText("0.0");
                } else {
                    stadionyEditText.setText(String.valueOf(stadionyAktualni));
                }
                if (glompyAktualni < 0.001) {
                    glompyEditText.setText("0.0");
                } else {
                    glompyEditText.setText(String.valueOf(glompyAktualni));
                }
                if (zemekouleAktualni < 0.001) {
                    zemekouleEditText.setText("0.0");
                } else {
                    zemekouleEditText.setText(String.valueOf(zemekouleAktualni));
                }
                if (geldolfyAktualni < 0.001) {
                    geldolfyEditText.setText("0.0");
                } else {
                    geldolfyEditText.setText(String.valueOf(geldolfyAktualni));
                }

                // nastavím novou aktuální značku
                aktualniZnacka = VzdalenostEnum.fromString((String) jednotkySpinner.getSelectedItem());

                // nastavím listenera na editText zpět
                if (vzdalenostEditText.isFocused()) {
                    vzdalenostEditText.addTextChangedListener(textWatcher);
                } else if (palceEditText.isFocused()) {
                    palceEditText.addTextChangedListener(textWatcher);
                } else if (penisyEditText.isFocused()) {
                    penisyEditText.addTextChangedListener(textWatcher);
                } else if (schodyEditText.isFocused()) {
                    schodyEditText.addTextChangedListener(textWatcher);
                } else if (sloniEditText.isFocused()) {
                    sloniEditText.addTextChangedListener(textWatcher);
                } else if (fabieEditText.isFocused()) {
                    fabieEditText.addTextChangedListener(textWatcher);
                } else if (danEditText.isFocused()) {
                    danEditText.addTextChangedListener(textWatcher);
                } else if (stadionyEditText.isFocused()) {
                    stadionyEditText.addTextChangedListener(textWatcher);
                } else if (glompyEditText.isFocused()) {
                    glompyEditText.addTextChangedListener(textWatcher);
                } else if (zemekouleEditText.isFocused()) {
                    zemekouleEditText.addTextChangedListener(textWatcher);
                } else if (geldolfyEditText.isFocused()) {
                    geldolfyEditText.addTextChangedListener(textWatcher);
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
        if (aktualniZnacka.equals(VzdalenostEnum.MILIMETR)) {
            nasobitel = 1000;
        } else if (aktualniZnacka.equals(VzdalenostEnum.CENTIMETR)) {
            nasobitel = 100;
        } else if (aktualniZnacka.equals(VzdalenostEnum.DECIMETR)) {
            nasobitel = 10;
        } else if (aktualniZnacka.equals(VzdalenostEnum.KILOMETR)) {
            nasobitel = 0.001;
        }
        return nasobitel;
    }

    private void pocitejVzdalenost(Editable objemEditable) {
        String vzdalenostString = objemEditable.toString();

        if (vzdalenostString.isEmpty()) {
            vzdalenostString = "0";
        }

        vzdalenostAktualni = Double.parseDouble(vzdalenostString);

        // výpočet podle metrů
        palceAktualni = vzdalenostAktualni / 0.0254 * koeficientZnacky;
        penisyAktualni = vzdalenostAktualni / 0.1312 * koeficientZnacky;
        schodyAktualni = vzdalenostAktualni / 0.160036 * koeficientZnacky;
        sloniAktualni = vzdalenostAktualni / 3.4909 * koeficientZnacky;
        fabieAktualni = vzdalenostAktualni / 3.96 * koeficientZnacky;
        danAktualni = vzdalenostAktualni / 19.684 * koeficientZnacky;
        stadionyAktualni = vzdalenostAktualni / 101.498 * koeficientZnacky;
        glompyAktualni = vzdalenostAktualni / 1201120.1 * koeficientZnacky;
        zemekouleAktualni = vzdalenostAktualni / 40075000 * koeficientZnacky;
        geldolfyAktualni = vzdalenostAktualni / (1201120.1 * 1000000) * koeficientZnacky;

        // Zaokrouhlení na maximálně 6 desetinných míst
        palceAktualni = Math.round(palceAktualni * 1e6) / 1e6;
        penisyAktualni = Math.round(penisyAktualni * 1e6) / 1e6;
        schodyAktualni = Math.round(schodyAktualni * 1e6) / 1e6;
        sloniAktualni = Math.round(sloniAktualni * 1e6) / 1e6;
        fabieAktualni = Math.round(fabieAktualni * 1e6) / 1e6;
        danAktualni = Math.round(danAktualni * 1e6) / 1e6;
        stadionyAktualni = Math.round(stadionyAktualni * 1e6) / 1e6;
        glompyAktualni = Math.round(glompyAktualni * 1e6) / 1e6;
        zemekouleAktualni = Math.round(zemekouleAktualni * 1e6) / 1e6;
        geldolfyAktualni = Math.round(geldolfyAktualni * 1e6) / 1e6;

        // a nastavím jako text do polí
        if (palceAktualni < 0.001) {
            palceEditText.setText("0.0");
        } else {
            palceEditText.setText(String.valueOf(palceAktualni));
        }
        if (penisyAktualni < 0.001) {
            penisyEditText.setText("0.0");
        } else {
            penisyEditText.setText(String.valueOf(penisyAktualni));
        }
        if (schodyAktualni < 0.001) {
            schodyEditText.setText("0.0");
        } else {
            schodyEditText.setText(String.valueOf(schodyAktualni));
        }
        if (sloniAktualni < 0.001) {
            sloniEditText.setText("0.0");
        } else {
            sloniEditText.setText(String.valueOf(sloniAktualni));
        }
        if (fabieAktualni < 0.001) {
            fabieEditText.setText("0.0");
        } else {
            fabieEditText.setText(String.valueOf(fabieAktualni));
        }
        if (danAktualni < 0.001) {
            danEditText.setText("0.0");
        } else {
            danEditText.setText(String.valueOf(danAktualni));
        }
        if (stadionyAktualni < 0.001) {
            stadionyEditText.setText("0.0");
        } else {
            stadionyEditText.setText(String.valueOf(stadionyAktualni));
        }
        if (glompyAktualni < 0.001) {
            glompyEditText.setText("0.0");
        } else {
            glompyEditText.setText(String.valueOf(glompyAktualni));
        }
        if (zemekouleAktualni < 0.001) {
            zemekouleEditText.setText("0.0");
        } else {
            zemekouleEditText.setText(String.valueOf(zemekouleAktualni));
        }
        if (geldolfyAktualni < 0.001) {
            geldolfyEditText.setText("0.0");
        } else {
            geldolfyEditText.setText(String.valueOf(geldolfyAktualni));
        }
    }

    private void pocitejPalce(Editable objemEditable) {
        String palceString = objemEditable.toString();

        if (palceString.isEmpty()) {
            palceString = "0";
        }

        palceAktualni = Double.parseDouble(palceString);

        // výpočet podle metrů
        vzdalenostAktualni = palceAktualni * 0.0254 / koeficientZnacky;
        penisyAktualni = vzdalenostAktualni / 0.1312 * koeficientZnacky;
        schodyAktualni = vzdalenostAktualni / 0.160036 * koeficientZnacky;
        sloniAktualni = vzdalenostAktualni / 3.4909 * koeficientZnacky;
        fabieAktualni = vzdalenostAktualni / 3.96 * koeficientZnacky;
        danAktualni = vzdalenostAktualni / 19.684 * koeficientZnacky;
        stadionyAktualni = vzdalenostAktualni / 101.498 * koeficientZnacky;
        glompyAktualni = vzdalenostAktualni / 1201120.1 * koeficientZnacky;
        zemekouleAktualni = vzdalenostAktualni / 40075000 * koeficientZnacky;
        geldolfyAktualni = vzdalenostAktualni / (1201120.1 * 1000000) * koeficientZnacky;

        // Zaokrouhlení na maximálně 6 desetinných míst
        vzdalenostAktualni = Math.round(vzdalenostAktualni * 1e6) / 1e6;
        penisyAktualni = Math.round(penisyAktualni * 1e6) / 1e6;
        schodyAktualni = Math.round(schodyAktualni * 1e6) / 1e6;
        sloniAktualni = Math.round(sloniAktualni * 1e6) / 1e6;
        fabieAktualni = Math.round(fabieAktualni * 1e6) / 1e6;
        danAktualni = Math.round(danAktualni * 1e6) / 1e6;
        stadionyAktualni = Math.round(stadionyAktualni * 1e6) / 1e6;
        glompyAktualni = Math.round(glompyAktualni * 1e6) / 1e6;
        zemekouleAktualni = Math.round(zemekouleAktualni * 1e6) / 1e6;
        geldolfyAktualni = Math.round(geldolfyAktualni * 1e6) / 1e6;

        // a nastavím jako text do polí
        if (vzdalenostAktualni < 0.001) {
            vzdalenostEditText.setText("0.0");
        } else {
            vzdalenostEditText.setText(String.valueOf(vzdalenostAktualni));
        }
        if (penisyAktualni < 0.001) {
            penisyEditText.setText("0.0");
        } else {
            penisyEditText.setText(String.valueOf(penisyAktualni));
        }
        if (schodyAktualni < 0.001) {
            schodyEditText.setText("0.0");
        } else {
            schodyEditText.setText(String.valueOf(schodyAktualni));
        }
        if (sloniAktualni < 0.001) {
            sloniEditText.setText("0.0");
        } else {
            sloniEditText.setText(String.valueOf(sloniAktualni));
        }
        if (fabieAktualni < 0.001) {
            fabieEditText.setText("0.0");
        } else {
            fabieEditText.setText(String.valueOf(fabieAktualni));
        }
        if (danAktualni < 0.001) {
            danEditText.setText("0.0");
        } else {
            danEditText.setText(String.valueOf(danAktualni));
        }
        if (stadionyAktualni < 0.001) {
            stadionyEditText.setText("0.0");
        } else {
            stadionyEditText.setText(String.valueOf(stadionyAktualni));
        }
        if (glompyAktualni < 0.001) {
            glompyEditText.setText("0.0");
        } else {
            glompyEditText.setText(String.valueOf(glompyAktualni));
        }
        if (zemekouleAktualni < 0.001) {
            zemekouleEditText.setText("0.0");
        } else {
            zemekouleEditText.setText(String.valueOf(zemekouleAktualni));
        }
        if (geldolfyAktualni < 0.001) {
            geldolfyEditText.setText("0.0");
        } else {
            geldolfyEditText.setText(String.valueOf(geldolfyAktualni));
        }
    }

    private void pocitejPenisy(Editable objemEditable) {
        String penisString = objemEditable.toString();

        if (penisString.isEmpty()) {
            penisString = "0";
        }

        penisyAktualni = Double.parseDouble(penisString);

        // výpočet podle metrů
        vzdalenostAktualni = penisyAktualni * 0.1312 / koeficientZnacky;
        palceAktualni = vzdalenostAktualni / 0.0254 * koeficientZnacky;
        schodyAktualni = vzdalenostAktualni / 0.160036 * koeficientZnacky;
        sloniAktualni = vzdalenostAktualni / 3.4909 * koeficientZnacky;
        fabieAktualni = vzdalenostAktualni / 3.96 * koeficientZnacky;
        danAktualni = vzdalenostAktualni / 19.684 * koeficientZnacky;
        stadionyAktualni = vzdalenostAktualni / 101.498 * koeficientZnacky;
        glompyAktualni = vzdalenostAktualni / 1201120.1 * koeficientZnacky;
        zemekouleAktualni = vzdalenostAktualni / 40075000 * koeficientZnacky;
        geldolfyAktualni = vzdalenostAktualni / (1201120.1 * 1000000) * koeficientZnacky;

        // Zaokrouhlení na maximálně 6 desetinných míst
        palceAktualni = Math.round(palceAktualni * 1e6) / 1e6;
        vzdalenostAktualni = Math.round(vzdalenostAktualni * 1e6) / 1e6;
        schodyAktualni = Math.round(schodyAktualni * 1e6) / 1e6;
        sloniAktualni = Math.round(sloniAktualni * 1e6) / 1e6;
        fabieAktualni = Math.round(fabieAktualni * 1e6) / 1e6;
        danAktualni = Math.round(danAktualni * 1e6) / 1e6;
        stadionyAktualni = Math.round(stadionyAktualni * 1e6) / 1e6;
        glompyAktualni = Math.round(glompyAktualni * 1e6) / 1e6;
        zemekouleAktualni = Math.round(zemekouleAktualni * 1e6) / 1e6;
        geldolfyAktualni = Math.round(geldolfyAktualni * 1e6) / 1e6;

        // a nastavím jako text do polí
        if (palceAktualni < 0.001) {
            palceEditText.setText("0.0");
        } else {
            palceEditText.setText(String.valueOf(palceAktualni));
        }
        if (penisyAktualni < 0.001) {
            penisyEditText.setText("0.0");
        } else {
            penisyEditText.setText(String.valueOf(penisyAktualni));
        }
        if (schodyAktualni < 0.001) {
            schodyEditText.setText("0.0");
        } else {
            schodyEditText.setText(String.valueOf(schodyAktualni));
        }
        if (sloniAktualni < 0.001) {
            sloniEditText.setText("0.0");
        } else {
            sloniEditText.setText(String.valueOf(sloniAktualni));
        }
        if (fabieAktualni < 0.001) {
            fabieEditText.setText("0.0");
        } else {
            fabieEditText.setText(String.valueOf(fabieAktualni));
        }
        if (danAktualni < 0.001) {
            danEditText.setText("0.0");
        } else {
            danEditText.setText(String.valueOf(danAktualni));
        }
        if (stadionyAktualni < 0.001) {
            stadionyEditText.setText("0.0");
        } else {
            stadionyEditText.setText(String.valueOf(stadionyAktualni));
        }
        if (glompyAktualni < 0.001) {
            glompyEditText.setText("0.0");
        } else {
            glompyEditText.setText(String.valueOf(glompyAktualni));
        }
        if (zemekouleAktualni < 0.001) {
            zemekouleEditText.setText("0.0");
        } else {
            zemekouleEditText.setText(String.valueOf(zemekouleAktualni));
        }
        if (geldolfyAktualni < 0.001) {
            geldolfyEditText.setText("0.0");
        } else {
            geldolfyEditText.setText(String.valueOf(geldolfyAktualni));
        }
    }

    private void pocitejSchody(Editable objemEditable) {
        String schodString = objemEditable.toString();

        if (schodString.isEmpty()) {
            schodString = "0";
        }

        schodyAktualni = Double.parseDouble(schodString);

        // výpočet podle metrů
        vzdalenostAktualni = schodyAktualni * 0.160036 / koeficientZnacky;
        palceAktualni = vzdalenostAktualni / 0.0254 * koeficientZnacky;
        penisyAktualni = vzdalenostAktualni / 0.1312 * koeficientZnacky;
        sloniAktualni = vzdalenostAktualni / 3.4909 * koeficientZnacky;
        fabieAktualni = vzdalenostAktualni / 3.96 * koeficientZnacky;
        danAktualni = vzdalenostAktualni / 19.684 * koeficientZnacky;
        stadionyAktualni = vzdalenostAktualni / 101.498 * koeficientZnacky;
        glompyAktualni = vzdalenostAktualni / 1201120.1 * koeficientZnacky;
        zemekouleAktualni = vzdalenostAktualni / 40075000 * koeficientZnacky;
        geldolfyAktualni = vzdalenostAktualni / (1201120.1 * 1000000) * koeficientZnacky;

        // Zaokrouhlení na maximálně 6 desetinných míst
        palceAktualni = Math.round(palceAktualni * 1e6) / 1e6;
        penisyAktualni = Math.round(penisyAktualni * 1e6) / 1e6;
        vzdalenostAktualni = Math.round(vzdalenostAktualni * 1e6) / 1e6;
        sloniAktualni = Math.round(sloniAktualni * 1e6) / 1e6;
        fabieAktualni = Math.round(fabieAktualni * 1e6) / 1e6;
        danAktualni = Math.round(danAktualni * 1e6) / 1e6;
        stadionyAktualni = Math.round(stadionyAktualni * 1e6) / 1e6;
        glompyAktualni = Math.round(glompyAktualni * 1e6) / 1e6;
        zemekouleAktualni = Math.round(zemekouleAktualni * 1e6) / 1e6;
        geldolfyAktualni = Math.round(geldolfyAktualni * 1e6) / 1e6;

        // a nastavím jako text do polí
        if (palceAktualni < 0.001) {
            palceEditText.setText("0.0");
        } else {
            palceEditText.setText(String.valueOf(palceAktualni));
        }
        if (penisyAktualni < 0.001) {
            penisyEditText.setText("0.0");
        } else {
            penisyEditText.setText(String.valueOf(penisyAktualni));
        }
        if (vzdalenostAktualni < 0.001) {
            vzdalenostEditText.setText("0.0");
        } else {
            vzdalenostEditText.setText(String.valueOf(vzdalenostAktualni));
        }
        if (sloniAktualni < 0.001) {
            sloniEditText.setText("0.0");
        } else {
            sloniEditText.setText(String.valueOf(sloniAktualni));
        }
        if (fabieAktualni < 0.001) {
            fabieEditText.setText("0.0");
        } else {
            fabieEditText.setText(String.valueOf(fabieAktualni));
        }
        if (danAktualni < 0.001) {
            danEditText.setText("0.0");
        } else {
            danEditText.setText(String.valueOf(danAktualni));
        }
        if (stadionyAktualni < 0.001) {
            stadionyEditText.setText("0.0");
        } else {
            stadionyEditText.setText(String.valueOf(stadionyAktualni));
        }
        if (glompyAktualni < 0.001) {
            glompyEditText.setText("0.0");
        } else {
            glompyEditText.setText(String.valueOf(glompyAktualni));
        }
        if (zemekouleAktualni < 0.001) {
            zemekouleEditText.setText("0.0");
        } else {
            zemekouleEditText.setText(String.valueOf(zemekouleAktualni));
        }
        if (geldolfyAktualni < 0.001) {
            geldolfyEditText.setText("0.0");
        } else {
            geldolfyEditText.setText(String.valueOf(geldolfyAktualni));
        }
    }

    private void pocitejSlony(Editable objemEditable) {
        String sloniString = objemEditable.toString();

        if (sloniString.isEmpty()) {
            sloniString = "0";
        }

        sloniAktualni = Double.parseDouble(sloniString);

        // výpočet podle metrů
        vzdalenostAktualni = sloniAktualni * 3.4909 / koeficientZnacky;
        palceAktualni = vzdalenostAktualni / 0.0254 * koeficientZnacky;
        penisyAktualni = vzdalenostAktualni / 0.1312 * koeficientZnacky;
        schodyAktualni = vzdalenostAktualni / 0.160036 * koeficientZnacky;
        fabieAktualni = vzdalenostAktualni / 3.96 * koeficientZnacky;
        danAktualni = vzdalenostAktualni / 19.684 * koeficientZnacky;
        stadionyAktualni = vzdalenostAktualni / 101.498 * koeficientZnacky;
        glompyAktualni = vzdalenostAktualni / 1201120.1 * koeficientZnacky;
        zemekouleAktualni = vzdalenostAktualni / 40075000 * koeficientZnacky;
        geldolfyAktualni = vzdalenostAktualni / (1201120.1 * 1000000) * koeficientZnacky;

        // Zaokrouhlení na maximálně 6 desetinných míst
        palceAktualni = Math.round(palceAktualni * 1e6) / 1e6;
        penisyAktualni = Math.round(penisyAktualni * 1e6) / 1e6;
        schodyAktualni = Math.round(schodyAktualni * 1e6) / 1e6;
        vzdalenostAktualni = Math.round(vzdalenostAktualni * 1e6) / 1e6;
        fabieAktualni = Math.round(fabieAktualni * 1e6) / 1e6;
        danAktualni = Math.round(danAktualni * 1e6) / 1e6;
        stadionyAktualni = Math.round(stadionyAktualni * 1e6) / 1e6;
        glompyAktualni = Math.round(glompyAktualni * 1e6) / 1e6;
        zemekouleAktualni = Math.round(zemekouleAktualni * 1e6) / 1e6;
        geldolfyAktualni = Math.round(geldolfyAktualni * 1e6) / 1e6;

        // a nastavím jako text do polí
        if (palceAktualni < 0.001) {
            palceEditText.setText("0.0");
        } else {
            palceEditText.setText(String.valueOf(palceAktualni));
        }
        if (penisyAktualni < 0.001) {
            penisyEditText.setText("0.0");
        } else {
            penisyEditText.setText(String.valueOf(penisyAktualni));
        }
        if (schodyAktualni < 0.001) {
            schodyEditText.setText("0.0");
        } else {
            schodyEditText.setText(String.valueOf(schodyAktualni));
        }
        if (vzdalenostAktualni < 0.001) {
            vzdalenostEditText.setText("0.0");
        } else {
            vzdalenostEditText.setText(String.valueOf(vzdalenostAktualni));
        }
        if (fabieAktualni < 0.001) {
            fabieEditText.setText("0.0");
        } else {
            fabieEditText.setText(String.valueOf(fabieAktualni));
        }
        if (danAktualni < 0.001) {
            danEditText.setText("0.0");
        } else {
            danEditText.setText(String.valueOf(danAktualni));
        }
        if (stadionyAktualni < 0.001) {
            stadionyEditText.setText("0.0");
        } else {
            stadionyEditText.setText(String.valueOf(stadionyAktualni));
        }
        if (glompyAktualni < 0.001) {
            glompyEditText.setText("0.0");
        } else {
            glompyEditText.setText(String.valueOf(glompyAktualni));
        }
        if (zemekouleAktualni < 0.001) {
            zemekouleEditText.setText("0.0");
        } else {
            zemekouleEditText.setText(String.valueOf(zemekouleAktualni));
        }
        if (geldolfyAktualni < 0.001) {
            geldolfyEditText.setText("0.0");
        } else {
            geldolfyEditText.setText(String.valueOf(geldolfyAktualni));
        }
    }

    private void pocitejFabie(Editable objemEditable) {
        String fabieString = objemEditable.toString();

        if (fabieString.isEmpty()) {
            fabieString = "0";
        }

        fabieAktualni = Double.parseDouble(fabieString);

        // výpočet podle metrů
        vzdalenostAktualni = fabieAktualni * 3.96 / koeficientZnacky;
        palceAktualni = vzdalenostAktualni / 0.0254 * koeficientZnacky;
        penisyAktualni = vzdalenostAktualni / 0.1312 * koeficientZnacky;
        schodyAktualni = vzdalenostAktualni / 0.160036 * koeficientZnacky;
        sloniAktualni = vzdalenostAktualni / 3.4909 * koeficientZnacky;
        danAktualni = vzdalenostAktualni / 19.684 * koeficientZnacky;
        stadionyAktualni = vzdalenostAktualni / 101.498 * koeficientZnacky;
        glompyAktualni = vzdalenostAktualni / 1201120.1 * koeficientZnacky;
        zemekouleAktualni = vzdalenostAktualni / 40075000 * koeficientZnacky;
        geldolfyAktualni = vzdalenostAktualni / (1201120.1 * 1000000) * koeficientZnacky;

        // Zaokrouhlení na maximálně 6 desetinných míst
        palceAktualni = Math.round(palceAktualni * 1e6) / 1e6;
        penisyAktualni = Math.round(penisyAktualni * 1e6) / 1e6;
        schodyAktualni = Math.round(schodyAktualni * 1e6) / 1e6;
        sloniAktualni = Math.round(sloniAktualni * 1e6) / 1e6;
        vzdalenostAktualni = Math.round(vzdalenostAktualni * 1e6) / 1e6;
        danAktualni = Math.round(danAktualni * 1e6) / 1e6;
        stadionyAktualni = Math.round(stadionyAktualni * 1e6) / 1e6;
        glompyAktualni = Math.round(glompyAktualni * 1e6) / 1e6;
        zemekouleAktualni = Math.round(zemekouleAktualni * 1e6) / 1e6;
        geldolfyAktualni = Math.round(geldolfyAktualni * 1e6) / 1e6;

        // a nastavím jako text do polí
        if (palceAktualni < 0.001) {
            palceEditText.setText("0.0");
        } else {
            palceEditText.setText(String.valueOf(palceAktualni));
        }
        if (penisyAktualni < 0.001) {
            penisyEditText.setText("0.0");
        } else {
            penisyEditText.setText(String.valueOf(penisyAktualni));
        }
        if (schodyAktualni < 0.001) {
            schodyEditText.setText("0.0");
        } else {
            schodyEditText.setText(String.valueOf(schodyAktualni));
        }
        if (sloniAktualni < 0.001) {
            sloniEditText.setText("0.0");
        } else {
            sloniEditText.setText(String.valueOf(sloniAktualni));
        }
        if (vzdalenostAktualni < 0.001) {
            vzdalenostEditText.setText("0.0");
        } else {
            vzdalenostEditText.setText(String.valueOf(vzdalenostAktualni));
        }
        if (danAktualni < 0.001) {
            danEditText.setText("0.0");
        } else {
            danEditText.setText(String.valueOf(danAktualni));
        }
        if (stadionyAktualni < 0.001) {
            stadionyEditText.setText("0.0");
        } else {
            stadionyEditText.setText(String.valueOf(stadionyAktualni));
        }
        if (glompyAktualni < 0.001) {
            glompyEditText.setText("0.0");
        } else {
            glompyEditText.setText(String.valueOf(glompyAktualni));
        }
        if (zemekouleAktualni < 0.001) {
            zemekouleEditText.setText("0.0");
        } else {
            zemekouleEditText.setText(String.valueOf(zemekouleAktualni));
        }
        if (geldolfyAktualni < 0.001) {
            geldolfyEditText.setText("0.0");
        } else {
            geldolfyEditText.setText(String.valueOf(geldolfyAktualni));
        }
    }

    private void pocitejDan(Editable objemEditable) {
        String danString = objemEditable.toString();

        if (danString.isEmpty()) {
            danString = "0";
        }

        danAktualni = Double.parseDouble(danString);

        // výpočet podle litrů
        vzdalenostAktualni = danAktualni * 19.684 / koeficientZnacky;
        palceAktualni = vzdalenostAktualni / 0.0254 * koeficientZnacky;
        penisyAktualni = vzdalenostAktualni / 0.1312 * koeficientZnacky;
        schodyAktualni = vzdalenostAktualni / 0.160036 * koeficientZnacky;
        sloniAktualni = vzdalenostAktualni / 3.4909 * koeficientZnacky;
        fabieAktualni = vzdalenostAktualni / 3.96 * koeficientZnacky;
        stadionyAktualni = vzdalenostAktualni / 101.498 * koeficientZnacky;
        glompyAktualni = vzdalenostAktualni / 1201120.1 * koeficientZnacky;
        zemekouleAktualni = vzdalenostAktualni / 40075000 * koeficientZnacky;
        geldolfyAktualni = vzdalenostAktualni / (1201120.1 * 1000000) * koeficientZnacky;

        // Zaokrouhlení na maximálně 6 desetinných míst
        palceAktualni = Math.round(palceAktualni * 1e6) / 1e6;
        penisyAktualni = Math.round(penisyAktualni * 1e6) / 1e6;
        schodyAktualni = Math.round(schodyAktualni * 1e6) / 1e6;
        sloniAktualni = Math.round(sloniAktualni * 1e6) / 1e6;
        fabieAktualni = Math.round(fabieAktualni * 1e6) / 1e6;
        vzdalenostAktualni = Math.round(vzdalenostAktualni * 1e6) / 1e6;
        stadionyAktualni = Math.round(stadionyAktualni * 1e6) / 1e6;
        glompyAktualni = Math.round(glompyAktualni * 1e6) / 1e6;
        zemekouleAktualni = Math.round(zemekouleAktualni * 1e6) / 1e6;
        geldolfyAktualni = Math.round(geldolfyAktualni * 1e6) / 1e6;

        // a nastavím jako text do polí
        if (palceAktualni < 0.001) {
            palceEditText.setText("0.0");
        } else {
            palceEditText.setText(String.valueOf(palceAktualni));
        }
        if (penisyAktualni < 0.001) {
            penisyEditText.setText("0.0");
        } else {
            penisyEditText.setText(String.valueOf(penisyAktualni));
        }
        if (schodyAktualni < 0.001) {
            schodyEditText.setText("0.0");
        } else {
            schodyEditText.setText(String.valueOf(schodyAktualni));
        }
        if (sloniAktualni < 0.001) {
            sloniEditText.setText("0.0");
        } else {
            sloniEditText.setText(String.valueOf(sloniAktualni));
        }
        if (fabieAktualni < 0.001) {
            fabieEditText.setText("0.0");
        } else {
            fabieEditText.setText(String.valueOf(fabieAktualni));
        }
        if (vzdalenostAktualni < 0.001) {
            vzdalenostEditText.setText("0.0");
        } else {
            vzdalenostEditText.setText(String.valueOf(vzdalenostAktualni));
        }
        if (stadionyAktualni < 0.001) {
            stadionyEditText.setText("0.0");
        } else {
            stadionyEditText.setText(String.valueOf(stadionyAktualni));
        }
        if (glompyAktualni < 0.001) {
            glompyEditText.setText("0.0");
        } else {
            glompyEditText.setText(String.valueOf(glompyAktualni));
        }
        if (zemekouleAktualni < 0.001) {
            zemekouleEditText.setText("0.0");
        } else {
            zemekouleEditText.setText(String.valueOf(zemekouleAktualni));
        }
        if (geldolfyAktualni < 0.001) {
            geldolfyEditText.setText("0.0");
        } else {
            geldolfyEditText.setText(String.valueOf(geldolfyAktualni));
        }
    }

    private void pocitejStadiony(Editable objemEditable) {
        String stadionString = objemEditable.toString();

        if (stadionString.isEmpty()) {
            stadionString = "0";
        }

        stadionyAktualni = Double.parseDouble(stadionString);

        // výpočet podle litrů
        vzdalenostAktualni = stadionyAktualni * 101.498 / koeficientZnacky;
        palceAktualni = vzdalenostAktualni / 0.0254 * koeficientZnacky;
        penisyAktualni = vzdalenostAktualni / 0.1312 * koeficientZnacky;
        schodyAktualni = vzdalenostAktualni / 0.160036 * koeficientZnacky;
        sloniAktualni = vzdalenostAktualni / 3.4909 * koeficientZnacky;
        fabieAktualni = vzdalenostAktualni / 3.96 * koeficientZnacky;
        danAktualni = vzdalenostAktualni / 19.684 * koeficientZnacky;
        glompyAktualni = vzdalenostAktualni / 1201120.1 * koeficientZnacky;
        zemekouleAktualni = vzdalenostAktualni / 40075000 * koeficientZnacky;
        geldolfyAktualni = vzdalenostAktualni / (1201120.1 * 1000000) * koeficientZnacky;

        // Zaokrouhlení na maximálně 6 desetinných míst
        palceAktualni = Math.round(palceAktualni * 1e6) / 1e6;
        penisyAktualni = Math.round(penisyAktualni * 1e6) / 1e6;
        schodyAktualni = Math.round(schodyAktualni * 1e6) / 1e6;
        sloniAktualni = Math.round(sloniAktualni * 1e6) / 1e6;
        fabieAktualni = Math.round(fabieAktualni * 1e6) / 1e6;
        danAktualni = Math.round(danAktualni * 1e6) / 1e6;
        vzdalenostAktualni = Math.round(vzdalenostAktualni * 1e6) / 1e6;
        glompyAktualni = Math.round(glompyAktualni * 1e6) / 1e6;
        zemekouleAktualni = Math.round(zemekouleAktualni * 1e6) / 1e6;
        geldolfyAktualni = Math.round(geldolfyAktualni * 1e6) / 1e6;

        // a nastavím jako text do polí
        if (palceAktualni < 0.001) {
            palceEditText.setText("0.0");
        } else {
            palceEditText.setText(String.valueOf(palceAktualni));
        }
        if (penisyAktualni < 0.001) {
            penisyEditText.setText("0.0");
        } else {
            penisyEditText.setText(String.valueOf(penisyAktualni));
        }
        if (schodyAktualni < 0.001) {
            schodyEditText.setText("0.0");
        } else {
            schodyEditText.setText(String.valueOf(schodyAktualni));
        }
        if (sloniAktualni < 0.001) {
            sloniEditText.setText("0.0");
        } else {
            sloniEditText.setText(String.valueOf(sloniAktualni));
        }
        if (fabieAktualni < 0.001) {
            fabieEditText.setText("0.0");
        } else {
            fabieEditText.setText(String.valueOf(fabieAktualni));
        }
        if (danAktualni < 0.001) {
            danEditText.setText("0.0");
        } else {
            danEditText.setText(String.valueOf(danAktualni));
        }
        if (vzdalenostAktualni < 0.001) {
            vzdalenostEditText.setText("0.0");
        } else {
            vzdalenostEditText.setText(String.valueOf(vzdalenostAktualni));
        }
        if (glompyAktualni < 0.001) {
            glompyEditText.setText("0.0");
        } else {
            glompyEditText.setText(String.valueOf(glompyAktualni));
        }
        if (zemekouleAktualni < 0.001) {
            zemekouleEditText.setText("0.0");
        } else {
            zemekouleEditText.setText(String.valueOf(zemekouleAktualni));
        }
        if (geldolfyAktualni < 0.001) {
            geldolfyEditText.setText("0.0");
        } else {
            geldolfyEditText.setText(String.valueOf(geldolfyAktualni));
        }
    }

    private void pocitejGlompy(Editable objemEditable) {
        String glompString = objemEditable.toString();

        if (glompString.isEmpty()) {
            glompString = "0";
        }

        glompyAktualni = Double.parseDouble(glompString);

        // výpočet podle litrů
        vzdalenostAktualni = glompyAktualni * 1201120.1 / koeficientZnacky;
        palceAktualni = vzdalenostAktualni / 0.0254 * koeficientZnacky;
        penisyAktualni = vzdalenostAktualni / 0.1312 * koeficientZnacky;
        schodyAktualni = vzdalenostAktualni / 0.160036 * koeficientZnacky;
        sloniAktualni = vzdalenostAktualni / 3.4909 * koeficientZnacky;
        fabieAktualni = vzdalenostAktualni / 3.96 * koeficientZnacky;
        danAktualni = vzdalenostAktualni / 19.684 * koeficientZnacky;
        stadionyAktualni = vzdalenostAktualni / 101.498 * koeficientZnacky;
        zemekouleAktualni = vzdalenostAktualni / 40075000 * koeficientZnacky;
        geldolfyAktualni = vzdalenostAktualni / (1201120.1 * 1000000) * koeficientZnacky;

        // Zaokrouhlení na maximálně 6 desetinných míst
        palceAktualni = Math.round(palceAktualni * 1e6) / 1e6;
        penisyAktualni = Math.round(penisyAktualni * 1e6) / 1e6;
        schodyAktualni = Math.round(schodyAktualni * 1e6) / 1e6;
        sloniAktualni = Math.round(sloniAktualni * 1e6) / 1e6;
        fabieAktualni = Math.round(fabieAktualni * 1e6) / 1e6;
        danAktualni = Math.round(danAktualni * 1e6) / 1e6;
        stadionyAktualni = Math.round(stadionyAktualni * 1e6) / 1e6;
        vzdalenostAktualni = Math.round(vzdalenostAktualni * 1e6) / 1e6;
        zemekouleAktualni = Math.round(zemekouleAktualni * 1e6) / 1e6;
        geldolfyAktualni = Math.round(geldolfyAktualni * 1e6) / 1e6;

        // a nastavím jako text do polí
        if (palceAktualni < 0.001) {
            palceEditText.setText("0.0");
        } else {
            palceEditText.setText(String.valueOf(palceAktualni));
        }
        if (penisyAktualni < 0.001) {
            penisyEditText.setText("0.0");
        } else {
            penisyEditText.setText(String.valueOf(penisyAktualni));
        }
        if (schodyAktualni < 0.001) {
            schodyEditText.setText("0.0");
        } else {
            schodyEditText.setText(String.valueOf(schodyAktualni));
        }
        if (sloniAktualni < 0.001) {
            sloniEditText.setText("0.0");
        } else {
            sloniEditText.setText(String.valueOf(sloniAktualni));
        }
        if (fabieAktualni < 0.001) {
            fabieEditText.setText("0.0");
        } else {
            fabieEditText.setText(String.valueOf(fabieAktualni));
        }
        if (danAktualni < 0.001) {
            danEditText.setText("0.0");
        } else {
            danEditText.setText(String.valueOf(danAktualni));
        }
        if (stadionyAktualni < 0.001) {
            stadionyEditText.setText("0.0");
        } else {
            stadionyEditText.setText(String.valueOf(stadionyAktualni));
        }
        if (vzdalenostAktualni < 0.001) {
            vzdalenostEditText.setText("0.0");
        } else {
            vzdalenostEditText.setText(String.valueOf(vzdalenostAktualni));
        }
        if (zemekouleAktualni < 0.001) {
            zemekouleEditText.setText("0.0");
        } else {
            zemekouleEditText.setText(String.valueOf(zemekouleAktualni));
        }
        if (geldolfyAktualni < 0.001) {
            geldolfyEditText.setText("0.0");
        } else {
            geldolfyEditText.setText(String.valueOf(geldolfyAktualni));
        }
    }

    private void pocitejZemekoule(Editable objemEditable) {
        String zemekouleString = objemEditable.toString();

        if (zemekouleString.isEmpty()) {
            zemekouleString = "0";
        }

        zemekouleAktualni = Double.parseDouble(zemekouleString);

        // výpočet podle litrů
        vzdalenostAktualni = zemekouleAktualni * 40075000 / koeficientZnacky;
        palceAktualni = vzdalenostAktualni / 0.0254 * koeficientZnacky;
        penisyAktualni = vzdalenostAktualni / 0.1312 * koeficientZnacky;
        schodyAktualni = vzdalenostAktualni / 0.160036 * koeficientZnacky;
        sloniAktualni = vzdalenostAktualni / 3.4909 * koeficientZnacky;
        fabieAktualni = vzdalenostAktualni / 3.96 * koeficientZnacky;
        danAktualni = vzdalenostAktualni / 19.684 * koeficientZnacky;
        stadionyAktualni = vzdalenostAktualni / 101.498 * koeficientZnacky;
        glompyAktualni = vzdalenostAktualni / 1201120.1 * koeficientZnacky;
        geldolfyAktualni = vzdalenostAktualni / (1201120.1 * 1000000) * koeficientZnacky;

        // Zaokrouhlení na maximálně 6 desetinných míst
        palceAktualni = Math.round(palceAktualni * 1e6) / 1e6;
        penisyAktualni = Math.round(penisyAktualni * 1e6) / 1e6;
        schodyAktualni = Math.round(schodyAktualni * 1e6) / 1e6;
        sloniAktualni = Math.round(sloniAktualni * 1e6) / 1e6;
        fabieAktualni = Math.round(fabieAktualni * 1e6) / 1e6;
        danAktualni = Math.round(danAktualni * 1e6) / 1e6;
        stadionyAktualni = Math.round(stadionyAktualni * 1e6) / 1e6;
        glompyAktualni = Math.round(glompyAktualni * 1e6) / 1e6;
        vzdalenostAktualni = Math.round(vzdalenostAktualni * 1e6) / 1e6;
        geldolfyAktualni = Math.round(geldolfyAktualni * 1e6) / 1e6;

        // a nastavím jako text do polí
        if (palceAktualni < 0.001) {
            palceEditText.setText("0.0");
        } else {
            palceEditText.setText(String.valueOf(palceAktualni));
        }
        if (penisyAktualni < 0.001) {
            penisyEditText.setText("0.0");
        } else {
            penisyEditText.setText(String.valueOf(penisyAktualni));
        }
        if (schodyAktualni < 0.001) {
            schodyEditText.setText("0.0");
        } else {
            schodyEditText.setText(String.valueOf(schodyAktualni));
        }
        if (sloniAktualni < 0.001) {
            sloniEditText.setText("0.0");
        } else {
            sloniEditText.setText(String.valueOf(sloniAktualni));
        }
        if (fabieAktualni < 0.001) {
            fabieEditText.setText("0.0");
        } else {
            fabieEditText.setText(String.valueOf(fabieAktualni));
        }
        if (danAktualni < 0.001) {
            danEditText.setText("0.0");
        } else {
            danEditText.setText(String.valueOf(danAktualni));
        }
        if (stadionyAktualni < 0.001) {
            stadionyEditText.setText("0.0");
        } else {
            stadionyEditText.setText(String.valueOf(stadionyAktualni));
        }
        if (glompyAktualni < 0.001) {
            glompyEditText.setText("0.0");
        } else {
            glompyEditText.setText(String.valueOf(glompyAktualni));
        }
        if (vzdalenostAktualni < 0.001) {
            vzdalenostEditText.setText("0.0");
        } else {
            vzdalenostEditText.setText(String.valueOf(vzdalenostAktualni));
        }
        if (geldolfyAktualni < 0.001) {
            geldolfyEditText.setText("0.0");
        } else {
            geldolfyEditText.setText(String.valueOf(geldolfyAktualni));
        }
    }

    private void pocitejGeldolfy(Editable objemEditable) {
        String geldolfString = objemEditable.toString();

        if (geldolfString.isEmpty()) {
            geldolfString = "0";
        }

        geldolfyAktualni = Double.parseDouble(geldolfString);

        // výpočet podle litrů
        vzdalenostAktualni = geldolfyAktualni * (1201120.1 * 1000000)/ koeficientZnacky;
        palceAktualni = vzdalenostAktualni / 0.0254 * koeficientZnacky;
        penisyAktualni = vzdalenostAktualni / 0.1312 * koeficientZnacky;
        schodyAktualni = vzdalenostAktualni / 0.160036 * koeficientZnacky;
        sloniAktualni = vzdalenostAktualni / 3.4909 * koeficientZnacky;
        fabieAktualni = vzdalenostAktualni / 3.96 * koeficientZnacky;
        danAktualni = vzdalenostAktualni / 19.684 * koeficientZnacky;
        stadionyAktualni = vzdalenostAktualni / 101.498 * koeficientZnacky;
        glompyAktualni = vzdalenostAktualni / 1201120.1 * koeficientZnacky;
        zemekouleAktualni = vzdalenostAktualni / 40075000 * koeficientZnacky;

        // Zaokrouhlení na maximálně 6 desetinných míst
        palceAktualni = Math.round(palceAktualni * 1e6) / 1e6;
        penisyAktualni = Math.round(penisyAktualni * 1e6) / 1e6;
        schodyAktualni = Math.round(schodyAktualni * 1e6) / 1e6;
        sloniAktualni = Math.round(sloniAktualni * 1e6) / 1e6;
        fabieAktualni = Math.round(fabieAktualni * 1e6) / 1e6;
        danAktualni = Math.round(danAktualni * 1e6) / 1e6;
        stadionyAktualni = Math.round(stadionyAktualni * 1e6) / 1e6;
        glompyAktualni = Math.round(glompyAktualni * 1e6) / 1e6;
        zemekouleAktualni = Math.round(zemekouleAktualni * 1e6) / 1e6;
        vzdalenostAktualni = Math.round(vzdalenostAktualni * 1e6) / 1e6;

        // a nastavím jako text do polí
        if (palceAktualni < 0.001) {
            palceEditText.setText("0.0");
        } else {
            palceEditText.setText(String.valueOf(palceAktualni));
        }
        if (penisyAktualni < 0.001) {
            penisyEditText.setText("0.0");
        } else {
            penisyEditText.setText(String.valueOf(penisyAktualni));
        }
        if (schodyAktualni < 0.001) {
            schodyEditText.setText("0.0");
        } else {
            schodyEditText.setText(String.valueOf(schodyAktualni));
        }
        if (sloniAktualni < 0.001) {
            sloniEditText.setText("0.0");
        } else {
            sloniEditText.setText(String.valueOf(sloniAktualni));
        }
        if (fabieAktualni < 0.001) {
            fabieEditText.setText("0.0");
        } else {
            fabieEditText.setText(String.valueOf(fabieAktualni));
        }
        if (danAktualni < 0.001) {
            danEditText.setText("0.0");
        } else {
            danEditText.setText(String.valueOf(danAktualni));
        }
        if (stadionyAktualni < 0.001) {
            stadionyEditText.setText("0.0");
        } else {
            stadionyEditText.setText(String.valueOf(stadionyAktualni));
        }
        if (glompyAktualni < 0.001) {
            glompyEditText.setText("0.0");
        } else {
            glompyEditText.setText(String.valueOf(glompyAktualni));
        }
        if (zemekouleAktualni < 0.001) {
            zemekouleEditText.setText("0.0");
        } else {
            zemekouleEditText.setText(String.valueOf(zemekouleAktualni));
        }
        if (vzdalenostAktualni < 0.001) {
            vzdalenostEditText.setText("0.0");
        } else {
            vzdalenostEditText.setText(String.valueOf(vzdalenostAktualni));
        }
    }
}
