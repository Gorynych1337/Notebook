package com.example.lw33.activities;

import static java.util.Calendar.getInstance;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.lw33.Note;
import com.example.lw33.NoteBook;
import com.example.lw33.R;

import java.util.Calendar;
import java.util.Date;

public class Edit extends AppCompatActivity implements TextWatcher {

    NoteBook notebook;
    EditText text, header;
    Spinner spinner;
    Button ok_btn;
    Note note;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        notebook = (NoteBook) getApplicationContext();

        setContentView(R.layout.edit_act);

        text = findViewById(R.id.note_edittext);
        text.addTextChangedListener(this);

        header = findViewById(R.id.header_edittext);
        header.addTextChangedListener(this);

        spinner = (Spinner) findViewById(R.id.note_priority_spinner);

        spinner.setAdapter(new ArrayAdapter<Note.Priority>
                (this, R.layout.spinner_element, R.id.spinner_tw, Note.Priority.values()));

        ok_btn = findViewById(R.id.ok_button);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Bundle args = getIntent().getExtras();

        if (args == null){
            ok_btn.setTextColor(getColor(R.color.disable_light_blue));
            ok_btn.setBackgroundColor(getColor(R.color.disable_dark_blue));
            ok_btn.setEnabled(false);
            return;
        }

        note = (Note) args.get(notebook.NOTE_KEY);

        header.setText(note.getHeader());
        text.setText(note.getText());
        spinner.setSelection(note.getPriority().getPriorityRange());
    }

    public void SaveNote(View view)
    {
        if (note == null)
            note = new Note();

        note.setHeader(header.getText().toString());
        note.setText(text.getText().toString());
        note.setPriority( (Note.Priority) spinner.getSelectedItem());

        DataDialog myDialogFragment = new DataDialog();
        FragmentManager manager = getSupportFragmentManager();

        FragmentTransaction transaction = manager.beginTransaction();
        myDialogFragment.show(transaction, "dialog");
    }

    public void SendNote(Date date){

        note.setDate(date);

        Intent intent = new Intent(this, List.class);

        intent.putExtra(notebook.NOTE_KEY, note);

        setResult(RESULT_OK, intent);
        finish();
    }

    public void DeleteNote(View view) {

        Intent intent = new Intent(this, List.class);

        if (note == null){
            setResult(RESULT_CANCELED, intent);
        }
        else
        {
            intent.putExtra(notebook.NOTE_KEY, note);
            setResult(notebook.DELETE_CODE, intent);
        }
        finish();
    }

    public void CancelChange(View view)
    {
        Intent intent = new Intent(this, List.class);

        setResult(RESULT_CANCELED, intent);
        finish();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (header.getText().length() == 0 || text.getText().length() == 0)
        {
            ok_btn.setTextColor(getColor(R.color.disable_light_blue));
            ok_btn.setBackgroundColor(getColor(R.color.disable_dark_blue));
            ok_btn.setEnabled(false);
        }
        else
        {
            ok_btn.setTextColor(getColor(R.color.light_blue));
            ok_btn.setBackgroundColor(getColor(R.color.dark_blue));
            ok_btn.setEnabled(true);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
