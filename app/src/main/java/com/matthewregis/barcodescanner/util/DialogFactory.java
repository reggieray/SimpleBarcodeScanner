package com.matthewregis.barcodescanner.util;

import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;

import com.afollestad.materialdialogs.MaterialDialog;
import com.matthewregis.barcodescanner.R;

import timber.log.Timber;


public final class DialogFactory {

    public static MaterialDialog createCustomMaterialDialogInput(Context context, String title, String message, String hint, String prefill, String positive, MaterialDialog.InputCallback inputCallback, MaterialDialog.SingleButtonCallback singleButtonCallback) {
        try {
            return new MaterialDialog.Builder(context)
                    .title(title)
                    .content(message)
                    .inputType(InputType.TYPE_CLASS_NUMBER)
                    .positiveText(positive)
                    .negativeText(R.string.Cancel)
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
            return new MaterialDialog.Builder(context)
                    .positiveText(R.string.Ok)
                    .dismissListener(dismissListener)
                    .content(message)
                    .build();
        } catch (Exception ex) {
            Timber.e(ex, "Error on creating input dialog");
        }
        return null;
    }

    public static MaterialDialog createConfirmMaterialDialog(Context context, String message, MaterialDialog.SingleButtonCallback positive, MaterialDialog.SingleButtonCallback negative) {
        try {
            return new MaterialDialog.Builder(context)
                    .positiveText(R.string.Ok)
                    .onPositive(positive)
                    .negativeText(R.string.Cancel)
                    .onNegative(negative)
                    .content(message)
                    .build();
        } catch (Exception ex) {
            Timber.e(ex, "Error on creating input dialog");
        }
        return null;
    }
}
