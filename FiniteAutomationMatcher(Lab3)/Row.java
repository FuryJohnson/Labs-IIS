class Row {
    private int q;
    private int k;
    private String a;

    Row(int q, String a, int k) {
        this.q = q;
        this.a = a;
        this.k = k;
    }

    String getA() {
        return a;
    }

    int getQ() {
        return q;
    }

    int getK() {
        return k;
    }
}