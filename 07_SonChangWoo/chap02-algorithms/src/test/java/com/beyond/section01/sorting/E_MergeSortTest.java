package com.beyond.section01.sorting;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class E_MergeSortTest {

  static int[] act1, exp1, act2, exp2, act3, exp3;

  @BeforeAll
  static void setUp() {
    act1 = new int[]{34, 23, 5, 24, 1, 9, 12};
    exp1 = new int[]{1, 5, 9, 12, 23, 24, 34};

    act2 = new int[]{33, 93, 105, 22, 10, 4, 12};
    exp2 = new int[]{4, 10, 12, 22, 33, 93, 105};

    act3 = new int[]{111, 265, 198, 232, 238, 155, 351, 62, 252, 367, 313, 262, 322, 32, 78, 228, 65, 228, 335, 92, 351, 202, 96, 149, 144, 331, 272, 41, 77, 29};
    exp3 = new int[]{29, 32, 41, 62, 65, 77, 78, 92, 96, 111, 144, 149, 155, 198, 202, 228, 228, 232, 238, 252, 262, 265, 272, 313, 322, 331, 335, 351, 351, 367};
  }

  static Stream<Arguments> provideAscendingSource() {
    return Stream.of(
        Arguments.of(act1, exp1),
        Arguments.of(act2, exp2),
        Arguments.of(act3, exp3)
    );
  }
  @DisplayName("병합 정렬 테스트")
  @ParameterizedTest
  @MethodSource("provideAscendingSource")
  void testMergeSort(int[] act, int[] exp) {
    E_MergeSort.solution(act); // 원본(act) 자체가 정렬됨
    Assertions.assertArrayEquals(act, exp); // 배열 요소가 모두 같으면 성공
  }



}