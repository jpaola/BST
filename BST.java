/*
 * This program represents a BST. A BST is a node based binary tree data structure
 * which has the following properties: 
 * i. The left sub-tree of a node contains only nodes with keys less than the 
 *     node's key.
 * ii. The right sub-tree of a node contains only nodes with keys greater than the 
 *     node's key.
 * iii. The left and right sub-tree must each also be a binary search tree.
 * 
 * This program includes node insertion, node removal, presence of a node in the 
 * BST, and a method to print all of the key values in sorted order (ascending).
 */
package BST;

import java.util.Random;

/*
 * @author Paola Jiron 
 */
// Programmer defined Binary Node class
class BinaryNode<E extends Comparable>
{

    // instance var's
    E data;
    BinaryNode<E> left, right;

    /**
     * Default Binary Node constructor.
     * @param node The key node being initialized.
     */
    BinaryNode(E node)
    {
        this(node, null, null);
    }

    /**
     * Constructor for a node with passed left and right values.
     * @param node The root node.
     * @param l_node The left key node.
     * @param r_node The right key node.
     */
    BinaryNode(E node, BinaryNode l_node, BinaryNode r_node)
    {
        data = node;
        left = l_node;
        right = r_node;
    }
} // end of BinaryNode class definitions

public class BST<E extends Comparable<E>>
{

    // instance var's
    private BinaryNode<E> root;

    /**
     * Constructor for the BST which initializes the root node.
     */
    BST()
    {
        root = null;
    }

    /**
     * Utility method to insert a node into the BST.
     * @param new_node The new node.
     * @param node The root node.
     * @return The node once inserted in the BST.
     */
    private BinaryNode<E> insert(E new_node, BinaryNode<E> root_node)
    {
        // if BST is empty, new node becomes the root

        if (root_node == null)
        {
            return new BinaryNode<>(new_node, null, null);
        }
        else
        {
            // insert in left sub-tree if less than key

            if (new_node.compareTo(root_node.data) < 0)
            {
                root_node.left = insert(new_node, root_node.left);
            }
            // otherwise, insert in right sub-tree if greater than key

            else
            {
                root_node.right = insert(new_node, root_node.right);
            }
            return (root_node); // return root key after new node insertion      
        }

    }

    /**
     * Inserts a node into the BST.
     * @param node The new node to be inserted into the BST.
     */
    public void insert(E node)
    {
        root = insert(node, root);
    }

    /**
     * Utility method to check if a particular node exists within the BST.
     * @param node The node to check.
     * @param root_node The root of the BST.
     * @return True if the node is in the BST; False, otherwise.
     */
    private boolean contains(E node, BinaryNode<E> root_node)
    {
        if (root_node == null) // returns false if the tree is empty
        {
            return false;
        }

        if (node.compareTo(root_node.data) < 0) // check the left sub-tree
        {
            return contains(node, root_node.left);
        }
        else if (node.compareTo(root_node.data) > 0) // check the right sub-tree
        {
            return contains(node, root_node.right);
        }
        else   // if here, then..
        {
            return true;    // return true (node exists in the BST)
        }
    }

    /**
     * Checks if a node already exists within the BST.
     * @param node The node.
     * @return True if it exists within the BST; False, otherwise.
     */
    public boolean contains(E node)
    {
        return contains(node, root);
    }

    /**
     * Removes a node from the BST and reestablishes balance in the tree.
     * @param value The node to remove.
     * @param root_node The key of the tree from which to remove.
     * @return The updated tree(key).
     */
    public BinaryNode<E> removeNode(E value, BinaryNode<E> root_node)
    {
        BinaryNode<E> tempNode = root_node;
        
        if (contains(value))
        {
            if (value.compareTo(root_node.data) < 0) // node is < the root key
            {
                root_node.left = removeNode(value, root_node.left);
            }
            else if (value.compareTo(root_node.data) > 0) // node is > the root key
            {
                root_node.right = removeNode(value, root_node.right);
            }
            else
            {
                // if here, we found the node to remove
                tempNode = remove(root_node);
            }
        }
        return (tempNode);
    }

    /**
     * Removes a node from the tree. It handles 3 cases: 1) node with left child
     * only, node with right child only, node with both left and right children.
     * @param root_node The node to remove.
     * @return The updated key.
     */
    private BinaryNode<E> remove(BinaryNode<E> root_node)
    {
        BinaryNode<E> node = root_node;
        
        if (root_node.left != null && root_node.right != null) // 2 children
        {
            root_node.data = percolateUp(root_node.right).data; // defines new root for sub-tree
            root_node.right = removeNode(root_node.data, root_node.right); // remove the stray
        }

        else if (root_node.left != null && root.right == null) // node with left child
        {
            node = root_node.left;
        }
        else // node with right child
        {
            node = root_node.right;
        }
        return (node);
    }

    /**
     * Returns the node with minimum value. To be used when removing a node with
     * two children.
     * @param node The node.
     * @return The node with smallest value from right sub-tree.
     */
    private BinaryNode<E> percolateUp(BinaryNode<E> node)
    {
        if (node == null)
        {
            return null;
        }
        else if (node.left == null)
        {
            return node;
        }
        return percolateUp(node.left);
    }

    /**
     * Prints the BST if it is not null, else it displays an error message.
     */
    public void print()
    {
        if (root != null)
        {
            System.out.println(printTree(root));
        }
        else
        {
            System.err.println("\nEmpty tree.");
        }

    }

    /**
     * Utility method to print the BST.
     * @param node The root of the BST.
     * @return A string version of the BST.
     */
    private String printTree(BinaryNode<E> node)
    {
        String tree = "";
        if (node != null)
        {

            tree = printTree(node.left) + " " + node.data
                 + " " + printTree(node.right);

        }
        return tree;
    }

    public static void main(String[] args)
    {
        BST<Integer> tree = new BST<>();
        BST<Integer> tree2 = new BST<>();  // for empty tree check purposes only

        Random generate = new Random();
        int num = generate.nextInt(23);

        // populate the tree
        tree.insert(20);
        tree.insert(8);
        tree.insert(5);
        tree.insert(10);
        tree.insert(9);
        tree.insert(15);
        tree.insert(22);

        System.out.print("BST: ");
        tree.print();

        System.out.printf("\nIs %d in the tree? ", num);

        if (tree.contains(num))
        {
            System.out.println("YES");
            System.out.printf("Removing %d \n", num);
        }
        else
        {
            System.out.println("NO");
        }

        tree.removeNode(num, tree.root);
        System.out.print("\nBST: ");
        tree.print();

    } // end of main definitions
}   // end of BST class definitions
