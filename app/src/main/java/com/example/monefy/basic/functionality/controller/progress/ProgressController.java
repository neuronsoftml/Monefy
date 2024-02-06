package com.example.monefy.basic.functionality.controller.progress;

import static com.example.monefy.basic.functionality.controller.date.DateController.dateFormatLocalApp;

import java.text.ParseException;
import java.util.Date;

public class ProgressController {
    public static int calculatePercentage(String currentDateStr, String dueDateStr) throws ParseException {
        Date currentDate = dateFormatLocalApp.parse(currentDateStr);
        Date dueDate = dateFormatLocalApp.parse(dueDateStr);

        long currentTime = currentDate.getTime();
        long dueTime = dueDate.getTime();
        long totalTime = dueTime - currentTime;

        long elapsedTime = System.currentTimeMillis() - currentTime;

        double percentage = (double) elapsedTime / totalTime * 100;
        return (int) Math.min(100, percentage); // Обмеження до 100%
    }

    public static int calculateAccumulativeAmount(int valueOne, int valueTwo){
        double percentage = (double) valueOne / valueTwo * 100;
        return (int) Math.min(100, percentage); // Обмеження до 100%
    }
}
