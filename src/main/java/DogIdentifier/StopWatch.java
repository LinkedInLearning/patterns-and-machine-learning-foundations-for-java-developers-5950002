package DogIdentifier;

/*
 * Simplistic timer class
 */
public class StopWatch {

    private long startTime = 0;
    private long stopTime = 0;
    private boolean running = false;

    public void start() {
        this.startTime = System.currentTimeMillis();
        this.running = true;
    }

    public void stop() {
        this.stopTime = System.currentTimeMillis();
        this.running = false;
    }

    public long getElapsedTime() {                      //elapsed time in milliseconds
        long elapsed;
        if (running) {
            elapsed = (System.currentTimeMillis() - startTime);
        } else {
            elapsed = (stopTime - startTime);
        }
        return elapsed;
    }

    public double getElapsedTimeSecs() {          //elapsed time in seconds
        return getElapsedTime() / 1000.;
    }

    public static void main(String[] args) {
        StopWatch s = new StopWatch();      // You can create multiple timers, each one is an instance
        s.start();
        //code you want to time goes here
        s.stop();
        System.out.println("elapsed time in milliseconds: " + s.getElapsedTime());
        System.out.println("elapsed time in seconds: " + s.getElapsedTimeSecs());

    }
}

