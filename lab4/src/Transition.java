import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Pair {
    String state;
    String symbol;

    @Override
    public String toString() {
        return "Pair{" +
                "state='" + state + '\'' +
                ", symbol='" + symbol + '\'' +
                '}';
    }

    Pair(String a, String b){
        state = a;
        symbol = b;
    }
}

public class Transition {
    public Map<Pair, String> transitions  = new HashMap<>();

    @Override
    public String toString() {
        return "Transition{" +
                "transitions=" + transitions +
                '}';
    }

    public Transition() {

    }
    public void addTransition(String state, String symbol, String result){
        transitions.put(new Pair(state, symbol), result);
    }
}
