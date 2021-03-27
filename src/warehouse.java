import java.util.*;

public class warehouse{

    public static int Clock; // ask TA what is the stop time or date.
    public static int inspector1IDLE, inspector2IDLE;
    private static Queue<Event> FEL;
    
    public static int bufferC1Workstation1;
    public static int bufferC1Workstation2;
    public static int bufferC2Workstation2;
    public static int bufferC1Workstation3;
    public static int bufferC3Workstation3;
    
    public static Random rnd;
    
    //figure out what these are for
    private static double BL, BS;                                           // Total busy time for the two loaders and the scale, respectively
    private static double UL, US;                                           // Utilization for the two loaders and the scale, respectively
    
    private static void initialization(){
        //Initialize the statistic tracking variables and clock
        //Initialize the statistic tracking variables and clock
        Clock = 0;
        
        inspector1IDLE = 0;
        inspector2IDLE = 0;
        
        bufferC1Workstation1 = 0;
        bufferC1Workstation2 = 0;
        bufferC2Workstation2 = 0;
        bufferC1Workstation3 = 0;
        bufferC3Workstation3 = 0;
        
        BL = 0.0;
        BS = 0.0;
        UL = 0.0;
        US = 0.0;
        
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

    public static void main(String args[]){
        //Initialize RNG for inspector 2

        //Initialize FEL and workstation buffers

        initialization();

        //Work through FEL queue and process the events as they appear

        //Print out desired data. (System Throughput and Inspector Idle time proportions)
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
