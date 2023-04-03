package cn.edu.ncepu.sa.Model;

/**
 * 数据组件单例类方法，单例类相当于全局变量，谨慎使用
 */
public class WarDataSingleton {

    /**
     * 唯一数据副本
     */
    private static WarData warData;

    /**
     * 构造函数
     */
    public WarDataSingleton() {
        warData = new WarData();
    }

    /**
     * 获取数据单例
     *
     * @return 数据单例
     */
    public static WarData getInstance() {
        if (warData == null) {
            warData = new WarData();
        }
        return warData;
    }

}
