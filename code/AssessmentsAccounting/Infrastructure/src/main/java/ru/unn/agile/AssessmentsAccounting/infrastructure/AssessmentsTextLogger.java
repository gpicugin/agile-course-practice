package ru.unn.agile.AssessmentsAccounting.infrastructure;

import ru.unn.agile.AssessmentsAccounting.viewmodel.ILogger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AssessmentsTextLogger implements ILogger {

    public AssessmentsTextLogger(final String filename) {
        this.filename = filename;

        BufferedWriter loggingBufferedWriter = null;
        try {
            loggingBufferedWriter = new BufferedWriter(new FileWriter(filename));
        } catch (Exception e) {
            e.printStackTrace();
        }
        writer = loggingBufferedWriter;
    }

    @Override
    public void log(final String stringLog) {
        try {
            writer.write(now() + "\t::\t" + stringLog);
            writer.newLine();
            writer.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<String> getLog() {
        BufferedReader bufferedReadereader;
        ArrayList<String> logList = new ArrayList<String>();
        try {
            bufferedReadereader = new BufferedReader(new FileReader(filename));
            String line = bufferedReadereader.readLine();

            while (line != null) {
                logList.add(line);
                line = bufferedReadereader.readLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return logList;
    }

    private static String now() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT_NOW, Locale.ENGLISH);
        return simpleDateFormat.format(cal.getTime());
    }

    private static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
    private final BufferedWriter writer;
    private final String filename;
}
