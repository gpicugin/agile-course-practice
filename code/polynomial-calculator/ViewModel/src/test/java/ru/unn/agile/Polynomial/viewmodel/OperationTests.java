package ru.unn.agile.Polynomial.viewmodel;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class OperationTests {
    @Test
    public void canGetOperationName() {
        String addName = Operation.ADD.toString();
        assertEquals("Add", addName);
    }

    @Test
    public void canGetNumberOfOperations() {
        int nOperations = Operation.values().length;
        assertEquals(3, nOperations);
    }

    @Test
    public void canCompareOperationsByName() {
        assertEquals(Operation.ADD, Operation.ADD);
        assertNotEquals(Operation.ADD, Operation.MULTIPLY);
    }

    @Test
    public void canGetListOfOperations() {
        Operation[] operations = Operation.values();
        Operation[] currentOperations = new Operation[] {
                Operation.ADD,
                Operation.SUB,
                Operation.MULTIPLY};

        assertArrayEquals(currentOperations, operations);
    }
}
