package cs206b;

import java.util.Arrays;

public class DoubleLinkedSeq implements Cloneable {

    private int manyNodes;
    private DoubleNode head;
    private DoubleNode tail;
    private DoubleNode cursor;
    private DoubleNode precursor;

    public DoubleLinkedSeq() {
        manyNodes = 0;
        head = null;
        tail = null;
        cursor = null;
        precursor = null;
    }

    public void addAfter(double element) {
        DoubleNode node = null;

        try {
            node = new DoubleNode(element, null);
        }
        catch(OutOfMemoryError e) {
            throw new OutOfMemoryError("addAfter(): Not enough memory for creating new node.");
        }

        if (manyNodes == 0) {
            head = node;
            tail = node;
        }
        else if (cursor == null) {
            tail.setLink(node);
            tail = node;
        }
        else {
            node.setLink(cursor.getLink());
            cursor.setLink(node);

            if (cursor == tail)
                tail = node;
        }

        precursor = cursor;
        cursor = node;
        manyNodes++;
    }

    public void addBefore(double element) {
        DoubleNode node = null;

        try {
            node = new DoubleNode(element, null);
        }
        catch(OutOfMemoryError e) {
            throw new OutOfMemoryError("addBefore(): Not enough memory for creating new node.");
        }

        if (manyNodes == 0) {
            head = node;
            tail = node;
        }
        else if (cursor == null) {
            node.setLink(head);
            head = node;
        }
        else {
            node.setLink(cursor);

            if (precursor != null)
                precursor.setLink(node);

            if (cursor == head)
                head = node;
        }

        cursor = node;
        manyNodes++;
    }

    public void addAll(DoubleLinkedSeq addend) {
        if (addend == null) {
            throw new NullPointerException("addAll(): input parameter is null");
        }
        else {
            for (DoubleNode current = addend.head; current != null; current = current.getLink()) {
                DoubleNode node = null;

                try {
                    node = new DoubleNode(current.getData(), null);
                }
                catch(OutOfMemoryError e) {
                    throw new OutOfMemoryError("addAll(): Not enough memory for creating new list.");
                }

                if (manyNodes == 0) {
                    head = tail = node;
                }
                else {
                    tail.setLink(node);
                    tail = node;
                }

                manyNodes++;
            }
        }
    }

    public void advance() {
        if(isCurrent()) {
            precursor = cursor;
            cursor = cursor.getLink();

            if (cursor == null)
                precursor = cursor = null;
        }
        else {
            throw new IllegalStateException("advance(): There are no current cursor.");
        }
    }

    public Object clone() {
        DoubleLinkedSeq copy = null;

        try {
            copy = (DoubleLinkedSeq) super.clone();
        } catch(OutOfMemoryError e) {
            throw new OutOfMemoryError("clone(): Not enough memory for creating new sequence.");
        } catch (CloneNotSupportedException e) {
            throw new OutOfMemoryError("clone(): Clone not supported. - Yer unit of power Harry! - I'm a watt?");
        }

        copy.manyNodes = 0;
        copy.head = copy.tail = null;
        copy.precursor = copy.cursor = null;

        for (DoubleNode current = head; current != null; current = current.getLink()) {
            try {
                copy.addAfter(current.getData());
            }
            catch (OutOfMemoryError e) {
                throw new OutOfMemoryError("clone(): Not enough memory for creating new sequence.");
            }
        }
        
        copy.start();
        for (DoubleNode current = head; current != null; current = current.getLink()) {
            if (current == cursor)
                break;
            else
                copy.advance();
        }

        return copy;
    }

    public static DoubleLinkedSeq concatenation(DoubleLinkedSeq s1, DoubleLinkedSeq s2) throws OutOfMemoryError {
        if(s1 == null || s2 == null) {
            throw new IllegalArgumentException("concaternation(s1, s2): at least one arguments is null");
        }

        DoubleLinkedSeq answer = new DoubleLinkedSeq();

        for (DoubleNode current = s1.head; current != null; current = current.getLink())
            answer.addAfter(current.getData());

        for (DoubleNode current = s2.head; current != null; current = current.getLink())
            answer.addAfter(current.getData());
        
        answer.precursor = answer.cursor = null;

        return answer;
    }

    public double getCurrent() {
        if (isCurrent()) {
            return cursor.getData();
        } else {
            throw new IllegalStateException("getCurrent(): There is no current element.");
        }
    }

    public boolean isCurrent() {
        return cursor != null;
    }

    public void removeCurrent() {
        if (isCurrent()) {
            if (manyNodes == 1) {
                head = tail = null;
                precursor = cursor = null;
            }
            else if (cursor == head) {
                cursor = cursor.getLink();
                head = cursor;
            }
            else if (cursor == tail) {
                tail = precursor;
                precursor = cursor = null;
            }
            else {
                cursor = cursor.getLink();
                precursor.setLink(cursor);
            }

            manyNodes--;
        } else {
            throw new IllegalStateException("removeCurrent(): There is no current element.");
        }
    }

    public int size() {
        return manyNodes;
    }

    public void start() {
        precursor = null;
        cursor = head;
    }

    public void removeNegative() {
        start();

        while (isCurrent()) {
            if (getCurrent() < 0)
                removeCurrent();
            else
                advance();
        }

        start();
    }

    public void removeMostDuplicate() {
        double[] arr = new double[manyNodes];

        start();
        for (int i = 0; isCurrent(); i++) {
            arr[i] = getCurrent();
            advance();
        }

        Arrays.sort(arr);

        double mostValue = 0;
        int mostCount = 0;
        int count = 0;

        for (int i = 0; i <= arr.length; i++) {
            if (i == arr.length || (i > 0 && !isEqual(arr[i - 1], arr[i]))) {
                if (mostCount < count) {
                    mostCount = count;
                    mostValue = arr[i - 1];
                }

                count = 1;
            } else {
                count++;
            }
        }

        double mostValue2 = 0;
        int mostCount2 = 0;
        int count2 = 0;

        for (int i = 0; i <= arr.length; i++) {
            if (i == arr.length || (i > 0 && !isEqual(arr[i - 1], arr[i]))) {
                if (mostCount2 < count2 && !isEqual(arr[i-1], mostValue)) {
                    mostCount2 = count2;
                    mostValue2 = arr[i - 1];
                }

                count2 = 1;
            } else {
                count2++;
            }
        }

        if (mostCount == mostCount2) {
            start();
            return;
        }
        else {
            start();

            while (isCurrent()) {
                if (isEqual(getCurrent(), mostValue))
                    removeCurrent();
                else
                    advance();
            }

            start();
        }
    }

    //Do not modify below methods.
    private boolean isEqual(double a, double b) {
        return Math.abs(a-b) <= 0.000001 ;
    }

    //returns String of the sequence of DoubleLinkedSeq.
    //needs size(), start(), getCurrent(), and advance().
    public String toString() {
        if(this.size() == 0) {
            return "no elements";
        }
        String ret = "";

        DoubleNode tempCursor = getCursor();
        this.start();

        ret += "many: " + this.size() + "\n";
        ret += "Sequence:";
        for(int i = 0; i < this.size(); i++) {
            ret += " "+ this.getCurrent();
            this.advance();
        }
        ret += "\n";

        if(tempCursor == null) {
            cursor = tempCursor;
        } else {
            this.start();
            while(this.getCursor() != tempCursor)
                this.advance();
        }
        return ret;
    }

    //Do not modify below methods, that is just need for grading code.
    //Also you don't need that methods for your homework

    public DoubleNode getHead() {
        return head;
    }

    public DoubleNode getCursor() {
        return cursor;
    }

    public DoubleNode getPrecursor() {
        return precursor;
    }

    public DoubleNode getTail() {
        return tail;
    }
    //////////////////////////////////////////////
}
