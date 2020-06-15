package dhbw.lichter.scheuring.formelapp.database;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import dhbw.lichter.scheuring.formelapp.R;
import dhbw.lichter.scheuring.formelapp.ui.database.DatabaseFragment;

public class FartAdapter extends RecyclerView.Adapter<FartViewHolder> {

    private final DatabaseFragment dbFragment;
    private final List<Fart> farts;
    private List<Fart> fartsView;

    public FartAdapter(List<Fart> farts, DatabaseFragment dbFragment) {
        this.farts = farts;
        this.fartsView = farts;
        this.dbFragment = dbFragment;
    }

    @Override
    public int getItemCount() {
        return fartsView.size();
    }

    @NonNull
    @Override
    public FartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_card_template, parent, false);
        return new FartViewHolder(view, dbFragment, this);
    }

    @Override
    public void onBindViewHolder(FartViewHolder fartViewHolder, int i) {
        Fart fart = fartsView.get(i);
        ImageView fartGif = fartViewHolder.fartGif;

        fartViewHolder.fart = fart;
        fartViewHolder.fartName.setText(fart.getName());
        fartViewHolder.fartScore.setText(String.format(Locale.GERMANY, "%.2f", fart.getScore()));
        fartViewHolder.creationDate.setText(formatDate(fart.getCreationDate()));

        int drawable = R.drawable.im_super_score;
        if (fart.getScore() <= 10.0) {
            drawable = R.drawable.im_low_score;
        } else if (fart.getScore() <= 20.0) {
            drawable = R.drawable.im_middle_score;
        } else if (fart.getScore() <= 40.0) {
            drawable = R.drawable.im_high_score;
        }
        fartGif.setImageResource(drawable);

        if(fart.getAudioPath().equals("")) {
            fartViewHolder.bPlay.setVisibility(View.GONE);
            fartViewHolder.bShare.setVisibility(View.GONE);
        }
    }

    public void removeFart(int position) {
        fartsView.remove(position);
        notifyItemRemoved(position);
    }

    public void filter(String text) {
        List<Fart> filteredFarts = new ArrayList<>();

        if (text.equals("")) {
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

    public void sort(int id, boolean sortAsc) {
        switch (id) {
            case R.id.database_sort_score:
                applySort(FartComparator.SORT_SCORE, sortAsc);
                break;
            case R.id.database_sort_date:
                applySort(FartComparator.SORT_CREATON_DATE, sortAsc);
                break;
            case R.id.database_sort_name:
                applySort(FartComparator.SORT_NAME, sortAsc);
                break;
        }
        notifyDataSetChanged();
    }


    private void applySort(String sortProperty, boolean sortAsc) {
        if (sortAsc)
            fartsView.sort(new FartComparator(sortProperty));
        else
            fartsView.sort(new FartComparator(sortProperty).reversed());
    }

    private String formatDate(String date) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.GERMANY);
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);
            Date creationDate = inputFormat.parse(date);
            assert creationDate != null;

            return outputFormat.format(creationDate);
        } catch (ParseException ex) {
            return date;
        }
    }
}
