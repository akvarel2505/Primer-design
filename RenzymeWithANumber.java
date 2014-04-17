/**
 * Created by futame on 12.04.14.
 */
public class RenzymeWithANumber implements Comparable<RenzymeWithANumber>{
    public Renzyme renzyme;
    public int number;

    @Override
    public int compareTo(RenzymeWithANumber r){
        if (r.number == this.number) return 0;
        if (r.number < this.number) return 1;
        return -1;
    }
}

