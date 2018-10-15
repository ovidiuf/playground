package io.novaordis.playground.java.beans.deepCopy;

public class SomethingElse implements Cloneable {

    private String color;

    public SomethingElse(String color) {

        this.color = color;
    }

    public String getColor() {

        return color;
    }

    public void setColor(String color) {

        this.color = color;
    }

    @Override
    public SomethingElse clone() {

        try {

            //
            // no need to copy members that are immutable (id, name), super.clone() copies references for us
            //

            return (SomethingElse) super.clone();
        }
        catch (CloneNotSupportedException e) {

            throw new IllegalStateException(e);
        }
    }

    @Override
    public String toString() {

        return color;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {

            return true;
        }

        if (color == null) {

            return false;
        }

        if (!(o instanceof SomethingElse)) {

            return false;
        }

        SomethingElse that = (SomethingElse)o;
        return color.equals(that.color);
    }

    @Override
    public int hashCode() {

        return color == null ? 0 : color.hashCode();
    }


}
