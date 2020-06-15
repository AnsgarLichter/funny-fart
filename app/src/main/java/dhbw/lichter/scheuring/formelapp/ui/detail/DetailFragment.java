package dhbw.lichter.scheuring.formelapp.ui.detail;

import android.annotation.SuppressLint;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.card.MaterialCardView;

import java.util.Locale;
import java.util.Objects;

import dhbw.lichter.scheuring.formelapp.R;
import dhbw.lichter.scheuring.formelapp.database.DatabaseManager;
import dhbw.lichter.scheuring.formelapp.database.Fart;
import dhbw.lichter.scheuring.formelapp.util.Toaster;
import io.github.kexanie.library.MathView;

public class DetailFragment extends Fragment {
    private String LOG_TAG;
    public MathView formulaVal;
    public TextView intensity;
    public TextView length;
    public TextView embarrassment;
    public TextView numberKids;
    public TextView ageListener;
    public TextView genderFactor;
    public TextView name;


    private Toaster toaster;
    private DatabaseManager dbHelper;
    private Fart fart;
    private MaterialCardView saveView;

    @SuppressLint("StringFormatMatches")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detail, container, false);
        View toastView = inflater.inflate(R.layout.custom_toast, (ViewGroup) root.findViewById(R.id.custom_toast_layout));

        LOG_TAG = requireActivity().getClass().getName();
        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getParentFragmentManager().getBackStackEntryCount() > 0) {
                    getParentFragmentManager().popBackStack();
                } else {
                    DetailFragment.super.requireActivity().onBackPressed();
                }
            }
        });
        toaster = new Toaster(requireActivity().getApplicationContext(), toastView);
        dbHelper = new DatabaseManager(getActivity());

        formulaVal = root.findViewById(R.id.detail_formula_values);
        intensity = root.findViewById(R.id.txtView_detail_intensity);
        length = root.findViewById(R.id.txtView_detail_length);
        embarrassment = root.findViewById(R.id.txtView_detail_embarrassment);
        numberKids = root.findViewById(R.id.txtView_detail_number_kids);
        ageListener = root.findViewById(R.id.txtView_detail_age_listeners);
        genderFactor = root.findViewById(R.id.txtView_detail_gender_factor);
        name = (EditText) root.findViewById(R.id.editText_fart_name);
        Button saveFart = root.findViewById(R.id.btn_detail_save_fart);
        saveFart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFartInDb();
            }
        });
        saveView = root.findViewById(R.id.fart_card_save);

        Bundle bundle = getArguments();
        assert bundle != null;

        int valueIntensity = bundle.getInt("intensity");
        int valueLength = bundle.getInt("length");
        int valueEmbarrassment = bundle.getInt("embarrassment");
        int valueNumberKids = bundle.getInt("numberKids");
        int valueAgeListeners = bundle.getInt("ageListeners");
        double valueGenderFactor = bundle.getDouble("genderFactor");
        double valueResult = bundle.getDouble("result");
        String strGenderFactor = bundle.getString("strGenderFactor");
        String valueAudioPath = bundle.getString("audioPath");
        boolean is_in_db = bundle.getBoolean("isInDb");

        if(valueAudioPath != null && !valueAudioPath.equals("")) {
            this.fart = new Fart(valueIntensity, valueLength, valueEmbarrassment, valueNumberKids, valueAgeListeners, valueResult, strGenderFactor, valueAudioPath);
        } else {
            this.fart = new Fart(valueIntensity, valueLength, valueEmbarrassment, valueNumberKids, valueAgeListeners, valueResult, strGenderFactor);
        }

        String stringResult = String.format(Locale.GERMANY, "%.2f", valueResult);
        formulaVal.setEngine(MathView.Engine.MATHJAX);
        formulaVal.setText(String.format(getString(R.string.detail_formula), valueIntensity, valueLength, valueEmbarrassment, valueNumberKids, valueAgeListeners, valueGenderFactor, stringResult));
        intensity.setText(String.format(getString(R.string.detail_intensity_value), valueIntensity));
        length.setText(String.format(getString(R.string.detail_length_value), valueLength));
        embarrassment.setText(String.valueOf(valueEmbarrassment));
        numberKids.setText(String.valueOf(valueNumberKids));
        ageListener.setText(String.valueOf(valueAgeListeners));
        genderFactor.setText(String.format(getString(R.string.detail_gender_factor_value), strGenderFactor, valueGenderFactor));

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
            try{
                fart.setName(name);
                this.dbHelper.saveFart(fart);
                toaster.showSuccess(R.string.detail_toast_success);
                saveView.setVisibility(View.GONE);
            } catch (SQLException ex) {
                toaster.showError(R.string.detail_toast_error);
                Log.e(LOG_TAG, "Fart couldn't be saved to database", ex);
            }
        }
    }
}
