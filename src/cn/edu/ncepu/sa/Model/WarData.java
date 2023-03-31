package cn.edu.ncepu.sa.Model;

import java.util.HashSet;

public class WarData {
    public static HashSet<Element> elements = new HashSet<>();
    public static Tank userTank = new Tank();

    static {
        elements.add(userTank);
    }

    public void updateStates() {
        for (Element element : elements
        ) {
            if (element.Destroyed) {
                elements.remove(element);
            }
        }
    }
}
