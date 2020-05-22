package dhbw.lichter.scheuring.formelapp.ui.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import dhbw.lichter.scheuring.formelapp.R;
import io.github.kexanie.library.MathView;

public class DetailFragment extends Fragment {

    public MathView formula;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detail, container, false);

        //Mit View Element verknüpfen
        formula = (MathView) root.findViewById(R.id.detail_formula);

        //getData
        Bundle bundle = getArguments();
        int valueIntensity = bundle.getInt("intensity");
        int valueLength = bundle.getInt("length");
        int valueEmbarrassment = bundle.getInt("embarrassment");
        int valueNumberKids = bundle.getInt("numberKids");
        int valueAgeListeners = bundle.getInt("ageListeners");
        int valueGenderFactor = bundle.getInt("genderFactor");
        double result = bundle.getDouble("result");
        String genderFactor = bundle.getString("strGenderFactor");
        String socialEmbarrassment = bundle.getString("strSocialEmbarrassment");

        //setData
        String strFormula = "$$"
                .concat(String.valueOf(result))
                .concat(" = \\frac{(")
                .concat(String.valueOf(valueIntensity))
                .concat(" * ")
                .concat(String.valueOf(valueLength))
                .concat(")^")
                .concat(String.valueOf(valueEmbarrassment))
                .concat(" * ")
                .concat(String.valueOf(valueNumberKids))
                .concat("}{(")
                .concat(String.valueOf(valueAgeListeners))
                .concat(" * ")
                .concat(String.valueOf(valueGenderFactor))
                .concat(")}$$");
        formula.setText(strFormula);

        return root;
    }
}
