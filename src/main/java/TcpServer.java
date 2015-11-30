package main.java;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class TcpServer implements Definitions {

	@Override
	public Map<Object, Object> execute(final Options options) throws IOException {
		final ServerSocket ss = new ServerSocket(options.port());
		final Socket socket = ss.accept();
		socket.setReceiveBufferSize(options.receiveBuffer());
		final InputStream in = socket.getInputStream();
		if (options.recTraffic()) {
			startWriteLoop(options.messageSize(), socket.getOutputStream());
		}
		long readBytes = 0;
		final long startTime = System.nanoTime();
		while (true) {
			final int ab = in.available();
			final byte[] ba = new byte[ab];
			readBytes += in.read(ba, 0, ab);
			final long nrOfMessages = (readBytes / options.messageSize());
			if ((nrOfMessages % 1000000) == 0) {
				System.out.println("Received messages: " + nrOfMessages);
			}
			if (nrOfMessages >= options.nrOfMessages()) {
				Definitions.printResult(Type.Server, nrOfMessages, startTime);
        		socket.close();
        		ss.close();
        		return Definitions.mapOf(nrOfMessages, startTime);
			}
			if (options.serverDelayMs() > 0) {
				try {
					Thread.sleep(options.serverDelayMs());
				} catch (InterruptedException e) {
				}
			}
		}
	}

	private void startWriteLoop(int messageSize, OutputStream out) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					final byte[] ba = new byte[messageSize];
					try {
						out.write(ba);
					} catch (IOException e) {
						return;
					}
				}
			}
		}).start();
	}
	
	
}
