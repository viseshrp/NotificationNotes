package com.theappnazi.notenotifier.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.theappnazi.notenotifier.ui.MainActivity;
import com.theappnazi.notenotifier.R;
import com.theappnazi.notenotifier.models.Note;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by vises_000 on 4/3/2016.
 */
public class MessageUtils {
    public static void displayToast(Context context, int messageStringReference) {
        if (context != null && context.getResources() != null)
            Toast.makeText(context, messageStringReference, Toast.LENGTH_SHORT).show();
    }

    public static void displayToast(Context context, String message) {
        if (context != null && context.getResources() != null)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public interface AlertDialogCallback {
        void onButtonClick(DialogInterface dialogInterface, int id, String clickedButtonType);
    }

    public static void showAddNoteDialog(final Context context, final AlertDialogCallback alertDialogCallback) {
        if (context != null && context.getResources() != null) {
            final Dialog dialog = new Dialog(context);
            dialog.setTitle(R.string.add_note_dialog_title);
            dialog.setContentView(R.layout.layout_add_note_dialog);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            Button button = (Button) dialog.findViewById(R.id.add_button);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText noteTitle = (EditText) dialog.findViewById(R.id.notification_title_edittext);
                    EditText noteContent = (EditText) dialog.findViewById(R.id.notification_content_edittext);
                    CheckBox persistentCheckBox = (CheckBox) dialog.findViewById(R.id.checkbox_persistent);

                    String notificationTitle = noteTitle.getText().toString();
                    String notificationContent = noteContent.getText().toString();

                    String noteDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

                    if (ValidationUtils.checkValidity(notificationTitle, AppConstants.DATA_TYPE_GENERAL_TEXT, context)) {
                        //pass notification id, then add to list
                        int notificationId = NotificationUtils.getNotificationId();
                        //notid randomly generated newly each time

                        //add to list
                        NotificationUtils.addToCurrentList(context, notificationId, true);

                        NotificationUtils.showNewNoteNotification(context, MainActivity.class, notificationId, notificationTitle, notificationContent, persistentCheckBox.isChecked());
                        Note note = new Note(notificationId, notificationTitle, notificationContent, noteDate, persistentCheckBox.isChecked());
                        note.save();
                        dialog.dismiss();
                    }

                    alertDialogCallback.onButtonClick(dialog, 0, AppConstants.ADD_BUTTON_CLICKED);
                }
            });
        }
    }

    public static void showNotifyDialog(final Context context, final int notificationId, final String notificationTitle, final String notificationContent, final boolean isPersistent, final AlertDialogCallback alertDialogCallback) {
        if (context != null && context.getResources() != null) {
            final Dialog dialog = new Dialog(context);
            dialog.setTitle(R.string.add_note_dialog_title);
            dialog.setContentView(R.layout.layout_notify_note_dialog);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            EditText noteTitle = (EditText) dialog.findViewById(R.id.notification_title_edittext);
            EditText noteContent = (EditText) dialog.findViewById(R.id.notification_content_edittext);
            final CheckBox persistentCheckBox = (CheckBox) dialog.findViewById(R.id.checkbox_persistent);

            noteTitle.setText(notificationTitle);
            noteContent.setText(notificationContent);
            persistentCheckBox.setChecked(isPersistent);

            Button button = (Button) dialog.findViewById(R.id.add_button);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (ValidationUtils.checkValidity(notificationTitle, AppConstants.DATA_TYPE_GENERAL_TEXT, context)) {

                        //add to list
                        NotificationUtils.addToCurrentList(context, notificationId, false);

                        //updates existing notif
                        NotificationUtils.showNewNoteNotification(context, MainActivity.class, notificationId, notificationTitle, notificationContent, isPersistent);

                        //find note object
                        Note note = Note.find(Note.class, "notification_Id = ?", String.valueOf(notificationId)).get(0);

                        //dont do the db update until something has changed
                        if (note != null && !note.getNotification_title().equals(notificationTitle) && !note.getNotification_content().equals(notificationContent)) {
                            note.setNotification_title(notificationTitle);
                            note.setNotification_content(notificationContent);
                            note.setNote_date(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                            note.setPersistent(isPersistent);
                            note.save();
                        }

                        dialog.dismiss();
                    }

                    alertDialogCallback.onButtonClick(dialog, 0, AppConstants.ADD_BUTTON_CLICKED);
                }
            });
        }
    }


}
