package ru.unn.agile.PrimeNumber.viewmodel;

public class Query {
    private String shortMessage;
    private String answerMessage;
    Query() {
        shortMessage = "";
        answerMessage = "";
    }

    Query(final String shortMessage, final String answerMessage) {
        this.shortMessage = shortMessage;
        this.answerMessage = answerMessage;
    }

    public String getShortMessage() {
        return shortMessage;
    }
    public String getAnswerMessage() {
        return answerMessage;
    }

    @Override
    public String toString() {
        return shortMessage;
    }

    @Override
    public boolean equals(final Object object) {
        if (!(object instanceof Query)) {
            return false;
        }
        Query query = (Query) object;
        return query.shortMessage.equals(this.shortMessage);
    }

    @Override
    public int hashCode() {
        return shortMessage.hashCode();
    }
}
