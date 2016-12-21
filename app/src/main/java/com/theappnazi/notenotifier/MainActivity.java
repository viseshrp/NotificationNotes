package com.theappnazi.notenotifier;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.theappnazi.notenotifier.models.Note;
import com.theappnazi.notenotifier.utils.MessageUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LinearLayout linearLayout;

    private RelativeLayout noNotesLayout;
    private RelativeLayout mainContentLayout;
    private CoordinatorLayout mainActivityLayout;
    private View mProgressView;

    private List<Note> noteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mProgressView = findViewById(R.id.content_progress);
        linearLayout = (LinearLayout) findViewById(R.id.card_layout);
        noNotesLayout = (RelativeLayout) findViewById(R.id.no_notes_layout);
        mainContentLayout = (RelativeLayout) findViewById(R.id.content_main_layout);
        mainActivityLayout = (CoordinatorLayout) findViewById(R.id.activity_main_layout);

        setupViews();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showAddNoteDialog();
                }
            });
        }
    }

    private void setupViews() {

        showProgress(true);

        noteList = Note.listAll(Note.class);

        if (!noteList.isEmpty()) {
            mainContentLayout.setVisibility(View.VISIBLE);
            noNotesLayout.setVisibility(View.GONE);
            for (int i = 0; i < noteList.size(); i++) {
                ViewStub viewStub = new ViewStub(this);
                linearLayout.addView(viewStub);
                viewStub.setLayoutResource(R.layout.notes_history_card_layout);
                viewStub.inflate();

                CardView cardView = (CardView) linearLayout.getChildAt(i);
                LinearLayout linearLayout1 = (LinearLayout) cardView.getChildAt(0);

                LinearLayout linearLayout2 = (LinearLayout) linearLayout1.getChildAt(0);
                TextView noteDateView = (TextView) linearLayout2.getChildAt(1);

                LinearLayout linearLayout3 = (LinearLayout) linearLayout1.getChildAt(1);
                TextView noteTitleView = (TextView) linearLayout3.getChildAt(1);

                LinearLayout linearLayout4 = (LinearLayout) linearLayout1.getChildAt(2);
                TextView noteContentView = (TextView) linearLayout4.getChildAt(1);

                LinearLayout linearLayout5 = (LinearLayout) linearLayout1.getChildAt(3);
                Button button = (Button) linearLayout5.getChildAt(0);
                button.setId(i);

                noteDateView.setText(String.valueOf(noteList.get(i).getNote_date()));
                noteTitleView.setText(noteList.get(i).getNotification_title());

                String noteContent = noteList.get(i).getNotification_content();
                if (noteContent != null && !noteContent.equals("")) {
                    noteContentView.setText(noteContent);
                } else {
                    linearLayout4.setVisibility(View.GONE);
                }
            }
            showProgress(false);
        } else {
            showProgress(false);
            noNotesLayout.setVisibility(View.VISIBLE);
            mainContentLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mainContentLayout.setVisibility(show ? View.GONE : View.VISIBLE);
            mainContentLayout.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mainContentLayout.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mainContentLayout.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showAddNoteDialog() {
        MessageUtils.showAddNoteDialog(MainActivity.this, new MessageUtils.AlertDialogCallback() {

            @Override
            public void onButtonClick(DialogInterface dialogInterface, int id, String clickedButtonType) {
                //refresh layout
                Snackbar.make(mainActivityLayout, "Note added!", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    public void onNotifyPressed(View view) {
        int viewId = view.getId();
        MessageUtils.showNotifyDialog(MainActivity.this, noteList.get(viewId).getNotification_title(),
                noteList.get(viewId).getNotification_content(), noteList.get(viewId).isPersistent(),
                new MessageUtils.AlertDialogCallback() {
                    @Override
                    public void onButtonClick(DialogInterface dialogInterface, int id, String clickedButtonType) {
                        //refresh the note list display here.
                        Snackbar.make(mainActivityLayout, "You've been notified!", Snackbar.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
