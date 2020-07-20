package reader.clazz;

import exeption.ReadFileException;
import exeption.NotCorrectArgumentException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.List;

public class JavaClassReader {

    private static final String START_MULTI_LINE_COMMENT = "/*";
    private static final String END_MULTI_LINE_COMMENT = "*/";
    private static final String LINE_COMMENT = "//";

    public long countNoCommentedLines(File file) {
        if (!file.isFile()) {
            String errorMessage = "[%s] is not a file.";
            throw new NotCorrectArgumentException(String.format(errorMessage, file.getName()));
        }
        List<String> allClazzLines;
        try {
            allClazzLines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new ReadFileException(e.getMessage());
        }

        HashSet<String> commentedLines = findAllCommentedLines(allClazzLines);

        return allClazzLines.stream()
                .filter(s -> !s.trim().isEmpty())
                .filter(s -> !commentedLines.contains(s))
                .count();
    }

    private HashSet<String> findAllCommentedLines(List<String> lines) {
        HashSet<String> hashSet = new HashSet<>();
        for (int i = 0; i < lines.size() - 1; i++) {
            if (lines.get(i).trim().contains(START_MULTI_LINE_COMMENT)) {
                hashSet.add(lines.get(i));
                for (int j = i; j < lines.size() - 1; j++) {
                    if (!lines.get(j).trim().contains(END_MULTI_LINE_COMMENT)) {
                        i = j;
                        hashSet.add(lines.get(j));

                    }
                    if (lines.get(j).trim().contains(END_MULTI_LINE_COMMENT)) {
                        if (hashSet.contains(lines.get(j)) && !lines.get(j).startsWith(START_MULTI_LINE_COMMENT)) {
                            hashSet.remove(lines.get(j));
                        } else {
                            hashSet.add(lines.get(j));
                        }
                        i = j;
                        break;
                    }
                }
            }
            if (lines.get(i).trim().startsWith(LINE_COMMENT)) {
                hashSet.add(lines.get(i));
            }
        }
        return hashSet;

    }

}
