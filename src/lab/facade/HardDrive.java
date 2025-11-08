package lab.facade;

public class HardDrive {
	private int readCount = 0;

	public byte[] read(long lba, int size) {
		readCount++;
		System.out.println(System.currentTimeMillis() + " | HardDrive: reading " + size + " bytes from LBA " + lba);
		// return dummy data
		byte[] data = new byte[size];
		for (int i = 0; i < size; i++) data[i] = (byte) (i % 256);
		return data;
	}

	public int getReadCount() {
		return readCount;
	}
}