package mx.com.cuubozsoft.notetaker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ListNotes extends AppCompatActivity
{
    private List<Note> noteList = new ArrayList<>();
    private int REQUEST_CODE = 1;
    private ListView notesListView;
    private int actualNote = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_notes);


        notesListView = (ListView) findViewById(R.id.noteListView);
        notesListView.setOnItemClickListener(
                (adapterView, view, itemIndex, l) -> {
                    Intent editIntent = new Intent(view.getContext(),EditNoteActivity.class);
                    editIntent.putExtra("Note",noteList.get(itemIndex));
                    actualNote = itemIndex;
                    startActivityForResult(editIntent,REQUEST_CODE);
                }
        );

        registerForContextMenu(notesListView);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode == RESULT_CANCELED)
        {
            return;
        }

        if(resultCode == EditNoteActivityFragment.RESULT_DELETE)
        {
            noteList.remove(actualNote);
            actualNote = -1;
            populateList();

        }

        Optional<Serializable> result = Optional.ofNullable(data.getSerializableExtra("Note"));

        result.ifPresent(value -> {
            Note newNote =(Note)value;

            if(actualNote == -1)
            {
                noteList.add(newNote);
            }
            else
            {
                noteList.set(actualNote,newNote);
                actualNote = -1;
            }
            populateList();
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        noteList.remove(info.position);

        populateList();

        return true;
    }

    private void populateList()
    {
        List<String> notelist = noteList.stream().map(n -> n.getTitle()).collect(Collectors.toList());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1, notelist);
        notesListView.setAdapter(adapter);
    }
}
