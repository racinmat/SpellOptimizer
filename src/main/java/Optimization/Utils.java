package Optimization;

public class Utils {

    public static double[][] mergeMatricesHorizontally(double[][]... matrices) {
        int columns = 0;
        for (double[][] matrix : matrices) {
            columns += matrix.length;
        }
        double[][] result = new double[matrices[0][0].length][columns];
        int i = 0;
        for (double[][] matrix : matrices) {
            int rowNumber = 0;
            for (double[] row : matrix) {
                System.arraycopy(row, 0, result[rowNumber], i, matrix.length);
                rowNumber++;
            }
            i += matrix.length;
        }
        return result;
    }

//    public static double[][] mergeMatricesVertically(double[][]... matrices) {
//        int rows = 0;
//        for (double[][] matrix : matrices) {
//            rows += matrix.length;
//        }
//        double[][] result = new double[rows][matrices[0][0].length];
//        int i = 0;
//        for (double[][] matrix : matrices) {
//            for (double[] row : matrix) {
//                System.arraycopy(row, 0, result[i], 0, row.length);
//                i++;
//            }
//        }
//        return result;
//    }

    public static double[] mergeArrays(double[]... arrays) {
        int elements = 0;
        for (double[] array : arrays) {
            elements += array.length;
        }
        double[] result = new double[elements];

        int i = 0;
        for (double[] array : arrays) {
            System.arraycopy(array, 0, result, i, array.length);
            i += array.length;
        }
        return result;
    }

}
