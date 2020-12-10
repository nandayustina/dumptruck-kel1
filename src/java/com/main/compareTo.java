package com.main;

import java.util.Comparator;

public class compareTo implements Comparator<Event>{
  public compareTo(){ super();}

  public int compare(Event x, Event y){
    if (x.getTime() < y.getTime()) return -1;

    else if (x.getTime() == y.getTime()){
      if (x.getEvent() > y.getEvent())  return -1;
      else return 1;
    }
    else return 1;
  }
}