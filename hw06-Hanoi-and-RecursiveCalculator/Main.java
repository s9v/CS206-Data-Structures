package elice;

import java.io.*;
import java.util.Scanner;

public class Main {
  public static void main(String args[]){
    Hanoi hanoi = new Hanoi(3);
    System.out.println(hanoi);
    hanoi.moveTower();
    System.out.println(hanoi);
    System.out.println(hanoi.log);

    RecursiveCalculator rc = new RecursiveCalculator();
    System.out.println(rc.calculate("3 - 2 - 1"));
    System.out.println(rc.log);
  }
}