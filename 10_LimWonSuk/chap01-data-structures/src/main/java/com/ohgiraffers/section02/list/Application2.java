package com.ohgiraffers.section02.list;

public class Application2 {
    public static void main(String[] args) {
        MyLinkedList<Integer> MylinkedList = new MyLinkedList<>();
        
        MylinkedList.add(10);
        MylinkedList.add(20);
        MylinkedList.add(30);

        /* 중간 삽입 확인 */
        MylinkedList.add(2,999);

        System.out.println("MylinkedList = " + MylinkedList);

        /* get() 확인 */
        System.out.println("1번 인덱스 : " + MylinkedList.get(1));

        /* remove2() 확인 */
        System.out.println("2번 인덱스를 제거 : " + MylinkedList.remove2(2) + "제거됨");
        System.out.println("MylinkedList = " + MylinkedList);
    }

}
