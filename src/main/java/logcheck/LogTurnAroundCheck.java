package logcheck;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LogTurnAroundCheck{
    String prevLine, currentLine;
    File file;
    LineIterator lineIterator;
    Logger logger= Logger.getLogger("LogTurnAroundCheck");
    int prev;


    public LogTurnAroundCheck() {
        this.prevLine = "";
        try{
            String userDir= System.getProperty("user.dir");
            this.file= new File(userDir + "\\src\\main\\java\\logcheck\\LOG_0001.txt"); //todo into file
            this.prev= 0;
        }catch (Exception pe){
            pe.printStackTrace();
        }

    }

    public void lineByRead() {
        try {
            lineIterator = FileUtils.lineIterator(file, "UTF-8");

            while (lineIterator.hasNext()) {
                currentLine = lineIterator.nextLine();
                //logger.info("뭘 읽고있니 "+ currentLine);
                turnAroundCheck();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            LineIterator.closeQuietly(lineIterator);
        }
    }

    /**
     *
     * @return (true: turn around, false: normal)
     */
    public boolean turnAroundCheck(){
        //todo modify regex pattern
        Pattern p= Pattern.compile("(\\d{6})\\s(\\d{3})\\s");
        Matcher mCurrent= p.matcher(currentLine);
        int currentInt;


        try{
            mCurrent.find();
            //System.out.println(mCurrent.group(1)+ mCurrent.group(2));
            currentInt= Integer.valueOf(mCurrent.group(1)+ mCurrent.group(2));
            if(this.prev > currentInt){
                logger.info("turnAround>> prev: "+ this.prev+ "\tcurrent: "+ currentInt);
            }

            //todo prev change
            this.prev= currentInt;
        } catch (Exception e){
            logger.info("not found in "+ currentLine);
        }

//        return true;
        return false;
    }
}
