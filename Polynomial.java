public class Polynomial {
    public double[] co;
    /*One field representing the coefficients of the polynomial using an array of double. A polynomial is assumed to hav
    e the form 𝑎0 + 𝑎1𝑥1 + ⋯ + 𝑎𝑛−1𝑥𝑛−1. For example, the polynomial 6 − 2𝑥 + 5𝑥3 would be represented using the array
    [6, -2, 0, 5]*/

    public Polynomial(){
        double[] new_co = new double[1];
        co = new_co;
        co[0] = 0.0;
    }
    //a no-argument constructor that sets the polynomial to zero (i.e. the corresponding array would be [0])

    public Polynomial(double[] coefficient){
        co = coefficient;
    }
    //a constructor that takes an array of double as an argument and sets the coefficients accordingly


    public Polynomial add(Polynomial another){

        int coefficient_length = 0;
        if(co.length <= another.co.length){
            coefficient_length = another.co.length;
        }else{
            coefficient_length = co.length;
        }
        //taking coefficient_length as the value of the longest co array length

        double[] result_co = new double[coefficient_length];

        for(int i = 0; i < another.co.length; i++){
            result_co[i] += another.co[i];
        }
        //adding the coefficient in another polynomial to the empty result_co

        for(int t = 0; t < co.length; t++){
            result_co[t] += co[t];
        }
        //adding the coefficient of the original polynomial to another polynomial

        return new Polynomial(result_co);
    }
    /*a method named add that takes one argument of type Polynomial and returns the polynomial resulting from adding the
     calling object and the argument*/

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


}