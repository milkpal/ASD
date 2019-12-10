import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        try {
            Tree tree = new Tree(args[0]);
            Stack s = new Stack(tree.nodecount);
            postOrder(tree.root,s);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static void postOrder(Node root, Stack stack){
        String past = "A";
        String current = "";

        stack.push(root);

        while(!stack.isEmpty()){
            Node curr = stack.pop();
            if(curr.isLeaf()){
                current=curr.getWord();
                if(current.compareTo(past)>0)
                    past=current;
            }

            if(curr.right!=null){
                stack.push(curr.right);
            }

            if(curr.left!=null){
                stack.push(curr.left);
            }
        }

        System.out.println(past);
    }


}

class Tree{
    int leafcount=0;
    int nodecount;
    Node root=new Node(".");

    Tree(String fname) throws IOException {
        Scanner s = new Scanner(new File(fname));
        String toHandle;
        while(s.hasNextLine()){
            toHandle=s.nextLine();
            if(toHandle.length()==1){
                root.letter=toHandle.substring(0,1);
                nodecount++;
            }else{
                addNode(toHandle.split(" ")[1],toHandle.substring(0,1),root);
                nodecount++;
            }
        }
    }

    void addNode(String path,String letter,Node prev){
        if(path.length()==1){
            if(path.charAt(0)=='L') {
                if(prev.left==null)
                    prev.left=new Node(letter);
                else
                    prev.left.letter=letter;
                prev.left.parent=prev;
            }
            else if(path.charAt(0)=='R'){
                if(prev.right==null)
                    prev.right=new Node(letter);
                else
                    prev.right.letter=letter;
                prev.right.parent=prev;
            }

        }else {
            String newpath = path.substring(1);
            if (path.charAt(0) == 'L') {
                if (prev.left == null){
                    prev.left = new Node("");
                    prev.left.parent=prev;
                }
                addNode(newpath, letter, prev.left);
            } else  if(path.charAt(0)=='R'){
                if (prev.right == null) {
                    prev.right = new Node("");
                    prev.right.parent=prev;
                }
                addNode(newpath, letter, prev.right);
            }
        }

        prev.leaf=false;
    }

}

class Node{
    Node parent=null;
    Node left=null;
    Node right=null;
    String letter;
    boolean leaf=true;

    Node(String letter){
        this.letter=letter;
    }

    String getWord(){
        if(parent==null) return letter;
        return letter+parent.getWord();
    }

    boolean isLeaf(){
        return (left==null&&right==null);
    }

}

class Stack{
    int top;
    Node a[];

    Stack(int capacity){
        a=new Node[capacity];
        top=-1;
    }

    boolean isEmpty(){
        return top<0;
    }

    void push(Node node){
        a[++top]=node;
    }

    Node pop(){
        return a[top--];
    }


}
