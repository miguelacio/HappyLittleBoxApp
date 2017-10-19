package mx.happylittlebox.repartidor.happylittleboxapp.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;

/**
 * Created by Beardboy02 on 4/21/16.
 */
public class DialogUtil {
    public static ProgressDialog showProgressDialog(Context context, String title, String message) {
        return ProgressDialog.show(context, title, message, true, false);
    }

    public static AlertDialog createSimpleDialog(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null);
        return builder.create();
    }
}
