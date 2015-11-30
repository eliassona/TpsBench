package main.java;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.Map;

import com.sun.nio.sctp.SctpChannel;
import com.sun.nio.sctp.SctpServerChannel;
import com.sun.nio.sctp.SctpStandardSocketOptions;

public class SctpServer implements Definitions  {
	@Override
	public Map<Object, Object> execute(final Options options) throws IOException {
        final SocketAddress serverSocketAddress = new InetSocketAddress(options.port()); 
        System.out.println("create and bind for sctp address"); 
        final SctpServerChannel sctpServerChannel =  SctpServerChannel.open().bind(serverSocketAddress); 
        System.out.println("address bind process finished successfully");
        sctpServerChannel.setOption(SctpStandardSocketOptions.SCTP_INIT_MAXSTREAMS, SctpStandardSocketOptions.InitMaxStreams.create(8, 8));

        SctpChannel sctpChannel; 
        while ((sctpChannel = sctpServerChannel.accept()) != null) { 
            sctpChannel.setOption(SctpStandardSocketOptions.SCTP_INIT_MAXSTREAMS, SctpStandardSocketOptions.InitMaxStreams.create(8, 8));
            sctpChannel.setOption(SctpStandardSocketOptions.SCTP_NODELAY, options.noDelay());
            sctpChannel.setOption(SctpStandardSocketOptions.SO_SNDBUF, options.sendBuffer());
            sctpChannel.setOption(SctpStandardSocketOptions.SO_RCVBUF, options.receiveBuffer());
            sctpChannel.setOption(SctpStandardSocketOptions.SCTP_DISABLE_FRAGMENTS, options.disableFragments());
            int nrOfMessages = 0;
            final long startTime = System.nanoTime();
            final ByteBuffer bb = ByteBuffer.allocate(options.messageSize());
            while (true) {
            	sctpChannel.receive(bb , null, null);
            	bb.flip();
            	bb.flip();
            	nrOfMessages++;
            	if (nrOfMessages >= options.nrOfMessages()) {
    				Definitions.printResult(Type.Server, nrOfMessages, startTime);
            		return Definitions.mapOf(nrOfMessages, startTime);
            	}
            }

        } 
        return null;
    } 
}


