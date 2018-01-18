package ru.unn.agile.Polynomial.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ViewModelTests {
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
        assertEquals("", viewModel.getFirstPolynomial());
        assertEquals("", viewModel.getSecondPolynomial());
        assertEquals(Operation.ADD, viewModel.getOperation());
        assertEquals("", viewModel.getResult());
        assertEquals(Status.WAITING, viewModel.getStatus());
    }

    @Test
    public void isStatusWaitingWhenCalculateWithEmptyFields() {
        viewModel.calculate();
        assertEquals(Status.WAITING, viewModel.getStatus());
    }

    @Test
    public void isStatusReadyWhenFieldsAreFilledCorrectly() {
        fillInputFieldsWithCorrectData();
        viewModel.processTextChanged();
        assertEquals(Status.READY, viewModel.getStatus());
    }

    @Test
    public void canReportBadFormat() {
        fillInputFieldsWithInvalidData();
        viewModel.processTextChanged();
        assertEquals(Status.BAD_FORMAT, viewModel.getStatus());
    }

    @Test
    public void canGetReadyStatusAfterBadFormat() {
        fillInputFieldsWithInvalidData();
        viewModel.processTextChanged();
        fillInputFieldsWithCorrectData();
        viewModel.processTextChanged();
        assertEquals(Status.READY, viewModel.getStatus());
    }

    @Test
    public void isCalculateButtonDisabledInitially() {
        assertEquals(false, viewModel.isButtonCalculateEnabled());
    }

    @Test
    public void isCalculateButtonEnabledWhenInputCorrect() {
        fillInputFieldsWithCorrectData();
        viewModel.processTextChanged();
        assertEquals(true, viewModel.isButtonCalculateEnabled());
    }

    @Test
    public void isCalculateButtonDisabledWhenFormatIsBad() {
        fillInputFieldsWithInvalidData();
        viewModel.processTextChanged();
        assertEquals(false, viewModel.isButtonCalculateEnabled());
    }

    @Test
    public void isCalculateButtonDisabledWithIncompleteInput() {
        viewModel.setSecondPolynomial("1x^2-14.3x^4");
        viewModel.processTextChanged();
        assertEquals(false, viewModel.isButtonCalculateEnabled());
    }

    @Test
    public void canSetAddOperation() {
        viewModel.setOperation(Operation.ADD);
        assertEquals(Operation.ADD, viewModel.getOperation());
    }

    @Test
    public void canSetSubOperation() {
        viewModel.setOperation(Operation.SUB);
        assertEquals(Operation.SUB, viewModel.getOperation());
    }

    @Test
    public void canSetMulOperation() {
        viewModel.setOperation(Operation.MULTIPLY);
        assertEquals(Operation.MULTIPLY, viewModel.getOperation());
    }

    @Test
    public void canSetSuccessMessage() {
        fillInputFieldsWithCorrectData();
        viewModel.calculate();
        assertEquals(Status.SUCCESS, viewModel.getStatus());
    }

    @Test
    public void canSetBadFormatMessage() {
        fillInputFieldsWithInvalidData();
        viewModel.calculate();
        assertEquals(Status.BAD_FORMAT, viewModel.getStatus());
    }

    @Test
    public void canPerformCalculationAfterBadFormat() {
        fillInputFieldsWithInvalidData();
        viewModel.calculate();
        assertEquals(Status.BAD_FORMAT, viewModel.getStatus());
        fillInputFieldsWithCorrectData();
        viewModel.calculate();
        assertEquals(Status.SUCCESS, viewModel.getStatus());
    }

    @Test
    public void canGetWaitingStatusAfterSuccess() {
        fillInputFieldsWithCorrectData();
        viewModel.calculate();
        assertEquals(Status.SUCCESS, viewModel.getStatus());
        viewModel.setSecondPolynomial("");
        viewModel.processTextChanged();
        assertEquals(Status.WAITING, viewModel.getStatus());
    }

    @Test
    public void canAddPolynomials() {
        viewModel.setFirstPolynomial("1.0x^(3)-2.132x^(-1)-0.2x^(10)");
        viewModel.setSecondPolynomial("-5.1x^(5)+0.9x^(10)");
        viewModel.setOperation(Operation.ADD);
        viewModel.calculate();
        assertEquals("-2.132x^(-1)+1.0x^(3)-5.1x^(5)+0.7x^(10)", viewModel.getResult());
    }

    @Test
    public void canSubPolynomials() {
        viewModel.setFirstPolynomial("1.0x^(3)-2.132x^(-1)-0.2x^(10)");
        viewModel.setSecondPolynomial("-5.1x^(5)+0.9x^(10)");
        viewModel.setOperation(Operation.SUB);
        viewModel.calculate();
        assertEquals("-2.132x^(-1)+1.0x^(3)+5.1x^(5)-1.1x^(10)", viewModel.getResult());
    }

    @Test
    public void canMultiplyPolynomials() {
        viewModel.setFirstPolynomial("1.0x^(6)+2.13x^(-1)");
        viewModel.setSecondPolynomial("-5.1x^(5)+0.9x^(10)");
        viewModel.setOperation(Operation.MULTIPLY);
        viewModel.calculate();
        assertEquals("-10.863x^(4)+1.917x^(9)-5.1x^(11)+0.9x^(16)", viewModel.getResult());
    }

    private void fillInputFieldsWithCorrectData() {
        viewModel.setFirstPolynomial("7x^(55)-0.1x^(-14)");
        viewModel.setSecondPolynomial("-99x^(-4)+0.411x^(1)");
    }

    private void fillInputFieldsWithInvalidData() {
        viewModel.setFirstPolynomial("ax^2+by+z");
        viewModel.setSecondPolynomial("2p");
    }

    private ViewModel viewModel;
}
