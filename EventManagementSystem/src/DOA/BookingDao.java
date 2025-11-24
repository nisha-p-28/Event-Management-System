package DOA;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DTO.BookingsDto;



public class BookingDao {

    // Check if already booked
    public boolean checkUserBooking(Connection conn, int eventId, int userId) throws SQLException {

        String query = "SELECT * FROM booking WHERE event_id = ? AND user_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, eventId);
            ps.setInt(2, userId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); 
            }
        }
    }
    
    
    public boolean checkBookingExists(Connection conn, int userId, int eventId) throws SQLException {

        String query = "SELECT * FROM booking WHERE user_id = ? AND event_id = ? AND status = 'booked'";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userId);
            ps.setInt(2, eventId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }


    // Adding new booking in database
    public void bookEvent(Connection conn, BookingsDto booking) throws SQLException {

        String query = "INSERT INTO booking (user_id, event_id, status) VALUES (?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, booking.getUserId());
            ps.setInt(2, booking.getEventId());
            ps.setString(3, booking.getStatus());

            ps.executeUpdate();
        }
    }
    
    //Canceling the booking
    public void cancelBooking(Connection conn, int userId, int eventId) throws SQLException {

        String query = "UPDATE booking SET status = 'cancelled' WHERE user_id = ? AND event_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userId);
            ps.setInt(2, eventId);

            ps.executeUpdate();
        }
    }

   
    //Fetching ticket cancellation details 
    public String[] getCancelBookingDetails(Connection conn, int userId, int eventId) throws SQLException {

        String query = "SELECT u.email, e.event_name, e.date, v.venue_name "
                + "FROM user u "
                + "JOIN booking b ON b.user_id = u.user_id "
                + "JOIN event e ON e.event_id = b.event_id "
                + "JOIN venue v ON e.venue_id = v.venue_id "
                + "WHERE b.user_id = ? AND b.event_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userId);
            ps.setInt(2, eventId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new String[]{
                            rs.getString("email"),
                            rs.getString("event_name"),
                            rs.getString("date"),
                            rs.getString("venue_name")
                    };
                }
            }
        }
        return null;
    }

    
    // Fetch user email + event name + date + venue
    public String[] getBookingDetails(Connection conn, int userId, int eventId) throws SQLException {

        String query = "SELECT u.email, e.event_name, e.date, v.venue_name " +
                       "FROM user u " +
                       "JOIN event e ON e.event_id = ? " +
                       "JOIN venue v ON e.venue_id = v.venue_id " +
                       "WHERE u.user_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, eventId);
            ps.setInt(2, userId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {

                    return new String[] {
                            rs.getString("email"),
                            rs.getString("event_name"),
                            rs.getString("date"),
                            rs.getString("venue_name")
                    };
                }
            }
        }
        return null;
    }


    
    //Display bookings to participants
    public BookingsDto getMyBooking(Connection conn, BookingsDto bookings) throws SQLException {

        String query = "SELECT * FROM booking WHERE user_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, bookings.getUserId());

            try (ResultSet rs = ps.executeQuery()) {
                System.out.println("Query executed successfully for User ID: " + bookings.getUserId());

                if (rs.next()) {
                    BookingsDto booking = new BookingsDto();

                    // Set values from result set into DTO
                    booking.setBookingId(rs.getInt("booking_id"));
                    booking.setUserId(rs.getInt("user_id"));
                    booking.setEventId(rs.getInt("event_id"));
                    booking.setBooking_date(rs.getDate("booking_date")); 
                    booking.setStatus(rs.getString("status"));

                    return booking;
                } else {
                    return null;
                }
            }
        }
    }

    
    public BookingsDto getMyBookings(Connection conn, int userId) throws SQLException {

        String query = 
            "SELECT b.booking_id, b.user_id, b.event_id, b.booking_date, b.status, " +
            "e.event_name AS event_name, e.date AS event_date " +
            "FROM booking b " +
            "JOIN event e ON b.event_id = e.event_id " +
            "WHERE b.user_id = ?";

        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, userId);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            BookingsDto booking = new BookingsDto();

            booking.setBookingId(rs.getInt("booking_id"));
            booking.setUserId(rs.getInt("user_id"));
            booking.setEventName(rs.getString("event_name"));
            booking.setEventDate(rs.getDate("event_date"));
            booking.setEventId(rs.getInt("event_id"));
            booking.setBooking_date(rs.getDate("booking_date"));
            booking.setStatus(rs.getString("status"));

            // event details
            

            return booking;
        }
        return null;
    }

 // Fetch single booking by event id (returns first matching booking or null)
    public BookingsDto getBookingById(Connection conn, int eventId) throws SQLException {

        String query = "SELECT * FROM booking WHERE event_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, eventId);

            try (ResultSet rs = ps.executeQuery()) {
                System.out.println("Query executed successfully for Event ID: " + eventId);

                if (rs.next()) {
                    BookingsDto booking = new BookingsDto();

                    // Set values from result set into DTO
                    booking.setBookingId(rs.getInt("booking_id"));
                    booking.setUserId(rs.getInt("user_id"));
                    booking.setEventId(rs.getInt("event_id"));
                    booking.setBooking_date(rs.getDate("booking_date")); 
                    booking.setStatus(rs.getString("status"));

                    return booking;
                } else {
                    return null;
                }
            }
        }
    }


    
}
