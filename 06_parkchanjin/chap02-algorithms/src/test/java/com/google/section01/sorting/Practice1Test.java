package com.google.section01.sorting;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class Practice1Test {

  static int[] act1, exp1, act2, exp2;

  @BeforeAll
  static void setUp(){
    act1 = new int[]{34, 23, 5, 24, 1, 9, 12};
    exp1 = new int[]{1, 5, 9, 12, 23, 24, 34};
    act2 = new int[] {1, 2, 3, 4, 6, 5};
    exp2 = new int[] {1, 2, 3, 4, 5, 6};
  }

  static Stream<Arguments> provideAscendingSource() {
    return Stream.of(
        Arguments.of(act1, exp1),
        Arguments.of(act2, exp2)
    );
  }

  @DisplayName("최적화된 버블 정렬 테스트")
  @ParameterizedTest
  @MethodSource("provideAscendingSource")
  void testBubbleSort(int[] act, int[] exp){
    Practice1.solution(act); // 원본(act) 자체가 정렬됨
    Assertions.assertArrayEquals(act, exp); // 배열 요소가 모두 같으면 성공

  }
}