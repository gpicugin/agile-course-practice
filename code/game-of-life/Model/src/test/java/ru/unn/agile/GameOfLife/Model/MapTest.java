package ru.unn.agile.GameOfLife.Model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

public class MapTest {

    @Test(expected = IllegalArgumentException.class)
    public void couldNotCreateMapWithNullArgument() {
        Map testMap = new Map(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void couldNotCreateMapWithIncorrectGrid() {
        byte[][] grid = {{}};
        Map testMap = new Map(grid);
    }

    @Test(expected = IllegalArgumentException.class)
    public void couldNotCreateMapWithIncorrectGridFormat() {
        byte[][] grid = {{0, 0}, {0, 0, 0}};
        Map testMap = new Map(grid);
    }

    @Test(expected = IllegalArgumentException.class)
    public void couldNotCreateMapWithIncorrectGridValues() {
        byte[][] grid = {{5, 0}, {0, 0}};
        Map testMap = new Map(grid);
    }

    @Test
    public void shouldCreateMapWithInputParameters() {
        byte[][] grid = {{1, 0, 0}, {0, 1, 0}};
        Map testMap = new Map(grid);
        assertArrayEquals(grid, testMap.getGrid());
    }

    @Test
    public void shouldCorrectlyCalculateSizeOfGrid() {
        byte[][] grid = {{1, 0, 0}, {0, 1, 0}};
        int[] gridSize = {2, 3};
        Map testMap = new Map(grid);
        assertArrayEquals(testMap.getSize(), gridSize);
    }

    @Test(expected = IllegalArgumentException.class)
    public void couldNotReturnAliveNeighborsCountForIncorrectCoordinates() {
        byte[][] grid = {{0}};
        Map testMap = new Map(grid);
        testMap.countAliveNeighbors(1, 1);
    }

    @Test
    public void shouldReturnZeroForDeadGrid() {
        byte[][] grid = {{0}};
        Map testMap = new Map(grid);
        assertEquals(0, testMap.countAliveNeighbors(0, 0));
    }

    @Test
    public void shouldReturnCorrectValueForUpperLeftPoint() {
        byte[][] grid = {{1, 1}, {1, 1}};
        Map testMap = new Map(grid);
        assertEquals(3, testMap.countAliveNeighbors(0, 0));
    }

    @Test
    public void shouldReturnCorrectValueForUpperRightPoint() {
        byte[][] grid = {{1, 1}, {1, 1}};
        Map testMap = new Map(grid);
        assertEquals(3, testMap.countAliveNeighbors(1, 0));
    }

    @Test
    public void shouldReturnCorrectValueForLowerLeftPoint() {
        byte[][] grid = {{1, 1}, {1, 1}};
        Map testMap = new Map(grid);
        assertEquals(3, testMap.countAliveNeighbors(0, 1));
    }

    @Test
    public void shouldReturnCorrectValueForLowerRightPoint() {
        byte[][] grid = {{1, 1}, {1, 1}};
        Map testMap = new Map(grid);
        assertEquals(3, testMap.countAliveNeighbors(1, 1));
    }

    @Test
    public void shouldReturnCorrectValueForPointWith8Neighbors() {
        byte[][] grid = {{1, 1, 1}, {1, 1, 1}, {1, 1, 1}};
        Map testMap = new Map(grid);
        assertEquals(testMap.countAliveNeighbors(1, 1), 8);
    }
}
