package com.nyklundamade.pickulacka.fragmenty;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.nyklundamade.pickulacka.R;
import org.jetbrains.annotations.NotNull;

public class PrdyFragment extends Fragment {

    private SeekBar prduchSeekbar, prdZvukSeekbar;
    private TextView fyzikalniProjevyTextView, nazevPrduchTextView, intenzitaPrduTextView, nazevPrdZvukTextView;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_prdy, container, false);

        fyzikalniProjevyTextView = view.findViewById(R.id.textViewPrduchProjevy);
        nazevPrduchTextView = view.findViewById(R.id.textViewPrduchNazev);
        intenzitaPrduTextView = view.findViewById(R.id.textViewPrdDecibely);
        nazevPrdZvukTextView = view.findViewById(R.id.textViewPrdZvukNazev);

        prduchSeekbar = view.findViewById(R.id.seekBarPrduchovoCislo);
        prduchSeekbar.setMin(-1);
        prduchSeekbar.setMax(16);

        prduchSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                nazevPrduchTextView.setText(ukazPrduchovoCisloNazev(progress));
                fyzikalniProjevyTextView.setText(ukazFyzikalniProjevyPrducha(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        prdZvukSeekbar = view.findViewById(R.id.seekBarPrdZvuk);
        prdZvukSeekbar.setMin(0);
        prdZvukSeekbar.setMax(130);

        prdZvukSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String stupen = String.valueOf(progress);
                if (progress > 125) {
                    stupen = ">125";
                }
                intenzitaPrduTextView.setText(stupen + " dB");
                nazevPrdZvukTextView.setText(ukazNazevZvukuPrdu(progress));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return view;
    }

    private String ukazFyzikalniProjevyPrducha(int stupen) {
        String prduchovoCislo = "";
        switch (stupen) {
            case -1: {
                prduchovoCislo = "Nelze nic cítit, smrad mizí v rektu";
                break;
            }
            case 0: {
                prduchovoCislo = "Nedetekovatelné";
                break;
            }
            case 1: {
                prduchovoCislo = "Smrdí bez následků";
                break;
            }
            case 2: {
                prduchovoCislo = "Chlupy na zadnici se pohybují (občas dochází k depilaci)";
                break;
            }
            case 3: {
                prduchovoCislo = "Proud rozvíří prach v okolí";
                break;
            }
            case 4: {
                prduchovoCislo = "Hmatem lze zachytit proud plynu";
                break;
            }
            case 5: {
                prduchovoCislo = "Papír se vznáší";
                break;
            }
            case 6: {
                prduchovoCislo = "Malé předměty padají";
                break;
            }
            case 7: {
                prduchovoCislo = "Předměty padají z poliček";
                break;
            }
            case 8: {
                prduchovoCislo = "Padá nábytek";
                break;
            }
            case 9: {
                prduchovoCislo = "Mění dřevo v dřevotřísku při přímém kontaktu";
                break;
            }
            case 10: {
                prduchovoCislo = "Zdi se boří";
                break;
            }
            case 11: {
                prduchovoCislo = "Domy padají";
                break;
            }
            case 12: {
                prduchovoCislo = "Odhazuje domy v okolí, prdel exploduje";
                break;
            }
            case 13: {
                prduchovoCislo = "Vznikají malé vulkány";
                break;
            }
            case 14: {
                prduchovoCislo = "Poruší zemskou kůru, může způsobit zemětřesení";
                break;
            }
            case 15: {
                prduchovoCislo = "Spustí obrovský jaderný výbuch";
                break;
            }
            case 16: {
                prduchovoCislo = "Konec světa (intenzita jako výbuch supernovy)";
                break;
            }
        }
        return prduchovoCislo;
    }

    private String ukazPrduchovoCisloNazev(int stupen) {
        String fyzikalniProjevy = "";
        switch (stupen) {
            case -1: {
                fyzikalniProjevy = "-1 - Smradocuc";
                break;
            }
            case 0: {
                fyzikalniProjevy = "0 - Úplné bezprdí";
                break;
            }
            case 1: {
                fyzikalniProjevy = "1 - Větříček";
                break;
            }
            case 2: {
                fyzikalniProjevy = "2 - Šeptoun/syčák";
                break;
            }
            case 3: {
                fyzikalniProjevy = "3 - Bubloun/kňoural";
                break;
            }
            case 4: {
                fyzikalniProjevy = "4 - Brumloun/kvičoun";
                break;
            }
            case 5: {
                fyzikalniProjevy = "5 - Praskoun/hvízdal";
                break;
            }
            case 6: {
                fyzikalniProjevy = "6 - Motorák";
                break;
            }
            case 7: {
                fyzikalniProjevy = "7 - Kokrhel/Ruprecht";
                break;
            }
            case 8: {
                fyzikalniProjevy = "8 - Decikrhel/Rarach";
                break;
            }
            case 9: {
                fyzikalniProjevy = "9 - Kilokrhel/Rachman";
                break;
            }
            case 10: {
                fyzikalniProjevy = "10 - Kontratrhel";
                break;
            }
            case 11: {
                fyzikalniProjevy = "11 - Kraken";
                break;
            }
            case 12: {
                fyzikalniProjevy = "12 - Utrhprdel";
                break;
            }
            case 13: {
                fyzikalniProjevy = "13 - Vybuchprdel";
                break;
            }
            case 14: {
                fyzikalniProjevy = "14 - Terrathel";
                break;
            }
            case 15: {
                fyzikalniProjevy = "15 - Světotrhel";
                break;
            }
            case 16: {
                fyzikalniProjevy = "16 - Armageddon";
                break;
            }
        }
        return fyzikalniProjevy;
    }

    private String ukazNazevZvukuPrdu(int intenzita) {
        String nazev = "";
        if (0 <= intenzita && intenzita < 10) {
            nazev = "Tichoun";
        }
        if (10 <= intenzita && intenzita < 30) {
            nazev = "Šeptíček";
        }
        if (30 <= intenzita && intenzita < 50) {
            nazev = "Šumiduch";
        }
        if (50 <= intenzita && intenzita < 60) {
            nazev = "Bublifuk";
        }
        if (60 <= intenzita && intenzita < 70) {
            nazev = "Břeněk";
        }
        if (70 <= intenzita && intenzita < 90) {
            nazev = "Řachan";
        }
        if (90 <= intenzita && intenzita < 120) {
            nazev = "Křachan";
        }
        if (120 <= intenzita && intenzita < 126) {
            nazev = "Hřmohrom";
        }
        if (126 <= intenzita) {
            nazev = "Sonický třesk";
        }
        return nazev;
    }
}
