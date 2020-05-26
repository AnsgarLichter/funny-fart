package dhbw.lichter.scheuring.formelapp.util;

import android.app.Activity;
import android.widget.Toast;

public class Toaster {

    private Activity activity = null;

    public Toaster(Activity activity) {
        this.activity = activity;
    }

    //TODO: Apply different styles for error and success toasts
    public void showError(int resId) {
        Toast toast = Toast.makeText(activity, activity.getString(resId), Toast.LENGTH_LONG);
        toast.show();
    }

    public void showSuccess(int resId) {
        Toast toast = Toast.makeText(activity, activity.getString(resId), Toast.LENGTH_SHORT);
        toast.show();
    }
}
