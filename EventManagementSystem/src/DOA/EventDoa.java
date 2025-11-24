package DOA;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DTO.EventDto;

public class EventDoa {
	//Method for Search event by id
    public EventDto getEventById(Connection conn, int id) throws SQLException {

        String eventQuery = "SELECT * FROM event WHERE event_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(eventQuery)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                System.out.println("Query executed successfully for Event ID: " + id);

                if (rs.next()) {
                    EventDto eventId = new EventDto();

                    eventId.setId(rs.getInt("event_id"));
                    eventId.setName(rs.getString("event_name"));
                    eventId.setVenueId(rs.getInt("venue_id"));
                    eventId.setDate(rs.getDate("date"));
                    eventId.setDesc(rs.getString("description"));
                    eventId.setTicketPrice(rs.getInt("ticket_price"));

                    return eventId;
                } 
                    return null;       
            }
        }
    }
    
    
    //Method for Search event by name
    public EventDto getEventByName(Connection conn, String name) throws SQLException {

        String eventQuery = "SELECT * FROM event WHERE event_name = ?";

        try (PreparedStatement ps = conn.prepareStatement(eventQuery)) {
            ps.setString(1, name);

            try (ResultSet rs = ps.executeQuery()) {
                System.out.println("Query executed successfully for Event Name: " + name);

                if (rs.next()) {
                    EventDto eventName = new EventDto();

                    eventName.setId(rs.getInt("event_id"));
                    eventName.setName(rs.getString("event_name"));
                    eventName.setVenueId(rs.getInt("venue_id"));
                    eventName.setDate(rs.getDate("date"));
                    eventName.setDesc(rs.getString("description"));
                    eventName.setTicketPrice(rs.getInt("ticket_price"));

                    return eventName;
                } 
                    return null;
                
            }
        }
    }
    
 // Method to fetch all events from the database
    public List<EventDto> getAllEvents(Connection conn) throws SQLException {

        String query = "SELECT * FROM event";
        List<EventDto> events = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("Query executed successfully. Fetching all events...");

            while (rs.next()) {
                EventDto event = new EventDto();

                event.setId(rs.getInt("event_id"));
                event.setName(rs.getString("event_name"));
                event.setVenueId(rs.getInt("venue_id"));
                event.setDate(rs.getDate("date"));
                event.setDesc(rs.getString("description"));
                event.setTicketPrice(rs.getInt("ticket_price"));

                events.add(event); 
            }
        }

        return events; 
    }


    
    
    // Method to search event by ID or Name
    public EventDto getEventByIdOrName(Connection conn, int id, String name) throws SQLException {
        String query = "SELECT * FROM event WHERE event_name = ? OR event_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, name);
            ps.setInt(2, id);

            try (ResultSet rs = ps.executeQuery()) {
                System.out.println("Query executed successfully for Event Name: " + name + " or Event ID: " + id);

                if (rs.next()) {
                    EventDto event = new EventDto();

                    event.setId(rs.getInt("event_id"));
                    event.setName(rs.getString("event_name"));
                    event.setVenueId(rs.getInt("venue_id"));
                    event.setDate(rs.getDate("date"));
                    event.setDesc(rs.getString("description"));
                    event.setTicketPrice(rs.getInt("ticket_price"));

                    return event;
                } else {
                    return null;
                }
            }
        }
    }

    
    
    // Method for Creating New Events 
    public void saveEvent(Connection conn, EventDto event) throws SQLException {
        String query = "INSERT INTO event (event_id, event_name, description, date, venue_id, ticket_price) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, event.getId());
            ps.setString(2, event.getName());
            ps.setString(3, event.getDesc());
            ps.setDate(4, event.getDate());
            ps.setInt(5, event.getVenueId());
            ps.setInt(6, (int) event.getTicketPrice());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Event created successfully.");
            } else {
                System.out.println("Failed to create event.");
            }
        }
    }
    
    
    //Method for updating event details 
    public void editEvent(Connection conn, EventDto event) throws SQLException {
    	
    	String query = "UPDATE event set ticket_price = ? WHERE event_id = ?";
    	
    	try (PreparedStatement ps = conn.prepareStatement(query)) {
    		ps.setInt(1, (int) event.getTicketPrice());
    		ps.setInt(2, event.getId());
    		
    		int rows = ps.executeUpdate();
    		
    		if (rows > 0) {
    			System.out.print("Event Updated Successfully."); 
    		} else {
    			System.out.print("Failed to Update Event.");
    		}
    	}
    }

    
    //Method for delete event 
    public void deleteEvent(Connection conn, int id) throws SQLException {
    	
    	String query = "DELETE FROM event WHERE event_id = ?";
    	
    	try (PreparedStatement ps = conn.prepareStatement(query)) {
    		ps.setInt(1, id);
    		
    		int row = ps.executeUpdate();
    		
    		if (row > 0) {
    			System.out.println("Event deleted successfully for Event ID: " + id);
    		} else {
    			System.out.println("No event found with Event ID: " + id);
    		}
    	}
    }


}


	


