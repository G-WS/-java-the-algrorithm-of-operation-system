public class Test {
    public static void main(String[] args){
        MemoryManage memoryManage = new MemoryManage();
        Job[] jobs = new Job[8];
        jobs[0] = new Job(1,10,"申请");
        jobs[1] = new Job(3,4,"申请");
        jobs[2] = new Job(4,12,"申请");
        jobs[3] = new Job(2,6,"申请");
        jobs[4] = new Job(4,12,"释放");
        jobs[5] = new Job(4,6,"申请");
        jobs[6] = new Job(3,4,"释放");
        jobs[7] = new Job(2,6,"释放");


        System.out.println("初始化空闲区说明表");
        memoryManage.printer();
        for (Job job:jobs){
            System.out.println();
            System.out.println();
            System.out.println("执行作业"+job.id+"任务是"+job.doWhat+job.need+"K");
            System.out.println("此时空闲分区表如下：");
            try {
                memoryManage.manage(job);
            }catch (JobNotFindException|MemoryFullException e){
                e.printStackTrace();
            }

        }

    }
}
