package dhbw.lichter.scheuring.formelapp.util;


import android.os.Build;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
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
        Fart fart = fartsView.get(i);

        fartViewHolder.fart = fart;
        fartViewHolder.fartName.setText(fart.getName());
        fartViewHolder.fartScore.setText(String.format("%.2f", fart.getScore()));
        fartViewHolder.creationDate.setText(formatDate(fart.getCreationDate()));
        fartViewHolder.fartGif.setImageResource(R.drawable.im_funny_fart);

        if(fart.getAudioPath().equals("")) {
            fartViewHolder.bPlay.setEnabled(false);
            // fartViewHolder.bPlay.setBackgroundColor(dbFragment.getResources().getColor(R.color.buttonColorDisabled));
        }
    }

    public void removeFart(int position) {
        fartsView.remove(position);
        notifyItemRemoved(position);
    }

    public void filter(String text) {
        List<Fart> filteredFarts = new ArrayList<Fart>();

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

    //TODO: Update to API24
    @RequiresApi(api = Build.VERSION_CODES.N)
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void applySort(String sortProperty, boolean sortAsc) {
        if (sortAsc)
            fartsView.sort(new FartComparator(sortProperty));
        else
            fartsView.sort(new FartComparator(sortProperty).reversed());
    }

    private String formatDate(String date) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd.MM.yyyy");
            Date creationDate = inputFormat.parse(date);
            return outputFormat.format(creationDate);
        } catch (ParseException ex) {
            return date;
        }
    }
}
