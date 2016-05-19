package mx.com.cuubozsoft.notetaker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Optional;

/**
 * A placeholder fragment containing a simple view.
 */
public class EditNoteActivityFragment extends Fragment
{
    public static final int RESULT_DELETE = -500;
    private boolean isInEditMode = true;
    private boolean isAddingNote = true;

    public EditNoteActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_edit_note, container, false);

        setHasOptionsMenu(true);

        Button saveButton = (Button) rootView.findViewById(R.id.saveButton);
        Button cancelButton = (Button) rootView.findViewById(R.id.cancelButton);
        EditText titleEditText = (EditText)rootView.findViewById(R.id.editTextTitle);
        EditText noteEditText = (EditText) rootView.findViewById(R.id.multilineTextNote);
        TextView viewDate = (TextView) rootView.findViewById(R.id.textViewDate);

        Intent editInetnt = getActivity().getIntent();
        Optional<Serializable> noteData = Optional.ofNullable(editInetnt.getSerializableExtra("Note"));

        noteData.ifPresent(info -> {
            Note editableNote = (Note)info;
            titleEditText.setText(editableNote.getTitle());
            noteEditText.setText(editableNote.getContent());
            DateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            viewDate.setText(dateformat.format(editableNote.getDate()));

            isInEditMode = false;
            titleEditText.setEnabled(isInEditMode);
            noteEditText.setEnabled(isInEditMode);

            saveButton.setText(R.string.edit);
            isAddingNote = false;
        });

        cancelButton.setOnClickListener(view -> {
            getActivity().setResult(Activity.RESULT_CANCELED,new Intent());
            getActivity().finish();
        });

        saveButton.setOnClickListener((View v) -> {
            if(isInEditMode)
            {
                Note note = new Note(titleEditText.getText().toString(),noteEditText.getText().toString(),Calendar.getInstance().getTime());

                Intent returnIntent = new Intent();
                returnIntent.putExtra("Note",note);

                getActivity().setResult(Activity.RESULT_OK,returnIntent);
                getActivity().finish();


            }
            else
            {
                isInEditMode = true;
                saveButton.setText(R.string.save);
                titleEditText.setEnabled(isInEditMode);
                noteEditText.setEnabled(isInEditMode);
            }
        });

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        if(! isAddingNote){
            inflater.inflate(R.menu.menu_main,menu);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.confirm_delete);
            builder.setMessage(R.string.alert_delete);
            builder.setPositiveButton(R.string.delete, (dialog, i)->{

                Intent returnIntent = new Intent();

                getActivity().setResult(RESULT_DELETE,returnIntent);
                getActivity().finish();

            });
            builder.setNegativeButton(R.string.cancel_button,(dialog, i)->{

            });
            builder.create().show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
