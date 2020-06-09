package dhbw.lichter.scheuring.formelapp.ui.database;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

import dhbw.lichter.scheuring.formelapp.R;
import dhbw.lichter.scheuring.formelapp.util.DatabaseManager;
import dhbw.lichter.scheuring.formelapp.util.Fart;
import dhbw.lichter.scheuring.formelapp.util.FartAdapter;
import dhbw.lichter.scheuring.formelapp.util.Toaster;

public class DatabaseFragment extends Fragment implements View.OnClickListener {
    private Activity activity;
    private Toaster toaster;
    private FartAdapter fartAdapter;

    private int sortProperty;
    private boolean sortAsc;

    public DatabaseManager dbHelper;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_database, container, false);
        View toastView = inflater.inflate(R.layout.custom_toast, (ViewGroup) root.findViewById(R.id.custom_toast_layout));

        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(false);

        activity = getActivity();
        dbHelper = new DatabaseManager(activity);
        toaster = new Toaster(requireActivity().getApplicationContext(), toastView);

        this.createCardsForFarts(root);
        this.addClickListenerToRadioButtons(root);
        this.addTextChangedListenerToSearchInput(root);
        return root;
    }

    private void createCardsForFarts(View root) {
        RecyclerView fartItemsView = root.findViewById(R.id.fart_items);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        fartItemsView.setLayoutManager(layoutManager);

        ArrayList<Fart> farts = dbHelper.getFarts();
        fartAdapter = new FartAdapter(farts, this);
        fartItemsView.setAdapter(fartAdapter);
    }

    private void addClickListenerToRadioButtons(View root) {
        RadioButton rbName = root.findViewById(R.id.database_sort_name);
        RadioButton rbDate = root.findViewById(R.id.database_sort_date);
        RadioButton rbScore = root.findViewById(R.id.database_sort_score);

        rbName.setOnClickListener(this);
        rbDate.setOnClickListener(this);
        rbScore.setOnClickListener(this);
    }

    private void addTextChangedListenerToSearchInput(View root) {
        EditText searchInput = root.findViewById(R.id.database_search);
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                fartAdapter.filter(s.toString());
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        Drawable desc = activity.getDrawable(R.drawable.ic_arrow_downward_black_24dp);
        Drawable asc = activity.getDrawable(R.drawable.ic_arrow_upward_black_24dp);
        RadioButton rbClicked = (RadioButton) view;
        int id = rbClicked.getId();

        if (sortProperty == id && sortAsc) {
            sortAsc = false;
            rbClicked.setCompoundDrawablesWithIntrinsicBounds(null, null, desc, null);
        } else {
            sortAsc = true;
            rbClicked.setCompoundDrawablesWithIntrinsicBounds(null, null, asc, null);
        }

        sortProperty = id;
        fartAdapter.sort(id, sortAsc);

        toaster.showSuccess(R.string.database_sort_success);
    }
}
