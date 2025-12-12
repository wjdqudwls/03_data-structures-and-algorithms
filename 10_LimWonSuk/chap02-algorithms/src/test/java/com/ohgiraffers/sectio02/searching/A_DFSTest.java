package com.ohgiraffers.sectio02.searching;


import com.ohgiraffers.sectio02.searching.A_DFS;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

class A_DFSTest {
    static String input;
    static Integer output;

    @BeforeAll
    static void setUp() {
        input = "7\n" + //  컴퓨터의 개스
                "6\n" + // 간선의수
                "1 2\n" + // 1은 2와 연결됨
                "2 3\n" + // 2는 3과 연결됨
                "1 5\n" + // 1은 5랑 연결됨
                "5 2\n" + // 5는 2랑 연결됨
                "5 6\n" + // 5는 6랑 연결됨
                "4 7"; // 4는 7랑 연결됨

        output = 4;

    }

    static Stream<Arguments> provideSource() {
        return Stream.of(
                Arguments.arguments(input, output)
        );
    }

    @DisplayName("dfs")
    @ParameterizedTest
    @MethodSource("provideSource")
    void dfsTest(String input, Integer output) throws IOException {
        Integer result = A_DFS.solution(input);
        Assertions.assertEquals(output, result);
    }

}