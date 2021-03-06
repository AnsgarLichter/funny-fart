package dhbw.lichter.scheuring.formelapp.ui.help;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.Objects;

import dhbw.lichter.scheuring.formelapp.R;
import io.github.kexanie.library.MathView;

public class HelpFragment extends Fragment {
    public MathView formula;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_help, container, false);

        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(false);


        formula = root.findViewById(R.id.help_formula);
        formula.setEngine(MathView.Engine.MATHJAX);
        formula.setText(getString(R.string.help_formula));
        return root;
    }
}
