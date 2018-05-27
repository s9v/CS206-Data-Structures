package elice;

public class IntegerNode {

    private Integer data;
    private IntegerNode link;

    public IntegerNode(Integer initialData, IntegerNode initialLink) {
        data = initialData;
        link = initialLink;
    }

    public void addNodeAfter(Integer element) {
        link = new IntegerNode(element, link);
    }

    public Integer getData() {
        return data;
    }

    public IntegerNode getLink() {
        return link;
    }

    public static IntegerNode listCopy(IntegerNode source) {
        IntegerNode copyHead;
        IntegerNode copyTail;

        // Handle the special case of the empty list.
        if (source == null)
            return null;

        // Make the first node for the newly created list.
        copyHead = new IntegerNode(source.data, null);
        copyTail = copyHead;

        // Make the rest of the nodes for the newly created list.
        while (source.link != null) {
            source = source.link;
            copyTail.addNodeAfter(source.data);
            copyTail = copyTail.link;
        }

        // Return the head reference for the new list.
        return copyHead;

    }
    public static Object[] listCopyWithTail(IntegerNode source) {
        IntegerNode copyHead;
        IntegerNode copyTail;
        Object[] answer = new Object[2];

        // Handle the special case of the empty list.
        if (source == null)
            return answer; // The answer has two null references .

        // Make the first node for the newly created list.
        copyHead = new IntegerNode(source.data, null);
        copyTail = copyHead;

        // Make the rest of the nodes for the newly created list.
        while (source.link != null) {
            source = source.link;
            copyTail.addNodeAfter(source.data);
            copyTail = copyTail.link;
        }

        // Return the head and tail references.
        answer[0] = copyHead;
        answer[1] = copyTail;
        return answer;
    }

    public static int listLength(IntegerNode head) {
        IntegerNode cursor;
        int answer;

        answer = 0;
        for (cursor = head; cursor != null; cursor = cursor.link)
            answer++;

        return answer;
    }

    public static Object[] listPart(IntegerNode start, IntegerNode end) {
        IntegerNode copyHead;
        IntegerNode copyTail;
        IntegerNode cursor;
        Object[] answer = new Object[2];

        // Check for illegal null at start or end.
        if (start == null)
            throw new IllegalArgumentException("start is null");
        if (end == null)
            throw new IllegalArgumentException("end is null");

        // Make the first node for the newly created list.
        copyHead = new IntegerNode(start.data, null);
        copyTail = copyHead;
        cursor = start;

        // Make the rest of the nodes for the newly created list.
        while (cursor != end) {
            cursor = cursor.link;
            if (cursor == null)
                throw new IllegalArgumentException("end node was not found on the list");
            copyTail.addNodeAfter(cursor.data);
            copyTail = copyTail.link;
        }

        // Return the head and tail references
        answer[0] = copyHead;
        answer[1] = copyTail;
        return answer;
    }

    public static IntegerNode listPosition(IntegerNode head, int position) {
        IntegerNode cursor;
        int i;

        if (position == 0)
            throw new IllegalArgumentException("position is zero");

        cursor = head;
        for (i = 1; (i < position) && (cursor != null); i++)
            cursor = cursor.link;

        return cursor;
    }

    public static IntegerNode listSearch(IntegerNode head, Integer target) {
        IntegerNode cursor;

        if (target == null) { // Search for a node in which the data is the null reference.
            for (cursor = head; cursor != null; cursor = cursor.link)
                if (cursor.data == null)
                    return cursor;
        } else { // Search for a node that contains the non-null target.
            for (cursor = head; cursor != null; cursor = cursor.link)
                if (target.equals(cursor.data))
                    return cursor;
        }

        return null;
    }

    public void removeNodeAfter() {
        link = link.link;
    }

    public void setData(Integer newData) {
        data = newData;
    }

    public void setLink(IntegerNode newLink) {
        link = newLink;
    }
}
