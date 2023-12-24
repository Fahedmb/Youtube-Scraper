import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONObject;

public class MiniProject {

	public static void main(String[] args) throws FileNotFoundException {
		
		File file = new File("C:\\Users\\fahed\\OneDrive\\Desktop\\channels.txt");
		Scanner scan = new Scanner(file);

		while (scan.hasNextLine()) {
			HttpsURLConnection con = null;
			try {
				URL u = new URL("https://www.googleapis.com/youtube/v3/channels?part=statistics&forUsername="+ scan.nextLine() +
						"&key=AIzaSyDBmPDk3jwtaVrEsoHLP33opXnB4h0Ls90");
				
				con = (HttpsURLConnection) u.openConnection();
				con.connect();
				

				
				BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line;
				
				while((line = br.readLine()) != null) {
					sb.append(line + "\n");
				}
				br.close();
				
				JSONObject jsonObj = new JSONObject(sb.toString());
				JSONArray items = jsonObj.getJSONArray("items");
				JSONObject object = items.getJSONObject(0);
				JSONObject ja = object.getJSONObject("statistics");
		
				System.out.println("ChannelName:"+scan.nextLine());
				System.out.println("totalViews:"+ ja.get("viewCount").toString());
				System.out.println("subs:"+ ja.get("subscriberCount").toString());
				System.out.println("totalVideos :"+ ja.get("videoCount").toString());	

				JSONObject JO = new JSONObject();
				JO.put("Channel_Name",scan.nextLine());
				JO.put("Total_Views", ja.get("viewCount"));
				JO.put("Subscribers",ja.get("subscriberCount"));
				JO.put("Total",ja.get("videoCount"));	
				
				FileWriter fw = new FileWriter("scraped_data.json");
				fw.write(JO.toString());
				fw.flush();
				fw.close();
				
			}catch(MalformedURLException ex) {
				ex.printStackTrace();
			}catch (IOException ex){
				ex.printStackTrace();
			}		
		}
	}
}

