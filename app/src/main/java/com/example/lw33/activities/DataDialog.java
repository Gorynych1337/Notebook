package com.example.lw33.activities;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.lw33.NoteBook;

import java.util.Calendar;

public class DataDialog extends DialogFragment {

    private final Calendar dateAndTime = Calendar.getInstance();
    Context ctx;
    private Edit parent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ctx = getContext();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Настройте дату публикации")
                .setPositiveButton("Хорошо", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        parent = (Edit) getActivity();
                        setData();
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Взять текущие", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id) {
                        ((Edit) getActivity()).SendNote(dateAndTime.getTime());
                        dialog.cancel();
                    }
                });
        return builder.create();
    }

    private void setData()
    {
        new DatePickerDialog(ctx, d,
                dateAndTime.get(YEAR),
                dateAndTime.get(MONTH),
                dateAndTime.get(DAY_OF_MONTH))
                .show();
    }

    private void setTime()
    {
        new TimePickerDialog(ctx, t,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true)
                .show();
    }


    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            setTime();
        }
    };
    TimePickerDialog.OnTimeSetListener t=new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);

            parent.SendNote(dateAndTime.getTime());
        }
    };
}
