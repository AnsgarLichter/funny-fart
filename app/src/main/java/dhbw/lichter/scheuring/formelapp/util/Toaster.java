package dhbw.lichter.scheuring.formelapp.util;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import dhbw.lichter.scheuring.formelapp.R;

public class Toaster {

    private Toast toast;
//    private Context applicationContext;
    private View view;
    private ImageView icon;
    private TextView text;

    public Toaster(Context applicationContext, View view) {
//        this.applicationContext = applicationContext;
        toast = new Toast(applicationContext);
        this.view = view;
        icon = view.findViewById(R.id.custom_toast_icon);
        text = view.findViewById(R.id.custom_toast_txt);
    }

    //TODO: Apply different styles for error and success toasts
    public void showError(int resId) {
//        Toast toast = Toast.makeText(applicationContext, applicationContext.getResources().getString(resId), Toast.LENGTH_LONG);
        icon.setImageResource(R.drawable.ic_toast_error);
        text.setText(resId);
        toast.setGravity(Gravity.BOTTOM, 0, 250);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }

    public void showSuccess(int resId) {
//        Toast toast = Toast.makeText(applicationContext, applicationContext.getString(resId), Toast.LENGTH_SHORT);
        icon.setImageResource(R.drawable.ic_toast_success);
        text.setText(resId);
        toast.setGravity(Gravity.BOTTOM, 0, 250);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }
}
