package com.google.section07;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/*
 * - Heap
 *  - 완전 이진 트리 구조 + 우선 순위 큐 구현을 위한 자료 구조
 *  - 부모 자식 노드 사이에 우선 순위만 보장, 자식 순서는 특별한 순서 X
 *
 * 최소 힙(Min heap)
 *  - 작은 값이 우선 순위가 높은 힙
 * */
public class MinHeap<T extends Comparable<T>> {
  // 힙의 요소들을 저장하기 위한 내부 저장소로 ArrayList를 사용
  private List<T> heap = new ArrayList<>();

  public static void main(String[] args) {
    MinHeap<Integer> minHeap = new MinHeap<>();

    // 초기 데이터 세팅
    int[] values = {10, 5, 20, 8, 15};
    System.out.println("삽입할 데이터: " + java.util.Arrays.toString(values));
    for (int value : values) {
      minHeap.add(value);
      System.out.println(value + " 삽입 후 힙 상태: " + minHeap);
    }
    System.out.println("초기 힙 구성 완료: " + minHeap);

    // 힙에 새로운 값 추가
    System.out.println("\n3 추가:");
    minHeap.add(3);
    System.out.println("3 삽입 후 힙 상태: " + minHeap);

    // 최소값 확인
    System.out.println("\n현재 최소값 (getMin): " + minHeap.getMin()); // 3

    // 힙에서 최소값 추출하며 상태 출력
    System.out.println("\n최소값 추출 (extractMin) 과정:");
    while (!minHeap.heap.isEmpty()) {
      int min = minHeap.extractMin();
      System.out.println("추출된 최소값: " + min + ", 현재 힙: " + minHeap);
    }
  }

  /**
   * 힙에 새로운 데이터를 추가
   * 시간 복잡도: O(log n)
   *
   * @param data 추가할 데이터
   */
  public void add(T data) {
    // 1. 데이터를 힙의 가장 마지막 위치(리스트의 끝)에 추가
    heap.add(data);
    // 2. 추가된 요소가 올바른 위치를 찾을 때까지 위로 올라가며 힙 속성을 유지하도록 재구성함.
    heapifyUp(heap.size() - 1);
  }

  /**
   * 힙에서 최소값(루트)를 제거하고 반환
   * 시간 복잡도: O(log n)
   *
   * @return 힙의 최소값
   * @throws NoSuchElementException 힙이 비어있을 경우 예외 발생
   */
  public T extractMin() {
    if (heap.isEmpty()) {
      throw new NoSuchElementException("힙이 비어있습니다.");
    }
    // 1. 최소값(루트 요소)을 임시 변수에 저장
    T min = heap.get(0);

    // 2. 힙의 가장 마지막 요소를 가져옴
    T last = heap.remove(heap.size() - 1);

    // 3. 만약 힙에 요소가 남아있다면, 마지막 요소를 루트 위치에 놓고
    //    힙 속성을 만족할 때까지 아래로 내려가며 재구성함.
    if (!heap.isEmpty()) {
      heap.set(0, last);
      heapifyDown(0);
    }

    // 4. 저장해둔 최소값을 반환
    return min;
  }

  /**
   * 힙에서 최소값(루트)을 제거하지 않고 반환
   * 시간 복잡도: O(1)
   *
   * @return 힙의 최소값
   * @throws NoSuchElementException 힙이 비어있을 경우 예외 발생
   */
  public T getMin() {
    if (heap.isEmpty()) {
      throw new NoSuchElementException("힙이 비어있습니다.");
    }
    return heap.get(0);
  }

  /**
   * 특정 인덱스의 요소를 부모 노드와 비교하며 위로 올라가 힙 속성을 유지하는 헬퍼 메서드 (Heapify-up)
   *
   * @param index 재구성할 요소의 현재 인덱스
   */
  private void heapifyUp(int index) {
    // 현재 인덱스가 루트(0)가 될 때까지 반복함.
    while (index > 0) {

      // 부모 노드의 인덱스 계산: (index - 1) / 2
      int parentIndex = (index - 1) / 2;

      // 만약 현재 노드의 값이 부모 노드의 값보다 크거나 같다면, 최소 힙 조건이 만족되므로 재구성을 멈춤
      if (heap.get(index).compareTo(heap.get(parentIndex)) >= 0) {
        break;
      }

      // 현재 노드와 부모 노드의 위치를 교환
      swap(index, parentIndex);

      // 인덱스를 부모 인덱스로 갱신하여 계속 위로 올라감
      index = parentIndex;
    }
  }

  /**
   * 특정 인덱스의 요소를 자식 노드와 비교하며 아래로 내려가 힙 속성을 유지하는 헬퍼 메서em (Heapify-down)
   *
   * @param index 재구성할 요소의 현재 인덱스
   */
  private void heapifyDown(int index) {

    int size = heap.size();

    while (true) {
      // 왼쪽 자식 인덱스: 2 * index + 1
      int leftChildIndex = 2 * index + 1;

      // 오른쪽 자식 인덱스: 2 * index + 2
      int rightChildIndex = 2 * index + 2;

      // 현재 노드, 왼쪽 자식, 오른쪽 자식 중 가장 작은 값을 가진 노드의 인덱스를 저장할 변수
      int smallest = index;

      // 왼쪽 자식이 존재하며, 왼쪽 자식의 값이 현재 노드(smallest)보다 작으면 smallest를 갱신
      if (leftChildIndex < size && heap.get(leftChildIndex).compareTo(heap.get(smallest)) < 0) {
        smallest = leftChildIndex;
      }

      // 오른쪽 자식이 존재하며, 오른쪽 자식의 값이 현재까지 가장 작은 값(smallest)보다 작으면 smallest를 갱신
      if (rightChildIndex < size && heap.get(rightChildIndex).compareTo(heap.get(smallest)) < 0) {
        smallest = rightChildIndex;
      }

      // 만약 현재 노드가 이미 가장 작은 값이면, 힙 속성이 만족되므로 재구성을 멈춤
      if (smallest == index) {
        break;
      }

      // 현재 노드와 가장 작은 값을 가진 자식 노드의 위치를 교환
      swap(index, smallest);

      // 인덱스를 교환된 자식의 인덱스로 갱신하여 계속 아래로 내려감
      index = smallest;
    }
  }

  /**
   * 인덱스에 해당하는 두 요소의 위치를 서로 교환하는 헬퍼 메서드
   */
  private void swap(int i, int j) {
    T temp = heap.get(i);
    heap.set(i, heap.get(j));
    heap.set(j, temp);
  }

  /**
   * 힙의 내용을 문자열로 반환 (디버깅용)
   *
   * @return 힙의 내부 리스트 표현
   */
  @Override
  public String toString() {
    return heap.toString();
  }
}

