package com.jinosoft.section02.list;

public class Application {
  public static void main(String[] args) {
    MyArrayList<Integer> myArrayList = new MyArrayList<>(3);
    myArrayList.isEmpty();
    if (myArrayList.isEmpty()){
      System.out.println("Empty");
    }
    myArrayList.add(10);
    myArrayList.add(20);
    myArrayList.add(30);
    myArrayList.add(40); // 용량 증가 확인

    myArrayList.add(2,999);

    for (int i = 0; i < myArrayList.size(); i++) {
      System.out.println("get("+i+") : " + myArrayList.get(i));
    }

    System.out.println("remove("+myArrayList.size()+") : " + myArrayList.remove(myArrayList.size()-1));

    for (int i = 0; i < myArrayList.size(); i++) {
      System.out.println("get("+i+") : " + myArrayList.get(i));
    }

    System.out.println("==== 종료 ====");

  }
}
