package playground.dsa.binaryTree;

public class InternalNode implements Node {

    private Object key;
    private Node parent;
    private Node left;
    private Node right;

    @Override
    public Object getKey() {

        return key;
    }

    @Override
    public void setKey(Object o) {

        this.key = o;
    }

    @Override
    public Node getParent() {

        return parent;
    }

    @Override
    public void setParent(Node n) {

        this.parent = n;
    }

    @Override
    public Node getLeftChild() {

        return left;
    }

    @Override
    public void setLeftChild(Node n) {

        this.left = n;

    }

    @Override
    public Node getRightChild() {

        return right;
    }

    @Override
    public void setRightChild(Node n) {

        this.right = n;
    }

    @Override
    public boolean isLeaf() {

        return false;
    }

    @Override
    public boolean isInternalNode() {

        return true;
    }
}
