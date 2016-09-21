import java.util.ArrayList;

/**
 * Created by camillom on 14/09/16.
 */
@SuppressWarnings("DefaultFileTemplate")
public class TestRead {

    public static void main(String[] args){

        if (args.length!=1) {
            System.out.println(args.length);
            System.out.println("Usage: TestRead <filename>");
            return;
        }

        String file_path = args[0];

        if (file_path.substring(file_path.length()-7 ,file_path.length()).equals(".conll")) {
            System.out.println("File must be a .conll file");
            return;
        }

        ArrayList<DependencyTree> corpus = CorpusReader.read(file_path);

        for (DependencyTree sent : corpus) {
            sent.printTree();
        }
    }
}
