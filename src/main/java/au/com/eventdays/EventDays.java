package au.com.eventdays;

/**
* Given to dates find the number of full days.
* @author gerardra
*
*/

public class EventDays {
    private static final int INI_FROM_MONTH = 1;
    private static final int INI_FROM_YEAR = 1901;
    private static final int INI_TO_YEAR = 2999;
    public static void main(String[] args) {
		if (args.length != 2) {
			throw new IllegalArgumentException("Please provide from and to dates DD/MM/YYYY format. ");
		}
		String fromDateStr = args[0];
		String toDateStr = args[1];
		System.out.println("From Dates is " + fromDateStr + " and to date is " + toDateStr);
		final EventDays eventDays = new EventDays();
		int totalDaysBetweenEvent = eventDays.calculateNumOfFullDaysBetweenDates(fromDateStr, toDateStr);
		if (totalDaysBetweenEvent > 0) {
			System.out.println("Number of days between event " + totalDaysBetweenEvent);  
		} else {
			System.out.println("Number of days between event " + 0);
		}
    }

    public int calculateNumOfFullDaysBetweenDates(final String fromDateStr, final String toDateStr) {
    	int daysDiff = 0;
    	int monthsDiff = 0;
    	int yearsDiff = 0;
    	int daysBetweenYears = 0;
    	int daysBetweenMonths = 0;
    	String[] fromDate = fromDateStr.split("/");
    	String[] toDate = toDateStr.split("/");
    	if (fromDate == null || fromDate.length != 3) {
    		throw new IllegalArgumentException("Invalid From Date Format");
        }

        int fromDays = Integer.valueOf(fromDate[0]);
        int fromMonths = Integer.valueOf(fromDate[1]);
        int fromYears = Integer.valueOf(fromDate[2]);
        if (fromYears > INI_TO_YEAR || fromYears < INI_FROM_YEAR) {
             throw new IllegalArgumentException("From Dates should be between 01/01/1901 to 31/12/2999");
        }

        if (toDate == null || toDate.length == 0) {
             throw new IllegalArgumentException("Invalid To Date Format");
        }

        int toDays = Integer.valueOf(toDate[0]);
        int toMonths = Integer.valueOf(toDate[1]);
        int toYears = Integer.valueOf(toDate[2]);
        if (toYears < INI_FROM_MONTH || toYears > INI_TO_YEAR) {
             throw new IllegalArgumentException("To Dates should be between 01/01/1901 to 31/12/2999");
        }

        // Swap dates when from year is greater than to year.
        if (isFromDateGreaterThanTodate(fromDays, fromMonths, fromYears, toDays, toMonths, toYears)) {
        	fromDays = Integer.valueOf(toDate[0]);
            fromMonths = Integer.valueOf(toDate[1]);
            fromYears = Integer.valueOf(toDate[2]);
            toDays = Integer.valueOf(fromDate[0]);
            toMonths = Integer.valueOf(fromDate[1]);
            toYears = Integer.valueOf(fromDate[2]);
        }
        if (toDays < fromDays) {
        	 // if today less than from day then borrow days from to month
             toDays = getDaysFromMonth(toMonths, toYears) + toDays;
             toMonths--;
             daysDiff = toDays - fromDays - 2;
        } else if (toDays > fromDays){
             daysDiff = toDays - fromDays - 1;
        } else {
             daysDiff = toDays - fromDays;
        }
        if (toMonths < fromMonths) {
        	 // if toMonth less than fromMonth then borrow 12 Month(1 year) from year.
             toMonths = getMonthsFromYear(toMonths);
             toYears--;
        }
        monthsDiff = toMonths - fromMonths;
        yearsDiff = toYears - fromYears;
        daysBetweenYears = calculateDaysBetweenYears(fromYears, toYears);
        // if exactly one year then reduce a day
        if(fromDays == toDays && fromMonths == toMonths && fromYears != toYears) {
        	daysBetweenYears = daysBetweenYears - 1;
        }
        daysBetweenMonths = calculateFullDaysBetweenMonths(fromDays, toDays, fromMonths, toMonths, toYears);
        System.out.println(" Years " + yearsDiff + " Months " + monthsDiff + " Days " + daysDiff);
        return daysBetweenYears + daysBetweenMonths + daysDiff;
    }

    private int getMonthsFromYear(int toMonths) {
    	return toMonths + 12;
   	}

    private int getDaysFromMonth(int toMonths, int toYears) {
    	return numDaysInMonth(toMonths, toYears);
    }
    
    private boolean isFromDateGreaterThanTodate(int fromday, int fromMonth, int fromYear, int toDay, int toMonth, int toYear) {
    	if (fromYear > toYear) {
    		return true;
    	} else if (fromYear == toYear && fromMonth > toMonth) {
    		return true;
    	} else if (fromYear == toYear && fromMonth == toMonth && fromday > toDay) {
    		return true;
    	}
    	return false;
    }
    
    /**
     * Calculate number of days between the given years.
     * @param fromYear
     * @param toyear
     * @return
     */
    private int calculateDaysBetweenYears(int fromYear, int toYear) {
    	int totalDayBetweenYears = 0;
        for (int i = fromYear ; i < toYear; i++) {
        	int daysInYear = 365;
        	if(isLeapYear(i)) {
        		daysInYear = 366;
            }
        	totalDayBetweenYears  = totalDayBetweenYears + daysInYear;
        }
        return totalDayBetweenYears;
    }
 

    /**
     * Calculate number of days between the given months.
     * @param fromMonth
     * @param toMonth
     * @param fromYear
     * @return
     */
    private int calculateFullDaysBetweenMonths(int fromDays, int toDays, int fromMonth, int toMonth, int fromYear) {
    	if (Math.abs(toMonth - fromMonth) == 1) {
    		return getPartialDaysBetweenMonths(fromDays, toDays, fromMonth, toMonth, fromYear);
    	}
        int totalDayBetweenMonths = 0;
        for (int i = fromMonth ; i < toMonth ; i++) {
        	totalDayBetweenMonths  = totalDayBetweenMonths + numDaysInMonth(i, fromYear);
        }
        return totalDayBetweenMonths;
    }

    private int getPartialDaysBetweenMonths(int fromDays, int toDays, int fromMonth, int toMonth, int fromYear) {
    	int totalDays = 0;
    	int numDaysInMonth = numDaysInMonth(fromMonth, fromYear);
    	for (int i = fromDays; i < numDaysInMonth ; i++) {
    		totalDays = totalDays + 1;
    	}
    	for (int i = 1; i < toDays ; i++) {
    		totalDays = totalDays + 1;
    	}
    	return totalDays;
    }

    /**
     * Check a year is a leap year or not.
     * @param year
     * @return
     */
    private boolean isLeapYear(int year) {
    	if (year % 4 != 0) {
    		return false;
        } else if (year % 400 == 0) {
             return true;
        } else if (year % 100 == 0) {
             return false;
        } else {
             return true;
        }
    }

    /**
     * Number of days for each month.
     * @param month
     * @param year
     * @return
     */
    private int numDaysInMonth(int month, int year) {
    	if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12
    			|| month == 13 || month == 15 || month == 17 || month == 19 || month == 20 || month == 12 || month == 24) {
               return 31;
        } else if (month == 2 || month == 14) {
        	if (isLeapYear(year)) {
        		return 29;
        	} else {
        		return 28;
        	}
        }
    	return 30;
    }

}