import javax.print.DocFlavor;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Stack;

public class SuffixTree {

    private TreeNode root;
    public char TERMINAL_CHAR = '$';
    private int numberOfNodes;

    public SuffixTree() {
        this.root = new TreeNode("");
        numberOfNodes = 1;
    }

    public void createTree(String s, boolean debug) {
        root.addChild(new TreeNode(s + TERMINAL_CHAR));
        for (int i = 1; i < s.length(); i++) {
            String suffix = s.substring(i);
            if (debug)
                System.out.println("Looking for the suffix: " + suffix);
            int[] match = root.stringAmongChildren(suffix, debug);
            if (match[0] == -1) { // No match; create a new child node with the current suffix as the value
                if (debug)
                    System.out.println("No match found. Creating a new child node: " + suffix + TERMINAL_CHAR);
                root.addChild(new TreeNode(suffix + TERMINAL_CHAR));
                numberOfNodes++;
            } else {
                if (debug) {
                    System.out.println("-------------");
                    System.out.println("Found a match. Splitting the node [" + root.getChildAtIndex(match[0]) +
                            "] at: " + root.getChildAtIndex(match[0]).getCharAtIndex(match[1]));
                }
                splitNode(root.getChildAtIndex(match[0]), match[1], suffix + TERMINAL_CHAR, debug);
                numberOfNodes += 2;
            }
        }
        /*if(debug)
            printTree();*/
    }

    public void splitNode(TreeNode t, int index, String s, boolean debug) {
        char[] beforeChar = new char[index]; // New parent node
        char[] afterChar = new char[s.length() - index]; // New right child node
        char[] remainder = new char[t.getValueLength() - index]; // Old parent node, left child node
        for (int i = 0; i < t.getValueLength(); i++) {
            if(i < index)
                beforeChar[i] = t.getCharAtIndex(i);
            else
                remainder[i - index] = t.getCharAtIndex(i);
        }
        for (int j = 0; j < afterChar.length; j++) {
            if (j == afterChar.length - 1)
                afterChar[j] = '$';
            else
                afterChar[j] = s.charAt(j + index);
        }
        TreeNode newParentNode = new TreeNode(beforeChar);
        TreeNode newChildNode = new TreeNode(afterChar);
        t.updateValue(remainder);
        newParentNode.addChild(t);
        newParentNode.addChild(newChildNode);
        if (debug) {
            System.out.println("New parent node: " + newParentNode);
            System.out.println("New Left node: " + t);
            System.out.println("New right node: " + newChildNode);
            System.out.println("-------------");
        }
    }

    public void printTree() {
        Stack<TreeNode> stack = new Stack<>();
        stack.add(root);
        int nodes = 1;
        while (nodes <= numberOfNodes) {
            if(stack.isEmpty())
                break;
            TreeNode node = stack.pop();
            for (int i = 0; i < node.numOfChildren(); i++) {
                stack.add(node.getChildAtIndex(i));
            }
            System.out.println(node);
            if (node.numOfChildren() > 0)
                System.out.println("Node " + Integer.toString(nodes) + " has children: ");
            nodes++;
        }
    }

    public static void main(String[] args) {
        SuffixTree st = new SuffixTree();
        st.createTree("gctgca", true);
    }

}
