package ru.unn.agile.AssessmentsAccounting.infrastructure;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class AssessmentsTextLoggerTests {

    @Before
    public void setUp() {
        assessmentsTextLogger = new AssessmentsTextLogger(LOG_FILE_NAME);
    }

    @Test
    public void canCreateLoggerWithFileName() {
        assertNotNull(assessmentsTextLogger);
    }

    @Test
    public void canCreateLogFileOnDisk() {
        try {
            new BufferedReader(new FileReader(LOG_FILE_NAME));
        } catch (FileNotFoundException e) {
            fail("File " + LOG_FILE_NAME + " wasn't found!");
        }
    }

    @Test
    public void canWriteLogMessage() {
        String logMessage = "log message";

        assessmentsTextLogger.log(logMessage);

        String message = assessmentsTextLogger.getLog().get(0);
        assertTrue(message.matches(".*" + logMessage + "$"));
    }

    @Test
    public void canWriteSeveralLogMessage() {
        String[] messages = {"First message", "Second message"};

        assessmentsTextLogger.log(messages[0]);
        assessmentsTextLogger.log(messages[1]);

        List<String> actualMessages = assessmentsTextLogger.getLog();
        for (int i = 0; i < actualMessages.size(); i++) {
            assertTrue(matchLogWithMessage(actualMessages.get(i), messages[i]));
        }
    }

    @Test
    public void doesLogContainDateAndTime() {
        String testMessage = "Test message";

        assessmentsTextLogger.log(testMessage);

        String message = assessmentsTextLogger.getLog().get(0);
        assertTrue(message.matches(LOG_MESSAGE_FORMAT));
    }

    private boolean matchLogWithMessage(final String logString, final String messageString) {
        return logString.matches(".*" + messageString + "$");
    }

    private static final String LOG_FILE_NAME = "./Assessments.log";
    private static final String LOG_MESSAGE_FORMAT =
            "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\t::\t.*";
    private AssessmentsTextLogger assessmentsTextLogger;
}
