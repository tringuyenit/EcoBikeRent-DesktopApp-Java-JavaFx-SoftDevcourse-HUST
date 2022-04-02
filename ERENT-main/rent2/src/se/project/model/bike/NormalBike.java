package se.project.model.bike;
public class NormalBike extends BikeType {
    public NormalBike() {
        super();
    }
    public NormalBike(String name, String type, String manu, String producer, int cost) {
        super(name, type, manu, producer, cost);
    }
    
    @Override
    public int getDeposit() {
        return 400000;
    }

    @Override
    public double totalCostSpecificBike(int time_in_minute) {
        return totalCostNormalBike(time_in_minute);
    }
}
