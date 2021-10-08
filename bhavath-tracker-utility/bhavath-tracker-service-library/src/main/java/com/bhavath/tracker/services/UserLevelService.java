package com.bhavath.tracker.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.bhavath.tracker.components.ComboComponent;
import com.bhavath.tracker.util.UserLevelEnum;
import com.bhavath.tracker.vos.ComboDataVO;
import com.bhavath.tracker.vos.UserLevelVO;

import lombok.extern.slf4j.Slf4j;

@Component
public interface UserLevelService {

	public List<ComboDataVO> getData4Combo(String... extraParams);


	@Service(value = "userLevelService")
	@Slf4j
	public class impl implements UserLevelService, ComboComponent {
		
		@Override
		public List<ComboDataVO> getData4Combo(String... extraParams) {
			Long level = Long.parseLong(extraParams[0]);
			List<UserLevelVO> dbContent = UserLevelEnum.getLevels(level);
			return dbContent.stream().map(p -> new ComboDataVO(p.getId(), p.getName(), null)).collect(Collectors.toList());
		}

	
	}

}