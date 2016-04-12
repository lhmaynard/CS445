import java.util.*;

/**
 * Created by Lenny on 12/5/15.
 */
public class TernaryTree<T> implements TernaryTreeInterface<T> {
    private TernaryNode<T> root;

    public TernaryTree() {
        root = null;
    } // end default constructor

    public TernaryTree(T rootData) {
        root = new TernaryNode<T>(rootData);
    } // end constructor

    public TernaryTree(T rootData, TernaryTree<T> leftTree, TernaryTree<T> middleTree, TernaryTree<T> rightTree) {
        privateSetTree(rootData, leftTree, middleTree, rightTree);
    } // end constructor

    public void setTree(T rootData) {
        root = new TernaryNode<T>(rootData);
    } // end setTree

    public void setTree(T rootData, TernaryTreeInterface<T> leftTree, TernaryTreeInterface<T> middleTree,
                        TernaryTreeInterface<T> rightTree) {
        privateSetTree(rootData, (TernaryTree<T>)leftTree, (TernaryTree<T>)middleTree,
                (TernaryTree<T>)rightTree);
    } // end setTree

    private void privateSetTree(T rootData, TernaryTree<T> leftTree, TernaryTree<T> middleTree, TernaryTree<T> rightTree) {
        root = new TernaryNode<T>(rootData);

        if ((leftTree != null) && !leftTree.isEmpty()) {
            root.setLeftChild(leftTree.root);
        }

        if ((middleTree != null) && !middleTree.isEmpty()) {
            if (middleTree != leftTree) {
                root.setMiddleChild(middleTree.root);
            } else {
                root.setMiddleChild(middleTree.root.copy());
            }
        }

        if ((rightTree != null) && !rightTree.isEmpty()) {
            if (rightTree != leftTree && rightTree != middleTree) {
                root.setRightChild(rightTree.root);
            } else {
                root.setRightChild(rightTree.root.copy());
            }
        } // end if


    } // end privateSetTree

    public T getRootData() {
        if (isEmpty()) {
            throw new EmptyTreeException();
        } else {
            return root.getData();
        }
    } // end getRootData

    public boolean isEmpty() {
        return root == null;
    } // end isEmpty

    public void clear() {
        root = null;
    } // end clear

    protected void setRootData(T rootData) {
        root.setData(rootData);
    } // end setRootData

    protected void setRootNode(TernaryNode<T> rootNode) {
        root = rootNode;
    } // end setRootNode

    protected TernaryNode<T> getRootNode() {
        return root;
    } // end getRootNode

    public int getHeight() {
        return root.getHeight();
    } // end getHeight

    public int getNumberOfNodes() {
        return root.getNumberOfNodes();
    } // end getNumberOfNodes

    public Iterator<T> getPreorderIterator() {
        return new PreorderIterator();
    } // end getPreorderIterator

    public Iterator<T> getInorderIterator() {
        throw new UnsupportedOperationException();
        // inOrder is not supported for a ternary tree because it is impossible to place
        // the root node in the middle of 3 children
    } // end getInorderIterator

    public Iterator<T> getPostorderIterator() {
        return new PostorderIterator();
    } // end getPostorderIterator

    public Iterator<T> getLevelOrderIterator() {
        return new LevelOrderIterator();
    } // end getLevelOrderIterator



    private class PreorderIterator implements Iterator<T> {
        private Stack<TernaryNode<T>> nodeStack;

        public PreorderIterator() {
            nodeStack = new Stack<TernaryNode<T>>();
            if (root != null) {
                nodeStack.push(root);
            }
        } // end default constructor

        public boolean hasNext() {
            return !nodeStack.isEmpty();
        } // end hasNext

        public T next() {
            TernaryNode<T> nextNode;

            if (hasNext()) {
                nextNode = nodeStack.pop();
                TernaryNode<T> leftChild = nextNode.getLeftChild();
                TernaryNode<T> middleChild = nextNode.getMiddleChild();
                TernaryNode<T> rightChild = nextNode.getRightChild();

                // Push into stack in reverse order of recursive calls
                if (rightChild != null) {
                    nodeStack.push(rightChild);
                }

                if (middleChild != null) {
                    nodeStack.push(middleChild);
                }

                if (leftChild != null) {
                    nodeStack.push(leftChild);
                }
            } else {
                throw new NoSuchElementException();
            }

            return nextNode.getData();
        } // end next

        public void remove() {
            throw new UnsupportedOperationException();
        } // end remove
    } // end PreorderIterator



    private class PostorderIterator implements Iterator<T> {
        private Stack<TernaryNode<T>> nodeStack;
        private TernaryNode<T> currentNode;

        public PostorderIterator() {
            nodeStack = new Stack<TernaryNode<T>>();

            Stack<TernaryNode<T>> tempStack = new Stack<TernaryNode<T>>();
            tempStack  = postOrder(root,tempStack);

            while(!tempStack.empty()){
                nodeStack.push(tempStack.pop());
            }
            currentNode = root;
        } // end default constructor
        Stack<TernaryNode<T>> postOrder (TernaryNode root, Stack<TernaryNode<T>> s)
        {

            if(root == null) return s;

            s = postOrder( root.getLeftChild(), s);
            s = postOrder(root.getMiddleChild(),s);
            s =postOrder( root.getRightChild(), s );

            s.push(root);
            return s;

        }
        public boolean hasNext() {
            return !nodeStack.isEmpty();
        } // end hasNext
        public T next() {
            return nodeStack.pop().getData();

        } // end next

        public void remove() {
            throw new UnsupportedOperationException();
        } // end remove

    } // end PostorderIterator

    private class LevelOrderIterator implements Iterator<T> {
        private LinkedList<TernaryNode<T>> nodeQueue;

        public LevelOrderIterator() {
            nodeQueue = new LinkedList<TernaryNode<T>>();
            if (root != null) {
                nodeQueue.add(root);
            }
        } // end default constructor

        public boolean hasNext() {
            return nodeQueue.peek() != null;
        } // end hasNext

        public T next() {
            TernaryNode<T> nextNode;

            if (hasNext()) {
                nextNode = nodeQueue.pollFirst();
                TernaryNode<T> leftChild = nextNode.getLeftChild();
                TernaryNode<T> middleChild = nextNode.getMiddleChild();
                TernaryNode<T> rightChild = nextNode.getRightChild();

                // Add to queue in order of recursive calls
                if (leftChild != null) {
                    nodeQueue.addLast(leftChild);
                }

                if (middleChild != null) {
                    nodeQueue.addLast(middleChild);
                }

                if (rightChild != null) {
                    nodeQueue.addLast(rightChild);
                }
            } else {
                throw new NoSuchElementException();
            }

            return nextNode.getData();
        } // end next

        public void remove() {
            throw new UnsupportedOperationException();
        } // end remove
    } // end LevelOrderIterator
} // end BinaryTree

