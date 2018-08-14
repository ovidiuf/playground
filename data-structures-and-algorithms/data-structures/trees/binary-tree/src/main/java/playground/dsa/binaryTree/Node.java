package playground.dsa.binaryTree;

public interface Node {

    Object getKey();
    void setKey(Object o);

    Node getParent();
    void setParent(Node n);

    Node getLeftChild();
    void setLeftChild(Node n);

    Node getRightChild();
    void setRightChild(Node n);

    boolean isLeaf();
    boolean isInternalNode();

}
