package edu.nuc.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class) // 执行测试用例
@SpringBootTest
public class UserControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;// 伪造mvc环境

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void whenQuerySuccess() throws Exception {
		String result = mockMvc.perform(get("/user")// 模拟发送请求
				.param("username", "konglingze").param("age", "18").param("sex", "1")
				// 分页参数
				/*
				 * .param("size", "15") .param("page", "3") .param("sort", "age,desc")
				 */
				.contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk())// 设置期望返回的结果状态是OK
				// jsonpath-json数据表-github参数组合
				.andExpect(jsonPath("$.length()").value(3))// 期望返回结果是3
				.andReturn().getResponse().getContentAsString();// 按照json格式返回查询结果视图jsonview
		// 1.javabean声明接口和get指定视图 2. controller指定视图

		System.out.println(result);
	}

	@Test
	public void whenGenInfoSuccess() throws Exception {
		String result = mockMvc.perform(get("/user/1").contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk()).andExpect(jsonPath("$.username").value("nuc")).andReturn().getResponse()
				.getContentAsString();
		System.out.println(result);
	}

	@Test
	public void whenGenInfoSuccessFail() throws Exception {
		mockMvc.perform(get("/user/1").contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().is4xxClientError()).andExpect(jsonPath("$.username").value("nuc"));
	}

	@Test
	public void whenCreateSuccess() throws Exception {
		String jsonString = "{\"username\":\"nuc\",\"password\":\"22\"}";

		String result = mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonString))
				.andExpect(status().isOk()).andDo(print())// 显示载入中间步骤
				.andExpect(jsonPath("$.id").value("1"))// 返回期望结果
				.andReturn().getResponse().getContentAsString();
		System.out.println(result);

	}

	@Test
	public void whenCreateSuccess2() throws Exception {
		Date date = new Date();
		System.out.println(date.getTime());
		String jsonString = "{\"username\":\"nuc\",\"password\":null,\"birthday\":\"" + date.getTime() + "\"}";
		String result = mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonString))
				.andExpect(status().isOk()).andDo(print())// 显示载入中间步骤
				.andExpect(jsonPath("$.id").value("1"))// 返回期望结果
				.andReturn().getResponse().getContentAsString();
		System.out.println(result);

	}

	@Test
	public void whenUpdateSuccess() throws Exception {
		Date date = new Date(// 获取未来的时间
				LocalDateTime.now().plusYears(1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
		System.out.println(date.getTime());
		String jsonString = "{\"username\":\"1\",\"username\":\"nuc\",\"password\":null,\"birthday\":\""
				+ date.getTime() + "\"}";
		String result = mockMvc.perform(put("/user/1").contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonString))
				.andExpect(status().isOk()).andDo(print())// 显示载入中间步骤
				.andExpect(jsonPath("$.id").value("1"))// 返回期望结果
				.andReturn().getResponse().getContentAsString();
		System.out.println(result);

	}

	@Test
	public void whenDeleteSuccess() throws Exception {

		String result = mockMvc.perform(delete("/user/1").contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk()).andDo(print())// 显示载入中间步骤
				.andReturn().getResponse().getContentAsString();
		System.out.println(result);

	}

	@Test
	public void whenUploadSuccess() throws Exception {
	String json = mockMvc.perform(fileUpload("/file").file(
				   new MockMultipartFile("file", "test.txt", "multipart/form-data", "hello upload".getBytes("Utf-8"))))
				 .andDo(print())
				 .andExpect(status().isOk())
				 .andReturn().getResponse().getContentAsString();
		System.out.println(json);

	}

}