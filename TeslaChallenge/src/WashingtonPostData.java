import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class WashingtonPostData extends NewspaperData {

	private static String url="http://www.washingtonpost.com";
	
	public WashingtonPostData() {
		
		super(url);
		
	}

	
	protected Element getFirstHeadlineLink() {
		
		return super.getFirstHeadlineLink("headline", "a[href]");
	}

	@Override
	protected String getArticle() {
		
		String article= this.getFirstHeadlineLink().attr("href");
		articleUrl=article;
		Document newdoc = null;
		StringBuilder content=null;
		try {
			newdoc = Jsoup.connect(article)
					  .data("query", "Java")
					  .userAgent("Mozilla")
					  .cookie("auth", "token")
					  .timeout(3000)
					  .get();
			
			Elements contentDiv = newdoc.select("div#article-body.article-body p");
			content= new StringBuilder(contentDiv.text());
			Elements links= newdoc.select("div#article-body.article-body").select("a[href]");
			for (Element link: links)
			{
				content.append("\n"+link.text());
				content.append("\n["+link.attr("href")+"]\n");
			}
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		return content.toString();
	}

	public boolean getArticleAndWriteTofile() throws IOException{
		 
		return super.getArticleAndWriteTofile();
	}
	

	

}
