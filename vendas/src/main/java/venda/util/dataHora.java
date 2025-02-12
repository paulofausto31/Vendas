package venda.util;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class dataHora {

    public static boolean isDataValida(String data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate.parse(data, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

	public static String dataDia()
	{
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int date = calendar.get(Calendar.DATE);
        String strData = texto.strZero(String.valueOf(date),2) + "/" + texto.strZero(String.valueOf(month),2) + "/" + String.valueOf(year);
        return strData;
	}
	
    public static int getDiaSemana(Date data) {

        Calendar calendario = Calendar.getInstance();
        calendario.setTime(data);
        int diaSemana = calendario.get(Calendar.DAY_OF_WEEK);
        int ret = 0;
        switch (diaSemana) {
            case Calendar.SUNDAY:
                 ret = 1;
                 break;
            case Calendar.MONDAY:
                 ret = 2;
                 break;
            case Calendar.TUESDAY:
                 ret = 3;
                 break;
            case Calendar.WEDNESDAY:
                 ret = 4;
                 break;
            case Calendar.THURSDAY:
                 ret = 5;
                 break;
            case Calendar.FRIDAY:
                 ret = 6;
                 break;
            case Calendar.SATURDAY:
                 ret = 7;
                 break;
        }
        return ret;
    }
    
}
