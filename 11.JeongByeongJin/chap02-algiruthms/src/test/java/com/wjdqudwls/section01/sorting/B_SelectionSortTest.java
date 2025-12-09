package com.wjdqudwls.section01.sorting;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class B_SelectionSortTest {
  static int[] act1, exp1,act2,exp2;

  @BeforeAll
  static void setUp() {
    act1 = new int[]{34, 23, 5, 24, 1, 9, 12};
    exp1 = new int[]{1, 5, 9, 12, 23, 24, 34};
    act2 = new int[]{50, 40, 30, 20, 10, 9, 3};
    exp2 = new int[]{3, 9, 10, 20, 30, 40, 50};
  }

  static Stream<Arguments> provideAscendingSource(){
    return Stream.of(
        Arguments.of(act1,exp1),
        Arguments.of(act2,exp2)
    );
  }

  @DisplayName("선택 정렬 테스트") // 테스트명 정하기
  @ParameterizedTest // 테스트 종류는 파라미터 받는 테스트
  @MethodSource("provideAscendingSource") // provideAscendingSource 라는 이름의 정적 메서드를 테스트 데이터 공급자로 사용하겠다는 선언
  void testBubbleSort(int[] act,int[] exp){
    B_SelectionSort.solution(act); // 원본(act) 자체가 정렬됨
    Assertions.assertArrayEquals(act,exp); // 배열 요소가 모두 같으면 성공
  }

}