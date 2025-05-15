public class Date 
{
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    
    public Date(int year, int month, int day, int hour, int minute) 
    {
        validateDate(year, month, day);
        validateTime(hour, minute);
        
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }
    
    public Date(int year, int month, int day) 
    {
        this(year, month, day, 0, 0);
    }

    public Date() 
    {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        this.year = calendar.get(java.util.Calendar.YEAR);
        this.month = calendar.get(java.util.Calendar.MONTH) + 1; 
        this.day = calendar.get(java.util.Calendar.DAY_OF_MONTH);
        this.hour = calendar.get(java.util.Calendar.HOUR_OF_DAY);
        this.minute = calendar.get(java.util.Calendar.MINUTE);
    }
    
    private void validateDate(int year, int month, int day) 
    {
        if (year < 1900 || year > 2100) 
        {
            throw new IllegalArgumentException("Year must be between 1900 and 2100");
        }
        
        if (month < 1 || month > 12) 
        {
            throw new IllegalArgumentException("Month must be between 1 and 12");
        }
        
        int maxDays = getDaysInMonth(month, year);
        if (day < 1 || day > maxDays) 
        {
            throw new IllegalArgumentException("Day must be between 1 and " + maxDays +
            "for month " + month);
        }
    }
    
    private void validateTime(int hour, int minute) 
    {
        if (hour < 0 || hour > 23) 
        {
            throw new IllegalArgumentException("Hour must be between 0 and 23");
        }
        
        if (minute < 0 || minute > 59) 
        {
            throw new IllegalArgumentException("Minute must be between 0 and 59");
        }
    }
    
    public static int getDaysInMonth(int month, int year) 
    {
        switch (month) 
        {
            case 4: case 6: case 9: case 11:
                return 30;
            case 2:
                return isLeapYear(year) ? 29 : 28;
            default:
                return 31;
        }
    }
    
    public static boolean isLeapYear(int year) 
    {
        if (year % 400 == 0) return true;
        if (year % 100 == 0) return false;
        return year % 4 == 0;
    }
    
    public Date addDays(int days) 
    {
        java.util.Calendar calendar = toJavaCalendar();
        calendar.add(java.util.Calendar.DATE, days);
        
        return fromJavaCalendar(calendar);
    }
    
    public Date addMonths(int months) 
    {
        java.util.Calendar calendar = toJavaCalendar();
        calendar.add(java.util.Calendar.MONTH, months);
        
        return fromJavaCalendar(calendar);
    }
    
    public Date addYears(int years) 
    {
        java.util.Calendar calendar = toJavaCalendar();
        calendar.add(java.util.Calendar.YEAR, years);
        
        return fromJavaCalendar(calendar);
    }
    
    public Date addHours(int hours) 
    {
        java.util.Calendar calendar = toJavaCalendar();
        calendar.add(java.util.Calendar.HOUR_OF_DAY, hours);
        
        return fromJavaCalendar(calendar);
    }
    
    public Date addMinutes(int minutes) 
    {
        java.util.Calendar calendar = toJavaCalendar();
        calendar.add(java.util.Calendar.MINUTE, minutes);
        
        return fromJavaCalendar(calendar);
    }
    
    public int compareTo(Date other) 
    {
        if (this.year != other.year) 
        {
            return this.year < other.year ? -1 : 1;
        }
        if (this.month != other.month) 
        {
            return this.month < other.month ? -1 : 1;
        }
        if (this.day != other.day) 
        {
            return this.day < other.day ? -1 : 1;
        }
        if (this.hour != other.hour) 
        {
            return this.hour < other.hour ? -1 : 1;
        }
        if (this.minute != other.minute) 
        {
            return this.minute < other.minute ? -1 : 1;
        }
        return 0;
    }
    
    public boolean isBefore(Date other) 
    {
        return compareTo(other) < 0;
    }
    
    public boolean isAfter(Date other) 
    {
        return compareTo(other) > 0;
    }
    
    public boolean equals(Date other) 
    {
        if (other == null) return false;
        return compareTo(other) == 0;
    }
    
    public long daysBetween(Date other) 
    {
        java.util.Calendar thisCal = toJavaCalendar();
        java.util.Calendar otherCal = other.toJavaCalendar();
        
        // Reset time components to compare just the dates
        thisCal.set(java.util.Calendar.HOUR_OF_DAY, 0);
        thisCal.set(java.util.Calendar.MINUTE, 0);
        thisCal.set(java.util.Calendar.SECOND, 0);
        thisCal.set(java.util.Calendar.MILLISECOND, 0);
        
        otherCal.set(java.util.Calendar.HOUR_OF_DAY, 0);
        otherCal.set(java.util.Calendar.MINUTE, 0);
        otherCal.set(java.util.Calendar.SECOND, 0);
        otherCal.set(java.util.Calendar.MILLISECOND, 0);
        
        long diffMillis = thisCal.getTimeInMillis() - otherCal.getTimeInMillis();
        return diffMillis / (24 * 60 * 60 * 1000);
    }
    
    private java.util.Calendar toJavaCalendar() 
    {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.set(year, month - 1, day, hour, minute, 0);
        calendar.set(java.util.Calendar.MILLISECOND, 0);
        return calendar;
    }
    
    private static Date fromJavaCalendar(java.util.Calendar calendar) 
    {
        return new Date(
            calendar.get(java.util.Calendar.YEAR),
            calendar.get(java.util.Calendar.MONTH) + 1,
            calendar.get(java.util.Calendar.DAY_OF_MONTH),
            calendar.get(java.util.Calendar.HOUR_OF_DAY),
            calendar.get(java.util.Calendar.MINUTE)
        );
    }
    
    @Override
    public String toString() 
    {
        return String.format("%04d-%02d-%02d %02d:%02d", year, month, day, hour, minute);
    }
    
    public String toDateString() 
    {
        return String.format("%04d-%02d-%02d", year, month, day);
    }
    
    public String toTimeString() 
    {
        return String.format("%02d:%02d", hour, minute);
    }
    
    public int getYear() 
    {
        return year;
    }
    
    public int getMonth() 
    {
        return month;
    }
    
    public int getDay() 
    {
        return day;
    }
    
    public int getHour() 
    {
        return hour;
    }
    
    public int getMinute() 
    {
        return minute;
    }
    
    public int getDayOfWeek() 
    {
        java.util.Calendar calendar = toJavaCalendar();
        int javaDay = calendar.get(java.util.Calendar.DAY_OF_WEEK);
        return javaDay;
    }
    
    public String getDayOfWeekName() 
    {
        String[] dayNames = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", 
        "Saturday"};
        return dayNames[getDayOfWeek() - 1];
    }
    
    public String getMonthName() 
    {
        String[] monthNames = {"January", "February", "March", "April", "May", "June", 
        "July", "August", "September", "October", "November", "December"};
        return monthNames[month - 1];
    }
}