package main.java;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public interface Definitions {

	public static long toTps(final long nrOfMessages, final long duration) {
		return (nrOfMessages * 1000) / duration;
	}

	public enum Type {Client, Server}
	
	public static void printResult(final Type type, final long nrOfMessages, final long startTime) {
		final long duration = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
		System.out.println(String.format("Type: %s, %s messages sent, duration %s ms, tps %s", type, nrOfMessages, duration, toTps(nrOfMessages, duration)));
	}

	public static Map<Object, Object> mapOf(final Object...nameValuePairs) {
		final Map<Object, Object> m = new HashMap<>();
		for (int i = 0; i < nameValuePairs.length; i = i + 2) {
			m.put(nameValuePairs[i], nameValuePairs[i + 1]);
		}
		return m;
	}
	
	public static Map<Object, Object> mapOf(final long nrOfMessages, final long startTime) {
		long duration = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
		return mapOf("nr-of-messages", nrOfMessages, "duration-ms", duration, "tps", toTps(nrOfMessages, duration));
	}
	
	
	Map<Object, Object> execute(Options options) throws IOException;
	
	
}
