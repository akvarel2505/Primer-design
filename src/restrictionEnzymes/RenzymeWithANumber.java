package restrictionEnzymes;
/**
 * Created by futame on 12.04.14.
 */
public class RenzymeWithANumber implements Comparable<RenzymeWithANumber>{
    public Renzyme renzyme;
    public int number;

    public boolean isIntersect() {
        return intersect;
    }
    public void setIntersect(boolean intersect) {
        this.intersect = intersect;
    }

    private boolean intersect = false;
    private final int pos;

    @Override
    public int compareTo(RenzymeWithANumber r){
        if (r.number == this.number) return 0;
        if (r.number < this.number) return 1;
        return -1;
    }

    RenzymeWithANumber(int pos){
        this.pos = pos;
    }

    public String toString(){
        if (intersect) return renzyme.toString() + ' ' + '!';
        else return renzyme.toString();
    }
}

