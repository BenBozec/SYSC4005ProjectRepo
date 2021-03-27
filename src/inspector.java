public class inspector {

    public enum componentType {C1, C2, C3};

    private int ID;
    private boolean isBlocked;
    private componentType currComponent;

    public inspector(int ID){
        this.ID = ID;
        isBlocked = false;
    }

    public int getID(){
        return ID;
    }

    public void setID(int ID){
        this.ID = ID;
    }

    public boolean getBlocked(){
        return isBlocked;
    }

    public void setBlocked(boolean isBlocked){
        this.isBlocked = isBlocked;
    }

    public componentType getCurrComponment(){
        return currComponent;
    }

    public void setCurrComponent(componentType newComponent) {
        currComponent = newComponent;
    }
}