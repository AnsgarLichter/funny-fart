package dhbw.lichter.scheuring.formelapp.util;

public class Fart {
    private int intensity;
    private int length;
    private int socialEmbarrassment;
    private int countChildren;
    private int averageAge;
    private double score;
    private String sex;

    public Fart (int intensity, int length, int socialEmbarrassment, int countChildren, int averageAge, String sex) {
       this.intensity = intensity;
       this.length = length;
       this.socialEmbarrassment = socialEmbarrassment;
       this.countChildren = countChildren;
       this.averageAge = averageAge;
       this.sex = sex;
    }

    public int getIntensity () {
        return intensity;
    }

    public void setIntensity (int intensity) {
        this.intensity = intensity;
    }

    public int getLength () {
        return length;
    }

    public void setLength (int length) {
        this.length = length;
    }

    public int getSocialEmbarrassment () {
        return socialEmbarrassment;
    }

    public void setSocialEmbarrassment (int socialEmbarrassment) {
        this.socialEmbarrassment = socialEmbarrassment;
    }

    public int getCountChildren () {
        return countChildren;
    }

    public void setCountChildren (int countChildren) {
        this.countChildren = countChildren;
    }

    public int getAverageAge () {
        return averageAge;
    }

    public void setAverageAge (int averageAge) {
        this.averageAge = averageAge;
    }

    public String getSex () {
        return sex;
    }

    public void setSex (String sex) {
        this.sex = sex;
    }

    public double getScore() {
        return score;
    }

    public void setScore (double score) {
        this.score = score;
    }

    public String ToInsertString () {
        return "" + score + ", " + intensity + ", " + length + ", " + socialEmbarrassment + ", " + countChildren + ", " + averageAge + ", " + sex;
    }
}
