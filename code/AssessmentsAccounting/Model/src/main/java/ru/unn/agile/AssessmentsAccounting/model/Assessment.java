package ru.unn.agile.AssessmentsAccounting.model;

public enum Assessment {
    EXCELLENT(5),
    GOOD(4),
    SATISFACTORY(3),
    UNSATISFACTORY(2),
    VERY_POOR(1);


    public int getAssessment() {
        return this.assessment;
    }

    Assessment(final int assessment) {
        this.assessment = assessment;
    }

    @Override
    public String toString() {
        return Integer.toString(this.assessment);
    }

    private final int assessment;
}
