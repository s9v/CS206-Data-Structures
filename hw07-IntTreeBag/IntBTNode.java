// File: IntBTNode.java from the package edu.colorado.nodes
// Complete documentation is available from the IntBTNode link in:
//   http://www.cs.colorado.edu/~main/docs

package elice;

/******************************************************************************
* A <CODE>IntBTNode</CODE> provides a node for a binary tree. Each node 
* contains a piece of data (which is a reference to an object) and references
* to a left and right child. The references to children may be null to indicate
* that there is no child. The reference stored in a node can also be null.
*
* <b>Limitations:</b> 
*   Beyond <CODE>Int.MAX_VALUE</CODE> elements, <CODE>treeSize</CODE>, is
*   wrong. 
*
* <b>Java Source Code for this class:</b>
*   <A HREF="../../../../edu/colorado/nodes/IntBTNode.java">
*   http://www.cs.colorado.edu/~main/edu/colorado/nodes/IntBTNode.java </A>
*
* @author Michael Main 
*   <A HREF="mailto:main@colorado.edu"> (main@colorado.edu) </A>
*
* @version Feb 10, 2016
*
* @see BTNode
* @see BooleanBTNode
* @see ByteBTNode
* @see CharBTNode
* @see DoubleBTNode
* @see FloatBTNode
* @see LongBTNode
* @see ShortBTNode
******************************************************************************/
public class IntBTNode
{
   // Invariant of the IntBTNode class:
   //   1. Each node has one integer, stored in the instance
   //      variable data.
   //   2. The instance variables left and right are references to the node's
   //      left and right children.
   private int data;
   private IntBTNode left, right;   

   /**
   * Initialize a <CODE>IntBTNode</CODE> with a specified initial data and links
   * children. Note that a child link may be the null reference, 
   * which indicates that the new node does not have that child.
   * @param initialData
   *   the initial data of this new node
   * @param initialLeft
   *   a reference to the left child of this new node--this reference may be null
   *   to indicate that there is no node after this new node.
   * @param initialRight
   *   a reference to the right child of this new node--this reference may be null
   *   to indicate that there is no node after this new node.
   * <b>Postcondition:</b>
   *   This node contains the specified data and links to its children.
   **/   
   public IntBTNode(int initialData, IntBTNode initialLeft, IntBTNode initialRight)
   {
      data = initialData;
      left = initialLeft;
      right = initialRight;
   }       
   
   
   /**
   * Accessor method to get the data from this node.   
   * @return
   *   the data from this node
   **/
   public int getData( )   
   {
      return data;
   }
   
   
   /**
   * Accessor method to get a reference to the left child of this node. 
   * @return
   *   a reference to the left child of this node (or the null reference if there
   *   is no left child)
   **/
   public IntBTNode getLeft( )
   {
      return left;                                               
   } 
   
   
   /**
   * Accessor method to get the data from the leftmost node of the tree below 
   * this node.
   * @return
   *   the data from the deepest node that can be reached from this node by
   *   following left links.
   **/
   public int getLeftmostData( )
   {
      if (left == null)
         return data;
      else
         return left.getLeftmostData( );
   }
      
   
   /**
   * Accessor method to get the data from the rightmost node of the tree below 
   * this node.
   * @return
   *   the data from the deepest node that can be reached from this node by
   *   following right links.
   **/
   public int getRightmostData( )
   {
      if (right == null)
         return data;
      else
         return right.getRightmostData( );
   }
   
   
   /**
   * Accessor method to get a reference to the right child of this node. 
   * @return
   *   a reference to the right child of this node (or the null reference if there
   *   is no right child)
   **/
   public IntBTNode getRight( )
   {
      return right;                                               
   } 


   /**
   * Uses an inorder traversal to print the data from each node at or below
   * this node of the binary tree.
   * <b>Postcondition:</b>
   *   The data of this node and all its descendants have been writeen by
   *   <CODE>System.out.println( )</CODE> using an inorder traversal.
   **/
   public String inorderPrint( )
   {
      String ret = "";      
      if (left != null)
         ret += left.inorderPrint( );
      ret += " "+data;
      if (right != null)
         ret += right.inorderPrint( );
      return ret;
   }  

   
   /**
   * Accessor method to determine whether a node is a leaf. 
   * @return
   *   <CODE>true</CODE> (if this node is a leaf) or 
   *   <CODE>false</CODE> (if this node is not a leaf.
   **/
   public boolean isLeaf( )
   {
      return (left == null) && (right == null);                                               
   } 


   /**
   * Uses a preorder traversal to print the data from each node at or below
   * this node of the binary tree.
   * <b>Postcondition:</b>
   *   The data of this node and all its descendants have been writeen by
   *   <CODE>System.out.println( )</CODE> using a preorder traversal.
   **/
   public String preorderPrint( )
   {
      String ret = " "+data;
      if (left != null)
         ret += left.preorderPrint( );
      if (right != null)
         ret += right.preorderPrint( );
      return ret;
   } 
   
      
   /**
   * Uses a postorder traversal to print the data from each node at or below
   * this node of the binary tree.
   * <b>Postcondition:</b>
   *   The data of this node and all its descendants have been writeen by
   *   <CODE>System.out.println( )</CODE> using a postorder traversal.
   **/
   public String postorderPrint( )
   {
      String ret = "";      
      if (left != null)
         ret += left.postorderPrint( );
      if (right != null)
         ret += right.postorderPrint( );
      ret += " "+data;
      return ret;
   }   


   /**
   * Uses an inorder traversal to print the data from each node at or below
   * this node of the binary tree, with indentations to indicate the depth
   * of each node.
   * @param depth
   *   the depth of this node (with 0 for root, 1 for the root's
   *   children, and so on)(
   * <b>Precondition:</b>
   *   <CODE>depth</CODE> is the depth of this node.
   * <b>Postcondition:</b>
   *   The data of this node and all its descendants have been writeen by
   *   <CODE>System.out.println( )</CODE> using an inorder traversal.
   *   The indentation of each line of data is four times its depth in the
   *   tree. A dash "--" is printed at any place where a child has no
   *   sibling.
   **/
   public void print(int depth)
   {
      int i;
   
      // Print the indentation and the data from the current node:
      for (i = 1; i <= depth; i++)
         System.out.print("    ");
      System.out.println(data);

      // Print the left subtree (or a dash if there is a right child and no left child)   
      if (left != null)
         left.print(depth+1);
      else if (right != null)
      {
         for (i = 1; i <= depth+1; i++)
            System.out.print("    ");
         System.out.println("--");
      }

      // Print the right subtree (or a dash if there is a left child and no left child)  
      if (right != null)
         right.print(depth+1);
      else if (left != null)
      {
         for (i = 1; i <= depth+1; i++)
            System.out.print("    ");
         System.out.println("--");
      }
   }
   

   /**
   * Remove the leftmost most node of the tree below this node.
   * @return
   *   The tree starting at this node has had its leftmost node removed (i.e.,
   *   the deepest node that can be reached by following left links). The
   *   return value is a reference to the root of the new (smaller) tree.
   *   This return value could be null if the original tree had only one
   *   node (since that one node has now been removed).
   **/
   public IntBTNode removeLeftmost( )
   {
      if (left == null)
         return right;
      else
      {
         left = left.removeLeftmost( );
         return this;
      }
   }


   /**
   * Remove the rightmost most node of the tree below this node.
   * @return
   *   The tree starting at this node has had its rightmost node removed (i.e.,
   *   the deepest node that can be reached by following right links). The
   *   return value is a reference to the root of the new (smaller) tree.
   *   This return value could be null if the original tree had only one
   *   node (since that one node has now been removed).
   **/
   public IntBTNode removeRightmost( )
   {
      if (right == null)
         return left;
      else
      {
         right = right.removeRightmost( );
         return this;
      }
   }
       
   /**
   * Modification method to set the data in this node.   
   * @param newData
   *   the new data to place in this node
   * <b>Postcondition:</b>
   *   The data of this node has been set to <CODE>newData</CODE>.
   **/
   public void setData(int newData)   
   {
      data = newData;
   }                                                               
   
   
   /**
   * Modification method to set the link to the left child of this node.
   * @param newLeft
   *   a reference to the node that should appear as the left child of this node
   *  (or the null reference if there is no left child for this node)
   * <b>Postcondition:</b>
   *   The link to the left child of this node has been set to <CODE>newLeft</CODE>.
   *   Any other node (that used to be the left child) is no longer connected to
   *   this node.
   **/
   public void setLeft(IntBTNode newLeft)
   {                    
      left = newLeft;
   }
    
    
   /**
   * Modification method to set the link to the right child of this node.
   * @param newRight
   *   a reference to the node that should appear as the right child of this node
   *  (or the null reference if there is no right child for this node)
   * <b>Postcondition:</b>
   *   The link to the right child of this node has been set to <CODE>newRight</CODE>.
   *   Any other node (that used to be the right child) is no longer connected to
   *   this node.
   **/
   public void setRight(IntBTNode newRight)
   {                    
      right = newRight;
   }  
    
    
   /**
   * Copy a binary tree.
   * @param source
   *   a reference to the root of a binary tree that will be copied (which may be
   *   an empty tree where <CODE>source</CODE> is null)
   * @return
   *   The method has made a copy of the binary tree starting at 
   *   <CODE>source</CODE>. The return value is a reference to the root of the copy. 
   * @exception OutOfMemoryError
   *   Indicates that there is insufficient memory for the new tree.   
   **/ 
   public static IntBTNode treeCopy(IntBTNode source)
   {
      IntBTNode leftCopy, rightCopy;

      if (source == null)
         return null;
      else
      {
         leftCopy = treeCopy(source.left);
         rightCopy = treeCopy(source.right);
         return new IntBTNode(source.data, leftCopy, rightCopy);
      }
   }
   

   /**
   * Count the number of nodes in a binary tree.
   * @param root
   *   a reference to the root of a binary tree (which may be
   *   an empty tree where <CODE>source</CODE> is null)
   * @return
   *   the number of nodes in the binary tree
   * <b>Note:</b>
   *   A wrong answer occurs for trees larger than 
   *   <CODE>INT.MAX_VALUE</CODE>.    
   **/ 
   public static int treeSize(IntBTNode root)
   {
      if (root == null)
         return 0;
      else
         return 1 + treeSize(root.left) + treeSize(root.right);
   }   

}
           