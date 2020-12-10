package com.main;

public class Event{
  private int time;            //lamanye event
  private int eventType;       //jenis eventnya
  private int id;
  
  public Event (int e, int t, int i){
    eventType = e;
    time = t;
    id = i;
  }
  public int getTime(){return time;}
  public int getID(){return id;}
  public int getEvent(){return eventType;}
  public String toString(){return("(" + eventType + ", " + time + ", " + id + ")");}    
}