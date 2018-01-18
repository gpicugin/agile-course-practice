package ru.unn.agile.Polynomial.viewmodel;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class StatusTests {
    @Test
    public void canGetStatusName() {
        String addName = Status.BAD_FORMAT.toString();
        assertEquals("Bad format", addName);
    }

    @Test
    public void canGetNumberOfStatuses() {
        int nStatuses = Status.values().length;
        assertEquals(4, nStatuses);
    }

    @Test
    public void canCompareStatusesByName() {
        assertEquals(Status.BAD_FORMAT, Status.BAD_FORMAT);
        assertNotEquals(Status.BAD_FORMAT, Status.SUCCESS);
    }

    @Test
    public void canGetListOfStatuses() {
        Status[] statuses = Status.values();
        Status[] currentStatuses = new Status[] {
                Status.WAITING,
                Status.READY,
                Status.BAD_FORMAT,
                Status.SUCCESS};

        assertArrayEquals(currentStatuses, statuses);
    }
}
