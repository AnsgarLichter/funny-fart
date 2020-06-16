package dhbw.lichter.scheuring.formelapp.ui.home;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.util.Objects;

import dhbw.lichter.scheuring.formelapp.R;
import dhbw.lichter.scheuring.formelapp.database.DatabaseManager;
import dhbw.lichter.scheuring.formelapp.ui.detail.DetailFragment;
import dhbw.lichter.scheuring.formelapp.util.Navigator;
import dhbw.lichter.scheuring.formelapp.util.Toaster;

public class HomeFragment extends Fragment {

    private ElegantNumberButton enbIntensity;
    private ElegantNumberButton enbTextLength;
    private ElegantNumberButton enbSocialEmbarrassment;
    private ElegantNumberButton enbNumberKids;
    private EditText editTextAgeListeners;
    private RadioButton male;
    private RadioButton female;

    private Toaster toast;
    private Navigator navigator;
    private DatabaseManager dbHelper;

    private Bundle bundle;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        View toastView = inflater.inflate(R.layout.custom_toast,  (ViewGroup) root.findViewById(R.id.custom_toast_layout));

        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(false);

        //Instance of Application Context for displaying a toast
        toast = new Toaster(requireActivity().getApplicationContext(), toastView);
        navigator = new Navigator(getParentFragmentManager());
        dbHelper = new DatabaseManager(requireActivity());
        Bundle source = getArguments();
        bundle = new Bundle();

        //Share Audio File
        if(source != null)  bundle.putString("audioPath", source.getString("audioPath"));
        //Connect with View Elements
        enbIntensity = root.findViewById(R.id.enb_fart_intensity);
        enbTextLength = root.findViewById(R.id.enb_fart_length);
        enbSocialEmbarrassment = root.findViewById(R.id.enb_social_embarrassment);
        enbNumberKids = root.findViewById(R.id.enb_number_kids_present);
        editTextAgeListeners = root.findViewById(R.id.editText_age_of_listener);
        male = root.findViewById(R.id.home_gender_male);
        female = root.findViewById(R.id.home_gender_female);
        Button btnCreateFart = root.findViewById(R.id.btn_create_fart);

        //Set Range
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
        //Import Values
        String stringIntensity = enbIntensity.getNumber();
        String stringLength = enbTextLength.getNumber();
        String stringNumberKids = enbNumberKids.getNumber();
        String stringAgeListeners = editTextAgeListeners.getText().toString();
        String stringEmbarrassment = enbSocialEmbarrassment.getNumber();
        String stringGenderFactor = female.getText().toString();

        if(male.isChecked()) {
            stringGenderFactor = male.getText().toString();
        }
        bundle.putString("strGenderFactor", stringGenderFactor);

        boolean calculate = true;
        if(TextUtils.isEmpty(stringAgeListeners)) {
            editTextAgeListeners.setError(getResources().getString(R.string.error_empty_field));
            calculate = false;
        }

        if(calculate) {
            toast.showSuccess(R.string.success_toast_field);

            calculateFart(
                    Integer.parseInt(stringIntensity),
                    Integer.parseInt(stringLength),
                    Integer.parseInt(stringNumberKids),
                    Integer.parseInt(stringAgeListeners),
                    Integer.parseInt(stringEmbarrassment),
                    dbHelper.getSexFactor(stringGenderFactor));

            navigator.navigate(new DetailFragment(), true, bundle);
        } else {
            toast.showError(R.string.error_toast_field);
        }
    }


    public void calculateFart(int valueIntensity, int valueLength, int valueNumberKids, int valueAgeListeners, int valueEmbarrassment, double valueGenderFactor) {
        double score = (Math.pow((valueIntensity * valueLength), valueEmbarrassment) * valueNumberKids) / (valueAgeListeners * valueGenderFactor);
        bundle.putInt("intensity", valueIntensity);
        bundle.putInt("length", valueLength);
        bundle.putInt("embarrassment", valueEmbarrassment);
        bundle.putInt("numberKids", valueNumberKids);
        bundle.putInt("ageListeners", valueAgeListeners);
        bundle.putDouble("genderFactor", valueGenderFactor);
        bundle.putDouble("result", score);
        bundle.putBoolean("isInDb", false);
    }
}
