import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class USATodayData extends NewspaperData{

	private static String url="http://www.usatoday.com";
		
	public USATodayData() {
		super(url);
				
	}
	
	protected Element getFirstHeadlineLink() {
		//extract the headline link from the frontpage
		return super.getFirstHeadlineLink("hfwmm-primary-text-wrap", "a[href]");
	}

	@Override
	protected String getArticle() {
		//extract the link from the headline
		StringBuilder article= new StringBuilder(this.getFirstHeadlineLink().attr("href"));
		articleUrl=article.insert(0, url).toString();
		Document newdoc = null;
		StringBuilder content=null;
		try {
			//go to the link of the first headline
			newdoc = Jsoup.connect(article.toString())
					  .data("query", "Java")
					  .userAgent("Mozilla")
					  .cookie("auth", "token")
					  .timeout(6000)
					  .get();
			
			//extract the first headline content
			Elements contentDiv = newdoc.select("div[class=asset-double-wide double-wide p402_premium] p");
			content= new StringBuilder(contentDiv.text());
			//extract the links from the article
			Elements links= newdoc.select("div[class=asset-double-wide double-wide p402_premium]").first().select("a[href]");
			content=new StringBuilder(contentDiv.text());
			//append the links to the content
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
