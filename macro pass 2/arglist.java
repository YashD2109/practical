public class arglist {
    String argname;
    String value;

    public arglist(String argname) {
        this.argname = argname;
        this.value = "";
    }

    public arglist(String argname, String value) {
        this.argname = argname;
        this.value = value;
    }
}
