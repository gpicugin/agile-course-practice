package ru.unn.agile.RatioCalculator.Model;

public class Ratio {
    public Ratio() {
        init(0, 1);
    }

    public Ratio(final int numerator, final int denominator) {
        init(numerator, denominator);
    }

    public Ratio(final int wholeNumber) {
        init(wholeNumber, 1);
    }

    public int getDenominator() {
        return denominator;
    }

    public int getNumerator() {
        return numerator;
    }

    public int getWholePart() {
        return numerator / denominator;
    }

    @Override
    public int hashCode() {
        final int shift = 8;

        return (int) ((long) (numerator) >>> shift + denominator);
    }

    @Override
    public boolean equals(final Object otherRatio) {
        if (otherRatio instanceof Ratio) {
            Ratio other = (Ratio) otherRatio;
            return (numerator == other.numerator)
                    && (denominator == other.denominator);
        }
        if (otherRatio.getClass() == Integer.class) {
            return (numerator == (int) (otherRatio))
                    && (denominator == 1);
        }
        return false;
    }

    public int compareTo(final Ratio other) {
        if ((long) (numerator) * other.denominator < (long) (denominator) * other.numerator) {
            return -1;
        }
        if ((long) (numerator) * other.denominator > (long) (denominator) * other.numerator) {
            return 1;
        }
        return 0;
    }

    public Ratio add(final Ratio other) {
        int commonDenominator = leastCommonMultiple(denominator, other.denominator);
        long newNumerator = numerator * (commonDenominator / denominator)
                + other.numerator * (commonDenominator / other.denominator);
        return new Ratio((int) newNumerator, commonDenominator);
    }

    public Ratio add(final int number) {
        return add(new Ratio(number));
    }

    public Ratio negate() {
        return new Ratio(-numerator, denominator);
    }

    public Ratio sub(final Ratio other) {
        return add(other.negate());
    }

    public Ratio sub(final int number) {
        return sub(new Ratio(number));
    }

    public Ratio mult(final Ratio other) {
        int newNumerator = numerator * other.numerator;
        int newDenominator = denominator * other.denominator;
        return new Ratio(newNumerator, newDenominator);
    }

    public Ratio mult(final int number) {
        return mult(new Ratio(number));
    }

    public Ratio reverse() {
        return new Ratio(denominator, numerator);
    }

    public Ratio div(final Ratio other) {
        return mult(other.reverse());
    }

    public Ratio div(final int number) {
        return div(new Ratio(number));
    }
    public enum Operation {
        ADD("Add") {
            public Ratio apply(final Ratio left, final Ratio right) {
                return left.add(right);
            }
        },
        MULTIPLY("Mul") {
            public Ratio apply(final Ratio left, final Ratio right) {
                return left.mult(right);
            }
        },
        DIV("Div") {
            public Ratio apply(final Ratio left, final Ratio right) {
                return left.div(right);
            }
        },
        SUB("Subtraction") {
            public Ratio apply(final Ratio left, final Ratio right) {
                return left.sub(right);
            }
        };

        private final String name;
        Operation(final String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

        public abstract Ratio apply(Ratio left, Ratio right);
    }

    private void init(final int numerator, final int denominator) {
        if (denominator == 0) {
            throw new ArithmeticException("Divide by zero is forbidden!");
        }
        this.numerator = numerator;
        this.denominator = denominator;
        reduce();
    }

    private void reduce() {
        int gcdValue = greatestCommonDivisor(numerator, denominator);
        numerator = numerator / gcdValue;
        denominator = denominator / gcdValue;
        if (denominator < 0) {
            numerator *= -1;
            denominator *= -1;
        }
    }

    private int greatestCommonDivisor(final int first, final int second) {
        int bigNumber = first;
        int smallNumber = second;
        while (smallNumber != 0) {
            int residual = bigNumber % smallNumber;
            bigNumber = smallNumber;
            smallNumber = residual;
        }
        return bigNumber;
    }

    private int leastCommonMultiple(final int first, final int second) {
        return first / greatestCommonDivisor(first, second) * second;
    }

    private int numerator;
    private int denominator;
}
