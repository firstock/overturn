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
    String fileName;
    LineIterator lineIterator;
    int prev;
    int cntLog, cntOverturnLog;

    Logger logger= Logger.getLogger("LogTurnAroundCheck");


    public LogTurnAroundCheck() {
        this.prevLine = "";
        this.prev= 0;
        this.cntLog= 0;
        this.cntOverturnLog= 0;
        this.fileName= "LOG.txt";

        //todo 생성자에서 딴거 분리
        //todo 임의 파일명인수로 넣어도 실행되게. default param도 있고
        fileSet();
    }

    public void fileSet(){
        try{
            String userDir= System.getProperty("user.dir");
            this.file= new File(userDir + "\\src\\main\\java\\logcheck\\"+ fileName); //todo into file
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
            logger.info("totLog: "+ cntLog+ "\toverturnLog: "+ cntOverturnLog);
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
        //todo modify regex pattern - real Log
        Pattern p= Pattern.compile("(\\d{6})\\s(\\d{3})\\s");
        Matcher mCurrent= p.matcher(currentLine);
        int currentInt;


        try{
            mCurrent.find(); // have to do
            cntLog++;
            //System.out.println(mCurrent.group(1)+ mCurrent.group(2));
            currentInt= Integer.valueOf(mCurrent.group(1)+ mCurrent.group(2));
            if(this.prev > currentInt){
                logger.info("turnAround> prev: "+ this.prev+ "\tcurrent: "+ currentInt);
                cntOverturnLog++;
            }

            this.prev= currentInt;
        } catch (Exception e){
            logger.info("not found in "+ currentLine);
        }

//        return true;
        return false;
    }
}
