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

    // Matrix multiplication
    public void multiply(Matrix mat) {
        assert (mat.getChannels() == 1);
        assert (channels == 1);
        assert (cols == mat.getRows());
        double[][][] result = new double[channels][rows][mat.getCols()];
        double[][][] matArr = mat.getMatrix();
        for (int i = 0; i < result.length; i++) {
            for (int k = 0; k < result[i].length; k++) {
                for (int j = 0; j < result[i][k].length; j++) {
                    double sum = 0;
                    for (int m = 0; m < cols; m++) {
                        sum += matrix[i][k][m] * matArr[i][m][j];
                    }
                    result[i][k][j] = sum;
                }
            }
        }
        setMatrix(result);
    }

    // Multiply by value
    public void multiply(double num) {
        double[][][] result = new double[this.getChannels()][this.getRows()][this.getCols()];
        for (int i = 0; i < matrix.length; i++) {
            for (int k = 0; k < matrix[i].length; k++) {
                for (int j = 0; j < matrix[i][k].length; j++) {
                    result[i][k][j] = matrix[i][k][j] * num;
                }
            }
        }
        setMatrix(result);
    }

    // Important for backpropogation(Activations)
    public void multiplyElementwise(Matrix mat) {
        assert (this.getChannels() == mat.getChannels());
        assert (this.getRows() == mat.getRows());
        assert (this.getCols() == mat.getCols());
        double[][][] result = new double[this.getChannels()][this.getRows()][this.getCols()];
        double[][][] matArr = mat.getMatrix();
        for (int i = 0; i < matrix.length; i++) {
            for (int k = 0; k < matrix[i].length; k++) {
                for (int j = 0; j < matrix[i][k].length; j++) {
                    result[i][k][j] = matrix[i][k][j] * matArr[i][k][j];
                }
            }
        }
        setMatrix(result);
    }

    public double[] toArray() {
        double[] result = new double[channels * cols * rows];
        int count = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int k = 0; k < matrix[i].length; k++) {
                for (int j = 0; j < matrix[i][k].length; j++) {
                    result[count] = matrix[i][k][j];
                    count++;
                }
            }
        }
        return result;
    }

    // Method helps with flattening layers
    public void reshape(int channels, int rows, int cols) {
        assert (this.channels * this.rows * this.cols == channels * cols * rows);
        double[][][] result = new double[channels][rows][cols];
        double[] matArr = toArray();
        int count = 0;
        for (int i = 0; i < result.length; i++) {
            for (int k = 0; k < result[i].length; k++) {
                for (int j = 0; j < result[i][k].length; j++) {
                    result[i][k][j] = matArr[count];
                    count++;
                }
            }
        }
        setMatrix(result);
    }

    public boolean isEmpty() {
        return matrix.length == 0 || matrix == null;
    }

    // Stride = 1
    // Padding = "valid"
    public Matrix convolution(Matrix mat) {
        assert(!mat.isEmpty());
        assert(!isEmpty());
        double[][][] result = new double[1][this.getRows() - mat.getRows() + 1][this.getCols() - mat.getCols() + 1];
        double[][][] matArr = mat.getMatrix();
        for (int k = 0; k < result[0].length; k++) {
            for (int j = 0; j < result[0][k].length; j++) {
                double sum = 0;
                for (int i = 0; i < matrix.length; i++) {
                    for(int m = k; m < k + mat.getRows(); k++) {
                        for(int n = j; n < j + mat.getCols(); n++) {
                            sum += matrix[i][m][n] * matArr[i][m - k][n - j];
                        }
                    }
                }
                result[0][k][j] = sum;
            }
        }
        return new Matrix(result);
    }

    //Stride = 2
    //PoolSize = 2
    public Matrix maxPooling() {
        double[][][] result = new double[channels][rows/2][cols/2];
        for(int i = 0; i < matrix.length; i++) {
            for(int k = 0; k < matrix[i].length; k += 2) {
                for(int j = 0; j < matrix[i][k].length; j++) {
                    double max = matrix[i][k][j];
                    for(int m = k; m < k + 2; m++) {
                        for(int n = j; n < j + 2; n++) {
                            if(matrix[i][m][n] > max) {
                                max = matrix[i][m][n];
                            }
                        }
                    }
                    result[i][k/2][j/2] = max;
                }
            } 
        }
        return new Matrix(result);
    }

    public Matrix[] split(int channels) {
        Matrix[] mats = new Matrix[this.getChannels()/channels];
        for(int i = 0; i < mats.length; i++) {
            for(int k = 0; k < channels; k++) {
                double[][][] result = new double[channels][this.getRows()][this.getCols()];
                for(int m = 0; m < this.getRows(); m++) {
                    for(int n = 0; n < this.getCols(); n++) {
                        result[k][m][n] = matrix[i * channels + k][m][n];
                    }
                }
                mats[i] = new Matrix(result);
            }
        }
        return mats;
    }

    public void add(Matrix mat) {
        assert (this.getChannels() == mat.getChannels());
        assert (this.getRows() == mat.getRows());
        assert (this.getCols() == mat.getCols());
        double[][][] result = new double[this.getChannels()][this.getRows()][this.getCols()];
        double[][][] matArr = mat.getMatrix();
        for (int i = 0; i < matrix.length; i++) {
            for (int k = 0; k < matrix[i].length; k++) {
                for (int j = 0; j < matrix[i][k].length; j++) {
                    result[i][k][j] = matrix[i][k][j] + matArr[i][k][j];
                }
            }
        }
        setMatrix(result);
    }

}