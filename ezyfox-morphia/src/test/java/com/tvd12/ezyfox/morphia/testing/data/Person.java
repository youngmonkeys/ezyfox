package com.tvd12.ezyfox.morphia.testing.data;

import java.io.Serializable;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.bson.types.ObjectId;

import lombok.Getter;
import lombok.Setter;
import xyz.morphia.annotations.Entity;
import xyz.morphia.annotations.Id;

@Setter
@Getter
@Entity(value = "ezyfox.mongodb.testing.person", noClassnameStored = true)
public class Person implements Serializable {
	private static final long serialVersionUID = 903502360552527690L;
	
	@Id
	private ObjectId id;
	private String name = "name#" + ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE);
	private String password = UUID.randomUUID().toString();

}
