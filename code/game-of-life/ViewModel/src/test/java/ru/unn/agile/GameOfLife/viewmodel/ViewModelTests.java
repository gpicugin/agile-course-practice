package ru.unn.agile.GameOfLife.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;

public class ViewModelTests {
    private ViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new ViewModel();
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void canSetDefaultValues() {
        assertEquals("", viewModel.heightProperty().get());
        assertEquals("", viewModel.widthProperty().get());
        assertEquals("", viewModel.tableProperty().get());
        assertEquals("", viewModel.resultProperty().get());
        assertEquals(Status.WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsWaitingIfNotEnoughCorrectData() {
        viewModel.heightProperty().set("3");

        assertEquals(Status.WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsReadyWhenFieldsAreFill() {
        viewModel.heightProperty().set("2");
        viewModel.widthProperty().set("4");
        viewModel.tableProperty().set("....\n.*..");
        assertEquals(Status.READY.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void canReportBadFormatWhenIncorrectHeight() {
        viewModel.heightProperty().set("a");
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void canReportBadFormatWhenIncorrectWidth() {
        viewModel.widthProperty().set("0.5");
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void canReportBadFormatWhenIncorrectTable() {
        viewModel.heightProperty().set("2");
        viewModel.widthProperty().set("2");
        viewModel.tableProperty().set("..*\n..");
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void getNextGenerationButtonIsDisabledInitially() {
        assertTrue(viewModel.gettingNextGenerationDisabledProperty().get());
    }

    @Test
    public void getNextGenerationButtonIsDisabledWhenFormatIsBad() {
        viewModel.heightProperty().set("2");
        viewModel.widthProperty().set("g");
        viewModel.tableProperty().set("..*\n..");
        assertTrue(viewModel.gettingNextGenerationDisabledProperty().get());
    }

    @Test
    public void getNextGenerationButtonIsDisabledWithIncompleteInput() {
        viewModel.heightProperty().set("4");
        assertTrue(viewModel.gettingNextGenerationDisabledProperty().get());
    }

    @Test
    public void getNextGenerationButtonIsDisabledWhenTableIsBad() {
        viewModel.heightProperty().set("2");
        viewModel.widthProperty().set("3");
        viewModel.tableProperty().set("..*\n..");
        assertTrue(viewModel.gettingNextGenerationDisabledProperty().get());
    }

    @Test
    public void getNextDenerationButtonIsEnabledWithCorrectInput() {
        viewModel.heightProperty().set("2");
        viewModel.widthProperty().set("4");
        viewModel.tableProperty().set("....\n.*..");
        assertFalse(viewModel.gettingNextGenerationDisabledProperty().get());
    }

    @Test
    public void canTranslateTableToInputArray() {
        viewModel.heightProperty().set("4");
        viewModel.widthProperty().set("2");
        viewModel.tableProperty().set("..\n*.\n.*\n..");
        String[] input = {"2 4", "..", "*.", ".*", ".."};
        assertArrayEquals(input, viewModel.translateTableToInputArray());
    }

    @Test
    public void gettingSimpleNextGenerationIsCorrect() {
        viewModel.heightProperty().set("3");
        viewModel.widthProperty().set("2");
        viewModel.tableProperty().set("..\n*.\n..");
        viewModel.getNextGeneration();
        String output = "..\n..\n..";
        assertEquals(output, viewModel.resultProperty().get());
    }

    @Test
    public void gettingNextGenerationIsCorrect() {
        viewModel.heightProperty().set("6");
        viewModel.widthProperty().set("9");
        viewModel.tableProperty().set("*..**..**\n.......**\n*...**..."
                + "\n....**..*\n**.......\n**..**..*");
        viewModel.getNextGeneration();
        String output = ".......**\n...******\n....*****\n**..**...\n**..**...\n**.......";
        assertEquals(output, viewModel.resultProperty().get());
    }

    @Test
    public void canSetDefaultTable() {
        viewModel.heightProperty().set("3");
        viewModel.widthProperty().set("4");
        viewModel.setDefaultTable();
        assertEquals("....\n....\n....", viewModel.tableProperty().get());
    }

    @Test
    public void settingDefaultTableIsDisabledWhenNotEnoughData() {
        viewModel.heightProperty().set("3");
        assertTrue(viewModel.settingDefaultTableDisabledProperty().get());
    }

    @Test
    public void canSetPreviousGeneration() {
        viewModel.heightProperty().set("3");
        viewModel.widthProperty().set("4");
        viewModel.tableProperty().set("..**\n.*..\n.**.");
        viewModel.getNextGeneration();
        viewModel.setPreviousGeneration();
        assertEquals("....\n.*.*\n....", viewModel.tableProperty().get());
    }
}
