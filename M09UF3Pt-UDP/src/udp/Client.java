package udp;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import lib.ConsoleInterface;

public class Client {
	private static final int BUFFER_SIZE = 1024;
	private InetAddress serverAddress;
	private int serverPort;
	private DatagramSocket socket;
	private ConsoleInterface consoleInterface;
	int attemp = 0;

	public void init(String host, int port) throws SocketException, UnknownHostException {
		serverAddress = InetAddress.getByName(host);
		serverPort = port;
		socket = new DatagramSocket();
	}

	public void runClient() throws IOException {
		byte [] receivedData = new byte[1024];
		byte [] sendingData;
		int[] data;
		
		sendingData = getRequest();
		while(mustContinue(sendingData)) {
			DatagramPacket packet = new DatagramPacket(sendingData, 
					sendingData.length, serverAddress, serverPort);
			socket.send(packet);
			packet = new DatagramPacket(new byte[1024], 1024);
			socket.setSoTimeout(5000);
			try {
				socket.receive(packet);
				sendingData = getDataToRequest(packet.getData(), packet.getLength());
			}catch(SocketTimeoutException e) {
				data = timeoutExceeded(packet);
			}
		}
	}


	private int[] timeoutExceeded(DatagramPacket packet) throws IOException {
		int[] ret;
		if(attemp < 3) {
			attemp++;
			ret = getInts(packet.getData(), 3);
		}else {
			ret = new int[] { -1 };
		}
		return ret;
	}

	private byte[] getDataToRequest(byte[] data, int length) {
		// TODO Auto-generated method stub
		return null;
	}

	private void close() {
		if (socket != null && !socket.isClosed()) {
			socket.close();
		}
	}
	
	private byte[] getRequest() throws IOException {
		short op1 = consoleInterface.readShort("Escriu el primer numero");
		short op2 = consoleInterface.readShort("Escriu el segon numero");
		char operation = consoleInterface.readOperation("Escriu la operacio ('+', '-', '*' or '/')");
		Request request = new Request(operation, op1, op2);
		
		return request.getBytes();
	}
	
	private boolean mustContinue(byte[] sendingData) {
		return (sendingData.length != 1 || sendingData[0] != -1);
	}
	
	private int[] getInts(byte[] data, int size) throws IOException{
		int[] result = new int[size];
		DataInputStream dataIn = new DataInputStream(new ByteArrayInputStream(data));
		for(int i=0; i<size; i++) {
			result[i] = dataIn.readInt();
		}
		try {
			dataIn.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
}