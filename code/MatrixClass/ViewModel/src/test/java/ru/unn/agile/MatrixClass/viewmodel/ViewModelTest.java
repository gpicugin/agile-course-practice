package ru.unn.agile.MatrixClass.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ViewModelTest {
    @Before
    public void setUp() {
        viewModel = new ViewModel();
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void canSetDefaultInputMatrixSize() {
        assertEquals("", viewModel.getInputMatrixSize());
    }

    @Test
    public void canSetDefaultDeterminant() {
        assertEquals("", viewModel.getDeterminant());
    }

    @Test
    public void canSetDefaultFieldForMatrix() {
        assertEquals("", viewModel.getFieldForMatrix());
    }

    @Test
    public void canSetDefaultStatus() {
        assertEquals(Status.WAITING.toString(), viewModel.getStatus());
    }

    @Test
    public void statusIsWaitingWhenEmptyMatrixSize() {
        viewModel.setMatrixSize("");
        assertEquals(Status.WAITING.toString(), viewModel.getStatus());
    }

    @Test
    public void statusIsBadFormatWhenInvalidMatrixSize() {
        viewModel.setMatrixSize("a");
        viewModel.setFieldForMatrix("3,9");
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.getStatus());
    }

    @Test
    public void statusIsBadFormatWhenMatrixSizeIsOne() {
        viewModel.setMatrixSize("1");
        viewModel.setFieldForMatrix("3");
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.getStatus());
    }

    @Test
    public void statusIsBadFormatWhenSizeForMatrixWithSeparator() {
        viewModel.setMatrixSize("65,7");
        viewModel.setFieldForMatrix("3");
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.getStatus());
    }

    @Test
    public void calculateButtonIsDisabledWhenInputIsEmpty() {
        viewModel.setMatrixSize("");
        viewModel.setFieldForMatrix("");
        assertTrue(viewModel.isCalculateButtonDisabled());
    }

    @Test
    public void calculateButtonIsDisabledWhenFieldForMatrixIsEmpty() {
        viewModel.setMatrixSize("2");
        viewModel.setFieldForMatrix("");
        assertTrue(viewModel.isCalculateButtonDisabled());
    }

    @Test
    public void statusIsBadFormatWhenNotSquareMatrix() {
        viewModel.setMatrixSize("2");
        viewModel.setFieldForMatrix("");
        assertTrue(viewModel.isCalculateButtonDisabled());
    }

    @Test
    public void calculateButtonIsDisabledWhenMatrixSizeIsNotInteger() {
        viewModel.setMatrixSize("2.2");
        assertTrue(viewModel.isCalculateButtonDisabled());
    }

    @Test
    public void calculateButtonIsDisabledWhenNegativeMatrixSize() {
        viewModel.setMatrixSize("-89");
        assertTrue(viewModel.isCalculateButtonDisabled());
    }

    @Test
    public void calculateButtonIsDisabledWhenMatrixSizeIsEmpty() {
        viewModel.setFieldForMatrix("3");
        viewModel.setMatrixSize("");
        assertTrue(viewModel.isCalculateButtonDisabled());
    }

    @Test
    public void calculateButtonIsEnableWhenValidArgumentsInFields() {
        viewModel.setMatrixSize("2");
        viewModel.setFieldForMatrix("3,2/2,1");
        assertFalse(viewModel.isCalculateButtonDisabled());
    }

    @Test
    public void statusIsWaitingMatrixWhenEmptyFieldForMatrix() {
        viewModel.setMatrixSize("2");
        viewModel.setFieldForMatrix("");
        assertEquals(Status.WAITING_MATRIX.toString(), viewModel.getStatus());
    }

    @Test
    public void statusIsBadFormatWhenMatrixOfOneRow() {
        viewModel.setMatrixSize("2");
        viewModel.setFieldForMatrix("3,2,2,1");
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.getStatus());
    }

    @Test
    public void statusIsBadFormatWhenFieldForMatrixContainsEmptyCharactersWithNumber() {
        viewModel.setMatrixSize("2");
        viewModel.setFieldForMatrix("3,2/ 2,1");
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.getStatus());
    }

    @Test
    public void statusIsBadFormatWhenFieldForMatrixContainsEmptyCharactersInsteadOfTheNumber() {
        viewModel.setMatrixSize("2");
        viewModel.setFieldForMatrix("3,2/2, ");
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.getStatus());
    }

    @Test
    public void statusIsBadFormatWhenTypeLetterInFieldForMatrix() {
        viewModel.setMatrixSize("2");
        viewModel.setFieldForMatrix("3,2/2,d");
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.getStatus());
    }

    @Test
    public void statusIsBadFormatWhenTypeLetterWithNumberInFieldForMatrix() {
        viewModel.setMatrixSize("2");
        viewModel.setFieldForMatrix("3,2/2,4d");
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.getStatus());
    }

    @Test
    public void statusIsBadFormatWhenIncorrectNumbersInFieldForMatrix() {
        viewModel.setMatrixSize("2");
        viewModel.setFieldForMatrix("3,2.1/2,1.1.3");
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.getStatus());
    }

    @Test
    public void statusIsReadyWhenValidArgumentsInFieldForMatrix() {
        viewModel.setMatrixSize("2");
        viewModel.setFieldForMatrix("3,2.1/2,1");
        assertEquals(Status.READY.toString(), viewModel.getStatus());
    }

    @Test
    public void statusIsReadyWhenValidNegativeArgumentsInFieldForMatrix() {
        viewModel.setMatrixSize("2");
        viewModel.setFieldForMatrix("-3,2.1/2,-1");
        assertEquals(Status.READY.toString(), viewModel.getStatus());
    }

    @Test
    public void canCalculateCorrectInputMatrix2x2() {
        viewModel.setMatrixSize("2");
        viewModel.setFieldForMatrix("3,2/2,1");
        viewModel.calculate();
        assertEquals("-1.0", viewModel.getDeterminant());
    }

    @Test
    public void canCalculateCorrectInputMatrix3x3() {
        viewModel.setMatrixSize("3");
        viewModel.setFieldForMatrix("3,2,3/2,1,8/5,6,9");
        viewModel.calculate();
        assertEquals("-52.0",
                viewModel.getDeterminant());
    }

    @Test
    public void statusIsWaitingAfterClearMatrixSize() {
        viewModel.setMatrixSize("894");
        viewModel.setMatrixSize("");
        assertEquals(Status.WAITING.toString(), viewModel.getStatus());
    }

    @Test
    public void calculateButtonIsDisabledAfterClearMatrixSize() {
        viewModel.setMatrixSize("894");
        viewModel.setMatrixSize("");
        assertTrue(viewModel.isCalculateButtonDisabled());
    }

    @Test
    public void statusIsBadFormatWhenInputIncorrectMatrixSizeAfterCalculate() {
        viewModel.setMatrixSize("2");
        viewModel.setFieldForMatrix("3,2/2,1");
        viewModel.calculate();
        viewModel.setMatrixSize("2a");
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.getStatus());
    }

    @Test
    public void calculateButtonIsDisabledWhenInputIncorrectMatrixSizeAfterCalculate() {
        viewModel.setMatrixSize("2");
        viewModel.setFieldForMatrix("3,2/2,1");
        viewModel.calculate();
        viewModel.setMatrixSize("2a");
        assertTrue(viewModel.isCalculateButtonDisabled());
    }

    @Test
    public void statusIsReadyWhenInputNewMatrixAfterCalculate() {
        viewModel.setMatrixSize("2");
        viewModel.setFieldForMatrix("3,2/2,1");
        viewModel.calculate();
        viewModel.setMatrixSize("3");
        viewModel.setFieldForMatrix("3,2,1/2,0,5/4,6,3");
        assertEquals(Status.READY.toString(), viewModel.getStatus());
    }

    @Test
    public void resultIsClearWhenInputNewMatrixAfterCalculate() {
        viewModel.setMatrixSize("2");
        viewModel.setFieldForMatrix("3,2/2,1");
        viewModel.calculate();
        viewModel.setMatrixSize("3");
        assertEquals("", viewModel.getDeterminant());
    }

    private ViewModel viewModel;
}
