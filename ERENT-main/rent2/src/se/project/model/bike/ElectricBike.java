package se.project.model.bike;

public class ElectricBike extends BikeType {

    public ElectricBike() {
        super();
    }

    public ElectricBike(String name, String type, String manu, String producer, int cost) {
        super(name, type, manu, producer, cost);
    }

    @Override
    public double totalCostSpecificBike(int time_in_minute) {
        return 1.5*totalCostNormalBike(time_in_minute);
    }

    @Override
    public int getDeposit() {
        return 700000;
    }
}
