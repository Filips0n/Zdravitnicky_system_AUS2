import other.App_core;
import other.Controller;
import other.Generator;
import other.Gui;
import structure.BST;

public class Sem_1 {
    public static void main(String[] args) {
        Gui gui = new Gui();
        App_core core = new App_core();
        Controller controller = new Controller(gui, core);
        controller.initController();

        //Operations test
//        BST<String> tree = new BST<String>();
//        Generator<Integer> gen = new Generator<>();
//        System.out.println("Insert/Find/Delete test:");
//        gen.operationGenerator(tree, 1000);
//        System.out.println("Unbalanced tree details:");
//        gen.getDetailsOfTree(tree);
//        System.out.println("Balanced tree results:");
//        tree.balance();
//        gen.getDetailsOfTree(tree);
//
//        //General test
//        System.out.println("Balance algorithm test: ");
//        gen.checkBalanceAlg(50, 500);
//        System.out.println("Autobalance algorithm results: ");
//        gen.checkBalanceNodeAlg(1000, 500);
//        System.out.println("Interval find test: ");
//        gen.checkIntervalFind(50, 2000);
    }
}
