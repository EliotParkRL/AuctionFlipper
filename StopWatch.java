public class StopWatch {
    private double startTime;
    private double endTime;
    private boolean running;

    public StopWatch(){}

    /**
     * Sets the startTime to the current time and running to true
     */
    public void startWatch(){
        startTime = System.nanoTime();
        running = true;
    }

    /**
     * Sets the endTime to the current time and running to false
     */
    public void endWatch(){
        endTime = System.nanoTime();
        running = false;
    }

    /**
     * Returns the time interval between startTime and endTime/current time.
     * @return the time interval
     */
    public double getTimeSeconds(){
        if(running == true){
            return (Math.round((System.nanoTime()-startTime)/(Math.pow(10, 7))))/(Math.pow(10, 2));
        } else {
            return (Math.round((endTime-startTime)/(Math.pow(10, 7))))/(Math.pow(10, 2));
        }
    }
}
