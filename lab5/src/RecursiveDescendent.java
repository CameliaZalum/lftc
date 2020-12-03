import java.util.ArrayList;
import java.util.List;

public class RecursiveDescendent {
    public List<Operations> operationList;
    public Grammar grammar;
    public List<String> inputSequence;

    public RecursiveDescendent(Grammar g) {
        this.operationList = new ArrayList<>();
        this.grammar = g;
    }
    public void expand(Operation a){
        if(this.grammar.nonTerminals.contains(a.inputStack.get(0))){
            List<String> newWorkingStack = a.workingStack;
            Production results = grammar.productions.get(a.inputStack.get(0)).get(0);

            newWorkingStack.add(a.inputStack.get(0));
            List<String> newInputStack = a.inputStack;
            newInputStack.remove(0);
            newInputStack.addAll(0, results.result);
            Operation op = new Operation(a.state, a.position, newWorkingStack, newInputStack);
            Operations newOperation = new Operations("expand", op);
            operationList.add(newOperation );
        }

    }
    public void advance(Operation a){
        if(this.grammar.terminals.contains(a.inputStack.get(0))) {

                List<String> newWorkingStack = a.workingStack;
                newWorkingStack.add(a.inputStack.get(0));
                List<String> newInputStack = a.inputStack;
                newInputStack.remove(0);
                Operation op = new Operation(a.state, a.position + 1, newWorkingStack, newInputStack);
                Operations newOperation = new Operations("advance", op);
                this.operationList.add(newOperation);

        }
    }
    public void momentaryInsuccess(Operation a){
        if(this.grammar.terminals.contains(a.inputStack.get(0))) {
            if(!a.workingStack.get(0).equals(inputSequence.get(a.position))) {
                List<String> newInputStack = a.inputStack;
                List<String> newWorkingStack = a.workingStack;
                Operation op = new Operation("b", a.position, newWorkingStack, newInputStack);
                Operations newOperation = new Operations("momentary insuccess", op);
                this.operationList.add(newOperation);
            }
        }
    }
    public void backOperation(Operation a){
        if(grammar.terminals.contains(a.workingStack.get(a.workingStack.size() - 1))){
            List<String> newInputStack = a.inputStack;
            List<String> newWorkingStack = a.workingStack;
            newWorkingStack.remove(a.workingStack.size() - 1);
            newInputStack.add(0, a.inputStack.get(a.inputStack.size() -1));
            Operation op = new Operation("b", a.position - 1, newWorkingStack, newInputStack);
            Operations newOperation = new Operations("back", op);
            this.operationList.add(newOperation);
        }
    }
    public void anotherTry(Operation a){
        if(grammar.nonTerminals.contains(a.workingStack.get(a.workingStack.size() - 1))) {
            String lastFromWorkingStack = a.workingStack.get(a.workingStack.size() - 1);
            List<Production> productionsOfTheLastSymbolFromWorkingStack = grammar.productions.get(lastFromWorkingStack);
            int indexOfProduction = -1;
            try {
                for (int k = 0; k < productionsOfTheLastSymbolFromWorkingStack.size(); k++) {
                    Production p = productionsOfTheLastSymbolFromWorkingStack.get(k);

                    boolean match = true;
                    for (int i = 0; i < p.result.size(); i++) {
                        if (!p.result.get(i).equals(a.inputStack.get(i))) {
                            System.out.println(i);
                            match = false;
                            break;
                        }
                    }
                    if (match == true) {
                        indexOfProduction = k;
                    }

                }
            }
            catch (Exception e){
                indexOfProduction = -1;
            }
            if (indexOfProduction + 1 < productionsOfTheLastSymbolFromWorkingStack.size() && indexOfProduction!=-1){
                List<String> newInputStack = a.inputStack;
                for (int i =0; i <productionsOfTheLastSymbolFromWorkingStack.get(indexOfProduction).result.size(); ++i){
                    newInputStack.remove(0);
                }
                List<String> newWorkingStack = a.workingStack;
                newInputStack.addAll(0, productionsOfTheLastSymbolFromWorkingStack.get(indexOfProduction +1).result);
                Operation op = new Operation("q", a.position, a.workingStack, newInputStack);
                Operations newOperation = new Operations("another try", op);
                this.operationList.add(newOperation);
            }else if(a.position == 1 && lastFromWorkingStack.equals(grammar.startingSymbol)){
                List<String> input = a.inputStack;
                List<String> output = a.workingStack;
                this.operationList.add(new Operations("anotherTry", new Operation("e",a.position, output, input)));

            } else {
                List<String> newInputStack = a.inputStack;
                for (int i =0; i <productionsOfTheLastSymbolFromWorkingStack.get(indexOfProduction).result.size(); ++i){
                    newInputStack.remove(0);
                }
                newInputStack.add(0, lastFromWorkingStack);
                List<String> newWorkingStack = a.workingStack;
                newWorkingStack.remove(a.workingStack.size()-1);
                Operation op = new Operation("b", a.position, newWorkingStack, newInputStack);
                Operations newOperation = new Operations("another try", op);
                this.operationList.add(newOperation);
            }

        }
    }
    public void success(Operation a){
        List<String> last = new ArrayList<>();
        last.add("e");
        Operation op = new Operation("f", a.position, a.inputStack, last);
        operationList.add(new Operations("success", op));
    }
}
