package cn.edu.ncepu.sa.Model;

import java.util.HashSet;
import java.util.Iterator;

/**
 * 数据组件，除了引用传递还可以使用单例类
 */
public class WarData {

    public HashSet<Element> elements = new HashSet<>();
    public Tank userTank = new Tank(200, 200, 0, 10, 0.1, 1);

    public WarData() {
        elements.add(userTank);
    }

    /**
     * 依据元素的状态，处理是否还保留在数据区中
     */
    public void updateDataSet() {
        // 删除中间的元素会有问题
        /*for (Element element : elements) {
            if (element.Destroyed) {
                elements.remove(element);
            } else {

            }
        }*/

        Iterator<Element> it = elements.iterator();
        while (it.hasNext()) {
            Element tmp = it.next();
            if (tmp.Destroyed) {
                it.remove();
            }
        }
    }
}
