package dhbw.lichter.scheuring.formelapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import dhbw.lichter.scheuring.formelapp.R;

public class HomeFragment extends Fragment {

    private EditText editTextIntensity;
    private EditText editTextLength;
    private EditText editTextNumberKids;
    private EditText editTextAgeListeners;
    private Spinner spnSocailEmbarressment;
    private Spinner spnGenderFactor;
    private Button btnCreateFart;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //Mit View Elementen Verknüpfen
        editTextIntensity = (EditText) root.findViewById(R.id.editText_fartIntensity);
        editTextLength = (EditText) root.findViewById(R.id.editText_fartLength);
        editTextNumberKids = (EditText) root.findViewById(R.id.editText_number_kids_present);
        editTextAgeListeners = (EditText) root.findViewById(R.id.editText_age_of_listener);
        spnSocailEmbarressment = (Spinner) root.findViewById(R.id.spn_social_embarressment);
        spnGenderFactor = (Spinner) root.findViewById(R.id.spn_gender_factor);
        btnCreateFart = (Button) root.findViewById(R.id.btn_create_fart);
        //EventListener für Button hinzufügen
        btnCreateFart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTitle();
            }
        });
        return root;
    }

    public void updateTitle() {
        editTextIntensity.setText("Test");
    }
}
