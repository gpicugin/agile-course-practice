package ru.unn.agile.RatioCalculator.Model;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class RatioTest {
    @Test
    public void canCreateRatioWithoutInitialValues() {
        Ratio ratio = new Ratio();

        assertEquals(ratio.getNumerator(), 0);
    }

    @Test
    public void canCreateRatioWithNumeratorAndDenominator() {
        Ratio ratio = new Ratio(1, 2);

        assertEquals(ratio.getDenominator(), 2);
    }

    @Test(expected = ArithmeticException.class)
    public void cantCreateRatioWithZeroDenominator() {
        Ratio ratio = new Ratio(1, 0);
    }

    @Test
    public void equalRatiosAreEqual() {
        Ratio firstRatio = new Ratio(1, 2);
        Ratio secondRatio = new Ratio(2, 4);

        assertEquals(firstRatio, secondRatio);
    }

    @Test
    public void notEqualRatiosAreNotEqual() {
        Ratio firstRatio = new Ratio(1, 2);
        Ratio secondRatio = new Ratio(2, 5);

        assertNotEquals(firstRatio, secondRatio);
    }

    @Test
    public void ratiosWithDefferentSignsAreNotEqual() {
        Ratio firstRatio = new Ratio(1, 2);
        Ratio secondRatio = new Ratio(-1, 2);

        assertNotEquals(firstRatio, secondRatio);
    }

    @Test
    public void equalNegativeRatiosAreEqual() {
        Ratio firstRatio = new Ratio(1, -2);
        Ratio secondRatio = new Ratio(-1, 2);

        assertEquals(firstRatio, secondRatio);
    }

    @Test
    public void whenZeroRatiosAreCreatedInDifferentWaysTheyAreStillEqual() {
        Ratio firstRatio = new Ratio(0, -2);
        Ratio secondRatio = new Ratio(0);

        assertEquals(firstRatio, secondRatio);
    }

    @Test
    public void zeroRatioEqualsZeroNumber() {
        Ratio ratio = new Ratio(0);

        assertEquals(ratio, 0);
    }

    @Test
    public void canCreateRatioFromWholeNumber() {
        Ratio ratio = new Ratio(5);
        Ratio expected = new Ratio(5, 1);

        assertEquals(ratio, expected);
    }

    @Test
    public void ratioWhichPresentsWholeNumberEqualsToThisNumber() {
        Ratio ratio = new Ratio(5);

        assertEquals(ratio, 5);
    }

    @Test
    public void whenRatioCreatedItMustBeIrreducible() {
        Ratio ratio = new Ratio(2, 4);
        Ratio expected = new Ratio(1, 2);

        assertEquals(ratio, expected);
    }

    @Test
    public void whenRatioDenominatorsAreEqualTheLessRatioHasTheLessNumerator() {
        Ratio firstRatio = new Ratio(2, 5);
        Ratio secondRatio = new Ratio(3, 5);

        assertTrue(firstRatio.compareTo(secondRatio) < 0);
    }

    @Test
    public void whenRatioNumeratorsAreEqualTheLessRatioHasTheBiggerDenominator() {
        Ratio firstRatio = new Ratio(1, 2);
        Ratio secondRatio = new Ratio(1, 3);

        assertTrue(firstRatio.compareTo(secondRatio) > 0);
    }

    @Test
    public void canCompareRatiosWithoutEqualsNumeratorsOrDenominators() {
        Ratio firstRatio = new Ratio(1, 6);
        Ratio secondRatio = new Ratio(3, 8);

        assertTrue(firstRatio.compareTo(secondRatio) < 0);
    }

    @Test
    public void positiveRatioIsBiggerThenNegative() {
        Ratio firstRatio = new Ratio(1, 6);
        Ratio secondRatio = new Ratio(-5, 8);

        assertTrue(firstRatio.compareTo(secondRatio) > 0);
    }

    @Test
    public void canCompareNegativeRatios() {
        Ratio firstRatio = new Ratio(-1, -6);
        Ratio secondRatio = new Ratio(-5, 8);

        assertTrue(firstRatio.compareTo(secondRatio) > 0);
    }

    @Test
    public void negativeRatiosHaveOneRepresentation() {
        Ratio firstRatio = new Ratio(-1, 2);
        Ratio secondRatio = new Ratio(1, -2);

        assertEquals(firstRatio, secondRatio);
    }

    @Test
    public void negativeRatioMustHaveNegativeNumeratorNotDenominator() {
        Ratio ratio = new Ratio(1, -2);

        assertTrue((ratio.getDenominator() > 0) && (ratio.getNumerator() < 0));
    }

    @Test
    public void ratioWithNegativeNumeratorAndDenominatorIsPositive() {
        Ratio firstRatio = new Ratio(-1, -2);
        Ratio secondRatio = new Ratio(1, 2);

        assertEquals(firstRatio, secondRatio);
    }

    @Test
    public void ratioWithZeroNumeratorMustEqualsOne() {
        Ratio ratio = new Ratio(0, 2);

        assertEquals(ratio.getDenominator(), 1);
    }

    @Test
    public void negativeRatioMustBeIrreducibleAfterCreating() {
        Ratio firstRatio = new Ratio(6, -9);
        Ratio secondRatio = new Ratio(-2, 3);

        assertEquals(firstRatio, secondRatio);
    }

    @Test
    public void theWholeRatioHasEqualWholePart() {
        Ratio ratio = new Ratio(10);

        assertEquals(ratio.getWholePart(), 10);
    }

    @Test
    public void whenRatioNumeratorIsLessThenDenominatorItHasZeroWholePart() {
        Ratio ratio = new Ratio(1, 6);

        assertEquals(ratio.getWholePart(), 0);
    }

    @Test
    public void canCalcWholePartOfRatioWhichHasBiggerNumerator() {
        Ratio ratio = new Ratio(37, 6);

        assertEquals(ratio.getWholePart(), 6);
    }

    @Test
    public void whenDenominatorDividesNumeratorResultOfDivisionIsTheWholePartOfRatio() {
        Ratio ratio = new Ratio(36, 6);

        assertEquals(ratio.getWholePart(), 6);
    }

    @Test
    public void theWholePartOfZeroIsZero() {
        Ratio ratio = new Ratio(0);

        assertEquals(ratio.getWholePart(), 0);
    }

    @Test
    public void canCalcWholePartOfNegativeRatio() {
        Ratio ratio = new Ratio(-37, 6);

        assertEquals(ratio.getWholePart(), -6);
    }

    @Test
    public void canCalcSumOfRatioAndZeroRatio() {
        Ratio firstRatio = new Ratio(37, 6);
        Ratio secondRatio = new Ratio(0);

        Ratio got = firstRatio.add(secondRatio);

        assertEquals(got, firstRatio);
    }

    @Test
    public void canCalcSumOfRatiosWithCommonDenominators() {
        Ratio firstRatio = new Ratio(2, 3);
        Ratio secondRatio = new Ratio(4, 3);
        Ratio expected = new Ratio(2);

        Ratio got = firstRatio.add(secondRatio);

        assertEquals(got, expected);
    }

    @Test
    public void canCalcSumOfRatiosWithoutCommonDenominators() {
        Ratio firstRatio = new Ratio(1, 6);
        Ratio secondRatio = new Ratio(3, 4);
        Ratio expected = new Ratio(11, 12);

        Ratio got = firstRatio.add(secondRatio);

        assertEquals(got, expected);
    }

    @Test
    public void canCalcSumOfPositiveAndNegativeRatio() {
        Ratio firstRatio = new Ratio(1, 8);
        Ratio secondRatio = new Ratio(-1, 4);
        Ratio expected = new Ratio(-1, 8);

        Ratio got = firstRatio.add(secondRatio);

        assertEquals(got, expected);
    }

    @Test
    public void canCalcSumOfNegativesRatios() {
        Ratio firstRatio = new Ratio(-1, 8);
        Ratio secondRatio = new Ratio(-1, 4);
        Ratio expected = new Ratio(-3, 8);

        Ratio got = firstRatio.add(secondRatio);

        assertEquals(got, expected);
    }

    @Test
    public void canNegateRatio() {
        Ratio ratio = new Ratio(1, 6);
        Ratio expected = new Ratio(-1, 6);

        Ratio got = ratio.negate();

        assertEquals(got, expected);
    }

    @Test
    public void canCalcSubtractOfRatioAndZeroRatio() {
        Ratio firstRatio = new Ratio(37, 6);
        Ratio secondRatio = new Ratio(0);

        Ratio got = firstRatio.sub(secondRatio);

        assertEquals(got, firstRatio);
    }

    @Test
    public void canCalcSubOfRatiosWithCommonDenominators() {
        Ratio firstRatio = new Ratio(2, 3);
        Ratio secondRatio = new Ratio(4, 3);
        Ratio expected = new Ratio(-2, 3);

        Ratio got = firstRatio.sub(secondRatio);

        assertEquals(got, expected);
    }

    @Test
    public void canCalcSubOfRatiosWithoutCommonDenominators() {
        Ratio firstRatio = new Ratio(1, 6);
        Ratio secondRatio = new Ratio(3, 4);
        Ratio expected = new Ratio(-7, 12);

        Ratio got = firstRatio.sub(secondRatio);

        assertEquals(got, expected);
    }

   @Test
   public void canCalcMultiplyOfRatioAndZeroRatio() {
       Ratio firstRatio = new Ratio(5, 6);
       Ratio secondRatio = new Ratio(0);
       Ratio expected = new Ratio(0);

       Ratio got = firstRatio.mult(secondRatio);

       assertEquals(got, expected);
   }

    @Test
    public void canCalcMultiplyOfRatioAndWholeNumberRatio() {
        Ratio firstRatio = new Ratio(5, 6);
        Ratio secondRatio = new Ratio(3);
        Ratio expected = new Ratio(15, 6);

        Ratio got = firstRatio.mult(secondRatio);

        assertEquals(got, expected);
    }

    @Test
    public void canCalcMultiplyOfNormalRatios() {
        Ratio firstRatio = new Ratio(5, 6);
        Ratio secondRatio = new Ratio(3, 4);
        Ratio expected = new Ratio(5, 8);

        Ratio got = firstRatio.mult(secondRatio);

        assertEquals(got, expected);
    }

    @Test
    public void canCalcMultOfPositiveAndNegativeRatio() {
        Ratio firstRatio = new Ratio(1, 8);
        Ratio secondRatio = new Ratio(-5, 4);
        Ratio expected = new Ratio(-5, 32);

        Ratio got = firstRatio.mult(secondRatio);

        assertEquals(got, expected);
    }

    @Test
    public void canCalcMultOfNegativesRatios() {
        Ratio firstRatio = new Ratio(-1, 8);
        Ratio secondRatio = new Ratio(-5, 4);
        Ratio expected = new Ratio(5, 32);

        Ratio got = firstRatio.mult(secondRatio);

        assertEquals(got, expected);
    }

    @Test
    public void canReverseRatio() {
        Ratio ratio = new Ratio(5, 6);
        Ratio expected = new Ratio(6, 5);

        Ratio got = ratio.reverse();

        assertEquals(got, expected);
    }

    @Test(expected = ArithmeticException.class)
    public void cantReverseZeroRatio() {
        Ratio ratio = new Ratio(0);

        Ratio got = ratio.reverse();
    }

    @Test(expected = ArithmeticException.class)
    public void cantCalcDivisionOfRatioAndZeroRatio() {
        Ratio firstRatio = new Ratio(5, 6);
        Ratio secondRatio = new Ratio(0);

        Ratio got = firstRatio.div(secondRatio);
    }

    @Test
    public void canCalcDivisionOfRatioAndWholeNumberRatio() {
        Ratio firstRatio = new Ratio(5, 6);
        Ratio secondRatio = new Ratio(3);
        Ratio expected = new Ratio(5, 18);

        Ratio got = firstRatio.div(secondRatio);

        assertEquals(got, expected);
    }

    @Test
    public void canCalcDivisionOfNormalRatios() {
        Ratio firstRatio = new Ratio(5, 6);
        Ratio secondRatio = new Ratio(3, 4);
        Ratio expected = new Ratio(10, 9);

        Ratio got = firstRatio.div(secondRatio);

        assertEquals(got, expected);
    }

    @Test
    public void canCalcDivisionOfPositiveAndNegativeRatio() {
        Ratio firstRatio = new Ratio(1, 8);
        Ratio secondRatio = new Ratio(-5, 4);
        Ratio expected = new Ratio(-1, 10);

        Ratio got = firstRatio.div(secondRatio);

        assertEquals(got, expected);
    }

    @Test
    public void canCalcDivisionOfNegativesRatios() {
        Ratio firstRatio = new Ratio(-1, 8);
        Ratio secondRatio = new Ratio(-5, 4);
        Ratio expected = new Ratio(1, 10);

        Ratio got = firstRatio.div(secondRatio);

        assertEquals(got, expected);
    }

    @Test
    public void canCalcSumOfRatioAndInteger() {
        Ratio ratio = new Ratio(2, 3);
        int number = 5;
        Ratio expected = new Ratio(17, 3);

        Ratio got = ratio.add(number);

        assertEquals(got, expected);
    }

    @Test
    public void canCalcSubOfRatioAndInteger() {
        Ratio ratio = new Ratio(2, 3);
        int number = 5;
        Ratio expected = new Ratio(-13, 3);

        Ratio got = ratio.sub(number);

        assertEquals(got, expected);
    }

    @Test
    public void canCalcMultiplyOfRatioAndInteger() {
        Ratio ratio = new Ratio(2, 3);
        int number = 5;
        Ratio expected = new Ratio(10, 3);

        Ratio got = ratio.mult(number);

        assertEquals(got, expected);
    }

    @Test
    public void canCalcDivisionOfRatioAndInteger() {
        Ratio ratio = new Ratio(2, 3);
        int number = 5;
        Ratio expected = new Ratio(2, 15);

        Ratio got = ratio.div(number);

        assertEquals(got, expected);
    }

    @Test
    public void ratiosWithBigDenominatorsAndNumeratorsComparesCorrectly() {
        Ratio firstRatio = new Ratio(Integer.MAX_VALUE, 2);
        Ratio secondRatio = new Ratio(Integer.MAX_VALUE, 1);

        assertTrue(firstRatio.compareTo(secondRatio) < 0);
    }

    @Test
    public void ratiosWithBigNegativeDenominatorsAndNumeratorsComparesCorrectly() {
        Ratio firstRatio = new Ratio(Integer.MIN_VALUE, 2);
        Ratio secondRatio = new Ratio(Integer.MIN_VALUE, 1);

        assertTrue(firstRatio.compareTo(secondRatio) > 0);
    }

}
