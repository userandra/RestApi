package Test;

import static org.testng.Assert.assertEquals;

import java.io.File;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;


public class RestExample {
	
	@SuppressWarnings("unchecked")
	@Test  // first add test ng libraries si apoi import adnotarea Test
	public void restExampleTest() {
		
		//CONSTR UN OB JSON IN INTER PUNEM CELE 2 PERECHI DE CHWIE VALOARE
		JSONObject requestBody = new JSONObject();
		requestBody.put("title", "JSON title");
		requestBody.put("body", "JSON body");
		
		File fisier = new File("fisier.json");
		
		
		Response response = 
				given().
				//header("Content-type", "application/json").
				//header("accept", "application/json").
				//ex 1--> direct in body ca string
				//body("{\"title\":\"Test Andra din java\",\"body\":\"Test Andra din java\"}").
				//ex 2 --> body as JsonOject
				//body(requestBody.toJSONString()).
				//ex 3 --> body ca si fisier json
				//body(fisier).
				when().  //poate fi omis
				post("https://keytodorestapi.herokuapp.com/api/save").	
				then().
				assertThat().statusCode(200).
				extract().response(); //ob pe care il primesc pe req il pun in response
					
			System.out.println(response.asPrettyString());
			System.out.println(response.asString());
				
					
		String info = response.jsonPath().getString("info");
		System.out.println(info);
		assertEquals(info, "Todo saved! Nice job!");
		assertThat(info, is("Todo saved! Nice job!"));
		
			
			
	}

}
