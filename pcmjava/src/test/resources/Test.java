public class Test {
    
    private int foo;
    protected Test test;

    public int getFoo() { return foo; }

    public Test bar() {
        if (foo > 0) {
            return test;
        } else {
            return getFoo();
        }
    }

}
