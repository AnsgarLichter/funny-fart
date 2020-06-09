package dhbw.lichter.scheuring.formelapp.util;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.util.Log;

import java.io.IOException;

import lombok.Getter;
import lombok.Setter;


public class Recorder {
    private static final String LOG_TAG = "MediaRecorder";

    private MediaRecorder recorder;
    private MediaPlayer player;

    @Getter @Setter
    private MediaRecorderStatus status;



    public Recorder() {
        recorder = new MediaRecorder();
        status = MediaRecorderStatus.IDLE;
        player = new MediaPlayer();

        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.reset();
                status = MediaRecorderStatus.IDLE;
            }
        });
    }

    public void startRecording(String path) {
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        recorder.setOutputFile(path);

        try {
            recorder.prepare();
            recorder.start();
            status = MediaRecorderStatus.RECORD;
        } catch (IOException ioe) {
            Log.e(LOG_TAG, "Unable to start the audio recorder", ioe);
        }
    }

    public void finishRecording() {
        recorder.stop();

        status = MediaRecorderStatus.IDLE;
    }

    public void play(String path) {
        try {
            player.setDataSource(path);
            player.prepare();
            player.start();

            status = MediaRecorderStatus.PLAY;
        } catch (IOException ioe) {
            Log.e(LOG_TAG, "Unable to play audio", ioe);
        }

    }
}
