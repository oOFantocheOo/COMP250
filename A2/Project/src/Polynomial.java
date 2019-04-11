//Yuyao Zhang 260832483

import javax.swing.table.TableRowSorter;
import java.math.BigInteger;

public class Polynomial {
    private SLinkedList<Term> polynomial;

    public int size() {
        return polynomial.size();
    }

    private Polynomial(SLinkedList<Term> p) {
        polynomial = p;
    }

    public Polynomial() {
        polynomial = new SLinkedList<Term>();
    }

    // Returns a deep copy of the object.
    public Polynomial deepClone() {
        return new Polynomial(polynomial.deepClone());
    }

    /*
     * TODO: Add new term to the polynomial. Also ensure the polynomial is
     * in decreasing order of exponent.
     */
    public void addTerm(Term t) {
        /**** ADD CODE HERE ****/
        if (t.getCoefficient().equals(BigInteger.valueOf(0))) return;
        if (polynomial.isEmpty()) {
            polynomial.add(0, t);
            return;
        } else {
            int expT = t.getExponent();
            int idx = 0;
            for (Term cur : polynomial) {
                int expC = cur.getExponent();
                if (expC == expT) {
                    cur.setCoefficient(t.getCoefficient().add(cur.getCoefficient()));
                    if (cur.getCoefficient().equals(BigInteger.valueOf(0))) {
                        polynomial.remove(idx);
                    }
                    return;
                } else if (expT < expC) {
                    idx += 1;
                    continue;
                } else {
                    polynomial.add(idx, t);
                    return;
                }
            }
            polynomial.addLast(t);
            return;
        }

        // Hint: Notice that the function SLinkedList.get(index) method is O(n),
        // so if this method were to call the get(index)
        // method n times then the method would be O(n^2).
        // Instead, use a Java enhanced for loop to iterate through
        // the terms of an SLinkedList.
        /*
        for (Term currentTerm: polynomial)
		{
			// The for loop iterates over each term in the polynomial!!
			// Example: System.out.println(currentTerm.getExponent()) should print the exponents of each term in the polynomial when it is not empty.  
		}
		*/
    }

    public Term getTerm(int index) {
        return polynomial.get(index);
    }

    //TODO: Add two polynomial without modifying either
    public static Polynomial add(Polynomial p1, Polynomial p2) {
        /**** ADD CODE HERE ****/
        Polynomial cp = new Polynomial();
        Polynomial cpp1 = p1.deepClone();//copy of p1
        Polynomial cpp2 = p2.deepClone();
        while (cpp1.size() > 0 || cpp2.size() > 0) {
            if(cpp1.size()==0){
                Term t2 = cpp2.getTerm(0);
                cp.polynomial.addLast(t2);
                cpp2.polynomial.remove(0);
                continue;
            }
            if(cpp2.size()==0){
                Term t1 = cpp1.getTerm(0);
                cp.polynomial.addLast(t1);
                cpp1.polynomial.remove(0);
                continue;
            }
            Term t1 = cpp1.getTerm(0);
            Term t2 = cpp2.getTerm(0);
            if (t1.getExponent() > t2.getExponent()) {
                cp.polynomial.addLast(t1);
                cpp1.polynomial.remove(0);
            } else if (t1.getExponent() < t2.getExponent()) {
                cp.polynomial.addLast(t2);
                cpp2.polynomial.remove(0);
            } else {
                if ((t1.getCoefficient().add(t2.getCoefficient()).equals(BigInteger.valueOf(0)))) {
                    cpp1.polynomial.remove(0);
                    cpp2.polynomial.remove(0);
                } else {
                    cp.polynomial.addLast(new Term(t1.getExponent(), t1.getCoefficient().add(t2.getCoefficient())));
                    cpp1.polynomial.remove(0);
                    cpp2.polynomial.remove(0);
                }
            }
        }
        return cp;
    }

    //TODO: multiply this polynomial by a given term.
    private void multiplyTerm(Term t) {
        /**** ADD CODE HERE ****/
        BigInteger coe = t.getCoefficient();
        int exp = t.getExponent();
        int idx = 0;
        for (Term cur : polynomial) {
            if (cur.getCoefficient() != BigInteger.valueOf(0)) {
                cur.setCoefficient(coe.multiply(cur.getCoefficient()));
                cur.setExponent(exp + cur.getExponent());
                if (cur.getCoefficient().equals(BigInteger.valueOf(0))) {
                    polynomial.remove(idx);
                    idx -= 1;
                }
            }
            idx += 1;
        }
    }

    //TODO: multiply two polynomials
    public static Polynomial multiply(Polynomial p1, Polynomial p2) {
        /**** ADD CODE HERE ****/
        Polynomial cp = new Polynomial();
        for (Term t : p2.polynomial) {
            Polynomial cur = p1.deepClone();
            cur.multiplyTerm(t);
            cp = add(cp, cur);
        }
        return cp;
    }

    //TODO: evaluate this polynomial.
    // Hint:  The time complexity of eval() must be order O(m),
    // where m is the largest degree of the polynomial. Notice
    // that the function SLinkedList.get(index) method is O(m),
    // so if your eval() method were to call the get(index)
    // method m times then your eval method would be O(m^2).
    // Instead, use a Java enhanced for loop to iterate through
    // the terms of an SLinkedList.

    public BigInteger eval(BigInteger x) {
        /**** ADD CODE HERE ****/
        if (polynomial.isEmpty()) return BigInteger.valueOf(0);
        BigInteger arr[] = new BigInteger[1 + polynomial.get(0).getExponent()];

        for (Term t : polynomial) {
            arr[t.getExponent()] = t.getCoefficient();
        }

        BigInteger res = arr[arr.length - 1];

        for (int i = arr.length - 2; i >= 0; i--) {
            res = res.multiply(x);
            if (arr[i] != null) res = res.add(arr[i]);
    }
        return res;
    }

    // Checks if this polynomial is same as the polynomial in the argument
    public boolean checkEqual(Polynomial p) {
        if (polynomial == null || p.polynomial == null || size() != p.size())
            return false;

        int index = 0;
        for (Term term0 : polynomial) {
            Term term1 = p.getTerm(index);

            if (term0.getExponent() != term1.getExponent() ||
                    term0.getCoefficient().compareTo(term1.getCoefficient()) != 0 || term1 == term0)
                return false;

            index++;
        }
        return true;
    }

    // This method blindly adds a term to the end of LinkedList polynomial.
    // Avoid using this method in your implementation as it is only used for testing.
    public void addTermLast(Term t) {
        polynomial.addLast(t);
    }

    // This is used for testing multiplyTerm
    public void multiplyTermTest(Term t) {
        multiplyTerm(t);
    }

    @Override
    public String toString() {
        if (polynomial.size() == 0) return "0";
        return polynomial.toString();
    }
}
