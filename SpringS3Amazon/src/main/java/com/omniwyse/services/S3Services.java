package com.omniwyse.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface S3Services {
	
	public List<String> getFileslistFromFolder() throws IOException;
	
	public String searchingInFiles(List<String> files,String searchKey,String searchValue) throws FileNotFoundException, IOException;
	
}
