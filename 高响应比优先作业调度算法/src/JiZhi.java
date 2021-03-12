import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class JiZhi {
    //后备队列
    static List<JCB> jobs = new ArrayList<JCB>();
    static double k1 = 0, k2 = 0;//相应比和记录的当前作业的相应比

    public static void shedule(JCB job, long memory, int tape, int printer) {
        if (job.getSize() <= memory && job.getTapes() <= tape && job.getPrinter() <= printer) {
            k1 = job.getWaitTime() / job.getRunTime();
            if (jobs.size() == 0) {
                k2 = k1;
                jobs.add(job);
            } else {
                if (k2 > k1) {
                    jobs.add(job);
                } else {
                    k2 = k1;
                    jobs.add(job);
                    Collections.swap(jobs, 0, jobs.size() - 1);//交换两者执行顺序
                }
            }
        } else {
            System.out.println("error");
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int tape = 4;
        int printer = 2;
        int memory = 65535;//64M=65535K
        System.out.println("输入作业数量");
        int n = in.nextInt();
        JCB job=null;
        for (int i = 0; i < n; i++) {
            job = new JCB();
            System.out.println("输入作业" + (i + 1) + "的作业名称");
            job.setName(in.next());
            System.out.println("输入作业" + (i + 1) + "的作业大小");
            job.setSize(in.nextInt());
            System.out.println("输入作业" + (i + 1) + "所使用磁带机数");
            job.setTapes(in.nextInt());
            System.out.println("输入作业" + (i + 1) + "所使用打印机数");
            job.setPrinter(in.nextInt());
            System.out.println("输入作业" + (i + 1) + "的输入作业等待时间");
            job.setWaitTime(in.nextDouble());
            System.out.println("输入作业" + (i + 1) + "的估计执行时间");
            job.setRunTime(in.nextDouble());
            JiZhi.shedule(job,memory,tape,printer);

        }
        System.out.println("当前执行程序为："+job.getName());
        memory-=jobs.get(0).getSize();
        System.out.println("系统剩余内存为："+memory+"KB");
        tape-=jobs.get(0).getTapes();
        System.out.println("系统剩余的磁带机数为："+tape);
        printer-=jobs.get(0).getPrinter();
        System.out.println("系统剩余打印机数量为："+printer);

    }
}
