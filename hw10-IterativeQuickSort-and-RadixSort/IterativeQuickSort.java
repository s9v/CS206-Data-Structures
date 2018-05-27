package elice;

import java.util.Random;
import java.util.Stack;

public class IterativeQuickSort {
    //Do not change the name
    public Stack<Integer> qsStack;

    public IterativeQuickSort(){
        qsStack = new Stack<Integer>();
    }

    /*
     * Precondition: n>1, and data has at least n elements starting at data[first].
     * Postcondition: The method has selected some "pivot value" that occurs in data[first]...data[first+n-1].
     * The elements of data have then been rearranged and the method returns a pivot index so that
     *  --data[pivot index] is equal to the pivot;
     *  --each element before data[pivot index] is <= the pivot;
     *  --each element after data[pivot index] is > the pivot.
     **/
    private static int partition (int[] data, int first, int n)
    {
        int pivot = data[first];
        int tooBigIndex = first + 1;
        int tooSmallIndex = first + n - 1;

        while (tooBigIndex <= tooSmallIndex) {
            while (tooBigIndex <= tooSmallIndex && data[tooBigIndex] <= pivot) tooBigIndex++;
            while (tooBigIndex <= tooSmallIndex && data[tooSmallIndex] > pivot) tooSmallIndex--;

            if (tooBigIndex < tooSmallIndex) {
                int foo = data[tooBigIndex];
                data[tooBigIndex] = data[tooSmallIndex];
                data[tooSmallIndex] = foo;
            }
        }

        int foo = data[first];
        data[first] = data[tooSmallIndex];
        data[tooSmallIndex] = foo;

        return tooSmallIndex;
    }

    public void QuickSort(int[] data, int first, int n)
    {
        qsStack.push(first);
        qsStack.push(n);

        while ( ! qsStack.isEmpty()) {
            n = qsStack.pop();
            first = qsStack.pop();

            int pivot_idx = partition(data, first, n);
            int n1 = pivot_idx - first;
            int n2 = n - n1 - 1;

            if (n1 > 1) {
                qsStack.push(first);
                qsStack.push(n1);
            }
            if (n2 > 1) {
                qsStack.push(pivot_idx + 1);
                qsStack.push(n2);
            }
        }
    }

    //Print method for your help
    public void print( int[] data, int n )
    {
        int i;
        for ( i = 0; i < n; ++i )
            System.out.print(data[i]+" ");
    }
}
