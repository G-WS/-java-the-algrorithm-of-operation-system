import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FileManager {
    public Map<String, FileWay> AllFiles = new HashMap<String, FileWay>();
    private int[] Fat = new int[128];
    private FileWay root = new FileWay("root", 1);
    private FileWay nowCatalog = root;

    public FileManager() {
        for (int i = 0; i < Fat.length; i++) {
            Fat[i] = 0;
        }
        Fat[1] = 255;//255表示磁盘块已经被占用
        Fat[0] = 126;//记录磁盘剩余块数
        root.setUpper(root);
        AllFiles.put("root", root);
    }

    public int setFat(int size) {
        int[] startNum = new int[128];
        int i = 2;
        for (int j = 0; j < size; j++) {
            if (Fat[i] == 0) {
                startNum[j] = i;
                if (j > 0) {
                    Fat[startNum[j - 1]] = i;
                }
                j++;
            }
        }
        Fat[i - 1] = 225;
        return startNum[0];
    }

    public void deleteFat(int startNum) {
        int nextPoint = Fat[startNum];
        int nowPoint = startNum;
        int count = 0;
        while (Fat[nowPoint] != 0) {
            nextPoint = Fat[nowPoint];
            if (nextPoint == 225) {
                Fat[nowPoint] = 0;
                count++;
                break;
            } else {
                Fat[nowPoint] = 0;
                count++;
                nowPoint = nextPoint;
            }
        }
        Fat[0] += count;
    }

    public void changeFat(int startNum, int add) {
        int nowPoint = startNum;
        int nextPoint = Fat[startNum];
        while (Fat[nowPoint] != 255) {
            nowPoint = nextPoint;
            nextPoint = Fat[nowPoint];
        }
        for (int i = 2, count = 0; count < add; i++) {
            if (Fat[i] == 0) {
                Fat[nowPoint] = i;
                nowPoint = i;
                count++;
                Fat[nowPoint] = 255;
            }
        }
    }

    public void showFile() {
        System.out.println("***************** < " + nowCatalog.getName() + " > *****************");

        if (!nowCatalog.subMap.isEmpty()) {
            for (FileWay value : nowCatalog.subMap.values()) {
                if (value.getWay() == 3) { //目录文件
                    System.out.println("文件名 : " + value.getName());
                    System.out.println("操作类型 ： " + "文件夹");
                    System.out.println("起始盘块 ： " + value.getAddress());
                    System.out.println("大小 : " + value.getSize());
                    System.out.println("<-------------------------------------->");
                } else if (value.getWay() == 2) {
                    System.out.println("文件名 : " + value.getName() + "." + value.getType());
                    System.out.println("操作类型 ： " + "可读可写文件");
                    System.out.println("起始盘块 ： " + value.getAddress());
                    System.out.println("大小 : " + value.getSize());
                    System.out.println("<-------------------------------------->");
                }
            }
        }
        for (int i = 0; i < 2; i++)
            System.out.println();
        System.out.println("磁盘剩余空间 ：" + Fat[0] + "            " + "退出系统请输入exit");
        System.out.println();
    }


    public void createFile(String name, String type, int size) {
        if (Fat[0] >= size) {
            FileWay value = nowCatalog.subMap.get(name);
            if (value != null) {
                if (value.getWay() == 3) {
                    int startNum = setFat(size);
                    FileWay file = new FileWay(name, type, startNum, size);
                    file.setUpper(nowCatalog);
                    nowCatalog.subMap.put(file.getName(), file);
                    AllFiles.put(file.getName(), file);
                    Fat[0] -= size;
                    System.out.println("创建文件成功");
                    showFile();
                } else if (value.getWay() == 2) {
                    System.out.println("创建失败，该文件已存在");
                    showFile();
                }

            } else if (value == null) { //若无同名文件或文件夹，继续创建文件
                int startNum = setFat(size);
                FileWay file = new FileWay(name, type, startNum, size);
                file.setUpper(nowCatalog);
                nowCatalog.subMap.put(name, file);
                AllFiles.put(file.getName(), file);
                Fat[0] -= size;
                System.out.println("创建文件成功！");
                showFile();
            }
        } else {
            System.out.println("创建文件失败，磁盘空间不足！");
        }
    }
    public void createCatolog(String name) {

        if(Fat[0] >= 1) { //判断磁盘空间是否足够创建文件夹

            FileWay value = nowCatalog.subMap.get(name); //判断该目录下是否存在同名目录或文件
            if(value != null) {
                if(value.getWay() == 2) {
                    int startNum = setFat(1);
                    FileWay catalog = new FileWay(name, startNum);
                    catalog.setUpper(nowCatalog);
                    nowCatalog.subMap.put(name, catalog);
                    Fat[0]--;
                    AllFiles.put(catalog.getName(), catalog);
                    System.out.println("创建目录成功！");
                    showFile();
                } else if(value.getWay() == 3) {
                    System.out.println("创建目录失败，该目录已存在！");
                    showFile();
                }
            } else if(value == null) {
                int startNum = setFat(1);
                FileWay catalog = new FileWay(name, startNum);
                catalog.setUpper(nowCatalog); //纪录上一层目录
                nowCatalog.subMap.put(name, catalog);
                Fat[0]--;
                AllFiles.put(catalog.getName(), catalog);
                System.out.println("创建目录成功！");
                showFile();
            }
        } else {
            System.out.println("创建目录失败，磁盘空间不足！");
        }
    }


}
