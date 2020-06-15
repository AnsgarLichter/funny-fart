package dhbw.lichter.scheuring.formelapp.database;

import android.database.sqlite.SQLiteStatement;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@RequiredArgsConstructor
public class Fart {
    private int intensity;
    private int length;
    private int socialEmbarrassment;
    private int countChildren;
    private int averageAge;
    private double score;
    private String sex;
    private String name;
    private String audioPath = "";
    private String creationDate;
    private long id;

    public Fart(int intensity, int length, int socialEmbarrassment, int countChildren, int averageAge, double score, String sex) {
        this.intensity = intensity;
        this.length = length;
        this.socialEmbarrassment = socialEmbarrassment;
        this.countChildren = countChildren;
        this.averageAge = averageAge;
        this.score = score;
        this.sex = sex;
    }

    public Fart(int intensity, int length, int socialEmbarrassment, int countChildren, int averageAge, double score, String sex, String audioPath) {
        this.intensity = intensity;
        this.length = length;
        this.socialEmbarrassment = socialEmbarrassment;
        this.countChildren = countChildren;
        this.averageAge = averageAge;
        this.score = score;
        this.sex = sex;
        this.audioPath = audioPath;
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
        insertFart.bindString(9, audioPath);

        return insertFart;
    }
}
