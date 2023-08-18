package com.nyklundamade.pickulacka.fragmenty;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
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
import com.nyklundamade.pickulacka.enumTridy.VzdalenostEnum;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VzdalenostFragment extends Fragment {

    Spinner jednotkySpinner;
    EditText palceEditText, penisyEditText, schodyEditText, sloniEditText, fabieEditText, danEditText, stadionyEditText, glompyEditText, zemekouleEditText, geldolfyEditText, vzdalenostEditText;


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vzdalenost, container, false);

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

        return view;
    }
}
