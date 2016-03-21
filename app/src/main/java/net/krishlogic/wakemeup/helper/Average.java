package net.krishlogic.wakemeup.helper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kvenkat on 3/19/16.
 */
public class Average {

    private final int NUMBER_LIMIT = 50000;

    private int higherAverage = 0;
    private int lowerAverage = 0;
    private int midAverage = 0;

    private List<Integer> lowerNumbers = new ArrayList<>(NUMBER_LIMIT);
    private List<Integer> midNumbers = new ArrayList<>(NUMBER_LIMIT);
    private List<Integer> higherNumbers = new ArrayList<>(NUMBER_LIMIT);

    private static Average mInstance = null;

    private Average() {}

    public static Average getInstance() {
        if (mInstance == null) {
            mInstance = new Average();
        }

        return mInstance;
    }

    public int getHigherAverage() {
        return higherAverage;
    }

    public int getLowerAverage() {
        return lowerAverage;
    }

    public int getMidAverage() {
        return midAverage;
    }

    public void resetHigherAverage() {
        higherAverage = 0;
        higherNumbers = new ArrayList(NUMBER_LIMIT);
    }

    public void resetLowerAverage() {
        lowerAverage = 0;
        lowerNumbers = new ArrayList(NUMBER_LIMIT);
    }

    public void resetMidAverage() {
        midAverage = 0;
        midNumbers = new ArrayList(NUMBER_LIMIT);
    }

    public void addLowerNumbers(int number) {

        if (number != 0 || lowerNumbers.size() != NUMBER_LIMIT) {
            lowerNumbers.add(number);

        }
    }

    private void calculateLowerAvg() {

        int num = 0;

        for (int number : lowerNumbers) {
            num = num + number;
        }

        int temp = (num / lowerNumbers.size());

        if (temp != 0) {
            lowerAverage = temp;
        }
    }
}
