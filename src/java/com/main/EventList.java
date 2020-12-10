package com.main;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.ListIterator;
import java.util.LinkedList;
import java.util.Iterator;

public class EventList{
  private PriorityQueue<Event> events;
  private Comparator comparator;
  
  //constructor
  public EventList(){
    comparator = new compareTo();
    events = new PriorityQueue<Event>(10, comparator);
  }
  
  //return head of the Future Event List
  public Event getNext(){
    return(events.poll());
  }
  
  //returns nth priority element
  public Event get(int numElement){
    Event temp;
    LinkedList<Event> tempEvents= new LinkedList();
    for (int i=0; events.peek()!=null ; i++){  //removes the top n times and stores in a list
      temp = events.poll();
      tempEvents.offer(temp);      
      if (i==numElement){                      //at nth element, read the previous elements
        ListIterator<Event> iterator = tempEvents.listIterator(0);
        while (iterator.hasNext()){
          events.offer(iterator.next());
        }
        return (temp);                    
      }
    }
    return(null);
  }
  
  
  //insert event into Future Event List
  public void insert (Event eventType){
    events.offer(eventType);
  }
  
  public int length(){
    return (events.size());
  }
  
  //return length of Future Event List
  public Event peek(){
    return(events.peek());
  }
  
  public String toString(){
    String temp = "";
    Iterator<Event> iter = events.iterator();
    while(iter.hasNext()){
      temp += iter.next() + " ";
    }
    return temp;
  }
  
  public Iterator iterator(){
    return(events.iterator());
  }
}
      