// File: Node.java from the package edu.colorado.nodes
// Complete documentation is available from the Node link in:
//   http://www.cs.colorado.edu/~main/docs

package cs206b;

/******************************************************************************
* A Node provides a generic node for a linked list. Each node 
* contains a piece of data (which is a reference to an E object) and a link
* (which is a reference to the next node of the list). The reference stored
* in a node can be null.
*
* @note
*   Lists of nodes can be made of any length, limited only by the amount of
*   free memory in the heap. But beyond Integer.MAX_VALUE (2,147,483,647),
*   the answer from listLength is incorrect because of arithmetic
*   overflow. 
* @see
*   <A HREF="../../../../edu/colorado/nodes/Node.java">
*   Java Source Code for this class
*   (www.cs.colorado.edu/~main/edu/colorado/nodes/Node.java) </A>
*
* @author Michael Main 
*   <A HREF="mailto:main@colorado.edu"> (main@colorado.edu) </A>
*
* @version Feb 10, 2016
* 
* @see BooleanNode
* @see ByteNode
* @see CharNode
* @see DoubleNode
* @see FloatNode
* @see IntNode
* @see LongNode
* @see ShortNode
******************************************************************************/
public class Node<E>
{
   // Invariant of the Node class:
   //   1. Each node has one reference to an E Object, stored in the instance
   //      variable data.
   //   2. For the final node of a list, the link part is null.
   //      Otherwise, the  link part is a reference to the
   //      next node of the list.
   private E data;
   private Node<E> link;   

   /**
   * Initialize a node with a specified initial data and link to the next
   * node. Note that the initialLink may be the null reference, 
   * which indicates that the new node has nothing after it.
   * @param initialData
   *   the initial data of this new node
   * @param initialLink
   *   a reference to the node after this new node--this reference may be null
   *   to indicate that there is no node after this new node.
   * @postcondition
   *   This node contains the specified data and link to the next node.
   **/   
   public Node(E initialData, Node<E> initialLink)
   {
      data = initialData;
      link = initialLink;
   }


   /**
   * Modification method to add a new node after this node.   
   * @param element
   *   the data to place in the new node
   * @postcondition
   *   A new node has been created and placed after this node.
   *   The data for the new node is element. Any other nodes
   *   that used to be after this node are now after the new node.
   * @exception OutOfMemoryError
   *   Indicates that there is insufficient memory for a new 
   *   Node. 
   **/
   public void addNodeAfter(E element)   
   {
      link = new Node<E>(element, link);
   }          
   
   
   /**
   * Accessor method to get the data from this node.   
   * @return
   *   the data from this node
   **/
   public E getData( )   
   {
      return data;
   }
   
   
   /**
   * Accessor method to get a reference to the next node after this node. 
   * @return
   *   a reference to the node after this node (or the null reference if there
   *   is nothing after this node)
   **/
   public Node<E> getLink( )
   {
      return link;                                               
   } 
    
    
   /**
   * Copy a list.
   * @param source
   *   the head of a linked list that will be copied (which may be
   *   an empty list in where source is null)
   * @param <E>
   *   type of an element in the list
   * @return
   *   The method has made a copy of the linked list starting at 
   *   source. The return value is the head reference for the
   *   copy. 
   * @exception OutOfMemoryError
   *   Indicates that there is insufficient memory for the new list.   
   **/ 
   public static <E> Node<E> listCopy(Node<E> source)
   {
      Node<E> copyHead;
      Node<E> copyTail;
      
      // Handle the special case of the empty list.
      if (source == null)
         return null;
         
      // Make the first node for the newly created list.
      copyHead = new Node<E>(source.data, null);
      copyTail = copyHead;
      
      // Make the rest of the nodes for the newly created list.
      while (source.link != null)
      {
         source = source.link;
         copyTail.addNodeAfter(source.data);
         copyTail = copyTail.link;
      }
 
      // Return the head reference for the new list.
      return copyHead;
   }
   
   
   /**
   * Copy a list, returning both a head and tail reference for the copy.
   * @param source
   *   the head of a linked list that will be copied (which may be
   *   an empty list in where source is null)
   * @param <E>
   *   type of an element in the list
   * @return
   *   The method has made a copy of the linked list starting at 
   *   source.  The return value is an
   *   array where the [0] element is a head reference for the copy and the [1]
   *   element is a tail reference for the copy.
   * @exception OutOfMemoryError
   *   Indicates that there is insufficient memory for the new list.   
   **/
   public static <E> Object[] listCopyWithTail(Node<E> source)
   {
      Node<E> copyHead;
      Node<E> copyTail;
      Object[ ] answer = new Object[2];
     
      // Handle the special case of the empty list.   
      if (source == null)
         return answer; // The answer has two null references .
      
      // Make the first node for the newly created list.
      copyHead = new Node<E>(source.data, null);
      copyTail = copyHead;
      
      // Make the rest of the nodes for the newly created list.
      while (source.link != null)
      {
         source = source.link;
         copyTail.addNodeAfter(source.data);
         copyTail = copyTail.link;
      }
      
      // Return the head and tail references.
      answer[0] = copyHead;
      answer[1] = copyTail;
      return answer;
   }
   
   
   /**
   * Compute the number of nodes in a linked list.
   * @param head
   *   the head reference for a linked list (which may be an empty list
   *   with a null head)
   * @param <E>
   *   type of an element in the list
   * @return
   *   the number of nodes in the list with the given head 
   * @note
   *   A wrong answer occurs for lists longer than Int.MAX_VALUE.
   **/   
   public static <E> int listLength(Node<E> head)
   {
      Node<E> cursor;
      int answer;
      
      answer = 0;
      for (cursor = head; cursor != null; cursor = cursor.link)
         answer++;
        
      return answer;
   }
   

   /**
   * Copy part of a list, providing a head and tail reference for the new copy. 
   * @param start
   *   references to start node of a linked list
   * @param end
   *   references to end node of a linked list
   * @param <E>
   *   type of an element in the list
   * @precondition
   *   start and end are non-null references to nodes
   *   on the same linked list,
   *   with the start node at or before the end node. 
   * @return
   *   The method has made a copy of the part of a linked list, from the
   *   specified start node to the specified end node. The return value is an
   *   array where the [0] component is a head reference for the copy and the
   *   [1] component is a tail reference for the copy.
   * @exception IllegalArgumentException
   *   Indicates that start and end do not satisfy
   *   the precondition.
   * @exception OutOfMemoryError
   *   Indicates that there is insufficient memory for the new list.    
   **/   
   public static <E> Object[] listPart(Node<E> start, Node<E> end)
   {
      Node<E> copyHead;
      Node<E> copyTail;
      Node<E> cursor;
      Object[ ] answer = new Object[2];
      
      // Check for illegal null at start or end.
      if (start == null)
         throw new IllegalArgumentException("start is null");      
      if (end == null)
         throw new IllegalArgumentException("end is null");
      
      // Make the first node for the newly created list.
      copyHead = new Node<E>(start.data, null);
      copyTail = copyHead;
      cursor = start;
      
      // Make the rest of the nodes for the newly created list.
      while (cursor != end)
      {
         cursor = cursor.link;
         if (cursor == null)
            throw new IllegalArgumentException
            ("end node was not found on the list");
         copyTail.addNodeAfter(cursor.data);
         copyTail = copyTail.link;
      }
      
      // Return the head and tail references
      answer[0] = copyHead;
      answer[1] = copyTail;
      return answer;
   }        
   
   
   /**
   * Find a node at a specified position in a linked list.
   * @param head
   *   the head reference for a linked list (which may be an empty list in
   *   which case the head is null)
   * @param position
   *   a node number
   * @param <E>
   *   type of an element in the list
   * @precondition
   *   position &gt; 0.
   * @return
   *   The return value is a reference to the node at the specified position in
   *   the list. (The head node is position 1, the next node is position 2, and
   *   so on.) If there is no such position (because the list is too short),
   *   then the null reference is returned.
   * @exception IllegalArgumentException
   *   Indicates that position is zero.    
   **/   
   public static <E> Node<E> listPosition(Node<E> head, int position)
   {
      Node<E> cursor;
      int i;
      
      if (position == 0)
           throw new IllegalArgumentException("position is zero");
      
      cursor = head;
      for (i = 1; (i < position) && (cursor != null); i++)
         cursor = cursor.link;

      return cursor;
   }


   /**
   * Search for a particular piece of data in a linked list.
   * @param head
   *   the head reference for a linked list (which may be an empty list in
   *   which case the head is null)
   * @param target
   *   a target to search for
   * @param <E>
   *   type of an element in the list
   * @return
   *   The return value is a reference to the first node that contains the
   *   specified target. If the target is non-null, then the
   *   target.equals method is used to find such a node.
   *   The target may also be null, in which case the return value is a
   *   reference to the first node that contains a null reference for its
   *   data. If there is no node that contains the target, then the null
   *   reference is returned.     
   **/   
   public static <E> Node<E> listSearch(Node<E> head, E target)
   {
      Node<E> cursor;
      
      if (target == null)
      {  // Search for a node in which the data is the null reference.
         for (cursor = head; cursor != null; cursor = cursor.link)
            if (cursor.data == null)
               return cursor;
      }
      else
      {  // Search for a node that contains the non-null target.
         for (cursor = head; cursor != null; cursor = cursor.link)
            if (target.equals(cursor.data))
               return cursor;
      }
        
      return null;
   }                                           

   
   /**
   * Modification method to remove the node after this node.   
   * @precondition
   *   This node must not be the tail node of the list.
   * @postcondition
   *   The node after this node has been removed from the linked list.
   *   If there were further nodes after that one, they are still
   *   present on the list.
   * @exception NullPointerException
   *   Indicates that this was the tail node of the list, so there is nothing
   *   after it to remove.
   **/
   public void removeNodeAfter( )   
   {
      link = link.link;
   }          
   
   
   /**
   * Modification method to set the data in this node.   
   * @param newData
   *   the new data to place in this node
   * @postcondition
   *   The data of this node has been set to newData.
   *   This data is allowed to be null.
   **/
   public void setData(E newData)   
   {
      data = newData;
   }                                                               
   
   
   /**
   * Modification method to set the link to the next node after this node.
   * @param newLink
   *   a reference to the node that should appear after this node in the linked
   *   list (or the null reference if there is no node after this node)
   * @postcondition
   *   The link to the node after this node has been set to newLink.
   *   Any other node (that used to be in this link) is no longer connected to
   *   this node.
   **/
   public void setLink(Node<E> newLink)
   {                    
      link = newLink;
   }
}
           
