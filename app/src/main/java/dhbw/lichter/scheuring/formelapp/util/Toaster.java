package dhbw.lichter.scheuring.formelapp.util;

import android.content.Context;
import android.widget.Toast;

public class Toaster {

    private Context applicationContext;

    public Toaster(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    //TODO: Apply different styles for error and success toasts
    public void showError(int resId) {
        Toast toast = Toast.makeText(applicationContext, applicationContext.getResources().getString(resId), Toast.LENGTH_LONG);
        toast.show();
    }

    public void showSuccess(int resId) {
        Toast toast = Toast.makeText(applicationContext, applicationContext.getString(resId), Toast.LENGTH_SHORT);
        toast.show();
    }
}
