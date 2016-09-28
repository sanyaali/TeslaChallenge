import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public abstract class NewspaperData {

	protected Document doc=null;
	protected String firstArticleTitle= null; 
	protected String articleUrl=null;
	
		
	public NewspaperData(String website) {
		
		try {
			this.doc = Jsoup.connect(website)
					  .data("query", "Java")
					  .userAgent("Mozilla")
					  .cookie("auth", "token")
					  .timeout(3000)
					  .post();
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}

	protected Document getFrontPage(String website){
		return doc;
	}
	
	protected Element getFirstHeadlineLink(String _class, String _selectParam){
		
		Elements links = doc.getElementsByClass(_class).select(_selectParam);
		Element	first=links.first();
		firstArticleTitle=first.text();
		return first;
	}
	
	protected abstract String getArticle();
	
	public boolean getArticleAndWriteTofile() throws IOException{
		

		 String content=this.getArticle();
		 Date dNow = new Date( );
	     SimpleDateFormat ft = new SimpleDateFormat ("yyyyMMdd"); 
	     String filename = ft.format(dNow).concat("-"+firstArticleTitle); 
	     String preContent= articleUrl+"\n\n"+firstArticleTitle+"\n";
	     filename = URLEncoder.encode(filename, "UTF-8");
		 this.writeToTextFile(filename, preContent+"\n"+content);
	    		 
		return true;
		
	}
	
	protected void writeToTextFile(String fileName, String content) throws IOException {
	    Files.write(Paths.get(fileName), content.getBytes(), StandardOpenOption.CREATE);
	  }
	
	
}
