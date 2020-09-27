package com.codespacelab.user;

import com.codespacelab.user.model.UserDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
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

	}

}
