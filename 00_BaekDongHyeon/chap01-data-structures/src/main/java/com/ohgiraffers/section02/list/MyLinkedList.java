package com.ohgiraffers.section02.list;

/*
* 단일 연결 리스트(Singly Linked List) 직접 구현하기
* 각 노드는 데이터 + 다음 노드를 가리키는 포인터 로 구성되어있다.
* */
public class MyLinkedList<T> {

  private Node<T> head; // 리스트의 첫 번째 노드를 가리키는 포인터
  
  private Node<T> tail; // 리스트의 마지막 노드를 가리키는 포인터
  
  private int size; // 저장된 요소의 개수
  
  
  /* 정적 내부 클래스 - Node
  * - 연결 리스트의 각 요소를 나타내는 역할의 클래스
  * */
  private static class Node<T>{
    T data;       // 노드가 저장할 실제 데이터
    Node<T> next; // 다음 노드를 가리키는 포인터
    
    // 생성자
    Node(T data){
      this.data = data;
      this.next = null; // 노드 처음 생성 시 다음 노드 미지정
    }
  }


  /* MyLinkedList 기본 생성자 */
  public MyLinkedList(){
    head = null;
    tail = null;
    size = 0;
  }

  /**
   * 리스트의 끝에 전달받은 요소를 추가
   * 시간 복잡도 : O(1) - tail 포인터를 이용해서 바로 추가 가능
   * @param element
   */
  public void add(T element){
    // 1. 새 Node 생성 + 노드에 데이터 저장
    Node<T> newNode = new Node<>(element);

    // 2. 리스트가 비어있는지 확인
    if(head == null){
      // 처음 추가된 노드는 head이자 tail
      head = newNode;
      tail = newNode;

    } else{
      // 3. 리스트 내 저장된 요소가 있다면
      tail.next = newNode;
      tail = newNode;
    }

    // 4. 리스트에 저장된 요소 개수 size를 1 증가
    size++;
  }

  /**
   * 지정된 인덱스에 요소를 삽입
   * 삽입 위치가 리스트의 제일 끝이면 tail 포인터를 갱신
   * 시간 복잡도 : 최악의 경우 O(n)
   *
   * @param index 삽입할 위치
   * @param element 삽입할 요소(data)
   * @throws IndexOutOfBoundsException 인덱스 범위가 유효 범위를 벗어 났을 때
   */
  public void add(int index, T element){

    // 1. 인덱스가 유효 범위를 벗어나는 경우
    if(index < 0 || index > size){
      throw new IndexOutOfBoundsException("인덱스가 범위를 벗어났습니다.");
    }

    // 2. 새 Node 생성
    Node<T> newNode = new Node<>(element);

    // 3. 새 Node를 지정된 위치에 삽입

    // 3-1. 삽입 위치가 맨 앞(head)인 경우
    if(index == 0){
      newNode.next = head; // 새 노드의 다음 요소를 현재 head로 지정
      head = newNode; // head 포인터가 새 노드를 가리키도록 지정
      
      if(size == 0){
        // 기존 저장된 요소가 없다면 head, tail 모두 newNode를 가르켜야함
        tail = newNode;
      }
    }

    // 3-2. 삽입 위치가 맨 뒤(tail)인 경우
    else if(index == size){
      tail.next = newNode; // 현재 tail이 다음 새 Node가 되고
      tail = newNode;      // tail이 새 Node를 가리킴
    }

    // 3-3. 삽입 위치가 중간인 경우
    else{
      // 삽입할 이전 위치의 노드 찾기

      Node<T> current = head; // head부터 하나씩 접근
      for(int i = 0 ; i < index - 1 ; i++){
        current = current.next; // index - 1 번째 노드가 최종 current
      }
      
      newNode.next = current.next; // 새 노드의 다음이 current의 다음 대입
      current.next = newNode; // current의 다음은 newNode가 되도록 대입
    }

    // 4. size 1 증가
    size++;

  }


  /**
   * 지정한 인덱스의 요소를 제거하고 반환하기
   * 요소 제거 후 head 또는 tail을 갱신할 가능성이 있다.
   * 시간 복잡도 : 최악의 경우 O(n)
   *
   * @param index 제거할 요소의 index 번호
   * @return 제거된 요소
   */
  public T remove(int index) {

    // 1. 인덱스가 유효한 범위(0부터 size-1까지)에 있는지 확인
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException("인덱스가 범위를 벗어났습니다.");
    }

    T removedElement; // 제거될 요소를 저장할 변수

    // 2. 제거 위치에 따라 처리
    // 2-1. 첫 번째 요소(head)를 제거하는 경우
    if (index == 0) {
      removedElement = head.data; // head의 데이터를 저장
      head = head.next;           // head를 다음 노드로 이동
      if (head == null) {         // 만약 리스트가 비게 되면 tail도 null로 설정
        tail = null;
      }
    }

    // 2-2. 중간 또는 마지막 요소를 제거하는 경우
    else {
      // 제거할 노드의 이전 노드를 찾습니다.
      Node<T> current = head;

      for (int i = 0; i < index - 1; i++) {
        current = current.next; // index-1 위치의 노드까지 이동
      }

      removedElement = current.next.data; // 제거될 노드의 데이터를 저장

      current.next = current.next.next;   // current의 다음을, 제거될 노드의 다음으로 연결 (제거된 노드 스킵)
      if (current.next == null) { // 만약 제거된 노드가 마지막 노드였다면, current가 새로운 tail이 됨
        tail = current;
      }
    }
    // 3. 리스트의 크기를 1 감소시킵니다.
    size--;
    // 4. 제거된 요소를 반환합니다.
    return removedElement;
  }


  /**
   * 인덱스 번째 노드의 값을 반환
   * 배열이 아니기 때문에 head부터 순회
   * 시간 복잡도 : O(n)
   * @param index
   * @return
   */
  public T get(int index) {
    // 1. 인덱스가 유효한 범위(0부터 size-1까지)에 있는지 확인
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException("인덱스가 범위를 벗어났습니다.");
    }

    // 2. head부터 시작하여 원하는 인덱스까지 노드를 순회
    Node<T> current = head;
    for (int i = 0; i < index; i++) {
      current = current.next;
    }

    // 3. 해당 노드의 데이터를 반환
    return current.data;
  }







  /**
   * head부터 tail까지 모든 Node를 차례대로 순회하면서
   * StringBuilder에 data를 추가한 후
   * 마지막에 String으로 반환하는 메서드
   * 시간 복잡도 : O(n)
   * @return
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    Node<T> current = head; // head부터 시작하여 모든 노드를 순회
    while (current != null) {
      sb.append(current.data); // 현재 노드의 데이터 추가
      if (current.next != null) { // 다음 노드가 있다면 쉼표와 공백 추가
        sb.append(", ");
      }
      current = current.next; // 다음 노드로 이동
    }
    sb.append("]");
    return sb.toString();
  }
}
