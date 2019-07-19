/*
 * Name = Sri Sai Teja Paturu
 * Student ID = s3644335
 * Description of this class = This class is a sub class of Vehicle class. This create a vehicle of over-sized type, 
 * and has methods to book and get the details of the vehicle.
*/


public class OversizedVehicle extends Vehicle
{
    //These are the attributes for vehicles.
	private double weight;
	private String category;

	//These are the constants of vehicle class.
	private final double CLEARANCE_HEIGHT;
	private final double LIGHT_VEHICLE_CHARGE = 10.0;
	private final double MEDIUM_VEHICLE_CHARGE = 20.0;
	private final double HEAVY_VEHICLE_CHARGE = 50.0;

	//Constructor for vehicle class.
	public OversizedVehicle(String regNo, String make, int year, String description, double CLEARANCE_HEIGHT)
	{
		super(regNo, make, year, description);
		this.CLEARANCE_HEIGHT = CLEARANCE_HEIGHT;
		this.category = "N/A";
	}

	//Records the weight for the over-sized vehicle and sets it's category depending on it's weight.
	private void recordWeight(double weight)
	{
		this.weight = weight;

		//Deciding the category of the over-sized vehicle depending upon the weight.
		if (weight <= 3.0)
		{
			this.category = "N/A";
		}

		else if (weight > 3.0 && weight < 4.5)
		{
			this.category = "LIGHT";
		}

		else if (weight > 4.5 && weight <= 8.0)
		{
			this.category = "MEDIUM";
		}

		else
		{
			this.category = "HEAVY";
		}

	}

	//Calculates the fee and books the over-sized vehicle. It sends an error if the date, weight and passenger number are not valid.
	public double book(int numPassengers, DateTime date, double weight) throws VehicleException
	{
		double fee = 0;

		//If the height exceeds 3m then throws an error.
		if (this.CLEARANCE_HEIGHT > 3)
		{
			throw new VehicleException("Error - clearance height must not exceed 3 meters!");
		}

		else
		{
		    //Checks for passenger or date error from the super class.
			double checkExceptions = super.book(numPassengers, date);
	
				double surCharge = 0;

				recordWeight(weight);

				//gets the sur charge depending on the category of the vehicle.
				if (this.category == "LIGHT")
				{
					surCharge = LIGHT_VEHICLE_CHARGE;
				}

				else if (this.category == "MEDIUM")
				{
					surCharge = MEDIUM_VEHICLE_CHARGE;
				}

				else if (this.category == "HEAVY")
				{
					surCharge = HEAVY_VEHICLE_CHARGE;
				}

				//calculates the fee from the passenger number, sur charge and weight.
				fee = 100 + (numPassengers * 20) + ((weight - 3.0) * surCharge);
				this.bookingFee = fee;
				return fee;

			
		}
	}

	//Gets all the over-sized vehicle details.
	@Override
	public String getVehicleDetails()
	{
		String[] Line = new String[4];

		//gets the vehicle details from the super class.
		Line[0] = super.getVehicleDetails();
		
		//add the over-sized vehicle detail.
		Line[1] = String.format("%-25s %s\n", "Category:", category);
		Line[2] = String.format("%-25s %s\n", "Weight:", weight);
		Line[3] = String.format("%-25s %s\n", "Height:", CLEARANCE_HEIGHT);

		String VehicleDetails = "";

		//Adds all the vehicle details in a single string in a formatted way and returns the string.
		for (int i = 0; i < 4; i++)
		{
			VehicleDetails += String.format("%s\n", Line[i]);
		}

		return VehicleDetails;
	}
}
