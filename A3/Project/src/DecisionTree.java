//260832483 Yuyao Zhang

import java.io.Serializable;
import java.util.ArrayList;
import java.text.*;
import java.lang.Math;

public class DecisionTree implements Serializable {

    DTNode rootDTNode;
    int minSizeDatalist; //minimum number of datapoints that should be present in the dataset so as to initiate a split
    //Mention the serialVersionUID explicitly in order to avoid getting errors while deserializing.
    public static final long serialVersionUID = 343L;

    public DecisionTree(ArrayList<Datum> datalist, int min) {
        minSizeDatalist = min;
        rootDTNode = (new DTNode()).fillDTNode(datalist);
    }

    class DTNode implements Serializable {
        //Mention the serialVersionUID explicitly in order to avoid getting errors while deserializing.
        public static final long serialVersionUID = 438L;
        boolean leaf;
        int label = -1;      // only defined if node is a leaf
        int attribute; // only defined if node is not a leaf
        double threshold;  // only defined if node is not a leaf


        DTNode left, right; //the left and right child of a particular node. (null if leaf)

        DTNode() {
            leaf = true;
            threshold = Double.MAX_VALUE;
        }


        // this method takes in a datalist (ArrayList of type datum) and a minSizeInClassification (int) and returns
        // the calling DTNode object as the root of a decision tree trained using the datapoints present in the
        // datalist variable
        // Also, KEEP IN MIND that the left and right child of the node correspond to "less than" and "greater than or equal to" threshold
        DTNode fillDTNode(ArrayList<Datum> datalist) {

            //YOUR CODE HERE

            if (datalist.size() >= minSizeDatalist) {
                boolean allSame = true;
                for (int i = 0; i < datalist.size() - 1; i++)
                    if (datalist.get(i).y != datalist.get(i + 1).y)
                        allSame = false;
                if (allSame) {
                    DTNode n = new DTNode();
                    n.label = datalist.get(0).y;
                    return n;
                } else {

                    double best_avg_entropy = Double.POSITIVE_INFINITY;
                    int best_attr = -1;
                    double best_threshold = -1;
                    for (int i = 0; i < 2; i++)
                        for (int j = 0; j < datalist.size(); j++) {

                            double tempThreshold = datalist.get(j).x[i];
                            ArrayList<Datum> a1 = new ArrayList<>();
                            ArrayList<Datum> a2 = new ArrayList<>();

                            for (int k = 0; k < datalist.size(); k++)
                                if (datalist.get(k).x[i] < tempThreshold)
                                    a1.add(datalist.get(k));
                                else a2.add(datalist.get(k));

                            double w1 = ((double) a1.size()) / ((double) datalist.size());
                            double w2 = ((double) a2.size()) / ((double) datalist.size());
                            double entropy1 = w1 * calcEntropy(a1);
                            double entropy2 = w2 * calcEntropy(a2);
                            double current_avg_entropy = entropy1 + entropy2;
                            if (best_avg_entropy > current_avg_entropy) {
                                best_avg_entropy = current_avg_entropy;
                                best_attr = i;
                                best_threshold = datalist.get(j).x[i];
                            }
                        }

                    DTNode newNode = new DTNode();
                    newNode.leaf = false;
                    newNode.threshold = best_threshold;
                    newNode.attribute = best_attr;
                    ArrayList<Datum> arr1 = new ArrayList<>();
                    ArrayList<Datum> arr2 = new ArrayList<>();
                    for (int i = 0; i < datalist.size(); i++)
                        if (datalist.get(i).x[best_attr] < best_threshold)
                            arr1.add(datalist.get(i));
                        else arr2.add(datalist.get(i));
                    newNode.left = fillDTNode(arr1);
                    newNode.right = fillDTNode(arr2);
                    return newNode;
                }
            } else {
                DTNode newNode = new DTNode();
                newNode.label = findMajority(datalist);
                return newNode;
            }

        }


        //This is a helper method. Given a datalist, this method returns the label that has the most
        // occurences. In case of a tie it returns the label with the smallest value (numerically) involved in the tie.

        int findMajority(ArrayList<Datum> datalist) {
            int l = datalist.get(0).x.length;
            int[] votes = new int[l];

            //loop through the data and count the occurrences of datapoints of each label
            for (Datum data : datalist) {
                votes[data.y] += 1;
            }
            int max = -1;
            int max_index = -1;
            //find the label with the max occurrences
            for (int i = 0; i < l; i++) {
                if (max < votes[i]) {
                    max = votes[i];
                    max_index = i;
                }
            }
            return max_index;
        }


        // This method takes in a datapoint (excluding the label) in the form of an array of type double (Datum.x) and
        // returns its corresponding label, as determined by the decision tree
        int classifyAtNode(double[] xQuery) {
            //YOUR CODE HERE

            if (leaf)
                return label;
            else {
                if (xQuery[attribute] < threshold)
                    return left.classifyAtNode(xQuery);
                else return right.classifyAtNode(xQuery);
            }

        }


        //given another DTNode object, this method checks if the tree rooted at the calling DTNode is equal to the tree rooted
        //at DTNode object passed as the parameter
        public boolean equals(Object dt2) {

            //YOUR CODE HERE
            if (dt2 == null && this == null) return true;
            if (dt2 == null || this == null) return false;
            if (leaf && ((DTNode) dt2).leaf) //if both leaves
                if (label != ((DTNode) dt2).label)
                    return false;
                else return true;
            else if (!leaf && !((DTNode) dt2).leaf)//if neither leaves
                if (attribute != ((DTNode) dt2).attribute || threshold != ((DTNode) dt2).threshold)
                    return false;
                else return left.equals(((DTNode) dt2).left) && right.equals(((DTNode) dt2).right);
            else return false;//else either is leaf, meaning tree structure is different, which returns false

        }
    }


    //Given a dataset, this retuns the entropy of the dataset
    double calcEntropy(ArrayList<Datum> datalist) {
        double entropy = 0;
        double px = 0;
        float[] counter = new float[2];
        if (datalist.size() == 0)
            return 0;
        double num0 = 0.00000001, num1 = 0.000000001;

        //calculates the number of points belonging to each of the labels
        for (Datum d : datalist) {
            counter[d.y] += 1;
        }
        //calculates the entropy using the formula specified in the document
        for (int i = 0; i < counter.length; i++) {
            if (counter[i] > 0) {
                px = counter[i] / datalist.size();
                entropy -= (px * Math.log(px) / Math.log(2));
            }
        }

        return entropy;
    }


    // given a datapoint (without the label) calls the DTNode.classifyAtNode() on the rootnode of the calling DecisionTree object
    int classify(double[] xQuery) {
        DTNode node = this.rootDTNode;
        return node.classifyAtNode(xQuery);
    }

    // Checks the performance of a DecisionTree on a dataset
    //  This method is provided in case you would like to compare your
    //results with the reference values provided in the PDF in the Data
    //section of the PDF

    String checkPerformance(ArrayList<Datum> datalist) {
        DecimalFormat df = new DecimalFormat("0.000");
        float total = datalist.size();
        float count = 0;

        for (int s = 0; s < datalist.size(); s++) {
            double[] x = datalist.get(s).x;
            int result = datalist.get(s).y;
            if (classify(x) != result) {
                count = count + 1;
            }
        }

        return df.format((count / total));
    }


    //Given two DecisionTree objects, this method checks if both the trees are equal by
    //calling onto the DTNode.equals() method
    public static boolean equals(DecisionTree dt1, DecisionTree dt2) {
        boolean flag = true;
        flag = dt1.rootDTNode.equals(dt2.rootDTNode);
        return flag;
    }

}
