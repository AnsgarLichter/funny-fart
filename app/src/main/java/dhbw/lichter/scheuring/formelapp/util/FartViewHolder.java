package dhbw.lichter.scheuring.formelapp.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;

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
    public ImageButton bPlay;
    public ImageButton bShare;


    private FartAdapter fartAdapter;
    private DatabaseFragment dbFragment;
    private Navigator navigator;


    FartViewHolder(View itemView, DatabaseFragment dbFragment, FartAdapter fartAdapter) {
        super(itemView);

        this.dbFragment = dbFragment;
        this.navigator = new Navigator(dbFragment.getFragmentManager());
        this.fartAdapter = fartAdapter;

        cardView = (CardView) itemView.findViewById(R.id.fart_card);
        fartGif = (ImageView) itemView.findViewById(R.id.fart_gif);
        fartName = (TextView) itemView.findViewById(R.id.card_fart_name);
        fartScore = (TextView) itemView.findViewById(R.id.card_fart_score);
        creationDate = (TextView) itemView.findViewById(R.id.card_creation_date);
        bDelete = itemView.findViewById(R.id.database_delete);
        bDetail = itemView.findViewById(R.id.database_detail);
        bPlay = itemView.findViewById(R.id.database_play);
        bShare = itemView.findViewById(R.id.database_share);

        bDelete.setOnClickListener(this);
        bDetail.setOnClickListener(this);
        bPlay.setOnClickListener(this);
        bShare.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.database_delete:
                this.onDeleteButtonClicked();
                break;
            case R.id.database_detail:
                this.onDetailButtonClicked();
                break;
            case R.id.database_play:
                this.onPlayButtonClicked();
                break;
            case R.id.database_share:
                this.onShareButtonClicked();
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

    private void onDetailButtonClicked() {
        Bundle bundle = this.createBundle();
        navigator.navigate(new DetailFragment(), true, bundle);
    }

    public void onDeleteButtonClicked() {
        Activity activity = dbFragment.getActivity();
        final long id = fart.getId();
        String name = fart.getName();
        String title = activity.getString(R.string.database_delete_security_query_title);
        String message = activity.getString(R.string.database_delete_security_query_message).replace(":name", "\"" + name + "\"");

        new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(Html.fromHtml("<font color=\"#FFFFFF\">" + message + "</font>"))
                .setPositiveButton(android.R.string.yes, this)
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void onPlayButtonClicked() {
        String audioPath = fart.getAudioPath();

        if (audioPath != "") {
            Recorder recorder = new Recorder();
            recorder.play(fart.getAudioPath());
        }
    }

    private void onShareButtonClicked() {
        File audioFile = new File(fart.getAudioPath());
        Context context = dbFragment.getContext();
        Uri fileUri = FileProvider.getUriForFile(dbFragment.getContext(), context.getApplicationContext().getPackageName() + ".provider", audioFile);
        context.grantUriPermission(context.getPackageName(), fileUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Intent share = new Intent();
        share.setAction(Intent.ACTION_SEND);
        share.setType("audio/*");
        share.putExtra(Intent.EXTRA_STREAM, fileUri);
        share.setPackage("com.whatsapp");
        share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        dbFragment.startActivity(share);
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
        bundle.putBoolean("isInDb", true);

        return bundle;
    }
}
