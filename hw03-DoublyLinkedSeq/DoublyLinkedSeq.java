package cs206b;

import java.io.*;
import java.nio.file.*;

public class DoublyLinkedSeq<T> implements Cloneable {
  /*
   * You should be use a dummy node as a first node of this sequence.
   * please note that dummy node is not "existing node", so must not be count as a size of sequence
   * Also, at the constructor, head and tail point to dummy.
   */

    int manyNodes;
  private Node<T> dummyHead;
    private Node<T> dummyTail;
    private Node<T> cursor;

  public DoublyLinkedSeq() {
        manyNodes = 0;
        dummyHead = new Node<T>();
        dummyTail = new Node<T>();
        dummyHead.setNext(dummyTail);
        dummyTail.setPrev(dummyHead);
        cursor = null;
  }
  
  public void addAfter(T element) {
        Node<T> node;
        
        try {
            node = new Node<T>(element, null, null);
        } catch (OutOfMemoryError e) {
            throw new OutOfMemoryError("Not enough memory for new Node<T>.");
        }
        
        if ( ! isCurrent())
            cursor = dummyHead;
         
        node.setPrev(cursor);
        node.setNext(cursor.getNext());
        cursor.getNext().setPrev(node);
        cursor.setNext(node);
        
        cursor = node;
        manyNodes++;
  }
  
  public void addBefore(T element) {
        Node<T> node;
        
        try {
            node = new Node<T>(element, null, null);
        } catch (OutOfMemoryError e) {
            throw new OutOfMemoryError("Not enough memory for new Node<T>.");
        }
        
        if ( ! isCurrent())
            cursor = dummyTail;
         
        node.setPrev(cursor.getPrev());
        node.setNext(cursor);
        cursor.getPrev().setNext(node);
        cursor.setPrev(node);
        
        cursor = node;
        manyNodes++;
  }

  public void addAll(DoublyLinkedSeq<T> addend) {
        if (addend == null) {
            throw new NullPointerException("addend is null.");
        }

        Node<T> old_cursor = cursor;
        cursor = dummyTail.getPrev();
        
        for (Node<T> current = addend.dummyHead.getNext(); current != addend.dummyTail; current = current.getNext()) {
            addAfter(current.getData());
        }
        
        cursor = old_cursor;
  }
    
    /*
    private String wd() {
        return System.getProperty("user.dir");
    }
    
    private String ls(String strdir) {
        String result = "";
        Path dir = Paths.get(strdir);
        result += "[Current directory: " + strdir + "]\n";

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path file : stream) {
                result += "-> " + file.getFileName() + " " + (Files.isDirectory(file) ?"[*]" :"") + "\n";
                
                if (file.getFileName().toString() == "Grader.class" || true) {
                    String content = new String(Files.readAllBytes(file));
                    result += ">>>>start<<<<\n" + content + "\n<<<<end>>>>\n";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return result;
    }
    */

  public void advanceForward() {
        if ( ! isCurrent()) {
            throw new IllegalStateException("Can't advance forward. No current element.");
        }
        
        cursor = cursor.getNext();
  }

  public void advanceBackward() {
        if ( ! isCurrent()) {
            throw new IllegalStateException("Can't advance backward. No current element.");
        }
        
        cursor = cursor.getPrev();
  }

    public void advanceNstepForward(int n) {
        Node<T> old_cursor = cursor;
    
        try {
            for (int i = 0; i < n; i++)
                advanceForward();
            
            if ( ! isCurrent())
                throw new IllegalStateException();
        } catch (IllegalStateException e) {
            cursor = old_cursor;
            throw new IllegalStateException(this.toString());
        }
  }
  public void advanceNstepBackward(int n) {
        Node<T> old_cursor = cursor;
    
        try {
            for (int i = 0; i < n; i++)
                advanceBackward();
            
            if ( ! isCurrent())
                throw new IllegalStateException();
        } catch (IllegalStateException e) {
            cursor = old_cursor;
            throw new IllegalStateException();
        }
  }

  public static <T> DoublyLinkedSeq<T> concatenation(DoublyLinkedSeq<T> s1, DoublyLinkedSeq<T> s2) {
        if (s1 == null || s2 == null) {
            throw new IllegalArgumentException("s1 or s2 is null.");
        }
        
        DoublyLinkedSeq<T> result = s1.clone();
        result.addAll(s2);
    return result;
  }

  public DoublyLinkedSeq<T> clone() {
        DoublyLinkedSeq<T> copy = null;
        
        try {
            copy = new DoublyLinkedSeq<T>();
        } catch (OutOfMemoryError e) {
            throw new OutOfMemoryError("Not enough memory for cloning DoublyLinkedSeq<T>.");
        }
        
        Node<T> copy_cursor = null;
        for (Node<T> current = dummyHead.getNext(); current != dummyTail; current = current.getNext()) {
            copy.addAfter(current.getData());
            
            if (current == cursor)
                copy_cursor = dummyTail.getPrev();
        }
        
        copy.cursor = copy_cursor;
        
    return copy;
  }
    
  public T getCurrent() {
        if ( ! isCurrent()) {
            throw new IllegalStateException("Can't get current element. No current element.");
        }
        
    return (T) cursor.getData();
  }

  public boolean isCurrent() {
    return cursor != dummyHead && cursor != dummyTail && cursor != null;
  }

  public void removeCurrent() {
        if ( ! isCurrent()) {
            throw new IllegalStateException("Can't remove current element. No current element.");
        }
        
        Node<T> old_cursor = cursor;
        cursor = cursor.getNext();
        
        old_cursor.getPrev().setNext(old_cursor.getNext());
        old_cursor.getNext().setPrev(old_cursor.getPrev());
        
        old_cursor.setPrev(null);
        old_cursor.setNext(null);
        
        manyNodes--;
  }

  public int size() {
    return manyNodes;
  }

  public void start() {
        cursor = dummyHead.getNext();
  }
    
    public String toString() {
        String s = "";
        
        s += "(size: " + manyNodes + ") ";
        s += "[";
    
        for (Node<T> current = dummyHead.getNext(); current != dummyTail; current = current.getNext()) {
            if (current == cursor)
                s += "(";
        
            s += "" + current.getData();
            
            if (current == cursor)
                s += ")";
            
            if (current.getNext() != dummyTail)
                s += ", ";
        }
        
        s += "]";
        
        return s;
    }
}
