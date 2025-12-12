package com.mycompany.section03.greedy;

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

class A_SugarDeliveryTest {
  private static Integer input1, input2, input3, input4,input5;
  private static Integer output1, output2, output3, output4,output5;

  @BeforeAll
  public static void setUp() {

    input1 = 18;
    output1 = 4;
    input2 = 4;
    output2 = -1;
    input3 = 6;
    output3 = 2;
    input4 = 12;
    output4 = 4;
    input5 = 16;
    output5 = 4;

  }

  public static Stream<Arguments> provideSource() {
    return Stream.of(
        arguments(input1, output1),
        arguments(input2, output2),
        arguments(input3, output3),
        arguments(input4, output4),
        arguments(input5, output5)

    );
  }

  @DisplayName("SugarDelivery")
  @Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
  @ParameterizedTest
  @MethodSource("provideSource")
  public void sugarDeliveryTest(Integer input, Integer output) throws Exception {
    Integer result = A_SugarDelivery.solution(input);
    Assertions.assertEquals(output, result);
  }
}