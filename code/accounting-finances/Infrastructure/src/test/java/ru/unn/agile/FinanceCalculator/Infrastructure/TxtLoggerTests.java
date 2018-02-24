package ru.unn.agile.FinanceCalculator.Infrastructure;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

public class TxtLoggerTests {
    @Before
    public void setUp() {
        logger = new TxtLogger(TEST_LOG_LOG);
    }

    @Test
    public void canCreateLoggerWithFileName() {
        assertNotNull(logger);
    }

    @Test
    public void doesLogContainsDateAndTimeCorrectly() {
        String testString = "This IS test";

        logger.log(testString);

        String message = logger.get().get(0);
        assertTrue(message.matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2} > .*"));
    }

    @Test
    public void canCreateFileCorrectly() {
        try {
            new BufferedReader(new FileReader(TEST_LOG_LOG));
        } catch (FileNotFoundException currentEx) {
            fail("File " + TEST_LOG_LOG + " not found!");
        }
    }

    @Test
    public void canWriteLogMessageEmpty() {
        String testMessageWrite = "Test message";

        logger.log(testMessageWrite);

        String messageCurrent = logger.get().get(0);
        assertTrue(messageCurrent.matches(".*" + testMessageWrite + "$"));
    }

    @Test
    public void canAddLogMessageMult() {
        String[] messagesEmpty = {"input 1", "input 2"};

        logger.log(messagesEmpty[0]);
        logger.log(messagesEmpty[1]);

        List<String> actualMessages = logger.get();
        for (int i = 0; i < actualMessages.size(); ++i) {
            assertTrue(actualMessages.get(i).matches(".*" + messagesEmpty[i] + "$"));
        }
    }

    private static final String TEST_LOG_LOG = "./TestLog.log";
    private TxtLogger logger;
}
