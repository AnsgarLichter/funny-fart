package dhbw.lichter.scheuring.formelapp.util;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import dhbw.lichter.scheuring.formelapp.R;

public class Toaster {

    private final Toast toast;
    private final View view;
    private final ImageView icon;
    private final TextView text;

    public Toaster(Context applicationContext, View view) {
        toast = new Toast(applicationContext);
        this.view = view;
        icon = view.findViewById(R.id.custom_toast_icon);
        text = view.findViewById(R.id.custom_toast_txt);
    }

    public void showError(int resId) {
        icon.setImageResource(R.drawable.ic_toast_error);
        text.setText(resId);
        toast.setGravity(Gravity.BOTTOM, 0, 250);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }

    public void showSuccess(int resId) {
        icon.setImageResource(R.drawable.ic_toast_success);
        text.setText(resId);
        toast.setGravity(Gravity.BOTTOM, 0, 250);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }
}
