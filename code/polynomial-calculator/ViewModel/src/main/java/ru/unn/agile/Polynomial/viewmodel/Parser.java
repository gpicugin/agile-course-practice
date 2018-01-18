package ru.unn.agile.Polynomial.viewmodel;

import ru.unn.agile.Polynomial.model.Polynomial;

public final class Parser {
    public static Polynomial getPolynomial(final String txt) {
        if (!isValidText(txt)) {
            return null;
        }
        String[] coefficientsAndDegrees = getCoefficientsAndDegrees(txt);
        Polynomial polynomial = new Polynomial();
        try {
            for (int i = 0; i < coefficientsAndDegrees.length; i += 2) {
                if (coefficientsAndDegrees[i].charAt(0) != '+'
                        && coefficientsAndDegrees[i].charAt(0) != '-') {
                    throw new IllegalArgumentException("Invalid sign between monomials!");
                }
                Double coefficient = Double.parseDouble(coefficientsAndDegrees[i]);
                Integer degree = Integer.parseInt(coefficientsAndDegrees[i + 1]);
                polynomial.addMonomial(degree, coefficient);
            }
        } catch (Exception ex) {
            polynomial = null;
        }
        return polynomial;
    }

    private static String removeWhitespacesAroundSign(final String txt) {
        String result = txt.replaceAll("\\)\\s\\+\\s", ")+");
        result = result.replaceAll("\\)\\s\\-\\s", ")-");
        return result;
    }

    private static boolean isValidText(final String txt) {
        String changedText;
        boolean result = !txt.isEmpty();
        if (result) {
            changedText = txt.replaceAll("\\)\\d", "");
            result = (txt.length() == changedText.length());
        }
        if (result) {
            changedText = removeWhitespacesAroundSign(txt);
            result = (changedText.replaceAll("[0-9.x\\+\\-\\^\\(\\)]", "").isEmpty());
        }
        if (result) {
            changedText = txt.replaceAll("x\\^\\(", "a");
            result = (changedText.replaceAll("a", "aa").length()
                    == changedText.replaceAll("\\)", "aa").length());
        }
        return result;
    }

    private static String[] getCoefficientsAndDegrees(final String txt) {
        String strPolynomial = removeWhitespacesAroundSign(txt);
        if (strPolynomial.charAt(0) != '-') {
            strPolynomial = "+" + strPolynomial;
        }
        strPolynomial = strPolynomial.replaceAll("x\\^\\(", "a");
        strPolynomial = strPolynomial.replaceAll("\\)", "a");
        return strPolynomial.split("a");
    }

    private Parser() { }
}
