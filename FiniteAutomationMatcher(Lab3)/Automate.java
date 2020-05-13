import java.util.ArrayList;

class Automate {

    private String P;
    private String[] Σ;
    private ArrayList<Row> δ;

    Automate(String pattern, String[] alphabet) {
        this.P = pattern;
        this.Σ = alphabet;
        ComputeTransitionFunction();
    }

    private void ComputeTransitionFunction() {
        δ = new ArrayList<>();
        int m = P.length();
        for (int q = 0; q <= m; q++) {
            for (String a : Σ) {
                int k = Integer.min(m, q + 2);
                while (!(P.substring(0, q) + a).endsWith(P.substring(0, k))) {
                    k = k - 1;
                }
                δ.add(new Row(q, a, k));
            }
        }
    }

    void print() {
        for (Row item : δ) {
            System.out.println("k: " + item.getK() + " q: " + item.getQ() + " char: " + item.getA());
        }
    }

    ArrayList<Row> getΔ() {
        return δ;
    }

    int getPatternLen() {
        return P.length();
    }
}