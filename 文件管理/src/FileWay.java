import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FileWay {
    public Map<String,FileWay> subMap = new HashMap<String,FileWay>();
    private String name;
    private String type;
    private int way;//用来识别是文件还是目录
    private int address;//在fat中的起始位置
    private int size;
    private FileWay upper = null;//文件的上一级目录
    public FileWay(String name,String type,int address,int size){
        this.address = address;
        this.name = name;
        this.type = type;
        this.way = 2;
        this.size = size;
    }
    public FileWay(String name,int address){
        this.name = name;
        this.way = 3;
        this.address = address;
        this.type = " ";
        this.size = 1;
    }

    public Map<String, FileWay> getSubMap() {
        return subMap;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getWay() {
        return way;
    }

    public int getAddress() {
        return address;
    }

    public int getSize() {
        return size;
    }

    public FileWay getUpper() {
        return upper;
    }

    public void setSubMap(Map<String, FileWay> subMap) {
        this.subMap = subMap;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setWay(int way) {
        this.way = way;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setUpper(FileWay upper) {
        this.upper = upper;
    }
}
