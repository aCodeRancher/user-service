package com.codespacelab.user;

import com.codespacelab.user.model.UserDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
//restore the List<UserDto> to the original value with 2 users after each test
//because there is a test for add , put and delete.
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserApplicationTests {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@Test
	void getUsersTest() throws Exception{
		 MvcResult result = mockMvc.perform(get("/user/all")).andReturn();
         String content =  result.getResponse().getContentAsString();
		 List<UserDto> users = objectMapper.readValue(content,
				new TypeReference<List<UserDto>>() {});
		 assertEquals(users.get(0).getName(), "Peter");
		 assertEquals(users.get(1).getName(), "Louise");
		 assertEquals(users.size(),2);
    }

	@Test
	void getUserTest() throws Exception{
		MvcResult result = mockMvc.perform(get("/user?id=123")).andReturn();
		String content =  result.getResponse().getContentAsString();
		UserDto  user = objectMapper.readValue(content, UserDto.class);
		assertEquals(user.getName(), "Peter");
    }

	@Test
    void addUserTest() throws Exception{
		UserDto user = new UserDto(1L, "John", true);
		String jsonString = objectMapper.writeValueAsString(user);
		MvcResult result = mockMvc.perform(post("/user")
		                                   .content(jsonString)
				                           .contentType(MediaType.APPLICATION_JSON))
				                           .andReturn();
		String content =  result.getResponse().getContentAsString();
		UserDto addedUser = objectMapper.readValue(content, UserDto.class);
        assertEquals(addedUser.getName(), "John");
		//Get all users to see if there are 3 users now
        result = mockMvc.perform(get("/user/all")).andReturn();
        content =  result.getResponse().getContentAsString();
		List<UserDto> users = objectMapper.readValue(content,
				new TypeReference<List<UserDto>>() {});
		assertEquals(users.get(0).getName(), "Peter");
		assertEquals(users.get(1).getName(), "Louise");
        assertEquals(users.get(2).getName(), "John");
		assertEquals(users.size(),3);
    }

	@Test
	void addInvalidUserTest() throws Exception{
		UserDto user = new UserDto(null, null, true);
		String jsonString = objectMapper.writeValueAsString(user);
		MvcResult result = mockMvc.perform(post("/user")
				.content(jsonString)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andReturn();
        String content = result.getResponse().getContentAsString();
        assertTrue(content.equals("invalid argument"));
	}

	@Test
	void updateUserTest() throws Exception {
		 UserDto userToUpdate = new UserDto(123L, "Paul", true);
		 String userJson = objectMapper.writeValueAsString(userToUpdate);
		 mockMvc.perform(put("/user").content(userJson)
		                  .contentType(MediaType.APPLICATION_JSON)).andReturn();
         MvcResult result = mockMvc.perform(get("/user?id=123")).andReturn();
	     String content =  result.getResponse().getContentAsString();
		 UserDto  user = objectMapper.readValue(content, UserDto.class);
		 assertEquals(user.getName(), "Paul");
	}

	@Test
	void updateInvalidUserTest() throws Exception {
		UserDto userToUpdate = new UserDto(123L, "x", true);
		String userJson = objectMapper.writeValueAsString(userToUpdate);
	    MvcResult result = mockMvc.perform(put("/user").content(userJson)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andReturn();
		String content = result.getResponse().getContentAsString();
		//assertTrue(content.equals("Argument invalid"));
	}

	@Test
	void deleteUserTest() throws Exception{
		MvcResult result = mockMvc.perform(delete("/user?id=123")).andReturn();
		String content = result.getResponse().getContentAsString();
		assert(content.contains("true"));
		result = mockMvc.perform(get("/user/all")).andReturn();
		content =  result.getResponse().getContentAsString();
		List<UserDto> users = objectMapper.readValue(content,
				new TypeReference<List<UserDto>>() {});
		assertEquals(users.size(),1);
	}

	@Test
	void validateUserTest() throws Exception{
		MvcResult result = mockMvc.perform(get("/user/validate?id=123")).andReturn();
		String content = result.getResponse().getContentAsString();
		assert(content.contains("true"));
	}

	@Test
	void validateUserError() throws Exception{
		mockMvc.perform(get("/user/validate?id=0"))
				.andExpect(status().is5xxServerError());
	}
}
