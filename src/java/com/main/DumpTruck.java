package com.main;

import static java.lang.System.out;
import java.util.LinkedList;
import java.util.Random;
import java.util.ListIterator;
import java.util.Iterator;

public class DumpTruck{
  public static void main(String[] args){
    int loadLQ = 3;

    LinkedList<Integer> load = new LinkedList();
    int trucksLoaded = 2;
    int weighWQ = 0;

    LinkedList<Integer> weigh = new LinkedList();
    int trucksW = 1;
    
    LinkedList<Integer> arriveTimes = new LinkedList();
    int sumResponse = 0;
    int count=0;
    int clock = 0;
    int previousClock = 0;
    int busyL = 0;
    int busyS = 0;
    int tempEventType;
    Event tempE;
    int temp;
    Random gen = new Random(1000);   
    float rand;

    EventList events = new EventList();
    
    //Travel = 5
    //Load queue = 4
    //Loading = 3
    //Weigh queue = 2
    //Weigh = 1
    events.insert(new Event(1, getWT(gen.nextFloat()), 1));
    events.insert(new Event(3, getLT(gen.nextFloat()), 2));
    events.insert(new Event(3, getLT(gen.nextFloat()), 3));
    load.add(4);
    load.add(5);
    load.add(6);
    
    while (clock<=200){
      busyS += trucksW * (clock-previousClock);
      busyL += trucksLoaded * (clock-previousClock);
      
      while((events.peek()).getTime()==clock){
        tempE = events.getNext();
        tempEventType = tempE.getEvent();
        rand = gen.nextFloat();
        switch (tempEventType){
          case(1): // kalau truck selesai weighing
            count++;
            if (count>6){
              sumResponse += (clock - arriveTimes.poll());
            }  
            trucksW--;
            events.insert(new Event(5, (getTT(rand)+clock), tempE.getID())); //travelling
            if (weighWQ>0){  //menunggu untuk weigh
              weighWQ--;
              trucksW++;
              temp = weigh.poll();
              events.insert(new Event(1, (getWT(rand)+clock), temp));   //schedule weighing
            }
            break;
          
          case(3): //kalau selesai loading
            trucksLoaded--; 
            if (trucksW==0){  //kalau ga ada yang weighing
              events.insert(new Event (1, (getWT(rand)+clock), tempE.getID()));  //schedule weighing
              trucksW++;
            }
            else{  //kalau ada yang antri weighing
              weighWQ++;                           
              weigh.add(tempE.getID());
            }
            if (loadLQ>0){  //kalau nunggu untuk diload
              loadLQ--;
              trucksLoaded++;
              temp = load.poll();
              events.insert(new Event(3, (getLT(rand)+clock), temp));   //schedule loading
            }
            break;
          
          case(5):
            arriveTimes.add(clock);
            if (trucksLoaded<2){  // gaada tempat untuk ngeload
              trucksLoaded++;
              events.insert(new Event(3, (getLT(rand)+clock), tempE.getID()));   //schedule loading
            }
            else{  //kalau ngantri ngeload
              loadLQ++;
              load.add(tempE.getID());
            }
            break;
        }
      }
      
      ListIterator<Integer> iteratorL = load.listIterator(0); 
      ListIterator<Integer> iteratorW = weigh.listIterator(0); 
      Iterator<Event> iteratorE = events.iterator();
      System.out.print("Clock t: " + clock + "| LQ(t): " + loadLQ + "| L(t): " + trucksLoaded + "| WQ(t): " + weighWQ + "| W(t): " + trucksW);
      
      System.out.print("| Loader Queue: ");
      while (iteratorL.hasNext()){
        System.out.print("DT" + iteratorL.next() + " "); 
      }
      
      System.out.print("| Weigh Queue: ");
      while (iteratorW.hasNext()){
        System.out.print("DT" + iteratorW.next() + " "); 
      }
      
      System.out.print("| Future Event List: ");
      while (iteratorE.hasNext()){
        tempE = iteratorE.next();
        tempEventType = tempE.getEvent();
        if (tempEventType==1)
          System.out.print("(EW, ");
        else if (tempEventType==3)
          System.out.print("(EL, ");
        else
          System.out.print(" (ALQ, ");
        System.out.print(tempE.getTime() + ", DT" + tempE.getID() + ")");
      }
      System.out.println(" | BL: " + busyL + " | BS: " + busyS + "\n");
      previousClock = clock;
      clock = (events.peek()).getTime();
    } 
    System.out.println("\nRata-rata waktu respon: " + ((float)sumResponse/(count-6)));
    System.out.println("\nUtilisasi loader: " + ((float)busyL/clock));
    System.out.println("\nUtilisasi scale: " + ((float)busyS/(clock/2)));
  }
  
  public void processDT(){
    int loadLQ = 3;

    LinkedList<Integer> load = new LinkedList();
    int trucksLoaded = 2;
    int weighWQ = 0;

    LinkedList<Integer> weigh = new LinkedList();
    int trucksW = 1;
    
    LinkedList<Integer> arriveTimes = new LinkedList();
    int sumResponse = 0;
    int count=0;
    int clock = 0;
    int previousClock = 0;
    int busyL = 0;
    int busyS = 0;
    int tempEventType;
    Event tempE;
    int temp;
    Random gen = new Random(1000);   
    float rand;

    EventList events = new EventList();
    
    //Travel = 5
    //Load queue = 4
    //Loading = 3
    //Weigh queue = 2
    //Weigh = 1
    events.insert(new Event(1, getWT(gen.nextFloat()), 1));
    events.insert(new Event(3, getLT(gen.nextFloat()), 2));
    events.insert(new Event(3, getLT(gen.nextFloat()), 3));
    load.add(4);
    load.add(5);
    load.add(6);
    
    while (clock<=200){
      busyS += trucksW * (clock-previousClock);
      busyL += trucksLoaded * (clock-previousClock);
      
      while((events.peek()).getTime()==clock){
        tempE = events.getNext();
        tempEventType = tempE.getEvent();
        rand = gen.nextFloat();
        switch (tempEventType){
          case(1): // kalau truck selesai weighing
            count++;
            if (count>6){
              sumResponse += (clock - arriveTimes.poll());
            }  
            trucksW--;
            events.insert(new Event(5, (getTT(rand)+clock), tempE.getID())); //travelling
            if (weighWQ>0){  //menunggu untuk weigh
              weighWQ--;
              trucksW++;
              temp = weigh.poll();
              events.insert(new Event(1, (getWT(rand)+clock), temp));   //schedule weighing
            }
            break;
          
          case(3): //kalau selesai loading
            trucksLoaded--; 
            if (trucksW==0){  //kalau ga ada yang weighing
              events.insert(new Event (1, (getWT(rand)+clock), tempE.getID()));  //schedule weighing
              trucksW++;
            }
            else{  //kalau ada yang antri weighing
              weighWQ++;                           
              weigh.add(tempE.getID());
            }
            if (loadLQ>0){  //kalau nunggu untuk diload
              loadLQ--;
              trucksLoaded++;
              temp = load.poll();
              events.insert(new Event(3, (getLT(rand)+clock), temp));   //schedule loading
            }
            break;
          
          case(5):
            arriveTimes.add(clock);
            if (trucksLoaded<2){  // gaada tempat untuk ngeload
              trucksLoaded++;
              events.insert(new Event(3, (getLT(rand)+clock), tempE.getID()));   //schedule loading
            }
            else{  //kalau ngantri ngeload
              loadLQ++;
              load.add(tempE.getID());
            }
            break;
        }
      }
      
      ListIterator<Integer> iteratorL = load.listIterator(0); 
      ListIterator<Integer> iteratorW = weigh.listIterator(0); 
      Iterator<Event> iteratorE = events.iterator();
      out.print("Clock t: " + clock + "| LQ(t): " + loadLQ + "| L(t): " + trucksLoaded + "| WQ(t): " + weighWQ + "| W(t): " + trucksW);
      
      out.print("| Loader Queue: ");
      while (iteratorL.hasNext()){
        out.print("DT" + iteratorL.next() + " "); 
      }
      
      out.print("| Weigh Queue: ");
      while (iteratorW.hasNext()){
        out.print("DT" + iteratorW.next() + " "); 
      }
      
      out.print("| Future Event List: ");
      while (iteratorE.hasNext()){
        tempE = iteratorE.next();
        tempEventType = tempE.getEvent();
        if (tempEventType==1)
          out.print("(EW, ");
        else if (tempEventType==3)
          out.print("(EL, ");
        else
          out.print(" (ALQ, ");
        out.print(tempE.getTime() + ", DT" + tempE.getID() + ")");
      }
      out.println(" | BL: " + busyL + " | BS: " + busyS + "\n");
      previousClock = clock;
      clock = (events.peek()).getTime();
    } 
    out.println("\nRata-rata waktu respon: " + ((float)sumResponse/(count-6)));
    out.println("\nUtilisasi loader: " + ((float)busyL/clock));
    out.println("\nUtilisasi scale: " + ((float)busyS/(clock/2)));
  }

  
  //Tabel Distribusinya
  public static int getLT(float temp){
    if (temp<0.3) return (5);
    else if (temp < 0.8) return (10);
    else return (15);
  }
  
  public static int getWT(float temp){
    if (temp<0.7) return (12);
    else return (16);
  }
  
  public static int getTT(float temp){
    if (temp<0.4) return (40);
    else if (temp < 0.7) return (60);
    else if (temp < 0.9) return (80);
    else return (100);
  }
  
  
    //Tabel Distribusinya
  public int getLTp(float temp){
    if (temp<0.3) return (5);
    else if (temp < 0.8) return (10);
    else return (15);
  }
  
  public int getWTp(float temp){
    if (temp<0.7) return (12);
    else return (16);
  }
  
  public int getTTp(float temp){
    if (temp<0.4) return (40);
    else if (temp < 0.7) return (60);
    else if (temp < 0.9) return (80);
    else return (100);
  }
  
  public String mainEngine (int clock, int trucksLoaded , int trucksW) {
      int loadLQ = 3;

    LinkedList<Integer> load = new LinkedList();
    //int trucksLoaded = 2;
    int weighWQ = 0;

    LinkedList<Integer> weigh = new LinkedList();
    //int trucksW = 1;
    
    LinkedList<Integer> arriveTimes = new LinkedList();
    int sumResponse = 0;
    int count=0;
    int i = 0;
    int previousClock = 0;
    int busyL = 0;
    int busyS = 0;
    int tempEventType;
    Event tempE;
    int temp;
    Random gen = new Random(1000);   
    float rand;

    EventList events = new EventList();
    
    //Travel = 5
    //Load queue = 4
    //Loading = 3
    //Weigh queue = 2
    //Weigh = 1
    events.insert(new Event(1, getWT(gen.nextFloat()), 1));
    events.insert(new Event(3, getLT(gen.nextFloat()), 2));
    events.insert(new Event(3, getLT(gen.nextFloat()), 3));
    load.add(4);
    load.add(5);
    load.add(6);
    
    while (i<=clock){
      busyS += trucksW * (clock-previousClock);
      busyL += trucksLoaded * (clock-previousClock);
      
      while((events.peek()).getTime()==clock){
        tempE = events.getNext();
        tempEventType = tempE.getEvent();
        rand = gen.nextFloat();
        switch (tempEventType){
          case(1): // kalau truck selesai weighing
            count++;
            if (count>6){
              sumResponse += (clock - arriveTimes.poll());
            }  
            trucksW--;
            events.insert(new Event(5, (getTT(rand)+clock), tempE.getID())); //travelling
            if (weighWQ>0){  //menunggu untuk weigh
              weighWQ--;
              trucksW++;
              temp = weigh.poll();
              events.insert(new Event(1, (getWT(rand)+clock), temp));   //schedule weighing
            }
            break;
          
          case(3): //kalau selesai loading
            trucksLoaded--; 
            if (trucksW==0){  //kalau ga ada yang weighing
              events.insert(new Event (1, (getWT(rand)+clock), tempE.getID()));  //schedule weighing
              trucksW++;
            }
            else{  //kalau ada yang antri weighing
              weighWQ++;                           
              weigh.add(tempE.getID());
            }
            if (loadLQ>0){  //kalau nunggu untuk diload
              loadLQ--;
              trucksLoaded++;
              temp = load.poll();
              events.insert(new Event(3, (getLT(rand)+clock), temp));   //schedule loading
            }
            break;
          
          case(5):
            arriveTimes.add(clock);
            if (trucksLoaded<2){  // gaada tempat untuk ngeload
              trucksLoaded++;
              events.insert(new Event(3, (getLT(rand)+clock), tempE.getID()));   //schedule loading
            }
            else{  //kalau ngantri ngeload
              loadLQ++;
              load.add(tempE.getID());
            }
            break;
        }
      }
      
      ListIterator<Integer> iteratorL = load.listIterator(0); 
      ListIterator<Integer> iteratorW = weigh.listIterator(0); 
      Iterator<Event> iteratorE = events.iterator();
      System.out.print("Clock t: " + clock + "| LQ(t): " + loadLQ + "| L(t): " + trucksLoaded + "| WQ(t): " + weighWQ + "| W(t): " + trucksW);
      
      System.out.print("| Loader Queue: ");
      while (iteratorL.hasNext()){
        System.out.print("DT" + iteratorL.next() + " "); 
      }
      
      System.out.print("| Weigh Queue: ");
      while (iteratorW.hasNext()){
        System.out.print("DT" + iteratorW.next() + " "); 
      }
      
      System.out.print("| Future Event List: ");
      while (iteratorE.hasNext()){
        tempE = iteratorE.next();
        tempEventType = tempE.getEvent();
        if (tempEventType==1)
          System.out.print("(EW, ");
        else if (tempEventType==3)
          System.out.print("(EL, ");
        else
          System.out.print(" (ALQ, ");
        System.out.print(tempE.getTime() + ", DT" + tempE.getID() + ")");
      }
      System.out.println(" | BL: " + busyL + " | BS: " + busyS + "\n");
      previousClock = clock;
      clock = (events.peek()).getTime();
    } 
    System.out.println("\nRata-rata waktu respon: " + ((float)sumResponse/(count-6)));
    System.out.println("\nUtilisasi loader: " + ((float)busyL/clock));
    System.out.println("\nUtilisasi scale: " + ((float)busyS/(clock/2)));
    
    
    
      return "";
  
  }
  
  public String test() {
  return "CDFDFDFDF";}
}

