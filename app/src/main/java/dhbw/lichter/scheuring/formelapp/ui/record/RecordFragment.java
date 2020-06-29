package dhbw.lichter.scheuring.formelapp.ui.record;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import dhbw.lichter.scheuring.formelapp.R;
import dhbw.lichter.scheuring.formelapp.ui.home.HomeFragment;
import dhbw.lichter.scheuring.formelapp.util.MediaRecorderStatus;
import dhbw.lichter.scheuring.formelapp.util.Navigator;
import dhbw.lichter.scheuring.formelapp.util.Recorder;
import dhbw.lichter.scheuring.formelapp.util.Toaster;

public class RecordFragment extends Fragment implements View.OnClickListener {
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;


    private boolean permissionToRecordAccepted = false;
    private final String[] permissions = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        };


    private final Recorder recorder = new Recorder();
    private Navigator navigator;
    private File file = new File("");
    private ImageButton input;

    private Toaster toaster;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_record, container, false);
        View toastView = inflater.inflate(R.layout.custom_toast,  (ViewGroup) root.findViewById(R.id.custom_toast_layout));

        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(false);

        input = root.findViewById(R.id.recorder_record);
        input.setOnClickListener(this);
        toaster = new Toaster(getActivity(), toastView);
        navigator = new Navigator(getParentFragmentManager());
        requestPermissions(permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        return root;
    }


    @Override
    public void onClick(View v) {
        long timestamp = System.currentTimeMillis() / 1000;
        boolean created = false;
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            if (recorder.getStatus() != MediaRecorderStatus.RECORD) {
                file = new File(requireContext().getFilesDir(), "funnyFart" + timestamp + ".ogg");
                if (!file.exists()) {
                    try {
                        created = file.createNewFile();
                    } catch (IOException ioe) {
                        Log.e("Create File Exception", "Unable to Create File", ioe);
                    }
                }

                if (created) {
                    recorder.startRecording(file.getAbsolutePath());
                    input.setBackgroundTintList(getContext().getColorStateList(R.color.activeRecorder));
                    toaster.showSuccess(R.string.recording_started);
                } else {
                    toaster.showSuccess(R.string.file_created);
                }
            } else {
                recorder.finishRecording();
                input.setBackgroundTintList(getContext().getColorStateList(R.color.toolbar));
                toaster.showSuccess(R.string.recording_finished);

                Bundle bundle = new Bundle();
                bundle.putString("audioPath", file.getAbsolutePath());
                navigator.navigate(new HomeFragment(), true, bundle);
            }
        } else {
            toaster.showError(R.string.permissions_not_granted);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_RECORD_AUDIO_PERMISSION && grantResults.length > 0) {
            boolean recordAudio = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            boolean writeExternalStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;
            boolean readExternalStorage = grantResults[2] == PackageManager.PERMISSION_GRANTED;
            if (recordAudio && writeExternalStorage && readExternalStorage) {
                permissionToRecordAccepted = true;
            }
        }

        if(!permissionToRecordAccepted) toaster.showError(R.string.permissions_not_granted);
    }

}
