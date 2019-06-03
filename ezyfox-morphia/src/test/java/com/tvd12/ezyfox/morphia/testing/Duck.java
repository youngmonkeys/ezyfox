package com.tvd12.ezyfox.morphia.testing;

import java.util.concurrent.ThreadLocalRandom;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Entity(value = "ezyfox.mongodb.testing.duck", noClassnameStored = true)
public class Duck {

	@Id
	private Long id = (long) ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE);
	private String name = "cat#" + ThreadLocalRandom.current().nextInt(Integer.MAX_VALUE);
	private String fixedValue = "fixedValue";
	
	public Duck(Long id) {
		this.id = id;
	}
	
}
