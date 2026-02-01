package com.friendshipos.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dflu42n7n",
                "api_key","858928448599817",
                "api_secret","0mxxkt7n3StrNQLZanPQMFdkfU0"
        ));
    }
}
