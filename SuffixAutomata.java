import org.omg.CosNaming._NamingContextStub;
import sun.net.www.content.image.gif;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

/**
 * Created by futame on 06.03.14.
 */
// g = 0; c = 1; t = 2; a = 3

   /*
    *Создание: SuffixAutomata s = new SuffixAutomata(DNA);
    * Поиск количества подстрок строки t в DNA s.numOfOccurrences(t);
    * Поиск первых символов вхождений t в DNA s.getOccurrences(t).
    */
public class SuffixAutomata {
    final static int alphabetSize = 4;
    private State[] states;
    private int[] s;
    private int last;
    private int size;
    class State{
        private int len;
        private int link;
        private int[] next;
        private int cnt;
        public List<Integer> inversedLinks;

        public int getCnt() {
            return cnt;
        }
        public void setCnt(int cnt) {
            this.cnt = cnt;
        }
        public int getLink() {
            return link;
        }
        public int getNext(int character) {
            return next[character];
        }
        public int getLen() {
            return len;
        }
        public void setLen(int len) {
            this.len = len;
        }
        public void setLink(int link) {
            this.link = link;
        }
        public void setNext(int character, int value) {
            this.next[character] = value;
        }
        public void addInversedLink(int link) {
            inversedLinks.add(link);
        }

        State(){
            len = 0;
            link = -1;
            next = new int[alphabetSize];
            Arrays.fill(next, -1);
        }

        public State getClone(){
            State state = new State();
            state.len = len;
            state.link = link;
            state.next = Arrays.copyOf(next,alphabetSize);
            return state;
        }
    }

    SuffixAutomata(String S){
        s = stringToNum(S);
        build();
    }
    private void extend(int character){
        int current_vertex = size ++;
        states[current_vertex] = new State();
        states[current_vertex].setLen(states[last].getLen() + 1);
        states[current_vertex].setCnt(1);
        int p;

        for (p = last; (p != -1) && (states[p].getNext(character) == -1); p = states[p].getLink() ){
            states[p].setNext(character, current_vertex);
        }

        if (p == -1){
            states[current_vertex].setLink(0);
        } else {
            int q = states[p].getNext(character);
            if (states[p].getLen() + 1 == states[q].getLen()){
                states[current_vertex].setLink(q);
            } else {
                int clone = size++;
                states[clone] = states[q].getClone();
                states[clone].setCnt(0);
                states[clone].setLen(states[p].getLen() + 1);
                for (; (p != -1) && (states[p].getNext(character) == q); p = states[p].getLink()){
                    states[p].setNext(character, clone);
                }
                states[q].setLink(clone);
                states[current_vertex].setLink(clone);
            }
        }
        last = current_vertex;
    }

    private void calculateCnt(){
        ArrayList<Integer> [] listsOfStates = new ArrayList[s.length + 1];
        for (int i = 0; i < s.length + 1; ++i){
            listsOfStates[i] = new ArrayList<Integer>();
        }

        for (int i = 0;i < size; ++i){
            listsOfStates[states[i].getLen()].add(i);
        }
        for (int len = s.length; len > 0; --len){
            for (int i = 0; i < listsOfStates[len].size(); ++i){
                int link = states[listsOfStates[len].get(i)].getLink();
                states[link].setCnt(states[listsOfStates[len].get(i)].getCnt() + states[link].getCnt());
            }
        }
    }

    private void build(){
        states = new State[2 * s.length];
        states[0] = new State();
        states[0].setCnt(1);
        last = 0;
        size = 1;

        for (int i = 0; i < s.length; ++i){
            extend(s[i]);
        }
        calculateCnt();
        for (int i = 1; i < size; ++i) {
            states[states[i].getLink()].addInversedLink(i);
        }
    }

    private int[] stringToNum(String t){
        int[] arrayOfInt = new int[t.length()];
        for (int i = 0; i < t.length(); ++i){
            switch (t.charAt(i)){
                case 'g':
                    arrayOfInt[i] = 0;
                    break;
                case 'c':
                    arrayOfInt[i] = 1;
                    break;
                case 't':
                    arrayOfInt[i] = 2;
                    break;
                case 'a':
                    arrayOfInt[i] = 3;
                    break;
                default:
                    arrayOfInt[i] = -1;
                    break;
            }
        }
        return arrayOfInt;
    }
    public int numOfOccurrences(String T){
        int[] t = stringToNum(T);
        int current_vertex = 0;
        for (int i = 0; i < t.length; ++i){
            current_vertex = states[current_vertex].getNext(t[i]);
            if (current_vertex == -1){
                break;
            }
        }
        if (current_vertex == -1) {
            return 0;
        }
        return states[current_vertex].getCnt();
    }

    private void depthFirstSearch(int vertex, int [] result, int last) {
        result[last++] = states[vertex].getLen() - 1;

        for (int nextVertex : states[vertex].inversedLinks) {
            depthFirstSearch(nextVertex, result, last);
        }
    }

    public int [] getOccurrences(String T) {
        int[] t = stringToNum(T);
        int current_vertex = 0;
        for (int i = 0; i < t.length; ++i){
            current_vertex = states[current_vertex].getNext(t[i]);
            if (current_vertex == -1){
                break;
            }
        }
        if (current_vertex == -1) {
            return null;
        }

        int[] result = new int[states[current_vertex].getCnt()];
        int last = 0;

        depthFirstSearch(current_vertex, result, last);

        for (int i = 0; i < result.length; ++i) {
            result[i] -= t.length - 1;
        }
        Arrays.sort(result);
        return result;
    }
}
