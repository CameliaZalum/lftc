import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scanner {
    SymbolTable<String, Integer> identifiers = new SymbolTable<>();
    SymbolTable<String, Integer> constants = new SymbolTable<>();
    List<String> tokens = new ArrayList<>();
    Map<String, Integer> pif = new HashMap<>();
    File outFile;
    String output;

    /**
     * creates a scanner for the program
     * @param tokens - file with all the tokens valid for the programs
     * @param outFile - the name of the output file
     */
    public Scanner(String tokens, String outFile) {
        File myObj = new File(tokens);
        System.out.println(myObj);
        java.util.Scanner myReader = null;
        try {
            myReader = new java.util.Scanner(myObj);
            int index = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(data.matches(".+:")) {
                    data = myReader.nextLine();
                }

                    this.tokens.add(data);

            }
            myReader.close();
        } catch (FileNotFoundException e) {
            myReader.close();
            e.printStackTrace();
        }
        System.out.println(this.tokens);
        try {
            this.outFile = new File(outFile);
            if ( this.outFile.createNewFile()) {
                System.out.println("File created: " +  this.outFile.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


    }

    /**
     * given the path for the program it outputs "lexically correct or lexically incorrect after the analysis
     * @param file - path of the program to be analyzed
     */
    public void checkProgram(String file) {
        File myObj = new File(file);
        java.util.Scanner myReader = null;
        int numberC = 0;
        int numberI = 0;
        boolean ok = true;
        try {
            myReader = new java.util.Scanner(myObj);
            int index = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dataTo = data.split(" |\n|\\[|\\]|\\{|\\}|\\:|\\~|\\}|\\.");

                for (int i = 0; i < dataTo.length; i++) {
//                    System.out.println(dataTo[i]);
                    if (dataTo[i].matches("\b[0-9]+\b")) {
                        SymbolTable.Node a = constants.add(dataTo[i], numberC);
                        System.out.println("this is a constant" + dataTo[i]);
                        pif.put(a.key, (Integer) a.val);
                        numberC++;
                    } else if (dataTo[i].matches("\b[^0-9][a-zA-Z+][0-9*]\b") && !tokens.contains(dataTo[i])) {
                        SymbolTable.Node b = identifiers.add(dataTo[i], numberI);
                        pif.put(b.key, (Integer) b.val);
                        numberI++;
                    } else if(tokens.contains(dataTo[i])) {
                        pif.put(dataTo[i], 0);
                    } else if(!tokens.contains(dataTo[i])){
                       output += "lexically incorrect " + dataTo[i] + "\n";
                       ok = false;
                    }
                }
            }
            if (ok) {
                output = "lexically correct";
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(outFile.getName()));
            writer.write(output);
            System.out.println(output);
            System.out.println(outFile.getAbsolutePath());
            writer.close();
        }catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
