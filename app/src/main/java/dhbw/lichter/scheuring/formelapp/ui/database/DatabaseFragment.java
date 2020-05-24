package dhbw.lichter.scheuring.formelapp.ui.database;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import dhbw.lichter.scheuring.formelapp.R;
import dhbw.lichter.scheuring.formelapp.util.DatabaseManager;

public class DatabaseFragment extends Fragment {
    protected Activity _activity;
    protected DatabaseManager dbHelper;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_database, container, false);

        dbHelper = new DatabaseManager(getActivity());
        return root;
    }
}
