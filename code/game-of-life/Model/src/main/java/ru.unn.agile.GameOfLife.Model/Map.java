package ru.unn.agile.GameOfLife.Model;

public class Map {
    public static final int AVAILABLE_STATES_COUNT = 2;

    public Map(final byte[][] grid) {
        if (grid == null) {
            throw new IllegalArgumentException("Argument cannot be null!");
        }
        if (grid.length < 1) {
            throw new IllegalArgumentException("Cannot create MAP: Empty grid!");
        }
        this.sizeX = grid.length;

        for (int x = 1; x < sizeX; x++) {
            if (grid[0].length != grid[x].length) {
                throw new IllegalArgumentException("Incorrect input value at line " + x + "!");
            }
        }
        if (grid[0].length < 1) {
            throw new IllegalArgumentException("Incorrect grid size!");
        }
        this.sizeY = grid[0].length;
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                if (!isValidState(grid[x][y])) {
                    throw new IllegalArgumentException("Grid has incorrect states!");
                }
            }
        }
        this.grid = grid;
    }

    public int[] getSize() {
        return new int[]{sizeX, sizeY};
    }

    public byte[][] getGrid() {
        return grid;
    }

    public int countAliveNeighbors(final int coordX, final int coordY) {
        if (coordX < sizeX && coordY < sizeY
                && coordX >= 0 && coordY >= 0) {
            return leftNeighbor(coordX, coordY) + rightNeighbor(coordX, coordY)
                    + upperNeighbor(coordX, coordY) + lowerNeighbor(coordX, coordY)
                    + leftUpperNeighbor(coordX, coordY) + rightUpperNeighbor(coordX, coordY)
                    + leftLowerNeighbor(coordX, coordY) + rightLowerNeighbor(coordX, coordY);
        } else {
            throw new IllegalArgumentException("Incorrect coordinates of point!");
        }
    }

    private int leftNeighbor(final int coordX, final int coordY) {
        return (coordX != 0) ? grid[coordX - 1][coordY] : 0;
    }

    private int rightNeighbor(final int coordX, final int coordY) {
        return (coordX != sizeX - 1) ? grid[coordX + 1][coordY] : 0;
    }

    private int upperNeighbor(final int coordX, final int coordY) {
        return (coordY != 0) ? grid[coordX][coordY - 1] : 0;
    }

    private int lowerNeighbor(final int coordX, final int coordY) {
        return (coordY != sizeY - 1) ? grid[coordX][coordY + 1] : 0;
    }

    private int leftUpperNeighbor(final int coordX, final int coordY) {
        return (coordX != 0 && coordY != 0)
                ? grid[coordX - 1][coordY - 1] : 0;
    }

    private int rightUpperNeighbor(final int coordX, final int coordY) {
        return (coordX != sizeX - 1 && coordY != 0)
                ? grid[coordX + 1][coordY - 1] : 0;
    }

    private int leftLowerNeighbor(final int coordX, final int coordY) {
        return (coordX != 0 && coordY != sizeY - 1)
                ? grid[coordX - 1][coordY + 1] : 0;
    }

    private int rightLowerNeighbor(final int coordX, final int coordY) {
        return (coordX != sizeX - 1 && coordY != sizeY - 1)
                ? grid[coordX + 1][coordY + 1] : 0;
    }

    private boolean isValidState(final byte checkState) {
        return (0 <= checkState && checkState < AVAILABLE_STATES_COUNT);
    }

    private int sizeX;
    private int sizeY;
    private byte[][] grid;
}
