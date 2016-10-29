import javax.print.DocFlavor;
import java.util.ArrayList;


public class TreeNode {

    private char[] value;
    private ArrayList<TreeNode> children;

    public TreeNode(String s) {
        this(s.toCharArray());
    }

    public TreeNode(char[] c) {
        this.value = c;
        children = new ArrayList<>();
    }

    public void addChild(TreeNode c) {
        if (!children.contains(c))
            children.add(c);
    }

    public int[] stringAmongChildren(String x, boolean debug) {
        char[] xArray = x.toCharArray();
        int[] result = new int[]{0, 1};
        for (TreeNode t : children) {
            if (debug)
                System.out.println("Comparing '" + String.valueOf(t.value) + "' with '" + x + "'");
            if (t.value[0] == xArray[0]) {
                for (int i = 1; i < xArray.length; i++) {
                    if (t.value[i] != xArray[i]) {
                        if (debug)
                            System.out.println("Match ended at: " + t.value[i] + " - Suffix char: " + xArray[i]);
                        break;
                    }
                    result[1]++;
                }
                return result;
            }
            result[0]++;
        }
        return new int[]{-1, -1};
    }

    public void updateValue(char[] newValue) {
        value = newValue;
    }

    public char getCharAtIndex(int i) {
        return value[i];
    }

    public TreeNode getChildAtIndex(int i) {
        return children.get(i);
    }

    public int getValueLength() {
        return value.length;
    }

    public int numOfChildren() {
        return children.size();
    }

    public String toString() {
        return String.valueOf(value);
    }

}
