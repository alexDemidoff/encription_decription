package encryptdecrypt.machines;

import encryptdecrypt.enums.Algorithm;
import encryptdecrypt.enums.Mode;
import encryptdecrypt.ProcessIO;
import encryptdecrypt.enums.State;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class CipherMachineManager implements ProcessIO {

    private Mode mode = Mode.ENC;
    private int key = 0;
    private String data = "";
    private Algorithm alg = Algorithm.SHIFT;

    private CipherMachine chosenCipherMachine;

    public State programState = State.SUCCESS;

    public void createCipherMachine() {
        if (programState == State.SUCCESS) {
            switch (alg) {
                case SHIFT:
                    chosenCipherMachine = new ShiftCipherMachine(data, key, mode);
                    break;
                case UNICODE:
                    chosenCipherMachine = new UnicodeCipherMachine(data, key, mode);
                    break;
            }
        } else {
            printErrorMessage();
        }
    }

    @Override
    public void printErrorMessage() {
        standardOut.println("Error");
    }

    @Override
    public void parseTerminalArgs(String[] args) {
        parseTerminalArgs(Arrays.asList(args));
    }

    @Override
    public void setOutputToFile(String fileName) {
        try {
            System.setOut(new PrintStream(new FileOutputStream(fileName)));
        } catch (FileNotFoundException e) {
            programState = State.ERROR;
        }
    }

    @Override
    public String readFromFile(String fileName) {
        StringBuilder stb = new StringBuilder();

        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(fileName));
            String line;

            while ((line = fileReader.readLine()) != null) {
                stb.append(line);
                stb.append(System.lineSeparator());
            }

            //Deleting the line separator at the end of result text (Linux: \n or Win: \r\n)
            stb.delete(stb.lastIndexOf(System.lineSeparator()), stb.length());

            fileReader.close();
        } catch (IOException e) {
            programState = State.ERROR;
        }

        return stb.toString();
    }

    @Override
    public void checkValues(List<String> args) {
        //If the the last element is an argument than is does not have a value
        if (arguments.contains(args.get(args.size() - 1))) {
            programState = State.ERROR;
        }

        for (int i = 0; i < args.size() - 1 && programState == State.SUCCESS; i += 2) {
            if (arguments.contains(args.get(i)) && arguments.contains(args.get(i + 1))) {
                programState = State.ERROR;
            }
        }
    }

    private void parseTerminalArgs(List<String> args) {
        checkValues(args);
        if (programState == State.SUCCESS) {
            for (int i = 0; i < args.size(); i += 2) {
                switch (args.get(i)) {
                    case "-mode":
                        mode = Mode.valueOf(args.get(i + 1).toUpperCase());
                        break;
                    case "-key":
                        key = Integer.parseInt(args.get(i + 1));
                        break;
                    case "-data":
                        data = args.get(i + 1);
                        break;
                    case "-in":
                        if (!args.contains("-data")) { //-data is preferred over -in
                            data = readFromFile(args.get(i + 1));
                        }
                        break;
                    case "-out":
                        setOutputToFile(args.get(i + 1));
                        break;
                    case "-alg":
                        alg = Algorithm.valueOf(args.get(i + 1).toUpperCase());
                        break;
                }

            }
        }
    }

    public void runCipherMachine() {
        if (programState == State.SUCCESS) {
            chosenCipherMachine.perform();
        }
    }
}
