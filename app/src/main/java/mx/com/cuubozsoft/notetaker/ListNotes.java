package mx.com.cuubozsoft.notetaker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ListNotes extends AppCompatActivity
{
    private List<Note> noteList = new ArrayList<>();
    private int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_notes);
        noteList.add(new Note("first note","bla bla",new Date()));
        noteList.add(new Note("second note","bla bla",new Date()));
        noteList.add(new Note("third note","bla bla",new Date()));
        noteList.add(new Note("fourth note","bla bla",new Date()));
        noteList.add(new Note("fifth note","bla bla",new Date()));

        populateList();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int itemId = item.getItemId();

        if(itemId == R.id.add_menu)
        {
            Intent editNoteIntent = new Intent(this,EditNoteActivity.class);
            startActivityForResult(editNoteIntent,REQUEST_CODE);

        }
        return true;
    }

    private void populateList() {
        ListView notesListView = (ListView) findViewById(R.id.noteListView);

        List<String> notelist = noteList.stream().map(n -> n.getTitle()).collect(Collectors.toList());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1, notelist);
        notesListView.setAdapter(adapter);
    }
}
