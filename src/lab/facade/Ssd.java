package lab.facade;

public class Ssd implements IHardDrive {
    private int readCount = 0;

    @Override
    public byte[] read(long lba, int size) {
        readCount++;
        System.out.println(System.currentTimeMillis() + " | SSD: reading " + size + " bytes from LBA " + lba);
        byte[] data = new byte[size];
        for (int i = 0; i < size; i++) data[i] = (byte) ((i * 2) % 256); // different dummy pattern
        return data;
    }

    public int getReadCount() {
        return readCount;
    }
}
