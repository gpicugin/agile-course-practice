package ru.unn.agile.MatrixClass.viewmodel;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import ru.unn.agile.ClassMatrix.Model.Matrix;

public class ViewModel {

    public ViewModel() {
        matrixSize.set("");
        determinant.set("");
        fieldForMatrix.set("");
        status.set(Status.WAITING.toString());

        BooleanBinding couldSetMatrixSize = new BooleanBinding() {
            {
                super.bind(matrixSize, fieldForMatrix);
            }

            @Override
            protected boolean computeValue() {
                return getInputStatus() == Status.READY;
            }
        };
        calculateButtonDisabled.bind(couldSetMatrixSize.not());

        final List<StringProperty> fields = new ArrayList<StringProperty>() {
            {
                add(matrixSize);
                add(fieldForMatrix);
            }
        };
        for (StringProperty field : fields) {
            final ValueChangeListener listener = new ValueChangeListener();
            field.addListener(listener);
            valueChangedListeners.add(listener);
        }
    }

    public StringProperty statusProperty() {
        return status;
    }

    public StringProperty determinantProperty() {
        return determinant;
    }

    public BooleanProperty calculateButtonDisabledProperty() {
        return calculateButtonDisabled;
    }

    public StringProperty matrixSizeProperty() {
        return matrixSize;
    }

    public StringProperty fieldForMatrixProperty() {
        return fieldForMatrix;
    }

    public final String getInputMatrixSize() {
        return matrixSize.get();
    }

    public final String getDeterminant() {
        return determinant.get();
    }

    public final String getFieldForMatrix() {
        return fieldForMatrix.get();
    }

    public final String getStatus() {
        return status.get();
    }

    public final void setMatrixSize(final String inputNumber) {
        matrixSize.set(inputNumber);
    }

    public final void setFieldForMatrix(final String inputNumber) {
        fieldForMatrix.set(inputNumber);
    }

    public final boolean isCalculateButtonDisabled() {
        return calculateButtonDisabled.get();
    }

    public void calculate() {
        if (isCalculateButtonDisabled()) {
            return;
        }
        int inputSize = Integer.parseInt(getInputMatrixSize());
        float[][] matrixA = new float[inputSize][inputSize];
        String inputMatrix = getFieldForMatrix().toString();
        String[] mas = inputMatrix.split("/");
        for (int i = 0; i < mas.length; i++) {
            String[] rowMatrix = mas[i].split(",");
            for (int j = 0; j < inputSize; j++) {
                matrixA[i][j] = Float.parseFloat(rowMatrix[j]);
            }
        }
        Matrix matrix = new Matrix(matrixA);
        determinant.set(Float.toString(matrix.calculateDeterminant()));
        status.set(Status.SUCCESS.toString());
    }

    private class ValueChangeListener implements ChangeListener<String> {
        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String oldValue, final String newValue) {
            status.set(getInputStatus().toString());
            determinant.set("");
        }
    }

    private Status getInputStatus() {
        Status inputStatus = Status.READY;
        if (getInputMatrixSize().isEmpty()) {
            inputStatus = Status.WAITING;
        } else {
            try {
                int inputSize = Integer.parseInt(getInputMatrixSize());
                if ((inputSize <= 1)) {
                    return Status.BAD_FORMAT;
                }
                if ((getFieldForMatrix().isEmpty())) {
                    return Status.WAITING_MATRIX;
                } else {
                    String inputMatrix = getFieldForMatrix().toString();
                    String[] mas = inputMatrix.split("/");
                    for (int i = 0; i < mas.length; i++) {
                        if (mas[i].equals("")) {
                            return Status.BAD_FORMAT;
                        }
                    }
                    for (int i = 0; i < mas.length; i++) {
                        String[] rowMatrix = mas[i].split(",");
                        if ((rowMatrix.length != mas.length)
                                || (rowMatrix.length * mas.length != inputSize * inputSize)) {
                            return Status.BAD_FORMAT;
                        }
                        for (int j = 0; j < rowMatrix.length; j++) {
                            if (!rowMatrix[j].matches(("(^[-]?\\d+[.]\\d+$)|(^[-]?\\d+$)"))) {
                                return Status.BAD_FORMAT;
                            }
                        }
                    }
                }
            } catch (NumberFormatException nfe) {
                inputStatus = Status.BAD_FORMAT;
            }
        }
        return inputStatus;
    }

    private final StringProperty matrixSize = new SimpleStringProperty();
    private final StringProperty determinant = new SimpleStringProperty();
    private final StringProperty fieldForMatrix = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final BooleanProperty calculateButtonDisabled = new SimpleBooleanProperty();
    private final List<ValueChangeListener> valueChangedListeners = new ArrayList<>();
}
