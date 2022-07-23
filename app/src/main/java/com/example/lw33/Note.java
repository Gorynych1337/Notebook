package com.example.lw33;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

public class Note implements Serializable {

    public enum Priority implements Serializable{
        NotImportant("Not important", 0),
        Important("Important", 1),
        VeryImportant("Very important", 2);

        private String outputString;
        private int priorityRange;

        Priority(String _outputString, int _priorityRange){
            outputString = _outputString;
            priorityRange = _priorityRange;
        }

        @Override public String toString(){
            return outputString;
        }

        public static Priority getValueByRange(int range){

            return Arrays.stream(Priority.values())
                    .filter(v -> v.getPriorityRange() == range)
                    .findFirst().orElse(NotImportant);
        }

        public int getPriorityRange(){
            return priorityRange;
        }
    }

    private int id;
    private String header;
    private String text;
    private Date date;
    private Priority priority;

    public Note() {
        header = "Note";
        text = "";
        date = new Date();
    }

    public int getID(){
        return id;
    }
    public String getHeader(){
        return header;
    }
    public String getText(){
        return text;
    }
    public Date getDate(){
        return date;
    }
    public Priority getPriority(){
        return priority;
    }

    public void setHeader(String _header){
        header = _header;
    }
    public void setText(String _text){
        text = _text;
    }
    public void setDate(Date _time){
        date = _time;
    }
    public void setPriority(Priority _priority){
        priority = _priority;
    }

    public Note(int _id, String _header, String _text, Priority _priority, long _date){
        id = _id;
        header = _header;
        text = _text;
        priority = _priority;
        date = new Date(_date);
    }
}
