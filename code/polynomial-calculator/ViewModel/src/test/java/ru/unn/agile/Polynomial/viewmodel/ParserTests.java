package ru.unn.agile.Polynomial.viewmodel;

import ru.unn.agile.Polynomial.model.Polynomial;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

public class ParserTests {
    @Test
    public void cantParseEmptyString() {
        assertNull(Parser.getPolynomial(""));
    }

    @Test
    public void cantParseIntegerNumber() {
        assertNull(Parser.getPolynomial("-1"));
    }

    @Test
    public void cantParseDoubleNumber() {
        assertNull(Parser.getPolynomial("7.00005"));
    }

    @Test
    public void cantParseMonomialWithMissedDegree() {
        assertNull(Parser.getPolynomial("1x"));
    }

    @Test
    public void cantParseMonomialWithEmptyDegree() {
        assertNull(Parser.getPolynomial("1x^()"));
    }

    @Test
    public void cantParseMonomialWithNotIntegerDegrees() {
        assertNull(Parser.getPolynomial("8x^(-131.0)"));
    }

    @Test
    public void cantParseMonomialWhereDegreeIsLetter() {
        assertNull(Parser.getPolynomial("8x^(b)"));
    }

    @Test
    public void cantParseMonomialWithMissedCoefficient() {
        assertNull(Parser.getPolynomial("x^(2)"));
    }

    @Test
    public void cantParseMonomialWhereCoefficientIsLetter() {
        assertNull(Parser.getPolynomial("ax^(2)"));
    }

    @Test
    public void cantParseMonomialWhereCoefficientsIsMixOfLetterAndDigit() {
        assertNull(Parser.getPolynomial("8ax^(1)"));
        assertNull(Parser.getPolynomial("a9x^(3)"));
    }

    @Test
    public void cantParseMonomialWithWhitespacesInCoefficient() {
        assertNull(Parser.getPolynomial("1 4x^(7)"));
    }

    @Test
    public void cantParseMonomialWithDoublePlusesAtTheBeginning() {
        assertNull(Parser.getPolynomial("++1x^(1)"));
    }

    @Test
    public void cantParseMonomialWithTrashAtTheEnd() {
        assertNull(Parser.getPolynomial("7x^(2)-"));
    }

    @Test
    public void cantParseMonomialWithWhitespaceAfterDegree() {
        assertNull(Parser.getPolynomial("9x^(-1) "));
    }

    @Test
    public void cantParseMonomialWithWhitespaceInDegree() {
        assertNull(Parser.getPolynomial("-63x^( -2)"));
    }

    @Test
    public void cantParseMonomialWithBigX() {
        assertNull(Parser.getPolynomial("881.0X^(9)"));
    }

    @Test
    public void cantParseMonomialWithPlusAtTheBeginning() {
        assertNull(Parser.getPolynomial("+1x^(1)"));
    }

    @Test
    public void cantParseMonomialWithWhitespaceBeforeCoefficient() {
        assertNull(Parser.getPolynomial(" 64x^(-2)"));
    }

    @Test
    public void cantParseMonomialWithWhitespaceAfterCoefficient() {
        assertNull(Parser.getPolynomial("85 x^(1)"));
    }

    @Test
    public void cantParseMonomialWithoutRightCurlyInDegree() {
        assertNull(Parser.getPolynomial("85x^(1"));
    }

    @Test
    public void cantParseMonomialWithRightCurlyInTheEnd() {
        assertNull(Parser.getPolynomial("85x^(1)("));
    }

    @Test
    public void cantParseMonomialWhereSpecialSequencesStandViceVersa() {
        assertNull(Parser.getPolynomial("85)1x^("));
    }

    @Test
    public void cantParseMonomialsWhereSpecialCharSequencesAreUsed() {
        assertNull(Parser.getPolynomial("1x^x0)"));
        assertNull(Parser.getPolynomial("1xyx0)"));
        assertNull(Parser.getPolynomial("-1xy)0x"));
        assertNull(Parser.getPolynomial("-1)yx0x"));
        assertNull(Parser.getPolynomial("-1)yx0x"));
        assertNull(Parser.getPolynomial("-1)^(0x"));
    }

    @Test
    public void canParseZeroMonomial() {
        Polynomial poly = Parser.getPolynomial("0.0x^(6)");
        assertNotNull(poly);
        assertEquals("0.0", poly.toString());
    }

    @Test
    public void canParseMonomialWithPositiveCoefficientAndDegree() {
        Polynomial poly = Parser.getPolynomial("1.5074x^(10)");
        assertNotNull(poly);
        assertEquals("1.5074x^(10)", poly.toString());
    }

    @Test
    public void canParseMonomialWithNegativeCoefficient() {
        Polynomial poly = Parser.getPolynomial("-111.56x^(9)");
        assertNotNull(poly);
        assertEquals("-111.56x^(9)", poly.toString());
    }

    @Test
    public void canParseMonomialWithNegativeDegree() {
        Polynomial poly = Parser.getPolynomial("0.01x^(-339)");
        assertNotNull(poly);
        assertEquals("0.01x^(-339)", poly.toString());
    }

    @Test
    public void canParseMonomialWithNegativeCoefficientAndDegree() {
        Polynomial poly = Parser.getPolynomial("-41.0441001x^(-9)");
        assertNotNull(poly);
        assertEquals("-41.0441001x^(-9)", poly.toString());
    }

    @Test
    public void canParseMonomialWithStrangeDegree() {
        Polynomial poly = Parser.getPolynomial("5x^(-02)");
        assertNotNull(poly);
        assertEquals("5.0x^(-2)", poly.toString());
    }

    @Test
    public void cantParsePolynomialWithMonomialWithoutDegree() {
        assertNull(Parser.getPolynomial("1x^(2)-5x"));
    }

    @Test
    public void cantParsePolynomialWithWrongOperand() {
        assertNull(Parser.getPolynomial("1x^(2)*5x^(-1)"));
    }

    @Test
    public void cantParsePolynomialWithBigX() {
        assertNull(Parser.getPolynomial("-6x^(12)-4X^(-41)"));
    }

    @Test
    public void cantParsePolynomialWithNumberMonomial() {
        assertNull(Parser.getPolynomial("1x^(2)+5"));
    }

    @Test
    public void cantParsePolynomialWithMonomialWithoutRightCurly() {
        assertNull(Parser.getPolynomial("1x^(2)-2x^(-2"));
    }

    @Test
    public void cantParsePolynomialWithoutSignBetweenMonomials() {
        assertNull(Parser.getPolynomial("1x^(1)1x^(1)"));
    }

    @Test
    public void cantParseSpecialPolynomial() {
        assertNull(Parser.getPolynomial("1x^(1x^(1)1)"));
    }

    @Test
    public void cantParsePolynomialWithTrash() {
        assertNull(Parser.getPolynomial("1x^(2)+word"));
    }

    @Test
    public void canParseZeroPolynomial() {
        Polynomial poly = Parser.getPolynomial("0.0x^(6)-0x^(-1)+0.0x^(4)");
        assertNotNull(poly);
        assertEquals("0.0", poly.toString());
    }

    @Test
    public void canParsePolynomialWithIntegerCoefficients() {
        Polynomial poly = Parser.getPolynomial("1x^(89)-555x^(-41)+9x^(-1)");
        assertNotNull(poly);
        assertEquals("-555.0x^(-41)+9.0x^(-1)+1.0x^(89)", poly.toString());
    }

    @Test
    public void canParsePolynomialWithFloatCoefficients() {
        Polynomial poly = Parser.getPolynomial("1.00001x^(89)-555.9999x^(-41)+9.3x^(-1)");
        assertNotNull(poly);
        assertEquals("-555.9999x^(-41)+9.3x^(-1)+1.00001x^(89)", poly.toString());
    }

    @Test
    public void canParsePolynomialWithIntegerAndFloatCoefficients() {
        Polynomial poly = Parser.getPolynomial("11x^(2)-5x^(1)+0.010104x^(-4)-13x^(0)");
        assertNotNull(poly);
        assertEquals("0.010104x^(-4)-13.0x^(0)-5.0x^(1)+11.0x^(2)", poly.toString());
    }

    @Test
    public void canParsePolynomialWhereAllMonomialsHaveTheSameDegree() {
        Polynomial poly = Parser.getPolynomial("88.1x^(-3)-1x^(-3)+2.00x^(-3)");
        assertNotNull(poly);
        assertEquals("89.1x^(-3)", poly.toString());
    }

    @Test
    public void canParsePolynomialWhereSomeMonomialsHaveTheSameDegree() {
        Polynomial poly = Parser.getPolynomial("1.90x^(-4)-7x^(-77)-1.02x^(-77)");
        assertNotNull(poly);
        assertEquals("-8.02x^(-77)+1.9x^(-4)", poly.toString());
    }

    @Test
    public void canParsePolynomialWithWhitespacesBetweenMonomials() {
        Polynomial poly = Parser.getPolynomial("11x^(2) - 5x^(1) + 13x^(0)");
        assertNotNull(poly);
        assertEquals("13.0x^(0)-5.0x^(1)+11.0x^(2)", poly.toString());
    }

    @Test
    public void canParseArbitraryPolynomial() {
        Polynomial poly = Parser.getPolynomial(
                "1133123.000107x^(-423) - 0.0000009x^(1)+999x^(0)");
        assertNotNull(poly);
        assertEquals("1133123.000107x^(-423)+999.0x^(0)-9.0E-7x^(1)",
                poly.toString());
    }

    @Test
    public void cantParseSpecificParsingSequences() {
        assertNull(Parser.getPolynomial("1(2)"));
        assertNull(Parser.getPolynomial("1(2)1(2)"));
    }

    @Test
    public void cantParseTrash() {
        Polynomial poly = Parser.getPolynomial("I'm polynomial");
        assertNull(poly);
    }
}
