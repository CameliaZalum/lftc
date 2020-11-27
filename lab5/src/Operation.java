import java.util.ArrayList;
import java.util.List;

public class Operation {
    public String state;
    public int position;
    public List<String> workingStack;
    public List<String> inputStack;

    @Override
    public String toString() {
        return "Operation{" +
                "state='" + state + '\'' +
                ", position=" + position +
                ", workingStack=" + workingStack +
                ", inputStack=" + inputStack +
                '}';
    }

    public Operation(String state, int position, List<String> workingStack, List<String> inputStack) {
        this.state = state;
        this.position = position;
        this.workingStack = workingStack;
        this.inputStack = inputStack;
    }

public List<String> copyWorkingList(){
        List<String> copy = new ArrayList<>();
    for (String k: workingStack
         ) {
       copy.add(k);
    }
    return copy;
}

    public List<String> copyInputList(){
        List<String> copy = new ArrayList<>();
        for (String k: inputStack
        ) {
            copy.add(k);
        }
        return copy;
    }

}
