import java.util.ArrayList;
import java.util.List;

public class States {
    public List<String> states = new ArrayList<>();
    public String initialState;
    public List<String> finalStates = new ArrayList<>();
    public States(){

    }
    public void addState(String state){
        states.add(state);
    }

    public void setInitialState(String state){
        initialState = state;
    }

    @Override
    public String toString() {
        return "States{" +
                "states=" + states +
                '}';
    }

    public void addFinalState(String state){
        finalStates.add(state);
    }
    public String printFinalStates(){
        String a= "";
        for (String state:finalStates) {
            a += state + "; ";
        }
        return a;
    }
}
