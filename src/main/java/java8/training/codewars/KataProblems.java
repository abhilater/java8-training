package java8.training.codewars;

import java.util.Arrays;
import java.util.Map;
import java.util.function.IntPredicate;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class KataProblems {

    public static Map<Character, Long> characterFrequenceyMap(String text) {
        return text.codePoints()
                .map(Character::toLowerCase)
                .mapToObj(c -> (char) c)
                .collect(groupingBy(identity(), counting()));
    }

    public static int duplicateCount(String text) {
        return (int) characterFrequenceyMap(text)
                .entrySet()
                .stream()
                .filter(e -> e.getValue() > 1)
                .count();
    }

    public static String SongDecoder(String song) {
        return song.replaceAll("WUB", " ")
                .replaceAll("( )+", " ")
                .trim();
    }

    public static String SongDecoderV1(String song) {
        return song.replaceAll("(WUB)+", " ").trim();
    }

    //FindOutlier https://www.codewars.com/kata/find-the-parity-outlier/train/java
    static int find(int[] integers) {
        IntPredicate isEven = i -> i % 2 == 0;
        boolean even = false;
        // case 1: first 2 are even
        if (isEven.test(integers[0]) && isEven.test(integers[1]))
            even = true;
            // case 2: first 2 are odd
        else if (!isEven.test(integers[0]) && !isEven.test(integers[1]))
            even = false;
            // case 3: first is outlier
        else if (isEven.test(integers[0]) && !isEven.test(integers[1]) && !isEven.test(integers[2]))
            return integers[0];
        else if (!isEven.test(integers[0]) && isEven.test(integers[1]) && isEven.test(integers[2]))
            return integers[0];
            // case 4: 2nd is outlier
        else if (isEven.test(integers[0]) && !isEven.test(integers[1]) && isEven.test(integers[2]))
            return integers[1];
        else if (!isEven.test(integers[0]) && isEven.test(integers[1]) && !isEven.test(integers[2]))
            return integers[1];

        IntPredicate p = even ? isEven.negate() : isEven;
        return Arrays.stream(integers)
                .parallel()
                .filter(p)
                .findFirst().getAsInt();
    }

    public static int findV2(int[] integers) {
        // Since we are warned the array may be very large, we should avoid counting values any more than we need to.

        // We only need the first 3 integers to determine whether we are chasing odds or evens.
        // So, take the first 3 integers and compute the value of Math.abs(i) % 2 on each of them.
        // It will be 0 for even numbers and 1 for odd numbers.
        // Now, add them. If sum is 0 or 1, then we are chasing odds. If sum is 2 or 3, then we are chasing evens.
        int sum = Arrays.stream(integers).limit(3).map(i -> Math.abs(i) % 2).sum();
        int mod = (sum == 0 || sum == 1) ? 1 : 0;

        return Arrays.stream(integers).parallel() // call parallel to get as much bang for the buck on a "large" array
                .filter(n -> Math.abs(n) % 2 == mod).findFirst().getAsInt();
    }
}
