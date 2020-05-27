package dhbw.lichter.scheuring.formelapp.util;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dhbw.lichter.scheuring.formelapp.R;
import dhbw.lichter.scheuring.formelapp.ui.database.DatabaseFragment;
import lombok.NonNull;

public class FartAdapter extends RecyclerView.Adapter<FartViewHolder> {

    private final DatabaseFragment dbFragment;
    private List<Fart> farts;
    private List<Fart> fartsView;
    private DatabaseManager dbHelper;

    public FartAdapter(List<Fart> farts, DatabaseManager dbHelper, DatabaseFragment dbFragment) {
        this.farts = farts;
        this.fartsView = farts;
        this.dbHelper = dbHelper;
        this.dbFragment = dbFragment;
    }

    @Override
    public int getItemCount() {
        return fartsView.size();
    }

    @Override
    public FartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_card_template, parent, false);
        return new FartViewHolder(view, dbFragment, this);
    }

    @Override
    public void onBindViewHolder(FartViewHolder fartViewHolder, int i) {
        fartViewHolder.fart = fartsView.get(i);
        fartViewHolder.fartName.setText(fartViewHolder.fart.getName());
        fartViewHolder.fartScore.setText("" + (int) fartViewHolder.fart.getScore());
        fartViewHolder.creationDate.setText(fartViewHolder.fart.getCreationDate());
        fartViewHolder.fartGif.setImageResource(R.drawable.im_funny_fart);
    }

    public void removeFart(int position) {
        fartsView.remove(position);
        farts.remove(position);
        notifyItemRemoved(position);
    }

    public void filter(String text) {
        List<Fart> filteredFarts = new ArrayList<Fart>();

        if (text == "") {
            fartsView = farts;
        } else {
            for (Fart fart : farts) {
                if (fart.getName().toLowerCase().contains(text.toLowerCase())) {
                    filteredFarts.add(fart);
                }
            }
            fartsView = filteredFarts;
        }
        notifyDataSetChanged();

    }
}
