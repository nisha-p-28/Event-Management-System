package Service;

import java.sql.Connection;
import java.util.Scanner;
import java.sql.SQLException;
import java.util.List;

import DTO.EventDto;
import DTO.VenueDto;
import DOA.EventDoa;
import DOA.VenueDao;

public class VenueService {

	
		
		Scanner sc = new Scanner(System.in);
		private Connection conn;

		
		 // Method for showing all venues
	    public void displayAllVenues(Connection conn) throws SQLException {
	        VenueDao doa = new VenueDao();
	        List<VenueDto> venues = doa.getAllVenues(conn);

	        if (venues.isEmpty()) {
	            System.out.println("No venues found in the database.");
	        } else {
	            System.out.println("========= All Venues List =========\n");

	            System.out.println("+----------+-------------------------+--------------------------------------------------+---------------+");
	            System.out.println("| Venue ID | Venue Name              | Location                                         | Capacity      |");
	            System.out.println("+----------+-------------------------+--------------------------------------------------+---------------+");

	            for (VenueDto e : venues) {
	            	System.out.printf("| %-10d %-25s %-50s %-13s | %n",
	            		    e.getId(),                          
	            		    e.getName(),
	            		    e.getLocation(),
	            		    e.getCapacity());                        
	            		             
	            }

	            System.out.println("+----------+-------------------------+--------------------------------------------------+---------------+");
	        }
	    }

	  //Method for adding new Venue
	  		public void addVenue(Connection conn) throws SQLException {
	  			System.out.println("======== Add New Venue ========");
	  	        System.out.print("Enter Venue ID: ");
	  	        int id = sc.nextInt();
	  	        sc.nextLine(); // consume newline

	  	        System.out.print("Enter Venue Name: ");
	  	        String name = sc.nextLine();
	  	        
	  	        System.out.println("Enter Venue Location: ");
	  	        String location = sc.nextLine();
	  	        
	  	        System.out.println("Enter Venue Capacity: ");
	  	        int capacity = sc.nextInt();
	  	        
	  	        VenueDao dao = new VenueDao();
	  	        
	  	   // Check if venue already exists (by ID or name)
	  	    VenueDto existingVenue = dao.getVenue(conn, id, name);
	  	        if (existingVenue != null) {
	  	            System.out.println("⚠️ Venue already exists! Please use a unique ID & name.");
	  	            return;
	  	        }
	  	        
	  	    //Create new event
	  	        VenueDto input = new VenueDto();
	  	        input.setId(id);
	  	        input.setName(name);
	  	        input.setLocation(location);      
	  	        input.setCapacity(capacity);

	  	        dao.saveVenue(conn, input);
	  	   }
	  		
	  		
	  		
	  	//Method Delete Event by ID
	  	    public void deleteVenue(Connection conn) throws SQLException {
	  	    	System.out.println("Enter Venue ID you Want to Delete.");
	  	    	int id = sc.nextInt();    	

	  	    	VenueDao dao = new VenueDao();
	  	    	dao.deleteVenue(conn, id); 

	  	    	}

}
