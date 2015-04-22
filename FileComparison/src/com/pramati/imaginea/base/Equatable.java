/**
 * 
 */
package com.pramati.imaginea.base;

import java.util.Set;

/**
 * @author anandu
 *
 */
public interface Equatable<T> {

	public Set<String> equate(T target) throws Exception;
	
}
