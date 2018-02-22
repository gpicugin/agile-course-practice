package ru.unn.agile.FinanceCalculator.viewmodel;
import java.util.List;

public interface ILogger {
    void log(String s);
    List<String> get();
}
