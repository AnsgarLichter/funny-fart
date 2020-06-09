package dhbw.lichter.scheuring.formelapp.ui.home;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import dhbw.lichter.scheuring.formelapp.R;
import dhbw.lichter.scheuring.formelapp.ui.detail.DetailFragment;
import dhbw.lichter.scheuring.formelapp.util.Toaster;

public class HomeFragment extends Fragment {

    private Button btnCreateFart;

    public ElegantNumberButton enbIntensity;
    public ElegantNumberButton enbTextLength;
    public ElegantNumberButton enbSocialEmbarrassment;
    public ElegantNumberButton enbNumberKids;
    public EditText editTextAgeListeners;
    public RadioGroup gender;
    public RadioButton male;
    public RadioButton female;
    public Toaster toast;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        View toastView = inflater.inflate(R.layout.custom_toast,  (ViewGroup) root.findViewById(R.id.custom_toast_layout));

        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        //Instanz of Application Context for displaying a toast
        toast = new Toaster(getActivity().getApplicationContext(), toastView);

        //Mit View Elementen Verknüpfen
        enbIntensity = (ElegantNumberButton) root.findViewById(R.id.enb_fart_intensity);
        enbTextLength = (ElegantNumberButton) root.findViewById(R.id.enb_fart_length);
        enbSocialEmbarrassment = (ElegantNumberButton) root.findViewById(R.id.enb_social_embarrassment);
        enbNumberKids = (ElegantNumberButton) root.findViewById(R.id.enb_number_kids_present);
        editTextAgeListeners = (EditText) root.findViewById(R.id.editText_age_of_listener);
        male = (RadioButton) root.findViewById(R.id.home_gender_male);
        gender = (RadioGroup) root.findViewById(R.id.radioGroupGender);
        female = (RadioButton) root.findViewById(R.id.home_gender_female);
        btnCreateFart = (Button) root.findViewById(R.id.btn_create_fart);

        //set Range
        enbSocialEmbarrassment.setRange(1, 3);

        btnCreateFart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFart();
            }
        });
        return root;
    }

    public void createFart() {
        //Bundle für Datenuebergabe für Detail View
        Bundle bundle = new Bundle();

        //Werte einlesen
        String stringIntensity = enbIntensity.getNumber();
        String stringLength = enbTextLength.getNumber();
        String stringNumberKids = enbNumberKids.getNumber();
        String stringAgeListeners = editTextAgeListeners.getText().toString();
        String stringEmbarrassment = enbSocialEmbarrassment.getNumber();
        String stringGenderFactor = female.getText().toString();

        if(male.isSelected()) {
            stringGenderFactor = male.getText().toString();
        }

        boolean calculate = true;
        if(TextUtils.isEmpty(stringAgeListeners)) {
            editTextAgeListeners.setError(getResources().getString(R.string.error_empty_field));
            calculate = false;
        }

        //TODO: Extract into own method
        if(calculate) {
            toast.showSuccess(R.string.success_toast_field);

            //Cast von String zu Int
            int valueIntensity = Integer.parseInt(stringIntensity);
            int valueLength = Integer.parseInt(stringLength);
            int valueNumberKids = Integer.parseInt(stringNumberKids);
            int valueAgeListeners = Integer.parseInt(stringAgeListeners);
            int valueEmbarrassment;
            double valueGenderFactor;

            //Wert des Spinners bestimmmen über zweite Arraylist
            int counter = -1;
            for (String el : getResources().getStringArray(R.array.keys_social_embarrassment)) {
                counter++;
                if (el.equals(stringEmbarrassment)) {
                    break;
                }
            }
            valueEmbarrassment = Integer.parseInt(getResources().getStringArray(R.array.values_social_embarrassment)[counter]);

            //Wert des Spinners GenderFactor bestimmen
            counter = -1;
            for (String el : getResources().getStringArray(R.array.keys_gender_factor)) {
                counter++;
                if (el.equals(stringGenderFactor)) {
                    break;
                }
            }
            valueGenderFactor = Double.parseDouble(getResources().getStringArray(R.array.values_gender_factor)[counter]);

            //Furz berechnen
            double score = (Math.pow((valueIntensity * valueLength), valueEmbarrassment) * valueNumberKids) / (valueAgeListeners * valueGenderFactor);

            //Werte in Bundle schreiben für Datenuebergabe
            //Werte fuer Berechnung
            bundle.putInt("intensity", valueIntensity);
            bundle.putInt("length", valueLength);
            bundle.putInt("embarrassment", valueEmbarrassment);
            bundle.putInt("numberKids", valueNumberKids);
            bundle.putInt("ageListeners", valueAgeListeners);
            bundle.putDouble("genderFactor", valueGenderFactor);
            bundle.putDouble("result", score);
            bundle.putBoolean("isInDb", false);

            //Werte fuer die Anzeige
            bundle.putString("strGenderFactor", stringGenderFactor);
            bundle.putString("strSocialEmbarrassment", stringEmbarrassment);

            //TODO: Implement navigate method
            Fragment fragment = new DetailFragment();
            fragment.setArguments(bundle);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else {
            toast.showError(R.string.error_toast_field);
        }
    }
}
