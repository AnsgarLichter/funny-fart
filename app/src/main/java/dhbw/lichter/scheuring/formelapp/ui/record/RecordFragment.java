package dhbw.lichter.scheuring.formelapp.ui.record;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.IOException;

import dhbw.lichter.scheuring.formelapp.R;
import dhbw.lichter.scheuring.formelapp.ui.home.HomeFragment;
import dhbw.lichter.scheuring.formelapp.util.MediaRecorderStatus;
import dhbw.lichter.scheuring.formelapp.util.Navigator;
import dhbw.lichter.scheuring.formelapp.util.Recorder;
import dhbw.lichter.scheuring.formelapp.util.Toaster;

public class RecordFragment extends Fragment implements View.OnClickListener {
    private static final String LOG_TAG = "AudioRecord";
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;


    private boolean permissionToRecordAccepted = false;
    private String[] permissions = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        };


    private Recorder recorder = new Recorder();
    private Navigator navigator;
    private File file = new File("");

    private Toaster toaster;
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
        navigator = new Navigator(getFragmentManager());
        requestPermissions(permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        return root;
    }


    @Override
    public void onClick(View v) {
        Long timestamp = System.currentTimeMillis() / 1000;
        if(recorder.getStatus() != MediaRecorderStatus.RECORD) {
            file = new File(getContext().getFilesDir(), "funnyFart" + timestamp.toString() + ".ogg");
            if(!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException ioe) {
                    Log.e("Create File Exception", "Unable to Create File", ioe);
                }
            }
            recorder.startRecording(file.getAbsolutePath());
            toaster.showSuccess(R.string.recording_started);
        } else {
            recorder.finishRecording();
            toaster.showSuccess(R.string.recording_finished);

            Bundle bundle = new Bundle();
            bundle.putString("audioPath", file.getAbsolutePath());
            navigator.navigate(new HomeFragment(), true, bundle);

            // recorder.play(file.getAbsolutePath());

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }

        if (!permissionToRecordAccepted ) toaster.showError(R.string.permissions_not_granted);
        else toaster.showSuccess(R.string.permissions_granted);
    }
}
