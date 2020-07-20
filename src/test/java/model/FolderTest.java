package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class FolderTest {

    @Test
    void countLinesInJavaClasses() {
        //GIVEN
        JavaClass javaClass1 = new JavaClass("name1", 10);
        JavaClass javaClass2 = new JavaClass("name2", 20);
        Folder folder = new Folder();
        folder.setJavaClasses(List.of(javaClass1, javaClass2));

        //WHEN
        long countLinesInJavaClasses = folder.countLinesInJavaClasses();

        //THEN
        assertThat(countLinesInJavaClasses).isEqualTo(30);
    }
}