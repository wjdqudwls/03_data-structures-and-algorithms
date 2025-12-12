package com.beyond.section01.sorting;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class C_InsertionSortTest {

  static int[] act1, exp1, act2, exp2, act3, exp3;

  @BeforeAll
  static void setUp() {
    act1 = new int[]{34, 23, 5, 24, 1, 9, 12};
    exp1 = new int[]{1, 5, 9, 12, 23, 24, 34};

    act2 = new int[]{33, 93, 105, 22, 10, 4, 12};
    exp2 = new int[]{4, 10, 12, 22, 33, 93, 105};

    act3 = new int[]{10, 4, 12, 13, 14, 15};
    exp3 = new int[]{4, 10, 12, 13, 14, 15};
  }

  static Stream<Arguments> provideAscendingSource() {
    return Stream.of(
        Arguments.of(act1, exp1),
        Arguments.of(act2, exp2),
        Arguments.of(act3, exp3)
    );
  }

  @DisplayName("삽입 정렬 테스트")
  @ParameterizedTest
  @MethodSource("provideAscendingSource")
  void testInsertionSort(int[] act, int[] exp) {
    C_InsertionSort.solution(act); // 원본(act) 자체가 정렬됨
    Assertions.assertArrayEquals(act, exp); // 배열 요소가 모두 같으면 성공
  }



}