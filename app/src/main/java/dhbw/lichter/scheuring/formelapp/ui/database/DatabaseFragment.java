package dhbw.lichter.scheuring.formelapp.ui.database;

import android.app.Activity;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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
    private DatabaseManager dbHelper;

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
        this.addClickListenerToCardViewButtons(root);
        return root;
    }


    private void createCardsForFarts(View root) {
        RecyclerView fartItemsView = root.findViewById(R.id.fart_items);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        fartItemsView.setLayoutManager(layoutManager);

        ArrayList<Fart> items = dbHelper.getFarts();
        FartAdapter itemsAdapter = new FartAdapter(items);
        fartItemsView.setAdapter(itemsAdapter);
    }

    private void addClickListenerToRadioButtons(View root) {
        RadioButton rbName = root.findViewById(R.id.database_sort_name);
        RadioButton rbDate = root.findViewById(R.id.database_sort_date);
        RadioButton rbScore = root.findViewById(R.id.database_sort_score);

        rbName.setOnClickListener(this);
        rbDate.setOnClickListener(this);
        rbScore.setOnClickListener(this);
    }

    private void addClickListenerToCardViewButtons(View root) {
        Button bDelete = root.findViewById(R.id.database_delete);
        Button bDetail = root.findViewById(R.id.database_detail);

        bDelete.setOnClickListener(this);
        bDetail.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.database_sort_date:
            case R.id.database_sort_name:
            case R.id.database_sort_score:
                this.onSortButtonClicked(view);
                break;
            case R.id.database_delete:
                this.onDeleteButtonClicked(view);
                break;
            case R.id.database_detail:
                this.onDetailButtonClicked(view);
                break;
            default:
                //Do nothing
        }
    }

    private void onSortButtonClicked(View view) {
        RadioButton rbSort = (RadioButton) view;

        //TODO: determine property to apply sorting for

        //TODO: sort farts

        //TODO: Positioning of message toast
        toaster.showSuccess(R.string.database_sort_success);
    }

    private void onDetailButtonClicked(View view) {
        Button bDetail = (Button) view;
        //TODO: get fart object

        //TODO: navigate to details page

        //TODO: transfer fart object to detail fragment
    }

    public void onDeleteButtonClicked(View view) {
        Button bDelete = (Button) view;
        //TODO: get id of fart object

        //TODO: security query

        //TODO: delete fart object if secuirty query was approved
    }
}
