import java.util.ArrayList;

public class Main {

    static String[] arrOfAlph;

    public static void main(String[] args) {

        String alphabet = "a b ś Σ";
        String text = "ś Σ a b a b";
        String[] pattern = {"śΣabab", "ab", "a", "Σ"};
        arrOfAlph = Split(text);
        Automate automate;

        for (String p : pattern) {
            System.out.println("• pattern: " + p);
            automate = new Automate(p, Split(alphabet));
            FiniteAutomationMatcher(Split(text), automate.getΔ(), automate.getPatternLen());
            System.out.println();
        }
    }

    private static String[] Split(String text) {
        return text.split("\\s+");
    }

    private static void FiniteAutomationMatcher(String[] T, ArrayList<Row> δ, int m) {
        int n = T.length;
        int q = 0;
        for (int i = 0; i < n; i++) {
            for (Row Δ : δ) {
                if (Δ.getQ() == q && Δ.getA().equals(T[i])) {
                    q = Δ.getK();
                    break;
                }
            }
            if (q == m) {
                System.out.print("\t• index: " + (i - m + 1) + ":\t");

                for (int index = 0; index < arrOfAlph.length; index++) {
                    if (index >= (i - m + 1) && index < (i + 1))
                        System.out.print("\033[4m" + arrOfAlph[index] + "\033[0m");
                    else
                        System.out.print(arrOfAlph[index]);
                }
                System.out.println();
            }
        }
    }
}