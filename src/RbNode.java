import java.util.Objects;

public class RbNode implements Comparable {




    private RbNode parent; //父节点
    private RbNode left;   //左节点
    private RbNode right;  //右节点

    private boolean colour; //颜色

    private String key;    //key值
    private Object value;  //value值

    public RbNode() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RbNode rbNode = (RbNode) o;
        return key.equals(rbNode.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

    public RbNode(boolean colour, String key, Object value) {
        this.colour = colour;
        this.key = key;
        this.value = value;
    }

    public RbNode getParent() {
        return parent;
    }

    public void setParent(RbNode parent) {
        this.parent = parent;
    }

    public RbNode getLeft() {
        return left;
    }

    public void setLeft(RbNode left) {
        this.left = left;
    }

    public RbNode getRight() {
        return right;
    }

    public void setRight(RbNode right) {
        this.right = right;
    }

    public boolean isColour() {
        return colour;
    }

    public void setColour(boolean colour) {
        this.colour = colour;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public int compareTo(Object o) {
        RbNode node = (RbNode) o;
        return this.key.compareTo(node.getKey());

    }
}
