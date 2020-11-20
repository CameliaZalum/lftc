import java.util.ArrayList;
import java.util.List;

public class Production {
    public List<String> result;
    public Production() {
        result = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Production{" +
                "result=" + result +
                '}';
    }

    public void setResult(List<String > res){
        this.result = res;
    }
}
