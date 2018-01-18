package ru.unn.agile.Polynomial.model;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.TreeMap;

public class PolynomialTest {
    private final double eps = 1e-9;

    @Test
    public void canCreateEmptyPolynomial() {
        Polynomial poly = new Polynomial();
        assertNotNull(poly);
    }

    @Test
    public void canCreatePolynomialWithEmptyCoefficients() {
        Polynomial poly = new Polynomial(new TreeMap<Integer, Double>());
        assertNotNull(poly);
    }

    @Test
    public void canCreatePolynomialWithNonEmptyCoefficients() {
        Polynomial poly = new Polynomial(new TreeMap<Integer, Double>() {{
            put(-2, -1.1);  put(0, -1.1); put(3, -1.1);
        }});
        assertNotNull(poly);
    }

    @Test
    public void canCreatePolynomialFromPolynomial() {
        Polynomial poly = new Polynomial(new TreeMap<Integer, Double>() {{ put(0, -1.1); }});
        assertTrue(poly.equals(new Polynomial(poly)));
    }

    @Test
    public void areEqualPolynomialsEqual() {
        Polynomial poly1 = new Polynomial(new TreeMap<Integer, Double>() {{ put(0, -1.1); }});
        Polynomial poly2 = new Polynomial(new TreeMap<Integer, Double>() {{ put(0, -1.1); }});
        assertTrue(poly1.equals(poly2));
    }

    @Test
    public void areEqualPolynomialsWithZeroCoefficientsEqual() {
        Polynomial poly1 = new Polynomial(new TreeMap<Integer, Double>() {{ put(10, 0.0); }});
        Polynomial poly2 = new Polynomial(new TreeMap<Integer, Double>() {{ put(0, 0.0); }});
        assertTrue(poly1.equals(poly2));
    }

    @Test
    public void areEqualPolynomialsWithZeroAndNonZeroCoefficientsEqual() {
        Polynomial poly1 = new Polynomial(new TreeMap<Integer, Double>() {{
            put(10, 0.0); put(13, 1.0);
        }});
        Polynomial poly2 = new Polynomial(new TreeMap<Integer, Double>() {{
            put(0, 0.0); put(13, 1.0);
        }});
        assertTrue(poly1.equals(poly2));
    }

    @Test
    public void areDifferentPolynomialsWithZeroAndNonZeroCoefficientsNotEqual() {
        Polynomial poly1 = new Polynomial(new TreeMap<Integer, Double>() {{
            put(-9, 0.0); put(12, 1.0);
        }});
        Polynomial poly2 = new Polynomial(new TreeMap<Integer, Double>() {{
            put(0, 0.0); put(12, 1.0); put(1, 1.0);
        }});
        assertFalse(poly1.equals(poly2));
    }

    @Test
    public void canGetExistentPolynomialCoefficient() {
        Polynomial poly = new Polynomial(new TreeMap<Integer, Double>() {{ put(0, -1.1); }});
        assertEquals(poly.getCoefficient(0), -1.1, eps);
    }

    @Test
    public void canGetNonExistentPolynomialCoefficient() {
        Polynomial poly = new Polynomial(new TreeMap<Integer, Double>() {{ put(0, -1.1); }});
        assertEquals(poly.getCoefficient(1), 0.0, eps);
    }

    @Test
    public void canAddPolynomialsZeroAndNonZero() {
        Polynomial poly1 = new Polynomial();
        Polynomial poly2 = new Polynomial(new TreeMap<Integer, Double>() {{ put(0, 2.0); }});
        Polynomial sum = poly1.add(poly2);
        assertTrue(sum.equals(new Polynomial(new TreeMap<Integer, Double>() {{ put(0, 2.0); }})));
    }

    @Test
    public void canAddZeroMonomialToPolynomial() {
        Polynomial poly = new Polynomial(new TreeMap<Integer, Double>() {{ put(1, 3.0); }});
        poly.addMonomial(0, 0.0);
        assertTrue(poly.equals(new Polynomial(new TreeMap<Integer, Double>() {{ put(1, 3.0); }})));
    }

    @Test
    public void canAddNonExistentMonomialToPolynomial() {
        Polynomial poly = new Polynomial(new TreeMap<Integer, Double>() {{ put(1, 3.0); }});
        poly.addMonomial(3, 1.1);
        assertTrue(poly.equals(new Polynomial(new TreeMap<Integer, Double>() {{
            put(1, 3.0); put(3, 1.1);
        }})));
    }

    @Test
    public void canAddExistentMonomialToPolynomial() {
        Polynomial poly = new Polynomial(new TreeMap<Integer, Double>() {{ put(1, 3.0); }});
        poly.addMonomial(1, 1.1);
        assertTrue(poly.equals(new Polynomial(new TreeMap<Integer, Double>() {{ put(1, 4.1); }})));
    }

    @Test
    public void canAddExistentMonomialWithNegativeExponentToPolynomial() {
        Polynomial poly = new Polynomial(new TreeMap<Integer, Double>() {{ put(-3, 3.0); }});
        poly.addMonomial(-3, 1.0);
        assertTrue(poly.equals(new Polynomial(new TreeMap<Integer, Double>() {{ put(-3, 4.0); }})));
    }

    @Test
    public void canAddMonomialsToPolynomial() {
        Polynomial poly = new Polynomial();
        poly.addMonomial(1, 3.0);
        poly.addMonomial(2, 17.33);
        poly.addMonomial(0, 3.0);
        poly.addMonomial(1, -3.5);
        poly.addMonomial(-1, 15.0);
        assertTrue(poly.equals(new Polynomial(new TreeMap<Integer, Double>() {{
            put(-1, 15.0); put(0, 3.0); put(1, -0.5); put(2, 17.33);
        }})));
    }

    @Test
    public void canAddZeroAndZeroPolynomials() {
        Polynomial poly1 = new Polynomial();
        Polynomial poly2 = new Polynomial();
        Polynomial sum = poly1.add(poly2);
        assertTrue(sum.equals(new Polynomial(new TreeMap<Integer, Double>() {{ put(0, 0.0); }})));
    }

    @Test
    public void canAddZeroAndNonZeroPolynomials() {
        Polynomial poly1 = new Polynomial();
        Polynomial poly2 = new Polynomial(new TreeMap<Integer, Double>() {{
            put(0, 2.0); put(13, 4.0);
        }});
        Polynomial sum = poly1.add(poly2);
        assertTrue(sum.equals(new Polynomial(new TreeMap<Integer, Double>() {{
            put(0, 2.0); put(13, 4.0);
        }})));
    }

    @Test
    public void canAddPolynomials() {
        Polynomial poly1 = new Polynomial(new TreeMap<Integer, Double>() {{
            put(-2, 2.0); put(3, -1.0); put(2, 3.0);
        }});
        Polynomial poly2 = new Polynomial(new TreeMap<Integer, Double>() {{
            put(-2, -3.0); put(3, 2.0); put(13, 4.0);
        }});
        Polynomial sum = poly1.add(poly2);
        assertTrue(sum.equals(new Polynomial(new TreeMap<Integer, Double>() {{
            put(-2, -1.0); put(3, 1.0); put(2, 3.0); put(13, 4.0);
        }})));
    }

    @Test
    public void canSubZeroMonomialFromPolynomial() {
        Polynomial poly = new Polynomial(new TreeMap<Integer, Double>() {{ put(1, 3.0); }});
        poly.subMonomial(0, 0.0);
        assertTrue(poly.equals(new Polynomial(new TreeMap<Integer, Double>() {{ put(1, 3.0); }})));
    }

    @Test
    public void canSubNonExistentMonomialFromPolynomial() {
        Polynomial poly = new Polynomial(new TreeMap<Integer, Double>() {{ put(1, 3.0); }});
        poly.subMonomial(3, 1.1);
        assertTrue(poly.equals(new Polynomial(new TreeMap<Integer, Double>() {{
            put(1, 3.0); put(3, -1.1);
        }})));
    }

    @Test
    public void canSubExistentMonomialFromPolynomial() {
        Polynomial poly = new Polynomial(new TreeMap<Integer, Double>() {{ put(1, 3.0); }});
        poly.subMonomial(1, 1.1);
        assertTrue(poly.equals(new Polynomial(new TreeMap<Integer, Double>() {{ put(1, 1.9); }})));
    }

    @Test
    public void canSubExistentMonomialWithNegativeExponentFromPolynomial() {
        Polynomial poly = new Polynomial(new TreeMap<Integer, Double>() {{ put(-3, 3.0); }});
        poly.subMonomial(-3, 1.1);
        assertTrue(poly.equals(new Polynomial(new TreeMap<Integer, Double>() {{ put(-3, 1.9); }})));
    }

    @Test
    public void canSubZeroAndZeroPolynomials() {
        Polynomial poly1 = new Polynomial();
        Polynomial poly2 = new Polynomial();
        Polynomial diff = poly1.sub(poly2);
        assertTrue(diff.equals(new Polynomial(new TreeMap<Integer, Double>() {{ put(0, 0.0); }})));
    }

    @Test
    public void canSubZeroAndNonZeroPolynomials() {
        Polynomial poly1 = new Polynomial();
        Polynomial poly2 = new Polynomial(new TreeMap<Integer, Double>() {{
            put(-1, 2.0); put(13, 4.0);
        }});
        Polynomial diff = poly1.sub(poly2);
        assertTrue(diff.equals(new Polynomial(new TreeMap<Integer, Double>() {{
            put(-1, -2.0); put(13, -4.0);
        }})));
    }

    @Test
    public void canSubNonZeroAndZeroPolynomials() {
        Polynomial poly1 = new Polynomial(new TreeMap<Integer, Double>() {{
            put(3, 2.0); put(13, 4.0);
        }});
        Polynomial poly2 = new Polynomial();
        Polynomial diff = poly1.sub(poly2);
        assertTrue(diff.equals(new Polynomial(new TreeMap<Integer, Double>() {{
            put(3, 2.0); put(13, 4.0);
        }})));
    }

    @Test
    public void canSubPolynomials() {
        Polynomial poly1 = new Polynomial(new TreeMap<Integer, Double>() {{
            put(-7, 1.0); put(7, -1.2); put(1, 3.0);
        }});
        Polynomial poly2 = new Polynomial(new TreeMap<Integer, Double>() {{
            put(-7, 7.0); put(7, 2.0); put(13, 4.0);
        }});
        Polynomial diff = poly1.sub(poly2);
        assertTrue(diff.equals(new Polynomial(new TreeMap<Integer, Double>() {{
            put(-7, -6.0); put(7, -3.2); put(1, 3.0); put(13, -4.0);
        }})));
    }

    @Test
    public void canMultiplyPolynomialByZeroScalar() {
        Polynomial poly = new Polynomial(new TreeMap<Integer, Double>() {{
            put(1, 3.0); put(13, -4.1);
        }});
        Polynomial mult = poly.multiply(0.0);
        assertTrue(mult.equals(new Polynomial()));
    }

    @Test
    public void canMultiplyZeroPolynomialByNonZeroScalar() {
        Polynomial poly = new Polynomial();
        Polynomial mult = poly.multiply(13.0);
        assertTrue(mult.equals(new Polynomial()));
    }

    @Test
    public void canMultiplyPolynomialByPositiveScalar() {
        Polynomial poly = new Polynomial(new TreeMap<Integer, Double>() {{
            put(1, 3.0); put(13, -4.1);
        }});
        Polynomial mult = poly.multiply(5.0);
        assertTrue(mult.equals(new Polynomial(new TreeMap<Integer, Double>() {{
            put(1, 15.0); put(13, -20.5);
        }})));
    }

    @Test
    public void canMultiplyPolynomialByNegativeScalar() {
        Polynomial poly = new Polynomial(new TreeMap<Integer, Double>() {{
            put(1, 3.0); put(13, -4.1);
        }});
        Polynomial mult = poly.multiply(-5.0);
        assertTrue(mult.equals(new Polynomial(new TreeMap<Integer, Double>() {{
            put(1, -15.0); put(13, 20.5);
        }})));
    }

    @Test
    public void canMultiplyPolynomialByScalar() {
        Polynomial poly = new Polynomial(new TreeMap<Integer, Double>() {{
            put(0, 1.0); put(2, -2.0); put(12, -3.0);
        }});
        Polynomial mult = poly.multiply(3.0);
        assertTrue(mult.equals(new Polynomial(new TreeMap<Integer, Double>() {{
            put(0, 3.0); put(2, -6.0); put(12, -9.0);
        }})));
    }

    @Test
    public void canMultiplyZeroPolynomialByMonomial() {
        Polynomial poly = new Polynomial();
        Polynomial mult = poly.multiply(3, 2.0);
        assertTrue(mult.equals(new Polynomial()));
    }

    @Test
    public void canMultiplyPolynomialByZeroMonomial() {
        Polynomial poly = new Polynomial(new TreeMap<Integer, Double>() {{
            put(21, 0.0); put(2, 13.3);
        }});
        Polynomial mult = poly.multiply(0, 0.0);
        assertTrue(mult.equals(new Polynomial()));
    }

    @Test
    public void canMultiplyPolynomialByMonomial() {
        Polynomial poly = new Polynomial(new TreeMap<Integer, Double>() {{
            put(21, 0.0); put(2, 13.3);
        }});
        Polynomial mult = poly.multiply(3, 2.0);
        assertTrue(mult.equals(new Polynomial(new TreeMap<Integer, Double>() {{
            put(5, 26.6); put(24, 0.0);
        }})));
    }

    @Test
    public void canMultiplyPolynomialByMonomialWithNegativeExponent() {
        Polynomial poly = new Polynomial(new TreeMap<Integer, Double>() {{
            put(24, 0.0); put(2, 13.0); put(-2, -2.0);
        }});
        Polynomial mult = poly.multiply(-3, 2.0);
        assertTrue(mult.equals(new Polynomial(new TreeMap<Integer, Double>() {{
            put(-1, 26.0); put(21, 0.0); put(-5, -4.0);
        }})));
    }

    @Test
    public void canMultiplyZeroPolynomialByZeroPolynomial() {
        Polynomial poly1 = new Polynomial();
        Polynomial poly2 = new Polynomial();
        Polynomial mult = poly1.multiply(poly2);
        assertTrue(mult.equals(new Polynomial()));
    }

    @Test
    public void canMultiplyZeroPolynomialByNonZeroPolynomial() {
        Polynomial poly1 = new Polynomial();
        Polynomial poly2 = new Polynomial(new TreeMap<Integer, Double>() {{
            put(4, -1.2); put(1, 3.0);
        }});
        Polynomial mult = poly1.multiply(poly2);
        assertTrue(mult.equals(new Polynomial()));
    }

    @Test
    public void canMultiplyNonZeroPolynomialByZeroPolynomial() {
        Polynomial poly1 = new Polynomial(new TreeMap<Integer, Double>() {{
            put(3, -1.2); put(1, 3.0);
        }});
        Polynomial poly2 = new Polynomial(new TreeMap<Integer, Double>() {{ put(10, 0.0); }});
        Polynomial mult = poly1.multiply(poly2);
        assertTrue(mult.equals(new Polynomial()));
    }

    @Test
    public void canMultiplyPolynomialByPolynomial() {
        Polynomial poly1 = new Polynomial(new TreeMap<Integer, Double>() {{
            put(0, -1.2); put(1, 3.0);
        }});
        Polynomial poly2 = new Polynomial(new TreeMap<Integer, Double>() {{
            put(0, 2.0); put(2, -4.0);
        }});
        Polynomial mult = poly1.multiply(poly2);
        assertTrue(mult.equals(new Polynomial(new TreeMap<Integer, Double>() {{
            put(0, -2.4); put(1, 6.0); put(2, 4.8); put(3, -12.0);
        }})));
    }

    @Test
    public void canMultiplyPolynomialByPolynomialWithNegativeExponents() {
        Polynomial poly1 = new Polynomial(new TreeMap<Integer, Double>() {{
            put(-2, 4.0); put(1, 3.0);
        }});
        Polynomial poly2 = new Polynomial(new TreeMap<Integer, Double>() {{
            put(-3, 2.0); put(4, -4.0);
        }});
        Polynomial mult = poly1.multiply(poly2);
        assertTrue(mult.equals(new Polynomial(new TreeMap<Integer, Double>() {{
            put(-2, 6.0); put(2, -16.0); put(-5, 8.0); put(5, -12.0);
        }})));
    }

    @Test
    public void canConvertEmptyPolynomialToString() {
        Polynomial poly = new Polynomial();
        assertEquals(poly.toString(), "0.0");
    }

    @Test
    public void canConvertZeroMonomialToString() {
        Polynomial poly = new Polynomial(new TreeMap<Integer, Double>() {{
            put(5, 0.0);
        }});
        assertEquals(poly.toString(), "0.0");
    }

    @Test
    public void canConvertArbitraryMonomialToString() {
        Polynomial poly = new Polynomial(new TreeMap<Integer, Double>() {{
            put(0, 1.0);
        }});
        assertEquals(poly.toString(), "1.0x^(0)");
    }

    @Test
    public void canConvertArbitraryPolynomialToString() {
        Polynomial poly = new Polynomial(new TreeMap<Integer, Double>() {{
            put(1234, -41464.0001); put(-20001, 7777.7);
            put(201321, -84541.8888); put(-1000005, -9412.0);
        }});
        assertEquals(poly.toString(),
                "-9412.0x^(-1000005)+7777.7x^(-20001)-41464.0001x^(1234)-84541.8888x^(201321)");
    }

    @Test
    public void canConvertArbitraryPolynomialWithZeroMonomialToString() {
        Polynomial poly = new Polynomial(new TreeMap<Integer, Double>() {{
            put(1234, -41464.0001); put(-20001, 7777.7);
            put(201321, -84541.8888); put(-1000005, 0.0);
        }});
        assertEquals(poly.toString(),
                "7777.7x^(-20001)-41464.0001x^(1234)-84541.8888x^(201321)");
    }
}
