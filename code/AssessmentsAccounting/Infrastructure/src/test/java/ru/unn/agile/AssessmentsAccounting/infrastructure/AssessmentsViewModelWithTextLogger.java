package ru.unn.agile.AssessmentsAccounting.infrastructure;

import ru.unn.agile.AssessmentsAccounting.viewmodel.AssessmentsAccountingViewModel;
import ru.unn.agile.AssessmentsAccounting.viewmodel.AssessmentsAccountingViewModelTests;

public class AssessmentsViewModelWithTextLogger extends AssessmentsAccountingViewModelTests {
    @Override
    public void setUp() {
        AssessmentsTextLogger testTextLogger = new AssessmentsTextLogger("./Assessments.log");

        super.setAnotherlViewModel(new AssessmentsAccountingViewModel(testTextLogger));
    }
}
