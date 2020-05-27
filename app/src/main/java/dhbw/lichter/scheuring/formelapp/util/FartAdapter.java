package dhbw.lichter.scheuring.formelapp.util;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dhbw.lichter.scheuring.formelapp.R;
import dhbw.lichter.scheuring.formelapp.ui.database.DatabaseFragment;
import lombok.Data;
import lombok.NonNull;

public class FartAdapter extends RecyclerView.Adapter<FartViewHolder> {

    private List<Fart> farts;

    public FartAdapter(List<Fart> farts){
        this.farts = farts;
    }

    @Override
    public int getItemCount() {
        return farts.size();
    }

    @Override
    public FartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_card_template, parent, false);
        return new FartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FartViewHolder fartViewHolder, int i) {
        fartViewHolder.fart = farts.get(i);
        fartViewHolder.fartName.setText(farts.get(i).getName());
        fartViewHolder.fartScore.setText("" + (int) farts.get(i).getScore());
        fartViewHolder.creationDate.setText(farts.get(i).getCreationDate());
        fartViewHolder.fartGif.setImageResource(R.drawable.im_funny_fart);
    }
}
