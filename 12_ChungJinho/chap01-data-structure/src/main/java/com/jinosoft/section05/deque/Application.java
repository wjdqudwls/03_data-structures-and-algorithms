package com.jinosoft.section05.deque;

import java.util.ArrayDeque;
import java.util.Deque;

public class Application {
  public static void main(String[] args) {
    Deque<Integer> deque = new ArrayDeque<>();
    deque.addLast(1);
    deque.addLast(2);
    deque.addLast(3);
    deque.addLast(4);
    System.out.println(" addLast 후 deque : " + deque);

    deque.addFirst(5);

    System.out.println("----- 덱에 요소 추가 - addFirst, addLast -----");

    System.out.println("----- 덱의 맨 앞/뒤 요소 확인 - getFirst, getLast -----");
    System.out.println("getFirst() 결과 : "+deque.getFirst());
    System.out.println("getLast() 결과 : "+deque.getLast());

    
  }
}
