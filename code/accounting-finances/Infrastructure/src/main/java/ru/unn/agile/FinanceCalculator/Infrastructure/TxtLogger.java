package ru.unn.agile.FinanceCalculator.Infrastructure;

import ru.unn.agile.FinanceCalculator.viewmodel.ILogger;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TxtLogger implements ILogger {

    @Override
    public List<String> get() {
        BufferedReader bufferedReader;
        ArrayList<String> strings = new ArrayList<>();
        try {
            bufferedReader = new BufferedReader(new FileReader(foldername));
            String readLine = bufferedReader.readLine();

            while (readLine != null) {
                strings.add(readLine);
                readLine = bufferedReader.readLine();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return strings;
    }
    public TxtLogger(final String s) {

        BufferedWriter bufferedWriter = null;
        this.foldername = s;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(s));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        this.bufferedWriter = bufferedWriter;
    }
    @Override
    public void log(final String input) {
        try {
            bufferedWriter.write(now() + " > " + input);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }


    private static String now() {
        Calendar date = Calendar.getInstance();
        SimpleDateFormat curreent = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS, Locale.ENGLISH);
        return curreent.format(date.getTime());
    }

    private static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    private final BufferedWriter bufferedWriter;
    private final String foldername;
}
