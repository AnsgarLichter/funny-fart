package dhbw.lichter.scheuring.formelapp.util;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import dhbw.lichter.scheuring.formelapp.R;

public class FartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public Fart fart;
    public CardView cardView;
    public ImageView fartGif;
    public TextView fartName;
    public TextView fartScore;
    public TextView creationDate;
    public Button bDelete;
    public Button bDetail;


    FartViewHolder(View itemView) {
        super(itemView);
        cardView = (CardView) itemView.findViewById(R.id.fart_card);
        fartGif = (ImageView) itemView.findViewById(R.id.fart_gif);
        fartName = (TextView) itemView.findViewById(R.id.card_fart_name);
        fartScore = (TextView) itemView.findViewById(R.id.card_fart_score);
        creationDate = (TextView) itemView.findViewById(R.id.card_creation_date);
        bDelete = itemView.findViewById(R.id.database_delete);
        bDetail = itemView.findViewById(R.id.database_detail);

        bDelete.setOnClickListener(this);
        bDetail.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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
