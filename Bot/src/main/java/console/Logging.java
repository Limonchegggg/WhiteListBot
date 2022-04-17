package console;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class Logging{
	public void Log(String label) {
		System.out.println("[WhiteListBot Logging] " + label);
	}
	public String getLogs() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		PrintStream old = System.out;
		System.setOut(ps);
		System.out.flush();
		System.setOut(old);
		return baos.toString();
	}
}
