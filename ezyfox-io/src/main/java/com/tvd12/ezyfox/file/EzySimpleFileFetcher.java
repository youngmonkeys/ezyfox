package com.tvd12.ezyfox.file;

import java.io.File;

import com.tvd12.ezyfox.builder.EzyBuilder;
import com.tvd12.ezyfox.exception.EzyFileNotFoundException;
import com.tvd12.ezyfox.file.EzyFileFetcher;
import com.tvd12.ezyfox.file.EzySimpleFileFetcher;

public class EzySimpleFileFetcher implements EzyFileFetcher {

	protected boolean throwException;
	
	protected EzySimpleFileFetcher(Builder builder) {
		this.throwException = builder.throwException;
	}
	
	@Override
	public File get(String filePath) {
		File file = new File(filePath);
		if(file.exists())
			return file;
		if(isThrowException())
			throw new EzyFileNotFoundException(file);
		return null;
	}
	
	protected boolean isThrowException() {
		return throwException;
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder implements EzyBuilder<EzyFileFetcher> {

		protected boolean throwException = true;
		
		public Builder throwException(boolean throwException) {
			this.throwException = throwException;
			return this;
		}
		
		@Override
		public EzyFileFetcher build() {
			return new EzySimpleFileFetcher(this);
		}
	}
}
