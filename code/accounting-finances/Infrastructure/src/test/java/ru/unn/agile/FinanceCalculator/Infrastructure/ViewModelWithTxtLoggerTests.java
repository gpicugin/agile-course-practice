package ru.unn.agile.FinanceCalculator.Infrastructure;

import ru.unn.agile.FinanceCalculator.viewmodel.ViewModel;
import ru.unn.agile.FinanceCalculator.viewmodel.ViewModelTests;

public class ViewModelWithTxtLoggerTests extends ViewModelTests {
    @Override
    public void setUp() {
        TxtLogger realLogger = new TxtLogger("./ViewModel_with_TxtLogger_Tests-lab3.log");
        super.setViewModel(new ViewModel(realLogger));
    }
}

