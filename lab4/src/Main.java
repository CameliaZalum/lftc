import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    static String menu = "1. show set of states;\n2.show set of input symbols\n3.show transitions\n4.show initial state\n5.show final state\n";

    public static void main(String[] args) throws FileNotFoundException {
        File fsFile = new File("C:\\Users\\camel\\Desktop\\faculta2\\lftc\\lab4\\src\\FA.in");
        Scanner scanner = new Scanner(fsFile);
        States states = new States();
        String setOfStates[] = scanner.nextLine().split(", |\n");
        for (String a: setOfStates) {
            states.addState(a);
        }
        InputSymbols is = new InputSymbols();
        String inputs[] = scanner.nextLine().split(", |\n");
        for (String a: inputs) {
            is.addSymbol(a);
        }
        Transition t = new Transition();
        String trans[] = scanner.nextLine().split("\\; |\n");
        for (String a: trans) {
            String sth[] = a.split("d\\(|, |\\) = ");
            t.addTransition(sth[1], sth[2], sth[3]);
        }
        String initialState = scanner.nextLine();
        states.setInitialState(initialState);
        String finalState[] = scanner.nextLine().split(", |\n");
        for (String a: finalState) {
            states.addFinalState(a);
        }

        scanner.close();
        System.out.println(menu);
        scanner = new Scanner(System.in);
        while(scanner.hasNextLine()){
            String scan = scanner.nextLine();
            if (scan.equals("1")) {
                System.out.println(states);
            }
            if(scan.equals("2")){
                System.out.println(is);
            }
            if(scan.equals("3")){
                System.out.println(t);
            }
            if(scan.equals("4")){
                System.out.println("initial state is "  + states.initialState);
            }
            if(scan.equals("5")){
                System.out.println("final states are " + states.printFinalStates());
            }
            if(scan == "0"){
                System.exit(0);
            }
        }

    }
}
