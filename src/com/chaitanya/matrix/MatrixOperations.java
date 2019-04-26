package com.chaitanya.matrix;
/*
* static matrix operations
*/
public class MatrixOperations {
    private MatrixOperations() {}

    public static Matrix multiply(Matrix mat1, Matrix mat2) {
        assert(mat1.getChannels() == mat2.getChannels());
        assert(mat1.getChannels() == 1);
        assert(mat1.getCols() == mat2.getRows());
        double[][][] result = new double[mat1.getChannels()][mat1.getRows()][mat2.getCols()];
        double[][][] mat1Arr = mat1.getMatrix();
        double[][][] mat2Arr = mat2.getMatrix();
        for(int i = 0; i < result.length; i++) {
            for(int k = 0; k < result[i].length; k++) {
                for(int j = 0; j < result[i][k].length; j++) {
                    double sum = 0;
                    for(int m = 0; m < mat1.getCols(); m++) {
                        sum += mat1Arr[i][k][m] * mat2Arr[i][m][j];
                    }
                    result[i][k][j] = sum;
                }
            }
        }
        return new Matrix(result);
    }

    public static Matrix multiply(Matrix mat, double num) {
        double[][][] result = new double[mat.getChannels()][mat.getRows()][mat.getCols()];
        double[][][] matrix = mat.getMatrix();
        for(int i = 0; i < matrix.length; i++) {
            for(int k = 0; k < matrix[i].length; k++) {
                for(int j = 0; j < matrix[i][k].length; j++) {
                    result[i][k][j] = matrix[i][k][j] * num;
                }
            }
        }
        return new Matrix(result);
    }
}