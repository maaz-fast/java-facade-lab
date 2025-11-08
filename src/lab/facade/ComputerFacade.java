package lab.facade;
public class ComputerFacade {
	private final Cpu cpu;
	private final Memory memory;
	private final IHardDrive hd;
	// Saved state when sleeping (simulated)
	private byte[] savedState;
	private boolean sleeping = false;
private static final long BOOT_ADDRESS = 0x1000;
private static final long BOOT_SECTOR = 0x2000;
private static final int SECTOR_SIZE = 64;
public ComputerFacade() {
this.cpu = new Cpu();
this.memory = new Memory();
		// default to an HDD implementation
		this.hd = new Hdd();
}
	// Additional constructor for dependency injection / testability
	public ComputerFacade(Cpu cpu, Memory memory, IHardDrive hd) {
		this.cpu = cpu;
		this.memory = memory;
		this.hd = hd;
	}
public void start() {
System.out.println("Facade: starting computer");
cpu.freeze();
byte[] bootData = hd.read(BOOT_SECTOR, SECTOR_SIZE);
memory.load(BOOT_ADDRESS, bootData);
cpu.jump(BOOT_ADDRESS);
cpu.execute();
System.out.println("Facade: computer started\n");
}
public void shutdown() {
		System.out.println("Facade: shutting down computer");
		// Print a small metrics/report about subsystem activity
		int memWrites = memory.getWriteCount();
		int hdReads = 0;
		if (hd instanceof Hdd) {
			hdReads = ((Hdd) hd).getReadCount();
		} else if (hd instanceof Ssd) {
			hdReads = ((Ssd) hd).getReadCount();
		} else if (hd instanceof HardDrive) {
			hdReads = ((HardDrive) hd).getReadCount();
		}
		System.out.println(System.currentTimeMillis() + " | Facade report: Memory writes=" + memWrites + ", HD reads=" + hdReads);
		// In a real system you'd order components gracefully
		System.out.println("Facade: power off\n");
}

	/**
	 * Simulate putting the computer to sleep: save state and reduce power.
	 */
	public void sleep() {
		System.out.println(System.currentTimeMillis() + " | Facade: saving state");
		// Simulate saving state by reading a block (placeholder for actual state capture)
		this.savedState = hd.read(BOOT_SECTOR, SECTOR_SIZE);
		System.out.println(System.currentTimeMillis() + " | Facade: state saved (" + (savedState != null ? savedState.length : 0) + " bytes)");
		System.out.println(System.currentTimeMillis() + " | Facade: reducing power (simulated)");
		sleeping = true;
		System.out.println(System.currentTimeMillis() + " | Facade: sleeping\n");
	}

	/**
	 * Simulate waking the computer and restoring previously saved state.
	 */
	public void wake() {
		if (!sleeping) {
			System.out.println(System.currentTimeMillis() + " | Facade: already awake");
			return;
		}
		System.out.println(System.currentTimeMillis() + " | Facade: waking up, restoring state");
		if (savedState != null) {
			memory.load(BOOT_ADDRESS, savedState);
			cpu.jump(BOOT_ADDRESS);
			cpu.execute();
			System.out.println(System.currentTimeMillis() + " | Facade: state restored");
		} else {
			System.out.println(System.currentTimeMillis() + " | Facade: no saved state to restore");
		}
		sleeping = false;
		System.out.println(System.currentTimeMillis() + " | Facade: awake\n");
	}
}