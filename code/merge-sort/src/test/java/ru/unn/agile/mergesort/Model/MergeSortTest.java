package ru.unn.agile.mergesort.Model;

import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import static org.junit.Assert.assertEquals;


public class MergeSortTest {
    @Test
    public void canSortEmptyCollection() {
        Collection<Integer> input = new ArrayList<Integer>();
        Collection<Integer> expected = new ArrayList<Integer>();
        Collection<Integer> actual = MergeSort.sort(input);
        assertEquals(expected, actual);
    }

    @Test
    public void canSortSingleValue() {
        Collection<Integer> input = Collections.singletonList(1);
        Collection<Integer> expected = input;
        Collection<Integer> actual = MergeSort.sort(input);
        assertEquals(expected, actual);
    }

    @Test
    public void canSortTwoValuesInteger() {
        Collection<Integer> input = Arrays.asList(2, 1);
        Collection<Integer> expected = Arrays.asList(1, 2);
        Collection<Integer> actual = MergeSort.sort(input);
        assertEquals(expected, actual);
    }

    @Test
    public void canSortMultipleValuesDouble() {
        Collection<Double> input = Arrays.asList(1.33, 11.88, 56.5, 9.9,
                -2.0, 18.0, -1.1, 3.0, 4.5);
        Collection<Double> expected = Arrays.asList(-2.0, -1.1, 1.33, 3.0, 4.5,
                9.9, 11.88, 18.0, 56.5);
        Collection<Double> actual = MergeSort.sort(input);
        assertEquals(expected, actual);
    }

    @Test
    public void canSortMultipleValuesArrayTwoDegreeSizeInt() {
        Collection<Integer> input = Arrays.asList(1, 11, 56, 999, -2, 18, -1999, 3,
                4, 15, 24, 87, 13, 44, 77, 100);
        Collection<Integer> expected = Arrays.asList(-1999, -2, 1, 3, 4, 11, 13, 15,
                18, 24, 44, 56, 77, 87,  100, 999);
        Collection<Integer> actual = MergeSort.sort(input);
        assertEquals(expected, actual);
    }

    @Test
    public void canSortMultipleValuesString() {
        Collection<String> input  = Arrays.asList("coffee", "wine", "juice", "vodka");
        Collection<String> expected  = Arrays.asList("coffee", "juice", "vodka", "wine");
        Collection<String> actual = MergeSort.sort(input);
        assertEquals(expected, actual);
    }

    @Test
    public void canSortcustomTypeTwoValuesComparator() {
        Collection<Car> input = Arrays.asList(new Car(400), new Car(30));
        Collection<Car> expected = Arrays.asList(new Car(30), new Car(400));
        Collection<Car> sortValues = MergeSort.sort(input);
        assertEquals(expected, sortValues);
    }

    @Test
    public void canSortcustomTypeMultipleValues() {
        Collection<Car> input = Arrays.asList(new Car(50), new Car(1),
                new Car(400), new Car(30),
                new Car(13), new Car(777));

        Collection<Car> expected = Arrays.asList(new Car(1), new Car(13),
                new Car(30), new Car(50), new Car(400),
                new Car(777));
        Collection<Car> sortValues = MergeSort.sort(input);
        assertEquals(expected, sortValues);
    }

    @Test
    public void isSortStableWithCustomType() {
        Collection<Car> input = Arrays.asList(new Car(3440, 1),
                new Car(40, 2), new Car(40, 3),
                new Car(1, 1), new Car(13, 8),
                new Car(777, 7), new Car(40, 4),
                new Car(40, 5));

        Collection<Car> expected = Arrays.asList(new Car(1, 1),
                new Car(13, 8),
                new Car(40, 2), new Car(40, 3),
                new Car(40, 4), new Car(40, 5),
                new Car(777, 7), new Car(3440, 1));
        Collection<Car> sortValues = MergeSort.sort(input);
        assertEquals(expected, sortValues);
    }

    @Test
    public void canSortEmptyCollectionWithComparator() {
        Collection<Integer> input = new ArrayList<Integer>();
        Collection<Integer> expected = new ArrayList<Integer>();
        Collection<Integer> actual = MergeSort.sort(input, Collections.reverseOrder());
        assertEquals(expected, actual);
    }

    @Test
    public void canSortSingleValueCollectionWithComparator() {
        Collection<Integer> input = Collections.singletonList(7);
        Collection<Integer> expected = input;
        Collection<Integer> actual = MergeSort.sort(input, Collections.reverseOrder());
        assertEquals(expected, actual);
    }

    @Test
    public void canSortTwoValuesIntCollectionWithComparator() {
        Collection<Integer> input = Arrays.asList(77, 88);
        Collection<Integer> expected = Arrays.asList(88, 77);
        Collection<Integer> actual = MergeSort.sort(input, Collections.reverseOrder());
        assertEquals(expected, actual);
    }

    @Test
    public void canSortMultipleValuesIntCollectionWithComparator() {
        Collection<Integer> input = Arrays.asList(1, 11, 6, -7, -2, 18, 1999, 3, 4);
        Collection<Integer> expected = Arrays.asList(1999, 18, 11, 6, 4, 3, 1, -2, -7);
        Collection<Integer> actual = MergeSort.sort(input, Collections.reverseOrder());
        assertEquals(expected, actual);
    }

    @Test
    public void canSortMultipleValuesTwoDegreeSizeCollectionWithComprator() {
        Collection<Integer> input = Arrays.asList(1, 44, 56, 999, -2, 110, 20,
                3, 4, 8, 24, 87, 13, -400, 77, 1200);
        Collection<Integer> expected = Arrays.asList(1200, 999, 110, 87, 77,
                56, 44, 24, 20, 13, 8, 4, 3, 1, -2, -400);
        Collection<Integer> actual = MergeSort.sort(input, Collections.reverseOrder());
        assertEquals(expected, actual);
    }

    @Test
    public void canSortMultipleValuesDoubleCollectionWithComparator() {
        Collection<Double> input = Arrays.asList(5.1, 12.3, 88.2, 54.01, 9., -8.99, 2., 3.9, 4.8);
        Collection<Double> expected = Arrays.asList(88.2, 54.01, 12.3,
                9.0, 5.1, 4.8, 3.9, 2.0, -8.99);
        Collection<Double> actual = MergeSort.sort(input, Collections.reverseOrder());
        assertEquals(expected, actual);
    }

    @Test
    public void canSortMultipleValuesStringCollectionWithComparator() {
        Collection<String> input  = Arrays.asList("red", "Alabaster", "Apricot", "Crystal");
        Collection<String> expected  = Arrays.asList("red", "Crystal", "Apricot", "Alabaster");
        Collection<String> actual = MergeSort.sort(input, Collections.reverseOrder());
        assertEquals(expected, actual);
    }

    @Test
    public void canSortCustomTypeTwoValuesCollectionWithComparator() {
        Collection<Car> input = Arrays.asList(new Car(404), new Car(56));
        Collection<Car> expected = Arrays.asList(new Car(404), new Car(56));
        Collection<Car> sortValues = MergeSort.sort(input, Collections.reverseOrder());
        assertEquals(expected, sortValues);
    }

    @Test
    public void canSortCustomTypeMultipleValuesWithComparator() {
        Collection<Car> input = Arrays.asList(new Car(55),
                new Car(50), new Car(0), new Car(18),
                new Car(1024));

        Collection<Car> expected = Arrays.asList(new Car(1024), new Car(55),
                new Car(50), new Car(18), new Car(0));

        Collection<Car> sortValues = MergeSort.sort(input, Collections.reverseOrder());
        assertEquals(expected, sortValues);
    }

    @Test
    public void sortIsStableForCustomTypeWithComparator() {
        Collection<Car> input = Arrays.asList(new Car(8840, 1111),
                new Car(11, 2), new Car(15, 3),
                new Car(15, 4), new Car(13, 800),
                new Car(619, 777), new Car(15, 5));
        Collection<Car> expected = Arrays.asList(new Car(8840, 1111),
                new Car(619, 777), new Car(15, 3),
                new Car(15, 4), new Car(15, 5), new Car(13, 800),
                new Car(11, 2));
        Collection<Car> sortValues = MergeSort.sort(input, Collections.reverseOrder());
        assertEquals(expected, sortValues);
    }
}
