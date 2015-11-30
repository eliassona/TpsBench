package main.java;


import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.Map;

import com.sun.nio.sctp.InvalidStreamException;
import com.sun.nio.sctp.MessageInfo;
import com.sun.nio.sctp.SctpChannel;
import com.sun.nio.sctp.SctpStandardSocketOptions;

public class SctpClient implements Definitions {
	@Override
	public Map<Object, Object> execute(final Options options) {
        try { 
            final SocketAddress socketAddress = new InetSocketAddress(options.port()); 
            System.out.println("open connection for socket [" + socketAddress + "]"); 
            final SctpChannel sctpChannel = SctpChannel.open();//(socketAddress, 1 ,1 );
            try { 
            	sctpChannel.bind(new InetSocketAddress( 1112)); //TODO remove this
            } catch (final Exception e) {
            	System.out.println("Could not bind to 1112");
            	e.printStackTrace();
            	throw e;
            }
            sctpChannel.connect(socketAddress, 1 ,1);
            sctpChannel.setOption(SctpStandardSocketOptions.SCTP_INIT_MAXSTREAMS, SctpStandardSocketOptions.InitMaxStreams.create(8, 8));
            sctpChannel.setOption(SctpStandardSocketOptions.SCTP_NODELAY, options.noDelay());
            sctpChannel.setOption(SctpStandardSocketOptions.SO_SNDBUF, options.sendBuffer());
            sctpChannel.setOption(SctpStandardSocketOptions.SO_RCVBUF, options.receiveBuffer());
            System.out.println("Supported options: " + sctpChannel.supportedOptions());
            System.out.println("sctpChannel.association() = " + sctpChannel.association());

            System.out.println("send bytes"); 
            final ByteBuffer byteBuffer = ByteBuffer.allocate(options.messageSize()); 
//            int ix = 0;
            while (true) {
                final MessageInfo messageInfo = MessageInfo.createOutgoing(null, 0); 
                messageInfo.unordered(true);
                messageInfo.payloadProtocolID(46);
                final byte [] message = new byte[options.messageSize()]; 
	            byteBuffer.put(message); 
	            byteBuffer.flip();
	            try { 
	                sctpChannel.send(byteBuffer, messageInfo);
	                byteBuffer.flip();
//		            ix = (ix + 1);
//		            if (ix >= 7) ix = 0;
	            } catch (final InvalidStreamException e) {
//	            	System.out.println("Invalid stream ix: " + ix);
	            	throw e;
	            } catch (final Exception e) {
	            	e.printStackTrace();
	            	break;
	            } 
            }
            System.out.println("close connection"); 
            sctpChannel.close();

        } catch (final Exception e) { 
            e.printStackTrace(); 
        } 
        return null;
    }

}