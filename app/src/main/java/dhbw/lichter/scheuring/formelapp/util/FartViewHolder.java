package dhbw.lichter.scheuring.formelapp.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.FileProvider;

import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;

import dhbw.lichter.scheuring.formelapp.R;
import dhbw.lichter.scheuring.formelapp.ui.database.DatabaseFragment;
import dhbw.lichter.scheuring.formelapp.ui.detail.DetailFragment;

public class FartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, DialogInterface.OnClickListener {
    public Fart fart;
    public final ImageView fartGif;
    public final TextView fartName;
    public final TextView fartScore;
    public final TextView creationDate;
    public final ImageButton bDelete;
    public final ImageButton bDetail;
    public final ImageButton bPlay;
    public final ImageButton bShare;


    private final FartAdapter fartAdapter;
    private final DatabaseFragment dbFragment;
    private final Navigator navigator;


    FartViewHolder(View itemView, DatabaseFragment dbFragment, FartAdapter fartAdapter) {
        super(itemView);

        this.dbFragment = dbFragment;
        this.navigator = new Navigator(dbFragment.getParentFragmentManager());
        this.fartAdapter = fartAdapter;

        fartGif = itemView.findViewById(R.id.fart_gif);
        fartName = itemView.findViewById(R.id.card_fart_name);
        fartScore = itemView.findViewById(R.id.card_fart_score);
        creationDate = itemView.findViewById(R.id.card_creation_date);
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
        assert activity != null;

        String name = fart.getName();
        String title = activity.getString(R.string.database_delete_security_query_title);
        String message = activity.getString(R.string.database_delete_security_query_message).replace(":name", "\"" + name + "\"");

        new AlertDialog.Builder(activity)
                .setTitle(title)
                .setMessage(HtmlCompat.fromHtml("<font color=\"#FFFFFF\">" + message + "</font>", HtmlCompat.FROM_HTML_MODE_LEGACY))
                .setPositiveButton(android.R.string.yes, this)
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void onPlayButtonClicked() {
        String audioPath = fart.getAudioPath();

        if (!audioPath.equals("")) {
            Recorder recorder = new Recorder();
            recorder.play(fart.getAudioPath());
        }
    }

    private void onShareButtonClicked() {
        File audioFile = new File(fart.getAudioPath());
        Context context = dbFragment.getContext();

        assert context != null;

        Uri fileUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", audioFile);
        context.grantUriPermission(context.getPackageName(), fileUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Intent share = new Intent();
        share.setAction(Intent.ACTION_SEND);
        share.setType("audio/*");
        share.putExtra(Intent.EXTRA_STREAM, fileUri);
        share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        dbFragment.startActivity(share);
    }

    private Bundle createBundle() {
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
