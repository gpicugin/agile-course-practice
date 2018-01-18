package ru.unn.agile.mergesort.Model;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class CarTest {
    @Test
    public void canCompareCarsWithSameMaxSpeed() {
        Car car = new Car(1, 1);
        Car other = new Car(1, 2);
        assertEquals(0, car.compareTo(other));
    }

    @Test
    public void canCompareCarsWithDifferentMaxSpeed() {
        Car car = new Car(777);
        Car other = new Car(888);
        assertEquals(-111, car.compareTo(other));
        assertEquals(111, other.compareTo(car));
    }

    @Test
    public void areEqualCarsEqual() {
        Car car = new Car(2, 11);
        Car other = new Car(2, 11);
        assertTrue(car.equals(other));
    }

    @Test
    public void areCarsWithDifferentMaxSpeedNotEqual() {
        Car car = new Car(2);
        Car other = new Car(77);
        assertFalse(car.equals(other));
    }

    @Test
    public void areCarsWithDifferentSerialEqual() {
        Car car = new Car(2, 8);
        Car other = new Car(2, 7);
        assertTrue(car.equals(other));
    }

    @Test
    public void areDifferentCarHaveDifferentHashCode() {
        Car car = new Car(2);
        Car other = new Car(77);
        assertFalse(car.hashCode() == other.hashCode());
    }

    @Test
    public void areEqualCarHaveEqualHashCode() {
        Car car = new Car(88);
        Car other = new Car(88);
        assertTrue(car.hashCode() == other.hashCode());
    }
}
