import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
//importing packages used in file operations

public class Polynomial{
    private double[] co;
    private int[] exponents;
    //defining two private instance variables: one for non-zero coefficients and one for according to lab2 pdf file

    public Polynomial(){
        co = new double[1];
        exponents = new int[1];
        co[0] = 0.0;
        exponents[0] = 0;
    }
    //a no-argument constructor that sets the polynomial to zero (i.e. the corresponding coefficient array would be
    // [0.0] and the exponents array would be [0])

    public Polynomial(double[] coefficient, int[] expo){
        co = coefficient;
        exponents = expo;
    }
    //a constructor that takes two arrays of double as arguments and sets the coefficients and exponents accordingly

    public Polynomial add(Polynomial another){
        int coefficient_length = 0;
        if(co.length <= another.co.length){
            coefficient_length = another.co.length;
        }else{
            coefficient_length = co.length;
        }
        //taking coefficient_length as the value of the longest co array length

        double[] resultCo = new double[coefficient_length];
        int[] resultExpo = new int[coefficient_length];

        for (int i = 0; i < co.length; i++) {
            resultCo[i] += co[i];
            resultExpo[i] = exponents[i];
        }

        for (int i = 0; i < another.co.length; i++) {
            resultCo[i] += another.co[i];
            resultExpo[i] = another.exponents[i];
        }
        //adding them up

        return combine_terms(resultCo, resultExpo);
        //use the helper function to integrate the actual polynomial
    }
    //this function adds up two polynomials and return a new one with its coefficient and exponent array

    public Polynomial combine_terms(double[] co, int[] expo){
        int coefficient_length = co.length;
        for(int i = 0; i < coefficient_length - 1; i++){
            for(int j = i + 1; j < coefficient_length; j++){
                if(expo[i] == expo[j]){
                    co[i] += co[j];
                    co[j] = 0.0;
                }
            }
        }
        /*use outer loop i to iterate over each term in coefficient and exponent array, then use inner loop j, which
        is one index after i to iterate over the remaining terms in two arrays. And within the inner loop, compare the
        exponent of the current term with the exponent fo the current term being processed. If the exponents are the
        same, combine the terms because they have the same exponent. And in order to remove the redundant term, set the
        coefficient of the current term to 0.0*/

        int integrated_length = 0;
        for(int t = 0; t < coefficient_length; t++){
            if(co[t] != 0.0){
                integrated_length++;
            }
        }
        //get the actual length of the result integrated polynomial

        double[] resultCo = new double[integrated_length];
        int [] resultExpo = new int[integrated_length];
        int index = 0;
        //create the coefficient and exponent arrays for the integrated polynomial

        for(int m = 0; m < coefficient_length; m++){
            if(co[m] != 0.0){
                resultCo[index] = co[m];
                resultExpo[index] = expo[m];
                index++;
            }
        }
        //put in the coefficients and exponents

        return new Polynomial(resultCo, resultExpo);
    }
    //this is the helper function to combine the terms

    public Polynomial Multiply(Polynomial another){
        int coefficient_length = co.length * another.co.length;
        double[] resultCo = new double[coefficient_length];
        int[] resultExpo = new int[coefficient_length];
        int index = 0;

        for(int i = 0; i < co.length; i++){
            for(int j = 0; j < another.co.length; j++){
                resultCo[index] = co[i] * another.co[j];
                resultExpo[index] = exponents[i] + another.exponents[j];
                index++;
            }
        }
        /*The outer loop use i to iterate over each term in the current polynomial's coefficient and exponents arrays.
        The inner loop use j to iterate over each term in the polynomial passed as argument's coefficients and exponents
        arrays. And withing the inner loop, calculate the exponent of the current term by adding the exponents of the
        two terms, calculate the coefficient of the current term by multiplying the coefficients fo the two terms.*/

        return combine_terms(resultCo, resultExpo);
        //use the helper function to integrate the actual polynomial
    }
    /*a method named multiply that takes one argument of type Polynomial and returns the polynomial resulting from
    multiplying the calling object and the argument*/

    public double evaluate(double x){
        double result = 0.0;
        double power_num = 1.0;

        for(double co : co){
            result += co * power_num;
            power_num *= x;
        }
        //iterates over the coefficient double arrya and multiply each coefficient by power of x

        return result;
    }
    /*a method named evaluate that takes one argument of type double representing a value of x and evaluates the polynom
    ial accordingly. For example, if the polynomial is 6 − 2𝑥 + 5𝑥3 and evaluate(-1) is invoked,
    the result should be 3*/

    public boolean hasRoot(double x){
        return evaluate(x) == 0.0;
    }
    /*a method named hasRoot that takes one argument of type double and determines whether this value is a root of the
    polynomial or not. Note that a root is a value of x for which the polynomial evaluates to zero.*/

    public Polynomial(File file) throws IOException{
        if(!file.exists()){
            throw new FileNotFoundException("The input File is not found: " + file.getAbsolutePath());
        }
        //if the input file is not found, it will be printed out with the absolut path of the file

        String P_string = Files.readString(Path.of(file.getAbsolutePath()));
        parse_P_string(P_string);
    }
    /*Takes file as an argument and reads the content and stores it in a string called P_string. Then it calls
        parse_P_string method to parse the polynomial string and initialize the polynomial*/

    public void saveToFile(String fileName) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName);
        if (fileWriter != null) {
            //FileWriter initialize successfully
            fileWriter.write(get_P_string());
            fileWriter.close();
        } else {
            //FileWriter fails to initialize
            System.out.println("Failed to create FileWriter object, please try again.");
        }
    }
    //this method writes in the polynomial string

    public void parse_P_string(String P_string) {
        String[] terms = P_string.split("\\s*\\+\\s*");
        //The polynomialString is split into an array of terms using the regular expression "\\s*\\+\\s*"

        double[] coefficients = new double[terms.length];
        int[] expo = new int[terms.length];
        /*Arrays coefficients and expo are initialized to store the coefficients and exponents of the polynomial
        terms, respectively, with the length of the terms array.*/

        for (int i = 0; i < terms.length; i++) {
            String term = terms[i].trim();
            String[] parts = term.split("x\\^?");
            /*Arrays coefficients and exponents are initialized to store the coefficients and exponents of the
            polynomial terms, respectively, with the length of the terms array*/

            int exponent;
            double coefficient;

            if (parts.length == 2) {
                coefficient = Double.parseDouble(parts[0]);
                exponent = Integer.parseInt(parts[1]);
            } else if (parts[0].equals("x")) {
                coefficient = 1.0;
                exponent = 1;
            } else {
                coefficient = Double.parseDouble(parts[0]);
                exponent = 0;
            }

            coefficients[i] = coefficient;
            expo[i] = exponent;
            /*Inside the loop, the current term is trimmed to remove any leading or trailing spaces. The term is split
            into two parts using the regex pattern "x\\^?". If the length of the parts array is 2, the coefficient is
            parsed from parts[0], and the exponent is parsed from parts[1]. If the length of the parts array is not 2,
            the term is either a constant or a term with only the variable "x". If parts[0] is "x", the coefficient is
            set to 1.0, and the exponent is set to 1. Otherwise, the coefficient is parsed from parts[0], and the
            exponent is set to 0. The coefficient and exponent are stored in the respective arrays coefficients and
            exponents at the current index*/
        }

        co = coefficients;
        exponents = expo;
    }

    public String get_P_string() {
        String P_string = "";

        for (int i = 0; i < co.length; i++) {
            double coefficient = co[i];
            int exponent = exponents[i];

            if (coefficient != 0) {
                String term;
                if (exponent == 0) {
                    term = String.valueOf(coefficient);
                    //if exponent is 0, the term is set as the coefficient itself
                } else if (exponent == 1) {
                    term = coefficient + "x";
                    //if exponent is 1, the term is constructed as "coefficient + x"
                } else {
                    term = coefficient + "x^" + exponent;
                    // if exponent greater than 1, the term is constructed as "coefficient + x^ + exponent"
                }

                if (!P_string.isEmpty() && coefficient > 0) {
                    P_string += " + " + term;
                    //P_string not empty then append "+term"
                } else {
                    P_string += term;
                }
            }
        }

        return P_string;
    }

}
