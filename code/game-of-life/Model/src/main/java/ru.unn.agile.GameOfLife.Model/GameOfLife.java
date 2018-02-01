package ru.unn.agile.GameOfLife.Model;

public class GameOfLife {
    public void readCurrentGeneration(final String[] input) {
        if (input == null) {
            throw new IllegalArgumentException("Argument cannot be null!");
        }
        if (input.length < MINIMAL_INPUT_STRINGS_COUNT) {
            throw new IllegalArgumentException(
                    "Incorrect input value! Not enough count of input strings");
        }
        String[] sizeParse = input[0].split(" ");
        if (sizeParse.length != SIZE_TYPES_COUNT) {
            throw new IllegalArgumentException("Incorrect count of grid size parameters!");
        }

        int[] numArr = new int[SIZE_TYPES_COUNT];

        for (int i = 0; i < SIZE_TYPES_COUNT; i++) {
            numArr[i] = Integer.parseInt(sizeParse[i]);
        }

        final int sizeX = numArr[0];
        final int sizeY = numArr[1];

        if (sizeY != input.length - 1) {
            throw new IllegalArgumentException("Incorrect Y-axis size!");
        }

        byte[][] arrayForGrid = new byte[sizeX][sizeY];

        for (int y = 0; y < sizeY; y++) {
            if (input[y + 1].length() != sizeX) {
                throw new IllegalArgumentException("Incorrect X-axis size!");
            } else {
                for (int x = 0; x < sizeX; x++) {
                    arrayForGrid[x][y] = parseSymbolToState(input[y + 1].charAt(x));
                }
            }
        }

        currentGeneration = new Map(arrayForGrid);
    }

    public String[] writeNextGeneration() {
        byte[][] grid = getNextGeneration().getGrid();
        int gridSizeX = nextGeneration.getSize()[0];
        int gridSizeY = nextGeneration.getSize()[1];
        String[] output = new String[1 + gridSizeY];
        output[0] = String.format("%d %d", gridSizeX, gridSizeY);
        for (int y = 0; y < gridSizeY; y++) {
            output[1 + y] = "";
            for (int x = 0; x < gridSizeX; x++) {
                output[1 + y] = output[1 + y] + convertStateToSymbol(grid[x][y]);
            }
        }
        return output;
    }

    public void buildNextGeneration() {
        final int sizeX = getCurrentGeneration().getSize()[0];
        final int sizeY = getCurrentGeneration().getSize()[1];
        byte[][] gridForNexGen = new byte[sizeX][sizeY];
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                int aliveNeighbors = currentGeneration.countAliveNeighbors(x, y);
                gridForNexGen[x][y] = rulesForNetState(aliveNeighbors);
            }
        }
        nextGeneration = new Map(gridForNexGen);
    }

    public Map getCurrentGeneration() {
        if (currentGeneration == null) {
            throw new NullPointerException("Current generation was not set!");
        } else {
            return currentGeneration;
        }
    }

    public Map getNextGeneration() {
        if (nextGeneration == null) {
            throw new NullPointerException("Next generation was not calculated!");
        } else {
            return nextGeneration;
        }
    }

    private static byte rulesForNetState(final int aliveNeighborsCount) {
        if (aliveNeighborsCount >= MIN_ALIVE_NEIGHBORS
                && aliveNeighborsCount <= MAX_ALIVE_NEIGHBORS) {
            return 1;
        } else {
            return 0;
        }
    }

    private static byte parseSymbolToState(final int value) {
        int answer = STATES_IN_SYMBOL_FORMAT.indexOf(value);
        if (answer != -1) {
            return ((byte) answer);
        } else {
            throw new IllegalArgumentException("Incorrect argument '" + value + "'!");
        }
    }

    private static char convertStateToSymbol(final byte value) {
        if (STATES_IN_SYMBOL_FORMAT.length() >= Map.AVAILABLE_STATES_COUNT) {
            return STATES_IN_SYMBOL_FORMAT.charAt(value);
        } else {
            throw new IllegalArgumentException("Incorrect state value: '" + value + "'!");
        }
    }

    private static final String STATES_IN_SYMBOL_FORMAT = ".*";
    private static final int SIZE_TYPES_COUNT = 2;
    private static final int MINIMAL_INPUT_STRINGS_COUNT = 2;
    private static final int MIN_ALIVE_NEIGHBORS = 3;
    private static final int MAX_ALIVE_NEIGHBORS = 4;
    private Map currentGeneration;
    private Map nextGeneration;
}
