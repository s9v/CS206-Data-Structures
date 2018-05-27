package elice;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String args[]) {
        /*while (true) {
            AVLTreeBag bag = new AVLTreeBag();

            String[] nicks = {
                    "aaa",
                    "bbb",
                    "ccc",
                    "ddd",
                    "eee",
                    "fff",
                    "ggg",
                    "hhh",
                    "iii"
            };

            shuffle(nicks);

            for (int i = 0; i < nicks.length; i++)
                bag.add(nicks[i], "");

            if ( !! bag.isbalanced()) {
                for (int i = 0; i < nicks.length; i++)
                    System.out.print(nicks[i] + " ");
                System.out.println();

                bag.print_tree();
                break;
            }
        }*/

        IntBinomialHeap heap = new IntBinomialHeap();
        heap.add(5, "Haha");
        heap.add(1, "Haha");
        heap.add(3, "Haha");
        heap.add(4, "Haha");
        heap.add(2, "Haha");

        heap.print();
        System.out.println("Next min: " + heap.remove());
        heap.print();
        System.out.println("Next min: " + heap.remove());
        heap.print();
        System.out.println("Next min: " + heap.remove());
        heap.print();
        System.out.println("Next min: " + heap.remove());
        heap.print();
        System.out.println("Next min: " + heap.remove());
        heap.print();
    }

    static void shuffle(String[] ar)
    {
        // If running on Java 6 or older, use `new Random()` on RHS here
        Random rnd = ThreadLocalRandom.current();

        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            String a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }
}
