package com.book.chapter4_flickr_client.pojo;

import com.book.chapter4_flickr_client.network.FlickrPhotoInfoResponse;
import com.book.chapter4_flickr_client.network.FlickrPhotosGetSizesResponse;

import java.util.List;

public class Photo {
    final private String id;
    final private String title;
    final private String username;
    final private String thumbnailUrl;

    public Photo(String id, String title, String username, String thumbnailUrl) {
        this.id = id;
        this.title = title;
        this.username = username;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUsername() {
        return username;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public static Photo createPhoto(FlickrPhotoInfoResponse.PhotoInfo photoInfo,
                                    List<FlickrPhotosGetSizesResponse.Size> sizes) {
        String thumbnailUrl = null;
        for (FlickrPhotosGetSizesResponse.Size size : sizes) {
            if (size.getLabel().equals("Square")) {
                thumbnailUrl = size.getSource();
                break;
            }
        }
        return new Photo(photoInfo.getId(), photoInfo.getTitle(), photoInfo.getUsername(), thumbnailUrl);
    }
}
