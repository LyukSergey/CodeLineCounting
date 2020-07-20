package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Folder {

    private Folder parent;
    private String name;
    private int layer;
    private long countLinesInSubFolders;
    private List<JavaClass> javaClasses = new ArrayList<>();
    private List<Folder> subFolders = new ArrayList<>();

    public void setParent(Folder parent) {
        this.parent = parent;
    }

    public Folder getParent() {
        return this.parent;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }


    public void setJavaClasses(List<JavaClass> javaClasses) {
        this.javaClasses = javaClasses;
    }

    public List<JavaClass> getJavaClasses() {
        return this.javaClasses;
    }

    public void addSubFolder(Folder subFolder) {
        this.subFolders.add(subFolder);
    }


    public List<Folder> getSubFolders() {
        return subFolders;
    }

    public long countLinesInJavaClasses() {
        return javaClasses.stream().map(JavaClass::getLineCount).reduce(0L, Long::sum);
    }

    public Folder getRoot() {
        return parent;
    }

    public long getCountLinesInSubFolders() {
        return countLinesInSubFolders;
    }

    public void setCountLinesInSubFolders(long countLinesInSubFolders) {
        this.countLinesInSubFolders = countLinesInSubFolders;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getName());
        stringBuilder.append(" ");
        stringBuilder.append(countLinesInJavaClasses());
        stringBuilder.append(" ");
        stringBuilder.append(javaClasses.stream().map(JavaClass::toString).collect(Collectors.joining()));
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Folder)) {
            return false;
        }

        Folder folder = (Folder) o;

        return new EqualsBuilder()
                .append(layer, folder.layer)
                .append(countLinesInSubFolders, folder.countLinesInSubFolders)
                .append(parent, folder.parent)
                .append(name, folder.name)
                .append(javaClasses, folder.javaClasses)
                .append(subFolders, folder.subFolders)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(parent)
                .append(name)
                .append(layer)
                .append(countLinesInSubFolders)
                .append(javaClasses)
                .append(subFolders)
                .toHashCode();
    }
}
