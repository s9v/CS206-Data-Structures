package cs206b;

import java.io.*;
import java.util.Scanner;

public class Main {
  public static void main(String args[]){
    DoubleLinkedSeq dls = new DoubleLinkedSeq();
        
        dls.addAfter(1);
        dls.addAfter(2);
        dls.addAfter(3);
        dls.addAfter(4);
        
        dls.removeMostDuplicate();
        
        System.out.println(dls);
  }
}