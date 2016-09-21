import java.util.ArrayList;

/**
 * Created by camillom on 19/09/16.
 */
@SuppressWarnings("DefaultFileTemplate")
public class TestParse {

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
        ProjectiveParser parser;

        for (DependencyTree sent : corpus) {
            System.out.println("Parsing sentence n." + sent.getSent_number());

            System.out.println("Parsing with 2-steps...");
            parser = new TwoStepsProjectiveParser(sent);
            DependencyTree parsed = parser.execute();
            System.out.println("... done\n");

            //TODO: reset added field of arcs

            //ProjectiveParser pars = new NivreProjectiveParser(sent);
            //pars.execute();

            //parsed.printTree();
        }
    }
}
