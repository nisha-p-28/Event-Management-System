package DOA;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DTO.VenueDto;


public class VenueDao {

	 // Method to fetch all events from the database
    public List<VenueDto> getAllVenues(Connection conn) throws SQLException {

        String query = "SELECT * FROM venue";
        List<VenueDto> venues = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("✅ Query executed successfully. Fetching all events...");

            while (rs.next()) {
                VenueDto venue = new VenueDto();

                venue.setId(rs.getInt("venue_id"));
                venue.setName(rs.getString("venue_name"));
                venue.setLocation(rs.getString("location"));
                venue.setCapacity(rs.getInt("capacity"));
                
                venues.add(venue); 
            }
        }

        return venues; 
    }
    
    public void saveVenue(Connection conn, VenueDto venue) throws SQLException {
        String query = "INSERT INTO venue (venue_id, venue_name, location, capacity) VALUES (?,?,?,?)";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, venue.getId());         
            ps.setString(2, venue.getName());    
            ps.setString(3, venue.getLocation());
            ps.setInt(4, venue.getCapacity());    

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Venue added successfully!");
            } else {
                System.out.println("Failed to add venue.");
            }

        } 
    }
    
    
 // Method to search venue by ID or Name
    public VenueDto getVenue(Connection conn, int id, String name) throws SQLException {
        String query = "SELECT * FROM venue WHERE venue_name = ? OR venue_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, name);
            ps.setInt(2, id);

            try (ResultSet rs = ps.executeQuery()) {
                System.out.println("✅ Query executed successfully for Venue Name: " + name + " or Event ID: " + id);

                if (rs.next()) {
                    VenueDto venue = new VenueDto();

                    venue.setId(rs.getInt("venue_id"));
                    venue.setName(rs.getString("venue_name"));                   
                    venue.setLocation(rs.getString("location"));
                    venue.setCapacity(rs.getInt("capacity"));

                    return venue;
                } else {
                    return null;
                }
            }
        }
    }
    
    
  //Method for delete venue 
    public void deleteVenue(Connection conn, int id) throws SQLException {
    	
    	String query = "DELETE FROM venue WHERE venue_id = ?";
    	
    	try (PreparedStatement ps = conn.prepareStatement(query)) {
    		ps.setInt(1, id);
    		
    		int row = ps.executeUpdate();
    		
    		if (row > 0) {
    			System.out.println("Venue deleted successfully for Venue ID: " + id);
    		} else {
    			System.out.println("No venue found with Venue ID: " + id);
    		}
    	}
    }

}

