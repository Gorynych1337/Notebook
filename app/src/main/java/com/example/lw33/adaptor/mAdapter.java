package com.example.lw33.adaptor;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.lw33.R;
import com.example.lw33.Note;
import com.example.lw33.NoteBook;

import java.util.ArrayList;

public class mAdapter extends ArrayAdapter<Note> {

    Context ctx;
    NoteBook notebook;

    public mAdapter(@NonNull Context context)
    {
        super(context, R.layout.list_element, new ArrayList<Note>());

        ctx = context;
        notebook = (NoteBook) context.getApplicationContext();
    }

    @Override
    public int getCount() {
        return notebook.getNotesCount();
    }

    @Override
    public Note getItem(int position) {
        return notebook.getNote(position);
    }

    @Override
    public long getItemId(int position) {
        return notebook.getNote(position).getID();
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater =
                (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.list_element, parent, false);
        TextView headerTextView = (TextView) view.findViewById(R.id.header_text);
        TextView textTextView = (TextView) view.findViewById(R.id.note_text);
        TextView priorityTextView = (TextView) view.findViewById(R.id.note_priority);
        TextView dataTextView = (TextView) view.findViewById(R.id.note_data);

        Note note = notebook.getNote(position);

        headerTextView.setText(note.getHeader());
        textTextView.setText(note.getText());
        priorityTextView.setText(note.getPriority().toString());

        String dateStr = DateUtils.formatDateTime(ctx,
                note.getDate().getTime(),
        DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_SHOW_TIME);

        dataTextView.setText(dateStr);

        return view;
    }
}
