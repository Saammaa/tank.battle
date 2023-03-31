package cn.edu.ncepu.sa.Model;

import java.util.HashSet;

public class WarData {
    public HashSet<Element> elements = new HashSet<>();
    public Tank userTank = new Tank();

    public WarData() {
        elements.add(userTank);
    }

    public void updateStates() {
        for (Element element : elements) {
            if (element.Destroyed) {
                elements.remove(element);
            } else {

            }
        }
    }
}
