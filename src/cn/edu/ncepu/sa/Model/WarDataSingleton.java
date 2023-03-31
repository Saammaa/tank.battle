package cn.edu.ncepu.sa.Model;

public class WarDataSingleton {

    private static WarData warData;

    public WarDataSingleton() {
        warData = new WarData();
    }

    public static WarData getInstance() {
        if (warData == null) {
            warData = new WarData();
        }
        return warData;
    }

}
