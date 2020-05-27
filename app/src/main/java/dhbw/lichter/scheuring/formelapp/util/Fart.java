package dhbw.lichter.scheuring.formelapp.util;

import android.database.sqlite.SQLiteStatement;

import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@RequiredArgsConstructor
public class Fart {
    @NonNull private int intensity;
    @NonNull private int length;
    @NonNull private int socialEmbarrassment;
    @NonNull private int countChildren;
    @NonNull private int averageAge;
    @NonNull private double score;
    @NonNull private String sex;
    @NonNull private String name;
    private String creationDate;
    private long id;

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
