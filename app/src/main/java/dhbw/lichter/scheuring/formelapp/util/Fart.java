package dhbw.lichter.scheuring.formelapp.util;

import android.database.sqlite.SQLiteStatement;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Fart {
    private int intensity;
    private int length;
    private int socialEmbarrassment;
    private int countChildren;
    private int averageAge;
    private double score;
    private String sex;
    private String name;

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
}
