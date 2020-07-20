import java.util.Scanner;
import reader.clazz.JavaClassReader;
import reader.folder.FolderTreeReader;

public class CountingCodeLineMain {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String path;
        if (args.length == 0) {
            System.out.println("Please enter path to folder or file: ");
            path = sc.nextLine();
        } else {
            path = args[0];
        }
        JavaClassReader javaClassReader = new JavaClassReader();
        FolderTreeReader treeReader = new FolderTreeReader(javaClassReader);
        System.out.println(treeReader.getStructureAsString(path));
    }
}
