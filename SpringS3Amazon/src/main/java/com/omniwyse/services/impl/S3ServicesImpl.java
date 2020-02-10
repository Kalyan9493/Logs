package com.omniwyse.services.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.omniwyse.services.S3Services;
import com.amazonaws.services.s3.model.S3Object;

@Service
public class S3ServicesImpl implements S3Services {
	
	private Logger logger = LoggerFactory.getLogger(S3ServicesImpl.class);
	
	@Autowired
	private AmazonS3 s3client;

	@Value("${jsa.s3.bucket}")
	private String bucketName;
	
	@Value("${jsa.s3.prefix}")
	private String keyPrefix;
	
	@Value("${jsa.s3.searchKey}")
	private String searchKey;
	
	@Value("${jsa.s3.searchValue}")
	private String searchValue;
	
	private ListObjectsRequest listObjectsRequest;

	public List<String> getFileslistFromFolder() throws IOException {
		   
		 listObjectsRequest=new ListObjectsRequest().withBucketName(bucketName).withPrefix(keyPrefix + "/");
		 
		  List<String> keys = new ArrayList<>();
		 
		  ObjectListing objects = s3client.listObjects(listObjectsRequest);
		  for (;;) {
		    List<S3ObjectSummary> summaries = objects.getObjectSummaries();
		    if (summaries.size() < 1) {
		      break;
		    }
		    summaries.forEach(s -> keys.add(s.getKey()));
		    objects = s3client.listNextBatchOfObjects(objects);
		  }
		  logger.info("Returning Keys");
		  
		  for (String string : keys) {
			  
			  System.out.println(string);
			
		}
		System.out.println( searchingInFiles(keys, searchKey, searchValue));
		 
		  return keys;
		}

	@Override
	public String searchingInFiles(List<String> files,String searchKey,String searchValue) throws IOException {
		
		
		String searchingString="<ns2:"+searchKey+">"+searchValue+"</ns2:"+searchKey+">";
		
		for (String key : files) {
			
			S3Object s3object = s3client.getObject(bucketName, key);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(s3object.getObjectContent()));
			
			String readline=br.readLine();
			while(readline!=null) {
				
				if(readline.trim().contains(searchingString.trim())) {
					return "Key Contains In File:"+key;
				}
				readline=br.readLine();
				
			}
		
		}
		return "Key Doesn't Contain";
	}

}
