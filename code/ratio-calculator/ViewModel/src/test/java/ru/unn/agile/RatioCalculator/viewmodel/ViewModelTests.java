package ru.unn.agile.RatioCalculator.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.RatioCalculator.Model.Ratio.Operation;
import static junit.framework.TestCase.assertEquals;

public class ViewModelTests {
    private ViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new ViewModel();
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void canSetDefaultValues() {
        assertEquals("", viewModel.denominatorFirstProperty().get());
        assertEquals("", viewModel.numeratorFirstProperty().get());
        assertEquals("", viewModel.denominatorSecondProperty().get());
        assertEquals("", viewModel.numeratorSecondProperty().get());
        assertEquals(Operation.ADD, viewModel.operationProperty().get());
        assertEquals(Status.WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsReadyWhenFieldsAreFill() {
        setInputData("1", "2", "3", "4");
        assertEquals(Status.READY.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void canNotDivOnZero() {
        viewModel.operationProperty().set(Operation.DIV);
        setInputData("1", "2", "3", "0");
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void canReportBadFormat() {
        viewModel.denominatorFirstProperty().set("a");

        assertEquals(Status.BAD_FORMAT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsWaitingIfNotEnoughCorrectData() {
        viewModel.denominatorFirstProperty().set("1");

        assertEquals(Status.WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void canSetAddOperation() {
        viewModel.operationProperty().set(Operation.ADD);
        assertEquals(Operation.ADD, viewModel.operationProperty().get());
    }

    @Test
    public void addIsDefaultOperation() {
        assertEquals(Operation.ADD, viewModel.operationProperty().get());
    }

    @Test
    public void statusIsWaitingWhenCalculateWithEmptyFields() {
        viewModel.calculate();
        assertEquals(Status.WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsBadFormatIfDenominatorIsZero() {
        setInputData("0", "1", "9", "2");
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsReadyIfDenominatorChangedFromZeroToCorrectValue() {
        setInputData("0", "2", "3", "4");
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.statusProperty().get());
        viewModel.denominatorFirstProperty().set("999");
        assertEquals(Status.READY.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void operationAddHasCorrectResult() {
        setInputData("9", "1", "9", "2");
        viewModel.calculate();
        assertEquals("1", viewModel.resultNumeratorProperty().get());
        assertEquals("3", viewModel.resultDenominatorProperty().get());
    }

    @Test
    public void operationMulHasCorrectResult() {
        setInputData("2", "1", "2", "1");
        viewModel.operationProperty().set(Operation.MULTIPLY);
        viewModel.calculate();
        assertEquals("1", viewModel.resultNumeratorProperty().get());
        assertEquals("4", viewModel.resultDenominatorProperty().get());
    }

    @Test
    public void operationSubHasCorrectResult() {
        setInputData("6", "1", "12", "1");
        viewModel.operationProperty().set(Operation.SUB);
        viewModel.calculate();
        assertEquals("1", viewModel.resultNumeratorProperty().get());
        assertEquals("12", viewModel.resultDenominatorProperty().get());
    }

    @Test
    public void operationDivHasCorrectResult() {
        setInputData("6", "1", "6", "1");
        viewModel.operationProperty().set(Operation.DIV);
        viewModel.calculate();
        assertEquals("1", viewModel.resultNumeratorProperty().get());
        assertEquals("1", viewModel.resultDenominatorProperty().get());
    }

    private void setInputData(final String denominatorFirst, final String numeratorFirst,
                              final String denominatorSecond, final String numeratorSecond) {
        viewModel.denominatorFirstProperty().set(denominatorFirst);
        viewModel.numeratorFirstProperty().set(numeratorFirst);
        viewModel.denominatorSecondProperty().set(denominatorSecond);
        viewModel.numeratorSecondProperty().set(numeratorSecond);
    }

}
