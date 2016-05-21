import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.*;
import java.util.*;
import static java.nio.file.StandardOpenOption.*;
import java.nio.file.*;

public class ProductInfo{

	private final String USER_AGENT = "";

	public static void main(String[] args) throws Exception {

		ProductInfo http = new ProductInfo();



		String productID = "17225269"; // Sample ID
		ArrayList<String> product1 = http.fetchDetails(productID);
		ArrayList<ArrayList<String>> ha = new ArrayList<ArrayList<String>>();
                ha.add(product1);
                http.writeToFile(ha);
}

	// HTTP GET request

    //String url = "http://api.target.com/products/v3/17225269?id_type=tcin&fields=pricing&key=Id8SS1KAXuFd2W7R60XC5AUTTGKbnU2U";

	// Input: product id
	// Output: json of the product in String form
	public ArrayList<String> fetchDetails(String productID) throws Exception {

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
		String str1 = response.toString();
                int index1 = str1.indexOf("display_price");
                int index2 = str1.indexOf("list_price");
                int index3 = str1.indexOf("general_description");
                index1+=16;
                index2+=13;
                index3+=22;
                int index_1 = str1.indexOf('"', index1);
                int index_2 = str1.indexOf('"', index2);
                int index_3 = str1.indexOf('"', index3);
                if(str1.charAt(index_3-1)=='\\')
                {
                    index3=index_3+2;
                    index_3 = str1.indexOf('"', index3);
                }
                String display_price = str1.substring(index1, index_1);
                String list_price = str1.substring(index2, index_2);
                String product_name = str1.substring(index3, index_3);
                ArrayList<String> product = new ArrayList<String>();
                product.add(display_price);
                product.add(list_price);
                product.add(product_name);
                product.add(productID);
                System.out.println(product);
                return product;
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
public void writeToFile(ArrayList<ArrayList<String>> products) {
	String line_to_write = "";
	Path p = Paths.get("../bin/logfile.txt");
	try (OutputStream out = new BufferedOutputStream(
		Files.newOutputStream(p, CREATE, APPEND))) {
			for (ArrayList<String> product_detail: products) {
				line_to_write += (product_detail.get(0) + " ");
				line_to_write += (product_detail.get(1) + " ");
				line_to_write += (product_detail.get(2) + " ");
				line_to_write += (product_detail.get(2) + " ");
				byte data[] = line_to_write.getBytes();
				out.write(data, 0, data.length);
			  }
	} catch (IOException x) {
		System.err.println(x);
	}

	}

}