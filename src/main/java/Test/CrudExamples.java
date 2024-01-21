package Test;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.json.simple.JSONObject;

public class CrudExamples {
	
	//obiective globale
	
	String id;
	//JSONObject body;
	JSONObject body, updatedBody; //l-am adaugat si pe updbody
	

	@BeforeClass
	public void setup() {
	
	//ca sa nu punem peste tot URLul
		
		RestAssured.baseURI = "https://keytodorestapi.herokuapp.com";
		
		//adaug
		body = new JSONObject();
		Faker fake = new Faker();
		
		body.put("title",fake.cat().name());
		body.put("body",fake.cat().name());
		
		//il add aici pe upd body
		
		updatedBody = new JSONObject();
		updatedBody.put("title",fake.artist().name());
		updatedBody.put("body",fake.backToTheFuture().quote());
		
	}
	
	//CREATE
	@Test(priority = 1)

public void postTodoMessage() {
		
		Response response = given().
		//contentType(ContentType.JSON).
		//am pus body ca ob trb sa-i sp ca-i trimitem un JSON
		header("Content-Type","application/json").
		body(body.toJSONString()).
		post("api/save").
		then().
		statusCode(200).
		body("info",equalTo("Todo saved! Nice job!")).
		extract().response();
		
		//ob de mai sus va fi populat cu id-ul
		id = response.jsonPath().getString("id");
		System.out.println(response.asPrettyString());
		
	}
	
	//READ
	@Test(priority = 2, dependsOnMethods = "postATodoMessage")
	public void readTodo() {
		
		//ca sa printeze raspunsul
		Response response = given().get("api/"+id).then().extract().response();
		System.out.println(response.asPrettyString());
		
			
	}
	
	//UPDATE
	@Test(priority = 3)
	public void updateTodo() {
	//ptr ca nu se pastreaza ce s-a incapsulat anterior vom fol din nou Responseca sa printeze raspunsul

	Response resp = given().
			contentType(ContentType.JSON).
			body(updatedBody.toJSONString()).
			when().
			put("api/todos"+id).
			then().
			statusCode(201).
			extract().response();
		System.out.println(resp.asPrettyString());

	}
	
	//DELETE
	@Test(priority = 4)
	public void deleteTodo() {
	//ptr ca nu se pastreaza ce s-a incapsulat anterior vom fol din nou Responseca sa printeze raspunsul

	Response resp = given().
			delete("api/delete"+id).
			then().
			statusCode(200).
			extract().response();
		System.out.println(resp.asPrettyString());
	
	}
}
	