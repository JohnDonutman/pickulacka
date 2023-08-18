package com.nyklundamade.pickulacka;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ObjemFragment extends Fragment {

    Spinner jednotkySpinner;
    EditText brazilskyPytelEditText, uruguayskyPytelEditText, nadrzeFabieEditText;

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
        jednotkySpinner = view.findViewById(R.id.spinnerJendotkyObjem);

        brazilskyPytelEditText.setFilters(new InputFilter[]{decimalFilter});
        uruguayskyPytelEditText.setFilters(new InputFilter[]{decimalFilter});
        nadrzeFabieEditText.setFilters(new InputFilter[]{decimalFilter});

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
}
