package com.theappnazi.notificationnotes;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.theappnazi.notificationnotes.datasources.NoteDataSource;
import com.theappnazi.notificationnotes.utils.MessageUtils;

public class MainActivity extends AppCompatActivity {

    private NoteDataSource noteDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        noteDataSource = new NoteDataSource(this);
        noteDataSource.open();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddNoteDialog(noteDataSource);
            }
        });
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
        MessageUtils.showAddNoteDialog(this, noteDataSource, new MessageUtils.AlertDialogCallback() {

            @Override
            public void onButtonClick(DialogInterface dialogInterface, int id, String clickedButtonType) {

                if (dialogInterface != null)
                    dialogInterface.dismiss();

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
