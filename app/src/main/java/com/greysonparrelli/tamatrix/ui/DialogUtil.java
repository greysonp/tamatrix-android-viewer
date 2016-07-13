package com.greysonparrelli.tamatrix.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.Patterns;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.greysonparrelli.tamatrix.storage.Preferences;

public class DialogUtil {

    public static void showServerUrlDialog(
            final Context context,
            @Nullable final DialogInterface.OnDismissListener dismissListener) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .title("Server URL")
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input(null, Preferences.getInstance().getBaseUrl(), new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence inputChars) {
                        String input = inputChars.toString();

                        // Make sure we match the URL regex
                        if (Patterns.WEB_URL.matcher(input).matches()) {
                            // If we don't have the protocol prefix, add it. Retrofit needs it.
                            if (!input.startsWith("http://") && !input.startsWith("https://")) {
                                input = "http://" + input;
                            }
                            Preferences.getInstance().setBaseUrl(input);
                        } else {
                            // If they don't enter a valid URL, toast and then show the dialog again
                            Toast.makeText(context, "Please enter a valid URL.", Toast.LENGTH_SHORT).show();
                            showServerUrlDialog(context, dismissListener);
                        }
                    }
                });
        if (dismissListener != null) {
            builder.dismissListener(dismissListener);
        }
        builder.show();
    }
}
