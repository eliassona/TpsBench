package main.java;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Map;

public class TcpClient implements Definitions {
	@Override
	public Map<Object, Object> execute(final Options options) throws UnknownHostException, IOException {
		final Socket s = new Socket("localhost", options.port());
		try {
			s.setSendBufferSize(options.sendBuffer());
			final OutputStream out = s.getOutputStream();
			if (options.recTraffic()) {
				startReadLoop(options, s.getInputStream());
			}
			while (true) {
				final byte[] ba = new byte[options.messageSize()];
				out.write(ba, 0, ba.length);
				out.flush();
			}
		} catch (final IOException e) {
			try {
				s.close();
			} catch (final IOException e1) {
				System.out.println("Couldn't close client socket");
			}
		}
		return null;
	}

	private void startReadLoop(final Options options, final InputStream in) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				long bytesRead = 0;
				final long startTime = System.nanoTime();
				while (true) {
					final byte[] ba = new byte[options.messageSize()];
					try {
						int br = in.read(ba);
						if (br < 0) throw new IOException();
						bytesRead += br;
						if (options.clientDelayMs() > 0) {
							try {
								Thread.sleep(options.clientDelayMs());
							} catch (InterruptedException e) {
							}
						}
					} catch (final IOException e) {
						Definitions.printResult(Type.Client, bytesRead / options.messageSize(), startTime);
						return;
					}
				}
			}
		}).start();
	}
	
	
	

}
