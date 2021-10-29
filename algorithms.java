import java.util.ArrayList;
import java.util.List;

public class algorithms {

  
  private final matrix A;
  private matrix Q = null;
  private matrix R = null;


  public algorithms(matrix a) {
    A = a;
  }

  public matrix getQ() {
    return this.Q;
  }

  public matrix getR() {
    return this.R;
  }
  
  public void cgs() { //return Q
    
    Q = new matrix(A.getM(), A.getN());
    List<vector> qs = new ArrayList<>();
    List<vector> rs = new ArrayList<>();

    R = new matrix(A.getN(), A.getN());
    
    vector v1 = new vector(A.getVector(0));
    vector q_i = new vector(v1);
    q_i.multiplyScalar(1 / v1.norm());
    
    qs.add(q_i);
    rs.add(
        q_i.transpose().multiply(A).transpose().getVector(0)
    );

    for(int j = 1; j < A.getN(); j++) {
      
      vector a_j = new vector(A.getVector(j));
      
      vector v_j = new vector(a_j);
      v_j.subtractVector(sum(a_j, qs));

      q_i = new vector(v_j);
      q_i.multiplyScalar(1 / v_j.norm());
      qs.add(q_i);

      rs.add(
        q_i.transpose().multiply(A).transpose().getVector(0)
      );
    }

    Q.setVectors(qs);
    R.setVectors(rs);
    R = R.transpose();
  }

  public void mgs() {

    Q = new matrix(A.getM(), A.getN());
    List<vector> qs = new ArrayList<>();
    List<vector> rs = new ArrayList<>();


    List<List<vector>> vs = new ArrayList<>();

    List<vector> _vs;

    for (int j = 0; j < A.getN(); j++) {
      _vs = new ArrayList<>();
      _vs.add(new vector(A.getVector(j)));
      vs.add(_vs);
    }

    vector v;
    vector q_i;

    for (int i = 0; i < A.getN() - 1; i++) {

      v = new vector(vs.get(i).get(i));
      q_i = new vector(v);

      q_i.multiplyScalar(1 / v.norm());

      for (int j = i + 1; j < A.getN(); j++) {

        vector _q = new vector(q_i);

        vector _v = new vector(vs.get(j).get(i));//v_j_i

        _q.multiplyScalar(_q.innerProduct(_v));

        _v.subtractVector(_q);

        vs.get(j).add(_v);
      }

      qs.add(q_i);
      rs.add(
        q_i.transpose().multiply(A).transpose().getVector(0)
      );
    }

    v = new vector(vs.get(A.getN() - 1).get(A.getN() - 1));
    q_i = new vector(v);
    q_i.multiplyScalar(1 / v.norm());

    qs.add(q_i);
    rs.add(
        q_i.transpose().multiply(A).transpose().getVector(0)
    );

    Q.setVectors(qs);
    R.setVectors(rs);
    R = R.transpose();
  }

  public matrix solve(vector b) {
    assert(A.getM() == b.getDim());

    cgs();
    matrix _Q = Q.transpose();

    matrix B = new matrix(b.getDim(), 1);

    List<vector> bs = new ArrayList<>();
    bs.add(b);

    B.setVectors(bs);

    matrix _QB = _Q.multiply(B);

    vector rhs = _QB.getVector(0);

    //_Rx = _QB


    List<Double> x = new ArrayList<>();

    Double c = rhs.getValue(rhs.getDim() - 1);

    x.add(c / R.getVector(R.getN() - 1).getValue(R.getM() - 1));

    for (int i = A.getN() - 1; i > 0; i--) {

      c = rhs.getValue(i - 1);

      c -= sum(i, A.getN(), R.transpose(), x);

      c /= R.getVector(i - 1).getValue(i - 1);

      x.add(c);
    }

    vector _x = new vector(A.getN());

    _x.setValues(reverse(x));

    List<vector> xs = new ArrayList<>();

    xs.add(_x);

    return new matrix(A.getN(), 1, xs);
  }


  private vector sum(vector a, List<vector> qs) {
    vector res = new vector(a.getDim());

    for (vector q1 : qs) {
      vector q = new vector(q1);

      q.multiplyScalar(q.innerProduct(a));

      res.addVector(q);
    }
    return res;
  }

  private double sum(int i, int n, matrix R, List<Double> x) {
    double acc = 0;

    for(int j = i; j < n; j++) {
      double r = R.getVector(i - 1).getValue(j);
      double _x = x.get(n - j - 1);
      acc += r * _x;
    }

    return acc;
  }

  private List<Double> reverse(List<Double> x){
    List<Double> _x = new ArrayList<>();

    for(int i = x.size(); i > 0; i--) {
      _x.add(x.get(i - 1));
    }

    return _x;
  }
}
