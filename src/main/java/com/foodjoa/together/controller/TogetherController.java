package com.foodjoa.together.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.foodjoa.together.service.TogetherService;
import com.foodjoa.together.vo.TogetherJoinVO;
import com.foodjoa.together.vo.TogetherReplyVO;
import com.foodjoa.together.vo.TogetherVO;

@Controller
@RequestMapping("Together")
public class TogetherController {

	@Autowired
	private TogetherService togetherService;
	
	@RequestMapping(value = "list", method = { RequestMethod.GET })
	public String list(Model model) {
		
		List<TogetherVO> togethers = togetherService.getTogethers();
		
		// key : 각 모임의 no
		HashMap<Integer, List<TogetherJoinVO>> classifiedJoin = togetherService.getJoins();
		
		model.addAttribute("togethers", togethers);
		model.addAttribute("classifiedJoin", classifiedJoin);
		
		return "/togethers/list";
	}

	@RequestMapping(value = "edit", method = { RequestMethod.GET })
	public String edit(Model model,
			@RequestParam(defaultValue = "0") int no) {
		
		TogetherVO together = togetherService.getTogether(no);
		
		model.addAttribute("together", together);
		
		return "/togethers/edit";
	}
	
	@ResponseBody
	@RequestMapping(value = "edit", method = { RequestMethod.POST })
	public String edit(TogetherVO togetherVO, MultipartHttpServletRequest multipartRequest,
			@RequestParam String _joinDate,
			@RequestParam String originPictures,
			@RequestParam String originSelectedPictures) throws Exception {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
		Date parsedDate = format.parse(_joinDate);
		togetherVO.setJoinDate(new Timestamp(parsedDate.getTime()));
		
		int togetherNo = togetherService.processTogetherEdit(
				togetherVO, 
				multipartRequest, 
				originPictures, 
				originSelectedPictures);
		
		return String.valueOf(togetherNo);
	}

	@RequestMapping(value = "read", method = { RequestMethod.GET })
	public String read(Model model, HttpSession session, @RequestParam int no) {
		
		String userId = (String) session.getAttribute("userId");
		
		HashMap<String, Object> togetherInfo = togetherService.processTogetherRead(no, userId);
		
		model.addAttribute("togetherInfo", togetherInfo);
		
		return "/togethers/read";
	}
	
	@ResponseBody
	@RequestMapping(value = "delete", method = { RequestMethod.GET })
	public String delete(@RequestParam int no) {
		return String.valueOf(togetherService.deleteTogether(no));
	}
	
	@ResponseBody
	@RequestMapping(value = "togetherJoin", method = { RequestMethod.GET })
	public String togetherJoin(HttpSession session, @RequestParam int no) {
		return String.valueOf(togetherService.processTogetherJoin(no, (String) session.getAttribute("userId")));
	}
	
	@ResponseBody
	@RequestMapping(value = "replyEdit", method = { RequestMethod.POST })
	public String replyEdit(TogetherReplyVO replyVO) {
		return String.valueOf(togetherService.addReply(replyVO));
	}
	
	@ResponseBody
	@RequestMapping(value = "deleteReply", method = { RequestMethod.GET })
	public String deleteReply(@RequestParam int no) {
		return String.valueOf(togetherService.deleteReply(no));
	}
	
	@ResponseBody
	@RequestMapping(value = "deleteJoin", method = { RequestMethod.GET })
	public String deleteJoin(TogetherJoinVO joinVO) {
		return String.valueOf(togetherService.deleteJoin(joinVO));
	}
	
	@ResponseBody
	@RequestMapping(value = "togetherFinish", method = { RequestMethod.GET })
	public String togetherFinish(@RequestParam int no) {
		return String.valueOf(togetherService.processTogetherFinish(no));
	}
}
