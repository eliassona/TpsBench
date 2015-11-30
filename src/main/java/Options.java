package main.java;

public interface Options {
	int sendBuffer();
	int receiveBuffer();
	int messageSize();
	int nrOfMessages();
	boolean recTraffic();
	int port();
	int serverDelayMs();
	int clientDelayMs();
	boolean noDelay();
	boolean disableFragments();
}
