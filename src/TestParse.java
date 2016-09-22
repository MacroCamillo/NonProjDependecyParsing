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

        int countBetter = 0, countWorse = 0;
        int countSwapped = 0;
        int totalOpNivre = 0, totalOpTwoSteps = 0;
        int countWords = 0;

        for (DependencyTree sent : corpus) {
            System.out.println("Parsing sentence n." + sent.getSent_number());

            System.out.println("Parsing with 2-steps...");
            parser = new TwoStepsProjectiveParser(sent);
            DependencyTree parsed = parser.execute();
            System.out.println("... done\n");

            int twoStepsOp = parser.getN_op();
            totalOpTwoSteps += twoStepsOp;

            sent.resetAdded();

            System.out.println("Parsing with original Nivre algorithm...");
            parser = new NivreProjectiveParser(sent);
            parser.execute();
            System.out.println("... done");
            System.out.println("-----------------------------------------");

            int nivreOp = parser.getN_op();
            totalOpNivre += nivreOp;

            if (parser.n_swap > 0)
                countSwapped++;
            if (twoStepsOp < nivreOp)
                countBetter++;
            if (twoStepsOp > nivreOp)
                countWorse++;

            countWords += sent.getNodes().size();
        }

        System.out.println("###################################################\n");
        System.out.println("Total number of sentences: " + corpus.size());
        System.out.println("Non-projective ones: " + countSwapped + "\n");

        System.out.println("2-steps better than Nivre: " + countBetter + " times");
        System.out.println("2-steps worse than Nivre: " + countWorse + " times\n");

        System.out.println("Total word count: " + countWords);
        System.out.println("Total operations with 2-steps: " + totalOpTwoSteps);
        System.out.println("Total operations with Nivre: " + totalOpNivre);
    }
}
