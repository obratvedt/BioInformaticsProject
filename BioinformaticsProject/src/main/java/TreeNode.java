import javax.print.DocFlavor;
import javax.print.attribute.TextSyntax;
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

    public void removeChild(TreeNode c) {
        if(children.contains(c))
            children.remove(c);
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

    public char[] getValue() {
        return value;
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

    public ArrayList<TreeNode> getChildren() {
        return children;
    }

    public void printChildren() {
        String s = "";
        for (TreeNode t : children)
            s += t.toString() + ",";
        System.out.println(s);
    }

}
