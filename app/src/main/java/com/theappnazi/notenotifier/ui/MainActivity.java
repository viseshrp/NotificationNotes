package com.theappnazi.notenotifier.ui;

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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.theappnazi.notenotifier.R;
import com.theappnazi.notenotifier.adapters.NoteItemsAdapter;
import com.theappnazi.notenotifier.models.Note;
import com.theappnazi.notenotifier.utils.MessageUtils;
import com.theappnazi.notenotifier.views.MyRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.note_item_list)
    MyRecyclerView mNoteItemList;

    @BindView(R.id.no_notes_layout)
    RelativeLayout noNotesLayout;

    @BindView(R.id.activity_main_layout)
    CoordinatorLayout mainActivityLayout;

    @BindView(R.id.content_progress)
    View mProgressView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private List<Note> noteList;

    private NoteItemsAdapter noteItemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

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
            mNoteItemList.setVisibility(View.VISIBLE);
            noNotesLayout.setVisibility(View.GONE);

            //recyclerview setup
            mNoteItemList.setHasFixedSize(true);
            RecyclerView.LayoutManager mLayoutManger = new LinearLayoutManager(this);
            mNoteItemList.setLayoutManager(mLayoutManger);
            noteItemsAdapter = new NoteItemsAdapter(this, new NoteItemsAdapter.ItemTapListener() {
                @Override
                public void onTap(Note note) {
                    showNotifyDialog(note);
                }
            });
            mNoteItemList.setAdapter(noteItemsAdapter);
            setDataset(noteList);

            showProgress(false);
        } else {
            showProgress(false);
            noNotesLayout.setVisibility(View.VISIBLE);
            mNoteItemList.setVisibility(View.GONE);
        }
    }

    private void setDataset(List<Note> noteList) {
        noteItemsAdapter.setDataset(noteList);
        noteItemsAdapter.notifyDataSetChanged();
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

            mNoteItemList.setVisibility(show ? View.GONE : View.VISIBLE);
            mNoteItemList.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mNoteItemList.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mNoteItemList.setVisibility(show ? View.GONE : View.VISIBLE);
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

                //mNoteItemList.invalidate();
                //noteItemsAdapter.notifyDataSetChanged();
                Snackbar.make(mainActivityLayout, "Note added!", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    public void showNotifyDialog(Note note) {
        MessageUtils.showNotifyDialog(MainActivity.this, note.getNotificationId(), note.getNotification_title(),
                note.getNotification_content(), note.isPersistent(),
                new MessageUtils.AlertDialogCallback() {
                    @Override
                    public void onButtonClick(DialogInterface dialogInterface, int id, String clickedButtonType) {
                        //refresh the note list display here.

                        //mNoteItemList.invalidate();
                        //noteItemsAdapter.notifyDataSetChanged();
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
