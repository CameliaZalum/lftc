import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grammar {
    public List<String> terminals;
    public List<String> nonTerminals;
    public Map<String, List<Production>> productions;
    public String startingSymbol;

    public Grammar (){
        terminals = new ArrayList<>();
        nonTerminals = new ArrayList<>();
        productions = new HashMap<>();

    }

    public void setTerminals(List<String> terminals) {
        this.terminals = terminals;
    }
    public void setNonTerminals(List<String> nonTerminals){
        this.nonTerminals = nonTerminals;
    }

    @Override
    public String toString() {
        return "Grammar{" +
                "terminals=" + terminals +
                ", nonTerminals=" + nonTerminals +
                ", productions=" + productions +
                ", startingSymbol='" + startingSymbol + '\'' +
                '}';
    }

    public void addProduction(String prod, Production p){
        try {
            if (this.productions.get(prod) != null){
                this.productions.get(prod).add(p);
            } else {
                List<Production> newProduction = new ArrayList<>();
                newProduction.add(p);
                this.productions.put(prod, newProduction);
            }
        } catch (Exception e) {
            System.out.println("addProduction failed");
        }
    }

    public void setStartingSymbol(String startingSymbol) {
        this.startingSymbol = startingSymbol;
    }
}
