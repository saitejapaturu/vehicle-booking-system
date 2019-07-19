/*
 * Name = Sri Sai Teja Paturu
 * Student ID = s3644335
 * Description of this class = This allows the user to add, book, and display information of the vehicles. It also has 
 * sample vehicles which can be seeded by the user. When the user exits from the program, this class will save all the 
 * vehicle information in a text vehicle and retrieve the information when the program is run again.
*/


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class BookingSystem
{
	private ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();
	private int vehicleCount = 0; // used in loops for Vehicle

	/*Restores the previously recorded vehicles from the text file or the backup file. Then, provides the user with a menu to let the user add, book,
	 *seed from the sample vehicles , and display vehicle information.
	*/
	
	public void run()
	{

		// reading from files

		Scanner inputStream = null;

		String vehicleDetails = "VehicleDetails.txt";
		String vehicleDetailsBackup = "vehicleDetailsBackup.txt";

		//First checks for VehicleDetails file.
		try
		{
			inputStream = new Scanner(new File(vehicleDetails));

			String whole = "";
			
			//Creating a string with all the words with a space in between.
			while (inputStream.hasNext())
			{
				String catchWords = inputStream.next();
				whole += " " + catchWords;
			}

			StringTokenizer tokenizer = new StringTokenizer(whole," ");
			
			//Counting the words and making an array with each word in different sections of the array.
			int tokens = tokenizer.countTokens();
			String[] words = new String[tokens];
			
			while (tokenizer.hasMoreElements())
			{
				for(int i=0; i<tokens;i++)
				{
					words[i] = tokenizer.nextToken();
				}
			}

			//sending the words array to RestoreVehicles method to restore the vehicles.
			vehicleCount = RestoringVehicles(words);

			inputStream.close();
			
	
		}

		//If the original VehicleDetails file is not found, then it will check for  VehicleDetailsBackup file.
		catch (FileNotFoundException e1)
		{
			
			try
			{
				inputStream = new Scanner(new File(vehicleDetailsBackup));
			
			//Creating a string with all the words with a space in between.	
			String whole = "";
			while (inputStream.hasNext())
			{
				String a = inputStream.next();
				whole += " " + a;
			}

			System.out.println("The vehicles have been restored from an backup file.");

			StringTokenizer tokenizer = new StringTokenizer(whole," ");
			
			//Counting the words and making an array with each word in different sections of the array.
			int tokens = tokenizer.countTokens();
			String[] words = new String[tokens];
			
			while (tokenizer.hasMoreElements())
			{
				for(int i=0; i<tokens;i++)
				{
					words[i] = tokenizer.nextToken();
				}
				
			}
			
			//sending the words array to RestoreVehicles method to restore the vehicles.
			vehicleCount = RestoringVehicles(words);
			inputStream.close();
			} 
			
			catch (FileNotFoundException e)
			{
				System.out.println("No vehicle data was restored.");
			}	
		}
		
		//This boolean is used to end the while loop and exit the program.
		boolean exit = false;
		while (exit == false)
		{
		    //Creating a menu array to get the menu printed in a neat format.
			String[] Menu = new String[5];
			Menu[0] = String.format("%-26s %s\n", "Seed Data", "A");
			Menu[1] = String.format("%-26s %s\n", "Add Vehicle", "B");
			Menu[2] = String.format("%-26s %s\n", "Display Vehicles", "C");
			Menu[3] = String.format("%-26s %s\n", "Book Passage", "D");
			Menu[4] = String.format("%-26s %s\n", "Exit Program", "X");

			
			System.out.println("*** Vehicle Booking System Menu ***");

            System.out.println();
			String FullMenu = "";

			for (int i = 0; i < 5; i++)
			{
				FullMenu += String.format("%s\n", Menu[i]);
			}

			//Printing the menu.
			
			System.out.println(FullMenu);

			System.out.println("Enter selection:");

			Scanner scanner = new Scanner(System.in);

			
			String input;
			input = scanner.next();

			//Check the input of the user and calling the appropriate method/function.
			if (input.equalsIgnoreCase("A"))
			{
				vehicleCount = seedData(vehicleCount);
			}

			else if (input.equalsIgnoreCase("B"))
			{
				vehicleCount= addVehicle(vehicleCount);
			}

			else if (input.equalsIgnoreCase("C"))
			{
				for (int i = 0; i < vehicleCount; i++)
				{
					displayVehicleInformation(i);
				}
			}

			else if (input.equalsIgnoreCase("D"))
			{
				bookPassage(vehicleCount);
			}

			else if (input.equalsIgnoreCase("X"))
			{
			    //Writing the added vehicles in VehicleDetails file and a backup file.
				String vehicleDetails1 = "VehicleDetails.txt";
				String vehicleDetailsBackup1 = "VehicleDetailsBackup.txt";
				PrintWriter outputStream;
				PrintWriter outputStreamBackup;
				try
				{
					outputStream = new PrintWriter(new FileOutputStream(vehicleDetails1));
					outputStreamBackup = new PrintWriter(new FileOutputStream(vehicleDetailsBackup1));
					
					for (int i = 0; i <= (vehicleCount - 1); i++)
					{
						outputStream.write((vehicles.get(i)).getVehicleDetails() + '\n');
						outputStreamBackup.write((vehicles.get(i)).getVehicleDetails() + '\n');
					}

					outputStream.close();
					outputStreamBackup.close();
				}

				catch (FileNotFoundException e)
				{
					e.printStackTrace();
				}
				exit = true;
			}

			else
			{
				System.out.println("The input is not valid.");
			}
			System.out.println();
		}
	}

	/*
	 * When this method is called, it creates 10 sample vehicles. 4 vehicles, in which 2 are booked. 6 over-sized vehicles, in which 3 are booked. The 
	 * vehicles can be seeded once.
	 */
	private int seedData(int vehicleCount)
	{
		//Checking if the vehicles have already been seeded.
		boolean seedValid = false;
		
		for(int i=(vehicleCount - 1); i>=0;i--)
		{
			if (((vehicles.get(i)).getRegNo()).equals("REG111"))
			{
				seedValid = true;
				i = -1;
			}
		}
		
		if (seedValid == false)
		{	
            // Creating sample vehicles, sample over-sized vehicles and sample dates.
		DateTime sampleDateTime1 = new DateTime(13, 12, 2017);
		DateTime sampleDateTime2 = new DateTime(19, 11, 2017);
		DateTime sampleDateTime3 = new DateTime(11, 7, 2018);

		Vehicle sampleVehicle1 = new Vehicle("REG111", "Tesla", 2014, "Deep Blue Sedan");
		Vehicle sampleVehicle2 = new Vehicle("REG222", "Maserati", 2017, "Midnight Black");

		Vehicle sampleVehicle3 = new Vehicle("REG333", "Audi", 1989, "Cobalt Blue");
		Vehicle sampleVehicle4 = new Vehicle("REG444", "BMW", 2014, "White Sedan");
		

		OversizedVehicle sampleVehicle5 = new OversizedVehicle("REG555", "Lexus", 2007, "Red Sedan", 1.10);
		OversizedVehicle sampleVehicle6 = new OversizedVehicle("REG666", "DAF", 2015, "Reliable Heavy Haul", 2.60);
		OversizedVehicle sampleVehicle7 = new OversizedVehicle("REG777", "Venture", 2016, "Passenger Coach", 2.90);

		OversizedVehicle sampleVehicle8 = new OversizedVehicle("REG888", "Tammy", 1999, "Black Sedan", 2.40);
		OversizedVehicle sampleVehicle9 = new OversizedVehicle("REG999", "Thunder", 2010, "Heavy Haul", 2.80);
		OversizedVehicle sampleVehicle10 = new OversizedVehicle("REG101", "Hyper", 2006, "Superfast Medium", 2.20);
		
		//booking sample vehicles.
		try
		{	
			sampleVehicle3.book(2, sampleDateTime1);
			sampleVehicle4.book(4, sampleDateTime2);
			sampleVehicle8.book(3, sampleDateTime1,3.3 );
			sampleVehicle9.book(1, sampleDateTime2, 6.7);
			sampleVehicle10.book(5, sampleDateTime3, 8.1);
		} catch (VehicleException e)
		{
			e.printStackTrace();
		}

		
		vehicles.add(vehicleCount, sampleVehicle1);
		vehicleCount++;
		vehicles.add(vehicleCount, sampleVehicle2);
		vehicleCount++;
		vehicles.add(vehicleCount, sampleVehicle3);
		vehicleCount++;
		vehicles.add(vehicleCount, sampleVehicle4);
		vehicleCount++;
		vehicles.add(vehicleCount, sampleVehicle5);
		vehicleCount++;
		vehicles.add(vehicleCount, sampleVehicle6);
		vehicleCount++;
		vehicles.add(vehicleCount, sampleVehicle7);
		vehicleCount++;
		vehicles.add(vehicleCount, sampleVehicle8);
		vehicleCount++;
		vehicles.add(vehicleCount, sampleVehicle9);
		vehicleCount++;
		vehicles.add(vehicleCount, sampleVehicle10);
		vehicleCount++;
		}
		
		else
		{
			System.out.println("It seems that the vehicles have already been seeded!");
		}
        return vehicleCount;
	}

	//When this class is called it lets the user add a vehicle. If there is any error, it will display the error.
	private int addVehicle(int vehicleCount)
	{
		//close scanner
		Scanner scanner = new Scanner(System.in);

		String inputRegNo;
		String inputMake;
		int inputYear;
		String inputDescription;
		double inputHeight;

		System.out.printf("%-20s ", "Enter vehicle registration:");

		inputRegNo = scanner.nextLine();

		//Checking if the registration number is already taken by a previous added/seeded vehicle.
		boolean regNoValidity = true;

		for(int i=(vehicleCount-1); i>=0 ; i--)
		{
			if (inputRegNo.equals((vehicles.get(i)).getRegNo()))
			{
				regNoValidity = false;
			}
		}

		if (regNoValidity == false)
		{
			System.out.println("Error - Registration " + inputRegNo + " already exists in the system!");
		}

		//If it's a new Registration Number, then asks the user for all the vehicle details.
		else
		{
			System.out.printf("\n %-20s ", "Enter vehicle make:");

			inputMake = scanner.nextLine();

			System.out.printf("\n %-20s ", "Enter vehicle year:");

			inputYear = Integer.parseInt(scanner.nextLine());
			// Check the line below
			System.out.printf("\n %-20s ", "Enter vehicle description:");

			inputDescription = scanner.nextLine();

			System.out.printf("\n %-20s ", "Enter vehicle height:");

			inputHeight = Double.parseDouble(scanner.nextLine());

			/*
			 * If the vehicle height is less than or equal to 1.0m then stored as vehicle object, if it is more than 1.0m then stores the vehicle 
			 * as an over-sized vehicle, and prints the user with the information of what vehicle was created.
			*/
			if (inputHeight <= 1.0)
			{
				Vehicle newvehicle = new Vehicle(inputRegNo, inputMake, inputYear, inputDescription);
				vehicles.add(vehicleCount, newvehicle);

				System.out.println("New Vehicle added successfully for registration " + (vehicles.get(vehicleCount)).getRegNo() + ".");
				vehicleCount++;
                return vehicleCount;
			}

			else
			{
				OversizedVehicle newvehicle = new OversizedVehicle(inputRegNo, inputMake, inputYear, inputDescription,
						inputHeight);
				vehicles.add(vehicleCount, newvehicle);

				System.out.println("New oversized Vehicle added successfully for registration " + (vehicles.get(vehicleCount)).getRegNo() + ".");
				
				vehicleCount++;
				return vehicleCount;
			}
		}
        return vehicleCount;
        
		

	}

	//This class displays all the vehicles' information.
	public void displayVehicleInformation(int vehicleCount)
	{
		System.out.println((vehicles.get(vehicleCount)).getVehicleDetails());
		System.out.println();
	}

	//This class will allow to book a passage, and calculate the fee and booking ID. If there is any error, it'll display the error.
	public void bookPassage(int vehicleCount)
	{
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter registration number: ");
		String inputRegNo = scanner.next(); 

		int requiredVehicle = 0;
		boolean vehicleRegNoValid = false;

		//Checks if the registration number matches any vehicle that are stored.
		for(int i=(vehicleCount - 1); i>=0;i--)
		{
			if (((vehicles.get(i)).getRegNo()).equals(inputRegNo))
			{
				vehicleRegNoValid = true;
				requiredVehicle = i;
				i = -1;
			}
		}

		//If there is a vehicle with that registration number then asks for the booking date and passengers.
		if (vehicleRegNoValid == true)
		{
			System.out.println("Enter day of month: ");
			int day = scanner.nextInt();

			System.out.println("Enter month: ");
			int month = scanner.nextInt();

			System.out.println("Enter year: ");
			int year = scanner.nextInt();

			System.out.println("Enter passengers: ");
			int passengers = scanner.nextInt();

			DateTime bookingDate = new DateTime(day, month, year);

			boolean overSizedVehicleValid = false;

			double weight = 0;
			
			//If the vehicle is an over-sized vehicle, then asks for the weight.
			if ((vehicles.get(requiredVehicle)) instanceof OversizedVehicle)
			{
				System.out.println("Enter weight: ");
				weight = scanner.nextDouble();
				
				overSizedVehicleValid = true;
			}

			//Calculation the booking fee, by calling the book method for the appropriate vehicle object.
			double bookingFee = 0;
			boolean bookingValidity = true;
			try
			{
				if (overSizedVehicleValid == false)
				{
					bookingFee = (vehicles.get(requiredVehicle)).book(passengers, bookingDate);
				} else
				{
					bookingFee = ((OversizedVehicle) (vehicles.get(requiredVehicle))).book(passengers, bookingDate, weight);
				}
			} 
			
			//If there is an error with the number of passengers, date or the height, receives the thrown error and prints it.
			catch (VehicleException e)
			{
				System.out.println(e);
				bookingValidity = false;
			}

			//If booking is successful, prints the user the booking ID, booking date and the cost of the booking.
			if (bookingValidity == true)
			{

				System.out.println("Booking for " + (vehicles.get(requiredVehicle)).getRegNo() + " on " + bookingDate.getFormattedDate() + " was successful.");
				System.out.println("The total cost of the booking is: " + bookingFee);
			}

		}

		//If registration number is not found, prints this message.
		else
		{
			System.out.println("Error - registration number not found");
		}
	}

	//This method checks through the words of either the text file or backup text file and restore the vehicles.
	public int RestoringVehicles(String words[])
	{
		//restoring vehicles for the array provided.
			int wordNo=0; //used in loops
			
			while(wordNo<words.length)
				{
			    /*
			     * In the VehicleDetails file and the backup file, the first line for every vehicle starts with "Reg". So the while loop starts checking  
			     * for the word Reg. 
			     */
					if(words[wordNo].equals("Reg"))
					{
					    /*
					     * If it is found then the 2nd word to the right of it will be stored as registration number of the vehicle as In the VehicleDetails
					     * file After Reg the next word is "no:" and the next word is the vehicle's registration number. All the other objects will be 
					     * stored in the same way.
						 */
					    
					    boolean vehicleAddValidity = false;
						String vehicleRegNo = words[wordNo+2];
						String vehicleMake = words[wordNo+4];
						int vehicleYear = Integer.parseInt(words[wordNo+6]);
						wordNo+=8;
						String vehicleDescription = words[wordNo];
						wordNo++;
						
						/*For the description, it isn't certain how many words it can be. So, while loop checks for the word "Booking", which is the word that
						 *  comes after the vehicle description.
						 */
						while(!(words[wordNo].equals("Booking")))
						{
						    //The vehicle description will be stored with a space in between.
							vehicleDescription+=(" " + words[wordNo]);
							wordNo++;
						}
						
						String vehicleBookingRef = words[wordNo+2];
						wordNo+=3;
						
						int bookingDay=0;
						int bookingMonth=0;
						int bookingYear=0;
						int vehiclePassengers=0;
					
						//If the vehicle has booking information, then this boolean will be changed to true.
						boolean bookingValidity=false;
						
						//If the booking Reference is not N/A, then it has a booking ID. So, then it will add the booking details of the vehicle.
						if(!(vehicleBookingRef.equals("N/A")))
						{
							wordNo+=2;
							
							String[] dateArray = words[wordNo].split("/");
							
							bookingDay = Integer.parseInt(dateArray[0]);
							bookingMonth = Integer.parseInt(dateArray[1]);
							bookingYear = Integer.parseInt(dateArray[2]);
							
							wordNo+=4;
							
							vehiclePassengers = Integer.parseInt(words[wordNo]);
							wordNo+=3;
							
							bookingValidity=true;
						}
					
						boolean oversizedValidity=false;
						double vehicleWeight=0.0;
						
						/*
						 * In the file, over-sized vehicles have category after the booking information of the vehicle. So, if it is an over-sized
						 * vehicle then the height, weight will be stored and the vehicle will be added. If it is a normal vehicle, then it will be added,
						 * from the recorded information.
						 */
						
						if(words[wordNo].equals("Reg"))
						{
						Vehicle vehicleBackup = new Vehicle(vehicleRegNo, vehicleMake, vehicleYear, vehicleDescription);
						vehicles.add(vehicleCount, vehicleBackup);
						vehicleAddValidity = true;
						}
						
						else if(words[wordNo].equals("Category:"))
						{	
							wordNo+=3;	
							vehicleWeight=Double.parseDouble(words[wordNo]);
							wordNo+=2;
							double vehicleHeight=Double.parseDouble(words[wordNo]);
							
							OversizedVehicle oversizedVehicleBackup = new OversizedVehicle(vehicleRegNo, vehicleMake, vehicleYear, vehicleDescription, vehicleHeight);
							vehicles.add(vehicleCount, oversizedVehicleBackup);
							vehicleAddValidity= true;
							oversizedValidity=true;
						}
						
						//If the vehicle has booking information, then the added vehicle will be booked.
						if(bookingValidity==true)
						{
							DateTime bookingDate = new DateTime(bookingDay, bookingMonth, bookingYear);
							
							try
							{
							    //The vehicle will be added depending upon what vehicle it is.
								if(oversizedValidity==false)
								{
									(vehicles.get(vehicleCount)).book(vehiclePassengers,bookingDate);
								}
								
								else
								{
									((OversizedVehicle)(vehicles.get(vehicleCount))).book(vehiclePassengers,bookingDate, vehicleWeight);
								}		
							} catch (VehicleException e)
							{
								e.printStackTrace();
							}	
						}
						if(vehicleAddValidity==true)
						{
						    //If the vehicle is added, vehicleCount will be increased and the next vehicle will be added in the next position.
						vehicleCount++;	
						}
					}
					
					else
					{
						wordNo++;
					}
				}
				return vehicleCount;
	}
	
}
