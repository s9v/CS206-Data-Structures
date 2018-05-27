package elice;

import java.util.EmptyStackException;

public class IntegerLinkedStack {
    private IntegerNode top;
    public int id;
    public String wrongLog;

    public IntegerLinkedStack(int id) {
        top = null;
        this.id = id;
        wrongLog = "";
    }
    
    public boolean isEmpty() {
        return (top == null);
    }

    public Integer peek() {
        if (top == null)
            throw new EmptyStackException();
        return top.getData();
    }

    public Integer pop() {
        Integer answer;

        if (top == null)
            throw new EmptyStackException();
            
        answer = top.getData();
        top = top.getLink();
        return answer;
    }

    public void push(Integer item) {
        if (top != null && item >= top.getData()) {
            System.out.println("SOMETHING WRONG : " + item + ", " + this);
            wrongLog += "SOMETHING WRONG : " +  item + ", " + this + "\n";
        }

        top = new IntegerNode(item, top);
    }

    public int size() {
        return IntegerNode.listLength(top);
    }

    public String toString(){
        if(top == null)
            return "EMPTY";
            
        IntegerNode curr = top;
        String ret = "" + curr.getData();
        while(curr.getLink() != null){
            curr = curr.getLink();
            ret += ", " + curr.getData();
        }
        return ret;
    }

}