
import java.io.*;

class FileManipulator{
    private String aspidFileData = "";


    public String getAspidFileData() {
        return aspidFileData;
    }

    public void setAspidFileData(String aspidFileData) {
        this.aspidFileData = aspidFileData;
    }

    public void readAspidFile(String fileName){
        String totalLine = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = "";
            while( (line = reader.readLine()) != null){
                totalLine += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        setAspidFileData(totalLine);
    }

    public void printAspidData(){
        System.out.println(getAspidFileData());
    }

    public void parseHTML(){
        String[] data = getAspidFileData().split(";");
        String finalHtml = "<!DOCTYPE html>\n<html>\n";
        for(String tag: data){
            finalHtml = finalHtml + "<" + tag + ">" + " </" + tag + ">\n";
        }
        finalHtml = finalHtml + "</html>";
        System.out.println(finalHtml);
    }
}


public class Main {

    public static void main(String[] args) {
        FileManipulator fm = new FileManipulator();
        fm.readAspidFile(args[0]);
        fm.parseHTML();

    }
}
