package console;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class Logging{
	public Logging() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		PrintStream old = System.out;
		System.setOut(ps);
		System.out.flush();
		System.setOut(old);
		System.out.println(baos.toString());
	}
}
