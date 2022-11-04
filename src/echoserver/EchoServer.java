package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

	// REPLACE WITH PORT PROVIDED BY THE INSTRUCTOR
	public static final int PORT_NUMBER = 6013;

	public static void main(String[] args) throws IOException, InterruptedException {
		EchoServer server = new EchoServer();
		server.start();
	}

	/**
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private void start() throws IOException, InterruptedException {
		try (ServerSocket serverSocket = new ServerSocket(PORT_NUMBER)) {
			while (true) {
				Socket socket = serverSocket.accept();

				// Put your code here.
				// This should do very little, essentially:
				// * Construct an instance of your runnable class
				// * Construct a Thread with your runnable
				// * Or use a thread pool
				// * Start that thread
				ServerRunnable serverRunnable = new ServerRunnable(socket);
				Thread thread = new Thread(serverRunnable);
				thread.start();
			}
		}
	}

	public class ServerRunnable implements Runnable {
		Socket client;

		public ServerRunnable(Socket socket) {
			client = socket;
		}

		@Override
		public void run() {
			try {
				InputStream input = client.getInputStream();
				OutputStream output = client.getOutputStream();
				int data = input.read();
				while (data >= 0) {
					output.write(data);
					data = input.read();
				}
				output.flush();
				client.shutdownOutput();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
