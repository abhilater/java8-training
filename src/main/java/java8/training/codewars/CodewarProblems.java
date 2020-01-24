package java8.training.codewars;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.stream.IntStream;

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
      if (isEven.test(integers[0]) && isEven.test(integers[1])) {
          even = true;
      }
      // case 2: first 2 are odd
      else if (!isEven.test(integers[0]) && !isEven.test(integers[1])) {
          even = false;
      }
    // case 3: first is outlier
      if (isEven.test(integers[0]) && !isEven.test(integers[1]) && !isEven.test(integers[2])) {
          return integers[0];
      } else if (!isEven.test(integers[0]) && isEven.test(integers[1]) && isEven
          .test(integers[2])) {
          return integers[0];
      }
      // case 4: 2nd is outlier
      else if (isEven.test(integers[0]) && !isEven.test(integers[1]) && isEven.test(integers[2])) {
          return integers[1];
      } else if (!isEven.test(integers[0]) && isEven.test(integers[1]) && !isEven
          .test(integers[2])) {
          return integers[1];
      }

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

    return Arrays.stream(integers)
        .parallel() // call parallel to get as much bang for the buck on a "large" array
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
      if (n < 0 || n % 2 == 0) {
          return null;
      }
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

  /**
   * Kata.expandedForm(12); # Should return "10 + 2" Kata.expandedForm(42); # Should return "40 + 2"
   * Kata.expandedForm(70304); # Should return "70000 + 300 + 4"
   *
   * @param num https://www.codewars.com/kata/5842df8ccbd22792a4000245/train/java
   * @return
   */
  public static String expandedForm(int num) {
    String[] digits = String.valueOf(num).split("");
    final List<String> collect = IntStream.range(0, digits.length)
        .mapToObj(i -> digits.length - (i + 1))
        .map(i -> new int[]{Integer.parseInt(digits[i]), digits.length - (i + 1)})
        .filter(d -> d[0] > 0)
        .map(d -> String.valueOf(d[0] * (int) Math.pow(10, d[1])))
        .collect(toList());
    Collections.reverse(collect);
    return collect.stream().collect(joining(" + "));
  }

  /**
   * Throw       Score ---------   ------------------ 5 1 3 4 1   50 + 2 * 100 = 250 1 1 1 3 1
   * 1000 + 100 = 1100 2 4 4 5 4   400 + 50 = 450
   *
   * @param dice https://www.codewars.com/kata/greed-is-good/train/java
   * @return
   */
  public static int greedy(int[] dice) {
    Map<Integer, Integer[]> points = new HashMap<Integer, Integer[]>() {{
      put(1, new Integer[]{1000, 100});
      put(6, new Integer[]{600, null});
      put(5, new Integer[]{500, 50});
      put(4, new Integer[]{400, null});
      put(3, new Integer[]{300, null});
      put(2, new Integer[]{200, null});
    }};
    return Arrays.stream(dice)
        .boxed()
        .collect(groupingBy(identity(), counting()))
        .entrySet().stream()
        .mapToInt(e -> calcPoints(points, e))
        .sum();

  }

  static int calcPoints(Map<Integer, Integer[]> points, Entry<Integer, Long> e) {
    Integer[] p = points.get(e.getKey());
    int result = 0;
    int c = e.getValue().intValue();
    int cMin3 = c - 3;
    if (cMin3 > 0) {
      result += p[0];
      result += p[1] == null ? 0 : cMin3 * p[1];
    } else if (cMin3 == 0) {
      result += p[0];
    } else {
      result = p[1] == null ? 0 : c * p[1];
    }
    return result;
  }

  /**
   * https://www.codewars.com/kata/555615a77ebc7c2c8a0000b8/train/java
   * Line.Tickets(new int[] {25, 25, 50}) // => YES
   * Line.Tickets(new int[]{25, 100}) // => NO. Vasya will not have enough money to give change to 100 dollars
   * Line.Tickets(new int[] {25, 25, 50, 50, 100}) // => NO. Vasya will not have the right bills to give 75 dollars of change (you can't make two bills of 25 from one of 50)
   */
  public static String Tickets(int[] peopleInLine) {
      int twenty5 = 0;
      int fifty = 0;
      for (int a : peopleInLine) {
          if (a == 25) {
              twenty5++;
          } else if (a == 50) {
              if (twenty5 >= 1) {
                  twenty5--;
                  fifty++;
              } else {
                  return "NO";
              }
          } else if (a == 100) {
              if (fifty >= 1 && twenty5 >= 1) {
                  fifty--;
                  twenty5--;
              } else if (twenty5 >= 3) {
                  twenty5 = twenty5 - 3;
              } else {
                  return "NO";
              }
          }
      }
      return "YES";
  }

  public static void main(String[] args) {

    System.out.println(
        "inbound_phone_calls,outbound_phone_calls,phone_escalations,is_from_phone_deflection"
            .toUpperCase());
  }

}
