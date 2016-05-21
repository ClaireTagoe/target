

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



import javax.net.ssl.HttpsURLConnection;

public class ProductInfo{

	private final String USER_AGENT = "";

	public static void main(String[] args) throws Exception {

		ProductInfo http = new ProductInfo();



		String productID = "17225269"; // Sample ID
		http.fetchDetails(productID);


	}

	// HTTP GET request

    //String url = "http://api.target.com/products/v3/17225269?id_type=tcin&fields=pricing&key=Id8SS1KAXuFd2W7R60XC5AUTTGKbnU2U";

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


	public Integer calculate_discount(String display_price, String list_price) {
/* Takes in a list_price and display_price and returns the discout */
    float display = Float.valueOf(display_price.trim()).floatValue();
		float list = Float.valueOf(list_price.trim()).floatValue();
    System.out.println("float display_price = " + display);
		System.out.println("float list_price = " + list);

		if (display == list) return 0;
		Integer discount = (int)((list - display)/list * 100);
		return discount;
}

}
