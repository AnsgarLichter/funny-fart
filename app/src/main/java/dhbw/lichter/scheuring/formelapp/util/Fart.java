package dhbw.lichter.scheuring.formelapp.util;

import android.database.sqlite.SQLiteStatement;

public class Fart {
    private int intensity;
    private int length;
    private int socialEmbarrassment;
    private int countChildren;
    private int averageAge;
    private double score;
    private String sex;
    private String name;
    private String insert_fart;

    public Fart(int intensity, int length, int socialEmbarrassment, int countChildren, int averageAge, String sex) {
        this.intensity = intensity;
        this.length = length;
        this.socialEmbarrassment = socialEmbarrassment;
        this.countChildren = countChildren;
        this.averageAge = averageAge;
        this.sex = sex;
    }

    public SQLiteStatement prepareInsertStatement(SQLiteStatement insertFart) {
        insertFart.bindDouble(1, score);
        insertFart.bindString(2, name);
        insertFart.bindLong(3, intensity);
        insertFart.bindLong(4, length);
        insertFart.bindLong(5, socialEmbarrassment);
        insertFart.bindLong(6, countChildren);
        insertFart.bindDouble(7, averageAge);
        insertFart.bindString(8, sex);

        return insertFart;
    }

    public int getIntensity() {
        return intensity;
    }

    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getSocialEmbarrassment() {
        return socialEmbarrassment;
    }

    public void setSocialEmbarrassment(int socialEmbarrassment) { this.socialEmbarrassment = socialEmbarrassment; }

    public int getCountChildren() {
        return countChildren;
    }

    public void setCountChildren(int countChildren) {
        this.countChildren = countChildren;
    }

    public int getAverageAge() {
        return averageAge;
    }

    public void setAverageAge(int averageAge) {
        this.averageAge = averageAge;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}
