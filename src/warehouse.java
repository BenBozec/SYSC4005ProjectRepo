import java.util.*;

public class warehouse{

    public static double Clock; // ask TA what is the stop time or date.
    public static double inspector1IDLE, inspector2IDLE;
    private static Queue<Event> FEL;
    
    public static int bufferC1Workstation1;
    public static int bufferC1Workstation2;
    public static int bufferC2Workstation2;
    public static int bufferC1Workstation3;
    public static int bufferC3Workstation3;

    public static inspector i1 ;
    public static inspector i2;
    public static workstation w1;
    public static workstation w2;
    public static workstation w3;

    //Statistic metrics for Inspector blocked times and product output
    private static double BI1, BI2;                                           // Total idle time for the two inspectors
    private static int P1, P2, P3;                                           // Products completed per workstation
    
    private static void initialization(){
        //Initialize the statistic tracking variables and clock
        //Initialize the statistic tracking variables and clock
        Clock = 0.0;
        
        inspector1IDLE = 0;
        inspector2IDLE = 0;
        
        bufferC1Workstation1 = 0;
        bufferC1Workstation2 = 0;
        bufferC2Workstation2 = 0;
        bufferC1Workstation3 = 0;
        bufferC3Workstation3 = 0;
        
        BI1 = 0.0;
        BI2 = 0.0;
        P1 = 0;
        P2 = 0;
        P3 = 0;

        //Initialize the 2 inspectors and the 3 workstations
        i1 = new inspector(1);
        i2 = new inspector(2);
        w1 = new workstation(1);
        w2 = new workstation(2);
        w3 = new workstation(3);

        //Set Inspector 1 component to C1
        i1.setCurrComponent(inspector.componentType.C1);
        //and create the initial events for the inspectors
        createEvent(Event.eventType.I1C, i1);
        createEvent(Event.eventType.I2C, i2);
    }

    public static void main(String args[]){
        //Initialize FEL and workstation buffers
        FEL = new PriorityQueue<>();

        initialization();
        //Work through FEL queue and process the events as they appear

        while (((P1 + P2 + P3) < 300) && !(FEL.isEmpty())) {
            Event ProcessEvent = FEL.poll();
            if (ProcessEvent != null) {
                Clock = ProcessEvent.getTime();
                System.out.print("Clock = " + Clock + ", Next Event => " + ProcessEvent.getType() + "\n");
                ProcessEvent(ProcessEvent);

            }
        }
        if(i1.getBlocked()){
            BI1 += Clock - inspector1IDLE;
        }
        if(i2.getBlocked()){
            BI2 += Clock - inspector2IDLE;
        }
        //Print out desired data. (System Throughput and Inspector Idle time proportions)
        GenerateReport();
    }

    private static void GenerateReport() {
        double Thru_P1, Thru_P2, Thru_P3, Total_Thru, IdleI1, IdleI2;
        Thru_P1 = (P1 / Clock);
        Thru_P2 = (P2 / Clock);
        Thru_P3 = (P3 / Clock);

        Total_Thru = (Thru_P1 + Thru_P2 + Thru_P3);

        IdleI1 = (BI1 / Clock) * 100;
        IdleI2 = (BI2 / Clock) * 100;


        System.out.print("\n-----------------------------------------------------------\n");
        System.out.print("Statistics\n");
        System.out.print(P1 + "\n");
        System.out.print(P2 + "\n");
        System.out.print(P3 + "\n");
        System.out.print(" Throughput of Workstation 1 = " + Thru_P1 + "\n");
        System.out.print(" Throughput of Workstation 2 " + Thru_P2 + "\n");
        System.out.print(" Throughput of Workstation 3 " + Thru_P3 + "\n");
        System.out.print(" Throughput of the System " + Total_Thru + "\n");
        System.out.print(" Idle percentage time for inspector 1 " + IdleI1 + "\n");
        System.out.print(" Idle percentage time for inspector 2  " + IdleI2 + "\n");
    }

    private static void ProcessI1C(Event e){
        inspector eventInspector = (inspector) e.getSubject();

        //Determine the correct buffer to place the component
        int bufferToPlace = 1;
        if(bufferC1Workstation1 > bufferC1Workstation2){
            bufferToPlace = 2;
        }
        if(bufferC1Workstation1 > bufferC1Workstation3 && bufferC1Workstation2 > bufferC1Workstation3){
            bufferToPlace = 3;
        }

        //If the inspector can place the component in a buffer, check if that workstation is waiting for components
        switch (bufferToPlace) {
            case 1:
                //If station is waiting, create WC event
                if (w1.getWaiting()) {
                    createEvent(Event.eventType.WC, w1);
                    w1.setWaiting(false);
                    createEvent(Event.eventType.I1C, eventInspector);
                //Buffers must all be full. Set inspector to blocked
                }else if (bufferC1Workstation1 == 2) {
                    eventInspector.setBlocked(true);
                    inspector1IDLE = Clock;
                }else{
                    createEvent(Event.eventType.I1C, eventInspector);
                    bufferC1Workstation1++;
                }
                break;
            case 2:
                //If station is waiting and there is at least one C3 in the buffer, create WC event
                if (w2.getWaiting() && bufferC2Workstation2 > 0) {
                    //Check if other inspector is blocked by this workstation and unblock is it is
                    if(i2.getBlocked() && i2.getCurrComponment().equals(inspector.componentType.C2)) {
                        i2.setBlocked(false);
                        createEvent(Event.eventType.I2C, i2);
                    }else {
                        bufferC2Workstation2--;
                    }
                    createEvent(Event.eventType.WC, w2);
                    w2.setWaiting(false);
                    createEvent(Event.eventType.I1C, eventInspector);
                }else{
                    createEvent(Event.eventType.I1C, eventInspector);
                    bufferC1Workstation2++;
                }
                break;
            case 3:
                //If station is waiting and there is at least one C3 in the buffer, create WC event
                if (w3.getWaiting() && bufferC3Workstation3 > 0) {
                    //Check if other inspector is blocked by this workstation and unblock is it is
                    if(i2.getBlocked() && i2.getCurrComponment().equals(inspector.componentType.C3)) {
                        i2.setBlocked(false);
                        createEvent(Event.eventType.I2C, i2);
                    }else {
                        bufferC3Workstation3--;
                    }
                    createEvent(Event.eventType.WC, w3);
                    w3.setWaiting(false);
                    createEvent(Event.eventType.I1C, eventInspector);
                }else{
                    createEvent(Event.eventType.I1C, eventInspector);
                    bufferC1Workstation3++;
                }
                break;
        }
    }


    private static void ProcessI2C(Event e){
        inspector eventInspector = (inspector) e.getSubject();
        //If the inspector can place the component in a buffer, check if that workstation is waiting for components
        if(eventInspector.getCurrComponment().equals(inspector.componentType.C2)){
            //If station is waiting and there is at least one C1 in the buffer, create WC event
            if (w2.getWaiting() && bufferC1Workstation2 > 0) {
                bufferC1Workstation2--;
                createEvent(Event.eventType.WC, w2);
                w2.setWaiting(false);
                createEvent(Event.eventType.I2C, eventInspector);
            //Buffers must all be full. Set inspector to blocked
            }else if (bufferC2Workstation2 == 2) {
                eventInspector.setBlocked(true);
                inspector2IDLE = Clock;
            }else{
                createEvent(Event.eventType.I2C, eventInspector);
                bufferC2Workstation2++;
            }
        } else {
            //If station is waiting and there is at least one C1 in the buffer, create WC event
            if (w3.getWaiting() && bufferC1Workstation3 > 0) {
                bufferC1Workstation3--;
                createEvent(Event.eventType.WC, w3);
                w3.setWaiting(false);
                createEvent(Event.eventType.I2C, eventInspector);
            //Buffers must all be full. Set inspector to blocked
            }else if (bufferC3Workstation3 == 2) {
                eventInspector.setBlocked(true);
                inspector2IDLE = Clock;
            }else{
                createEvent(Event.eventType.I2C, eventInspector);
                bufferC3Workstation3++;
            }
        }
    }

    private static void ProcessWC(Event e){
        workstation eventWorkstation = (workstation) e.getSubject();
        switch (eventWorkstation.getID()) {
            case 1:
                //increment number of products completed by 1
                P1++;
                //If the components are available, remove one of each and create new WC event
                if(bufferC1Workstation1 > 0){
                    //Check if inspector is blocked by the buffers
                    //If blocked, set to not blocked, create a new event for that inspector
                    if(i1.getBlocked()){
                        i1.setBlocked(false);
                        createEvent(Event.eventType.I1C, i1);
                        BI1 += Clock - inspector1IDLE;
                    }else{
                        bufferC1Workstation1--;
                    }
                    createEvent(Event.eventType.WC, w1);
                //If the components not available, set the workstation to waiting
                } else {
                    w1.setWaiting(true);
                }

                break;
            case 2:
                //increment number of products completed by 1
                P2++;
                //If the components are available, remove one of each and create new WC event
                if(bufferC1Workstation2 > 0 && bufferC2Workstation2 > 0){
                    //Check if inspector 2 is blocked by the buffer. Inspector 1 can only be blocked by w1 due
                    //to priority algorithm.
                    bufferC1Workstation2--;
                    //If blocked, set to not blocked, create a new event for that inspector
                    if(i2.getBlocked()){
                        i2.setBlocked(false);
                        createEvent(Event.eventType.I2C, i2);
                        BI2 += Clock - inspector2IDLE;
                    }else{
                        bufferC2Workstation2--;
                    }
                    createEvent(Event.eventType.WC, w2);
                //If the components not available, set the workstation to waiting
                } else {
                    w2.setWaiting(true);
                }
                break;
            case 3:
                //increment number of products completed by 1
                P3++;
                //If the components are available, remove one of each and create new WC event
                if(bufferC1Workstation3 > 0 && bufferC3Workstation3 > 0){
                    //Check if inspector 2 is blocked by the buffer. Inspector 1 can only be blocked by w1 due
                    //to priority algorithm.
                    bufferC1Workstation3--;
                    //If blocked, set to not blocked, create a new event for that inspector
                    if(i2.getBlocked()){
                        i2.setBlocked(false);
                        createEvent(Event.eventType.I2C, i2);
                        BI2 += Clock - inspector2IDLE;
                    }else{
                        bufferC3Workstation3--;
                    }
                    createEvent(Event.eventType.WC, w3);
                //If the components not available, set the workstation to waiting
                } else {
                    w3.setWaiting(true);
                }
                break;
        }

    }

    private static void ProcessEvent(Event newEvent){
        switch(newEvent.getType()){
            case I1C:
                ProcessI1C(newEvent);
                break;
            case I2C:
                ProcessI2C(newEvent);
                break;
            case WC:
                ProcessWC(newEvent);
                break;
        }
    }

    private static void createEvent(Event.eventType type, Object subject){
        //Generate processing time for new event using calculated estimators and distributions
        double processTime = -1;
        switch(type){
            case WC:
                workstation eWS = (workstation) subject;
                switch (eWS.getID()){
                    case 1:
                        processTime = getRandomTime(0.02172);
                        break;
                    case 2:
                        processTime = getRandomTime(0.09178);
                        break;
                    case 3:
                        processTime = getRandomTime(0.1137);
                        break;
                }
                break;
            case I1C:
                processTime = getRandomTime(0.09655);
                break;
            case I2C:
                double nextComponentRNG = Math.random();
                if(nextComponentRNG < 0.5) {
                    i2.setCurrComponent(inspector.componentType.C2);
                    processTime = getRandomTime(0.0644);
                }else{
                    i2.setCurrComponent(inspector.componentType.C3);
                    processTime = getRandomTime(0.0485);
                }
                break;
        }
        Event newEvent = new Event(Clock+processTime, type, subject);
        System.out.print("New Event => " + newEvent.getType() + ", time => " + newEvent.getTime() + ", processing time => " + processTime + ", current time => " + Clock + "\n");
        FEL.offer(newEvent);
    }

    //Gets the random time given a lambda estimator using
    //an exponential function. This is due to all of our inspectors and stations having this distribution
    private static double getRandomTime(double lambda){
        double randNum = Math.random();
        return (-1/lambda)*Math.log(1-randNum);
    }
}
