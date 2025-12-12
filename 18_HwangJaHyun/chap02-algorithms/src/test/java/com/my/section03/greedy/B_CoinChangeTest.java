package com.my.section03.greedy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

class B_CoinChangeTest {
  private static String input1, input2, input3;
  private static Integer output1, output2, output3;

  @BeforeAll
  public static void set() {

    input1 = "10 4200\n" +
        "1\n" +
        "5\n" +
        "10\n" +
        "50\n" +
        "100\n" +
        "500\n" +
        "1000\n" +
        "5000\n" +
        "10000\n" +
        "50000";
    output1 = 6;

    input2 = "10 14790\n" +
        "1\n" +
        "5\n" +
        "10\n" +
        "50\n" +
        "100\n" +
        "500\n" +
        "1000\n" +
        "5000\n" +
        "10000\n" +
        "50000";
    output2 = 13;

    input3 = "6 26840\n" +
        "1\n" +
        "20\n" +
        "200\n" +
        "2000\n" +
        "4000\n" +
        "8000\n";
    output3 = 10;

  }

  public static Stream<Arguments> provideSource() {
    return Stream.of(
        arguments(input1, output1),
        arguments(input2, output2),
        arguments(input3, output3)
    );
  }

  @DisplayName("CoinChange")
  @Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
  @ParameterizedTest
  @MethodSource("provideSource")
  public void coinChangeTest(String input, Integer output) throws Exception {
    Integer result = B_CoinChange.solution(input);
    Assertions.assertEquals(output, result);
  }
}