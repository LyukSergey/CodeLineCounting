package reader.folder;

import static util.FileUtil.isJavClass;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import model.Folder;
import model.JavaClass;
import reader.clazz.JavaClassReader;

public class FolderTreeReader {

    private JavaClassReader javaClassReader;

    public FolderTreeReader(JavaClassReader javaClassReader) {
        this.javaClassReader = javaClassReader;
    }

    public String getStructureAsString(String path) {
        File source = new File(path);
        if (source.isFile() && isJavClass(source.getName())) {
            return new JavaClass(source.getName(), javaClassReader.countNoCommentedLines(source)).toString();
        }
        Folder root = createFolderTree(source, 0, null);
        return printFoldersTree(root, root, "");
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

    private String printFoldersTree(Folder folder, Folder root, String structure) {
        String shift = String.join("", Collections.nCopies(folder.getLayer(), " | "));
        if (!folder.getSubFolders().isEmpty()) {
            List<Folder> subFolders = folder.getSubFolders()
                    .stream()
                    .sorted(Comparator.comparing(Folder::getName))
                    .collect(Collectors.toList());

            StringBuilder structureBuilder = new StringBuilder(structure);
            for (Folder subFolder : subFolders) {
                root.setCountLinesInSubFolders(root.getCountLinesInSubFolders() + subFolder.countLinesInJavaClasses());
                structureBuilder.append(printFoldersTree(subFolder, root, subFolder.toString()));
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

    private List<JavaClass> createListOfJavaClassesInFolder(File folder) {
        List<File> files = Arrays.asList(folder.listFiles())
                .stream()
                .filter(File::isFile)
                .collect(Collectors.toList());

        return files
                .stream()
                .filter(file1 -> isJavClass(file1.getName()))
                .map(file1 -> new JavaClass(file1.getName(), javaClassReader.countNoCommentedLines(file1)))
                .collect(Collectors.toList());
    }

}
