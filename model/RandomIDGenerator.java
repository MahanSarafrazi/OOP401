package model;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

public class RandomIDGenerator implements Iterator<Integer> {
    private Random random = new Random();
    private Integer lastNumber ;
    public Integer getLastNumber() {
        for (Integer x : generated)
            lastNumber = x;
        return lastNumber;
    }
    public static int getSize() {return generated.size();}
    private static Set<Integer> generated = new LinkedHashSet<>();

    public RandomIDGenerator(int size) {
        while (generated.size() < size + 1) {
            Integer next = random.nextInt(899999)+100000;
            System.out.println(next);
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
