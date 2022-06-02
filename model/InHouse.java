package model;

public class InHouse extends Part {
    private int MachineID;

    public InHouse(int id, String name, double price,int stock, int min,int max, int machineID) {
        super(id, name, price, stock,min, max );

        this.MachineID = machineID;
    }
    public void setMachineID(int MachineID)
    {
        this.MachineID = MachineID;
    }
    public int getMachineID()
    {
        return MachineID;
    }
}