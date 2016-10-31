import java.util.Stack;

public class SuffixTree {

    private TreeNode root;
    public static char TERMINAL_CHAR = '$';
    private int numberOfNodes;

    public SuffixTree() {
        this.root = new TreeNode("");
        numberOfNodes = 1;
    }

    public void createTree(String s, boolean debug) {
        root.addChild(new TreeNode(s + TERMINAL_CHAR));
        for (int i = 1; i < s.length(); i++) {
            if (debug)
                root.printChildren();
            String suffix = s.substring(i);
            if (debug)
                System.out.println("Looking for the suffix: " + suffix);
            int[] match = {0, 0, 0, 0};
            stringAmongChildren(root, suffix, debug);
            if (match[0] == -1) { // No match; create a new child node with the current suffix as the value
                /*if (debug)
                    System.out.println("No match found. Creating a new child node: " + suffix + TERMINAL_CHAR);
                root.addChild(new TreeNode(suffix + TERMINAL_CHAR));
                numberOfNodes++;*/
            } else {
                if (match[2] > -1) {
                    /*if (debug) {
                        System.out.println("-------------");
                        System.out.println("Found a match. Splitting the node [" + root.getChildAtIndex(match[0]).getChildAtIndex(match[2]) +
                                "] at: " + root.getChildAtIndex(match[0]).getChildAtIndex(match[2]).getCharAtIndex(match[3]));
                    }
                    splitNode(root.getChildAtIndex(match[0]), root.getChildAtIndex(match[0]).getChildAtIndex(match[2]), match[3], suffix + TERMINAL_CHAR, debug);*/
                } else {
                    if (match[2] == -2) { // No matching children found recursively; create a new child node with the substring of suffix
                        /*if (debug)
                            System.out.println("No match found. Creating a new child node: " + suffix.substring(match[1]) + TERMINAL_CHAR);
                        root.getChildAtIndex(match[0]).addChild(new TreeNode(suffix.substring(match[1]) + TERMINAL_CHAR));
                        numberOfNodes++;*/
                    } else {
                        /*if (debug) {
                            System.out.println("-------------");
                            System.out.println("Found a match. Splitting the node [" + root.getChildAtIndex(match[0]) +
                                    "] at: " + root.getChildAtIndex(match[0]).getCharAtIndex(match[1]));
                        }
                        splitNode(root, root.getChildAtIndex(match[0]), match[1], suffix + TERMINAL_CHAR, debug);*/
                    }
                }
            }
        }
        /*if(debug)
            printTree();*/
    }

    public void stringAmongChildren(TreeNode node, String x, boolean debug) {
        char[] xArray = x.toCharArray();
        TreeNode match = null;
        int matchEnd = 0;
        if (x.length() > 0) {
            for (TreeNode child : node.getChildren()) {
                if (match != null)
                    break;
                if (debug)
                    System.out.println("Comparing '" + String.valueOf(child.getValue()) + "' with '" + x + "'");
                if (child.getCharAtIndex(0) == xArray[0]) {

                    match = child;
                    for (int i = 0; i < xArray.length; i++) {
                        if (i == child.getValueLength()) {
                            break;
                        }
                        if (child.getCharAtIndex(i) == TERMINAL_CHAR) { // We have reached the end of a suffix, and the string must be identical
                            // TODO: Nothing
                            break;
                        }
                        if (child.getCharAtIndex(i) != xArray[i]) {
                            if (debug)
                                System.out.println("Match ended at: " + child.getCharAtIndex(i) + " - Suffix char: " + xArray[i]);
                            break;
                        }
                        matchEnd++;
                    }
                    if (matchEnd == match.getValueLength() && xArray.length > matchEnd) { // There are still more characters to check. Recursively call on children
                        if (debug) {
                            System.out.println("The entire node matches: " + String.valueOf(child.getValue()) + " == " + String.valueOf(xArray));
                            System.out.println("##################");
                            System.out.println("Looking through the children of '" + child + "'");
                        }
                        stringAmongChildren(match, x.substring(matchEnd), debug);
                        return;
                    }
                }
            }
        }
        if (match == null) { // No match; create a new child node with the current suffix as the value
            if (debug)
                System.out.println("No match found. Creating a new child node: " + x + TERMINAL_CHAR);
            node.addChild(new TreeNode(x + TERMINAL_CHAR));
            numberOfNodes++;
        } else { // Found a match that ended on a child; split the given child node after the match
            if (match.getValueLength() == 1) {
                if(debug)
                    System.out.println("Reached the last suffix. Creating new child node with character: " + TERMINAL_CHAR);
                match.addChild(new TreeNode(new char[]{TERMINAL_CHAR}));
                numberOfNodes++;
            } else {
                if (debug) {
                    System.out.println("Split at: " + matchEnd);
                    System.out.println(match);
                    System.out.println("-------------");
                    System.out.println("Found a match. Splitting the node [" + match +
                            "] at: " + match.getCharAtIndex(matchEnd));
                }
                splitNode(node, match, matchEnd, x + TERMINAL_CHAR, debug);
                numberOfNodes += 2;
            }
        }
    }

    public void splitNode(TreeNode parent, TreeNode t, int index, String s, boolean debug) {
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
        parent.removeChild(t);
        newParentNode.addChild(t);
        newParentNode.addChild(newChildNode);
        parent.addChild(newParentNode);
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
        st.createTree("TGGAATTCTCGGGTGCCAAGGAACTCCAGTCACACAGTGATCTCGTATGCCGTCTTCTGCTTG", true);
        //st.createTree("gctgca", true);
        //st.createTree("TGGAATTCTCGGGT", true);
    }

}
