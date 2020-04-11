import java.sql.*;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import java.text.*;
import java.util.Date;
import java.util.Random;

public class ConnectExample{
	
	public static void main(String args []) throws SQLException{

		boolean continueLoop1 = true;
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Hello, you are trying to access the cs421g37 Hotel Database.");
		System.out.println("I am your personal assistant. What would you like to do?");
		
		while(continueLoop1) {
			
			System.out.println("---------------------------");
			System.out.println("Press 1-6 to continue:");
			System.out.println("---------------------------");
			System.out.println("1) Check in/out a customer.");
			System.out.println("2) Make a reservation.");
			System.out.println("3) View employee ids.");
			System.out.println("4) Browse customer reviews.");
			System.out.println("5) Procedure ...");
			System.out.println("6) Quit.");
			System.out.println("---------------------------");
				
			boolean validInput = false;
		    
		    do{
		        
		        if(sc.hasNextInt()){
		        	
		            int userInput = sc.nextInt();
		            
		            if((userInput >= 1) && (userInput <= 6)) {
		            	
		            	validInput = true;
		            	
		            	if(userInput == 1){
					    	checkInOutCustomer(sc);
					    }
					    
					    else if(userInput == 2){
					    	makeReservation(sc);
					    }
					    
					    else if(userInput == 3){
					    	viewEmployeeIds(sc);
					    }
					    
					    else if(userInput == 4){
					    	browseCustomerReviews(sc);
					    }
					    
					    else if(userInput == 5){
					    	procedure(sc);
					    }
					    
					    else{
					    	System.out.println("Goodbye.");
					    	continueLoop1 = false;
					    }
		            }
		            
		            else {
		            	System.out.println("Enter a valid Integer value: 1-6");
		            }
		        }
		        
		        else{
		        	sc.nextLine();
		        	System.out.println("Enter a valid Integer value, 1-6:");
		        }
		        
		    }while(!validInput);
			
			
			
		}
	    
		sc.close();

  }
	
	public static void checkInOutCustomer(Scanner sc) {
		
		System.out.println("You have chosen to check in/out a customer.");
		System.out.println("First, I need your employee id (eid).");
		System.out.println("If you are not sure, type 'back' to return to the main menu,");
		System.out.println("and select 3) View Employee ids, to find a list of eids.");
		System.out.println("Note, you must enter a Receptionist eid to check in/out a customer.");
		System.out.println("Enter your eid below:");
		
		boolean continueLoop3 = true;
		
		while(continueLoop3) {
		
			if(sc.hasNext()) {
				
				String userInput = sc.next();
				
				if (userInput.equals("back")) {
					continueLoop3 = false;
				}
				
				else {
					
					int userEIDInput = -1;
					
			        try {
			            int validInt = Integer.parseInt(userInput); 
			            userEIDInput = validInt; 
			            
						String[] eidEmployee = connectAndExecute("SELECT", "eid Employee", "int");
						int eidELength = eidEmployee.length;
						
						String[] eidReceptionist = connectAndExecute("SELECT", "eid Receptionist", "int");
						int eidRLength = eidReceptionist.length;
						
						boolean eidETruth = false;
						boolean eidRTruth = false;
						
						for(int i = 0; i < eidELength; i++) {
							if (eidEmployee[i].equals(String.valueOf(userEIDInput))) {
								eidETruth = true;
							}
						}
						
						for(int i = 0; i < eidRLength; i++) {
							if (eidReceptionist[i].equals(String.valueOf(userEIDInput))) {
								eidRTruth = true;
							}
						}
						
						if (eidRTruth) {
							
							continueLoop3 = false;
							
							System.out.println("You entered a valid Receptionist eid.");
							System.out.println("Now, please enter the customer's phone number,");
							System.out.println("or type 'back' to return to the main menu:");
							
							boolean continueLoop4 = true;
							
							while (continueLoop4) {
								
								if(sc.hasNext()) {
									
									userInput = sc.next();
									
									if (userInput.equals("back")) {
										continueLoop4 = false;
									}
									
									else {
										
										int userPNumInput = -1;
										
										try {
								            validInt = Integer.parseInt(userInput); 
								            userPNumInput = validInt; 
								            
											String[] pNumCustomer = connectAndExecute("SELECT", "phone_number Customer", "int");
											int pNumCLength = pNumCustomer.length;
											
											boolean pNumCTruth = false;
											
											for(int i = 0; i < pNumCLength; i++) {
												if (pNumCustomer[i].equals(String.valueOf(userPNumInput))) {
													pNumCTruth = true;
												}
											}
											
											if (pNumCTruth) {
												
												continueLoop4 = false;
												
												System.out.println("Would you like to check this customer in or out?");
												System.out.println("Type 'in', 'out', or 'back' for neither:");
												
												boolean continueLoop5 = true;
												
												while (continueLoop5) {
													
													if(sc.hasNext()) {
														
														userInput = sc.next();
														
														if (userInput.equals("back")) {
															continueLoop5 = false;
														}
														
														else if(userInput.equals("in")){
															
															String[] ridHandle = connectAndExecute("SELECT", "rid Handle phone_number " + String.valueOf(userPNumInput), "int");
															int ridHLength = ridHandle.length;
															
															if(ridHLength == 0) {
																
																System.out.println("There is no Reservation to check-in for this customer.");
																System.out.println("To make a Reservation, select 2) Handle a Reservation,");
																System.out.println("in the main menu.");
																continueLoop5 = false;
															}
															
															else {
																
																String inputString = "room_number Belongs phone_number ";
																
																for (int i = 0; i < ridHandle.length; i++) {
																	
																	inputString = inputString + ridHandle[i];
																	
																	if (i != (ridHandle.length - 1)) {
																		inputString = inputString + " ";
																	}
																}
																
																String[] rNumsBelongs = connectAndExecute("SELECT", inputString, "int");
																int rNumsBLength = rNumsBelongs.length;
																
																if(rNumsBLength == 0) {
																	System.out.println("It appears that all rooms have already been checked in by this Customer.");
																	continueLoop5 = false;
																}
																
																else {
																	
																	System.out.println("Here is a list of all possible rooms to check into:");
																	
																	for(int i = 0; i < rNumsBLength; i++) {
																		System.out.println(rNumsBelongs[i]);
																	}
																	
																	int roomsCheckedIn = 0;
																	boolean continueLoop6 = true;
																	
																	while (continueLoop6) {
																		
																		continueLoop5 = false;
																		
																		if (roomsCheckedIn < rNumsBLength){
																			
																			System.out.println("Which would room you like to check-in?");
																			System.out.println("Type one of the room numbers, or type 'done' to");
																			System.out.println("signify that you would not like to check-in a room:");
																			
																			if(sc.hasNext()) {
																				
																				userInput = sc.next();
																				String roomEntered = userInput;
																				
																				boolean validRoom = false;
																				
																				for(int i = 0; i < rNumsBLength; i++) {
																					if(roomEntered.equals(rNumsBelongs[i])) {
																						validRoom = true;
																					}
																				}
																				
																				if (userInput.equals("done")) {
																					continueLoop6 = false;
																				}
																				
																				else if(validRoom) {
																					connectAndExecute("UPDATE", "Room room_status OCC room_number " + roomEntered, null);
																					connectAndExecute("INSERT", "Check_in_out " + roomEntered + " " + userEIDInput + " " + userPNumInput, null);
																					roomsCheckedIn += 1;
																				}
																				
																				else {
																					
																					System.out.println("Please enter either 'done', or one of the following room numbers:");
																					
																					for(int i = 0; i < rNumsBLength; i++) {
																						System.out.println(rNumsBelongs[i]);
																					}
																				}
																				
																			}
																				
																	        else{
																	        	sc.nextLine();
																				System.out.println("Type one of the room numbers, or type 'done' to");
																				System.out.println("signify that you would not like to check-in a room:");
																	        }
																			
																		}
																		
																		else {
																			System.out.println("There are no more rooms to check-in.");
																			continueLoop6 = false;
																		}
																	}
																}
															}
														}
														
														else if(userInput.equals("out")) {
															
															String[] rNumCIO = connectAndExecute("SELECT", "room_number Check_in_out phone_number " + String.valueOf(userPNumInput), "int");
															int rNumCIOLength = rNumCIO.length;
															
															if(rNumCIOLength == 0) {
																
																System.out.println("It seems that this customer has not checked-in to the hotel.");
																continueLoop5 = false;
															}
															
															else {
																
																System.out.println("Here is a list of all possible rooms to check-out of:");
																
																for(int i = 0; i < rNumCIOLength; i++) {
																	System.out.println(rNumCIO[i]);
																}
																
																int roomsCheckedOut = 0;
																boolean continueLoop6 = true;
																
																while (continueLoop6) {
																	
																	continueLoop5 = false;
																	
																	if (roomsCheckedOut < rNumCIOLength){
																		
																		System.out.println("Which would room you like to check-out of?");
																		System.out.println("Type one of the room numbers, or type 'done' to");
																		System.out.println("signify that you would not like to check-out of a room:");
																		
																		if(sc.hasNext()) {
																			
																			userInput = sc.next();
																			String roomEntered = userInput;
																			
																			boolean validRoom = false;
																			
																			for(int i = 0; i < rNumCIOLength; i++) {
																				if(roomEntered.equals(rNumCIO[i])) {
																					validRoom = true;
																				}
																			}
																			
																			if (userInput.equals("done")) {
																				continueLoop6 = false;
																			}
																			
																			else if(validRoom) {
																				
																				connectAndExecute("UPDATE", "Room room_status V room_number " + roomEntered, null);
																				connectAndExecute("DELETE", "Check_in_out room_number" + roomEntered, null);
																				
																				String[] ridBelongs = connectAndExecute("SELECT", "rid Belongs room_number " + roomEntered, "int");
																				
																				String inputString = "phone_number Handle rid ";
																				
																				boolean foundReservation = false;
																				int ridBelongsCounter = 0;
																				
																				while(!foundReservation) {
																					
																					String realInputString = inputString + ridBelongs[ridBelongsCounter];
																					String[] pNumHandle = connectAndExecute("SELECT", realInputString, "int");
																					
																					boolean foundPNum = false;
																					
																					for (int j = 0; j < pNumHandle.length; j++) {
																						
																						if(pNumHandle[j].equals(String.valueOf(userPNumInput))) {
																							foundPNum = true;
																						}
																					}
																					
																					if(!foundPNum) {
																						ridBelongsCounter++;
																					}
																				}
																				
																				String ridValid = ridBelongs[ridBelongsCounter];
																				
																				String[] rNumBelongs = connectAndExecute("SELECT", "room_number Belongs rid " + ridValid, "int");
																				int rNumLength = rNumBelongs.length;
																				
																				connectAndExecute("DELETE", "Belongs room_number" + roomEntered + " rid " + ridValid, null);
																				
																				roomsCheckedOut += 1;
																				
																				if (rNumLength == 1) {
																					connectAndExecute("DELETE", "Handle rid " + ridValid, null);
																					connectAndExecute("DELETE", "Reservation rid " + ridValid, null);
																					
																				}
																			}
																			
																			else {
																				
																				System.out.println("Please enter either 'done', or one of the following room numbers:");
																				
																				for(int i = 0; i < rNumCIOLength; i++) {
																					System.out.println(rNumCIO[i]);
																				}
																			}
																			
																		}
																			
																        else{
																        	sc.nextLine();
																			System.out.println("Type one of the room numbers, or type 'done' to");
																			System.out.println("signify that you would not like to check-out of a room:");
																        }
																	}
																	
																	else {
																		System.out.println("There are no more rooms to check-out of.");
																		continueLoop6 = false;
																	}
																	
																}
															}
														}
												
														else {
															System.out.println("Please enter either 'in', 'out', or 'back' to exit:");
														}
														
													}
													
											        else{
											        	sc.nextLine();
											        	System.out.println("Please enter either 'in', 'out', or 'back' to exit:");
											        }
												}
											}
											
											else {
												System.out.println("Please enter either a valid phone number, or 'back':");
											}
										}
										
								        catch (NumberFormatException e)  {
								            System.out.println("Please enter either a valid phone number, or 'back':"); 
								        } 
									}
								}
								
						        else{
						        	sc.nextLine();
						        	System.out.println("Please enter either a valid phone number, or 'back':");
						        }
								
							}
						}
						
						else if(eidETruth) {
							System.out.println("You entered a valid eid, but it is not a Receptionist eid.");
							System.out.println("Please enter either a valid eid, or 'back':"); 
						}
						
						else {
							System.out.println("Please enter either a valid eid, or 'back':"); 
						}
			        }  
			        
			        catch (NumberFormatException e)  {
			            System.out.println("Please enter either a valid eid, or 'back':"); 
			        } 
				}
			}
			
	        else{
	        	sc.nextLine();
	        	System.out.println("Please enter either a valid eid, or 'back':");
	        }
			
		}
		
	}
	
	public static void makeReservation(Scanner sc) {
		
		System.out.println("You have chosen to make a reservation.");
		System.out.println("First, I need your employee id (eid).");
		System.out.println("If you are not sure, type 'back' to return to the main menu,");
		System.out.println("and select 3) View Employee ids, to find a list of eids.");
		System.out.println("Note, you must enter a Receptionist eid to check in/out a customer.");
		System.out.println("Enter your eid below:");
		
		boolean continueLoop3 = true;
		
		while(continueLoop3) {
		
			if(sc.hasNext()) {
				
				String userInput = sc.next();
				
				if (userInput.equals("back")) {
					continueLoop3 = false;
				}
				
				else {
					
					int userEIDInput = -1;
					
			        try {
			        	
			            int validInt = Integer.parseInt(userInput); 
			            userEIDInput = validInt; 
			            
						String[] eidEmployee = connectAndExecute("SELECT", "eid Employee", "int");
						int eidELength = eidEmployee.length;
						
						String[] eidReceptionist = connectAndExecute("SELECT", "eid Receptionist", "int");
						int eidRLength = eidReceptionist.length;
						
						boolean eidETruth = false;
						boolean eidRTruth = false;
						
						for(int i = 0; i < eidELength; i++) {
							if (eidEmployee[i].equals(String.valueOf(userEIDInput))) {
								eidETruth = true;
							}
						}
						
						for(int i = 0; i < eidRLength; i++) {
							if (eidReceptionist[i].equals(String.valueOf(userEIDInput))) {
								eidRTruth = true;
							}
						}
						
						if (eidRTruth) {
							
							continueLoop3 = false;
							
							System.out.println("You entered a valid Receptionist eid.");
							System.out.println("Now, please enter the customer's phone number,");
							System.out.println("or type 'back' to return to the main menu:");
		
							boolean continueLoop4 = true;
							
							while (continueLoop4) {
								
								if(sc.hasNext()) {
									
									userInput = sc.next();
									
									if (userInput.equals("back")) {
										continueLoop4 = false;
									}
									
									else {
										
										int userPNumInput = -1;
										
										try {
											
								            validInt = Integer.parseInt(userInput); 
								            userPNumInput = validInt; 
								            
								            String[] pNumCustomer = connectAndExecute("SELECT", "phone_number Customer", "int");
								            int pNumCLength = pNumCustomer.length;
								            
								            boolean customerFound = false;
								            
								            for(int i = 0; i < pNumCLength; i++) {
								            	if(pNumCustomer[i].equals(String.valueOf(userPNumInput))) {
								            		customerFound = true;
								            	}
								            }
								            
								            if(!customerFound) {
								            	
								            	System.out.println("This phone number does not appear in the database.");
								            	System.out.println("I would like to register this customer.");
								            	System.out.println("The name and email of the customer are required.");
								            	System.out.println("First, what is the customer's name.");
								            	System.out.println("Please enter your response:");
								            	
								            	String customerName = "";
								            	String customerEmail = "";
								            	
								            	boolean continueLoop5 = true;
												
												while (continueLoop5) {
													
													if(sc.hasNext()) {
														
														userInput = sc.next();
														
														continueLoop5 = false;
														
														if (userInput.equals("NULL")) {
															System.out.println("Please enter a valid name:");
														}
														
														else {
															customerName = userInput;
														}
													}
														
											        else{
											        	sc.nextLine();
											        	System.out.println("Please enter a valid name:");
											        }
												}
												
								            	System.out.println("Finally, what is the customer's email.");
								            	System.out.println("Please enter your response:");
												
												boolean continueLoop6 = true;
												
												while (continueLoop6) {
													
													if(sc.hasNext()) {
														
														userInput = sc.next();
														
														continueLoop6 = false;
														
														if (userInput.equals("NULL")) {
															System.out.println("Please enter a valid email:");
														}
														
														else {
															customerEmail = userInput;
														}
													}
														
											        else{
											        	sc.nextLine();
											        	System.out.println("Please enter a valid email:");
											        }
												}
												
												connectAndExecute("INSERT", "Customer " + String.valueOf(userPNumInput) + " " + customerName + " " + customerEmail, null);
								            }
								            
								            System.out.println("What day would you like to arrive?");
								            System.out.println("Enter the date in the format 'YYYY-MM-DD',");
								            System.out.println("or enter 'back' to exit this reservation.");
								            System.out.println("The date must be on '2020-05-01' or later:");
								            
								            continueLoop4 = false;
								            
											boolean continueLoop7 = true;
											String validDate1 = "";
											
											while (continueLoop7) {
												
												if(sc.hasNext()) {
													
													userInput = sc.next();
													validDate1 = userInput;
													
													if (userInput.equals("back")) {
														continueLoop7 = false;
													}
													
													else {
														
														String[] arrOfInput1 = validDate1.split("-", 0);
														
														try {
															
												            int validYear = Integer.parseInt(arrOfInput1[0]); 
												            int validMonth = Integer.parseInt(arrOfInput1[1]); 
												            int validDay = Integer.parseInt(arrOfInput1[2]); 
												            
												            if((arrOfInput1[0].length() != 4) || (arrOfInput1[1].length() != 2) || (arrOfInput1[2].length() != 2)) {
													        	System.out.println("Please enter either a valid date,");
													        	System.out.println("in the format 'YYYY-MM-DD', or 'back'.");
													        	System.out.println("The date must be on '2020-05-01' or later:");
												            }
												            
												            else if((validMonth < 1) || (validMonth > 12) || (validDay < 1) || (validDay > 31)) {
													        	System.out.println("Please enter either a valid date,");
													        	System.out.println("in the format 'YYYY-MM-DD', or 'back'.");
													        	System.out.println("The date must be on '2020-05-01' or later:");
												            }
												            
												            else if(validYear < 2020) {
													        	System.out.println("Please enter either a valid date,");
													        	System.out.println("in the format 'YYYY-MM-DD', or 'back'.");
													        	System.out.println("The date must be on '2020-05-01' or later:");
												            }
												            
												            else if((validYear == 2020) && (validMonth < 5)) {
													        	System.out.println("Please enter either a valid date,");
													        	System.out.println("in the format 'YYYY-MM-DD', or 'back'.");
													        	System.out.println("The date must be on '2020-05-01' or later:");
												            }
												            
												            else {
												            	continueLoop7 = false;
												            }
														}
														
														catch(NumberFormatException e) {
												        	System.out.println("Please enter either a valid date,");
												        	System.out.println("in the format 'YYYY-MM-DD', or 'back'.");
												        	System.out.println("The date must be on '2020-05-01' or later:");
														}
														
														catch(IndexOutOfBoundsException e) {
												        	System.out.println("Please enter either a valid date,");
												        	System.out.println("in the format 'YYYY-MM-DD', or 'back'.");
												        	System.out.println("The date must be on '2020-05-01' or later:");
														}
													}
												}

										        else{
										        	sc.nextLine();
										        	System.out.println("Please enter either a valid date,");
										        	System.out.println("in the format 'YYYY-MM-DD', or 'back'.");
										        	System.out.println("The date must be on '2020-05-01' or later:");
										        }
											}
											
								            System.out.println("What day would you like to leave?");
								            System.out.println("Enter the date in the format 'YYYY-MM-DD',");
								            System.out.println("or enter 'back' to exit this reservation.");
								            System.out.println("The date must be on '2020-05-02' or later:");
											
											boolean continueLoop8 = true;
											String validDate2 = "";
											
											while (continueLoop8) {
												
												if(sc.hasNext()) {
													
													userInput = sc.next();
													validDate2 = userInput;
													
													if (userInput.equals("back")) {
														continueLoop8 = false;
													}
													
													else {
														
														String[] arrOfInput2 = validDate2.split("-", 0);
														
														try {
															
												            int validYear = Integer.parseInt(arrOfInput2[0]); 
												            int validMonth = Integer.parseInt(arrOfInput2[1]); 
												            int validDay = Integer.parseInt(arrOfInput2[2]); 
												            
															String[] arrOfInput1 = validDate1.split("-", 0);
															
															int yearDiff = Integer.valueOf(arrOfInput2[0]) - Integer.valueOf(arrOfInput1[0]);
															int monthDiff = Integer.valueOf(arrOfInput2[1]) - Integer.valueOf(arrOfInput1[1]);
															int dayDiff = Integer.valueOf(arrOfInput2[2]) - Integer.valueOf(arrOfInput1[2]);
															
															int totalDays = (yearDiff * 365) + (monthDiff * 30) + dayDiff;
												            
												            if((arrOfInput2[0].length() != 4) || (arrOfInput2[1].length() != 2) || (arrOfInput2[2].length() != 2)) {
													        	System.out.println("Please enter either a valid date,");
													        	System.out.println("in the format 'YYYY-MM-DD', or 'back'.");
													        	System.out.println("The date must be after " + validDate1 + ":");
												            }
												            
												            else if((validMonth < 1) || (validMonth > 12) || (validDay < 1) || (validDay > 31)) {
													        	System.out.println("Please enter either a valid date,");
													        	System.out.println("in the format 'YYYY-MM-DD', or 'back'.");
													        	System.out.println("The date must be after " + validDate1 + ":");
												            }
												            
												            else if(validYear < 2020) {
													        	System.out.println("Please enter either a valid date,");
													        	System.out.println("in the format 'YYYY-MM-DD', or 'back'.");
													        	System.out.println("The date must be after " + validDate1 + ":");
												            }
												            
												            else if((validYear == 2020) && (validMonth < 5)) {
													        	System.out.println("Please enter either a valid date,");
													        	System.out.println("in the format 'YYYY-MM-DD', or 'back'.");
													        	System.out.println("The date must be after " + validDate1 + ":");
												            }
												            
												            else if(totalDays < 1) {
													        	System.out.println("Please enter either a valid date,");
													        	System.out.println("in the format 'YYYY-MM-DD', or 'back'.");
													        	System.out.println("The date must be after " + validDate1 + ":");
												            }
												            
												            else {
												            	continueLoop8 = false;
												            }
														}
														
														catch(NumberFormatException e) {
												        	System.out.println("Please enter either a valid date,");
												        	System.out.println("in the format 'YYYY-MM-DD', or 'back'.");
												        	System.out.println("The date must be after " + validDate1 + ":");
														}
														
														catch(IndexOutOfBoundsException e) {
												        	System.out.println("Please enter either a valid date,");
												        	System.out.println("in the format 'YYYY-MM-DD', or 'back'.");
												        	System.out.println("The date must be after " + validDate1 + ":");
														}
													}
												}

										        else{
										        	sc.nextLine();
										        	System.out.println("Please enter either a valid date,");
										        	System.out.println("in the format 'YYYY-MM-DD', or 'back'.");
										        	System.out.println("The date must be after " + validDate1 + ":");
										        }
											}
											
								            String[] rNumsRoom = connectAndExecute("SELECT", "room_number Room", "int");
											
											String inputString = "rid Belongs room_number ";
											
											SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
											
											Random rand = new Random();
											int rid = rand.nextInt(9000000) + 1000000;
											
											double price = 0.0;
											
											connectAndExecute("INSERT", "Reservation " + String.valueOf(rid) + " " + validDate1 + " " + validDate2 + " " + "9999.99", null);
											
											for (int i = 0; i < rNumsRoom.length; i++) {
												
												String selectString = inputString + rNumsRoom[i];
												
												String[] ridBelongs = connectAndExecute("SELECT", selectString, "int");
												
												boolean rNumValid = true;
												
												for (int j = 0; j < ridBelongs.length; j++) {
													
													String startDate = connectAndExecute("SELECT", "start_date Reservation rid " + ridBelongs[j], "date")[0];
													String endDate = connectAndExecute("SELECT", "end_date Reservation rid " + ridBelongs[j], "date")[0];
													
													try {
														
														Date startD = sdformat.parse(startDate);
														Date endD = sdformat.parse(endDate);
														Date given1D = sdformat.parse(validDate1);
														Date given2D = sdformat.parse(validDate2);
														
														if(((given1D.compareTo(endD) < 0) && (given1D.compareTo(startD) > 0)) || ((given2D.compareTo(endD) < 0) && (given2D.compareTo(startD) > 0))) {
													         rNumValid = false;
														}
														
													} 
													
													catch (ParseException e) {
														e.printStackTrace();
													}
												}
												
												String[] roomType = connectAndExecute("SELECT", "type Room room_number " + rNumsRoom[i], "string");
												
												if (rNumValid) {
													
													System.out.println("Room with rid (room id) " + rNumsRoom[i] + " is available for the dates you requested.");
													System.out.println("This room is a " + roomType[0] + " type room, all rooms have 2 beds.");
													System.out.println("Would you like to reserve this room?");
													System.out.println("Enter 'yes' to reserve this room, enter 'no',");
													System.out.println("to not reserve this room, or enter 'done' to stop reserving rooms:");
													
													boolean continueLoop9 = true;
													
													while (continueLoop9) {
														
														if(sc.hasNext()) {
															
															userInput = sc.next();
															
															if (userInput.equals("yes")) {
																
																continueLoop9 = false;
																
																String[] arrOfInput1 = validDate1.split("-", 0);
																String[] arrOfInput2 = validDate2.split("-", 0);
																
																int yearDiff = Integer.valueOf(arrOfInput2[0]) - Integer.valueOf(arrOfInput1[0]);
																int monthDiff = Integer.valueOf(arrOfInput2[1]) - Integer.valueOf(arrOfInput1[1]);
																int dayDiff = Integer.valueOf(arrOfInput2[2]) - Integer.valueOf(arrOfInput1[2]);
																
																int totalDays = (yearDiff * 365) + (monthDiff * 30) + dayDiff;
																
																if (roomType[0].equals("Queen")) {
																	price = price + (totalDays * 100.0);
																}
																
																else if (roomType[0].equals("King")) {
																	price = price + (totalDays * 200.0);
																}

																else if (roomType[0].equals("Suite")) {
																	price = price + (totalDays * 500.0);
																}
																
																connectAndExecute("INSERT", "Belongs " + String.valueOf(rid) + " " + String.valueOf(rNumsRoom[i]), null);
																
																continueLoop9 = false;
															}
															
															if (userInput.equals("no")) {
																continueLoop9 = false;
															}
															
															if (userInput.equals("done")) {
																continueLoop9 = false;
																i = 100000;
															}
															
															else {
																System.out.println("Please enter either 'yes', 'no', or 'done':");
															}
														}
														
												        else{
												        	sc.nextLine();
												        	System.out.println("Please enter either 'yes', 'no', or 'done':");
												        }
													}
												}	
											}
											
											connectAndExecute("UPDATE", "Reservation price_amount " + String.valueOf(price) + " rid " + String.valueOf(rid), null);
											connectAndExecute("INSERT", "Handle " + String.valueOf(rid) + " " + String.valueOf(userEIDInput) + " " + String.valueOf(userPNumInput), null);
										}
										
								        catch (NumberFormatException e)  {
								            System.out.println("Please enter either a valid phone number, or 'back':"); 
								        } 
									}    
								}
								
						        else{
						        	sc.nextLine();
						        	System.out.println("Please enter either a valid phone number, or 'back':");
						        }
							}	            
						}
						
						else if(eidETruth) {
							System.out.println("You entered a valid eid, but it is not a Receptionist eid.");
							System.out.println("Please enter either a valid eid, or 'back':"); 
						}
						
						else {
							System.out.println("Please enter either a valid eid, or 'back':"); 
						}
			        }
			        
			        catch (NumberFormatException e)  {
			            System.out.println("Please enter either a valid eid, or 'back':"); 
			        } 
				}
			}
			
	        else{
	        	sc.nextLine();
	        	System.out.println("Please enter either a valid eid, or 'back':");
	        }
			
		}
		
	}
	
	public static void viewEmployeeIds(Scanner sc) {
		
		
		
	}
	
	public static void browseCustomerReviews(Scanner sc) {
		
		
		
	}
	
	public static void procedure(Scanner sc) {
		
		
		
	}
	
	public static String[] connectAndExecute(String executionType, String arguments, String selectType){
		
	    try {
	    	Class.forName("org.postgresql.Driver");
	    } catch (ClassNotFoundException e){
	    	e.printStackTrace();
	    }
	    
	    // Connect to the database
	    
	    // auto close connection
	    try(Connection conn = DriverManager.getConnection("jdbc:postgresql://comp421.cs.mcgill.ca:5432/cs421", "cs421g37", "v447hm7d")){
	      
	    	String[] returnedValues;
	    	
	    	if (conn != null) {
	    		
		    	if(executionType.equals("SELECT")) {
		    		returnedValues = select(conn, arguments, selectType);
		    	}
		    	
		    	else if(executionType.equals("INSERT")){
		    		insert(conn, arguments);
		    		returnedValues = null;
		    	}
		    	
		    	else if(executionType.equals("UPDATE")){
		    		update(conn, arguments);
		    		returnedValues = null;
		    	}
		    	
		    	else if(executionType.equals("DELETE")){
		    		delete(conn, arguments);
		    		returnedValues = null;
		    	}
		    	
		    	else {
		    		returnedValues = null;
		    	}
	        
	    	} 
	    	
	    	else {
	    		
	    		System.out.println("Failed to make connection to database!");
	    		System.out.println("Check your wifi, or possibly VPN!");
	    		
	    		returnedValues = null;
	    	}
	    	
			conn.close();
			return returnedValues;
	      
	    } 
	    
	    catch (SQLException e){
	    	System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
	      
	    } 
	    
	    catch (Exception e){
	    	e.printStackTrace();
	    }
		
	    return null;
	    
	}
	
	public static void insert(Connection conn, String arguments) {
		
		try {
			
			String[] arrOfStr = arguments.split(" ", 0);
			
			String insertString = "";
			
			for (int i = 1; i < arrOfStr.length; i++) {
				
				insertString = insertString + arrOfStr[i];
				
				if (i == (arrOfStr.length - 1)) {
					insertString += ")";
				}
				
				else {
					insertString += ", ";
				}
			}
			
			Statement stmtInsert = conn.createStatement();
			stmtInsert.executeUpdate("INSERT INTO " + arrOfStr[0] + " VALUES(" + insertString);
			
			stmtInsert.close();
		}
		
	    catch (SQLException e){
	    	System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
	    } 
	    
	    catch (Exception e){
	    	e.printStackTrace();
	    }
	}
	
	public static void delete(Connection conn, String arguments) {
		
		try {
			
			String[] arrOfStr = arguments.split(" ", 0);
			
			Statement stmtDelete = conn.createStatement();
			
			String executeString = "DELETE FROM " + arrOfStr[0] + " WHERE " + arrOfStr[1] + " = " + arrOfStr[2];
			
			int stringAttachments = (arrOfStr.length - 3) / 2;
			int stringAttachmentCounter = 0;
			
			while (stringAttachments != stringAttachmentCounter) {
				
				executeString = executeString + " AND " + arrOfStr[3 + (2 * stringAttachmentCounter)] + " = " + arrOfStr[4 + (2 * stringAttachmentCounter)];
				stringAttachmentCounter += 1;
			}
			
			stmtDelete.executeUpdate(executeString);
			stmtDelete.close();
		}
		
	    catch (SQLException e){
	    	System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
	    } 
	    
	    catch (Exception e){
	    	e.printStackTrace();
	    }
	}
	
	public static void update(Connection conn, String arguments) {
		
		try {
			
			String[] arrOfStr = arguments.split(" ", 0);
			
			Statement stmtUpdate = conn.createStatement();
			stmtUpdate.executeUpdate("UPDATE " + arrOfStr[0] + " SET " + arrOfStr[1] + " = " + arrOfStr[2] + " WHERE " + arrOfStr[3] + " = " + arrOfStr[4]);
			
			stmtUpdate.close();
		}
		
	    catch (SQLException e){
	    	System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
	    } 
	    
	    catch (Exception e){
	    	e.printStackTrace();
	    }
	}
	
	public static String[] select(Connection conn, String arguments, String selectType) {
		
		try {
			
			String[] arrOfStr = arguments.split(" ", 0);
			
			if (selectType.equals("int")) {
				
				if (arrOfStr.length == 2) {
					
					Statement stmtSelect = conn.createStatement();
					ResultSet rsSelect = stmtSelect.executeQuery("SELECT " + arrOfStr[0] + " FROM " + arrOfStr[1]);
					
					Statement stmtCount = conn.createStatement();
					ResultSet rsCount = stmtCount.executeQuery("SELECT COUNT(" + arrOfStr[0] + ") FROM " + arrOfStr[1]);
					
					int selectLength = 0;
					
					while(rsCount.next()) {
						selectLength = rsCount.getInt(1);
					}
					
					String[] returnedValues = new String[selectLength];
					
					int rsCounter = 0;
					
					while(rsSelect.next()){
						returnedValues[rsCounter] = String.valueOf(rsSelect.getInt(1));
						rsCounter++;
					}
					
					stmtSelect.close();
					stmtCount.close();
					
					return returnedValues;
				}
				
				else if(arrOfStr.length >= 4) {
					
					PreparedStatement prepareSelect = conn.prepareStatement("SELECT " + arrOfStr[0] + " FROM " + arrOfStr[1] + " WHERE " + arrOfStr[2] + " = ?");
					PreparedStatement prepareCount = conn.prepareStatement("SELECT COUNT(" + arrOfStr[0] + ") FROM " + arrOfStr[1] + " WHERE " + arrOfStr[2] + " = ?");
					
					int totalCount = 0;
					
					ResultSet rsCount;
					
					for(int i = 3; i < arrOfStr.length; i++) {
						
						prepareCount.setString(1, arrOfStr[i]);
						rsCount = prepareSelect.executeQuery();
						
						int selectLength = 0;
						
						while(rsCount.next()) {
							selectLength = rsCount.getInt(1);
						}
						
						totalCount += selectLength;
						
					}
					
					String[] returnedValues = new String[totalCount];
					
					int rsCounter = 0;
					
					ResultSet rsSelect;
					
					for(int i = 3; i < arrOfStr.length; i++) {
						
						prepareSelect.setString(1, arrOfStr[i]);
						rsSelect = prepareSelect.executeQuery();
						
						while(rsSelect.next()){
							returnedValues[rsCounter] = String.valueOf(rsSelect.getInt(1));
							rsCounter++;
						}
						
					}
					
					prepareSelect.close();
					prepareCount.close();
					
					return returnedValues;
				}
			}
			
			else if(selectType.equals("date")) {
				
				Statement stmtSelect = conn.createStatement();
				ResultSet rsSelect = stmtSelect.executeQuery("SELECT " + arrOfStr[0] + " FROM " + arrOfStr[1] + " WHERE " + arrOfStr[2] + " = " + arrOfStr[3]);
				
				String selectDate = "";
				
				while(rsSelect.next()) {
					selectDate = rsSelect.getDate(1).toString();
				}
				
				String[] returnedValues = new String[1];
				returnedValues[0] = selectDate;
				
				stmtSelect.close();
				
				return returnedValues;
				
			}
			
			else if(selectType.equals("string")) {
				
				Statement stmtSelect = conn.createStatement();
				ResultSet rsSelect = stmtSelect.executeQuery("SELECT " + arrOfStr[0] + " FROM " + arrOfStr[1] + " WHERE " + arrOfStr[2] + " = " + arrOfStr[3]);
				
				Statement stmtCount = conn.createStatement();
				ResultSet rsCount = stmtCount.executeQuery("SELECT COUNT(" + arrOfStr[0] + ") FROM " + arrOfStr[1] + " WHERE " + arrOfStr[2] + " = " + arrOfStr[3]);
				
				int selectLength = 0;
				
				while(rsCount.next()) {
					selectLength = rsCount.getInt(1);
				}
				
				String[] returnedValues = new String[selectLength];
				
				int rsCounter = 0;
				
				while(rsSelect.next()){
					returnedValues[rsCounter] = rsSelect.getString(1);
					rsCounter++;
				}
				
				stmtSelect.close();
				stmtCount.close();
				
				return returnedValues;
			}
		}
		
	    catch (SQLException e){
	    	System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
	    } 
	    
	    catch (Exception e){
	    	e.printStackTrace();
	    }
		
		return null;
		
	}
	
}