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
            List<String> newInputStack = a.inputStack;
            Production results = grammar.productions.get(a.workingStack.get(0)).get(0);
            String k = " ";
            for (String x: results.result){
                k += x;
            }
            newInputStack.add(a.workingStack.get(0) + "1");
            List<String> newWorkingStack = a.workingStack;
            newWorkingStack.add(0, k);
            Operation op = new Operation(a.state, a.position, newInputStack, newWorkingStack);
            Operations newOperation = new Operations("expand", op);
            operationList.add(newOperation );
        }

    }
    public void advance(Operation a){
        if(this.grammar.terminals.contains(a.workingStack.get(0))) {
            if(a.workingStack.get(0).equals(inputSequence.get(a.position))) {
                List<String> newInputStack = a.inputStack;
                newInputStack.add(a.workingStack.get(0));
                List<String> newWorkingStack = a.workingStack;
                newWorkingStack.remove(0);
                Operation op = new Operation(a.state, a.position + 1, newInputStack, newWorkingStack);
                Operations newOperation = new Operations("advance", op);
                this.operationList.add(newOperation);
            }
        }
    }
    public void momentaryInsuccess(Operation a){
        if(this.grammar.terminals.contains(a.workingStack.get(0))) {
            if(!a.workingStack.get(0).equals(inputSequence.get(a.position))) {
                List<String> newInputStack = a.inputStack;
                List<String> newWorkingStack = a.workingStack;
                Operation op = new Operation("b", a.position, newInputStack, newWorkingStack);
                Operations newOperation = new Operations("momentary insuccess", op);
                this.operationList.add(newOperation);
            }
        }
    }
    public void backOperation(Operation a){
        if(grammar.terminals.contains(a.workingStack.get(0))){
            List<String> newInputStack = a.inputStack;
            newInputStack.remove(newInputStack.size() - 1);
            List<String> newWorkingStack = a.workingStack;
            newWorkingStack.add(0, a.inputStack.get(a.inputStack.size() -1));
            Operation op = new Operation(a.state, a.position - 1, newInputStack, newWorkingStack);
            Operations newOperation = new Operations("back", op);
            this.operationList.add(newOperation);
        }
    }
    public void anotherTry(Operation a){
        if(grammar.nonTerminals.contains(a.workingStack.get(0))) {
            System.out.println("va urma... :D");
            String lastSymbol = a.inputStack.get(a.inputStack.size()-1);
            String lastSymbolWithoutIndex = lastSymbol.split("[0-9]")[0];
            int index = Integer.parseInt(lastSymbol.replace(lastSymbolWithoutIndex, ""));

        }
    }
    public void success(Operation a){
        List<String> last = new ArrayList<>();
        last.add("e");
        Operation op = new Operation("f", inputSequence.size() + 1, a.inputStack, last);
    }
}
