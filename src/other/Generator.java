package other;

import data.Hospital;
import data.Hospitalization;
import data.Patient;
import structure.BST;
import structure.BST_Data;
import structure.BST_Node;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

public class Generator<T extends Comparable<T>> {

    public String generateName(){
        String name = "";
        File f = new File("names.txt");
        Random rnd = new Random();
        int n = 0;
        try {
            for(Scanner sc = new Scanner(f); sc.hasNext(); ) {
                n++;
                String readLine = sc.nextLine();
                if(rnd.nextInt(n) == 0)
                    name = readLine;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return name;
    }

    public String generateDiagnosis(){
        String name = "";
        File f = new File("diagnosis.txt");
        Random rnd = new Random();
        int n = 0;
        try {
            for(Scanner sc = new Scanner(f); sc.hasNext(); ) {
                n++;
                String readLine = sc.nextLine();
                if(rnd.nextInt(n) == 0)
                    name = readLine;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return name;
    }

    public LocalDate generateDate(){
        Random rnd = new Random();
        int year = (rnd.nextInt(2022 - 1920) + 1920);
        int month = (rnd.nextInt(12 - 1) + 1);
        String monthS = String.format("%02d", month);
        int day = (rnd.nextInt((YearMonth.from(LocalDate.parse(year+"-"+monthS+"-01")).atEndOfMonth().getDayOfMonth()) - 1) + 1);

        return LocalDate.of(year, month, day);
    }

    public LocalDate generateHospiDate(LocalDate minDate){
        Random rnd = new Random();
        int year = (rnd.nextInt(2022 - minDate.getYear()) + minDate.getYear());
        int month = (rnd.nextInt(12 - 1) + 1);
        if (year == minDate.getYear()) {
            month = (rnd.nextInt(12 - minDate.getMonth().getValue()) + minDate.getMonth().getValue());
        }
        String monthS = String.format("%02d", month);
        int day = (rnd.nextInt((YearMonth.from(LocalDate.parse(year+"-"+monthS+"-01")).atEndOfMonth().getDayOfMonth()) - 1) + 1);
        if (year == minDate.getYear() && month == minDate.getMonth().getValue()) {
            day = (rnd.nextInt((YearMonth.from(LocalDate.parse(year+"-"+monthS+"-01")).atEndOfMonth().getDayOfMonth()) - minDate.getDayOfMonth()) + minDate.getDayOfMonth());
        }
        return LocalDate.of(year, month, day);
    }

    public ArrayList<BST_Data> generatePatients(int quantity, int numOfIcs){
        Random rnd = new Random();
        ArrayList<BST_Data> dataList = new ArrayList<>(quantity);

        String id;
        int ic;
        for (int i = 0; i < quantity; i++){
            //id = (rnd.nextInt(999999 - 100000) + 100000)+"/"+(rnd.nextInt(9999 - 1000) + 1000);
            id = String.format("%010d", i);
            id = id.substring(0,6)+"/"+id.substring(6,10);
            ic = rnd.nextInt(numOfIcs)+1;
            dataList.add((BST_Data)
                    //new Patient(i, generateName(), generateName(), id, generateDate(), ic)
                    new Patient(id, generateName(), generateName(), id, generateDate(), ic)
            );
        }
        return dataList;
    }

    public ArrayList<BST_Data> generateHospitals(int quantity){
        File f = new File("hospitals.txt");
        ArrayList<BST_Data> dataList = new ArrayList<>(quantity);
        ArrayList<String> stringList = new ArrayList<>(quantity);

        int i = 0;
        try {
            for(Scanner sc = new Scanner(f); sc.hasNext(); ) {
                if (i == quantity) {break;}
                String readLine = sc.nextLine();
                stringList.add(readLine);
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Random rnd = new Random();
        Collections.sort(stringList);
        for (int j = 0; j < quantity; j++) {
            dataList.add((BST_Data) new Hospital(stringList.get(rnd.nextInt(stringList.size()))+ " " + generateName() + " " + generateName() + " " + generateName()));
        }

        return dataList;
    }

    public ArrayList<BST_Data> generateHospitalizations(int quantity, BST patientsTree, BST hospitalsTree){
        ArrayList<BST_Data> dataList = new ArrayList<>(quantity);
        ArrayList<BST_Data> patientList = patientsTree.inOrderData(patientsTree.getRoot());
        ArrayList<BST_Data> hospitalList = hospitalsTree.inOrderData(hospitalsTree.getRoot());
        Random rnd = new Random();

        for (int i = 0; i < quantity; i++){
            int durationDays = rnd.nextInt(20);
            Patient patient = (Patient) patientList.get(rnd.nextInt(patientsTree.getSize()));
            LocalDate date = generateHospiDate(patient.getBirthDate());
            Hospital hospital = (Hospital) hospitalList.get(rnd.nextInt(hospitalsTree.getSize()));
            LocalDate dateTo = durationDays > 0 ? date.plusDays(durationDays) : null;
            Hospitalization hospi = new Hospitalization(i+1, date, dateTo, generateDiagnosis(), patient, hospital);
            if (!patient.addHospitalization(hospi)) {
                continue;
            };
            dataList.add((BST_Data)hospi);
            //patient.addHospitalization(hospi);
            if (dateTo == null) {
                hospital.addHospitalization(hospi, true);
            } else {
                hospital.addHospitalization(hospi, false);
            }
        }
        return dataList;
    }

    public BST insertDataIntoTree(BST tree, ArrayList<BST_Data> sortedDataList){
        Stack<int[]> indexes = new Stack<>();
        int size = sortedDataList.size();
        tree.insertData(sortedDataList.get((size-1)/2),false);
        indexes.push(new int[]{0,size-1});

        int minIndex = 0;
        int maxIndex = 0;
        while (!indexes.isEmpty()) {
            int currentIndexes[] = indexes.peek();
            indexes.pop();

            minIndex = currentIndexes[0];
            maxIndex = currentIndexes[1];

            int median = minIndex + ((maxIndex - minIndex)/2);
            if (minIndex < median) {
                tree.insertData(sortedDataList.get(minIndex + ((median - 1 - minIndex)/2)), false);
                indexes.push(new int[]{minIndex, median - 1});;
            }

            if (median < maxIndex) {
                tree.insertData(sortedDataList.get(median + 1 + ((maxIndex - median - 1)/2)), false);
                indexes.push(new int[]{median+1, maxIndex});
            }
        }
        return tree;
    }

    public void operationGenerator(BST tree, int numOfOperations){
        Random rnd = new Random();
        //ArrayList<Integer> listOfKeys = new ArrayList<>(1000000);
        ArrayList<String> listOfKeys = new ArrayList<>(1000000);
        int operation;
        for(int i = 0; i < numOfOperations; i++){
            operation = rnd.nextInt(9);
            String id = (rnd.nextInt(999999 - 100000) + 100000)+"/"+(rnd.nextInt(9999 - 1000) + 1000);
//            BST_Node<Integer> node = new BST_Node<Integer>(
                //new Patient(rnd.nextInt(50000)+1, "aaa", "bbb", "123456/1452", generateDate(), 5));
            BST_Node<String> node = new BST_Node<String>(
                new Patient(id, "aaa", "bbb", id, generateDate(), 5));
            if(operation < 5){
                if (tree.insertData((BST_Data<T>) node.getData())){
                    //listOfKeys.add((Integer) node.getData().getKey());
                    listOfKeys.add((String) node.getData().getKey());
                } ;
            } else if(operation < 6){
                //tree.findNode(((BST_Data<T>) node.getData()).getKey());
                tree.findData(((BST_Data<T>) node.getData()).getKey());
            } else {
                //tree.removeNodeByKey(((BST_Data<T>)node.getData()).getKey());
                if (listOfKeys.size() <= 0) {continue;}
                int index = rnd.nextInt(listOfKeys.size());
                if(tree.removeNodeByKey((T) listOfKeys.get(index))){
                    listOfKeys.remove(index);
                };
            }
//            //kontrola balancovania
//            tree.balance();
//            checkBalance(tree);

            //kontrola zhodnosti klucov
            ArrayList<BST_Node<T>> inOrder = tree.inOrder(tree.getRoot());
            Collections.sort(listOfKeys);
            if (inOrder == null) {continue;}
            if (inOrder.size() != listOfKeys.size()){System.out.println("Nezhodujuce sa vekosti"); }
            for (int j = 0; j < inOrder.size(); j++) {
                if (listOfKeys.get(j) != inOrder.get(j).getData().getKey()){
                    System.out.println("Kluc neexistuje");
                }
            }
        }
        //tree.balance();

    }

    public void getDetailsOfTree(BST tree){
        if (tree == null) {return;}
        int i = 0;
        if (tree.getSize() != 0 && (tree.getSize() & (tree.getSize() - 1)) == 0) {     //https://stackoverflow.com/questions/600293/how-to-check-if-a-number-is-a-power-of-2
            i = 1;
        }
        int minPossibleHeight = (int) Math.ceil(Math.log(tree.getSize()+i) / Math.log(2));
        System.out.println("----------");
        System.out.println("Size of tree: " + tree.getSize());
        System.out.println("Min height of tree: " + minPossibleHeight);
        System.out.println("Current height of tree: " + tree.getHeightOfSubtree(tree.getRoot()));
//        System.out.println("Current height left subtree: " + getHeightOfSubtree(getRoot().getLeftSon()));
//        System.out.println("Current height right subtree: " + getHeightOfSubtree(getRoot().getRightSon()));

        System.out.println("----------");
    }

    public void checkBalance(BST tree){
        if (tree == null) {return;}
        int i = 0;
        if (tree.getSize() != 0 && (tree.getSize() & (tree.getSize() - 1)) == 0) {     //https://stackoverflow.com/questions/600293/how-to-check-if-a-number-is-a-power-of-2
            i = 1;
        }

        int minPossibleHeight = (int) Math.ceil(Math.log(tree.getSize()+i) / Math.log(2));
        ArrayList<BST_Node<T>> allNodes = tree.levelOrder();
        if (allNodes == null) {return;}
        for (BST_Node<T> node: allNodes) {
            if(Math.abs(tree.getHeightOfSubtree(node.getRightSon()) - tree.getHeightOfSubtree(node.getLeftSon())) > 1){
                System.out.println(node.getData().getKey()+" chyba");
            }
        }

        for (BST_Node<T> node: allNodes) {
            if((!node.hasRightSon() || !node.hasLeftSon()) && tree.getLevelOfNode(node) < minPossibleHeight-1){
                System.out.println(node.getData().getKey()+" chyba");
            }
        }
    }

    public void checkBalanceAlg(int numOfTrees, int quantity) {
        Random rnd = new Random();
        for (int j = 0; j < numOfTrees; j++) {
            BST<String> tree = new BST<String>();
            for (int i = 0; i < quantity; i++) {
                String id = (rnd.nextInt(999999 - 100000) + 100000)+"/"+(rnd.nextInt(9999 - 1000) + 1000);
                tree.insertData(
                        new Patient(id, "aaa", "bbb", id, generateDate(), 5));
            }
            tree.balance();
            checkBalance((BST<T>) tree);
        }
    }

    public void checkBalanceNodeAlg(int numOfTrees, int quantity){
        Random rnd = new Random();
        int sameHeight = 0;
        int betterHeight = 0;
        int worseHeight = 0;
        for (int j = 0; j < numOfTrees; j++) {
            BST<String> tree = new BST<String>();
            BST<String> treeBalanced = new BST<String>();
            for (int i = 0; i < quantity; i++) {
                int random = rnd.nextInt(quantity)+1;
                String id = (rnd.nextInt(999999 - 100000) + 100000)+"/"+(rnd.nextInt(9999 - 1000) + 1000);
                tree.insertData(
                        //new Patient(random, "aaa", "bbb", "123456/1452", generateDate(), 5), false);
                        new Patient(id, "aaa", "bbb", id, generateDate(), 5), false);
                treeBalanced.insertData(
                        //new Patient(random, "aaa", "bbb", "123456/1452", generateDate(), 5));
                        new Patient(id, "aaa", "bbb", id, generateDate(), 5));
            }

            int heightOfUnbalancedTree = tree.getHeightOfSubtree(tree.getRoot());
            int heightOfBalancedTree = treeBalanced.getHeightOfSubtree(treeBalanced.getRoot());
            if (heightOfUnbalancedTree < heightOfBalancedTree) {
                worseHeight++;
            } else if (heightOfUnbalancedTree > heightOfBalancedTree){
                betterHeight++;
            } else {
                sameHeight++;
            }
        }
        System.out.println("Lepsia vyska v " +(double)betterHeight/numOfTrees*100+"%");
        System.out.println("Rovnaka vyska v " +(double)sameHeight/numOfTrees*100+"%");
        System.out.println("Horsia vyska v " +(double)worseHeight/numOfTrees*100+"%");
    }

    public void checkIntervalFind(int numOfTrees, int quantity) {
        Random rnd = new Random();
        int digits = String.valueOf(quantity).length();
        for (int j = 0; j < numOfTrees; j++) {
            BST<String> tree = new BST<>();
            ArrayList<String> keys = new ArrayList<>(quantity);
            for (int i = 0; i < quantity; i++) {
                String id = Integer.toString(rnd.nextInt(quantity));
                id = String.format("%0" + digits + "d", Integer.parseInt(id));
                if(tree.insertData(new Patient(id, "aaa", "bbb", id, generateDate(), 5))) {
                    keys.add(id);
                }
            }

            int minIndex = rnd.nextInt(keys.size()-1);
            String min = keys.get(minIndex);
            String max = keys.get(rnd.nextInt(keys.size()-1 - minIndex) + minIndex);
            ArrayList<BST_Data> treeInterval = tree.intervalFind(new Patient(min, "aaa", "bbb", min, generateDate(), 5),
                    new Patient(max, "aaa", "bbb", max, generateDate(), 5));

            ArrayList<String> keysInterval = new ArrayList<>(quantity);
            for (int k = 0; k < keys.size(); k++) {
                if (Integer.parseInt(keys.get(k)) >= Integer.parseInt(min) && Integer.parseInt(keys.get(k)) <= Integer.parseInt(max)) {
                    keysInterval.add(keys.get(k));
                }
            }
            Collections.sort(keysInterval);
            for (int k = 0; k < keysInterval.size(); k++) {
                if (!(keysInterval.get(k).equals(treeInterval.get(k).getKey()))){
                    System.out.println("Chyba!");
                }
            }
        }
    }
}
