/**
 *
 */
package ar.com.angelDurmiente.helpers;

import java.text.SimpleDateFormat;
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
		calendar.set(Calendar.MONTH, monthOfyear - 1);
		calendar.set(Calendar.YEAR, year);
		return calendar.getTime();
	}
	
	public static String formatDate(String dateFormat, Date date){
		return new SimpleDateFormat(dateFormat).format(date);
	}

	private CalendarHelper(){
		super();
	}
}
