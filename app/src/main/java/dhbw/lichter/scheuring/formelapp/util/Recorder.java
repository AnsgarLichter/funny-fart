package dhbw.lichter.scheuring.formelapp.util;

import android.Manifest;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;

import lombok.Getter;
import lombok.Setter;


public class Recorder {
    private MediaRecorder recorder;
    private MediaPlayer player;

    @Getter @Setter
    private MediaRecorderStatus status;



    public Recorder() {
        recorder = new MediaRecorder();

        status = MediaRecorderStatus.IDLE;
        player = new MediaPlayer();
    }

    public void startRecording(String path) {
        recorder.reset();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        //recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        //recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(path);
        try {
            recorder.prepare();
            recorder.start();
            status = MediaRecorderStatus.RECORD;
        } catch (IOException ioe) {
            Log.e("Recorder Exception", "Unable to start the audio recorder", ioe);
        }
    }

    public void finishRecording() {
        recorder.stop();
        recorder.release();

        status = MediaRecorderStatus.IDLE;
    }

    public void play(String path) {
        try {
            player.setDataSource(path);
            player.prepare();
            player.start();

            status = MediaRecorderStatus.IDLE;
        } catch (IOException ioe) {
            Log.e("Markus ist scheiße", "Markus ist noch beschissener", ioe);
        }

    }
}
