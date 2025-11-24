package Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import DOA.EventDoa;
import DTO.EventDto;

public class EventService {

    Scanner sc = new Scanner(System.in);
	private Connection conn;

    // Method for event search with ID
    public void searchByID(Connection conn) throws SQLException {
        System.out.print("Enter Event ID: ");
        int id = sc.nextInt();

        EventDoa doaId = new EventDoa();
        EventDto eventId = doaId.getEventById(conn, id);

        if (eventId != null) {
            System.out.println("\n==== Event Details ====");
            System.out.println("ID: " + eventId.getId());
            System.out.println("Name: " + eventId.getName());
            System.out.println("Venue ID: " + eventId.getVenueId());
            System.out.println("Date: " + eventId.getDate());
            System.out.println("Description: " + eventId.getDesc());
            System.out.println("Ticket Price: " + eventId.getTicketPrice());
        } else {
        	System.out.println("No event found with the name: " + id);
        }
        
    }

    // Method for event search by name
    public void searchByName(Connection conn) throws SQLException {
        System.out.print("Enter Event Name: ");
        String name = sc.nextLine(); 
       

        EventDoa doaName = new EventDoa();
        EventDto eventName = doaName.getEventByName(conn, name);

        if (eventName != null) {
            System.out.println("\n==== Event Details ====");
            System.out.println("ID: " + eventName.getId());
            System.out.println("Name: " + eventName.getName());
            System.out.println("Venue ID: " + eventName.getVenueId());
            System.out.println("Date: " + eventName.getDate());
            System.out.println("Description: " + eventName.getDesc());
            System.out.println("Ticket Price: " + eventName.getTicketPrice());
        } else {
            System.out.println("No event found with the name: " + name);
        }
    }
    
    
 // Method for showing all events
    public void displayAllEvents(Connection conn) throws SQLException {
        EventDoa doa = new EventDoa();
        List<EventDto> events = doa.getAllEvents(conn);

        SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");

        if (events.isEmpty()) {
            System.out.println("No events found in the database.");
        } else {
            System.out.println("========= All Events List =========\n");

            System.out.println("+----------+-------------------------+------------------------------------------------------------+---------------+---------------+--------------+");
            System.out.println("| Event ID | Event Name              | Description                                                | Date          | Venue ID      | Ticket Price |");
            System.out.println("+----------+-------------------------+------------------------------------------------------------+---------------+---------------+--------------+");

            for (EventDto e : events) {
            	System.out.printf("| %-10d %-25s %-60s %-15s %-15d ₹%-11.2f | %n",
            		    e.getId(),                          // ✅ %-10d → int
            		    e.getName(),                        // ✅ %-20s → String
            		    e.getDesc(),                        // ✅ %-35s → String
            		    date.format(e.getDate()),          // ✅ %-12s → String (formatted date)
            		    e.getVenueId(),                    // ✅ %-10d → int
            		    e.getTicketPrice());               // ✅ ₹%-12.2f → double (2 decimal places)
            }

            System.out.println("+----------+-------------------------+------------------------------------------------------------+---------------+---------------+--------------+");
        }
    }        
    
        
    
 // Method for creating new events
    public void createEvent(Connection conn) throws SQLException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        System.out.println("======== Create New Event ========");
        System.out.print("Enter Event ID: ");
        int id = sc.nextInt();
        sc.nextLine(); // consume newline

        System.out.print("Enter Event Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Event Description: ");
        String desc = sc.nextLine();

        System.out.print("Enter Event Date (dd-MM-yyyy): ");
        String dateInput = sc.nextLine();
        LocalDate localDate = LocalDate.parse(dateInput, formatter);

        System.out.print("Enter Event Venue ID: ");
        int venueId = sc.nextInt();

        System.out.print("Enter Ticket Price: ");
        int ticket = sc.nextInt();

        EventDoa doa = new EventDoa();

        // Check if event already exists (by ID or name)
        EventDto existingEvent = doa.getEventByIdOrName(conn, id, name);
        if (existingEvent != null) {
            System.out.println("⚠️ Event already exists! Please use a unique ID or name.");
            return;
        }

        //Create new event
        EventDto input = new EventDto();
        input.setId(id);
        input.setName(name);
        input.setDesc(desc);
        input.setDate(java.sql.Date.valueOf(localDate));
        input.setVenueId(venueId);
        input.setTicketPrice(ticket);

        doa.saveEvent(conn, input);
    }
    
    
    //Method for updating event details 
    public void updateEvent(Connection conn) throws SQLException {
    	
        System.out.print("Enter Event ID: ");
        int id = sc.nextInt();
    	
        
        EventDoa doa = new EventDoa();
        EventDto event = doa.getEventById(conn, id);
    	
        if (event == null) {
        	System.out.println("Event not avaliable.");
        	return;
        }
        
        System.out.println("Update the new ticket price: ");
        int price = sc.nextInt();
        
        event.setTicketPrice(price); 
        
        doa.editEvent(conn, event);
    }
    
    //Delete Event by ID
    public void deleteEventById(Connection conn) throws SQLException {
    	System.out.println("Enter Event ID you Want to Delete.");
    	int id = sc.nextInt();    	

    	EventDoa doa = new EventDoa();
    	doa.deleteEvent(conn, id); 

    	}

}
