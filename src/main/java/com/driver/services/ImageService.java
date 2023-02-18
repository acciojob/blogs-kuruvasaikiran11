package com.driver.services;

import com.driver.models.*;
import com.driver.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ImageService {

    @Autowired
    BlogRepository blogRepository2;
    @Autowired
    ImageRepository imageRepository2;

    public Image addImage(Integer blogId, String description, String dimensions){
        //add an image to the blog
        Image image = new Image();
        Blog blog = blogRepository2.findById(blogId).get();
        image.setBlog(blog);
        image.setDescription(description);
        image.setDimensions(dimensions);

        List<Image> listOfImages = blog.getImageList();

        if(Objects.isNull(listOfImages))
            listOfImages = new ArrayList<>();

        listOfImages.add(image);

        blog.setImageList(listOfImages);

        imageRepository2.save(image);
        blogRepository2.save(blog);

        return image;
    }

    public void deleteImage(Integer id){
        Image image = imageRepository2.findById(id).get();
        Blog blog = image.getBlog();

        List<Image> imageList = blog.getImageList();

        imageList.remove(image);

        blog.setImageList(imageList);

        imageRepository2.save(image);
    }

    public int countImagesInScreen(Image image, String screenDimensions) {
        //Find the number of images of given dimensions that can fit in a screen having `screenDimensions`
        if(image == null)
            return 0;
        int dim = Integer.parseInt(image.getDimensions());
        int totaldim = Integer.parseInt(screenDimensions);

        return totaldim/dim;
    }

    public Image findById(int id) {
        Image image = imageRepository2.findById(id).get();
        return image;
    }
}
