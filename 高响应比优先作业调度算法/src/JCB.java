public class JCB {
    /*
     *作业名称
     *作业大小
     *所需要打印机的数量
     *所需要磁带机的数量
     *估计执行时间
     * 作业在系统中的等待时间
     */
    private String name = null;
    private int size = 0;
    private int printer = 0;
    private int tapes = 0;
    private double runTime = 0;
    private double waitTime = 0;

    public JCB() {

    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public int getPrinter() {
        return printer;
    }

    public int getTapes() {
        return tapes;
    }

    public double getRunTime() {
        return runTime;
    }

    public double getWaitTime() {
        return waitTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setPrinter(int printer) {
        this.printer = printer;
    }

    public void setTapes(int tapes) {
        this.tapes = tapes;
    }

    public void setRunTime(double runTime) {
        this.runTime = runTime;
    }

    public void setWaitTime(double waitTime) {
        this.waitTime = waitTime;
    }
}
