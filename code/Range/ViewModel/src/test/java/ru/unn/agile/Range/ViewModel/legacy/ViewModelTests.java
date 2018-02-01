package ru.unn.agile.Range.ViewModel.legacy;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class ViewModelTests {
    @Before
    public void setUp() {
        viewModel = new ViewModel();
    }

    @After
    public void clean() {
        viewModel = null;
    }

    @Test
    public void canCreateViewModel() {
        assertNotNull(viewModel);
    }

    @Test
    public void isCalculateButtonDisabledWhenInputIsEmpty() {
        assertFalse(viewModel.isCalculateButtonEnabled());
    }

    @Test
    public void isInputArgumentTextFieldDisabledWhenInputRangeIsEmpty() {
        assertFalse(viewModel.isInputArgumentTextFieldEnabled());
    }

    @Test
    public void isInputArgumentTextFieldDisabledWhenOperationIsGETALLPOINTS() {
        viewModel.setOperation(ViewModel.Operation.GET_ALL_POINTS);

        assertFalse(viewModel.isInputArgumentTextFieldEnabled());
    }

    @Test
    public void isInputArgumentTextFieldDisabledWhenOperationIsGETENDPOINTS() {
        viewModel.setOperation(ViewModel.Operation.GET_END_POINTS);

        assertFalse(viewModel.isInputArgumentTextFieldEnabled());
    }

    @Test
    public void isInputArgumentTextFieldEnabledWhenOperationIsCONTAINSPOINTS() {
        viewModel.setInputRange("(1,10]");
        viewModel.setOperation(ViewModel.Operation.CONTAINS_POINTS);

        assertTrue(viewModel.isInputArgumentTextFieldEnabled());
    }

    @Test
    public void showMessageWhenInputRangeIsEmpty() {
        assertEquals(ViewModel.Message.DEFAULT_RANGE, viewModel.getMessageText());
    }

    @Test
    public void isCalculateButtonEnabledWhenEnterCorrectRange() {
        viewModel.setInputRange("(1,10]");

        assertTrue(viewModel.isCalculateButtonEnabled());
    }

    @Test
    public void isCalculateButtonEnabledWhenEnterCorrectArgumentRange() {
        viewModel.setInputRange("(1,10]");
        viewModel.setOperation(ViewModel.Operation.CONTAINS_RANGE);
        viewModel.setInputArgument("(1,10]");

        assertTrue(viewModel.isCalculateButtonEnabled());
    }

    @Test
    public void isCalculateButtonEnabledWhenOperationIsCONTAINSPOINTSAndEnterCorrectSetOfNumbers() {
        viewModel.setInputRange("(1,10]");
        viewModel.setOperation(ViewModel.Operation.CONTAINS_POINTS);
        viewModel.setInputArgument("{1,10}");

        assertTrue(viewModel.isCalculateButtonEnabled());
    }

    @Test
    public void isCalculateButtonDisabledWhenOperationIsntCONTAINSPOINTSAndSetOfNumbersIsCorrect() {
        viewModel.setInputRange("(1,10]");
        viewModel.setOperation(ViewModel.Operation.CONTAINS_RANGE);
        viewModel.setInputArgument("{1,10}");

        assertFalse(viewModel.isCalculateButtonEnabled());
    }

    @Test
    public void isCalculateButtonDisabledWhenClearInputRange() {
        viewModel.setInputRange("(1,10]");
        viewModel.setInputRange("");

        assertFalse(viewModel.isCalculateButtonEnabled());
    }

    @Test
    public void isCalculateButtonDisabledWhenClearInputArgument() {
        viewModel.setInputRange("(1,10]");
        viewModel.setOperation(ViewModel.Operation.CONTAINS_RANGE);
        viewModel.setInputArgument("(1,10]");
        viewModel.setInputArgument("");

        assertFalse(viewModel.isCalculateButtonEnabled());
    }

    @Test
    public void isResultCorrectWhenCalculateGETALLPOINTS() {
        viewModel.setInputRange("(1,3]");
        viewModel.setOperation(ViewModel.Operation.GET_ALL_POINTS);
        viewModel.calculate();

        assertEquals("2, 3", viewModel.getResult());
    }

    @Test
    public void isResultCorrectWhenCalculateGETENDPOINTS() {
        viewModel.setInputRange("(1,10]");
        viewModel.setOperation(ViewModel.Operation.GET_END_POINTS);
        viewModel.calculate();

        assertEquals("2, 10", viewModel.getResult());
    }

    @Test
    public void isResultCorrectWhenCalculateAREEQUALRANGESAndRangesAreEqual() {
        viewModel.setInputRange("(1,10]");
        viewModel.setOperation(ViewModel.Operation.ARE_EQUAL_RANGES);
        viewModel.setInputArgument("(1,10]");
        viewModel.calculate();

        assertEquals("These ranges are equal", viewModel.getResult());
    }

    @Test
    public void isResultCorrectWhenCalculateAREEQUALRANGESAndRangesAreNotEqual() {
        viewModel.setInputRange("(1,10]");
        viewModel.setOperation(ViewModel.Operation.ARE_EQUAL_RANGES);
        viewModel.setInputArgument("(1,11]");
        viewModel.calculate();

        assertEquals("These ranges are not equal", viewModel.getResult());
    }

    @Test
    public void isResultCorrectWhenCalculateCONTAINSPOINTSAndRangeContainsSetOfNumbers() {
        viewModel.setInputRange("(1,10]");
        viewModel.setOperation(ViewModel.Operation.CONTAINS_POINTS);
        viewModel.setInputArgument("{2,4}");
        viewModel.calculate();

        assertEquals("The range contains these values", viewModel.getResult());
    }

    @Test
    public void isResultCorrectWhenCalculateCONTAINSPOINTSAndRangeDoesNotContainSetOfNumbers() {
        viewModel.setInputRange("(1,10]");
        viewModel.setOperation(ViewModel.Operation.CONTAINS_POINTS);
        viewModel.setInputArgument("{2,15}");
        viewModel.calculate();

        assertEquals("The range doesn't contain these values", viewModel.getResult());
    }

    @Test
    public void isResultCorrectWhenCalculateCONTAINSRANGEAndRangeContainsArgumentRange() {
        viewModel.setInputRange("(1,10]");
        viewModel.setOperation(ViewModel.Operation.CONTAINS_RANGE);
        viewModel.setInputArgument("[2,5]");
        viewModel.calculate();

        assertEquals("The range contains this range", viewModel.getResult());
    }

    @Test
    public void isResultCorrectWhenCalculateCONTAINSRANGEAndRangeDoesNotContainArgumentRange() {
        viewModel.setInputRange("(1,10]");
        viewModel.setOperation(ViewModel.Operation.CONTAINS_RANGE);
        viewModel.setInputArgument("[2,15]");
        viewModel.calculate();
        assertEquals("The range doesn't contain this range", viewModel.getResult());
    }

    @Test
    public void isResultCorrectWhenCalculateOVERLAPSRANGEAndRangesOverlap() {

        viewModel.setInputRange("(1,10]");
        viewModel.setOperation(ViewModel.Operation.OVERLAPS_RANGE);
        viewModel.setInputArgument("(2,5]");
        viewModel.calculate();

        assertEquals("These ranges overlap", viewModel.getResult());
    }

    @Test
    public void isResultCorrectWhenCalculateOVERLAPSRANGEAndRangesDoNotOverlap() {
        viewModel.setOperation(ViewModel.Operation.OVERLAPS_RANGE);
        viewModel.setInputRange("(1,10]");
        viewModel.setInputArgument("(20,50]");
        viewModel.calculate();

        assertEquals("These ranges don't overlap", viewModel.getResult());
    }

    @Test
    public void isCalculateButtonDisabledWhenEnterInvalidInputRange() {
        viewModel.setInputRange("abc");

        assertFalse(viewModel.isCalculateButtonEnabled());
    }

    @Test
    public void showErrorMessageWhenEnterInvalidInputRange() {
        viewModel.setInputRange("(1,10]");
        viewModel.setInputRange("abc");

        assertEquals(ViewModel.Message.INCORRECT_INPUT_RANGE, viewModel.getMessageText());
    }

    @Test
    public void showErrorMessageWhenInputRangeHasLeftBoundBiggerThenRight() {
        viewModel.setInputRange("(10,1]");

        assertEquals("Error: Value of left bound should not exceed value of right bound to the "
                + "inclusion in the range!", viewModel.getMessageText());
    }

    @Test
    public void isCalculateButtonDisabledWhenEnterInvalidInputArgument() {
        viewModel.setOperation(ViewModel.Operation.OVERLAPS_RANGE);
        viewModel.setInputRange("(1,10]");
        viewModel.setInputArgument("abc");

        assertFalse(viewModel.isCalculateButtonEnabled());
    }

    @Test
    public void showErrorMessageWhenEnterInvalidInputArgumentRange() {
        viewModel.setInputRange("(1,10]");
        viewModel.setOperation(ViewModel.Operation.OVERLAPS_RANGE);
        viewModel.setInputArgument("abc");

        assertEquals(ViewModel.Message.INCORRECT_INPUT_ARGUMENT_RANGE, viewModel.getMessageText());
    }

    @Test
    public void showErrorMessageWhenEnterInvalidSetOfNumber() {
        viewModel.setInputRange("(1,10]");
        viewModel.setOperation(ViewModel.Operation.CONTAINS_POINTS);
        viewModel.setInputArgument("abc");

        assertEquals(ViewModel.Message.INCORRECT_INPUT_SET_OF_NUMBERS, viewModel.getMessageText());
    }

    @Test
    public void showMessageWhenEnterValidInputRangeAndArgumentRange() {
        viewModel.setInputRange("(1,10]");
        viewModel.setOperation(ViewModel.Operation.OVERLAPS_RANGE);
        viewModel.setInputArgument("(3,6]");

        assertEquals(ViewModel.Message.CORRECT_INPUT_ARGUMENT_RANGE, viewModel.getMessageText());
    }

    @Test
    public void showMessageWhenEnterValidInputRangeAndInputArgumentIsEmpty() {
        viewModel.setInputRange("(1,10]");
        viewModel.setOperation(ViewModel.Operation.OVERLAPS_RANGE);

        assertEquals(ViewModel.Message.DEFAULT_ARGUMENT_RANGE, viewModel.getMessageText());
    }

    private ViewModel viewModel;
}
