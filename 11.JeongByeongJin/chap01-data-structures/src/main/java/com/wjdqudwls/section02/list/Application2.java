package com.wjdqudwls.section02.list;

public class Application2 {
  public static void main(String[] args) {
    MyLinkedList<Integer> myLinkedList = new MyLinkedList<>();

    myLinkedList.add(10);
    myLinkedList.add(20);
    myLinkedList.add(30);

    /* 중간 삽입 확인 */
    myLinkedList.add(2, 999);

    System.out.println("myLinkedList = " + myLinkedList);

    /* get() 확인 */
    System.out.println("1번 인덱스 : " + myLinkedList.get(1));

    /* remove() 확인 */
    System.out.println("2번 인덱스 제거 : " + myLinkedList.remove(2) + "제거됨");
  }
}
