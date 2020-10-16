
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import static jdk.nashorn.internal.objects.ArrayBufferView.length;

public class SymbolTable<Key, Value>  {

    public HashMap<Integer, Node> st;    // array of linked-list symbol tables

    public static class Node {
        public final String key;
        private Object val;
        private Node next;

        public Node(String key, Object val, Node next)  {
            this.key  = key;
            this.val  = val;
            this.next = next;
        }

        @Override
        public String toString() {
            return key + " "  + val + " ; ";
        }
    }

    public SymbolTable() {
        st = new HashMap<>();
    }

    // Hash value = (sn-1 + 16(sn-2 + .. + 16(s1+16s0)))
    private int hash(String key) {
        int value = 0;
        for (int i = 0; i < key.length(); i++) {
            value = (value << 4) + key.charAt(i);
        }
        return value;
    }

    public Value get(String key) {
        int i = hash(key);
        for (Node x = st.get(i); x != null; x = x.next) {
            if (key.equals(x.key)) return (Value) x.val;
        }
        return null;
    }

    public void put(String key, Value val) {
        if (val == null) {
            remove(key);
            return;
        }

        int i = hash(key);
        try {
            for (Node x = st.get(i); x != null; x = x.next) {
                if (key.equals(x.key)) {
                    x.val = val;
                    return ;
                }
            }
        } catch (Exception e) {
            st.put(i, new Node(key, val, null));

        }
    }
    public void remove(String key) {
        int i = hash(key);
        st.put(i, remove(st.get(i), key));

    }

    private Node remove(Node x, String key) {
        x.next = remove(x.next, key);
        return x;
    }

}

class Main {

    public static void main(String[] args) throws FileNotFoundException {
        SymbolTable<String, Integer> stt = new SymbolTable<>();


        File myObj = new File("C:\\Users\\camel\\Desktop\\faculta2\\lftc\\lab2\\src\\tokens.txt");
        Scanner myReader = new Scanner(myObj);
        int index = 0;
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            String[] dataTo = data.split(" ");
            for (int i = 0; i < dataTo.length; i++) {
                stt.put(dataTo[i], index);
                index++;
            }
        }
        myReader.close();
        // print keys
        for (SymbolTable.Node s : stt.st.values())
            System.out.println(s + " " + stt.get(s.key));

    }
}