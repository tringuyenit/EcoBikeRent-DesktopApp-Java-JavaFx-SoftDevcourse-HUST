package se.project.model.bike;

public class BikeFactory {
	  public static BikeType getBike(String type) {
		    switch (type) {
		      case "Electric":
		        return new ElectricBike();

		      case "Bike":
		        return new NormalBike();
                    
		      case "Twin":
		        return new TwinBike();
		       default:
		    	   return null;
                
		    }
	   }
}
