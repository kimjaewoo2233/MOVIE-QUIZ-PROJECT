package com.movie.back.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService {


        @Value("${file.path}")
        private String uploadPath;

        public Resource  viewFileGet(String movieName){
                Resource resource = new FileSystemResource(uploadPath+ File.separator+movieName);

                String rresourceName = resource.getFilename();

                return resource;
        }

        public List<Resource> viewFileAll(){
            List<Resource> list = new ArrayList<>();
            File dir = new File(uploadPath);
            String[] filenames = dir.list();
            for(String fileName:filenames){
                    list.add(viewFileGet(fileName));
            }

            return list;
        }
}
