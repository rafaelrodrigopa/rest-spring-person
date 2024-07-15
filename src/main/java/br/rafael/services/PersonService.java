package br.rafael.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import br.rafael.controllers.PersonController;
import br.rafael.data.vo.v1.PersonVO;
import br.rafael.data.vo.v2.PersonVOV2;
import br.rafael.exceptions.ResourceNotFoundExceptions;
import br.rafael.mapper.DozzerMapper;
import br.rafael.mapper.custom.PersonMapper;
import br.rafael.models.Person;
import br.rafael.repositories.PersonRepository;

@Service
public class PersonService {
	
	private Logger logger = Logger.getLogger(PersonService.class.getName());
	
	@Autowired
	PersonRepository personRepository;
	
	@Autowired
	PersonMapper personMapper;
	
	public List<PersonVO> findAll() {
		logger.info("finding all people!");
		
		var persons = DozzerMapper.parseListObjects(personRepository.findAll(), PersonVO.class);
		persons
			.stream()
			.forEach(p -> {
				try {
					p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		return persons;
	}
	
	public PersonVO findById(Long id) {
		logger.info("finding one person!");
		
		var entity = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundExceptions("No records found for this ID"));
		var vo = DozzerMapper.parseObject(entity, PersonVO.class);
		try {
			vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vo;
	}
	
	public PersonVO create(PersonVO person) {
		logger.info("Creating one person");
		
		var entity = DozzerMapper.parseObject(person, Person.class);
		var vo = DozzerMapper.parseObject(entity, PersonVO.class);
		try {
			vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vo;
	}
	public PersonVOV2 createV2(PersonVOV2 person) {
		logger.info("Creating one person with v2");
		
		var entity = personMapper.convertVoToEntity(person);
		var vo = personMapper.convertEntityToVo(personRepository.save(entity));
		return vo;
	}

	public PersonVO update(PersonVO person) {
		logger.info("Updating one person");
		var entity = personRepository.findById(person.getKey()).orElseThrow(() -> new ResourceNotFoundExceptions("No records found for this ID"));
		
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		
		var vo = DozzerMapper.parseObject(entity, PersonVO.class);
		try {
			vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vo;
	}

	public void delete(Long id) {
		logger.info("Deleting one person");
		
		var entity = personRepository.findById(id).orElseThrow(() -> new ResourceNotFoundExceptions("No records found for this ID"));
		personRepository.delete(entity);
	}
	
}

