package encryptdecrypt.machines;

import encryptdecrypt.enums.Mode;

public abstract class CipherMachine {
    protected Mode mode;
    protected int key;
    protected String data;

    public CipherMachine(String data, int key, Mode mode) {
        this.data = data;
        this.key = key;
        this.mode = mode;
    }

    public void perform() {
        if (mode == Mode.ENC) {
            System.out.print(encrypt(data, key));
        } else if (mode == Mode.DEC) {
            System.out.print(decrypt(data, key));
        }
    }

    protected abstract String decrypt(String data, int key);

    protected abstract String encrypt(String data, int key);

    protected abstract char encodeOneSymbol(char c, int key);
}
