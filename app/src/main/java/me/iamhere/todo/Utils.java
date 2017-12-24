package me.iamhere.todo;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

//util class for toasts
public class Utils
{
    static Toast toast;
    //shows only one toast, cancels if already a toast is being displayed
    static void showToast(String message, Context context)
    {
        if(toast!=null)
            toast.cancel();
        Activity activity = (Activity) context;
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View layout = layoutInflater.inflate(R.layout.toast, (ViewGroup) activity.findViewById(R.id.toast_layout));
        TextView text = layout.findViewById(R.id.text);
        text.setText(message);
        toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(layout);
        toast.show();
    }
}
