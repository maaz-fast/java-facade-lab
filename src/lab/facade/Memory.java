package lab.facade;
public class Memory {
	private int writeCount = 0;

	public void load(long position, byte[] data) {
		writeCount++;
		System.out.println(System.currentTimeMillis() + " | Memory: loading " + data.length + " bytes at " + position);
	}

	public int getWriteCount() {
		return writeCount;
	}
}
 