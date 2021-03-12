public class Table {
    public int jobId;
    public int address;
    public int size;
    public boolean state;

    public Table(int jobId, int address, int size, boolean state) {
        this.jobId = jobId;
        this.address = address;
        this.size = size;
        this.state = state;
    }

    @Override
    public String toString() {
        return address + "k " + size + "k " + (state ? "未分配" : "空表目");
    }
}
