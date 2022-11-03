package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class EchoClient {
	public static final int PORT_NUMBER = 6013;

	public static void main(String[] args) throws IOException, InterruptedException {
		EchoClient client = new EchoClient();
		client.start();
	}

	private void start() throws IOException, InterruptedException {
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

			Tin.join();
			//Shutdown
			socket.shutdownOutput();
			Tout.join();
			//shutdown
			
			socket.close();

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
				int data = socket.read();
				while (data >= 0) {
					System.out.write(data);
					data = socket.read();
				}
				System.out.flush();
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
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}
}
