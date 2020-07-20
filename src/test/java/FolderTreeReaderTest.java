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
    void read() {
        /*File file = new File(
                getClass().getClassLoader().getResource("a").getFile()
        ); */

        File file = new File(
                "/Users/sergijluk/Documents/mangosoft/nogodi_services/nqd-peer-to-peer-transfer/src/main/java/sa/com/nogodi/p2ptransferservice"
//               "/Users/sergijluk/Documents/ home/java_pojects/algorithm/src/main/java/com/liuk/algorithm/extension"
//                "/Users/sergijluk/Documents/ home/java_pojects/algorithm/src/main/java/com/liuk/algorithm/threds"
        );
        JavaClassReader javaClassReader = new JavaClassReader();
        FolderTreeReader folderTreeReader = new FolderTreeReader(javaClassReader);
        System.out.println(folderTreeReader.getStructureAsString(file.getAbsolutePath()));
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