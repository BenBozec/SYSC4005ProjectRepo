public class workstation{

    private int ID;
    private boolean isWaiting;

    public workstation(int ID){
        this.ID = ID;
        this.isWaiting = false;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public getWaiting(){
        return isWaiting;
    }

    public void setWaiting(boolean waiting) {
        isWaiting = waiting;
    }
}