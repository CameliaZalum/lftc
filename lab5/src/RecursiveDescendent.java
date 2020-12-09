import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;

class Pair {
    public String nonTerminal;
    public int position;

    @Override
    public String toString() {
        return "Pair{" +
                "nonTerminal='" + nonTerminal + '\'' +
                ", position=" + position +
                '}';
    }

    Pair(String a, int b) {
        this.nonTerminal = a;
        this.position = b;
    }
}

public class RecursiveDescendent {
    public static int MAXIMUM_POSITIONS = 0;
    public List<Operations> operationList;
    static List<List<String>> workingStacksList = new ArrayList<>();
    public Grammar grammar;
    public List<String> inputSequence;
    public static Map<Pair, List<Integer>> usedIndexesOfProduction = new HashMap<>();

    public RecursiveDescendent(Grammar g) {
        this.operationList = new ArrayList<>();
        this.grammar = g;
        for (String a : grammar.nonTerminals) {
            for (int i = 0; i < MAXIMUM_POSITIONS + 1; i++) {
                usedIndexesOfProduction.put(new Pair(a, i + 1), new ArrayList<>());
            }
        }
    }

    @Override
    public String toString() {
        return "RecursiveDescendent{" +
                "operationList=" + operationList +
                ", grammar=" + grammar +
                ", inputSequence=" + inputSequence +
                '}';
    }

    public void expand(Operation a) {
        if (this.grammar.nonTerminals.contains(a.inputStack.get(0))) {
            List<String> newWorkingStack = a.workingStack;
            Production results = grammar.productions.get(a.inputStack.get(0)).get(0);
            newWorkingStack.add(a.inputStack.get(0));
            List<String> newInputStack = a.inputStack;
            newInputStack.remove(0);
            newInputStack.addAll(0, results.result);
            Operation op = new Operation(a.state, a.position, newWorkingStack, newInputStack);
            Operations newOperation = new Operations("expand", op);
            operationList.add(newOperation);
        }

    }

    public void advance(Operation a) {
        if (this.grammar.terminals.contains(a.inputStack.get(0))) {

            List<String> newWorkingStack = a.workingStack;
            newWorkingStack.add(a.inputStack.get(0));
            List<String> newInputStack = a.inputStack;
            newInputStack.remove(0);
            Operation op = new Operation(a.state, a.position + 1, newWorkingStack, newInputStack);
            Operations newOperation = new Operations("advance", op);
            this.operationList.add(newOperation);

        }
    }

    public void momentaryInsuccess(Operation a) {
        if (this.grammar.terminals.contains(a.inputStack.get(0))) {
            if (!a.workingStack.get(0).equals(inputSequence.get(a.position))) {
                List<String> newInputStack = a.inputStack;
                List<String> newWorkingStack = a.workingStack;
                Operation op = new Operation("b", a.position, newWorkingStack, newInputStack);
                Operations newOperation = new Operations("momentary insuccess", op);
                this.operationList.add(newOperation);
            }
        }
    }

    public void backOperation(Operation a) {
        if (grammar.terminals.contains(a.workingStack.get(a.workingStack.size() - 1))) {
            List<String> newInputStack = a.copyInputList();
            List<String> newWorkingStack = a.copyWorkingList();
            newWorkingStack.remove(a.workingStack.size() - 1);
            newInputStack.add(0, a.workingStack.get(a.workingStack.size() - 1));
            Operation op = new Operation("b", a.position - 1, newWorkingStack, newInputStack);
            Operations newOperation = new Operations("back", op);
            this.operationList.add(newOperation);
        }
    }

    public void anotherTry(Operation a) throws InterruptedException {
        if (grammar.nonTerminals.contains(a.workingStack.get(a.workingStack.size() - 1))) {
            String lastFromWorkingStack = a.workingStack.get(a.workingStack.size() - 1);
            List<Production> productionsOfTheLastSymbolFromWorkingStack = grammar.productions.get(lastFromWorkingStack);
            List<Integer> indexOfProductions = new ArrayList<>();
            Pair pair = new Pair(lastFromWorkingStack, a.position);

            for (int k = 0; k < productionsOfTheLastSymbolFromWorkingStack.size(); k++) {
                Production p = productionsOfTheLastSymbolFromWorkingStack.get(k);

                boolean match = true;
                if (a.inputStack.size() >= p.result.size()) {
                    for (int i = 0; i < p.result.size(); i++) {
                        if (!p.result.get(i).equals(a.inputStack.get(i))) {
                            match = false;
                            break;
                        }
                    }
                    if (match == true) {
                        indexOfProductions.add(k);
                    }
                }
            }
            List<Pair> list = new ArrayList<>(); //= usedIndexesOfProduction.keySet().stream().filter(s -> (a.position == s.position && s.nonTerminal.equals(lastFromWorkingStack))).collect(Collectors.toList());
            usedIndexesOfProduction.keySet().forEach(value-> {
                if (value.position == a.position && value.nonTerminal.equals(lastFromWorkingStack)){
                    list.add(value);

                }
            });

            for (int i =0; i < indexOfProductions.size(); i++) {
                if(usedIndexesOfProduction.get(list.get(0)).size() > 0) {
                    if (usedIndexesOfProduction.get(list.get(0)).contains(indexOfProductions.get(i)) && workingStacksList.contains(a.workingStack)) {
                        indexOfProductions.remove(indexOfProductions.get(i));
                    }
                }
            }
            workingStacksList.add(a.copyWorkingList());
            int indexOfProduction;
            try {
                indexOfProduction = indexOfProductions.get(0);
            } catch (Exception e) {
                indexOfProduction = usedIndexesOfProduction.get(list.get(0)).get(usedIndexesOfProduction.get(list.get(0)).size() -1);
            }

            if (indexOfProduction + 1 < productionsOfTheLastSymbolFromWorkingStack.size() && indexOfProduction != -1) {
                List<String> newInputStack = a.copyInputList();
                for (int i = 0; i < productionsOfTheLastSymbolFromWorkingStack.get(indexOfProduction).result.size(); ++i) {
                    newInputStack.remove(0);
                }
                usedIndexesOfProduction.get(list.get(0)).add(indexOfProduction);
                List<String> newWorkingStack = a.copyWorkingList();
                newInputStack.addAll(0, productionsOfTheLastSymbolFromWorkingStack.get(indexOfProduction + 1).result);
                Operation op = new Operation("q", a.position, a.workingStack, newInputStack);
                Operations newOperation = new Operations("another try", op);
                this.operationList.add(newOperation);
            } else if (a.position == 1 && lastFromWorkingStack.equals(grammar.startingSymbol)) {
                List<String> input = a.copyInputList();
                List<String> output = a.copyWorkingList();
                this.operationList.add(new Operations("anotherTry", new Operation("e", a.position, output, input)));

            } else {
                List<String> newInputStack = a.copyInputList();
                for (int i = 0; i < productionsOfTheLastSymbolFromWorkingStack.get(indexOfProduction).result.size(); ++i) {
                    newInputStack.remove(0);
                }

                usedIndexesOfProduction.get(list.get(0)).add(indexOfProduction);
                newInputStack.add(0, lastFromWorkingStack);
                List<String> newWorkingStack = a.copyWorkingList();
                newWorkingStack.remove(a.workingStack.size() - 1);
                Operation op = new Operation("b", a.position, newWorkingStack, newInputStack);
                Operations newOperation = new Operations("another try", op);
                this.operationList.add(newOperation);
            }

        }
    }

    public void success(Operation a) {
        List<String> last = new ArrayList<>();
        last.add("e");
        Operation op = new Operation("f", a.position, a.copyWorkingList(), last);
        operationList.add(new Operations("success", op));
    }
}
