package ru.unn.agile.AssessmentsAccounting.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class FakeLogger implements ILogger {
    @Override
    public void log(final String logString) {
        log.add(logString);
    }

    @Override
    public List<String> getLog() {
        return log;
    }

    private ArrayList<String> log = new ArrayList<String>();
}
