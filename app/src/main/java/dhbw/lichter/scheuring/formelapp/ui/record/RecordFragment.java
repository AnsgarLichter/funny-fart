package dhbw.lichter.scheuring.formelapp.ui.record;

import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.io.IOException;

import dhbw.lichter.scheuring.formelapp.R;
import dhbw.lichter.scheuring.formelapp.util.MediaRecorderStatus;
import dhbw.lichter.scheuring.formelapp.util.Recorder;
import dhbw.lichter.scheuring.formelapp.util.Toaster;

public class RecordFragment extends Fragment implements View.OnClickListener {

    private Recorder recorder = new Recorder();
    private Toaster toaster;
    private String outputFile;
    private ImageButton input;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_record, container, false);
        View toastView = inflater.inflate(R.layout.custom_toast,  (ViewGroup) root.findViewById(R.id.custom_toast_layout));

        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        input = root.findViewById(R.id.recorder_record);
        input.setOnClickListener(this);
        toaster = new Toaster(getActivity(), toastView);
        return root;
    }


    @Override
    public void onClick(View v) {

        if(recorder.getStatus() != MediaRecorderStatus.RECORD) {
            outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";
            recorder.startRecording(outputFile);
            toaster.showSuccess(R.string.recording_started);
        } else {
            recorder.finishRecording();
            toaster.showSuccess(R.string.recording_finished);

            recorder.play(outputFile);
        }


    }
}
