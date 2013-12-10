
/**
 *
 * @author yvanbarbaria
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RestClientPost {
  
    public String input = "";
    
    public void RestClientPost(String inputJson){
        this.input = inputJson;
    }
    
   // http://localhost:8080/RESTfulExample/json/product/post
    public boolean communicationRestPost(String error) {

        boolean valueEnd = false;
        try {

            URL url = new URL("http://localhost:8080/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            OutputStream os = conn.getOutputStream();
            os.write(this.input.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                valueEnd = false;
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
                
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            conn.disconnect();

        } catch (MalformedURLException e) {

            valueEnd = false;
            error = "Malformed URL";
            e.printStackTrace();

        } catch (IOException e) {

            valueEnd = false;
            error = "Error";
            e.printStackTrace();

        }
           return valueEnd;
    }
}
