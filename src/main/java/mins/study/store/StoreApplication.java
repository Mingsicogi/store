package mins.study.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Calendar;
import java.util.Scanner;

@SpringBootApplication
public class StoreApplication {

    //    public static void main(String[] args) {
    //        SpringApplication.run(StoreApplication.class, args);
    //    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("년도를 입력하세요 : ");
        int year = sc.nextInt(); //년도
        System.out.println("월을 입력하세요 : ");
        int month = sc.nextInt(); //월

        String weekStr = "\t일\t월\t화\t수\t목\t금\t토";

        String printStr = "-----------[" + year + "년 " + (month - 1) + "월]-----------\t";
        printStr += "-----------[" + year + "년 " + month + "월]-----------\t";
        printStr += "-----------[" + year + "년 " + (month + 1) + "월]-----------\n";
        printStr = printStr + weekStr + "\t\t";
        printStr = printStr + weekStr + "\t\t";
        printStr = printStr + weekStr + "\t\n";

        printStr += makeCalendarStrBeforeCurrentNext(year, month);

        System.out.println(printStr);
    }

    private static String makeCalendarStrBeforeCurrentNext(int year, int month) {
        // 1주차 부터 순서대로 3개의 달의 날짜를 String 에 더함
        String str = "\t";

        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 2, 1);   //이전달
        int endDayOfBeforeMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH); //해당 월 마지막 날짜
        int dayOfBeforeMonth = cal.get(Calendar.DAY_OF_WEEK); //해당 날짜의 요일(1:일요일 … 7:토요일)
        int indexOfBeforeMonth = 1;


        cal.set(year, month - 1, 1);   // 현재
        int endDayOfCurrentMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH); //해당 월 마지막 날짜
        int dayOfCurrentMonth = cal.get(Calendar.DAY_OF_WEEK); //해당 날짜의 요일(1:일요일 … 7:토요일)
        int indexOfCurrentMonth = 1;

        cal.set(year, month, 1);   // 다음달
        int endDayOfNextMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH); //해당 월 마지막 날짜
        int dayOfNextMonth = cal.get(Calendar.DAY_OF_WEEK); //해당 날짜의 요일(1:일요일 … 7:토요일)
        int indexOfNextMonth = 1;

        StringBuilder sb = new StringBuilder();
        while (indexOfBeforeMonth <= endDayOfBeforeMonth || indexOfCurrentMonth <= endDayOfCurrentMonth || indexOfNextMonth <= endDayOfNextMonth) {
            // before
            indexOfBeforeMonth = makeByIndex(endDayOfBeforeMonth, dayOfBeforeMonth, indexOfBeforeMonth, sb);

            // current
            indexOfCurrentMonth = makeByIndex(endDayOfCurrentMonth, dayOfCurrentMonth, indexOfCurrentMonth, sb);

            // next
            indexOfNextMonth = makeByIndex(endDayOfNextMonth, dayOfNextMonth, indexOfNextMonth, sb);

            // init
            sb.append("\n");
            dayOfBeforeMonth = 1;
            dayOfCurrentMonth = 1;
            dayOfNextMonth = 1;
        }

        return sb.toString();
    }

    private static int makeByIndex(int endDayOfMonth, int dayOfMonth, int index, StringBuilder sb) {
        if (index == 1) {
            sb.append(makeTab(dayOfMonth - 1));
        }
        while (dayOfMonth++ % 8 != 0) {
            if (index > endDayOfMonth) {
                sb.append(makeTab(9 - dayOfMonth));
                break;
            }

            sb.append(makeTab(1));
            sb.append(index++);
        }
        return index;
    }

    private static String makeTab(int tabSize) {
        String temp = "";
        for (int i = 0; i < tabSize; i++) {
            temp += "\t";
        }

        return temp;
    }
}
