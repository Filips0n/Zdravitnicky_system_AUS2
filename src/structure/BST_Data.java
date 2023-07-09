package structure;

public abstract class BST_Data<T> {
    T key;

    public BST_Data(T key) {
        this.key = key;
    }

    public BST_Data() {

    }

    public T getKey() {return key;}

    public abstract int compareTo(BST_Data<T> nodeData);
    public abstract int compareToKey(T key);
}
