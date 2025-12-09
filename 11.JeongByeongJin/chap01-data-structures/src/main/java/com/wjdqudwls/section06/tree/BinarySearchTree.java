package com.wjdqudwls.section06.tree;

import javax.imageio.stream.ImageInputStream;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

/* 이진 트리(Binary Tree)
 * - 각 노드가 최대 두개의 자식(L, R)을 가지는 트리 구조.
 *
 * 이진 탐색 트리 (Binary Search Tree)
 * - 이진 트리 구조를 가지면서, 정렬 속성을 만족하는 트리
 * - 모든 노드의 왼쪽 서브트리에 있는 모든 노드의 값은 현재 노드 값보다 작다.
 * - 모든 노드의 오른쪽 서브트리에 있는 모든 노드의 값은 현재 노드 값보다 크다.
 * - 모든 서브트리 또한 이진 탐색 트리다.
 *
 * - 이러한 정렬 속성 덕분에 탐색, 삽입, 삭제 연산을 효율적으로 수행 가능함
 *   (평균 O(log n))
 * - 하지만, 한 쪽으로 편향된 트리는 최악의 경우 O(n)이 될 수 있다.
 * */
public class BinarySearchTree<T extends Comparable<T>> { // 2진 탐색 트리
  static class Node<T> {
    T data;
    Node<T> left;   // 왼쪽 자식 노드
    Node<T> right;  // 오른쪽 자식 노드
    Node(T data) {
      this.data = data;
      this.left = null;
      this.right = null;
    }
  }
  // 최상위 노드
  private Node<T> root;
  // 기본 생성자
  public BinarySearchTree() {
    root = null;
  }
  /**
   * 트리에 새로운 데이터를 삽입하는 메서드
   *
   * @param data
   */
  public void insert(T data) {
    root = insertRec(root, data);
  }
  /**
   * 노드 삽입을 위한 재귀 헬퍼 메서드
   *
   * @param node 현재 탐색 중인 노드
   * @param data 삽입할 데이터
   * @return 삽입 후 서브 트리의 루트 노드
   */
  public Node<T> insertRec(Node<T> node, T data) {

    // 현재 노드가 null인 경우 새로운 노드 생성, 반환
    if (node == null) {
      return new Node<>(data);
    }
    // 현재 Data가 node.data 보다  작을 경우 -> 왼쪽 삽입
    if (data.compareTo(node.data) < 0) {
      node.left = insertRec(node.left, data);
    }
    // 현재 Data가 node.data 보다 큰 경우 -> 오른쪽 삽입
    else if (data.compareTo(node.data) > 0) {
      node.right = insertRec(node.right, data);
    }
    // 현재 data가 node.data와 같을 경우 -> 주복이라서 삽입 x
    return node;
  }

  /* ===== 트리 순회 (Traversal) ======*/

  /**
   * 전위 순회 : Root -> L -> R
   * @return
   */
  public List<T> preOrder(){
    List<T> result = new ArrayList<>();
    preOrderRec(root,result);
    return result;
  }
  /**
   * 재귀용 전위 순회 헬퍼 메서드
   * @param node
   * @param result
   */
  private void preOrderRec(Node<T> node, List<T> result){
    if(node != null){
      result.add(node.data); // 현재 노드 값 기록 -> Root Node 방문
      preOrderRec(node.left, result); // 왼쪽 순회
      preOrderRec(node.right, result);// 오른쪽 순회

    }

  }
  /**
   * 중위 순회 : L -> Root -> R
   * @return
   */
  public List<T> inOrder(){
    List<T> result = new ArrayList<>();
    inOrderRec(root,result);
    return result;
  }
  /**
   * 재귀용 중위 순회 헬퍼 메서드
   * @param node
   * @param result
   */
  private void inOrderRec(Node<T> node, List<T> result){
    if(node != null){
      inOrderRec(node.left, result); // 왼쪽 순회
      result.add(node.data); // 현재 노드 값 기록 -> Root Node 방문
      inOrderRec(node.right, result);// 오른쪽 순회

    }

  }
  /**
   * 후위 순회 : L -> R -> Root
   * @return
   */
  public List<T> postOrder(){
    List<T> result = new ArrayList<>();
    postOrder(root,result);
    return result;
  }
  /**
   * 재귀용 후위 순회 헬퍼 메서드
   * @param node
   * @param result
   */
  private void postOrder(Node<T> node, List<T> result){
    if(node != null){
      postOrder(node.left, result); // 왼쪽 순회
      postOrder(node.right, result);// 오른쪽 순회
      result.add(node.data); // 현재 노드 값 기록 -> Root Node 방문

    }
  }

  /**
   * 특정 데이터가 트리 내에 있는지 검색
   * 시간 복잡도
   * - 평균 : O(log n)
   * - 최악 : O(n)
   * @param data
   * @return 존재하면 true, 업으면 false
   */

  public boolean search (T data){
  return searchRec(root, data);
  }

  /**
   * 노드 탐색 헬퍼 메서드
   * @param node 현재 노드
   * @param data 찾을 값
   * @return 찾으면 true, 못찾으면 false
   */
  private boolean searchRec(Node<T> node, T data){
    // 마지막 자식 노드의 왼쪽 또는 오른쪽이 null == 찾는 값이 없다
    if (node == null) return false;
    // 찾을 값과 현재 노드 값이 같은 경우 == 찾았다 !
    if (data.compareTo(node.data) == 0) return true;
    // 재귀 호출을 이용해서 왼쪽/오른쪽 반복 탐색
    return data.compareTo(node.data) < 0
        ? searchRec(node.left, data)    // 찾을 값이 작으면 왼쪽 탐색
        : searchRec(node.right, data);  // 찾을 값이 크면 오른쪽 탐색
  }

  /**
   * 트리에서 특정 데이터가 포함된 노드 삭제
   * @param data
   */
  public void delete(T data){
    root = deleteRec(root, data);
  }

  /**
   * 노드 삭제 헬퍼 메서드
   * @param node
   * @param data
   * @return
   */
  private Node<T> deleteRec(Node<T> node, T data){

    if (node == null) return node; // 삭제할 데이터가 트리에 없음

    /* 삭제할 노드 검색 */
    if(data.compareTo(node.data) < 0){
      node.left = deleteRec(node.left, data);
    }
    else if (data.compareTo(node.data) > 0){
      node.right = deleteRec(node.right, data);
    }
    else {
      // 삭제할 노드를 찾았을 경우 (3가지 경우)

      // 1) 자식 노드가 왼쪽 자식이 없을 때
      if(node.left == null){
        return node.right; // 오른쪽 자식 반환
      // 2) 자식 노드가 오른쪽이 없을 때
      } else if (node.right == null){
        return node.left; // 왼쪽 자식 반환
      }
      // 3) 자식 노드가 둘 다 있을 경우
      // -> 오른쪽 서브트리에서 가장 작은 값을 찾아 현재 노드의 데이터로 대체
      node.data = minValue(node.right);

      // 제일 작은 값을 가지는 노드를 삭제
      node.right = deleteRec(node.right, node.data);
    }
    return node;
  }

  /**
   * 특정 서브트리에서 가장 작은 값 찾는 헬퍼 메서드
   * @param node
   * @return
   */
  private T minValue(Node<T> node){
    T minVal = node.data;

    while (node.left != null){ // 왼쪽 자식 노드가 있다면 계쏙 반복
      node = node.left;
      minVal = node.data;

    }
    return minVal;
  }

  public static void main(String[] args) {
    BinarySearchTree<Integer> bst = new BinarySearchTree<>();

    bst.insert(50);
    bst.insert(30);
    bst.insert(70);
    bst.insert(20);
    bst.insert(40);
    bst.insert(60);
    bst.insert(80);

    System.out.println(" ===== 트리 순회 ===== ");
    // 전위 순회 : 50 30 20 40 70 60 80
    System.out.println("전위 순회 : " + bst.preOrder());
    // 중위 순회 : 20 30 40 50 60 70 80
    System.out.println("중위 순회 : " + bst.inOrder());
    // 후위 순회 : 20 40 30 60 70 80 50
    System.out.println("후위 순회 : " + bst.postOrder());

    System.out.println(" ====== 데이터 검색 =====");
    System.out.println("40 검색 : " + bst.search(40));
    System.out.println("90 검색 : " + bst.search(90));
    System.out.println("20 검색 : " + bst.search(20));
    System.out.println("80 검색 : " + bst.search(80));

    System.out.println(" ====== 데이터 삭제 =====");
    bst.delete(20);
    System.out.println("20 삭제 후 중위 순회 : " + bst.inOrder());
    bst.delete(50);
    System.out.println("50 삭제 후 중위 순회 : " + bst.inOrder());


  }
}