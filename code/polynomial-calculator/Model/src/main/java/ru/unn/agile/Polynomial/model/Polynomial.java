package ru.unn.agile.Polynomial.model;

import java.util.Map;
import java.util.TreeMap;

public class Polynomial {
    public Polynomial() {
        coefficients = new TreeMap<Integer, Double>();
    }

    public Polynomial(final Map<Integer, Double> coefficients) {
        this.coefficients = coefficients;
    }

    public Polynomial(final Polynomial poly) {
        coefficients = poly.coefficients;
    }

    public Double getCoefficient(final Integer exponent) {
        if (coefficients.containsKey(exponent)) {
            return coefficients.get(exponent);
        } else {
            return 0.0;
        }
    }

    @Override
    public int hashCode() {
        return coefficients.hashCode();
    }

    @Override
    public boolean equals(final Object object) {
        if (!(object instanceof Polynomial)) {
            return false;
        }
        Polynomial poly = (Polynomial) object;
        Polynomial diff = poly.sub(this);
        for (Map.Entry<Integer, Double> entry : diff.coefficients.entrySet()) {
            if (entry.getValue() != 0) {
                return false;
            }
        }
        return true;
    }

    public void addMonomial(final Integer exponent, final Double addCoefficient) {
        Double coefficient = addCoefficient;
        if (coefficients.containsKey(exponent)) {
            coefficient = coefficients.get(exponent) + coefficient;
        }
        coefficients.put(exponent, coefficient);
    }

    public Polynomial add(final Polynomial poly) {
        Polynomial sum = new Polynomial(this);
        for (Map.Entry<Integer, Double> entry : poly.coefficients.entrySet()) {
            Integer key = entry.getKey();
            Double value = entry.getValue();
            sum.addMonomial(key, value);
        }
        return sum;
    }

    public void subMonomial(final Integer exponent, final Double addCoefficient) {
        addMonomial(exponent, -addCoefficient);
    }

    public Polynomial sub(final Polynomial poly) {
        Polynomial diff = new Polynomial(this);
        for (Map.Entry<Integer, Double> entry : poly.coefficients.entrySet()) {
            Integer key = entry.getKey();
            Double value = entry.getValue();
            diff.subMonomial(key, value);
        }
        return diff;
    }

    public Polynomial multiply(final Double mult) {
        Polynomial poly = new Polynomial(this);
        for (Map.Entry<Integer, Double> entry : poly.coefficients.entrySet()) {
            entry.setValue(entry.getValue() * mult);
        }
        return poly;
    }

    public Polynomial multiply(final Integer exponent, final Double mult) {
        Map<Integer, Double> newCoefficients = new TreeMap<Integer, Double>();
        for (Map.Entry<Integer, Double> entry : coefficients.entrySet()) {
            newCoefficients.put(entry.getKey() + exponent, entry.getValue() * mult);
        }
        return new Polynomial(newCoefficients);
    }

    public Polynomial multiply(final Polynomial poly) {
        Polynomial mult = new Polynomial();
        for (Map.Entry<Integer, Double> entry : coefficients.entrySet()) {
            mult.add(poly.multiply(entry.getKey(), entry.getValue()));
        }
        return mult;
    }

    @Override
    public String toString() {
        String result = "";
        for (Map.Entry<Integer, Double> entry: coefficients.entrySet()) {
            if (entry.getValue() != 0.0) {
                result += (entry.getValue() > 0.0) ? "+" : "";
                result += entry.getValue() + "x^(" + entry.getKey() + ")";
            }
        }
        if (result.isEmpty()) {
            result = String.valueOf(0.0);
        } else if (result.charAt(0) == '+') {
            result = result.substring(1, result.length());
        }
        return result;
    }

    private Map<Integer, Double> coefficients;
}
