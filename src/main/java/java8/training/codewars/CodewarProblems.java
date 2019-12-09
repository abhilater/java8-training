package java8.training.codewars;

import java.util.Arrays;
import java.util.Map;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;

public class CodewarProblems {

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

    // FindOutlier https://www.codewars.com/kata/find-the-parity-outlier/train/java
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
        if (isEven.test(integers[0]) && !isEven.test(integers[1]) && !isEven.test(integers[2]))
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

    public static boolean validatePin(String pin) {
        Predicate<String> validLenth = s -> s.length() == 6 || s.length() == 4;
        return validLenth.test(pin) &&
                pin.codePoints().mapToObj(c -> (char) c)
                        .allMatch(Character::isDigit);
    }

    /**
     * toCamelCase("the-stealth-warrior"); // returns "theStealthWarrior"
     * toCamelCase("The_Stealth_Warrior"); // returns "TheStealthWarrior"
     *
     * @param s https://www.codewars.com/kata/convert-string-to-camel-case/train/java
     * @return
     */
    static String toCamelCase(String s) {
        String[] tokens = s.split("[-_]");
        String first = tokens[0];
        String collect = Arrays.stream(Arrays.copyOfRange(tokens, 1, tokens.length))
                .map(str -> str.substring(0, 1).toUpperCase() + str.substring(1))
                .collect(joining());
        return first + collect;
    }

    /**
     * 5 -> " *\n***\n *\n"
     *
     * @param n https://www.codewars.com/kata/give-me-a-diamond/train/java
     * @return
     */
    public static String diamond(int n) {
        if (n < 0 || n % 2 == 0) return null;
        StringBuilder sb = new StringBuilder();
        return IntStream.range(0, n)
                .mapToObj(i -> createRow(n, i))
                .collect(joining());
    }

    // 0 -> 2 - 0 : 2
    // 1 -> 2 - 1 : 1
    // 2 -> 2 - 2 : 0
    // 3 -> 2 - 3 : 1
    // 4 -> 2 - 4 : 2
    static String createRow(int n, int i) {
        String spaceStr = IntStream.range(0, Math.abs((n / 2) - i))
                .mapToObj(idx -> " ")
                .collect(joining());
        String starStr = IntStream.range(0, n - 2 * (Math.abs((n / 2) - i)))
                .mapToObj(idx -> "*")
                .collect(joining());
        return spaceStr + starStr + "\n";
    }

}
