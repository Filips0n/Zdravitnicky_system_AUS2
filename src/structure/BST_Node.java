package structure;

public class BST_Node<T> {
    private BST_Node<T> leftSon;
    private BST_Node<T> rightSon;
    private BST_Node<T> parent;
    private BST_Data<T> data;

    public BST_Node(BST_Data<T> pData) {
        this.data = pData;
    }

    public boolean hasLeftSon(){return leftSon != null;}
    public boolean hasRightSon(){return rightSon != null;}

    public void setRightSon(BST_Node<T> newNode){this.rightSon = newNode;}
    public void setLeftSon(BST_Node<T> newNode){this.leftSon = newNode;}
    public void setParent(BST_Node<T> newNode){this.parent = newNode;}

    public BST_Node<T> getLeftSon(){return leftSon;}
    public BST_Node<T> getRightSon(){return rightSon;}
    public BST_Node<T> getParent(){return parent;}

    public BST_Data<T> getData() {
        return data;
    }

    public boolean isLeaf(){return leftSon == null && rightSon ==null;}
}
