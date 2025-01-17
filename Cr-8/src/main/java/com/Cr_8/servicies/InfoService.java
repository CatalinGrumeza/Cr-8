package com.Cr_8.servicies;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Cr_8.entities.Info;
import com.Cr_8.entities.Reference;
import com.Cr_8.entities.Status;
import com.Cr_8.exceptions.ResourceNotFoundException;
import com.Cr_8.repositories.InfoRepo;
import com.Cr_8.repositories.ReferenceRepo;

import jakarta.transaction.Transactional;

@Service
public class InfoService {
	@Autowired
	private InfoRepo infoRepo;
	@Autowired
	private ReferenceService referenceService;
	@Autowired
	private StatusService statusService;
	
	public List<Info> getAllInfo(){
		return infoRepo.findAll();
	}
	@Transactional//rollback in case of fail when saving
	public void createInfo(String name,String surname,String phone,String email,String Text){
		
		Info info=new Info();
		info.setText(Text);
		info.setCreatedAt(LocalDateTime.now());
		info.setStatus(statusService.findByName("Pending"));
		if(referenceService.findByEmail(email)!=null) {
			Reference user=referenceService.findByEmail(email);
			info.setReference(user);
		}else {
			Reference user=referenceService.createReference(email, surname, name, phone);
			info.setReference(user);
		}
		infoRepo.save(info);
		
	}
	public void updateInfoStatus(String status,Long infoId) {
		if(infoRepo.findById(infoId).isPresent()) {
			Info info=infoRepo.findById(infoId).get();
			if((status.equals("Completed")||status.equals("Pending"))&&status!=null)
				info.setStatus(statusService.findByName(status));
			infoRepo.save(info);
		}else {
			throw new ResourceNotFoundException("Info non trovata!");
		}
	}
	

}