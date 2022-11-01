package echoserver;

import java.io.IOException;
import java.io.InputStream;

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
