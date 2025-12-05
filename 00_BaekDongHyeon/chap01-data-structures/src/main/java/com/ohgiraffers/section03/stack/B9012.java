package com.ohgiraffers.section03.stack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;


/* 문제 설명
* - 입력된 문자열이 올바른 괄호 쌍으로 이루어져 있는지 판별
* - '(', ')' 개수가 같아야함
* - 모든 ')'는 자신보다 앞에있는 '(' 괄호와 짝을 이루어야 한다
*
* 해결 전략
* - '('를 만나면 스택에 push
* - ')'를 만나면 스택에서 pop
*   -> 만약 비어있는데 pop을 시도한다 == 괄호 쌍이 안맞음
*      -->  NO
* - 모든 문자열을 순회하면서 push/pop 수행했는데
*   스택에 남은 '('가 있으면 쌍이 맞지 않음으로 판단
*     --> NO
* */


public class B9012 {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringBuilder sb = new StringBuilder();

    // 입력의 첫 번째 줄에는 입력 데이터의 수를 나타내는 정수 T가 주어진다.
    int T = Integer.parseInt(br.readLine());

    for(int i=0 ; i < T ; i++){
      sb.append( solution( br.readLine() )   );
      sb.append("\n");
    }

    System.out.println(sb.toString());
  }


  public static String solution(String s){

    // '(' 괄호를 담는 스택
    Stack<Character> stack = new Stack<>();
    
    for(int i = 0 ; i < s.length() ; i++){
      char ch = s.charAt(i); // "((()()()))" 문자열에서 i번째만 추출
      
      // '(' 스택에 담기
      if(ch == '(') stack.push(ch);
      
      else{ // ')' 인 경우
        // 비어있으면 == 짝이 맞지 않음 == "NO" 반환
        if(stack.isEmpty()) return "NO";
        
        stack.pop(); // '(' 하나 제거
      }
    }

    // 반복이 끝난 후에도 stack에 '('가 남아있음 == 쌍이 맞지 않음 == "NO"
    if(!stack.isEmpty()){
      return "NO";
    }

    return "YES";
  }
}
