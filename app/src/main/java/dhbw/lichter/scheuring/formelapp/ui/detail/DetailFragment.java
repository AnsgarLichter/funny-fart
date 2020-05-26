package dhbw.lichter.scheuring.formelapp.ui.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import dhbw.lichter.scheuring.formelapp.util.Fart;
import dhbw.lichter.scheuring.formelapp.R;
import dhbw.lichter.scheuring.formelapp.util.DatabaseManager;
import io.github.kexanie.library.MathView;

public class DetailFragment extends Fragment {

    public MathView formulaVal;
    public TextView intensity;
    public TextView length;
    public TextView embarrassment;
    public TextView numberKids;
    public TextView ageListener;
    public TextView genderFactor;
    public TextView result;

    private Button saveFart;
    private DatabaseManager dbHelper;
    private Fart fart;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detail, container, false);

        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        dbHelper = new DatabaseManager(getActivity());

        formulaVal = (MathView) root.findViewById(R.id.detail_formula_values);
        intensity = (TextView) root.findViewById(R.id.txtView_detail_intensity);
        length = (TextView) root.findViewById(R.id.txtView_detail_length);
        embarrassment = (TextView) root.findViewById(R.id.txtView_detail_embarrassment);
        numberKids = (TextView) root.findViewById(R.id.txtView_detail_number_kids);
        ageListener = (TextView) root.findViewById(R.id.txtView_detail_age_listeners);
        genderFactor = (TextView) root.findViewById(R.id.txtView_detail_gender_factor);
        result = (TextView) root.findViewById(R.id.txtView_detail_result);
        saveFart = (Button) root.findViewById(R.id.btn_detail_save_fart);
        saveFart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFartInDb();
            }
        });

        //TODO: Extract into own method
        Bundle bundle = getArguments();
        int valueIntensity = bundle.getInt("intensity");
        int valueLength = bundle.getInt("length");
        int valueEmbarrassment = bundle.getInt("embarrassment");
        int valueNumberKids = bundle.getInt("numberKids");
        int valueAgeListeners = bundle.getInt("ageListeners");
        double valueGenderFactor = bundle.getDouble("genderFactor");
        double valueResult = bundle.getDouble("result");
        String strGenderFactor = bundle.getString("strGenderFactor");
        //TODO
        String valueName ="Name";
        this.fart = new Fart(valueIntensity, valueLength, valueEmbarrassment, valueNumberKids, valueAgeListeners, valueResult, strGenderFactor, valueName);

        //TODO: Extract into own method
        String strFormulaVal = "$$\\frac{("
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
        formulaVal.setText(strFormulaVal);
        intensity.setText(getString(R.string.detail_intensity).concat(String.valueOf(" " + valueIntensity + " db")));
        length.setText(getString(R.string.detail_length).concat(String.valueOf(" " + valueLength + " Sekunden")));
        embarrassment.setText(getString(R.string.detail_social_embarrassment).concat(String.valueOf(" " + valueEmbarrassment)));
        numberKids.setText(getString(R.string.detail_number_kids).concat(String.valueOf(" " + valueNumberKids)));
        ageListener.setText(getString(R.string.detail_age_listener).concat(String.valueOf(" " + valueAgeListeners)));
        genderFactor.setText(getString(R.string.detail_gender_factor).concat(strGenderFactor + " mit Wert " + valueGenderFactor));
        result.setText(getString(R.string.detail_result).concat(String.valueOf(" " + valueResult)));

        return root;
    }

    public void saveFartInDb() {
            this.dbHelper.saveFart(fart);
    }
}
