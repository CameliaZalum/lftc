
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;


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

    /**
     * Computes the hash value of a String
     * @param key The string for the hash computation
     * @return (sn-1 + 16(sn-2 + .. + 16(s1+16s0))) value computed for the string
     */
    private int hash(String key) {
        int value = 0;
        for (int i = 0; i < key.length(); i++) {
            value = (value << 4) + key.charAt(i);
        }
        return value;
    }

    /**
     * Gets a node by its key
     * @param key String to search for
     * @return the Node (key and position in the tokens list) from the symbol table
     */
    public Node get(String key) {
        int i = hash(key);
        for (Node x = st.get(i); x != null; x = x.next) {
            if (key.equals(x.key)) return x;
        }
        return null;
    }

    /**
     * Adds a value in the symbol table
     * @param key String to be added
     * @param val the position of the key in the tokens list
     * @return a Node (key and position in the tokens list) that was added
     */
    public Node add(String key, Value val) {
        if (val == null) {
            remove(key);
            return null;
        }

        int i = hash(key);
        if (get(key) != null) {
            return get(key);
        } else {
            st.put(i, new Node(key, val, null));
            return get (key);
        }
    }

    /**
     * Removes a key from the symbol table
     * @param key String to be removed
     */
    public void remove(String key) {
        int i = hash(key);
        st.put(i, remove(st.get(i), key));

    }

    /**
     * Removes a value from the symbol table
     * @param x - the node that needs to be removed
     * @param key - the key to be removed
     * @return the value that was removed
     */
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
                stt.add(dataTo[i], index);
                index++;
            }
        }
        myReader.close();
        // print keys
        for (SymbolTable.Node s : stt.st.values())
            System.out.println(s );

    }
}