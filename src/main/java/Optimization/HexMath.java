package Optimization;

public class HexMath {

    public static int getArea(int radius) {
        int area = 1;
        if (radius == 0) {
            return area;
        }
        for (int i = 0; i < radius; i++) {
            area += (radius + i + 1) * 2;
        }
        area += 2 * radius;
        return area;
    }
}
