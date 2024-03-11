package api.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests {
	
	Faker faker;
	User userPayload;
	
	
	@BeforeClass
	public void setUpdata(){
		
		faker=new Faker();
		userPayload=new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password());
		userPayload.setPhone(faker.phoneNumber().cellPhone());
}
		@Test(priority=1)
		public void testPostUser() {
			Response res=UserEndPoints.createUser(userPayload);
			res.then().log().all();
			Assert.assertEquals(res.getStatusCode(), 200);
			
		}
		@Test(priority=2)
		public void testReadUser(){
			
			Response res=UserEndPoints.readUser(this.userPayload.getUsername());
			res.then().log().all();
			Assert.assertEquals(res.getStatusCode(), 200);
			
		}
		@Test(priority=3)
		public void testUpdateUser() {
			
			userPayload.setFirstName(faker.name().firstName());
			userPayload.setLastName(faker.name().lastName());
			userPayload.setEmail(faker.internet().safeEmailAddress());
			
			Response res=UserEndPoints.updateUser(this.userPayload.getUsername(),userPayload);
			res.then().log().body();
			Assert.assertEquals(res.getStatusCode(), 200);
			
			//checking after update
			Response resafterUpdate=UserEndPoints.readUser(this.userPayload.getUsername());
			//resafterUpdate.then().log().all();
			Assert.assertEquals(resafterUpdate.getStatusCode(), 200);
			
			}
		@Test(priority=4)
		public void deleteUserByName() {
			Response res=UserEndPoints.deleteUser(this.userPayload.getUsername());
			Assert.assertEquals(res.getStatusCode(), 200);
			
		}
}
