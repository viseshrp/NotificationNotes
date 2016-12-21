package com.theappnazi.notenotifier.utils;

import android.content.Context;

import com.theappnazi.notenotifier.R;

/**
 * Created by vises_000 on 4/7/2016.
 */
public class ValidationUtils {
    public static boolean checkValidity(String data, int dataType, Context context) {
        boolean isValid = false;
        data = data.trim();

        if (data.isEmpty()) {
            MessageUtils.displayToast(context, R.string.data_invalid_toast);
            isValid = false;
        } else {
            if (dataType == AppConstants.DATA_TYPE_GENERAL_TEXT)
                isValid = true;
        }
        return isValid;
    }
}
