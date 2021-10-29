import java.util.ArrayList;
import java.util.List;

public class matrix {

  private final int m; //rows
  private final int n; //columns

  private List<vector> vectors = new ArrayList<>();

  public matrix(int m, int n) {
    this.m = m;
    this.n = n;
  }

  public matrix(int m, int n, List<vector> newVectors) {
    this(m, n);
    vectors = newVectors;
  }

  public int getM() {
    return m;
  }

  public int getN() {
    return n;
  }

  public vector getVector(int i) {
    return vectors.get(i);
  }

  public void setVectors(List<vector> vectors) {
    this.vectors = vectors;
  }

  public matrix transpose() {
    List<vector> newVectors = new ArrayList<>();
    for (int row = 0; row < m; row++) {

      List<Double> values = new ArrayList<>();

      for (int col = 0; col < n; col++) {
        double val = vectors.get(col).getValue(row);
        values.add(val);
      }

      vector v = new vector(n);
      v.setValues(values);
      newVectors.add(v);
    }
    return new matrix(n, m, newVectors);
  }

  public matrix multiply(matrix b) {
    assert(n == b.m) : "invalid matrix dimensions";

    matrix _x = this.transpose();

    List<vector> vs = new ArrayList<>();

    for (vector bv : b.vectors) {
      List<Double> vals = new ArrayList<>();
      for (vector _xv : _x.vectors) {
        vals.add(_xv.innerProduct(bv));
      }
      vector v = new vector(m);
      v.setValues(vals);
      vs.add(v);
    }

    matrix m = new matrix(this.m, b.n, vs);
    return m;
  }

  public void print() {

    matrix _this = this.transpose();

    for (vector v :
        _this.vectors) {
      System.out.println(v.getValues());
    }
    System.out.println();
  }

  public matrix subtract(matrix b) {
    assert(m == b.m && n == b.n);

    for (int i = 0; i < n; i++)
      vectors.get(i).subtractVector(b.vectors.get(i));

    return this;
  }
}
