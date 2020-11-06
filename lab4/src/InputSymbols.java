import java.util.ArrayList;
import java.util.List;

public class InputSymbols {
    public List<String> symbols = new ArrayList<>();
    public InputSymbols(){

    }

    @Override
    public String toString() {
        return "InputSymbols{" +
                "symbols=" + symbols +
                '}';
    }

    public void addSymbol(String state){
        symbols.add(state);
    }
}
