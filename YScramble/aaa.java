import java.util.HashMap;
import java.util.Map;
public class aaa {
public static void main(String[] args) {
 String eqtnFtn = "/*-- 주석1 --*/ /**-- 주석2 --**/  주석아님1 /** 주석3 **/  /* \n 주석 \n 주석 */  주석아님2  //eqtnFtn = eq﻿tnFtn.replaceAll(/\\*(?:.|[\\n\\r])*?\\*/, );";
 //eqtnFtn = eq﻿tnFtn.replaceAll("/\\*(?:.|[\\n\\r])*?\\*/", "");
 //eqtnFtn = eqtnFtn.replaceAll("/\\*.*\\*/", ""); //엔터를 찾지못해서 문제
 //eqtnFtn = eqtnFtn.replaceAll("/\\*(.|[\r\n])*\\*/", ""); // 주석과 주석 사이에 있는 문자열까지 찾아서 문제
 //eqtnFtn = eqtnFtn.replaceAll("/\\*([^*]|[\r\n])*\\*/", ""); // 하나의 주석에 다수의 '*'가 있는경우를 인지못해서 문제
 //eqtnFtn = eqtnFtn.replaceAll("/\\*([^*]|[\r\n]|(\\*([^/]|[\r\n])))*\\*/", ""); // '주석아님1'을 찾지 못해서 문제
 //eqtnFtn = eqtnFtn.replaceAll("/\\*([^*]|[\r\n]|(\\*+([^*/]|[\r\n])))*\\*/", ""); // 주석들이 다시 튀어나와서 문제
 //드뎌해결 1
 //eqtnFtn = eqtnFtn.replaceAll("/\\*([^*]|[\r\n]|(\\*+([^*/]|[\r\n])))*\\*+/", ""); // 마지막에 '+' 넣어서 해결
 //또다른해결 2(Non-greedy Matching)
 eqtnFtn = eqtnFtn.replaceAll("/\\*(?:.|[\\n\\r])*?\\*/", "");// *. --> *?
 System.out.println(eqtnFtn);
}
}
