package com.example.demo.services;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

@Service
public class POSTaggerServiceImpl implements POSTaggerService {
	
	POSTaggerME tagger;
	
	//getClass().getClassLoader().getResource("en-pos-maxent.bin").getFile())
	
	POSTaggerServiceImpl() throws IOException{
		try(InputStream modelIn = new ClassPathResource("en-pos-maxent.bin").getInputStream()) {
			POSModel model = new POSModel(modelIn);
			this.tagger = new POSTaggerME(model);
		}
		System.out.println("Init POSTagger success");
	}

	public String[] tag(String sentence) {
		String[] sentenceArr = sentence.split(" ");
		return this.tagger.tag(sentenceArr);
	}
	
}
