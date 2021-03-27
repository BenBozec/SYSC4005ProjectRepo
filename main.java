import java.util.*;
public class main {


    public static int Clock;
    public static int inspector1IDLE, inspector2IDLE;
    private static Queue<Event> FEL;
    public static int bufferC1Workstation1;
    public static int bufferC1Workstation2;
    public static int bufferC2Workstation2;
    public static int bufferC1Workstation3;
    public static int bufferC3Workstation3;

    public static Random rnd;

    //NEED TO FIGURE OUT WHAT THESE TRANSLATE TO
    private static double Bi1,Bi2;
    private static int P1, P2, P3 ;




    private static void initialization(){
        //Initialize the statistic tracking variables and clock
        Clock = 0;
        inspector1IDLE=0;
        inspector2IDLE=0;


        bufferC1Workstation1=0;
        bufferC1Workstation2=0;
        bufferC2Workstation2=0;
        bufferC1Workstation3=0;
        bufferC3Workstation3=0;

        Bi1=0.0;
        Bi2= 0.0;
        P1=0;
        P2=0;
        P3=0;

        rnd = new Random();

        //Initialize the 2 inspectors and the 3 workstations
        inspector i1 = new inspector(1);
        inspector i2 = new inspector(2);
        workstation w1 = new workstation(1);
        workstation w2 = new workstation(2);
        workstation w3 = new workstation(3);

        //Determine the first component type for inspector 2
        //and create the initial events for the inspectors
    }

    public static void main(String args[]) {
        //Initialize RNG for inspector 2


        //Initialize FEL and workstation buffers
        FEL = new PriorityQueue<>();

        initialization();
        //Work through FEL queue and process the events as they appear
        System.out.print("\n-----------------------------------------------\n");
        System.out.print(" Time " + ":D" + "\n");
        while (((P1 + P2 + P3) <= 300) && !(FEL.isEmpty())) {
            Event ProcessEvent = FEL.poll();
            if (ProcessEvent != null) {
                Clock = ProcessEvent.getTime();
                System.out.print("Clock = " + Clock);
                ProcessEvent(ProcessEvent);

            }
            GenerateReport();


            //Print out desired data. (System Throughput and Inspector Idle time proportions)
        }
    }

    private static void GenerateReport() {
        int P_1, P_2, P_3, sum, Temp1, temp2;
             P_1=(P1/Clock);
             P_2=(P2/Clock);
             P_3=(P3/Clock);

             sum=(P_1 +P_2+P_3);

             Temp1=(Bi1/Clock)*100;
             temp2=(Bi2/Clock)*100;





         System.out.print("\n-----------------------------------------------------------\n");
         System.out.print("Statistics\n");
         System.out.print(" PRODUCT 1 = " + P_1 + "\n");
        System.out.print(" PRODUCT 2 " + P_2 + "\n");
        System.out.print(" PRODUCT 3 " + P_3+ "\n");
        System.out.print(" Total through put of the system  " + sum + "\n");
        System.out.print(" Idle percentage time for inspector 1 " + Temp1 + "\n");
        System.out.print(" Idle percentage time for inspector 2  " + temp2 "\n");


    }

    private static void ProcessI1C(Event e){
        //Determine the correct buffer to place the component

        //If the inspector can place the component in a buffer, check if that workstation is waiting for components
        //If the workstation is waiting and now has all necessary components, create a new WC event and set station
        //to not waiting

        //After the component is placed, create a new I1C event

        //If all buffers are full, set the inspector as blocked
    }

    private static void ProcessI2C(Event e){
        //Determine the correct buffer to place the component

        //If the inspector can place the component in the buffer, check if that workstation is waiting for components
        //If the workstation is waiting and now has all necessary components, create a new WC event and set station
        //to not waiting

        //After the component is placed, create a new I2C event

        //If the buffer is full, set the inspector is blocked
    }

    private static void ProcessWC(Event e){
        //increment number of products completed by 1

        //Check if the necessary components are in its buffers
        //If the components are available, remove one of each and create new WC event
        //Check if either inspector is blocked by the buffers
        //If blocked, set to not blocked, add the component to the buffer and create a new event for that inspector

        //If the components not available, set the workstation to waiting
    }

    private static void createEvent(Event.eventType type, Object subject){
        //Create new event for specified event type
        //Time is determined by associated data file
    }
}
