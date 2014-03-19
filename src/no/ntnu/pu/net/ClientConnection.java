package no.ntnu.pu.net;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ClientConnection implements Connection {

	@Override
	public void connect(InetAddress remoteAddress, int remotePort)
			throws IOException, SocketTimeoutException {
		Socket clientSocket = new Socket(remoteAddress, remotePort);
	}

	@Override
	public void accept() throws IOException, SocketTimeoutException {
		// TODO Auto-generated method stub

	}

	@Override
	public void send(String msg) throws ConnectException, IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public String receive() throws ConnectException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub

	}

}
