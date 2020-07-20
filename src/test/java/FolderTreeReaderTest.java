import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import model.Folder;
import model.JavaClass;
import org.junit.jupiter.api.Test;
import reader.clazz.JavaClassReader;
import reader.folder.FolderTreeReader;

class FolderTreeReaderTest {

    @Test
    public void getStructureAsString_for_file() {
        //GIVEN
        String rootFolder = "folderTreeReaderTest/a/JavaClass.java";
        JavaClassReader javaClassReader = new JavaClassReader();
        FolderTreeReader folderTreeReader = new FolderTreeReader(javaClassReader);
        File file = new File(getClass().getClassLoader().getResource(rootFolder).getFile());
        JavaClass javaClass = new JavaClass(file.getName(), javaClassReader.countNoCommentedLines(file));

        //WHEN
        String structureAsString = folderTreeReader.getStructureAsString(file.getAbsolutePath());

        //THEN
        assertThat(structureAsString).isEqualTo(javaClass.toString());
    }

    @Test
    public void getStructureAsString_for_folder() {
        //GIVEN
        JavaClassReader javaClassReader = new JavaClassReader();
        FolderTreeReader folderTreeReader = new FolderTreeReader(javaClassReader);
        File rootFolder = new File(getClass().getClassLoader().getResource("folderTreeReaderTest/a").getFile());
        File rootFolderWithFile  = new File(getClass().getClassLoader().getResource( "folderTreeReaderTest/a/JavaClass.java").getFile());

        JavaClass javaClass = new JavaClass(rootFolderWithFile.getName(), javaClassReader.countNoCommentedLines(rootFolderWithFile));

        Folder folder = new Folder();
        folder.setName("a");
        folder.setJavaClasses(List.of(javaClass));

        //WHEN
        String structureAsString = folderTreeReader.getStructureAsString(rootFolder.getAbsolutePath());

        //THEN
        assertThat(structureAsString).isEqualTo(folder.toString());
    }

    @Test
    public void createFolderTree() {
        //GIVEN
        String rootFolder = "folderTreeReaderTest";
        JavaClassReader javaClassReader = new JavaClassReader();
        FolderTreeReader folderTreeReader = new FolderTreeReader(javaClassReader);
        File file = new File(getClass().getClassLoader().getResource(rootFolder).getFile());

        //WHEN
        Folder folderTreeRoot = folderTreeReader.createFolderTree(file, 0, null);

        //THEN
        List<Folder> subFolders = folderTreeRoot.getSubFolders();
        assertThat(folderTreeRoot.getName()).isEqualTo(rootFolder);
        assertThat(folderTreeRoot.getParent()).isNull();
        assertThat(subFolders.stream().map(Folder::getName).collect(Collectors.toList())).containsAll(List.of("a", "b"));
        assertThat(subFolders.stream().map(Folder::countLinesInJavaClasses).collect(Collectors.toList())).containsAll(List.of(3L, 4L));
        assertThat(subFolders.stream().flatMap(folder -> folder.getJavaClasses().stream()).map(JavaClass::getName)
                .collect(Collectors.toList())).containsAll(List.of("JavaClass.java", "JavaClass1.java"));
    }
}