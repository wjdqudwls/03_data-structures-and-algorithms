package com.ohgiraffers.section04.queue.queue;

import java.util.LinkedList;
import java.util.Queue;

/* Queue
* - 선입선출 구조 (First in, First out, FIFO)
*    원칙을 따르느 선형 자료구조
*  * 주요 연산:
 * - offer(E e): 큐의 "뒤"에 요소를 추가합니다. (성공 시 true, 용량 초과 시 false 반환)
 * - add(E e): 큐의 "뒤"에 요소를 추가합니다. (용량 초과 시 IllegalStateException 발생)
 * - poll(): 큐의 "앞"에 있는 요소를 제거하고 반환합니다. (큐가 비어있으면 null 반환)
 * - remove(): 큐의 "앞"에 있는 요소를 제거하고 반환합니다. (큐가 비어있으면 NoSuchElementException 발생)
 * - peek(): 큐의 "앞"에 있는 요소를 제거하지 않고 반환합니다. (큐가 비어있으면 null 반환)
 * - element(): 큐의 "앞"에 있는 요소를 제거하지 않고 반환합니다. (큐가 비어있으면 NoSuchElementException 발생)*/
public class Application1 {
    public static void main(String[] args) {
        /* collection 프레임 워크에는 Queue 인터페이스를 구현한 여러 클래스가 존재함.
        * 그 중  LinkedList를 사용하여 연결 리스트 기반 큐를 사용해볼 예정
        * (LinkedList는 DoublyLinkedList 기반으로, Queue 연산(offer, poll)에 효율적임)*/
        Queue<Integer> queue = new LinkedList<>();

        System.out.println("=========== 큐에 요소 추가 - offer() ===========");
        // booleam offet(E e) : 큐 뒤에 요소 e 추가
        // LinkedList는 용량 제한이 없어서 항상 true 반환
        queue.offer(1); // [1]  (front: 1, rear: 1)
        queue.offer(2); // [1, 2] (front: 1, rear: 2)
        queue.offer(3); // [1, 2, 3] (front: 1, rear: 3)
        System.out.println("queue = " + queue);
        System.out.println("===================== 큐 맨 앞 요소 확인 - peak() ======================");
        // 큐가 비어있을 경우 null 반환
        System.out.println("peek() 결과 : " + queue.peek());
        System.out.println("peek() 후 큐 : " + queue);

        System.out.println("===================== 큐 맨 앞 요소 제거 - peak() ======================");
        // 큐가 비어있을 경우 null 반환
        System.out.println("peek() 결과 : " + queue.poll()); // 1 제거
        System.out.println("peek() 후 큐 : " + queue); // [2, 3]

        System.out.println("peek() 결과 : " + queue.poll()); // 2 제거
        System.out.println("peek() 후 큐 : " + queue); // [3]

        System.out.println("peek() 결과 : " + queue.poll()); // 3 제거
        System.out.println("peek() 후 큐 : " + queue); // []

        System.out.println("peek() 결과 : " + queue.poll()); // 비어있어서 null  반환

        System.out.println("===================== 큐가 비어 있는지 확인 - isEmpty() ======================");
        System.out.println("is Empty() : " + queue.isEmpty()); // true

        queue.offer(4); // 큐 뒤에 추가
        System.out.println("is Empty() : " + queue.isEmpty()); // false
    }
}
