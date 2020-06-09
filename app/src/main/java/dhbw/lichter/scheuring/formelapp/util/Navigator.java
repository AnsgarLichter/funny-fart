package dhbw.lichter.scheuring.formelapp.util;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import dhbw.lichter.scheuring.formelapp.R;
import dhbw.lichter.scheuring.formelapp.ui.detail.DetailFragment;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Navigator {

    private FragmentManager fragmentManager;

    public void navigate(Fragment fragment, Boolean addToBackStack, Bundle bundle) {
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
        if(addToBackStack) fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void navigate(Fragment fragment, Boolean addToBackStack) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
        if(addToBackStack) fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
