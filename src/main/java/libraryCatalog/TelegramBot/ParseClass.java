package libraryCatalog.TelegramBot;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ParseClass {



    public String bookNameParse(String[] strings){
        String[] s = strings[0].split("(?=\\p{Lu})");
        String bookName="";
        for (int i =0;i<s.length;i++){
            bookName+=" "+s[i];
        }
        return bookName;
    }
    public String authorNameParse(String[] strings){
        String[] author = strings[2].split("(?=\\p{Lu})");;
        String authorName=author[0];
        for (int i =1;i<author.length;i++){
            authorName+=" "+author[i];
        }
        return authorName;
    }
     public String locationNameParse(String[] strings){
         String[] location = strings[3].split("(?=\\p{Lu})");
         String locationName=location[0];
         for (int i =1;i<location.length;i++){
             locationName+=" "+location[i];
         }
        return locationName;
     }
     public java.sql.Date addedDateParseToSqlDate(String[] strings){
         SimpleDateFormat format = new SimpleDateFormat();
         format.applyPattern("yyyy-MM-dd");
         java.util.Date addedDate= null;
         try {
             addedDate = format.parse(strings[5]);
         } catch (ParseException e) {
             e.printStackTrace();
         }

         java.sql.Date sqlAddedDate
                 = new java.sql.Date(addedDate.getTime());
            return sqlAddedDate;

     }
    public java.sql.Date pubDateParseToSqlDate(String[] strings){
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        java.util.Date pubDate= null;
        try {
            pubDate = format.parse(strings[4]);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        java.sql.Date sqlPubDate
                = new java.sql.Date(pubDate.getTime());
        return sqlPubDate;
    }
    public java.sql.Date modDateParseToSqlDate(String[] strings){
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        java.util.Date modDate= null;
        try {
            modDate = format.parse(strings[6]);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        java.sql.Date sqlModDate
                = new java.sql.Date(modDate.getTime());
        return sqlModDate;
    }

}
