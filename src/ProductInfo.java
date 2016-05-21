

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
//import org.json.*;	// Tiff does not have this

import javax.net.ssl.HttpsURLConnection;

public class ProductInfo{

	private final String USER_AGENT = "";

	public static void main(String[] args) throws Exception {

		ProductInfo http = new ProductInfo();

		String productID = "17225269"; // Sample ID
		http.fetchDetails(productID);

	}

	// HTTP GET request
	// Input: product id
	// Output: json of the product in String form
	public String fetchDetails(String productID) throws Exception {

		String url = "http://api.target.com/products/v3/" + productID + "?id_type=tcin&fields=pricing&key=Id8SS1KAXuFd2W7R60XC5AUTTGKbnU2U";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		System.out.println(response.toString());

		
		return response.toString();
	}


	public void fileOpen() {

	}

}
