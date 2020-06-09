package dhbw.lichter.scheuring.formelapp.util;

import java.util.Comparator;

public class FartComparator implements Comparator<Fart> {
    private String sortProperty;

    public final static String SORT_SCORE = "score";
    public final static String SORT_NAME = "name";
    public final static String SORT_CREATON_DATE = "creationDate";

    public FartComparator(String sortProperty) {
        this.sortProperty = sortProperty;
    }

    public int compare(Fart o1, Fart o2) {
        switch(sortProperty) {
            case SORT_SCORE:
                return compareScore(o1,o2);
            case SORT_NAME:
                return compareName(o1,o2);
            case SORT_CREATON_DATE:
                return compareCreationDate(o1,o2);
            default:
                return compareId(o1,o2);
        }
    }

    private int compareId(Fart o1, Fart o2) {
        return Double.compare(o1.getScore(), o2.getScore());
    }

    private int compareScore(Fart o1, Fart o2) {
        return Double.compare(o1.getScore(), o2.getScore());
    }

    private int compareCreationDate(Fart o1, Fart o2) {
        return o1.getCreationDate().compareTo(o2.getCreationDate());
    }

    private int compareName(Fart o1, Fart o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
