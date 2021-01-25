package encryptdecrypt.machines;

import encryptdecrypt.enums.Mode;

public class UnicodeCipherMachine extends CipherMachine {

    public UnicodeCipherMachine(String data, int key, Mode mode) {
        super(data, key, mode);
    }

    @Override
    protected String decrypt(String data, int key) {
        return encrypt(data, -key);
    }

    //Encodes in symbols excluding the special ones
    @Override
    protected String encrypt(String data, int key) {
        StringBuilder result = new StringBuilder();
        String[] lines = data.split(System.lineSeparator());

        for (String line : lines) {
            for (int i = 0; i < line.length(); i++) {
                result.append(encodeOneSymbol(line.charAt(i), key));
            }
            result.append(System.lineSeparator());
        }

        //Deleting the line separator at the end of result text
        result.delete(result.lastIndexOf(System.lineSeparator()), result.length());

        return result.toString();
    }

    @Override
    protected char encodeOneSymbol(char c, int key) {
        int index = calculateNewUnicodePosition(c, key);

        return (char) index;
    }

    private int calculateNewUnicodePosition(char c, int key) {
        //We define borders to exclude the special symbols
        int startIndex = 32;
        int endIndex = 127;

        int index = (c + key) % endIndex;
        index = index < 0 ? endIndex - index : index;
        index = index < startIndex ? index + startIndex : index;

        return index;
    }

}
