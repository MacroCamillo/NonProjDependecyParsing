import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by camillom on 06/09/16.
 */
@SuppressWarnings("DefaultFileTemplate")
public class CorpusReader {

    public static ArrayList<DependencyTree> read(String filename) {

        ArrayList<DependencyTree> sentence_list = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF8"))) {
            int num_sentence = 0;
            String line;

            while ((line = reader.readLine()) != null) {

                DependencyTree sentence = new DependencyTree(num_sentence);
                sentence_list.add(sentence);
                //System.out.println("Sentence: " + num_sentence);

                HashMap<Integer,Node> node_map = sentence.getNodes();

                while(line != null && line.trim().length() != 0) {            //remove whitespaces

                    String[] parts = line.split("\t");
                    int current = Integer.parseInt(parts[0]);
                    int head = Integer.parseInt(parts[6]);
                    Node cur_node;
                    //System.out.println("Current: " + current + " Head: "+ head);

                    cur_node = sentence.addNode(current);

                    Node head_node;
                    if(head > current) {

                        head_node = sentence.addNode(head);
                        head_node.addLeftSon(cur_node);
                    } else {
                        head_node = node_map.get(head);
                        head_node.addRightSon(cur_node);
                    }
                    line = reader.readLine();
                }
                num_sentence++;
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return sentence_list;
    }
}
