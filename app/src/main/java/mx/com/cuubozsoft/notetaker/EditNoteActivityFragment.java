package mx.com.cuubozsoft.notetaker;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A placeholder fragment containing a simple view.
 */
public class EditNoteActivityFragment extends Fragment {

    private boolean isInEditMode = true;

    public EditNoteActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_edit_note, container, false);

        Button saveButton = (Button) rootView.findViewById(R.id.saveButton);

        saveButton.setOnClickListener((View v) -> {
            EditText titleEditText = (EditText)rootView.findViewById(R.id.editTextTitle);
            EditText noteEditText = (EditText) rootView.findViewById(R.id.multilineTextNote);
            TextView viewDate = (TextView) rootView.findViewById(R.id.textViewDate);
            String buttonCaptionNestState = "";
            String date = "";

            if(isInEditMode)
            {
//                buttonCaptionNestState = "Edit";
                DateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                date = dateformat.format(Calendar.getInstance().getTime());
                viewDate.setText(date);

                Note note = new Note(titleEditText.getText().toString(),noteEditText.getText().toString(),Calendar.getInstance().getTime());

                Intent returnIntent = new Intent();
                returnIntent.putExtra("Note",note);

                getActivity().setResult(Activity.RESULT_OK,returnIntent);
                getActivity().finish();


            }
            else
            {
//                buttonCaptionNestState = "Save";
            }

//            saveButton.setText(buttonCaptionNestState);
//            isInEditMode = !isInEditMode;
//            titleEditText.setEnabled(isInEditMode);
//            noteEditText.setEnabled(isInEditMode);

        });

        return rootView;
    }
}
