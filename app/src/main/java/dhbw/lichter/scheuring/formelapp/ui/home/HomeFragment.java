package dhbw.lichter.scheuring.formelapp.ui.home;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import dhbw.lichter.scheuring.formelapp.R;
import dhbw.lichter.scheuring.formelapp.ui.database.DatabaseFragment;
import dhbw.lichter.scheuring.formelapp.ui.detail.DetailFragment;

public class HomeFragment extends Fragment {

    private Button btnCreateFart;

    public EditText editTextIntensity;
    public EditText editTextLength;
    public EditText editTextNumberKids;
    public EditText editTextAgeListeners;
    public Spinner spnSocialEmbarrassment;
    public Spinner spnGenderFactor;
    public Toast toast;
    public TextView result;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        //Mit View Elementen Verknüpfen
        editTextIntensity = (EditText) root.findViewById(R.id.editText_fartIntensity);
        editTextLength = (EditText) root.findViewById(R.id.editText_fartLength);
        editTextNumberKids = (EditText) root.findViewById(R.id.editText_number_kids_present);
        editTextAgeListeners = (EditText) root.findViewById(R.id.editText_age_of_listener);
        spnSocialEmbarrassment = (Spinner) root.findViewById(R.id.spn_social_embarrassment);
        spnGenderFactor = (Spinner) root.findViewById(R.id.spn_gender_factor);
        result = (TextView) root.findViewById(R.id.txtView_result);
        btnCreateFart = (Button) root.findViewById(R.id.btn_create_fart);

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
        String stringIntensity = editTextIntensity.getText().toString();
        String stringLength = editTextLength.getText().toString();
        String stringNumberKids = editTextNumberKids.getText().toString();
        String stringAgeListeners = editTextAgeListeners.getText().toString();
        String stringEmbarrassment = spnSocialEmbarrassment.getSelectedItem().toString();
        String stringGenderFactor = spnGenderFactor.getSelectedItem().toString();

        //TODO: Extract into own method
        boolean calculate = true;
        //Fehleranzeige bei leeren Feldern
        if(TextUtils.isEmpty(stringIntensity)) {
            editTextIntensity.setError(getResources().getString(R.string.error_empty_field));
            calculate = false;
        }
        if(TextUtils.isEmpty(stringLength)) {
            editTextLength.setError(getResources().getString(R.string.error_empty_field));
            calculate = false;
        }
        if(TextUtils.isEmpty(stringNumberKids)) {
            editTextNumberKids.setError(getResources().getString(R.string.error_empty_field));
            calculate = false;
        }
        if(TextUtils.isEmpty(stringAgeListeners)) {
            editTextAgeListeners.setError(getResources().getString(R.string.error_empty_field));
            calculate = false;
        }

        //TODO: Extract into own method
        if(calculate) {
            //Erfolgsanzeige
            toast = Toast.makeText(getActivity(), getString(R.string.success_toast_field),Toast.LENGTH_SHORT);
            toast.show();

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

            //Wert des Spinners GenderFacotr bestimmner
            counter = -1;
            for (String el : getResources().getStringArray(R.array.keys_gender_factor)) {
                counter++;
                if (el.equals(stringGenderFactor)) {
                    break;
                }
            }
            valueGenderFactor = Double.parseDouble(getResources().getStringArray(R.array.values_gender_factor)[counter]);

            //Furz berechnen
            double fart = (Math.pow((valueIntensity * valueLength), valueEmbarrassment) * valueNumberKids) / (valueAgeListeners * valueGenderFactor);
            result.setText(Double.toString(fart));

            //Werte in Bundle schreiben für Datenuebergabe
            //Werte fuer Berechnung
            bundle.putInt("intensity", valueIntensity);
            bundle.putInt("length", valueLength);
            bundle.putInt("embarrassment", valueEmbarrassment);
            bundle.putInt("numberKids", valueNumberKids);
            bundle.putInt("ageListeners", valueAgeListeners);
            bundle.putDouble("genderFactor", valueGenderFactor);
            bundle.putDouble("result", fart);

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
            //TODO: Extract into MessageToastClass
            toast = Toast.makeText(getActivity(), getResources().getString(R.string.error_toast_field),Toast.LENGTH_SHORT);
            //TODO: Extract into MessageToastClass
            toast = Toast.makeText(getActivity(), getString(R.string.error_toast_field),Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
