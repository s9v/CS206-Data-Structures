package elice;

import java.io.*;
import java.util.Scanner;

public class Main {
  public static void main(String args[]) {
  
    System.out.println("Hello World!");
    
    int[] data = {44, 32, Integer.MAX_VALUE, 1234, 14, 0, 44, 53, 1, 5224, 14};
    
    RadixSort rad = new RadixSort();
    rad.radixSort(data);
    
    for (int i = 0; i < data.length; i++)
        System.out.print(data[i] + " ");

  }

}