/*
 * Name = Sri Sai Teja Paturu
 * Student ID = s3644335
 * Description of this class = This class is a sub class of Exception class, this class is used in vehicle and over-sized vehicle classes to 
 * throw errors to BookingSystem class, if there is any wrong input from the user.
*/

public class VehicleException extends Exception
{
	public VehicleException(String message)
	{
		super(message);
	}

}
