package edu.nuc.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import edu.nuc.web.dto.FileInfo;

@RestController
@RequestMapping("/file")
public class FileController {

	private String ulr = "C:\\Users\\孔先生\\Desktop\\resource";

	@PostMapping
	public FileInfo upload(MultipartFile file) throws Exception {

		System.out.println(file.getName());// 参数名字
		System.out.println(file.getOriginalFilename());// 原文件名字
		System.out.println(file.getSize());

		// InputStream inputStream = file.getInputStream();

		File localFile = new File(ulr, new Date().getTime() + ".txt");

		file.transferTo(localFile);
		return new FileInfo(localFile.getAbsolutePath());

	}

	@GetMapping("/{id}")
	public void download(@PathVariable String id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		//自带关闭流
		try (InputStream inputStream = new FileInputStream(new File(ulr, id + ".txt"));
				OutputStream outputStream = response.getOutputStream();) {

			response.setContentType("application/x-download");
			response.addHeader("Content-Disposition", "attachment;filename="
					+ "test.txt");
			IOUtils.copy(inputStream, outputStream);
			outputStream.flush();
		} catch (Exception e) {

		}

	}

}
