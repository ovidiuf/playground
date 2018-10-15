package io.novaordis.playground.java.beans.deepCopy;

public class Something implements Cloneable {

    private long id;
    private String name;
    private SomethingElse somethingElse;

    Something(long id, String name, SomethingElse somethingElse) {

        this.id = id;
        this.name = name;
        this.somethingElse = somethingElse;
    }

    @Override
    public Something clone() {

        try {

            Something c =  (Something) super.clone();

            //
            // no need to copy members that are immutable (id, name), super.clone() copies references for us ...
            //

            //
            // ... but we need to clone mutable members
            //

            c.somethingElse = somethingElse.clone();

            return c;
        }
        catch (CloneNotSupportedException e) {

            throw new IllegalStateException(e);
        }
    }

    @Override
    public String toString() {

        return id + ": " + name + "(" + somethingElse + ")";
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {

            return true;
        }

        if (name == null) {

            return false;
        }

        if (somethingElse == null) {

            return false;
        }

        if (!(o instanceof Something)) {

            return false;
        }

        Something that = (Something)o;
        return id == that.id && name.equals(that.name) && somethingElse.equals(that.somethingElse);
    }

    @Override
    public int hashCode() {

        return
                Long.hashCode(id) +
                        17 * (name == null ? 0 : name.hashCode()) +
                        19 * (somethingElse == null ? 0 : somethingElse.hashCode());
    }
}
