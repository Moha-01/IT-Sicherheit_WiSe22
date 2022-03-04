public class A {
    private String string;
    private int value;

    public A(String string) {
        this.string = string;
    }

    public A(int value) {
        this.value = value;
    }

    public A(String string, int value) {
        this.string = string;
        this.value = value;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setValues(String string, int value) {
        this.string = string;
        this.value = value;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{ A | ");
        stringBuilder.append("string = ").append(string).append(",");
        stringBuilder.append("value = ").append(value).append(" }");
        return stringBuilder.toString();
    }
}