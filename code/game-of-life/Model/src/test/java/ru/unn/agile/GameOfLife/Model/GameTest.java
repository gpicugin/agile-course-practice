package ru.unn.agile.GameOfLife.Model;

import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;

public class GameTest {
    @Test(expected = IllegalArgumentException.class)
    public void couldNotWorkWithNullInput() {
        GameOfLife testGame = new GameOfLife();
        testGame.readCurrentGeneration(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void couldNotWorkWithIncorrectInput() {
        String[] inputArray = {"", ""};
        GameOfLife testGame = new GameOfLife();
        testGame.readCurrentGeneration(inputArray);
    }

    @Test(expected = IllegalArgumentException.class)
    public void couldNotWorkWithIncorrectMapSizeByYValue() {
        String[] inputArray = {"1 4", ".", "."};
        GameOfLife testGame = new GameOfLife();
        testGame.readCurrentGeneration(inputArray);
    }

    @Test(expected = IllegalArgumentException.class)
    public void couldNotWorkWithIncorrectMapSizeByXValue() {
        String[] inputArray = {"2 3", ".", ".", "."};
        GameOfLife testGame = new GameOfLife();
        testGame.readCurrentGeneration(inputArray);
    }

    @Test(expected = IllegalArgumentException.class)
    public void couldNotWorkWithIncorrectStringLength() {
        String[] inputArray = {"2 3", "..", "...", ".."};
        GameOfLife testGame = new GameOfLife();
        testGame.readCurrentGeneration(inputArray);
    }

    @Test
    public void shouldCorrectlyParseInputGridSize() {
        String[] inputArray = {"2 3", "..", "..", ".."};
        int[] gridSize = new int[]{2, 3};
        GameOfLife testGame = new GameOfLife();
        testGame.readCurrentGeneration(inputArray);
        assertArrayEquals(testGame.getCurrentGeneration().getSize(), gridSize);
    }

    @Test
    public void shouldParseCorrectInput() {
        String[] inputArray = {"2 3", "..", "..", ".."};
        byte[][] correctGrid = {{0, 0, 0}, {0, 0, 0}};
        GameOfLife testGame = new GameOfLife();
        testGame.readCurrentGeneration(inputArray);
        assertArrayEquals(testGame.getCurrentGeneration().getGrid(), correctGrid);
    }

    @Test(expected = NullPointerException.class)
    public void couldNotReturnNotCreatedNextGeneration() {
        GameOfLife testGame = new GameOfLife();
        testGame.getNextGeneration();
    }

    @Test
    public void shouldCreateNextGenerationWithCorrectSize() {
        String[] inputArray = {"2 3", "..", "*.", ".."};
        int[] gridSize = new int[]{2, 3};
        GameOfLife testGame = new GameOfLife();
        testGame.readCurrentGeneration(inputArray);
        testGame.buildNextGeneration();
        assertArrayEquals(testGame.getCurrentGeneration().getSize(), gridSize);
    }

    @Test
    public void shouldCreateCorrectNextGenerationGrid() {
        String[] inputArray = {"2 3", "..", "*.", ".."};
        byte[][] correctGrid = {{0, 0, 0}, {0, 0, 0}};
        GameOfLife testGame = new GameOfLife();
        testGame.readCurrentGeneration(inputArray);
        testGame.buildNextGeneration();
        assertArrayEquals(testGame.getNextGeneration().getGrid(), correctGrid);
    }

    @Test
    public void shouldCreateCorrectOutputValue() {
        String[] inputArray = {"2 3", "..", "*.", ".."};
        String[] correctOutputArray = {"2 3", "..", "..", ".."};
        GameOfLife testGame = new GameOfLife();
        testGame.readCurrentGeneration(inputArray);
        testGame.buildNextGeneration();
        String[] outputArray = testGame.writeNextGeneration();
        assertArrayEquals(correctOutputArray, outputArray);
    }

    @Test
    public void verifyThatItShouldBeCorrectResultFor9x6InputGrid() {
        String[] inputArray = {"9 6",
                "*..**..**",
                ".......**",
                "*...**...",
                "....**..*",
                "**.......",
                "**..**..*"};
        String[] correctOutputArray = {"9 6",
                ".......**",
                "...******",
                "....*****",
                "**..**...",
                "**..**...",
                "**......."};
        GameOfLife testGame = new GameOfLife();
        testGame.readCurrentGeneration(inputArray);
        testGame.buildNextGeneration();
        assertArrayEquals(correctOutputArray, testGame.writeNextGeneration());
    }
}
