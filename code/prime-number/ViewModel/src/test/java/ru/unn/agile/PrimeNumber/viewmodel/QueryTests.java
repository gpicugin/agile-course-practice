package ru.unn.agile.PrimeNumber.viewmodel;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class QueryTests {
    @Test
    public void canConstructQuery() {
        Query query = new Query();
        assertNotNull(query);
    }

    @Test
    public void canConstructQueryFromString() {
        Query query = new Query("short message", "answer message");
        assertNotNull(query);
    }

    @Test
    public void canGetShortMessage() {
        Query query = new Query("short message", "answer message");
        assertEquals("short message", query.getShortMessage());
    }

    @Test
    public void canGetAnswerMessage() {
        Query query = new Query("short message", "answer message");
        assertEquals("answer message", query.getAnswerMessage());
    }

    @Test
    public void areEqualQueriesEqual() {
        Query query1 = new Query("short message", "answer message");
        Query query2 = new Query("short message", "answer message");
        assertEquals(query1, query2);
    }

    @Test
    public void areDifferentQueriesNonEqual() {
        Query query1 = new Query("short message 1", "answer message 1");
        Query query2 = new Query("short message 2", "answer message 2");
        assertNotEquals(query1, query2);
    }

    @Test
    public void isQueryEqualToItself() {
        Query query = new Query("short message", "answer message");
        assertEquals(query, query);
    }

    @Test
    public void canConvertQueryToString() {
        Query query = new Query("short message", "answer message");
        assertEquals("short message", query.toString());
    }
}
