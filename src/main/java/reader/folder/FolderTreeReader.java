package reader.folder;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import model.JavaClass;
import model.Folder;
import reader.clazz.JavaClassReader;

public class FolderTreeReader {

    private JavaClassReader javaClassReader;

    public FolderTreeReader(JavaClassReader javaClassReader) {
        this.javaClassReader = javaClassReader;
    }

    public String getStructureAsString(String path) {
        Folder root = createFolderTree(new File(path), 0, null);
        return printFolderTree(root, root, "");
    }

    public Folder createFolderTree(File file, int layer, Folder root) {
        Folder folder = new Folder();

        if (file.isDirectory()) {
            folder.setName(file.getName());
            folder.setParent(root);
            folder.setLayer(layer);
        }

        File[] files = file.listFiles();
        if (files != null) {

            List<JavaClass> javaClasses = createListOfJavaClassesInFolder(file);
            folder.setJavaClasses(javaClasses);

            List<File> directories = Arrays.asList(files)
                    .stream()
                    .filter(File::isDirectory)
                    .collect(Collectors.toList());

            for (File directory : directories) {
                folder.addSubFolder(createFolderTree(directory, folder.getLayer() + 1, folder));
            }
        }
        return folder;
    }

    private String printFolderTree(Folder folder, Folder root, String structure) {
        String shift = String.join("", Collections.nCopies(folder.getLayer(), " | "));
        if (!folder.getSubFolders().isEmpty()) {
            List<Folder> subFolders = folder.getSubFolders();
            StringBuilder structureBuilder = new StringBuilder(structure);
            for (Folder subFolder : subFolders) {
                root.setCountLinesInSubFolders(root.getCountLinesInSubFolders() + subFolder.countLinesInJavaClasses());
                structureBuilder.append(printFolderTree(subFolder, root, subFolder.toString()));
            }
            structure = shift + structureBuilder.toString();
        } else {
            return shift + folder.toString();
        }
        if (folder.getRoot() == null) {
            return shift + folder.getName() + " " + folder.getCountLinesInSubFolders() + "\n" + structure;
        }

        return structure;
    }

    private List<JavaClass> createListOfJavaClassesInFolder(File file) {
        List<File> files = Arrays.asList(file.listFiles())
                .stream()
                .filter(File::isFile)
                .collect(Collectors.toList());

        return files
                .stream()
                .map(file1 -> new JavaClass(file1.getName(), javaClassReader.contNoCommentedLines(file1)))
                .collect(Collectors.toList());
    }

}
