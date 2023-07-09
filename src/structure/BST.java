package structure;

import java.util.*;

public class BST<T> {
    private BST_Node<T> root = null;
    private int size = 0;

    public BST() {}

    public boolean insertData(BST_Data<T> newData){
        return insertData(newData, true);
    }
    //Composite
    public boolean insertDataComposite(T newData){
        return insertDataComposite(newData, true);
    }

    public boolean insertData(BST_Data<T> newData, boolean balanceNewNode){
        if (root == null){
            root = new BST_Node<T>(newData);
        } else {
            BST_Node<T> newNode = new BST_Node<T>(newData);
            BST_Node<T> parentNode = null;
            BST_Node<T> currentNode = root;

            while (currentNode != null){
                parentNode = currentNode;
                if (parentNode.getData().compareTo(newNode.getData()) == 0){return false;}
                if (parentNode.hasLeftSon() && parentNode.getLeftSon().getData().compareTo(newNode.getData()) == 0){ return false;}
                if (parentNode.hasRightSon() && parentNode.getRightSon().getData().compareTo(newNode.getData()) == 0){ return false;}
                if (currentNode.getData().compareTo(newNode.getData()) == -1){
                    currentNode = currentNode.getLeftSon();
                } else {
                    currentNode = currentNode.getRightSon();
                }
            }

            newNode.setParent(parentNode);
            if(parentNode.getData().compareTo(newNode.getData()) == -1){
                parentNode.setLeftSon(newNode);
            } else {
                parentNode.setRightSon(newNode);
            }
            if(balanceNewNode) {balanceNode(newNode);};
        }
        size++;
        return true;
    }

    public BST_Data<T> findData(T key){
        BST_Node<T> node = findNode(key);
        if (node == null) {return null;}
        return node.getData();
    }

    public BST_Node<T> findNode(T key){
        if (root == null) {return null;}
        BST_Node<T> currentNode = root;
        while (currentNode.getData().compareToKey(key) != 0){
            if (currentNode.getData().compareToKey(key) == -1){
                if (currentNode.hasLeftSon()){
                    currentNode = currentNode.getLeftSon();
                } else {
                    return null;
                }
            } else if (currentNode.getData().compareToKey(key) == 1){
                if (currentNode.hasRightSon()){
                    currentNode = currentNode.getRightSon();
                } else {
                    return null;
                }
            }
        }
        return currentNode;
    }
    //Composite
    public BST_Node findNodeComposite(BST_Data key, boolean lastFinded){
        if (root == null) {return null;}
        BST_Node currentNode = root;
        while (currentNode.getData().compareTo(key) != 0){
            if (currentNode.getData().compareTo(key) == -1){
                if (currentNode.hasLeftSon()){
                    currentNode = currentNode.getLeftSon();
                } else {
                    if (lastFinded) {return currentNode;}
                    return null;
                }
            } else if (currentNode.getData().compareTo(key) == 1){
                if (currentNode.hasRightSon()){
                    currentNode = currentNode.getRightSon();
                } else {
                    if (lastFinded) {return currentNode;}
                    return null;
                }
            }
        }
        return currentNode;
    }
    //Composite
    public BST_Data findDataComposite(BST_Data key, boolean lastFinded){
        BST_Node node = findNodeComposite(key, lastFinded);
        if (node == null) {return null;}
        return node.getData();
    }

    public ArrayList<BST_Data> intervalFind(BST_Data min, BST_Data max) {
        ArrayList<BST_Data> list = new ArrayList<>();
        BST_Node startNode = findStartNode(min, true);

        if (startNode == null) {return null;}
        if(startNode.hasRightSon()) {
            if (startNode.getData().compareTo(min) == -1 && startNode.getRightSon().getData().compareTo(min) == 1) {return null;}
        }

        BST_Node currentNode = startNode;

        boolean added = true;
        while(currentNode.getData().compareTo(max) != -1) {
            if (startNode.getData().compareTo(min) == 1 && added) {
                currentNode = getSuccessor(startNode);
                if (currentNode == null) {break;}
                if (currentNode.getData().compareTo(max) == -1) {break;}
                added=false;
            }
            list.add(currentNode.getData());
            currentNode = getSuccessor(currentNode);
            if (currentNode == null) {break;}
        }
        return list;
    }

    public boolean removeNodeByKey(T key){
        if (root == null) {return false;}
        BST_Node<T> node = findNode(key);
        boolean result = false;
        if (node != null) {
            result = removeNode(node);
            if (result){
                size--;
            }
        }
        return result;
    }

    public boolean removeNodeComposite(BST_Node key){
        return removeNode(key);
    }

    public void balance(){
        //System.out.println("Balancing tree");
        ArrayList<BST_Node<T>> allNodes;
        ArrayList<BST_Node<T>> inOrder;
        BST_Node<T> median;
        BST_Node<T> currentTopParent;
        //System.out.println("------------");
        if (levelOrder() == null) {return;}
        for (int i = 0; i < levelOrder().size(); i++) {
            allNodes = levelOrder();
            inOrder = inOrder(allNodes.get(i));
            median = inOrder.get((inOrder.size()/2));
            currentTopParent = allNodes.get(i).getParent();
            //System.out.println(median.getData().getKey());
            while(currentTopParent != median.getParent()){
                if (median.getParent().getRightSon() == median){
                    leftRotation(median);
                } else {
                    rightRotation(median);
                }
            }
        }

        //checkTree();
    }

    public ArrayList<BST_Node<T>> levelOrder(){
        if (root == null) {return null;}
        ArrayList<BST_Node<T>> arrayList = new ArrayList<>(size);
        Queue<BST_Node<T>> queue = new LinkedList<>();
        queue.add(root);

        while(!queue.isEmpty()){
            BST_Node<T> head = queue.peek();
            queue.remove();
            //System.out.println(head.getData().getKey());
            arrayList.add(head);
            if(head.hasLeftSon()) { queue.add(head.getLeftSon());}
            if(head.hasRightSon()) { queue.add(head.getRightSon());}
        }
        return arrayList;
    }

    public ArrayList<BST_Data> levelOrderData(){
        if (root == null) {return null;}
        ArrayList<BST_Data> arrayList = new ArrayList<>(size);
        Queue<BST_Node<T>> queue = new LinkedList<>();
        queue.add(root);

        while(!queue.isEmpty()){
            BST_Node<T> head = queue.peek();
            queue.remove();
            //System.out.println(head.getData().getKey());
            arrayList.add(head.getData());
            if(head.hasLeftSon()) { queue.add(head.getLeftSon());}
            if(head.hasRightSon()) { queue.add(head.getRightSon());}
        }
        return arrayList;
    }

    public ArrayList<BST_Node<T>> inOrder(BST_Node<T> pNode){
        if (root == null) {return null;}
        if (pNode == null) {pNode = root;}
        BST_Node<T> currentNode = pNode;
        ArrayList<BST_Node<T>> list = new ArrayList<>();
        Stack<BST_Node<T>> stack = new Stack<>();

        while(!(stack.isEmpty() && currentNode == null)){
            if (currentNode != null){
                stack.push(currentNode);
                currentNode = currentNode.getLeftSon();
            } else{
                if(!stack.isEmpty()){
                    BST_Node<T> node = stack.pop();
                    list.add(node);
                    //System.out.println(node.getData().getKey());
                    currentNode = node.getRightSon();
                }
            }
        }
        return list;
    }

    public ArrayList<BST_Data> inOrderData(BST_Node<T> pNode){
        if (root == null) {return null;}
        if (pNode == null) {pNode = root;}
        BST_Node<T> currentNode = pNode;
        ArrayList<BST_Data> list = new ArrayList<>();
        Stack<BST_Node<T>> stack = new Stack<>();

        while(!(stack.isEmpty() && currentNode == null)){
            if (currentNode != null){
                stack.push(currentNode);
                currentNode = currentNode.getLeftSon();
            } else{
                if(!stack.isEmpty()){
                    BST_Node<T> node = stack.pop();
                    list.add(node.getData());
                    //System.out.println(node.getData().getKey());
                    currentNode = node.getRightSon();
                }
            }
        }
        return list;
    }

    public int getLevelOfNode(BST_Node<T> node){
        if (node == null) {return 0;}
        int level = 1;
        while(node.getParent() != null){
            node = node.getParent();
            level++;
        }
        return  level;
    }

    //----------------------------------------------------------------//
    //Composite

    private boolean insertDataComposite(T newData, boolean balanceNewNode){
        if (root == null){
            root = new BST_Node<T>((BST_Data<T>) newData);
        } else {
            BST_Node<T> newNode = new BST_Node<T>((BST_Data<T>)newData);
            BST_Node<T> parentNode = null;
            BST_Node<T> currentNode = root;

            while (currentNode != null){
                parentNode = currentNode;
                if (parentNode.getData().compareTo(newNode.getData()) == 0){ return false;}
                if (parentNode.hasLeftSon() && parentNode.getLeftSon().getData().compareTo(newNode.getData()) == 0){ return false;}
                if (parentNode.hasRightSon() && parentNode.getRightSon().getData().compareTo(newNode.getData()) == 0){ return false;}
                if (currentNode.getData().compareTo(newNode.getData()) == -1){
                    currentNode = currentNode.getLeftSon();
                } else {
                    currentNode = currentNode.getRightSon();
                }
            }

            newNode.setParent(parentNode);
            if(parentNode.getData().compareTo(newNode.getData()) == -1){
                parentNode.setLeftSon(newNode);
            } else {
                parentNode.setRightSon(newNode);
            }
            if(balanceNewNode) {balanceNode(newNode);};
        }
        size++;
        return true;
    }

    private void balanceNode(BST_Node<T> node){
        if (node == null || node.getParent() == null || node.getParent().getParent() == null) {return;}
        int i = 0;
        if (getSize() != 0 && (getSize() & (getSize() - 1)) == 0) {   //https://stackoverflow.com/questions/600293/how-to-check-if-a-number-is-a-power-of-2
            i = 1;
        }
        int minPossibleHeight = (int) Math.ceil(Math.log(getSize()+i) / Math.log(2));
        if (getLevelOfNode(node) <= minPossibleHeight) {return;}
        //if (Math.abs(getHeightOfSubtree(node.getLeftSon()) - getHeightOfSubtree(node.getRightSon())) <= 1) {return;}

        BST_Node<T> parent = node.getParent();
        BST_Node<T> grandParent = parent.getParent();
        int prevHeightOfSubtree = 0;

        if (getHeightOfSubtree(root.getLeftSon()) -1 > getHeightOfSubtree(root.getRightSon())){
            rightRotation(root.getLeftSon());
        } else if (getHeightOfSubtree(root.getRightSon()) -1 > getHeightOfSubtree(root.getLeftSon())){
            leftRotation(root.getRightSon());
        }

        if (parent.getRightSon() == node && grandParent.getLeftSon() == parent) {
            prevHeightOfSubtree = getHeightOfSubtree(grandParent);
            leftRotation(node);
            rightRotation(node);
            if (getHeightOfSubtree(node) > prevHeightOfSubtree) {
                leftRotation(grandParent);
                rightRotation(parent);
            }
            return;
        }

        if (parent.getLeftSon() == node && grandParent.getRightSon() == parent) {
            prevHeightOfSubtree = getHeightOfSubtree(grandParent);
            rightRotation(node);
            leftRotation(node);
            if (getHeightOfSubtree(node) > prevHeightOfSubtree) {
                rightRotation(grandParent);
                leftRotation(parent);
            }
            return;
        }

        if (parent.getRightSon() == node && grandParent.getRightSon() == parent) {
            prevHeightOfSubtree = getHeightOfSubtree(parent);
            leftRotation(parent);
            if (getHeightOfSubtree(parent) > prevHeightOfSubtree) {
                rightRotation(grandParent);
            }
            return;
        }

        if (parent.getLeftSon() == node && grandParent.getLeftSon() == parent) {
            prevHeightOfSubtree = getHeightOfSubtree(parent);
            rightRotation(parent);
            if (getHeightOfSubtree(parent) > prevHeightOfSubtree) {
                leftRotation(grandParent);
            }
            return;
        }

    }

    private BST_Node findStartNode(BST_Data key, boolean lastFinded){
        if (root == null) {return null;}
        BST_Node currentNode = root;
        while (currentNode.getData().compareTo(key) != 0){
            if (currentNode.getData().compareTo(key) == -1){
                if (currentNode.hasLeftSon()){//toto musi byt takto
                    if (currentNode.getLeftSon().getData().compareTo(key) == 1 &&
                            currentNode.getData().compareTo(key) == -1 && !currentNode.getLeftSon().hasRightSon()) {return currentNode;}
                    currentNode = currentNode.getLeftSon();
                } else {
                    if (lastFinded) {return currentNode;}
                    return null;
                }
            } else if (currentNode.getData().compareTo(key) == 1){
                if (currentNode.hasRightSon()){//toto musi byt takto
                    if (currentNode.getRightSon().getData().compareTo(key) == -1 &&
                            currentNode.getData().compareTo(key) == 1 && !currentNode.getRightSon().hasLeftSon()) {return currentNode;}
                    currentNode = currentNode.getRightSon();
                } else {
                    if (lastFinded) {return currentNode;}
                    return null;
                }
            }
        }
        return currentNode;
    }

    private BST_Node getPredecessor(BST_Node node) {
        if (node == null) return null;
        if (node.getLeftSon() != null) {
            return getMaximum(node.getLeftSon());
        }

        BST_Node parent = node.getParent();
        while(parent != null && node == parent.getLeftSon()){
            node = parent;
            parent = parent.getParent();
        }
        return parent;
    }

    private BST_Node getSuccessor(BST_Node node) {
        if (node == null) return null;

        if (node.getRightSon() != null) {
            return getMinimum(node.getRightSon());
        }

        BST_Node parent = node.getParent();
        while(parent != null && node == parent.getRightSon()){
            node = parent;
            parent = parent.getParent();
        }
        return parent;
    }

    private BST_Node getMinimum(BST_Node node){
        while (node.getLeftSon() != null) {
            node = node.getLeftSon();
        }
        return node;
    }

    private BST_Node getMaximum(BST_Node node){
        while (node.getRightSon() != null) {
            node = node.getRightSon();
        }
        return node;
    }

    private boolean removeNode(BST_Node<T> node){
        BST_Node<T> parentNode = null;

        if (root == null || node == null) {return false;}
        if (node != root) {parentNode = node.getParent();}
        if (node != root && parentNode == null) {return false;}

        if (node == root && !node.hasLeftSon() && !node.hasRightSon()) { //Root //1
            root = null;
        } else if (node == root && node.hasLeftSon() && !node.hasRightSon()) {  //2
            BST_Node<T> leftSon = root.getLeftSon();
            leftSon.setParent(null);
            root.setLeftSon(null);
            root = leftSon;
        } else if (node == root && !node.hasLeftSon() && node.hasRightSon()) {  //3
            BST_Node<T> rightSon = root.getRightSon();
            rightSon.setParent(null);
            root.setRightSon(null);
            root = rightSon;
        } else if (node == root && node.hasLeftSon() && node.hasRightSon()) {

            if (!root.getRightSon().hasLeftSon()) {                             //4
                BST_Node<T> rightSon = root.getRightSon();
                rightSon.setParent(null);
                root.getLeftSon().setParent(rightSon);
                rightSon.setLeftSon(root.getLeftSon());
                root.setLeftSon(null);
                root.setRightSon(null);
                root = rightSon;
            } else {                                                            //5
                BST_Node<T> leftSon = root.getLeftSon();
                BST_Node<T> rightSon = root.getRightSon();
                node = rightSon;
                while (node.hasLeftSon()) {
                    node = node.getLeftSon();
                }
                BST_Node<T> newRoot = node;
                newRoot.getParent().setLeftSon(null);
                changeReferences((BST_Node<T>) newRoot, (BST_Node<T>) leftSon, (BST_Node<T>) rightSon);
                root = newRoot;
                newRoot.setParent(null);
            }
        } else if (node.hasLeftSon() && node.hasRightSon()) {   //Node
            if (!node.getRightSon().hasLeftSon()) {
                if (parentNode.getRightSon() == node) {                         //6
                    parentNode.setRightSon(node.getRightSon());
                } else {                                                        //7
                    parentNode.setLeftSon(node.getRightSon());
                }
                node.getRightSon().setLeftSon(node.getLeftSon());
                node.getLeftSon().setParent(node.getRightSon());
                node.getRightSon().setParent(parentNode);
                node.setParent(null);
                node.setRightSon(null);
                node.setLeftSon(null);
            } else {                                                            //8
                BST_Node<T> outdatedNode = node;
                BST_Node<T> leftSon = outdatedNode.getLeftSon();
                BST_Node<T> rightSon = outdatedNode.getRightSon();

                node = outdatedNode.getRightSon();
                while (node.hasLeftSon()) {
                    node = node.getLeftSon();
                }
                if (node.getParent().getLeftSon() == node){
                    node.getParent().setLeftSon(null);
                }
                changeReferences((BST_Node<T>) node, (BST_Node<T>) leftSon, (BST_Node<T>) rightSon);

                node.setParent(outdatedNode.getParent());
                if (parentNode.getRightSon() == outdatedNode) {
                    parentNode.setRightSon(node);
                } else {
                    parentNode.setLeftSon(node);
                }
                outdatedNode.setParent(null);
            }
        } else if (node.hasLeftSon() && !node.hasRightSon()) {
            if (parentNode.getLeftSon() == node) {                              //9
                parentNode.setLeftSon(node.getLeftSon());
            } else {                                                            //10
                parentNode.setRightSon(node.getLeftSon());
            }
            node.getLeftSon().setParent(parentNode);
            node.setParent(null);
            node.setLeftSon(null);

        } else if (node.hasRightSon() && !node.hasLeftSon()) {
            if (parentNode.getLeftSon() == node) {                              //11
                parentNode.setLeftSon(node.getRightSon());
            } else {
                parentNode.setRightSon(node.getRightSon());                     //12
            }
            node.getRightSon().setParent(parentNode);
            node.setParent(null);
            node.setRightSon(null);

        } else if (!node.hasRightSon() && !node.hasLeftSon()) {
            if (parentNode.getLeftSon() == node) {                              //13
                parentNode.setLeftSon(null);
            } else {                                                            //14
                parentNode.setRightSon(null);
            }
            node.setParent(null);
        }
        return true;
    }

    private void changeReferences(BST_Node<T> node, BST_Node<T> leftSon, BST_Node<T> rightSon) {
        if (node.hasRightSon()){
            node.getRightSon().setParent(node.getParent());
            node.getParent().setLeftSon(node.getRightSon());
        }
        node.setLeftSon(leftSon);
        node.setRightSon(rightSon);
        leftSon.setParent(node);
        rightSon.setParent(node);
    }

    private int highestLevelOfLeafInSubtree(BST_Node<T> node) {
        int minLevel = size;
        ArrayList<BST_Node<T>> allNodes = levelOrder();
        for (BST_Node<T> node2: allNodes) {
            //if (node.getParent().getRightSon() == node) {
                if(root.getData().compareTo(node2.getData()) == -1 && node2.isLeaf()){
                    if(minLevel > getLevelOfNode(node2)){
                        minLevel = getLevelOfNode(node2);
                    }
                }
            //}
        }
        return minLevel + getLevelOfNode(node);
    }

    private void rightRotation(BST_Node<T> pivot){
        if (pivot == null || pivot.getParent() == null) {return;}
        BST_Node<T> parent = pivot.getParent();
        if (!parent.hasLeftSon()) {return;}

        BST_Node<T> son = pivot.getRightSon();
        if(son != null) {son.setParent(parent);};

        parent.setLeftSon(son);
        pivot.setRightSon(parent);

        if(parent == root){
            root = pivot;
            root.setParent(null);
        } else {
            if (parent == parent.getParent().getRightSon()){
                parent.getParent().setRightSon(pivot);
            } else {
                parent.getParent().setLeftSon(pivot);
            }
            pivot.setParent(parent.getParent());
            //if(son != null) {son.setParent(parent);};
        }
        parent.setParent(pivot);
        //System.out.println("right");
    }

    private void leftRotation(BST_Node<T> pivot){
        if (pivot == null || pivot.getParent() == null) {return;}
        BST_Node<T> parent = pivot.getParent();
        if (!parent.hasRightSon()) {return;}

        BST_Node<T> son = pivot.getLeftSon();
        if(son != null) {son.setParent(parent);};

        parent.setRightSon(son);
        pivot.setLeftSon(parent);

        if(parent == root){
            root = pivot;
            root.setParent(null);
        } else {
            if (parent == parent.getParent().getLeftSon()){
                parent.getParent().setLeftSon(pivot);
            } else {
                parent.getParent().setRightSon(pivot);
            }
            pivot.setParent(parent.getParent());
            //if(son != null) {son.setParent(parent);};
        }
        parent.setParent(pivot);
        //System.out.println("left");
    }

    //---------GETTERS-----------------------------------------------//
    public int getHeightOfSubtree(BST_Node<T> node) {
        if (node == null) {return 0;}
        Queue<BST_Node<T>> queue = new LinkedList<>();
        queue.add(node);

        int height = 0;
        int queueSize = queue.size();
        while (!queue.isEmpty()) {
            height++;

            queueSize = queue.size();
            for (int i = 0; i < queueSize; i++) {
                BST_Node<T> currentNode = queue.peek();
                queue.remove();
                //System.out.println(i);
                if (currentNode.hasLeftSon()){
                    queue.add(currentNode.getLeftSon());
                }

                if (currentNode.hasRightSon()) {
                    queue.add(currentNode.getRightSon());
                }
            }
        }
        //System.out.println("------------------------------");
        return height;
    }

    public BST_Node<T> getRoot() {return root;}

    public int getSize() {return size;}
}
