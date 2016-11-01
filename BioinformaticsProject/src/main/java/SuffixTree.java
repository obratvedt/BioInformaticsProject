import java.util.ArrayList;
import java.util.Locale;
import java.util.Stack;

public class SuffixTree {

    private TreeNode root;
    public static char TERMINAL_CHAR = '$';
    private int numberOfNodes;
    private int numberOfTerminals;

    public SuffixTree() {
        this.root = new TreeNode("");
        numberOfNodes = 1;
        numberOfTerminals = 0;
    }

    public void createTreeFromSequence(String fileName, boolean debug) {
        System.out.println("--- Initializing Generalized Suffix Tree Creation ---");
        ArrayList<String> sequences = FileParser.getSequences(fileName);
        System.out.println("--- Successfully Fetched Sequences From File ---");
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            for (int j = 0; j < 50; j++) {
                String suffix = sequences.get(i).substring(j) + TERMINAL_CHAR;
                if (debug) {
                    root.printChildren();
                    System.out.println("Looking for the suffix: " + suffix);
                }
                stringAmongChildren(root, suffix, j, i, debug);
            }
            //System.out.println("Sequence: " + i);
        }
        if (debug)
            printTree();
        System.out.println("### GENERALIZED SUFFIX TREE SUCCESSFULLY CREATED ###");
        System.out.println("Tree generation time: " + Long.toString(System.currentTimeMillis() - startTime) + " ms");
        System.out.println("Number of nodes in the tree: " + numberOfNodes);
        System.out.println("Number of Terminal nodes in the tree: " + numberOfTerminals);
    }

    public void createTreeTest(String s, boolean debug) {
        for (int i = 0; i < s.length(); i++) {
            String suffix = s.substring(i) + TERMINAL_CHAR;
            if (debug) {
                root.printChildren();
                System.out.println("Looking for the suffix: " + suffix);
            }
            stringAmongChildren(root, suffix, i, 0, debug);
        }
        if (debug)
            printTree();
        System.out.println("Number of nodes in the tree: " + numberOfNodes);
    }

    public void stringAmongChildren(TreeNode node, String suffix, int start, int seqNum, boolean debug) {
        if (numberOfNodes == 1) {
            TreeNode child = new TreeNode(suffix);
            root.addChild(child);
            numberOfNodes++;
            numberOfTerminals++;
            return;
        }
        char[] xArray = suffix.toCharArray();
        TreeNode match = null;
        int matchEnd = 0;
        if (suffix.length() > 0) {
            for (TreeNode child : node.getChildren()) {
                if (match != null)
                    break;
                if (debug)
                    System.out.println("Comparing '" + String.valueOf(child.getValue()) + "' with '" + suffix + "'");
                if (child.getCharAtIndex(0) == xArray[0]) { // Checking if first character of the child node and suffix match
                    match = child;
                    for (int i = 0; i < xArray.length; i++) {
                        if (i == child.getValueLength()) {
                            break;
                        }
                        if (child.getCharAtIndex(i) != xArray[i]) {
                            if (debug)
                                System.out.println("Match ended at: " + child.getCharAtIndex(i) + " - Suffix char: " + xArray[i]);
                            break;
                        }
                        if (child.hasOccurrences()) {
                            if (child.getCharAtIndex(i) == TERMINAL_CHAR && i == xArray.length - 1) { // We have reached the end of a suffix, and the string must be identical
                                match.addOccurrence(seqNum, start);
                                if (debug)
                                    System.out.println("We have found a complete match on a terminal node [" + child + "]; Occurrence added");
                                return;
                            }
                        }
                        matchEnd++;
                    }
                    if (matchEnd == match.getValueLength() && xArray.length > matchEnd) { // There are still more characters to check. Recursively call on children
                        if (debug) {
                            System.out.println("The entire node matches: " + String.valueOf(child.getValue()) + " == " + String.valueOf(xArray));
                            System.out.println("##################");
                            System.out.println("Looking through the children of '" + child + "'");
                        }
                        stringAmongChildren(match, suffix.substring(matchEnd), start, seqNum, debug);
                        return;
                    }
                }
            }
        }
        if (match == null) { // No match; create a new child node with the current suffix as the value
            if (debug)
                System.out.println("No match found. Creating a new child node: " + suffix);
            TreeNode newNode = new TreeNode(suffix);
            newNode.addOccurrence(seqNum, start);
            node.addChild(newNode);
            numberOfNodes++;
            numberOfTerminals++;
        } else { // Found a match that ended on a child; split the given child node after the match
            if (debug) {
                System.out.println("-------------");
                System.out.println("Found a match. Splitting the node [" + match +
                        "] at: " + match.getCharAtIndex(matchEnd));
            }
            splitNode(node, match, matchEnd, suffix, seqNum, start, debug);
            numberOfNodes += 2;
            numberOfTerminals++;
        }
    }

    public void splitNode(TreeNode parent, TreeNode t, int index, String s, int seqNum, int start, boolean debug) {
        char[] beforeChar = new char[index]; // New parent node
        char[] afterChar = new char[s.length() - index]; // New right child node
        char[] remainder = new char[t.getValueLength() - index]; // Old parent node, left child node
        for (int i = 0; i < t.getValueLength(); i++) {
            if (i < index)
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
        newChildNode.addOccurrence(seqNum, start);
        t.updateValue(remainder);
        parent.removeChild(t);
        newParentNode.addChild(t);
        newParentNode.addChild(newChildNode);
        parent.addChild(newParentNode);
        if (debug) {
            System.out.println("New parent node: " + newParentNode);
            System.out.println("New Left node: " + t);
            System.out.println("New right node: " + newChildNode);
            System.out.println(newParentNode.hasOccurrences());
            System.out.println(t.hasOccurrences());
            System.out.println(newChildNode.hasOccurrences());
            System.out.println("-------------");
        }
    }

    public int[][] searchTree(TreeNode node, String pattern, boolean debug) {
        char[] patternArray = pattern.toCharArray();
        for (TreeNode c : node.getChildren()) {
            if (c.getCharAtIndex(0) == patternArray[0]) {
                if (patternArray.length == 1) {
                    if (debug) {
                        System.out.println("Found a complete match on node " + c.toString());
                        c.printOccurrences();
                    }
                    return c.getOccurrences();
                }
                int endI = 0;
                if (c.getValueLength() > 1) {
                    for (int i = 0; i < c.getValueLength(); i++) {
                        if (i == patternArray.length) {
                            if(debug)
                                System.out.println("No match found! Pattern: " + pattern + " != " + c);
                            return null;
                        }
                        if (c.getCharAtIndex(i) != patternArray[i]) {
                            if(debug)
                                System.out.println("No match found! Pattern: " + pattern + " != " + c);
                            return null;
                        }
                        if (i == patternArray.length - 1) {
                            if (debug) {
                                System.out.println("Found a complete match on node " + c.toString());
                                c.printOccurrences();
                            }
                            return c.getOccurrences();
                        }
                        endI++;
                    }
                    return searchTree(c, pattern.substring(endI), debug);
                } else {
                    return searchTree(c, pattern.substring(1), debug);
                }
            }
        }
        return null;
    }

    public void searchTreeForPattern(String pattern, boolean debug) {
        System.out.println("--- Initiated Search for '" + pattern + "' ---");
        long startTime = System.currentTimeMillis();
        for (int i = 1; i < pattern.length(); i++) {
            if (debug)
                System.out.println("Searching the tree for pattern: " + pattern.substring(0, i));
            searchTree(root, pattern.substring(0, i), debug);
        }
        System.out.println("### SEARCH FINISHED! ##");
        System.out.println("Search time: " + Long.toString(System.currentTimeMillis() - startTime) + " ms");
    }

    public void printTree() {
        Stack<TreeNode> stack = new Stack<>();
        stack.add(root);
        int nodes = 1;
        while (nodes <= numberOfNodes) {
            if (stack.isEmpty())
                break;
            TreeNode node = stack.pop();
            for (int i = 0; i < node.numOfChildren(); i++) {
                stack.add(node.getChildAtIndex(i));
            }
            System.out.println(node);
            if (node.numOfChildren() > 0)
                System.out.println("Node " + Integer.toString(nodes) + " has children: ");
            if (node.hasOccurrences())
                System.out.println("Node has occurrence: " + node.printOccurrences());
            nodes++;
        }
    }

    public static void main(String[] args) {
        SuffixTree st = new SuffixTree();
        //st.createTree("TGGAATTCTCGGGTGCCAAGGAACTCCAGTCACACAGTGATCTCGTATGCCGTCTTCTGCTTG", true);
        //st.createTree("gctgca", true);
        //st.createTree("TGGAATTCTCGGGT", true);
        st.createTreeFromSequence("s_3_sequence_1M.txt", false);
        //st.createTreeTest("TGGAATTCTCGGGT", true);
        st.searchTreeForPattern("TGGAATTCTCGGGTGCCAAGGAACTCCAGTCACACAGTGATCTCGTATGCCGTCTTCTGCTTG", true);
    }

}
