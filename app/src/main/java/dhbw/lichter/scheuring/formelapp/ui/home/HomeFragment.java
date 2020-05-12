package dhbw.lichter.scheuring.formelapp.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import dhbw.lichter.scheuring.formelapp.R;

public class HomeFragment extends Fragment {

    public EditText editTextIntensity;
    public EditText editTextLength;
    public EditText editTextNumberKids;
    public EditText editTextAgeListeners;
    public Spinner spnSocialEmbarrassment;
    public Spinner spnGenderFactor;
    private Button btnCreateFart;
    public Toast toast;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        toast = Toast.makeText(getActivity(), getResources().getString(R.string.err),Toast.LENGTH_SHORT);

        //Mit View Elementen Verknüpfen
        editTextIntensity = (EditText) root.findViewById(R.id.editText_fartIntensity);
        editTextLength = (EditText) root.findViewById(R.id.editText_fartLength);
        editTextNumberKids = (EditText) root.findViewById(R.id.editText_number_kids_present);
        editTextAgeListeners = (EditText) root.findViewById(R.id.editText_age_of_listener);
        spnSocialEmbarrassment = (Spinner) root.findViewById(R.id.spn_social_embarrassment);
        spnGenderFactor = (Spinner) root.findViewById(R.id.spn_gender_factor);
        btnCreateFart = (Button) root.findViewById(R.id.btn_create_fart);
        //EventListener für Button hinzufügen
        btnCreateFart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFart();
            }
        });
        return root;
    }

    public void createFart() {
        //Werte einlesen
        String stringIntensity = editTextIntensity.getText().toString();
        String stringLength = editTextLength.getText().toString();
        String stringNumberKids = editTextNumberKids.getText().toString();
        String stringAgeListeners = editTextAgeListeners.getText().toString();
        String stringEmbarrassment = spnSocialEmbarrassment.getSelectedItem().toString();
        String stringGenderFactor = spnGenderFactor.getSelectedItem().toString();

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

        if(calculate) {
            //Cast von String zu Int
            int valueIntensity = Integer.parseInt(stringIntensity);
            int valueLength = Integer.parseInt(stringLength);
            int valueNumberKids = Integer.parseInt(stringNumberKids);
            int valueAgeListeners = Integer.parseInt(stringAgeListeners);
            int valueEmbarrassment;
            int valueGenderFactor;

            //Wert des Spinners bestimmmen über zweite Arraylist
            int counter = -1;
            for (String el : getResources().getStringArray(R.array.keys_social_embarrassment)) {
                counter++;
                if (el.equals(stringEmbarrassment)) {
                    break;
                }
            }
            //Wert des Spinners GenderFacotr bestimmner
            valueEmbarrassment = Integer.parseInt(getResources().getStringArray(R.array.values_social_embarrassment)[counter]);
            counter = -1;
            for (String el : getResources().getStringArray(R.array.values_gender_factor)) {
                counter++;
                if (el.equals(stringGenderFactor)) {
                    break;
                }
            }
            valueGenderFactor = Integer.parseInt(getResources().getStringArray(R.array.values_gender_factor)[counter]);

            //Furz berechnen
            double fart = (Math.pow((valueIntensity * valueLength), valueEmbarrassment) * valueNumberKids) / (valueAgeListeners * valueGenderFactor);
        } else {
            toast.show();
        }
    }
}