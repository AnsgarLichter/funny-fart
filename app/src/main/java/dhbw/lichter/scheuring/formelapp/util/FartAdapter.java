package dhbw.lichter.scheuring.formelapp.util;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dhbw.lichter.scheuring.formelapp.R;
import dhbw.lichter.scheuring.formelapp.ui.database.DatabaseFragment;
import lombok.NonNull;

public class FartAdapter extends RecyclerView.Adapter<FartViewHolder> {

    private final DatabaseFragment dbFragment;
    private List<Fart> farts;
    private DatabaseManager dbHelper;

    public FartAdapter(List<Fart> farts, DatabaseManager dbHelper, DatabaseFragment dbFragment) {
        this.farts = farts;
        this.dbHelper = dbHelper;
        this.dbFragment = dbFragment;
    }

    @Override
    public int getItemCount() {
        return farts.size();
    }

    @Override
    public FartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_card_template, parent, false);
        return new FartViewHolder(view, dbFragment, this);
    }

    @Override
    public void onBindViewHolder(FartViewHolder fartViewHolder, int i) {
        fartViewHolder.fart = farts.get(i);
        fartViewHolder.fartName.setText(farts.get(i).getName());
        fartViewHolder.fartScore.setText("" + (int) farts.get(i).getScore());
        fartViewHolder.creationDate.setText(farts.get(i).getCreationDate());
        fartViewHolder.fartGif.setImageResource(R.drawable.im_funny_fart);
    }

    public void removeFart(int position) {
        farts.remove(position);
        notifyItemRemoved(position);
    }
}
