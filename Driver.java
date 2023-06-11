import java.io.File;
import java.io.IOException;

public class Driver {
    public static void main(String[] args) {
        // Test Case 1: Evaluating polynomial p at x = 3
        Polynomial p = new Polynomial();
        System.out.println("p(3) = " + p.evaluate(3));

        // Test Case 2: Adding two polynomials p1 and p2
        double[] c1 = {6, 0, 0, 5};
        Polynomial p1 = new Polynomial(c1, new int[]{0, 1, 2, 3});
        double[] c2 = {0, -2, 0, 0, -9};
        Polynomial p2 = new Polynomial(c2, new int[]{0, 1, 2, 3, 4});
        Polynomial s = p1.add(p2);
        System.out.println("s(0.1) = " + s.evaluate(0.1));

        // Test Case 3: Checking if the polynomial s has a root at x = 1
        if (s.hasRoot(1)) {
            System.out.println("1 is a root of s");
        } else {
            System.out.println("1 is not a root of s");
        }

        // Test Case 4: Multiplying polynomials p1 and p2
        Polynomial product = p1.Multiply(p2);
        System.out.println("Product: " + product.get_P_string());

        // Test Case 5: Saving the polynomial p1 to a file
        try {
            p1.saveToFile("polynomial.txt");
            System.out.println("Polynomial saved to file successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the polynomial to a file.");
            e.printStackTrace();
        }

        // Test Case 6: Parsing a polynomial from a file and evaluating it
        try {
            Polynomial parsedPolynomial = new Polynomial(new File("polynomial.txt"));
            System.out.println("Parsed polynomial: " + parsedPolynomial.get_P_string());
            System.out.println("Parsed polynomial evaluated at x = 2: " + parsedPolynomial.evaluate(2));
        } catch (IOException e) {
            System.out.println("An error occurred while parsing the polynomial from the file.");
            e.printStackTrace();
        }
    }
}
