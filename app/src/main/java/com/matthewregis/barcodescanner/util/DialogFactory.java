package com.matthewregis.barcodescanner.util;

import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.text.util.Linkify;

import com.afollestad.materialdialogs.MaterialDialog;

import timber.log.Timber;


public final class DialogFactory {

    public static MaterialDialog createCustomMaterialDialogInput(Context context, String title, String message, String hint, String prefill, String positive, MaterialDialog.InputCallback inputCallback, MaterialDialog.SingleButtonCallback singleButtonCallback) {
        try {
            return new MaterialDialog.Builder(context)
                    .title(title)
                    .content(message)
                    .inputType(InputType.TYPE_CLASS_NUMBER)
                    .positiveText(positive)
                    .input(hint, prefill, inputCallback)
                    .onPositive(singleButtonCallback)
                    .build();
        } catch (Exception ex) {
            Timber.e(ex, "Error on creating input dialog");
        }
        return null;
    }

    public static MaterialDialog createOkMaterialDialog(Context context, String message, DialogInterface.OnDismissListener dismissListener) {
        try {

            MaterialDialog okDialog = new MaterialDialog.Builder(context)
                    .positiveText("Ok")
                    .dismissListener(dismissListener)
                    .build();

            okDialog.setContent(message);
            Linkify.addLinks(okDialog.getContentView(), Linkify.ALL);
            return okDialog;

        } catch (Exception ex) {
            Timber.e(ex, "Error on creating input dialog");
        }
        return null;
    }
}
