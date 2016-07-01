package Optimization;

public class Utils {

    public static double[][] mergeArrays(double[][]... arrays) {
        int size = 0;
        for (double[][] array : arrays) {
            size += array.length;
        }
        double[][] result = new double[size][arrays[0][0].length];
        int i = 0;
        for (double[][] array : arrays) {
            System.arraycopy(array, 0, result, i, array.length);
        }
        return result;
    }
}
