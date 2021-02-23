public class inspector {

    public enum componentType {C1, C2, C3};

    private int ID;
    private boolean isBlocked;
    private componentType currComponent;

    public inspector(int ID){
        this.ID = ID;
        isBlocked = false;
    }

    public getID(){
        return ID;
    }

    public setID(int ID){
        this.ID = ID;
    }

    public getBlocked(){
        return isBlocked;
    }

    public setBlocked(boolean isBlocked){
        this.isBlocked = isBlocked;
    }

    public getCurrComponment(){
        return currComponent;
    }

    public void setCurrComponent(componentType newComponent) {
        currComponent = newComponent;
    }
}