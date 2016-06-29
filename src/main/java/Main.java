/**
 * Created by Azathoth on 29. 6. 2016.
 */
public class Main {

    public static void main(String[] args) {
        Optimizer optimizer = new NaiveOptimizer();
        Spell spell = optimizer.optimize(2, 30, 30);
        System.out.println(spell.toString());
    }

}
