package ru.unn.agile.PrimeNumber.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.sqrt;

public class PrimeNumber {
    public enum Methods {
        ERATOSTHENES, SIMPLE
    }

    public final void setLim(final Integer left, final Integer right) {
        if (left < right) {
            this.leftLim = left;
            this.rightLim = right;
        } else {
            this.leftLim = right;
            this.rightLim = left;
        }

        if (this.leftLim < 1) {
            this.leftLim = 1;
        }
    }


    public PrimeNumber(final Integer left, final Integer right) {
        this.setLim(left, right);

        this.primeNum = new ArrayList<Integer>();
    }

    public void findPrimeNumberFromRange(final Methods method) {
        if (method == Methods.ERATOSTHENES) {
            findPrimeNumberFromRangeEratosthenes();
        }
        if (method == Methods.SIMPLE) {
            findPrimeNumberFromRangeSimpleSearch();
        }
    }

    public Integer getLeftLim() {
        return this.leftLim;
    }

    public Integer getRightLim() {
        return this.rightLim;
    }

    public List<Integer> getPrimeList() {
        return this.primeNum;
    }

    private boolean[] eratosthenes(final Integer num) {
        boolean[] isPrime = new boolean[num + 1];

        Arrays.fill(isPrime, true);
        isPrime[0] = false;
        isPrime[1] = false;

        Integer limitNum = (Integer) (int) (sqrt(num + 1));

        for (Integer divider = 2; divider <= limitNum; divider++) {
            if (isPrime[divider]) {
                for (Integer dividend = 2 * divider; dividend <= num; dividend += divider) {
                    isPrime[dividend] = false;

                    if ((Integer.MAX_VALUE - dividend) < divider) {
                        break;
                    }
                }
            }
        }

        return isPrime;
    }

    private boolean isPrime(final Integer num) {
        if (num < 2) {
            return false;
        }

        Integer limitNum = (Integer) (int) (sqrt(num + 1));

        for (Integer divider = 2; divider <= limitNum; divider++) {
            if (num % divider == 0) {
                return false;
            }
        }

        return true;
    }

    private void findPrimeNumberFromRangeSimpleSearch() {
        this.primeNum.clear();

        for (Integer testNum = this.leftLim; testNum <= this.rightLim; testNum++) {
            if (isPrime(testNum)) {
                this.primeNum.add(testNum);
            }

            if (testNum == Integer.MAX_VALUE) {
                break;
            }
        }
    }

    private void findPrimeNumberFromRangeEratosthenes() {

        if (rightLim > 0) {

            this.primeNum.clear();
            boolean[] isPrime = eratosthenes(this.rightLim);

            for (Integer testNum = this.leftLim; testNum <= this.rightLim; testNum++) {
                if (isPrime[testNum]) {
                    this.primeNum.add(testNum);
                }

                if (testNum == Integer.MAX_VALUE) {
                    break;
                }
            }
        }
    }

    private Integer leftLim;
    private Integer rightLim;

    private List<Integer> primeNum;

}
