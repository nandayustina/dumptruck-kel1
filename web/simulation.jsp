<%-- 
    Document   : simulation
    Created on : Dec 9, 2020, 9:06:54 PM
    Author     : gilang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.main.*,java.util.LinkedList,java.util.Random,java.util.ListIterator,java.util.Iterator"%>

<!DOCTYPE html>
<html>
  <head>
    <title>Dump Truck Problem Simulation</title>
  </head>
  <body>
    <h1>Dump Truck Problem Simulation</h1>
    <br/><br/>

    <%
    int jumlahevent   = Integer.parseInt(request.getParameter("jumlahevent").trim());
    int jumlahloader   = Integer.parseInt(request.getParameter("jumlahloader").trim());
    int jumlahweigh   = Integer.parseInt(request.getParameter("jumlahweigh").trim());
    if (jumlahevent == 0 || jumlahloader == 0 || jumlahweigh == 0) {
    %>
      Please fill all the input<br><br><br>
    <%
    } else {
    %>
      jumlahevent <%=jumlahevent%><br> 
      jumlahloader <%=jumlahloader%><br>
      jumlahloader <%=jumlahweigh%><br>
      
      
    <%
    }
    %>
    <a href="index.html">Try again?</a>
    <br>
    <%
   int loadLQ = 3;
    DumpTruck dt = new DumpTruck();
    LinkedList<Integer> load = new LinkedList();
    int trucksLoaded = jumlahloader;
    int weighWQ = 0;

    LinkedList<Integer> weigh = new LinkedList();
    int trucksW = jumlahweigh;
    
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
    events.insert(new Event(1, dt.getWTp(gen.nextFloat()), 1));
    events.insert(new Event(3, dt.getLTp(gen.nextFloat()), 2));
    events.insert(new Event(3, dt.getLTp(gen.nextFloat()), 3));
    load.add(4);
    load.add(5);
    load.add(6);
    
    while (clock<=jumlahevent){
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
            events.insert(new Event(5, (dt.getTTp(rand)+clock), tempE.getID())); //travelling
            if (weighWQ>0){  //menunggu untuk weigh
              weighWQ--;
              trucksW++;
              temp = weigh.poll();
              events.insert(new Event(1, (dt.getWTp(rand)+clock), temp));   //schedule weighing
            }
            break;
          
          case(3): //kalau selesai loading
            trucksLoaded--; 
            if (trucksW==0){  //kalau ga ada yang weighing
              events.insert(new Event (1, (dt.getWTp(rand)+clock), tempE.getID()));  //schedule weighing
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
              events.insert(new Event(3, (dt.getLTp(rand)+clock), temp));   //schedule loading
            }
            break;
          
          case(5):
            arriveTimes.add(clock);
            if (trucksLoaded<2){  // gaada tempat untuk ngeload
              trucksLoaded++;
              events.insert(new Event(3, (dt.getLTp(rand)+clock), tempE.getID()));   //schedule loading
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
      %>
      <br/><br/>
      <%out.print("\n Clock t: " + clock + "| LQ(t): " + loadLQ + "| L(t): " + trucksLoaded + "| WQ(t): " + weighWQ + "| W(t): " + trucksW);
      
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
%>
<br/><br/>
<%
    out.println("\n Rata-rata waktu respon: " + ((float)sumResponse/(count-6)));
    out.println("\n Utilisasi loader: " + ((float)busyL/clock));
    out.println("\n Utilisasi scale: " + ((float)busyS/(clock/2)));

    %>
  </body>
</html>
