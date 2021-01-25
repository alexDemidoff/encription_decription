package encryptdecrypt;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

public interface ProcessIO {
    List<String> arguments = Arrays.asList("-mode", "-key", "-data", "-in", "-out", "-alg");

    PrintStream standardOut = System.out;

    void printErrorMessage();

    void parseTerminalArgs(String[] args);

    void setOutputToFile(String fileName);

    String readFromFile(String fileName);

    void checkValues(List<String> args);
}
