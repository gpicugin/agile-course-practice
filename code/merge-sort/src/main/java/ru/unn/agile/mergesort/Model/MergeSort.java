package ru.unn.agile.mergesort.Model;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public final class MergeSort {

    public static <T> Collection<T> sort(final Collection<T> values,
                                         final Comparator<T> comparator) {
        return mergeSort(values, comparator);
    }

    public static <T extends Comparable<T>> Collection<T> sort(final Collection<T> values) {

        class Precede<T extends Comparable<T>> implements Comparator<T> {
           public int compare(final T a, final T b) {
                return a.compareTo(b);
            }
        }
        return mergeSort(values, new Precede<T>());
    }

    private static <T> Collection<T> mergeSort(final Collection<T> input,
                                               final Comparator<T> comparator) {
        List<T> inputList = new LinkedList<T>(input);
        return mergeSortList(inputList, comparator);
    }

    private static <T> List<T> mergeSortList(final List<T> input,
                                             final Comparator<T> comparator) {
        if (input.size() < 2) {
            return input;
        }
        int middleIndex = input.size() / 2;
        List<T> firstHalf = new LinkedList<T>(input.subList(0, middleIndex));
        List<T> secondHalf = new LinkedList<T>(input.subList(middleIndex, input.size()));
        List<T> sortedFirstHalf = mergeSortList(firstHalf, comparator);
        List<T> sortedSecondHalf = mergeSortList(secondHalf, comparator);
        return merge(sortedFirstHalf, sortedSecondHalf, comparator);
    }

    private static <T> List<T> merge(final List<T> first, final List<T> second,
                                     final Comparator<T> comparator) {
        int firstIndex = 0;
        int secondIndex = 0;
        List<T> result = new LinkedList<T>();
        while (firstIndex < first.size() && secondIndex < second.size()) {
            if (comparator.compare(second.get(secondIndex), first.get(firstIndex)) < 0) {
                result.add(second.get(secondIndex++));
            } else {
                result.add(first.get(firstIndex++));
            }
        }
        while (firstIndex < first.size()) {
            result.add(first.get(firstIndex++));
        }
        while (secondIndex < second.size()) {
            result.add(second.get(secondIndex++));
        }
        return result;
    }
    private MergeSort() {
    }

}
