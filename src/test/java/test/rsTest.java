package test;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.Assert;
import org.testng.Assert.*;
import org.testng.annotations.AfterTest;

import io.restassured.http.*;
import static io.restassured.path.json.JsonPath.from;
import static io.restassured.matcher.RestAssuredMatchers.*;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class rsTest {
  //RequestSpecification httpRequest;
  @Test (enabled=false)
  public void Validate_default_page_index_and_iterate_pages() {
	  int total_pages=0;
	//Verify the default page index   
	  total_pages= given().
	    contentType(ContentType.JSON).
	  when().
	  	get("https://reqres.in/api/users").
	  then().
	  	log().ifValidationFails().
	  	statusCode(200).and().body("page",equalTo(1)).
	  	log().all().
	  	extract().path("total_pages");
	  
	 //Verify if the paging is working  
	  for(int i=1; i< total_pages; i++) {
		  //reading each page
		  System.out.println("Get the " +i+ " page");
		  given().
		  	contentType(ContentType.JSON).
		  	pathParam("page", i).
		  	//param("page", String.valueOf(i)).
		  when().
		  	get("https://reqres.in/api/users?page={page}").
		  then().
		  	log().ifValidationFails().
		  	statusCode(200).and().
		  	body("page", equalTo(i)).
		  	log().all();
	  }
  }
  
  @Test
  public void Find_given_user_within_response() {
	  Response rs= given().
	  	contentType(ContentType.JSON).
	  	param("job","Qa").
	  	param("name", "tester").
	  when().
	  	get("https://reqres.in/api/users").
	  then().
	  	log().ifValidationFails().
	  	statusCode(200).and().
	  	extract().response();
	  
	  System.out.println("whole body in response \n\r" + rs.asString());
	  System.out.println("all headers: "+rs.headers().toString());
	  //
	  String jsonString = rs.asString();
	  HashMap<String,?> jsonBody= from(jsonString).get();
	  System.out.println(jsonBody.toString());
	  //Assert.assertEquals(jsonBody.size(), 3);
	  //List<String> ls= rs.jsonPath().getString("").getList("$");
	  
	  System.out.println("first names: " + rs.jsonPath().getString("data.id"));
	  System.out.println("first names: " + rs.path("data.first_name"));
	  System.out.println("total: " + rs.path("total")); 

	  	
	  	
	  
	  
  }
  
	
  @Test(enabled=false)
  @Parameters ("UserName")
  public void List_All_Users_and_Create_a_New_User(String UserName) {
	  //httpRequest.contentType("application/json");
	  
	  Response rs = RestAssured.given().get("https://reqres.in/api/users");
			  
	
	  System.out.println(rs.getHeaders());
	  System.out.println("------------------------------------");
	  System.out.println(rs.getTime());
	  System.out.println("------------------------------------");
	  System.out.println(rs.getCookies());
	  System.out.println("------------------------------------");
	  System.out.println(rs.getDetailedCookies());
	  System.out.println("------------------------------------");
	  System.out.println(rs.getStatusCode());
	  System.out.println("------------------------------------");
	  System.out.println(rs.getStatusLine());
	  System.out.println("------------------------------------");
	  System.out.println(rs.contentType());
	  System.out.println("------------------------------------");
	  System.out.println(rs.getSessionId());
	  System.out.println("------------------------------------");
	  System.out.println(rs.getBody().asString());
	  
	  RestAssured.given().
	  			  	contentType(ContentType.JSON).
	  			when().
	  			  	post("https://reqres.in/api/users").
  			  	then().
  			  		log().
  			  		ifValidationFails().statusCode(200).and().assertThat();
	  
	  //httpRequest.post("https://reqres.in/api/users").then().statusCode(201).log().all();
	  
	  //System.out.println(rs.get);
	  //RestAssured.given().when().get("http://www.google.com").then().statusCode(200).toString();
	  //System.out.println(RestAssured.given().when().get("http://www.google.com").then().statusCode(200).toString());
  }
  @BeforeTest
  public void beforeTest() {
	  String port = System.getProperty("server.port");
      if (port == null) {
          RestAssured.port = Integer.valueOf(8080);
      }
      else{
          RestAssured.port = Integer.valueOf(port);
      }


      String basePath = System.getProperty("server.base");
      if(basePath==null){
          basePath = "/restapi.demoqa.com/utilities/weather/city";
      }
      RestAssured.basePath = basePath;

      String baseHost = System.getProperty("server.host");
      if(baseHost==null){
          baseHost = "http://restapi.demoqa.com";
      }
      RestAssured.baseURI = baseHost;
      
      RequestSpecification httpRequest = RestAssured.given();
      Response rsp = httpRequest.request(Method.GET, RestAssured.baseURI + RestAssured.basePath + "/vancouver");
      int statusCode = rsp.getStatusCode();
      System.out.println("status code is: "+statusCode);
      System.out.println(rsp.getHeaders());
      String result = rsp.getBody().asString();
      System.out.println("WW: "+result);
     
      System.out.println("start the assert");
      //Assert.as
      Assert.assertEquals(200, 200, "unxpected result");
    		 // RequestSpecification(Method.GET, "http://restapi.demoqa.com/utilities/weather/city");
     
      
  }
}
