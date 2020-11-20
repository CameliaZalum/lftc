import java.util.List;

public class Operation {
    public String state;
    public int position;
    public List<String> workingStack;
    public List<String> inputStack;

    public Operation(String state, int position, List<String> workingStack, List<String> inputStack) {
        this.state = state;
        this.position = position;
        this.workingStack = workingStack;
        this.inputStack = inputStack;
    }



}
