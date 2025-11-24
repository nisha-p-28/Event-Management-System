package Service;

import DOA.BookingDao;
import DTO.BookingsDto;
import util.EmailUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class BookingService {

    Scanner sc = new Scanner(System.in);

    // Booking Event from participants
    public void bookEvent(Connection conn) throws SQLException {

        System.out.println("======== Book Event ========");

        System.out.print("Enter User ID: ");
        int userId = sc.nextInt();

        System.out.print("Enter Event ID: ");
        int eventId = sc.nextInt();

        BookingDao dao = new BookingDao();


        // 1️. Check if user already booked event
        boolean isBooked = dao.checkUserBooking(conn, eventId, userId);

        if (isBooked) {
            System.out.println("You have already booked this event!");
            return;
        }


        // 2️. Create booking object
        BookingsDto booking = new BookingsDto();
        booking.setUserId(userId);
        booking.setEventId(eventId);
        booking.setStatus("booked");


        // 3️. Insert booking in DB
        dao.bookEvent(conn, booking);
        System.out.println("✅ Event booked successfully!");

        // 4️. Fetch user email + event details + venue
        String[] details = dao.getBookingDetails(conn, userId, eventId);

        if (details == null) {
            System.out.println("Could not fetch email/event details.");
            return;
        }

        // Extract details
        String userEmail  = details[0];
        String eventName  = details[1];
        String eventDate  = details[2];
        String venueName  = details[3];


        // 5️. Send confirmation email
        EmailUtil.sendBookingEmail(userEmail, eventName, eventDate, venueName);

        System.out.println("Booking confirmation email has been sent to: " + userEmail);
    }

    
    //canceling event booking 
    public void cancelBooking(Connection conn) throws SQLException {

        System.out.println("======== Cancel Booking ========");

        System.out.print("Enter User ID: ");
        int userId = sc.nextInt();

        System.out.print("Enter Event ID: ");
        int eventId = sc.nextInt();

        BookingDao dao = new BookingDao();

        // 1️. Check if booking exists
        boolean exists = dao.checkBookingExists(conn, userId, eventId);

        if (!exists) {
            System.out.println("No active booking found for this event!");
            return;
        }

        // 2️. Cancel booking in DB
        dao.cancelBooking(conn, userId, eventId);
        System.out.println("Ticket cancelled successfully!");

        // 3️. Fetch details for email
        String[] details = dao.getCancelBookingDetails(conn, userId, eventId);

        if (details == null) {
            System.out.println("Could not fetch cancellation details.");
            return;
        }

        String email = details[0];
        String eventName = details[1];
        String eventDate = details[2];
        String venueName = details[3];

        // 4️. Send cancellation email
        EmailUtil.sendCancellationEmail(email, eventName, eventDate, venueName);

        System.out.println("Cancellation email has been sent to: " + email);
    }

    
    //display booking to participants
    public void displayMyBooking(Connection conn) throws SQLException {
        System.out.print("Enter User ID: ");
        int id = sc.nextInt();

        // Create object and set ID
        BookingsDto input = new BookingsDto();
        input.setUserId(id);

        BookingDao dao = new BookingDao();
        BookingsDto participant = dao.getMyBooking(conn, input);

        if (participant != null) {
            System.out.println("\n==== Event Details ====");
            System.out.println("User ID: " + participant.getUserId());
            System.out.println("Booking ID: " + participant.getBookingId());
            System.out.println("Event ID: " + participant.getEventId());
            System.out.println("Booking Date: " + participant.getBooking_date());
            System.out.println("Status: " + participant.getStatus());
        } else {
            System.out.println("No booking found with the user id: " + id);
        }
    }

    
    public void displayBooking(Connection conn) throws SQLException {

        System.out.print("Enter User ID: ");
        int userId = sc.nextInt();

        BookingDao dao = new BookingDao();
        BookingsDto booking = dao.getMyBookings(conn, userId);

        if (booking != null) {

            System.out.println("\n=========== Booking Details ===========");

            System.out.println("Booking ID   : " + booking.getBookingId());
            System.out.println("User ID      : " + booking.getUserId());
            System.out.println("Event Name   : " + booking.getEventName());
            System.out.println("Event Date   : " + booking.getEventDate());
            System.out.println("Event ID     : " + booking.getEventId());
            System.out.println("Booking Date : " + booking.getBooking_date());
            System.out.println("Status       : " + booking.getStatus());

            

            System.out.println("=========================================");
        } 
        else {
            System.out.println("No booking found for user: " + userId);
        }
    }


    //Display all the bookings to admin
    public void displayBookingById(Connection conn) throws SQLException {

        System.out.print("Enter Event ID: ");
        int eventId = sc.nextInt();

        BookingDao dao = new BookingDao();

        BookingsDto booking = dao.getBookingById(conn, eventId);

        if (booking != null) {

            System.out.println("\n================ BOOKING DETAILS ================\n");

            System.out.println("+------------+----------+-----------+----------------+----------+");
            System.out.println("| Booking ID | Event ID | User ID   | Booking Date   | Status   |");
            System.out.println("+------------+----------+-----------+----------------+----------+");

            System.out.printf("| %-10d | %-8d | %-9d | %-14s | %-8s |\n",
                    booking.getBookingId(),
                    booking.getEventId(),
                    booking.getUserId(),
                    booking.getBooking_date().toString(),
                    booking.getStatus()
            );

            System.out.println("+------------+----------+-----------+----------------+----------+\n");
        }
        else {
            System.out.println("\nNo booking found for Event ID: " + eventId);
        }
    }


	

}


