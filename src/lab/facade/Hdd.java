package lab.facade;

public class Hdd implements IHardDrive {
    private int readCount = 0;

    @Override
    public byte[] read(long lba, int size) {
        readCount++;
        System.out.println(System.currentTimeMillis() + " | HDD: reading " + size + " bytes from LBA " + lba);
        byte[] data = new byte[size];
        for (int i = 0; i < size; i++) data[i] = (byte) (i % 256);
        return data;
    }

    public int getReadCount() {
        return readCount;
    }
}
