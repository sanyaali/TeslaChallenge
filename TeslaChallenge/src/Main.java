import java.io.IOException;


public class Main {

	public static void main(String[] args) throws IOException {

		WashingtonPostData page1= new WashingtonPostData();
		USATodayData page2= new USATodayData();
		
		page1.getArticleAndWriteTofile();
		page2.getArticleAndWriteTofile();

		
	}

}
