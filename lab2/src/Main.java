import java.io.File;
import java.io.FileNotFoundException;


class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner1 = new Scanner("C:\\Users\\camel\\Desktop\\faculta2\\lftc\\lab1b\\token.in", "out1.txt");
        scanner1.checkProgram("C:\\Users\\camel\\Desktop\\faculta2\\lftc\\lab1a\\p1.txt");
        Scanner scanner2 = new Scanner("C:\\Users\\camel\\Desktop\\faculta2\\lftc\\lab1b\\token.in", "out2.txt");
        scanner2.checkProgram("C:\\Users\\camel\\Desktop\\faculta2\\lftc\\lab1a\\p2.txt");
        Scanner scanner3 = new Scanner("C:\\Users\\camel\\Desktop\\faculta2\\lftc\\lab1b\\token.in", "out3.txt");
        scanner3.checkProgram("C:\\Users\\camel\\Desktop\\faculta2\\lftc\\lab1a\\p3.txt");
        Scanner scanner4 = new Scanner("C:\\Users\\camel\\Desktop\\faculta2\\lftc\\lab1b\\token.in", "out4.txt");
        scanner4.checkProgram("C:\\Users\\camel\\Desktop\\faculta2\\lftc\\lab1a\\p1err.txt");
    }
}