import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by camillom on 14/09/16.
 */
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
            System.out.println("Sentence n." + sent.getSent_number());

            for (Map.Entry<Integer, Node> head : sent.getNodes().entrySet()) {
                System.out.print(head.getKey() + ": ");
                ArrayList<Arc> children = head.getValue().getLeft_children();
                for (Arc son : children) {
                    int son_id = son.getTail().getId();
                    System.out.print(son_id + " ");
                }
                System.out.print(", ");
                children = head.getValue().getRight_children();
                for (Arc son : children) {
                    int son_id = son.getTail().getId();
                    System.out.print(son_id + " ");
                }
                System.out.print("\n");
            }
            System.out.print("\n");
        }
    }
}
