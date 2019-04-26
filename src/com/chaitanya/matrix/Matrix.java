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

}