package playground;

@SuppressWarnings("WeakerAccess")
public class Red {

    private Green green;

    public Red(Green green) {

        this.green = green;
    }

    public String run() {

        return this + " -> " + green.run();
    }

    @Override
    public String toString() {

        return "Red[]";
    }
}
