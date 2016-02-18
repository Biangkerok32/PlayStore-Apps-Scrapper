package com.appstore.scrapper;

public class Main {
	
	public static void main(String[] args) throws Exception {
		
		
		PlaystoreInfo playstoreInfo = new PlaystoreInfo("c1x.sdk.adhive");
		playstoreInfo.getInfo(new PlaystoreInfo.OnGetInfoListener() {
			@Override
			public void OnSuccess(PlaystoreInfo playstoreInfo) {
				System.out.println(playstoreInfo.getApplicationName());
			}
			
			@Override
			public void OnFaliure() { }
		});
	}
}
