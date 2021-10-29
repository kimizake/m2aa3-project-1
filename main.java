import java.util.ArrayList;
import java.util.List;

public class main {

  private static double K = Math.pow(10, -1);

  public static void main(String[] args) {
    algorithms a = new algorithms(generateMatrix());

    runCgs(a);
    runMgs(a);
  }

  private static void runCgs(algorithms a) {
    System.out.println("|=======================CGS START=======================|");

    a.cgs();

    System.out.println("cgs Q:");
    a.getQ().print();
    System.out.println("cgs R:");
    printSolve(a);
  }

  private static void runMgs(algorithms a) {
    System.out.println("|=======================MGS START=======================|");

    a.mgs();

    System.out.println("mgs Q:");
    a.getQ().print();
    System.out.println("mgs R:");
    printSolve(a);
  }

  private static void printSolve(algorithms a) {
    printIdentityDiff(a);

    matrix x = a.solve(generateB());
    System.out.println("Ax = b:");
    x.print();
    vector r = generateB().subtractVector(generateMatrix().multiply(x).getVector(0));
    System.out.println("residual norm = " + r.norm());
  }

  private static void printIdentityDiff(algorithms a) {
    a.getR().print();

    matrix Q = a.getQ();
    matrix _Q = Q.transpose();

    System.out.println("QT*Q - I:");
    _Q.multiply(Q).subtract(generateIdentityMatrix()).print();
  }

  private static matrix generateMatrix() {
    matrix m = new matrix(4, 4);

    List<vector> vs = new ArrayList<>();

    List<Double> col1 = new ArrayList<>();
    List<Double> col2 = new ArrayList<>();
    List<Double> col3 = new ArrayList<>();
    List<Double> col4 = new ArrayList<>();

    col1.add(-2.0);
    col1.add(K);
    col1.add(0.0);
    col1.add(0.0);

    col2.add(-1.0);
    col2.add(0.0);
    col2.add(K);
    col2.add(0.0);

    col3.add(1.0);
    col3.add(0.0);
    col3.add(0.0);
    col3.add(K);

    col4.add(2.0);
    col4.add(0.0);
    col4.add(0.0);
    col4.add(0.0);

    setMatrixVectors(vs, col1, col2);
    setMatrixVectors(vs, col3, col4);

    m.setVectors(vs);

    return m;
  }

  private static matrix generateIdentityMatrix() {
    matrix m = new matrix(4, 4);

    List<vector> vs = new ArrayList<>();

    List<Double> col1 = new ArrayList<>();
    List<Double> col2 = new ArrayList<>();
    List<Double> col3 = new ArrayList<>();
    List<Double> col4 = new ArrayList<>();

    col1.add(1.0);
    col1.add(0.0);
    col1.add(0.0);
    col1.add(0.0);

    col2.add(0.0);
    col2.add(1.0);
    col2.add(0.0);
    col2.add(0.0);

    col3.add(0.0);
    col3.add(0.0);
    col3.add(1.0);
    col3.add(0.0);

    col4.add(0.0);
    col4.add(0.0);
    col4.add(0.0);
    col4.add(1.0);

    setMatrixVectors(vs, col1, col2);
    setMatrixVectors(vs, col3, col4);

    m.setVectors(vs);

    return m;
  }

  private static vector generateB() {
    List<Double> bs = new ArrayList<>();

    for(int i = 0; i < 4; i++)
      bs.add(1.0);

    return new vector(bs);
  }

  private static void setMatrixVectors(List<vector> vs, List<Double> col1, List<Double> col2) {
    vector v;
    v = new vector(4);
    v.setValues(col1);
    vs.add(v);
    v = new vector(4);
    v.setValues(col2);
    vs.add(v);
  }

}
