package model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class JavaClassTest {

    @Test
    void testToString() {
        //GIVEN
        String name = "Test.java";
        long lineCount = 13L;
        String expectedString = name + " " + lineCount + " ";
        JavaClass javaClass = new JavaClass(name, lineCount);

        //WHEN
        String string = javaClass.toString();

        //THEN
        assertThat(string).isEqualTo(expectedString);
    }
}