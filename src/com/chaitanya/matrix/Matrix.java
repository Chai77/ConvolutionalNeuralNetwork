package com.chaitanya.matrix;
/*
* Matrix class
* Stores 3D matrix
*/
public class Matrix {
    
    private double[][][] matrix;
    private int rows, cols, channels;

    public Matrix(int rows, int cols, int channels) {
        this.rows = rows;
        this.cols = cols;
        this.channels = channels;
        this.matrix = new double[channels][rows][cols];
    }

    public Matrix(double[][][] matrix) {
        this.matrix = matrix;
        this.channels = matrix.length;
        this.rows = matrix[0].length;
        this.cols = matrix[0][0].length;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getChannels() {
        return channels;
    }

    public double[][][] getMatrix() {
        return matrix;
    }

    public void setMatrix(double[][][] matrix) {
        this.matrix = matrix;
        this.channels = matrix.length;
        this.rows = matrix[0].length;
        this.cols = matrix[0][0].length;
    }

    public void multiply(Matrix mat) {
        assert(mat.getChannels() == 1);
        assert(channels == 1);
        assert(cols == mat.getRows());
        double[][][] result = new double[channels][rows][mat.getCols()];
        double[][][] matArr = mat.getMatrix();
        for(int i = 0; i < result.length; i++) {
            for(int k = 0; k < result[i].length; k++) {
                for(int j = 0; j < result[i][k].length; j++) {
                    double sum = 0;
                    for(int m = 0; m < cols; m++) {
                        sum += matrix[i][k][m] * matArr[i][m][j];
                    }
                    result[i][k][j] = sum;
                }
            }
        }
        setMatrix(result);
    }

    public void multiply(double num) {
        double[][][] result = new double[this.getChannels()][this.getRows()][this.getCols()];
        for(int i = 0; i < matrix.length; i++) {
            for(int k = 0; k < matrix[i].length; k++) {
                for(int j = 0; j < matrix[i][k].length; j++) {
                    result[i][k][j] = matrix[i][k][j] * num;
                }
            }
        }
        setMatrix(result);
    }

    public void multiplyElementwise(Matrix mat) {
        assert(this.getChannels() == mat.getChannels());
        assert(this.getRows() == mat.getRows());
        assert(this.getCols() == mat.getCols());
        double[][][] result = new double[this.getChannels()][this.getRows()][this.getCols()];
        double[][][] matArr = mat.getMatrix();
        for(int i = 0; i < matrix.length; i++) {
            for(int k = 0; k < matrix[i].length; k++) {
                for(int j = 0; j < matrix[i][k].length; j++) {
                    result[i][k][j] = matrix[i][k][j] * matArr[i][k][j];
                }
            }
        }
        setMatrix(result);
    }
}