/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package parse;
import java.util.*;
public class Parse {
 private Map<String, List<String>> P;

    public Parse() {
        P = new HashMap<String, List<String>>();
    }

    public void addP(String non, String[] exp) {
        List<String> PL = P.getOrDefault(non, new ArrayList<String>());
        PL.addAll(Arrays.asList(exp));
        P.put(non, PL);
    } 

    public String gen() {
        String sS = "<sentence>";
        List<String> s = genSen(sS);
        return String.join(" ", s);
    }

    private List<String> genSen(String symbol) {
        List<String> sen = new ArrayList<String>();

        if (!P.containsKey(symbol)) {
            sen.add(symbol);
            return sen;
        }

        List<String> exps = P.get(symbol);
        String exp = SE(exps);
        String[] symbols = exp.split("\\s+");

        for (String s : symbols) {
            sen.addAll(genSen(s));
        }

        return sen;
    }

    private String SE(List<String> expansions) {
        Map<String, Double> PRO = new HashMap<String, Double>();
        double TP = 0.0;

        for (String expansion : expansions) {
            double prob = 1.0;
            String[] symbols = expansion.split("\\s+");

            for (String s : symbols) {
                if (P.containsKey(s)) {
                    List<String> subExpansions = P.get(s);
                    double subProb = subExpansions.size();
                    prob *= subProb;
                }
            }

            PRO.put(expansion, prob);
            TP += prob;
        }

        double rand = Math.random() * TP;
        double CProb = 0.0;

        for (String expansion : expansions) {
            double prob = PRO.get(expansion);
            CProb += prob;

            if (CProb >= rand) {
                return expansion;
            }
        }

        return "";
    }
    public static void main(String[] args) {
       Parse parser = new Parse();

        // Define grammar rules
        parser.addP("<sentence>", new String[] {"<noun_phrase> <verb_phrase>","she <verb_phrase>","he <verb_phrase>",
            "<noun_phrase> <verb_phrase> that <verb> <noun_phrase>"});
        parser.addP("<noun_phrase>", new String[] {"<article> <noun>"});
        parser.addP("<verb_phrase>", new String[] {"<verb> <noun_phrase>"});
        parser.addP("<article>", new String[] {"the", "a"});
        parser.addP("<noun>", new String[] {"dog", "cat", "fish"});
        parser.addP("<verb>", new String[] {"chases", "eats", "slaps","kissed"});

      
            String sentence = parser.gen();
            System.out.println(sentence);
        
    }
    }
    

