
import java.io.*;
import java.util.*;

class FileManipulator{
    private String aspidFileData = "";

    public String[] getAspidFileData() {
        return aspidFileData.split(";");
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

}

class HtmlTagStructure{
    public String beginningTag;
    public String text;
    public String special;
    public String endingTag;
}

class Parser{

	private ArrayList<HtmlTagStructure> htmlTags = new ArrayList<>();

	public void addHtmlBeginningTags(){
	    HtmlTagStructure doctype = new HtmlTagStructure();
        HtmlTagStructure html = new HtmlTagStructure();
	    doctype.beginningTag = "<!DOCTYPE>";
        html.beginningTag = "<html>";
        html.endingTag = "</html>";
        this.htmlTags.add(doctype);
        this.htmlTags.add(html);
    }

	public HtmlTagStructure getTags(String function){
	    HtmlTagStructure tagStructure = new HtmlTagStructure();
		String tag = function.split("\\(")[0];
		tagStructure.beginningTag = "<" + tag + ">";
        tagStructure.endingTag = "</" + tag + ">";
        return tagStructure;
	}

	public void parseHTML(String[] data){
	    addHtmlBeginningTags();
        for(String tag: data){
            this.htmlTags.add(getTags(tag));
        }
    }

    public String addSpacing(int numberOfSpaces){
	    String spacing = "";
	    for(int i = 0; i < numberOfSpaces; i++){
	       spacing += "    ";
        }
	    return spacing;
    }

    public String reassembleHtmlTags(){
	    String reassembledHtml = "";
	    int count = 0;
	    for(HtmlTagStructure tagData: this.htmlTags){
	        if(count < 2){
	           reassembledHtml = reassembledHtml + tagData.beginningTag + "\n";
            }else{
                reassembledHtml = reassembledHtml + tagData.beginningTag + tagData.endingTag + "\n";
            }
            count++;
        }
	    reassembledHtml = reassembledHtml + this.htmlTags.get(1).endingTag + "\n";
	   return reassembledHtml;
    }
}


public class Main {

    public static void main(String[] args) {
        FileManipulator fm = new FileManipulator();
        Parser parser = new Parser();
        fm.readAspidFile(args[0]);
        parser.parseHTML(fm.getAspidFileData());
        System.out.println(parser.reassembleHtmlTags());



    }
}
