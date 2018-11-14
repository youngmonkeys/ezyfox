package com.tvd12.ezyfox.morphia.testing.data;

import java.util.concurrent.ThreadLocalRandom;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import xyz.morphia.annotations.Entity;
import xyz.morphia.annotations.Id;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity(value = "ezyfox.mongodb.testing.monkey", noClassnameStored = true)
public class Monkey {
	@Id
	private Long id = (long) ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE);
	private String name = "monkey#" + ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE);
	
	public Monkey(Long id) {
		this.id = id;
	}
}
