package dhbw.lichter.scheuring.formelapp.ui.database;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dhbw.lichter.scheuring.formelapp.R;
import dhbw.lichter.scheuring.formelapp.util.DatabaseManager;
import dhbw.lichter.scheuring.formelapp.util.Fart;
import dhbw.lichter.scheuring.formelapp.util.FartAdapter;
import dhbw.lichter.scheuring.formelapp.util.Toaster;

public class DatabaseFragment extends Fragment implements View.OnClickListener {
    private Activity activity;
    private Toaster toaster;
    private ArrayList<Fart> farts;
    private FartAdapter fartAdapter;

    public DatabaseManager dbHelper;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_database, container, false);

        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        activity = getActivity();
        dbHelper = new DatabaseManager(activity);
        toaster = new Toaster(activity);

        this.createCardsForFarts(root);
        this.addClickListenerToRadioButtons(root);
        this.addTextChangedListenerToSearchInput(root);
        return root;
    }

    private void createCardsForFarts(View root) {
        RecyclerView fartItemsView = root.findViewById(R.id.fart_items);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        fartItemsView.setLayoutManager(layoutManager);

        farts = dbHelper.getFarts();
        fartAdapter = new FartAdapter(farts, dbHelper, this);
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

    @Override
    public void onClick(View view) {
        RadioButton rbSort = (RadioButton) view;

        //TODO: determine property to apply sorting for

        //TODO: sort farts

        //TODO: Positioning of message toast
        toaster.showSuccess(R.string.database_sort_success);
    }
}
