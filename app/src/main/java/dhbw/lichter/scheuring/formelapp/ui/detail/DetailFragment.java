package dhbw.lichter.scheuring.formelapp.ui.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.card.MaterialCardView;

import dhbw.lichter.scheuring.formelapp.util.Fart;
import dhbw.lichter.scheuring.formelapp.R;
import dhbw.lichter.scheuring.formelapp.util.DatabaseManager;
import dhbw.lichter.scheuring.formelapp.util.Toaster;
import io.github.kexanie.library.MathView;

public class DetailFragment extends Fragment {

    public MathView formulaVal;
    public TextView intensity;
    public TextView length;
    public TextView embarrassment;
    public TextView numberKids;
    public TextView ageListener;
    public TextView genderFactor;
    public TextView name;

    private Button saveFart;
    private Toaster toaster;
    private DatabaseManager dbHelper;
    private Fart fart;
    private MaterialCardView saveView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detail, container, false);
        View toastView = inflater.inflate(R.layout.custom_toast, (ViewGroup) root.findViewById(R.id.custom_toast_layout));

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        toaster = new Toaster(getActivity().getApplicationContext(), toastView);
        dbHelper = new DatabaseManager(getActivity());

        formulaVal = (MathView) root.findViewById(R.id.detail_formula_values);
        intensity = (TextView) root.findViewById(R.id.txtView_detail_intensity);
        length = (TextView) root.findViewById(R.id.txtView_detail_length);
        embarrassment = (TextView) root.findViewById(R.id.txtView_detail_embarrassment);
        numberKids = (TextView) root.findViewById(R.id.txtView_detail_number_kids);
        ageListener = (TextView) root.findViewById(R.id.txtView_detail_age_listeners);
        genderFactor = (TextView) root.findViewById(R.id.txtView_detail_gender_factor);
        name = (EditText) root.findViewById(R.id.editText_fart_name);
        saveFart = (Button) root.findViewById(R.id.btn_detail_save_fart);
        saveFart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFartInDb();
            }
        });
        saveView = (MaterialCardView) root.findViewById(R.id.fart_card_save);

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
        Boolean is_in_db = bundle.getBoolean("isInDb");
        //TODO
        String valueName ="Name";
        this.fart = new Fart(valueIntensity, valueLength, valueEmbarrassment, valueNumberKids, valueAgeListeners, valueResult, strGenderFactor, valueName);

        //TODO: Extract into own method
        String strFormulaVal = "$$\\color{white}{\\frac{("
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
                .concat(String.format("%.2f", valueResult))
                .concat("}$$");
        formulaVal.setEngine(MathView.Engine.MATHJAX);
        formulaVal.setText(strFormulaVal);
        intensity.setText(valueIntensity + " db");
        length.setText(valueLength + " Sekunde(n)");
        embarrassment.setText("" + valueEmbarrassment);
        numberKids.setText("" + valueNumberKids);
        ageListener.setText("" + valueAgeListeners);
        genderFactor.setText(strGenderFactor + ", " + valueGenderFactor);

        if(is_in_db) {
            saveView.setVisibility(View.GONE);
        } else {
            saveView.setVisibility(View.VISIBLE);
        }

        return root;
    }

    public void saveFartInDb() {
        String name = this.name.getText().toString();

        if(name.equals("") || name.trim().equals("")) {
            this.name.setError(getResources().getString(R.string.error_empty_field));
        } else {
            fart.setName(name);
            this.dbHelper.saveFart(fart);
            toaster.showSuccess(R.string.detail_toast_success);
            saveView.setVisibility(View.GONE);
        }
    }
}
