package com.Cr_8.servicies;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Cr_8.entities.Info;
import com.Cr_8.repositories.InfoRepo;

@Service
public class InfoService {
	@Autowired
	private InfoRepo infoRepo;
	
	public List<Info> getAllInfo(){
		return infoRepo.findAll();
	}
	public void createInfo(Info infoSent){
		Info info=new Info();
		info.setName(infoSent.getName());
		info.setEmail(infoSent.getEmail());
		info.setSurname(infoSent.getSurname());
		info.setText(infoSent.getText());
		info.setCreatedAt(LocalDateTime.now());
		infoRepo.save(info);
		
	}
	

}