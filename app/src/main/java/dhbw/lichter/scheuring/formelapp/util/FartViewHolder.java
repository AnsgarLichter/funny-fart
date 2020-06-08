package dhbw.lichter.scheuring.formelapp.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import dhbw.lichter.scheuring.formelapp.R;
import dhbw.lichter.scheuring.formelapp.ui.database.DatabaseFragment;
import dhbw.lichter.scheuring.formelapp.ui.detail.DetailFragment;

public class FartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, DialogInterface.OnClickListener {
    public Fart fart;
    public CardView cardView;
    public ImageView fartGif;
    public TextView fartName;
    public TextView fartScore;
    public TextView creationDate;
    public ImageButton bDelete;
    public ImageButton bDetail;
    private FartAdapter fartAdapter;
    private DatabaseFragment dbFragment;


    FartViewHolder(View itemView, DatabaseFragment dbFragment, FartAdapter fartAdapter) {
        super(itemView);

        this.dbFragment = dbFragment;
        this.fartAdapter = fartAdapter;

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

    @Override
    public void onClick(DialogInterface dialog, int which) {
        dbFragment.dbHelper.deleteFart(fart.getId());
        this.fartAdapter.removeFart(getAdapterPosition());
    }

    private void onDetailButtonClicked(View view) {
        Bundle bundle = this.createBundle();
        this.navigateToDetailPage(bundle);
    }

    public void onDeleteButtonClicked(View view) {
        Activity activity = dbFragment.getActivity();
        final long id = fart.getId();
        String name = fart.getName();
        String title = activity.getString(R.string.database_delete_security_query_title);
        String message = activity.getString(R.string.database_delete_security_query_message).replace(":name", name);

        new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, this)
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private Bundle createBundle() {
        //TODO: use fart object
        Bundle bundle = new Bundle();
        bundle.putInt("intensity", fart.getIntensity());
        bundle.putInt("length", fart.getLength());
        bundle.putInt("embarrassment", fart.getSocialEmbarrassment());
        bundle.putInt("numberKids", fart.getCountChildren());
        bundle.putInt("ageListeners", fart.getAverageAge());
        bundle.putDouble("result", fart.getScore());
        bundle.putString("strGenderFactor", fart.getSex());

        return bundle;
    }

    private void navigateToDetailPage(Bundle bundle) {
        Fragment fragment = new DetailFragment();
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = dbFragment.getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
