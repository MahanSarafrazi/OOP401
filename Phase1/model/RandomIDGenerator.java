package Phase1.model;

import java.util.*;

public class RandomIDGenerator implements Iterator<Integer> {
    public Integer getLastNumber() {
        int lastNumber = 0;
        for (Integer x : generated)
            lastNumber = x;
        return lastNumber;
    }
    private static final Set<Integer> generated = new LinkedHashSet<>();

    public static Set<Integer> getGenerated() {return generated;}

    public RandomIDGenerator() {
        int newSize = generated.size() + 1;
        while (generated.size() < newSize) {
            Random random = new Random();
            Integer next = random.nextInt(899999)+100000;
            generated.add(next);
        }
    }

    @Override
    public boolean hasNext() {
        return false;
    }
    public Integer next() {
        Iterator<Integer> iterator = generated.iterator();
        Integer next = iterator.next();
        iterator.remove();
        return next;
    }
}