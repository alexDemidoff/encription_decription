package encryptdecrypt.machines;

import encryptdecrypt.enums.Mode;

public class ShiftCipherMachine extends CipherMachine {

    private final String alphabet = "abcdefghijklmnopqrstuvwxyz";

    public ShiftCipherMachine(String data, int key, Mode mode) {
        super(data, key, mode);
    }

    @Override
    protected String decrypt(String data, int key) {
        return encrypt(data, -key);
    }

    @Override
    protected String encrypt(String data, int key) {
        StringBuilder result = new StringBuilder();
        String[] lines = data.split(System.lineSeparator());

        for (String line : lines) {
            for (int i = 0; i < line.length(); i++) {
                if (Character.isLetter(line.charAt(i))) {
                    result.append(encodeOneSymbol(line.charAt(i), key));
                } else {
                    result.append(line.charAt(i));
                }
            }
            result.append(System.lineSeparator());
        }

        //Deleting the line separator at the end of result text
        result.delete(result.lastIndexOf(System.lineSeparator()), result.length());

        return result.toString();
    }

    @Override
    protected char encodeOneSymbol(char c, int key) {
        int index = calculateNewPosition(c, key);

        if (Character.isUpperCase(c)) {
            return Character.toUpperCase(alphabet.charAt(index));
        } else {
            return alphabet.charAt(index);
        }
    }

    private int calculateNewPosition(char c, int key) {
        int endIndex = 26;

        int index = (alphabet.indexOf(Character.toLowerCase(c)) + key) % endIndex;
        index = index < 0 ? endIndex + index : index;

        return index;

    }
}
