package com.scm.services.impl;

import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.scm.helper.AppConstants;
import com.scm.services.ImageService;

@Service
public class ImageServiceImpl implements ImageService {
	
	private Cloudinary cloudinary;

	public ImageServiceImpl(Cloudinary cloudinary) {
		super();
		this.cloudinary = cloudinary;
	}



	@Override
	public String uploadImage(MultipartFile contactImage, String filename) {
				
		try 
		{
			//creating byte array of data, size is depends on the contactimage size
			byte[] data = new byte[contactImage.getInputStream().available()];
			
			//storing the value of contact image in the array of data
			contactImage.getInputStream().read(data);
			
			//Uploading the image in cloudinary
			cloudinary.uploader().upload(data, ObjectUtils.asMap(
																	"public_id",filename
																));
			
			return this.getURLFromPublicId(filename);
			
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}		
		
	}



	@Override
	public String getURLFromPublicId(String publicId) {

		return cloudinary.url()
						 .transformation(
								 			new Transformation<>().width(AppConstants.CONTACT_IMAGE_WIDTH)
								 								  .height(AppConstants.CONTACT_IMAGE_HIGHT)
								 								  .crop(AppConstants.CONTACT_IMAGE_CROP)
								        )
						 .generate(publicId);
	}

}
