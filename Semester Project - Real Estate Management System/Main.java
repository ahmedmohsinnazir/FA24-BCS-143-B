public class Main 
{
    public static void main(String[] args) 
    {
        Address address = new Address("123 Main St", "Lahore", "Punjab", "54000", "Pakistan");

        Admin admin = new Admin(1, "Alice", "alice@realestate.com", "admin123", "1234567890", "System Admin");
        Broker broker = new Broker(2, "Bob", "bob@realestate.com", "broker123", "0987654321", 0.05);
        Customer customer = new Customer(3, "Charlie", "charlie@gmail.com", "cust123", "1122334455");

        ResidentialProperty resProperty = new ResidentialProperty(101,5000.0,2000.0,
        PropertyStatus.AVAILABLE,address,3, 2,ResidentialType.SINGLE_FAMILY
        );

        broker.addProperty(resProperty);

        System.out.println("=== Admin Info ===");
        admin.displayInfo();

        System.out.println("\n=== Property Search: Lahore ===");
        Property[] searchResults = broker.searchByLocation("Lahore");
        if (searchResults.length == 0) {
            System.out.println("No properties found in Lahore.");
        } else {
            for (Property prop : searchResults) {
                System.out.println(prop.getDescription());
            }
        }

        Date appointmentDate = new Date(2025, 5, 20, 14, 30); // Custom Date object
        Appointment appointment = new Appointment(1, appointmentDate, customer, broker, resProperty);
        broker.scheduleAppointment(appointment);
        appointment.confirm();

        System.out.println("\n=== Appointment Details ===");
        System.out.println("Appointment ID: " + appointment.getAppointmentId());
        System.out.println("Date & Time: " + appointment.getDateTime());
        System.out.println("Customer: " + appointment.getCustomer().getName());
        System.out.println("Broker: " + appointment.getBroker().getName());
        System.out.println("Property: " + appointment.getProperty().getDescription());
        System.out.println("Status: " + appointment.getStatus());
    }
}