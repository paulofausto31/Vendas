package venda.util;

public class math {

	public static double pot(double a, double b) {
        if (b < 0) {
           throw new IllegalArgumentException("negative powers not supported");
        }
        double r = 1;
        for (; b > 0; b--) {
            r *= a;
        }
        return r;
    }
	
}
