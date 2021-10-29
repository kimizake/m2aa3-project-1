import java.util.ArrayList;
import java.util.List;

public class vector {

  private final int dim;
  private List<Double> values;


  public vector(int dim) {
    this.dim = dim;
    values = allZeroes(dim);
  }

  public vector(List<Double> values) {
    dim = values.size();
    this.values = values;
  }

  public vector(vector vector) {
    dim = vector.dim;
    values = copyValues(vector.values);
  }

  public int getDim() {
    return dim;
  }

  public void setValues(List<Double> values) {
    this.values = values;
  }

  public List<Double> getValues() {
    return values;
  }

  private List<Double> copyValues(List<Double> values) {
    List<Double> out = new ArrayList<>();
    for (Double x : values) {
      out.add(x);
    }
    return out;
  }

  private List<Double> allZeroes(int dim) {
    List<Double> z = new ArrayList<>();
    for(int i = 0; i < dim; i++)
      z.add((double) 0);
    return z;
  }

  public Double getValue(int i){
    return values.get(i);
  }

  public vector addVector(vector b) {
    assert(dim == b.dim) : "invalid dimensions";

    for (int i = 0; i < dim; i++) {
      values.set(i, values.get(i) + b.values.get(i));
    }

    return this;
  }

  public vector subtractVector(vector b) {
    assert(dim == b.dim) : "invalid dimensions";

    for (int i = 0; i < dim; i++) {
      values.set(i, values.get(i) - b.values.get(i));
    }

    return this;
  }

  public vector multiplyScalar(double x) {
    for(int i = 0; i < dim; i++) {
      values.set(i, values.get(i) * x);
    }
    return this;
  }

  public double innerProduct(vector b) {
    assert(dim == b.dim) : "invalid dimensions";

    double sum = 0;

    for (int i = 0; i < dim; i++) {
      sum += values.get(i) * b.values.get(i);
    }
    return sum;
  }

  public double norm() {
    return Math.sqrt(innerProduct(this));
  }

  public matrix transpose(){
    matrix out = new matrix(1, dim);

    List<vector> vectors = new ArrayList<>();

    for (Double val : values) {
      vector _val = new vector(1);

      List<Double> vector = new ArrayList<>();

      vector.add(val);

      _val.setValues(vector);

      vectors.add(_val);
    }
    out.setVectors(vectors);
    return out;
  }
}
