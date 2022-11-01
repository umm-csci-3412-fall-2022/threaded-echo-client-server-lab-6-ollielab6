package echoserver;

import java.io.IOException;
import java.io.OutputStream;

public class InThread implements Runnable {

    private OutputStream socketOutputStream;

    public InThread(OutputStream socketOutputStream) {
        socketOutputStream = socketOutputStream;
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
