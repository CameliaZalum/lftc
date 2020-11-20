import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static String menu = "1. show set terminals;\n2.show set nonterminals\n3.show productions\n4.show production of given terminal";

    public static void main(String[] args) throws FileNotFoundException {
        Grammar grammar = new Grammar();
        File fsFile = new File("C:\\Users\\camel\\Desktop\\faculta2\\lftc\\lab5\\src\\g1.txt");
        Scanner scanner = new Scanner(fsFile);

        String terminals[] = scanner.nextLine().split(", |\n");
        grammar.setNonTerminals(Arrays.asList(terminals));

        String nonTerminals[] = scanner.nextLine().split(", |\n");
        grammar.setTerminals(Arrays.asList(nonTerminals));

        String productions[] = scanner.nextLine().split("; |\n");
        for (String a: productions){
            String parts[] = a.split("-> ");
            String start = parts[0];
            String partsOfParts[] = parts[1].split(" \\| ");
            for (String p: partsOfParts) {
                Production prod = new Production();
                prod.setResult(Arrays.asList(p.split(" ")));
                grammar.addProduction(start, prod);
            }
        }
        grammar.setStartingSymbol(scanner.nextLine());
//        System.out.println(grammar);

        scanner.close();

        System.out.println(menu);
        scanner = new Scanner(System.in);
        while(scanner.hasNextLine()){
            String scan = scanner.nextLine();
            if (scan.equals("1")) {
                System.out.println(grammar.terminals);
            }
            if(scan.equals("2")){
                System.out.println(grammar.nonTerminals);
            }
            if(scan.equals("3")){
                System.out.println(grammar.productions);
            }
            if(scan.equals("4")){
                System.out.println("type the terminal");
                String terminal = scanner.nextLine();
                System.out.println(grammar.productions.get(terminal));
            }

            if(scan == "0"){
                System.exit(0);
            }
        }


    }
}
