package com.jinosoft.section03.stack;

import java.util.ArrayDeque;
import java.util.Stack;

/** Stack
*  - 후입선출(LIFO : Last In First Out) 원칙을 따르는 선형 자료구조
 *
 *  Java제공 Stack 클래스
 *  - java 제공 Stack 클래스
 *  - Vector 클래스(List 비슷, 내부 배열 사용)를 상속하여 만들어짐
 *  -> 내부 배열의 크기를 동적으로 조정할 수 있음
 *
 *  - 최근에는 Stack 대신 Deque를 상속받은 ArrayDeque을 사용하도록 권장 */
public class Application {
  public static void main(String[] args) {
    Stack<Integer> stack = new Stack<>();

    stack.push(1);
    stack.push(2);
    stack.push(3);
    System.out.println("stack = " + stack);

    if (stack.isEmpty()) {
      System.out.println("stack 최상단 = " + stack.peek());

      stack.pop();
      System.out.println("pop!");

      System.out.println("stack 최상단 = " + stack.peek());

      stack.pop();
      System.out.println("pop!");

      System.out.println("stack 최상단 = " + stack.peek());
    }

  }
}
