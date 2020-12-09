import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RecursiveDescendentAlgorithm {
    String test;
    List<String> testList = new ArrayList<>();
    String result;
    Grammar grammar;
    int n;
    RecursiveDescendent rd;
    String state;
    List<Integer> fathers = new ArrayList<>();
    int position = 1;
    List<String> inputStack = new ArrayList<>();
    List<String> workingStack = new ArrayList<>();

    RecursiveDescendentAlgorithm(String test, Grammar grammar) throws InterruptedException {
        this.test = test;
        this.grammar = grammar;
        String[] a = test.split(" ");

        for (String b : a) {
            testList.add(b);
        }
        RecursiveDescendent.MAXIMUM_POSITIONS = testList.size();
        rd = new RecursiveDescendent(grammar);
        this.n = testList.size();

        state = "q";
        position = 1;
        inputStack = new ArrayList<>();
        inputStack.add(grammar.startingSymbol);
        workingStack = new ArrayList<>();
        this.processResult();
    }

    public String getResult() {
        return result;
    }

    public void processResult() throws InterruptedException {
        while (!(state.equals("f") || state.equals("e"))) {
//            Thread.sleep(1000);
            for (Operations a : rd.operationList) {
                System.out.println(a.type);
                System.out.println(a.operation);
            }
            System.out.println("---------------------------------------------------------------");
            if ((inputStack.size() == 0 || inputStack.size() == 1) && position == n + 1) {

                rd.success(new Operation(state, position, workingStack, inputStack));
                renewParameters();
                break;

            }
            try {
                if (inputStack.get(0).equals("e")) {
                    inputStack.remove(0);
                }
            } catch (Exception e) {
                if (!state.equals("f")) {
                    state = "b";
                } else {
                    break;
                }
            }

            if (state.equals("q")) {

                if (grammar.nonTerminals.contains(inputStack.get(0))) {
                    rd.expand(new Operation(state, position, workingStack, inputStack));
                    renewParameters();
                } else {
                    try {
                        if (inputStack.get(0).equals(testList.get(position - 1))) {
                            rd.advance(new Operation(state, position, workingStack, inputStack));
                            renewParameters();
                        } else {
                            state = "b";
                        }
                    } catch (Exception e) {
                        state = "b";
                    }
                }
            } else {
                if (state.equals("b")) {
                    if (grammar.terminals.contains(workingStack.get(workingStack.size() - 1))) {
                        rd.backOperation(new Operation(state, position, workingStack, inputStack));
                        renewParameters();

                    } else {
                        rd.anotherTry(new Operation(state, position, workingStack, inputStack));

                    }
                    renewParameters();
                }
            }
        }
        if (state.equals("e")) {
            result = "error";
        } else {
            if (state.equals("f")) {
                result = "success";
            }
        }

    }

    private void renewParameters() {
        state = rd.operationList.get(rd.operationList.size() - 1).operation.state;
        position = rd.operationList.get(rd.operationList.size() - 1).operation.position;
        inputStack = rd.operationList.get(rd.operationList.size() - 1).operation.copyInputList();
        workingStack = rd.operationList.get(rd.operationList.size() - 1).operation.copyWorkingList();
    }
}
