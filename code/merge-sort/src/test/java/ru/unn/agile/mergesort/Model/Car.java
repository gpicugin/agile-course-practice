package ru.unn.agile.mergesort.Model;

class Car implements Comparable<Car> {

    Car(final int theMaxSpeed) {
        init(theMaxSpeed, 0);
    }

    Car(final int theMaxSpeed, final int theSerialNumber) {
        init(theMaxSpeed, theSerialNumber);
    }

    void init(final int theMaxSpeed, final int theSerialNumber) {
        maxSpeed = theMaxSpeed;
        serialNumber = theSerialNumber;
    }

    public int compareTo(final Car other) {
        return maxSpeed  - other.maxSpeed;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Car car = (Car) o;

        return maxSpeed == car.maxSpeed;
    }

    @Override
    public int hashCode() {
        int result = maxSpeed;
        result = 31 * result + serialNumber;
        return result;
    }

    private int maxSpeed;
    private int serialNumber;
}
