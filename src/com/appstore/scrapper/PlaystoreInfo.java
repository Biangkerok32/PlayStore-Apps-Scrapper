package com.appstore.scrapper;

import java.util.Arrays;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * DAO Object to fetch Playstore information using JSOUP
 * Dependencies can be downloaded from here http://jsoup.org/download
 * or use Gradle/Maven as per site's instructions!
 * 
 * @author arindamnath
 * 
 * HACKY CODE WARINING!!!! Works with the current version of Playstore
 */
public class PlaystoreInfo {
	
	private final String PLAYSTORE_URL = "https://play.google.com/store/apps/details?id=";
	
	private List<String> classInfo = Arrays.asList(new String[]{
			"id-app-title",
			"rating-count",
			"score"});
	
	private List<String> additionalInfo = Arrays.asList(new String[]{
			"author",
			"genre",
			"price",
			"description",
			"datePublished",
			"fileSize",
			"numDownloads",
			"softwareVersion",
			"operatingSystems",
			"contentRating"});

	private Elements storeElement;
	
	private String packageName, applicationName, ratingCount, score, publisher, 
			genre, price, description, publishedOn, fileSize, downloadCount, version, contentRating, operatingSystems;
	private boolean isPaid = false;
	
	public interface OnGetInfoListener {
		public void OnSuccess(PlaystoreInfo playstoreInfo);
		public void OnFaliure();
	}
	
	public PlaystoreInfo() {
		
	}
	
	public PlaystoreInfo(String packageName) {
		this.packageName = packageName;
	}
	
	public void getInfo(OnGetInfoListener onGetInfo) { 
		try {
			if(packageName != null) {
				Document doc = Jsoup.connect(PLAYSTORE_URL + packageName).get();
				//Parse the class elements first
				for(String type : classInfo) {
					storeElement = doc.getElementsByClass(type);
					for (Element link : storeElement) {
						if(type.equals("id-app-title")) {
							applicationName = link.text();
						} else if(type.equals("rating-count")) {
							ratingCount = link.text();
						} else if(type.equals("score")) {
							score = link.text();
						}
						//System.out.println(type + " : " + link.text());
					}
				}
				//Parse the custom attribute information
				for(String type : additionalInfo) {
					storeElement = doc.getElementsByAttributeValue("itemprop", type);
					for (Element link : storeElement) {
						if(type.equals("author")) {
							publisher = link.getElementsByAttributeValue("itemprop", "name").text();
							//System.out.println(type + " : " + link.getElementsByAttributeValue("itemprop", "name").text());
						} else if(type.equals("price")) {
							price = link.attr("content");
							if(!price.equals("0")) {
								isPaid = true;
							}
							//System.out.println(type + " : " + link.attr("content"));
						} else if (type.equals("genre")) {
							genre = link.text();
						} else if (type.equals("description")) {
							description = link.text();
						} else if (type.equals("datePublished")) {
							publishedOn = link.text();
						} else if (type.equals("fileSize")) {
							fileSize = link.text();
						} else if (type.equals("numDownloads")) {
							downloadCount = link.text();
						} else if (type.equals("softwareVersion")) {
							version = link.text();
						} else if (type.equals("operatingSystems")) {
							operatingSystems = link.text();
						} else if (type.equals("contentRating")) {
							contentRating = link.text();
						}
						//System.out.println(type + " : " + link.text());
					}
				}
				onGetInfo.OnSuccess(this);
			} else {
				throw new Exception("Package Name cannot be null");
			}
		} catch (Exception e) {
			onGetInfo.OnFaliure();
		}
	}

	//////////////////////////////// Getter Methods ///////////////////////////////////////
	
	/**
	 * @return the packageName
	 */
	public String getPackageName() {
		return packageName;
	}

	/**
	 * @param packageName the packageName to set
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	/**
	 * @return the storeElement
	 */
	public Elements getStoreElement() {
		return storeElement;
	}

	/**
	 * @return the applicationName
	 */
	public String getApplicationName() {
		return applicationName;
	}

	/**
	 * @return the ratingCount
	 */
	public String getRatingCount() {
		return ratingCount;
	}

	/**
	 * @return the score
	 */
	public String getScore() {
		return score;
	}

	/**
	 * @return the publisher
	 */
	public String getPublisher() {
		return publisher;
	}

	/**
	 * @return the genre
	 */
	public String getGenre() {
		return genre;
	}

	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the publishedOn
	 */
	public String getPublishedOn() {
		return publishedOn;
	}

	/**
	 * @return the fileSize
	 */
	public String getFileSize() {
		return fileSize;
	}

	/**
	 * @return the downloadCount
	 */
	public String getDownloadCount() {
		return downloadCount;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @return the contentRating
	 */
	public String getContentRating() {
		return contentRating;
	}

	/**
	 * @return the operatingSystems
	 */
	public String getOperatingSystems() {
		return operatingSystems;
	}

	/**
	 * @return the isPaid
	 */
	public boolean isPaid() {
		return isPaid;
	}
}
