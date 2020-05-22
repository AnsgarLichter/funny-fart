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
    public TextView intensity;
    public TextView length;
    public TextView embarrassment;
    public TextView numberKids;
    public TextView ageListener;
    public TextView genderFactor;
    public TextView result;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detail, container, false);

        //Mit View Element verknüpfen
        formula = (MathView) root.findViewById(R.id.detail_formula);
        intensity = (TextView) root.findViewById(R.id.txtView_detail_intensity);
        length = (TextView) root.findViewById(R.id.txtView_detail_length);
        embarrassment = (TextView) root.findViewById(R.id.txtView_detail_embarrassment);
        numberKids = (TextView) root.findViewById(R.id.txtView_detail_number_kids);
        ageListener = (TextView) root.findViewById(R.id.txtView_detail_age_listeners);
        genderFactor = (TextView) root.findViewById(R.id.txtView_detail_gender_factor);
        result = (TextView) root.findViewById(R.id.txtView_detail_result);

        //getData
        Bundle bundle = getArguments();
        int valueIntensity = bundle.getInt("intensity");
        int valueLength = bundle.getInt("length");
        int valueEmbarrassment = bundle.getInt("embarrassment");
        int valueNumberKids = bundle.getInt("numberKids");
        int valueAgeListeners = bundle.getInt("ageListeners");
        double valueGenderFactor = bundle.getDouble("genderFactor");
        double valueResult = bundle.getDouble("result");
        String strGenderFactor = bundle.getString("strGenderFactor");

        //setData
        String strFormula = "$$\\frac{("
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
                .concat(")} = ")
                .concat(String.valueOf((int) valueResult))
                .concat("$$");
        formula.setText(strFormula);
        intensity.setText(getResources().getString(R.string.detail_intensity).concat(String.valueOf(" " + valueIntensity + " db")));
        length.setText(getResources().getString(R.string.detail_length).concat(String.valueOf(" " + valueLength + " Sekunden")));
        embarrassment.setText(getResources().getString(R.string.detail_social_embarrassment).concat(String.valueOf(" " + valueEmbarrassment)));
        numberKids.setText(getResources().getString(R.string.detail_number_kids).concat(String.valueOf(" " + valueNumberKids)));
        ageListener.setText(getResources().getString(R.string.detail_age_listener).concat(String.valueOf(" " + valueAgeListeners)));
        genderFactor.setText(getResources().getString(R.string.detail_gender_factor).concat(strGenderFactor + " mit Wert " + valueGenderFactor));
        result.setText(getResources().getString(R.string.detail_result).concat(String.valueOf(" " + valueResult)));

        return root;
    }
}
