package encryptdecrypt;

import encryptdecrypt.machines.CipherMachineManager;

public class Main {

    public static void main(String[] args) {
        CipherMachineManager cipherMachineManager = new CipherMachineManager();

        cipherMachineManager.parseTerminalArgs(args);
        cipherMachineManager.createCipherMachine();
        cipherMachineManager.runCipherMachine();
    }
}
