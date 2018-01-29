package ru.unn.agile.ClassMatrix.Model;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertArrayEquals;

public class MatrixTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void canNotCreateMatrixFromNull() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Can't create matrix from null");
        Matrix testMatrix = new Matrix(null);
    }

    @Test
    public void canNotCreateMatrixFromArrayWithDifferentSizeInside() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("There are rows with diffrent sizes in array!");
        float[][] array = {{1, 2},
                {3, 5, 3},
                {5}
        };

        Matrix testMatrix = new Matrix(array);
    }

    @Test
    public void canNotSetElementWithNegativeIndex() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Can't set an element with such index!");
        Matrix testMatrix = new Matrix(2, 2);
        testMatrix.set(-1, -1, 2);
    }

    @Test
    public void canNotSetElementWithNonexistentIndex() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Can't set an element with such index!");
        Matrix testMatrix = new Matrix(2, 3);
        testMatrix.set(3, 3, 5);
    }

    @Test
    public void canNotGetElementWithNegativeIndex() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Can't get an element with such index!");
        Matrix testMatrix = new Matrix(3, 3);
        testMatrix.get(-1, -1);
    }

    @Test
    public void canNotGetElementWithNonexistentIndex() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("Can't get an element with such index!");
        Matrix testMatrix = new Matrix(2, 2);
        testMatrix.get(3, 3);
    }

    @Test
    public void canFillMatrix2x3() {
        Matrix testMatrix = new Matrix(2, 3);

        testMatrix.set(0, 0, 2);
        testMatrix.set(0, 1, 3);
        testMatrix.set(0, 2, 6);
        testMatrix.set(1, 0, 3);
        testMatrix.set(1, 1, 5);
        testMatrix.set(1, 2, 2);

        float[][] array = {{2, 3, 6},
                {3, 5, 2}
        };

        assertArrayEquals(array, testMatrix.toArray());
    }

    @Test
    public void canCreateMatrixFromArray() {
        float[][] array = {{1.3f, 2.3f},
                {3f, 5f}
        };

        Matrix testMatrix = new Matrix(array);
        assertArrayEquals(array, testMatrix.toArray());
    }

    @Test
    public void isDeterminantOfNotSquareMatrix0() {
        float[][] array = {{6.2f, 4.1f},
                {3.1f, 5.3f},
                {6.4f, 5.2f}
        };

        Matrix testMatrix = new Matrix(array);
        assertEquals(0f, testMatrix.calculateDeterminant(), accuracy);
    }

    @Test
    public void isSquareMatrixDeterminedAsSquare() {
        float[][] array = {{1f, 2f},
                {3f, 5f}
        };

        Matrix testMatrix = new Matrix(array);
        assertTrue(testMatrix.isSquareMatrix());
    }

    @Test
    public void isNotSquareMatrixDeterminedAsNotSquare() {
        float[][] array = {{1f, 2f, 3f},
                {3f, 5f, 9f}
        };

        Matrix testMatrix = new Matrix(array);
        assertFalse(testMatrix.isSquareMatrix());
    }

    @Test
    public void canCalculateDeterminantOfMatrix1x1() {
        float[][] array = {{777f}};

        Matrix testMatrix = new Matrix(array);
        assertEquals(777f, testMatrix.calculateDeterminant(), accuracy);
    }

    @Test
    public void canCalculateDeteriminantOfSquareMatrix2x2Expects18() {
        float[][] array = {{6f, 4f},
                {3.f, 5f}
        };

        Matrix testMatrix = new Matrix(array);
        assertEquals(18f, testMatrix.calculateDeterminant(), accuracy);
    }

    @Test
    public void canCalculateDeteriminantOfMatrix3x3Expects1() {
        float[][] array = {{1f, 0f, 1f},
                {0f, 1f, 0f},
                {0f, 0f, 1f},
        };

        Matrix testMatrix = new Matrix(array);
        assertEquals(1f, testMatrix.calculateDeterminant(), accuracy);
    }

    @Test
    public void isDeterminantOfDegenerateMatrix0() {
        float[][] array = {{1f, 2f, 4f},
                {1f, 1f, 1f},
                {1f, 1f, 1f},
        };

        Matrix testMatrix = new Matrix(array);
        assertEquals(0f, testMatrix.calculateDeterminant(), accuracy);
    }

    private float accuracy = 1.0e-4f;
}
