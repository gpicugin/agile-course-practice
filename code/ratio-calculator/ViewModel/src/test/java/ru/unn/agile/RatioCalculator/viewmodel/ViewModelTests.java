package ru.unn.agile.RatioCalculator.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.RatioCalculator.Model.Ratio.Operation;


import static org.junit.Assert.*;

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
    public void correctStatusWhenFieldsAreFill() {
        setInputData();
        assertEquals(Status.READY.toString(), viewModel.statusProperty().get());
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
    public void operationAddHasCorrectResult() {
        viewModel.denominatorFirstProperty().set("9");
        viewModel.numeratorFirstProperty().set("1");
        viewModel.denominatorSecondProperty().set("9");
        viewModel.numeratorSecondProperty().set("2");

        viewModel.calculate();

        assertEquals("1", viewModel.resultNumeratorProperty().get());
        assertEquals("3", viewModel.resultDenominatorProperty().get());
    }
    @Test
    public void operationMulHasCorrectResult() {
        viewModel.denominatorFirstProperty().set("2");
        viewModel.numeratorFirstProperty().set("1");
        viewModel.denominatorSecondProperty().set("2");
        viewModel.numeratorSecondProperty().set("1");

        viewModel.operationProperty().set(Operation.MULTIPLY);

        viewModel.calculate();

        assertEquals("1", viewModel.resultNumeratorProperty().get());
        assertEquals("4", viewModel.resultDenominatorProperty().get());
    }


    private void setInputData() {
        viewModel.denominatorFirstProperty().set("1");
        viewModel.numeratorFirstProperty().set("2");
        viewModel.denominatorSecondProperty().set("3");
        viewModel.numeratorSecondProperty().set("4");
    }


}
