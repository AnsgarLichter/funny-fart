package dhbw.lichter.scheuring.formelapp.util;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dhbw.lichter.scheuring.formelapp.R;
import lombok.NonNull;

public class FartAdapter extends RecyclerView.Adapter<FartAdapter.FartViewHolder> {

    public static class FartViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView fartGif;
        TextView fartName;
        TextView fartScore;
        TextView creationDate;


        FartViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.fart_card);
            fartGif = (ImageView) itemView.findViewById(R.id.fart_gif);
            fartName = (TextView) itemView.findViewById(R.id.card_fart_name);
            fartScore = (TextView) itemView.findViewById(R.id.card_fart_score);
            creationDate = (TextView) itemView.findViewById(R.id.card_creation_date);
        }
    }

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
        View view = new TextView(parent.getContext());
        LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_card_template, parent, false);
        return new FartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FartViewHolder fartViewHolder, int i) {
        fartViewHolder.fartName.setText(farts.get(i).getName());
        fartViewHolder.fartScore.setText("" + farts.get(i).getScore());
        fartViewHolder.creationDate.setText(farts.get(i).getCreationDate());
        fartViewHolder.fartGif.setImageResource(R.drawable.im_funny_fart);
    }
}
