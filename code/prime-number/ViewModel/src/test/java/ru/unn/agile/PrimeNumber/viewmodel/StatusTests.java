package ru.unn.agile.PrimeNumber.viewmodel;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StatusTests {
    @Test
    public void statusesDefined() {
        Status status = Status.WAITING;
        status = Status.BAD_FORMAT;
        status = Status.SUCCESS;
        status = Status.READY;
    }

    @Test
    public void canGetStatusName() {
        Status status = Status.WAITING;
        assertEquals("WAITING", status.name());
    }

    @Test
    public void canConvertStatusToString() {
        Status status = Status.WAITING;
        assertEquals("Please provide input data", status.toString());
    }
}
