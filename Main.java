package SyllableDistribution;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author danhd
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        String test = readStringFromFile("data/5815000");

        int[] count = countDistributedTones(test);

        String[] toneName = {"SHARP", "HANGING", "ASKING", "TUMBLING", "HEAVY", "LEVEL"};
        //print count distributed tones result
        for (int i = 0; i < count.length; i++) {
            System.out.println("Number of words belongs to DIACRITIC " + toneName[i] + " : " + count[0]);
        }

        HashMap<String, Integer> syllableDitribution = countDistributedSyllables(test);

        //print count distributed syllables result
        for (String key : syllableDitribution.keySet()) {
            System.out.println("Number of words has " + key + " syllables: " + syllableDitribution.get(key));
        }
    }

    private static int[] countDistributedTones(String s) {
        int[] count = new int[6];
        for (int i = 0; i < 6; i++) {
            count[i] = 0;
        }
        String[] words = s.replaceAll("_", " ").split(" ");
        for (int i = 0; i < words.length; i++) {
            Pattern p = Pattern.compile("\\w*[" + Diacritic.SHARP + "]+");
            Matcher m = p.matcher(words[i]);
            if (m.find()) // match
            {
                count[0]++;
                continue;
            }

            p = Pattern.compile("\\w*[" + Diacritic.HANGING + "]+");
            m = p.matcher(words[i]);
            if (m.find()) // match
            {
                count[1]++;
                continue;
            }

            p = Pattern.compile("\\w*[" + Diacritic.ASKING + "]+");
            m = p.matcher(words[i]);
            if (m.find()) // match
            {
                count[2]++;
                continue;
            }

            p = Pattern.compile("\\w*[" + Diacritic.TIMBLING + "]+");
            m = p.matcher(words[i]);
            if (m.find()) // match
            {
                count[3]++;
                continue;
            }

            p = Pattern.compile("\\w*[" + Diacritic.HEAVY + "]+");
            m = p.matcher(words[i]);
            if (m.find()) // match
            {
                count[4]++;
                continue;
            }
            count[5]++;
        }

        return count;
    }

    private static HashMap<String, Integer> countDistributedSyllables(String s) {
        HashMap<String, Integer> result = new HashMap();
        String[] words = s.split(" ");
        for (int i = 0; i < words.length; i++) {
            int matches = 1;
            Matcher matcher = Pattern.compile("_", Pattern.CASE_INSENSITIVE).matcher(words[i]);
            while (matcher.find()) {
                matches++;
            }
            if (result.containsKey(String.valueOf(matches))) {
                int value = result.get(String.valueOf(matches)) + 1;
                result.put(String.valueOf(matches), value);
            } else {
                result.put(String.valueOf(matches), 1);
            }
        }
        return result;
    }

    private static String readStringFromFile(String path) throws FileNotFoundException, IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded);
    }
}
