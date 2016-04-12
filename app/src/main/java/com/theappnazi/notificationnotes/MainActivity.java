package com.theappnazi.notificationnotes;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.theappnazi.notificationnotes.datasources.NoteDataSource;
import com.theappnazi.notificationnotes.models.Note;
import com.theappnazi.notificationnotes.utils.MessageUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NoteDataSource noteDataSource;

    private LinearLayout linearLayout;
    private Toolbar toolbar;
    private Toolbar noNotesToolbar;

    private List<Note> noteList;

    private boolean isPersistent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        noNotesToolbar = (Toolbar) findViewById(R.id.toolbar_no_notes);
        setSupportActionBar(toolbar);

        linearLayout = (LinearLayout) findViewById(R.id.card_layout);

        isPersistent = false;

        noteDataSource = new NoteDataSource(this);
        noteDataSource.open();

        noteList = noteDataSource.getAllNotes();

        setupViews();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showAddNoteDialog(noteDataSource);
                }
            });
        }
    }

    private void setupViews() {
        if (!noteList.isEmpty()) {
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
        } else {
            setContentView(R.layout.no_notes);

            if (noNotesToolbar != null) {
                setSupportActionBar(noNotesToolbar);
                noNotesToolbar.setTitle(R.string.app_name);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

    public void showAddNoteDialog(NoteDataSource noteDataSource) {
        MessageUtils.showAddNoteDialog(MainActivity.this, noteDataSource, new MessageUtils.AlertDialogCallback() {

            @Override
            public void onButtonClick(DialogInterface dialogInterface, int id, String clickedButtonType) {
                //refresh layout
            }
        });
    }

    public void onNotifyPressed(View view) {
        int viewId = view.getId();
        MessageUtils.showNotifyDialog(MainActivity.this, noteList.get(viewId).getNotification_title(),
                noteList.get(viewId).getNotification_content(), noteList.get(viewId).isPersistent(), noteDataSource,
                new MessageUtils.AlertDialogCallback() {
                    @Override
                    public void onButtonClick(DialogInterface dialogInterface, int id, String clickedButtonType) {
                        //refresh the note list display here.
                    }
                });
    }

    @Override
    protected void onPause() {
        noteDataSource.close();
        super.onPause();
    }

    @Override
    protected void onResume() {
        noteDataSource.open();
        super.onResume();
    }

}
