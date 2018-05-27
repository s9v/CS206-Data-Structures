package cs206b;

import java.io.*;
import java.lang.*;
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


    public static void sampleTest()
    {
        DoublyLinkedSeq<Integer> seq1 = new DoublyLinkedSeq<Integer>();
        DoublyLinkedSeq<Integer> seq2 = new DoublyLinkedSeq<Integer>();
        
        for (int i = 1; i <= 5; i++)
            seq1.addAfter(new Integer(i));

        System.out.println(seq1);
        
        seq1.start();
        seq1.advanceForward();
        seq1.advanceForward();
        seq1.advanceForward();
        
        System.out.println(seq1.getCurrent());
        
        for (int i = 1; i <= 5; i++)
            seq2.addAfter(new Integer(-i*i));
        
        System.out.println(seq2);
        
        seq1.addAll(seq2);
        
        System.out.println(seq1);
        System.out.println(seq1.getCurrent());
    }

}