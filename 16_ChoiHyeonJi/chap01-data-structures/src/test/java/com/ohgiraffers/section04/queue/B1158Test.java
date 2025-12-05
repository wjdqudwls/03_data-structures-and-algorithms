package com.ohgiraffers.section04.queue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Stream;

public class B1158Test {
  /* 테스트용 데이터 */
  static Stream<Arguments> provideTestCases(){

    return Stream.of(
        Arguments.arguments(7, 3, "<3, 6, 2, 7, 5, 1, 4>"),
        Arguments.arguments(5, 2, "<2, 4, 1, 5, 3>")
    );
  }

  @DisplayName("요세푸스 수열 테스트")
  @ParameterizedTest
  @MethodSource("provideTestCases")
  public void testSolution(int n, int k, String expected){
    String result = B1158.solution(n, k);
    Assertions.assertEquals(expected, result); // 예상 결과와 실제 결과 비교
  }

}
