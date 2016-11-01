
import com.sun.org.apache.regexp.internal.RE;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;

import java.util.ArrayList;
import java.util.Arrays;


public class TreeNode {

    private char[] value;
    //private ArrayList<TreeNode> children;
    private TreeNode[] children2;
    private int numOfChildren;
    //private ArrayList<int[]> occurrences;
    private int[][] occurrences;

    public TreeNode(String s) {
        this(s.toCharArray());
    }

    public TreeNode(char[] c) {
        this.value = c;
        //children = new ArrayList<>();
        children2 = new TreeNode[6];
        numOfChildren = 0;
    }

    private <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    private void removeElement(int remIndex) {
        int numElements = children2.length - (remIndex + 1) ;
        System.arraycopy(children2, remIndex + 1, children2, remIndex, numElements) ;
    }

    private int childIndex(TreeNode t) {
        for (int i = 0; i < children2.length; i++) {
            if (children2[i].equals(t))
                return i;
        }
        return -1;
    }

    public void addChild(TreeNode c) {
        /*if (!children.contains(c))
            children.add(c);*/
        //children2 = concat(children2, new TreeNode[]{c});
        for (int i = 0; i < children2.length; i++) {
            if (children2[i] == null) {
                children2[i] = c;
                break;
            }
        }
        numOfChildren++;
    }

    public void removeChild(TreeNode c) {
        /*if(children.contains(c))
            children.remove(c);*/
        //removeElement(childIndex(c));
        children2[childIndex(c)] = null;
        numOfChildren--;
    }

    public void updateValue(char[] newValue) {
        value = newValue;
    }

    public char getCharAtIndex(int i) {
        return value[i];
    }

    public TreeNode getChildAtIndex(int i) {
        //return children.get(i);
        return children2[i];
    }

    public char[] getValue() {
        return value;
    }

    public int getValueLength() {
        return value.length;
    }

    public int numOfChildren() {
        //return children.size();
        return numOfChildren;
    }

    public String toString() {
        return String.valueOf(value);
    }

    public /*ArrayList<TreeNode>*/ TreeNode[] getChildren() {
        //return children;
        /*TreeNode[] result = new TreeNode[numOfChildren];
        System.arraycopy(children2, 0, result, 0, numOfChildren);*/
        TreeNode[] result = new TreeNode[numOfChildren];
        int i = 0;
        for (TreeNode c : children2) {
            if (c != null) {
                result[i] = c;
                i++;
            }
        }
        return result;
    }

    public void printChildren() {
        String s = "";
        for (TreeNode c : children2)
            if (c != null)
                s += c.toString() + ",";
        System.out.println(s);
    }

    public void addOccurrence(int seqNum, int startIndex) {
        if (!hasOccurrences())
            occurrences = new int[0][0];
        //occurrences.add(new int[] {seqNum, startIndex});
        //System.out.println("OCCURRENCE ADDED TO: [" + String.valueOf(value) + "]");
        concat(occurrences, new int[][]{{seqNum, startIndex}});
    }

    public void clearOccurrences() {
        occurrences = null;
    }

    public int[][] getOccurrences() {
        return occurrences;
    }

    public String printOccurrences() {
        if (!hasOccurrences())
            return "";
        String s = "";
        for (int[] o : occurrences) {
            s += "SeqNum: " + Integer.toString(o[0]) + ", StartIndex: " + o[1];
        }
        return s;
    }

    public boolean hasOccurrences() {
        return occurrences != null;
    }

}
