public class Calendar 
{
    private Date currentDate;
    private Appointment appointments[]; // Composition with Appointment
    private int appointmentCount;
    private static final int MAX_APPOINTMENTS = 100;
    
    // Singleton instance for getInstance() method
    private static Calendar instance = null;
    
    public Calendar(Date currentDate) 
    {
        this.currentDate = currentDate;
        this.appointments = new Appointment[MAX_APPOINTMENTS];
        this.appointmentCount = 0;
    }

    public Calendar() 
    {
        this(new Date());
    }
    
    public static synchronized Calendar getInstance() 
    {
        if (instance == null) 
        {
            instance = new Calendar();
        }
        return instance;
    }

    public void setTime(Date date) 
    {
        this.currentDate = date;
    }
    
    public Date getTime() 
    {
        return this.currentDate;
    }
    
    public void add(int field, int amount) 
    {
        switch (field) 
        {
            case DATE:
                this.currentDate = this.currentDate.addDays(amount);
                break;
            case MONTH:
                this.currentDate = this.currentDate.addMonths(amount);
                break;
            case YEAR:
                this.currentDate = this.currentDate.addYears(amount);
                break;
            case HOUR:
            case HOUR_OF_DAY:
                this.currentDate = this.currentDate.addHours(amount);
                break;
            case MINUTE:
                this.currentDate = this.currentDate.addMinutes(amount);
                break;
            default:
                throw new IllegalArgumentException("Unsupported calendar field: " + field);
        }
    }
    
    public static final int DATE = 5;          // Day of month
    public static final int MONTH = 2;         // Month
    public static final int YEAR = 1;          // Year
    public static final int HOUR = 10;         // Hour (12-hour clock)
    public static final int HOUR_OF_DAY = 11;  // Hour (24-hour clock)
    public static final int MINUTE = 12;       // Minute
    public static final int DAY_OF_WEEK = 7;   // Day of week
    
    public boolean addAppointment(Appointment appointment) 
    {
        if (appointmentCount < MAX_APPOINTMENTS) 
        {
            appointments[appointmentCount] = appointment;
            appointmentCount++;
            return true;
        } 
        else 
        {
            System.out.println("Calendar is full, cannot add more appointments");
            return false;
        }
    }
    
    public Appointment findAppointment(int appointmentId) 
    {
        for (int i = 0; i < appointmentCount; i++) 
        {
            if (appointments[i].getAppointmentId() == appointmentId) 
            {
                return appointments[i];
            }
        }
        return null;
    }
    
    public Appointment[] getAppointmentsOnDate(Date date) 
    {
        int count = 0;
        for (int i = 0; i < appointmentCount; i++) 
        {
            Date appointmentDate = appointments[i].getDateTime();
            if (isSameDay(appointmentDate, date)) 
            {
                count++;
            }
        }
        
        // Create result array with exact size
        Appointment[] result = new Appointment[count];
        int resultIndex = 0;
        
        // Fill result array
        for (int i = 0; i < appointmentCount; i++) 
        {
            Date appointmentDate = appointments[i].getDateTime();
            if (isSameDay(appointmentDate, date)) 
            {
                result[resultIndex] = appointments[i];
                resultIndex++;
            }
        }
        
        return result;
    }
    
    public Appointment[] getAppointmentsForBroker(int brokerId) 
    {
        int count = 0;
        for (int i = 0; i < appointmentCount; i++) 
        {
            if (appointments[i].getBroker().getUserId() == brokerId) 
            {
                count++;
            }
        }
        
        // Create result array with exact size
        Appointment[] result = new Appointment[count];
        int resultIndex = 0;
        
        // Fill result array
        for (int i = 0; i < appointmentCount; i++) 
        {
            if (appointments[i].getBroker().getUserId() == brokerId) 
            {
                result[resultIndex] = appointments[i];
                resultIndex++;
            }
        }
        
        return result;
    }
    
    public Appointment[] getAppointmentsForCustomer(int customerId) 
    {
        int count = 0;
        for (int i = 0; i < appointmentCount; i++) 
        {
            if (appointments[i].getCustomer().getUserId() == customerId) 
            {
                count++;
            }
        }
        
        // Create result array with exact size
        Appointment[] result = new Appointment[count];
        int resultIndex = 0;
        
        // Fill result array
        for (int i = 0; i < appointmentCount; i++) 
        {
            if (appointments[i].getCustomer().getUserId() == customerId) 
            {
                result[resultIndex] = appointments[i];
                resultIndex++;
            }
        }
        
        return result;
    }
    

    public Appointment[] getAppointmentsInDateRange(Date startDate, Date endDate) 
    {
        int count = 0;
        for (int i = 0; i < appointmentCount; i++) 
        {
            Date appointmentDate = appointments[i].getDateTime();
            if (!appointmentDate.isBefore(startDate) && !appointmentDate.isAfter(endDate)) 
            {
                count++;
            }
        }
        
        // Create result array with exact size
        Appointment[] result = new Appointment[count];
        int resultIndex = 0;
        
        // Fill result array
        for (int i = 0; i < appointmentCount; i++) 
        {
            Date appointmentDate = appointments[i].getDateTime();
            if (!appointmentDate.isBefore(startDate) && !appointmentDate.isAfter(endDate)) 
            {
                result[resultIndex] = appointments[i];
                resultIndex++;
            }
        }
        
        return result;
    }
    

    public Appointment[] getAppointmentsByStatus(String status) 
    {
        int count = 0;
        for (int i = 0; i < appointmentCount; i++) 
        {
            if (appointments[i].getStatus().equals(status)) 
            {
                count++;
            }
        }
        
        // Create result array with exact size
        Appointment[] result = new Appointment[count];
        int resultIndex = 0;
        
        // Fill result array
        for (int i = 0; i < appointmentCount; i++) 
        {
            if (appointments[i].getStatus().equals(status)) 
            {
                result[resultIndex] = appointments[i];
                resultIndex++;
            }
        }
        
        return result;
    }
    
    public boolean isBrokerAvailable(int brokerId, Date dateTime, int durationMinutes) 
    {
        Date endTime = dateTime.addMinutes(durationMinutes);
        
        for (int i = 0; i < appointmentCount; i++) 
        {
            Appointment appt = appointments[i];
            
            // Skip cancelled appointments and appointments for other brokers
            if ("cancelled".equals(appt.getStatus()) || appt.getBroker().getUserId() != brokerId) 
            {
                continue;
            }
            
            // Assume each appointment is 60 minutes by default
            Date apptStartTime = appt.getDateTime();
            Date apptEndTime = apptStartTime.addMinutes(60);
            
            // Check for overlap
            if (!(dateTime.isAfter(apptEndTime) || endTime.isBefore(apptStartTime))) 
            {
                return false; // There is an overlap, broker is not available
            }
        }
        
        return true; // No overlap found, broker is available
    }
    

    public Date[] getAvailableTimeSlots(int brokerId, Date date, int businessStartHour, 
    int businessEndHour, int slotDurationMinutes) 
    {
        // Create a list to hold available time slots
        Date[] tempSlots = new Date[(businessEndHour - businessStartHour) * (60 / slotDurationMinutes)];
        int slotCount = 0;
        
        // Create a date object for the specified date at business start hour
        Date slotTime = new Date(date.getYear(), date.getMonth(), date.getDay(), 
                              businessStartHour, 0);
        
        // Iterate through all possible time slots in business hours
        while (slotTime.getHour() < businessEndHour) 
        {
            if (isBrokerAvailable(brokerId, slotTime, slotDurationMinutes)) 
            {
                tempSlots[slotCount] = new Date(slotTime.getYear(), slotTime.getMonth(), 
                slotTime.getDay(), slotTime.getHour(), slotTime.getMinute());
                slotCount++;
            }
            slotTime = slotTime.addMinutes(slotDurationMinutes);
        }
        
        // Create result array with exact size
        Date[] result = new Date[slotCount];
        for (int i = 0; i < slotCount; i++) 
        {
            result[i] = tempSlots[i];
        }
        
        return result;
    }

    public Date getCurrentDate() 
    {
        return currentDate;
    }
    
    public void setCurrentDate(Date currentDate) 
    {
        this.currentDate = currentDate;
    }
    
    public void advanceDays(int days) 
    {
        currentDate = currentDate.addDays(days);
    }
    
    public Appointment[] getAllAppointments() 
    {
        Appointment[] result = new Appointment[appointmentCount];
        for (int i = 0; i < appointmentCount; i++) 
        {
            result[i] = appointments[i];
        }
        return result;
    }
    
    public int getAppointmentCount() 
    {
        return appointmentCount;
    }
    
    private boolean isSameDay(Date date1, Date date2) 
    {
        return date1.getYear() == date2.getYear() && 
               date1.getMonth() == date2.getMonth() && 
               date1.getDay() == date2.getDay();
    }
    

    public String generateMonthlyCalendarView(int year, int month) {
        StringBuilder calendar = new StringBuilder();
        
        // Add month and year header
        Date firstDayOfMonth = new Date(year, month, 1);
        calendar.append(firstDayOfMonth.getMonthName()).append(" ").append(year).append("\n");
        calendar.append("Su Mo Tu We Th Fr Sa\n");
        
        // Get the day of week for the first day of the month (0=Sunday, 6=Saturday)
        int firstDayOfWeek = firstDayOfMonth.getDayOfWeek() - 1; // Adjust to 0-based for calculation
        
        // Get the number of days in the month
        int daysInMonth = Date.getDaysInMonth(month, year);
        
        // Calculate the number of weeks needed
        int weeksInMonth = (int) Math.ceil((firstDayOfWeek + daysInMonth) / 7.0);
        
        // Generate the calendar grid
        int dayCounter = 1;
        for (int week = 0; week < weeksInMonth; week++) 
        {
            for (int dayOfWeek = 0; dayOfWeek < 7; dayOfWeek++) 
            {
                if (week == 0 && dayOfWeek < firstDayOfWeek) 
                {
                    // Print spaces before the first day
                    calendar.append("   ");
                } 
                else if (dayCounter <= daysInMonth) 
                {
                    // Print the day number with proper spacing
                    calendar.append(String.format("%2d ", dayCounter));
                    dayCounter++;
                }
            }
            calendar.append("\n");
        }
        
        return calendar.toString();
    }
    

    public static boolean hasSchedulingConflict(Appointment appointment1, 
    Appointment appointment2, int durationMinutes) 
    {
        // Get appointment dates
        Date time1 = appointment1.getDateTime();
        Date time2 = appointment2.getDateTime();
        
        // Calculate end times
        Date end1 = time1.addMinutes(durationMinutes);
        Date end2 = time2.addMinutes(durationMinutes);
        
        // Check for overlap
        return !(time1.isAfter(end2) || end1.isBefore(time2));
    }
}