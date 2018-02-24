package ru.unn.agile.FinanceCalculator.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class FakeLogger implements ILogger {

    @Override
    public void log(final String currentLog) {
        logInput.add(currentLog);
    }
    private final ArrayList<String> logInput = new ArrayList<>();

    @Override
    public List<String> get() {
        return logInput;
    }
}
