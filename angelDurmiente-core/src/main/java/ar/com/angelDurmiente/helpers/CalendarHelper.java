/**
 *
 */
package ar.com.angelDurmiente.helpers;

import java.util.Calendar;
import java.util.Date;


/**
 * @author William
 *
 */
public class CalendarHelper {

	public static Date createDate(int dayOfMonth, int monthOfyear, int year){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		calendar.set(Calendar.MONTH, monthOfyear);
		calendar.set(Calendar.YEAR, year);
		return calendar.getTime();
	}

	private CalendarHelper(){
		super();
	}
}
