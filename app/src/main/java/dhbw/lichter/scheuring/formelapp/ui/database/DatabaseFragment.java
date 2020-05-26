package dhbw.lichter.scheuring.formelapp.ui.database;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

public class DatabaseFragment extends Fragment {
    private Activity activity;
    protected DatabaseManager dbHelper;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_database, container, false);

        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        activity = getActivity();
        dbHelper = new DatabaseManager(activity);

        RecyclerView fartItemsView = root.findViewById(R.id.fart_items);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        fartItemsView.setLayoutManager(layoutManager);

        ArrayList<Fart> items = dbHelper.getFarts();
        FartAdapter itemsAdapter = new FartAdapter(items);
        fartItemsView.setAdapter(itemsAdapter);
        return root;
    }

    public void onRadioButtonClicked(View view) {

    }
}
