import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class MemoryManage {
    private List<Table> list = new LinkedList<>();

    public MemoryManage() {
        list.add(new Table(0, 0, 128, true));
    }

    public void manage(Job job) throws JobNotFindException, MemoryFullException {
        if ("申请".equals(job.doWhat)) {
            apply(job);
            printer();
        } else if ("释放".equals(job.doWhat)) {
            release(job);
            printer();
        }
    }

    private void apply(Job job) throws MemoryFullException {
        for (Table t : this.list) {
            if (t.state && t.size >= job.need) {
                if (t.size == job.need) {
                    t.jobId = job.id;
                    t.state = false;
                } else {
                    Table tTrue = new Table(job.id, t.address, job.need, false);
                    Table tFalse = new Table(job.id, t.address + job.need, t.size - job.need, true);
                    list.remove(t);
                    list.add(tTrue);
                    list.add(tFalse);
                }
                this.list.sort(new Comparator<Table>() {
                    @Override
                    public int compare(Table o1, Table o2) {
                        return o1.address - o2.address;
                    }
                });
                return;
            }

        }
        throw new MemoryFullException("内存不够");
    }

    private void release(Job job) throws JobNotFindException {
        for (Table t : this.list) {
            if (t.jobId == job.id) {
                t.state = true;
                for (int address = list.indexOf(t) - 1; address >= 0 && list.get(address).state; address--) {
                    Table t1 = list.get(address);
                    t1.size += t.size;
                    list.remove(t);
                    t = t1;
                }
                for (int address = list.indexOf(t) + 1; address < list.size() && list.get(address).state; address++) {
                    Table t1 = list.get(address);
                    t.size += t1.size;
                    list.remove(t1);
                }
                return;
            }
        }
        throw new JobNotFindException("未找到要释放的内存位置");
    }

    public void printer() {
        System.out.println("-----------------");
        System.out.println("起址 长度 状态");
        for (Table t : this.list) {
            if (t.state) {
                System.out.println(t.toString());
            }
        }
        System.out.println("-----------------");
    }
}
