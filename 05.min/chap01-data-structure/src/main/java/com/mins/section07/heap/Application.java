package com.mins.section07.heap;

/* 자바 컬렉션 중 Heap을 직접 지원하는 클래스는 없지만
* PriorityQueue (우선 순위 큐)를 이용하여 힙을 쉽게 구현할 수 있다
* -> 기본적으로 최소 힙으로 동작
*
* - 최소 힙 : 낮은 숫자가 높은 우선 순위를 갖는다.
* */

import java.util.PriorityQueue;

public class Application {
  public static void main(String[] args) {

    PriorityQueue<Integer> minHeap = new PriorityQueue<>();

    minHeap.offer(10);
    minHeap.offer(5);
    minHeap.offer(8);
    System.out.println("현재 최소 힙 : " + minHeap);

    System.out.println("최소값 조회(peek()) : " + minHeap.peek());

    System.out.println("poll() 결과 : " + minHeap.poll());
    System.out.println("현재 최소 힙 : " + minHeap);
    System.out.println("poll() 결과 : " + minHeap.poll());
    System.out.println("현재 최소 힙 : " + minHeap);
    System.out.println("poll() 결과 : " + minHeap.poll());
    System.out.println("현재 최소 힙 : " + minHeap);


    System.out.println("-------------------------------------------");

    /* 최대 힙(Max Heap)
    * - PriorityQueue를 최대 힙으로 사용 하려면
    *   생성자 매개 변수로 Comparator를 전달하여
    *   요소의 우선 순위 결정 방식을 변경해야 한다!!!(내림 차순)
    * */
    PriorityQueue<Integer> maxHeap
        = new PriorityQueue<>((a, b) -> b - a);


    maxHeap.offer(10);
    maxHeap.offer(5);
    maxHeap.offer(8);
    System.out.println("현재 최대 힙 : " + maxHeap);

    System.out.println("최대값 조회(peek()) : " + maxHeap.peek());

    System.out.println("poll() 결과 : " + maxHeap.poll());
    System.out.println("현재 최대 힙 : " + maxHeap);

    System.out.println("poll() 결과 : " + maxHeap.poll());
    System.out.println("현재 최대 힙 : " + maxHeap);

    System.out.println("poll() 결과 : " + maxHeap.poll());
    System.out.println("현재 최대 힙 : " + maxHeap);



  }
}
