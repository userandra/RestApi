package Test;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

import java.util.List;

public class JsonPathExample {
	
	@Test
	public void getAllUsers() {
		
		Response resp = given().
				get("https://keytrcrud.herokuapp.com/api/users/").
				then().
				extract().response();
		
		System.out.println(resp.asString());
		System.out.println(resp.jsonPath().getString("users"));
		
		JsonPath jsonPath = resp.jsonPath(); //in obiectul asta tin functia
		
		//get by index
		System.out.println(jsonPath.getString("users[2]"));
		System.out.println(jsonPath.getString("users[3].name"));
		System.out.println(jsonPath.getString("users[3].email"));
		System.out.println(jsonPath.getString("users[3].age"));
		
		System.out.println(jsonPath.getString("users.size"));
		System.out.println(jsonPath.getString("users.name"));
		
		List<String> allNames = jsonPath.getList("users.name");
		System.out.println(allNames.get(4));
		
		//sau cu instantiere "new"
		//List<String> allNames = new ArrayList<String>();
		//allNames = jsonPath.getList("users.name");
				
		System.out.println(jsonPath.getString("users._id"));
		
		System.out.println(jsonPath.getString("users.gender"));
		
		System.out.println("--------------------");
		
		//putem sa filtram decizionzl - ex sa dscoatem doar gender = m
		//o interogare conditionala
		System.out.println("all males :"+ jsonPath.getString("users.findAll{it.gender == 'm'}"));
		System.out.println("all males :"+ jsonPath.getString("users.findAll{it.gender == 'm'}.size()"));
		System.out.println("all males :"+ jsonPath.getString("users.findAll{it.gender == 'm'}._id"));
		System.out.println("all males :"+ jsonPath.getString("users.findAll{it.gender == 'm'}.name"));
		System.out.println("all males :"+ jsonPath.getString("users.findAll{it.gender == 'm' && it.age >=50}.name"));
		
		
		System.out.println("--------------------");
		System.out.println("all Danyka :" + jsonPath.getList("users.findAll{it.name == 'Danyka'}._id"));
		//System.out.println("single Danyka :" + jsonPath.getString("users.find{it.name == 'Danyka'}._id"));
		
		System.out.println("single Danyka :" + jsonPath.getString("users.find{it.name == 'Danyka' && it.age == 23}._id"));
		
		System.out.println("danyka sau Cristobal :" + jsonPath.getString("users.findAll{it.name == 'Danyka' || it.name == 'Cristobal' && it.age==12}.name"));

		System.out.println("all under 18 " + jsonPath.getList("users.findAll{it.age >18}.findAll{it.name =='Jairo'}"));
		
		
		
	}

}
