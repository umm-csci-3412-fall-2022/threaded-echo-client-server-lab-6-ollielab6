package Ex;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadDemo {
    private int count = 0;
    private AtomicInteger atomicCount = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        ThreadDemo demo = new ThreadDemo();
        demo.go();
    }

    public void go() throws InterruptedException {
        IncrementLots firstIncrementer = new IncrementLots(100000);
        IncrementLots secondIncrementer = new IncrementLots(200000);
        Thread firstThread = new Thread(firstIncrementer);
        Thread secondThread = new Thread(secondIncrementer);
        firstThread.start();
        secondThread.start();
        firstThread.join();
        secondThread.join();
        System.out.println("The result was " + count);
        System.out.println("The atomic result was " + atomicCount);
    }

    public class IncrementLots implements Runnable {

        private int numIncrements;

        public IncrementLots(int numIncrements) {
            this.numIncrements = numIncrements;
        }

        @Override
        public void run() {
            for (int i=0; i<numIncrements; ++i) {
                ++count;
                atomicCount.incrementAndGet();
            }
        }
    }
}
