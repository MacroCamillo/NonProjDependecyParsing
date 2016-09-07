import java.io.*;

/**
 * Created by camillom on 06/09/16.
 */
public class CorpusReader {

    public static void read(String filename) {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF8"))) {
            int num_sentence = 0;
            DependencyTree sentence = new DependencyTree(num_sentence);
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().length() != 0) {            //remove whitespaces

                    String[] parts = line.split("\t");
                    int current = Integer.parseInt(parts[0]);
                    int head = Integer.parseInt(parts[6]);

                    Node cur_node = sentence.getNodes().get(current); //se è testa di nodi precedenti esiste già
                    if (cur_node == null) {
                        cur_node = new Node(current, num_sentence);
                        sentence.getNodes().add(current, cur_node);
                    }

                    Node head_node = sentence.getNodes().get(head);
                    if(head > current) {
                        if (head_node == null) {            //the Tree doesn't contain the head yet
                            head_node = new Node(head, num_sentence);
                            sentence.getNodes().add(head, head_node);
                        }
                        head_node.addLeftSon(cur_node);
                    } else {
                        head_node = sentence.getNodes().get(head);
                        head_node.addRightSon(cur_node);
                    }
                }
                else {
                    num_sentence++;
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }


}
