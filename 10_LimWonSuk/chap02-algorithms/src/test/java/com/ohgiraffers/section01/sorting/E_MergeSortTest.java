package com.ohgiraffers.section01.sorting;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class E_MergeSortTest {
    static int[] act1, exp1, act2, exp2, act3, exp3, exp4, act4;

    @BeforeAll // 다른 테스트가 하나도 시작하기 전에
    static void setUp(){
        act1 = new int[]{34, 23, 5, 24, 1, 9, 12};
        exp1 = new int[]{1, 5, 9, 12, 23, 24, 34};
        act2 = new int[]{22, 4, 2, 33, 51, 122, 31};
        exp2 = new int[]{2, 4, 22, 31, 33, 51, 122};
        act3 = new int[]{306, 99, 267, 21, 196, 56, 319, 131, 233, 305};
        exp3 = new int[]{21, 56, 99, 131, 196, 233, 267, 305, 306, 319};
        act4 = new int[]{111, 265, 198, 232, 238, 155, 351, 62, 252, 367, 313, 262, 322, 32, 78, 228, 65, 228, 335, 92, 351, 202, 96, 149, 144, 331, 272, 41, 77, 29};
        exp4 = new int[]{29, 32, 41, 62, 65, 77, 78, 92, 96, 111, 144, 149, 155, 198, 202, 228, 228, 232, 238, 252, 262, 265, 272, 313, 322, 331, 335, 351, 351, 367};
    }
    static Stream<Arguments> provideAscendingSource(){
        return Stream.of(
                Arguments.of(act1, exp1),
                Arguments.of(act2, exp2),
                Arguments.of(act3, exp3),
                Arguments.of(act4, exp4)


        );
    }
    @DisplayName("병합 정렬 테스트")
    @ParameterizedTest // provideAscendingSource 에서 참조
    @MethodSource("provideAscendingSource")
    void testMergeSort(int[] act, int[] exp){
        E_MergeSort.solution(act); // 원본(act) 자체가 정렬됨
        Assertions.assertArrayEquals(act, exp); // 배열 요소가 모두 같으면 성공
    }
}


/*
            for ( j = i - 1; i >= 0; j++) { // temp 값은 i에 해당하는 배열의 숫자값
        if (arr[j] > temp) { // if arr의 j번째가 임시저장한것보다 크다면 True
arr[j + 1] = arr[j];// 위의값을 여기에 대입하겠다
        } else {
        break;
        }*/
