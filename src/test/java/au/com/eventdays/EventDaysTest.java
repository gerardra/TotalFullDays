package au.com.eventdays;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class EventDaysTest {

	private EventDays eventDays = null;
	
	@Before
	public void setUp() throws Exception {
		eventDays = new EventDays();
	}

	@After
	public void tearDown() {
		eventDays = null;
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void CalculateNumOfFullDaysBetweenDates_InvalidDates() {
		eventDays.calculateNumOfFullDaysBetweenDates("12-12-2010", "12-12-2010");
	}

	@Test(expected = IllegalArgumentException.class)
	public void CalculateNumOfFullDaysBetweenDates_OutofRangedDates() {
		eventDays.calculateNumOfFullDaysBetweenDates("12/12/1000", "12/12/2010");
	}
	

	@Test
	public void CalculateNumOfFullDaysBetweenDates_SameYearAndDayDifferentMonth() {
		int betweenDates = eventDays.calculateNumOfFullDaysBetweenDates("12/11/2000", "12/12/2000");
		Assert.assertEquals(29, betweenDates);
	}
	

	@Test
	public void CalculateNumOfFullDaysBetweenDates_DifferentYearSameMonth() {
		int betweenDates = eventDays.calculateNumOfFullDaysBetweenDates("12/12/2000", "01/12/2010");
		Assert.assertEquals(3640, betweenDates);
	}

	@Test
     public void CalculateNumOfFullDaysBetweenDates_FromDateIsGreaterThanToDate() {
		int betweenDates = eventDays.calculateNumOfFullDaysBetweenDates("01/12/2002", "12/12/2000");
		Assert.assertEquals(718, betweenDates);
     }

     @Test
     public void CalculateNumOfFullDaysBetweenDates_SameMonthAndYearDayDifferent() {
    	 int betweenDates = eventDays.calculateNumOfFullDaysBetweenDates("02/06/1983", "22/06/1983");
    	 Assert.assertEquals(19, betweenDates);
     }

     @Test
     public void CalculateNumOfFullDaysBetweenDates_SameYearDifferentMonthAndDay() {
    	 int betweenDates = eventDays.calculateNumOfFullDaysBetweenDates("04/07/1984", "25/12/1984");
    	 Assert.assertEquals(173, betweenDates);
     }

     @Test
     public void CalculateNumOfFullDaysBetweenDates_SameDayDifferentMonthAnYear() {
    	 int betweenDates = eventDays.calculateNumOfFullDaysBetweenDates("03/01/1989", "03/08/1983");
    	 Assert.assertEquals(1979, betweenDates);
     }
     

     @Test
     public void CalculateNumOfFullDaysBetweenDates_SameDaDate() {
    	 int betweenDates = eventDays.calculateNumOfFullDaysBetweenDates("03/01/1989", "03/01/1989");
    	 Assert.assertEquals(0, betweenDates);
     }

     @Test
     public void CalculateNumOfFullDaysBetweenDates_SameMonthAndYear_DifferentYear() {
    	 int betweenDates = eventDays.calculateNumOfFullDaysBetweenDates("01/01/1901", "01/01/2999");
    	 Assert.assertEquals(401036, betweenDates);
     }
     
     @Test
     public void CalculateNumOfFullDaysBetweenDates_GreaterFromDay_SameDayYearAndMonth() {
    	 int betweenDates = eventDays.calculateNumOfFullDaysBetweenDates("31/11/2000", "01/11/2000");
    	 Assert.assertEquals(29, betweenDates);
     }
}
