import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import exeption.NotCorrectArgumentException;
import java.io.File;
import org.junit.jupiter.api.Test;
import reader.clazz.JavaClassReader;

class JavaClassReaderTest {

    @Test
    void countCommentedLines_pathological_comment() {
        //GIVEN
        JavaClassReader javaClassReader = new JavaClassReader();
        File file = new File(getClass().getClassLoader().getResource("clazzReaderTest/a/b/c/TestPathologicalComment.java").getFile());

        //WHEN
        long countCommentedLines = javaClassReader.countNoCommentedLines(file);

        //THEN
        assertThat(countCommentedLines).isEqualTo(5);
    }

    @Test
    void countCommentedLines_simple_comment() {
        //GIVEN
        JavaClassReader javaClassReader = new JavaClassReader();
        File file = new File(getClass().getClassLoader().getResource("clazzReaderTest/a/b/c/SimpleComments.java").getFile());

        //WHEN
        long countCommentedLines = javaClassReader.countNoCommentedLines(file);

        //THEN
        assertThat(countCommentedLines).isEqualTo(3);
    }

    @Test
    void countCommentedLines_exception() {
        //GIVEN
        String errorMessage = "[%s] is not a file.";
        JavaClassReader javaClassReader = new JavaClassReader();
        File file = new File(getClass().getClassLoader().getResource("clazzReaderTest/a/b/c").getFile());

        //WHEN
        Throwable thrown = catchThrowable(() -> javaClassReader.countNoCommentedLines(file));

        //THEN
        assertThat(thrown).isInstanceOf(NotCorrectArgumentException.class).hasMessageContaining(String.format(errorMessage, file.getName()));
    }
}