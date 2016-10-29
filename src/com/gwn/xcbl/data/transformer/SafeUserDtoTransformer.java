package com.gwn.xcbl.data.transformer;

import org.apache.commons.collections4.Transformer;

import com.gwn.xcbl.data.hibernate.entity.User;
import com.gwn.xcbl.data.shared.UserDTO;

/**
 * Excludes password
 */
public class SafeUserDtoTransformer implements Transformer<User, UserDTO> {

	@Override
	public UserDTO transform(User input) {
		UserDTO r = new UserDTO();
		r.setId(input.getId());
		r.setUsername(input.getUsername());
		r.setRole(input.getRole());
		r.setEmail(input.getEmail());
		return r;
	}
}
