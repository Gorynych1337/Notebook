package com.example.lw33.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lw33.Note;
import com.example.lw33.NoteBook;
import com.example.lw33.R;
import com.example.lw33.adaptor.mAdapter;

public class List extends AppCompatActivity {

    NoteBook notebook;
    ListView lw;
    mAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        notebook = (NoteBook)getApplicationContext();

        setContentView(R.layout.list_act);

        adapter = new mAdapter(this);

        lw = findViewById(R.id.note_list);
        lw.setAdapter(adapter);

        lw.setOnItemClickListener(this::onItemClick);
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Intent intent = new Intent(this, Edit.class);

        intent.putExtra(notebook.NOTE_KEY, notebook.getNote(position));

        startActivityForResult(intent, notebook.EDIT_NOTE_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_CANCELED)
            return;

        Bundle args = data.getExtras();
        Note note = (Note) args.getSerializable(notebook.NOTE_KEY);

        if (resultCode == notebook.DELETE_CODE){
            notebook.deleteNote(note);
            lw.invalidateViews();
            return;
        }

        if (requestCode == notebook.ADD_NOTE_CODE){
            notebook.addNote(note);
        }
        else if (requestCode == notebook.EDIT_NOTE_CODE)
        {
            notebook.updateNote(note);
        }

        lw.invalidateViews();
    }

    public void CreateNote(View view)
    {
        Intent intent = new Intent(this, Edit.class);
        startActivityForResult(intent, notebook.ADD_NOTE_CODE);
    }
}
