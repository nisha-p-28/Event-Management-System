package login;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import DOA.loginDao;
import Event.DBConnection;

import Service.EventService;
import Service.BookingService;
import Service.VenueService;

public class eventLogin {

    Scanner sc = new Scanner(System.in);

    public void doLogin() throws ClassNotFoundException, SQLException {

        System.out.println("Enter Username:");
        String username = sc.nextLine();

        System.out.println("Enter Password:");
        String password = sc.nextLine();

        try (Connection conn = DBConnection.getConnection()) {
            loginDao logindoa = new loginDao();
            String userType = logindoa.doLogin(conn, username, password);

            if (userType == null) {
                System.out.println("Invalid credentials! Please try again.");
                return;
            }

            System.out.println("Login successful! Welcome, " + username + " (" + userType + ")");

            if (userType.equalsIgnoreCase("Admin")) {
                System.out.println("Opening Admin Dashboard...");
                displayAdminMenu(conn);
            } else if (userType.equalsIgnoreCase("Participant")) {
                System.out.println("Opening Participant Dashboard...");
                displayParticipantMenu(conn, username);
            } else {
                System.out.println("Access Denied! Unknown user type.");
            }
        }
    }

    private void displayAdminMenu(Connection conn) throws SQLException {
        int choice;
        EventService eventService = new EventService();
        VenueService venueService = new VenueService();
        BookingService booking = new BookingService();

        do {
            System.out.println("\n========================================");
            System.out.println("          EVENT MANAGEMENT SYSTEM");
            System.out.println("========================================");
            System.out.println(" 1. Create New Event");
            System.out.println(" 2. View All Events");
            System.out.println(" 3. Update Event Details");
            System.out.println(" 4. Delete an Event");
            System.out.println(" 5. Manage Venues");
            System.out.println(" 6. View Bookings");
            System.out.println(" 7. Logout");
            System.out.println("========================================");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    eventService.createEvent(conn);
                    break;
                case 2:
                	searchEvent(conn);
                    break;
                case 3:
                    eventService.updateEvent(conn);
                    break;
                case 4:
                	eventService.deleteEventById(conn);
                    break;
                case 5:
                	manageVenues(conn);
                    break;
                case 6:
                	booking.displayBookingById(conn);
                    break;
                case 7:
                    System.out.println("Logged out successfully. Thank you, Admin!");
                    break;
                default:
                    System.out.println("Invalid choice! Please select a valid option.");
            }

        } while (choice != 7);
    }

    private void displayParticipantMenu(Connection conn, String username) throws SQLException {
        int choice;

        BookingService booking = new BookingService();
        do {
            System.out.println("\n========================================");
            System.out.println("         WELCOME PARTICIPANT");
            System.out.println("========================================");
            System.out.println(" 1. View All Events");
            System.out.println(" 2. Book an Event");
            System.out.println(" 3. View My Bookings");
            System.out.println(" 4. Cancel My Bookings");
            System.out.println(" 5. Logout");
            System.out.println("========================================");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();

            switch (choice) {
                case 1:
                	EventService eventservice = new EventService();
                	eventservice.displayAllEvents(conn);
                    break;
                case 2:                  	
                	booking.bookEvent(conn);
                    break;
                case 3:
                    booking.displayBooking(conn);
                    break;
                case 4:
                	booking.cancelBooking(conn);
                	break;
                case 5:
                    System.out.println("Logged out successfully. Thank you, " + username + "!");
                    break;
                default:
                    System.out.println("Invalid choice! Please select a valid option.");
            }

        } while (choice != 5);
    }

    // Method for search event  
    private void searchEvent(Connection conn) throws SQLException {
        System.out.println("1. Search Event with ID.");
        System.out.println("2. Search Event with Name.");
        System.out.println("3. View All Events.");
        System.out.print("Please Enter your Choice: ");
        int choice = sc.nextInt();

        EventService eventService = new EventService();

        switch (choice) {
            case 1:
                eventService.searchByID(conn);
                break;
            case 2:
                eventService.searchByName(conn);
                break;
            case 3:	
            	eventService.displayAllEvents(conn);
            	break;
            default:
                System.out.println("Invalid Choice! Try Again.");
        }
    }
    
    //Manage Venues
  private void manageVenues(Connection conn) throws SQLException {
    	System.out.println("1. View Venues.");
    	System.out.println("2. Add Venue."); 
    	System.out.println("3. Delete Venue.");
    	System.out.print("Please Enter your Choice: ");
    	
        VenueService venue = new VenueService();
        
        int choice = sc.nextInt();
		switch(choice) {
        case 1: 
        	venue.displayAllVenues(conn);
        	break;
        case 2:
        	venue.addVenue(conn);
        	break;
        case 3: 
        	venue.deleteVenue(conn);
        	break;
        default:
        		System.out.println("Invalid Choice.");
        }
    }
}

