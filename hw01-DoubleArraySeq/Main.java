package cs206b;

import java.io.*;
import java.util.Scanner;

public class Main {
  public static void main(String args[]) {

    /*
      
    You can do your own test in this file.
    When you press 'run' button, then thisi main function runs.

    */
    System.out.println("Hello World!");
    sampleTest();
  }


  public static void sampleTest() {
    DoubleArraySeq a = new DoubleArraySeq();
    a.addAfter(1);
    a.addAfter(2);
    a.addAfter(3);
    a.addAfter(4);
    a.addAfter(5);
    DoubleArraySeq seq = new DoubleArraySeq();
    seq.addAfter(8);
    seq.addAfter(9);
    seq.addAfter(10);

    DoubleArraySeq b = a.insertSeqAt(seq, 3);
    for (b.start(); b.isCurrent(); b.advance()) {
      System.out.println(b.getCurrent() + " " + b.size);
    }
  }
}