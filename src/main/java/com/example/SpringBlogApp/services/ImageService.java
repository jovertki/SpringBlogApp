package com.example.SpringBlogApp.services;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ImageService {
    @Autowired
    private GridFsOperations gridFsOperations;

    public String save(MultipartFile file, String postId) throws IOException {
        DBObject metaData = new BasicDBObject();
        metaData.put("type", "image");
        InputStream inputStream = new BufferedInputStream(file.getInputStream());

        return gridFsOperations.store(inputStream, postId, "image", metaData).toString();
    }

    public InputStream getById(String id) throws IOException {
        GridFSFile file = gridFsOperations.findOne(new Query(Criteria.where("_id").is(id)));
        if (file == null){
            return InputStream.nullInputStream();
        }
        return gridFsOperations.getResource(file).getInputStream();
    }

    public String replace(MultipartFile file, String oldImageId, String postId) throws IOException {
        deleteById(oldImageId);
        return save(file, postId);
    }
    public void deleteById(String id) {
        gridFsOperations.delete(new Query(Criteria.where("_id").is(id)));
    }
}
