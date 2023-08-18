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
import android.widget.TextView;
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

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plocha, container, false);

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
        jednotkySpinner = view.findViewById(R.id.spinnerJendotkyPlocha);

        plochaEditText.setFilters(new InputFilter[]{decimalFilter});
        stadionyEditText.setFilters(new InputFilter[]{decimalFilter});
        vaclavakyEditText.setFilters(new InputFilter[]{decimalFilter});

        String[] znacky = new String[PlochaEnum.values().length];
        for (int i = 0; i < PlochaEnum.values().length; i++) {
            znacky[i] = PlochaEnum.values()[i].getZnacka();
        }

        ArrayAdapter<String> znackyAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, znacky);
        znackyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jednotkySpinner.setAdapter(znackyAdapter);
        jednotkySpinner.setSelection(3);

        return view;
    }
}
