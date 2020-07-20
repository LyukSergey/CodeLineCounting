package model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class JavaClass {

    private String name;
    private long lineCount;

    public JavaClass(String name, long lineCount) {
        this.name = name;
        this.lineCount = lineCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getLineCount() {
        return lineCount;
    }

    public void setLineCount(long lineCount) {
        this.lineCount = lineCount;
    }

    @Override
    public String toString() {
        return name + " : " + lineCount + " ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof JavaClass)) {
            return false;
        }

        JavaClass javaClass = (JavaClass) o;

        return new EqualsBuilder()
                .append(lineCount, javaClass.lineCount)
                .append(name, javaClass.name)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(name)
                .append(lineCount)
                .toHashCode();
    }
}
