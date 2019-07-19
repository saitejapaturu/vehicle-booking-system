/*
 * Name = Sri Sai Teja Paturu
 * Student ID = s3644335
 * Description of this class = This class is a super class of OversizedVehicle, and is used to create vehicles. It has methods to calculate the fee 
 * and book the vehicle, get the vehicle details.
 */
public class Vehicle
{
    //These are the attributes for vehicles.
	private String regNo;
	private String make;
	private int year;
	private String description;
	private String bookingID;
	private DateTime bookingDate;
	private int numPassengers;
	
	// This attributes stores the bookingFee of the vehicle.
	protected double bookingFee;

	//These are the constants of vehicle class.
	private final int BOOKING_FEE = 100;
	private final int PASSENGER_SURCHARGE = 20;

	//Constructor for vehicle class.
	public Vehicle(String regNo, String make, int year, String description)
	{
		this.regNo = regNo;
		this.make = make;
		this.year = year;
		this.description = description;
		this.bookingID = "N/A";
	}
	
	//Used for getting Registration number of the vehicle, i.e getter of registration number.
	public String getRegNo()
	{
		return regNo;
	}


	//Records the passenger number for the vehicle and checks the validity.
	private boolean RecordPassengerNumbers(int numPassengers)
	{
		if (numPassengers < 1 || numPassengers > 6)
		{
			return false;
		}

		else
		{
			this.numPassengers = numPassengers;
			return true;
		}
	}

	//Calculates the fee and books the vehicle. It sends an error if the date and passenger number are not valid.
	public double book(int numPassengers, DateTime bookingDate) throws VehicleException
	{
		boolean ValidationOfPassengers;
		DateTime currentDate = new DateTime();
		currentDate.getEightDigitDate();

		//Creates booking fee depending upon the number of passengers.
		double bookingFee = BOOKING_FEE + (PASSENGER_SURCHARGE*numPassengers);

		//Checks for the validation of the number of passengers, and if it's not valid then throws an error.
		ValidationOfPassengers = RecordPassengerNumbers(numPassengers);
		
		//Checks for the difference in current date and booking date, and throws an error if the date is in the past.
		int difference = DateTime.diffDays (bookingDate, currentDate);
		
		if (ValidationOfPassengers == false)
		{
			throw new VehicleException("Error - passenger count must be between 1 and 6!");
		}

		else if (difference<1)
		{
			throw new VehicleException("Error - date must be in the future!");
		}

		//If there is no error, makes the booking.
		else
		{
			this.bookingDate= bookingDate;
			bookingID = regNo + bookingDate.getEightDigitDate();
			this.bookingFee = bookingFee;
			return bookingFee;
		}

	}

	//Gets all the vehicle details in a formatted way and returns them in a string.
	public String getVehicleDetails()
	{
		String[] Line=new String[8];
		Line[0] = String.format("%-25s %s\n", "Reg Num:", regNo);
		Line[1] = String.format("%-25s %s\n", "Make:", make);
		Line[2] = String.format("%-25s %s\n", "Year:", year);
		Line[3] = String.format("%-25s %s\n", "Description:", description);
		Line[4] = String.format("%-25s %s\n", "Booking ref:", bookingID);
		
		String VehicleDetails = "";
		
		if(bookingID.equals("N/A"))
		{
			for(int i=0; i<5;i++)
			{
			VehicleDetails+=String.format("%s\n",Line[i]) ;
			}
		}
		
		//If the booking ID is NOT "N/A" then gets the booking information of the vehicle.
		else
		{
	
		Line[5] = String.format("%-25s %s\n", "Booking Date:", bookingDate.getFormattedDate());
		Line[6] = String.format("%-25s %s\n", "Number of Passengers:", numPassengers);
		
		
		Line[7] = String.format("%-25s %s\n", "Fee:", bookingFee);
		
			for(int i=0; i<8;i++)
			{
			VehicleDetails+=String.format("%s\n",Line[i]) ;
			}
		}
		
		return VehicleDetails;
	}
}
