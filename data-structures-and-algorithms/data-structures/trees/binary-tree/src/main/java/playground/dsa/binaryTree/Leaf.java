package playground.dsa.binaryTree;



public class Leaf implements Node {

    private Object key;
    private Node parent;

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

        return null;
    }

    @Override
    public void setLeftChild(Node n) {

        throw new OperationNotSupportedException();
    }

    @Override
    public Node getRightChild() {

        return null;
    }

    @Override
    public void setRightChild(Node n) {

        throw new OperationNotSupportedException();
    }

    @Override
    public boolean isLeaf() {

        return true;
    }

    @Override
    public boolean isInternalNode() {

        return false;
    }
}
