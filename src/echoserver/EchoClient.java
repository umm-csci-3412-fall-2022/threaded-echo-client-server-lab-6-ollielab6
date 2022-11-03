package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class EchoClient {
	public static final int PORT_NUMBER = 6013;

	public static void main(String[] args) throws IOException {
		EchoClient client = new EchoClient();
		client.start();
	}

	private void start() throws IOException {
		try (Socket socket = new Socket("localhost", PORT_NUMBER)) {
			InputStream socketInputStream = socket.getInputStream();
			OutputStream socketOutputStream = socket.getOutputStream();

			// Put your code here.
			InThread inThread = new InThread(socketOutputStream);
			Thread Tin = new Thread(inThread);
			Tin.start();

			OutThread outThread = new OutThread(socketInputStream);
			Thread Tout = new Thread(outThread);
			Tout.start();
		}
	}

	public class OutThread implements Runnable {

		private InputStream socket;

		public OutThread(InputStream socketInputStream) {
			socket = socketInputStream;
		}

		@Override
		public void run() {
			try {
				while (true) {
					System.out.write(socket.read());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public class InThread implements Runnable {

		private OutputStream socketOutputStream;

		public InThread(OutputStream socketOutputStream) {
			this.socketOutputStream = socketOutputStream;
		}

		@Override
		public void run() {
			int data;
			try {
				data = System.in.read();
				while (data >= 0) {
					socketOutputStream.write(data);
					data = System.in.read();
				}
				socketOutputStream.flush();
				socketOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}
}
